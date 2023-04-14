package mod.azure.doom.entity.projectiles;

import mod.azure.azurelib.AzureLibMod.AzureBlocks;
import mod.azure.azurelib.entities.TickingLightEntity;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomProjectiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

public class ArgentBoltEntity extends AbstractArrow {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	public static final EntityDataAccessor<Boolean> PARTICLE = SynchedEntityData.defineId(ArgentBoltEntity.class, EntityDataSerializers.BOOLEAN);
	private LivingEntity shooter;
	private BlockPos lightBlockPos = null;
	private int idleTicks = 0;
	public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

	public ArgentBoltEntity(EntityType<? extends ArgentBoltEntity> entityType, Level world) {
		super(entityType, world);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public ArgentBoltEntity(Level world, LivingEntity owner) {
		super(DoomProjectiles.ARGENT_BOLT.get(), owner, world);
		this.shooter = owner;
	}

	protected ArgentBoltEntity(EntityType<? extends ArgentBoltEntity> type, double x, double y, double z, Level world) {
		this(type, world);
	}

	protected ArgentBoltEntity(EntityType<? extends ArgentBoltEntity> type, LivingEntity owner, Level world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		this.shooter = owner;
		if (owner instanceof Player)
			this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(PARTICLE, false);
	}

	public boolean useParticle() {
		return (Boolean) this.entityData.get(PARTICLE);
	}

	public void setParticle(boolean spin) {
		this.entityData.set(PARTICLE, spin);
	}

	@Override
	protected void doPostHurtEffects(LivingEntity living) {
		super.doPostHurtEffects(living);
		if (!(living instanceof Player) && !(living instanceof IconofsinEntity)) {
			living.setDeltaMovement(0, 0, 0);
			living.invulnerableTime = 0;
		}
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void tickDespawn() {
		++this.ticksInAir;
		if (this.tickCount >= 40)
			this.remove(RemovalReason.KILLED);
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		super.shoot(x, y, z, velocity, inaccuracy);
		this.ticksInAir = 0;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putShort("life", (short) this.ticksInAir);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.ticksInAir = compound.getShort("life");
	}

	@Override
	public void tick() {
		var idleOpt = 100;
		if (getDeltaMovement().lengthSqr() < 0.01)
			idleTicks++;
		else
			idleTicks = 0;
		if (idleOpt <= 0 || idleTicks < idleOpt)
			super.tick();
		++this.ticksInAir;
		if (this.ticksInAir >= 80)
			this.remove(Entity.RemovalReason.DISCARDED);
		var isInsideWaterBlock = level.isWaterAt(blockPosition());
		spawnLightSource(isInsideWaterBlock);
		if (this.level.isClientSide())
			this.level.addParticle(this.useParticle() ? ParticleTypes.ANGRY_VILLAGER : ParticleTypes.FLASH, true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
	}

	private void spawnLightSource(boolean isInWaterBlock) {
		if (lightBlockPos == null) {
			lightBlockPos = findFreeSpace(level, blockPosition(), 2);
			if (lightBlockPos == null)
				return;
			level.setBlockAndUpdate(lightBlockPos, AzureBlocks.TICKING_LIGHT_BLOCK.get().defaultBlockState());
		} else if (checkDistance(lightBlockPos, blockPosition(), 2)) {
			var blockEntity = level.getBlockEntity(lightBlockPos);
			if (blockEntity instanceof TickingLightEntity)
				((TickingLightEntity) blockEntity).refresh(isInWaterBlock ? 20 : 0);
			else
				lightBlockPos = null;
		} else
			lightBlockPos = null;
	}

	private boolean checkDistance(BlockPos blockPosA, BlockPos blockPosB, int distance) {
		return Math.abs(blockPosA.getX() - blockPosB.getX()) <= distance && Math.abs(blockPosA.getY() - blockPosB.getY()) <= distance && Math.abs(blockPosA.getZ() - blockPosB.getZ()) <= distance;
	}

	private BlockPos findFreeSpace(Level world, BlockPos blockPos, int maxDistance) {
		if (blockPos == null)
			return null;

		var offsets = new int[maxDistance * 2 + 1];
		offsets[0] = 0;
		for (var i = 2; i <= maxDistance * 2; i += 2) {
			offsets[i - 1] = i / 2;
			offsets[i] = -i / 2;
		}
		for (var x : offsets)
			for (var y : offsets)
				for (var z : offsets) {
					var offsetPos = blockPos.offset(x, y, z);
					var state = world.getBlockState(offsetPos);
					if (state.isAir() || state.getBlock().equals(AzureBlocks.TICKING_LIGHT_BLOCK.get()))
						return offsetPos;
				}

		return null;
	}

	public void initFromStack(ItemStack stack) {
		if (stack.getItem() == DoomItems.ARGENT_BOLT.get()) {
		}
	}

	@Override
	public boolean isNoGravity() {
		if (this.isInWater())
			return false;
		return true;
	}

	@Override
	public void setSoundEvent(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.ARMOR_EQUIP_IRON;
	}

	@Override
	public void remove(RemovalReason reason) {
		if (this.useParticle()) {
			AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
			areaeffectcloudentity.setParticle(ParticleTypes.LAVA);
			areaeffectcloudentity.setRadius(6);
			areaeffectcloudentity.setDuration(1);
			areaeffectcloudentity.setPos(this.getX(), this.getY(), this.getZ());
			this.level.addFreshEntity(areaeffectcloudentity);
			level.playSound((Player) null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0F, 1.5F);
		}
		super.remove(reason);
	}

	protected void explode() {
		final AABB aabb = new AABB(this.blockPosition().above()).inflate(2D, 2D, 2D);
		this.getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
			if (e instanceof LivingEntity) {
				e.hurt(DamageSource.playerAttack((Player) this.shooter), DoomConfig.SERVER.argent_bolt_damage.get().floatValue());
			}
		});
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		if (entityHitResult.getType() != HitResult.Type.ENTITY || !((EntityHitResult) entityHitResult).getEntity().is(entity)) {
			if (!this.level.isClientSide) {
				this.remove(RemovalReason.KILLED);
			}
		}
		Entity entity1 = this.getOwner();
		DamageSource damagesource;
		if (entity1 == null) {
			damagesource = DamageSource.arrow(this, this);
		} else {
			damagesource = DamageSource.arrow(this, entity1);
			if (entity1 instanceof LivingEntity) {
				((LivingEntity) entity1).setLastHurtMob(entity);
			}
		}
		if (entity.hurt(damagesource, DoomConfig.SERVER.argent_bolt_damage.get().floatValue())) {
			if (entity instanceof LivingEntity) {
				LivingEntity livingentity = (LivingEntity) entity;
				if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
					EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
					EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingentity);
					this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.5F, Explosion.BlockInteraction.NONE);
					this.remove(RemovalReason.KILLED);
				}
				this.doPostHurtEffects(livingentity);
				if (entity1 != null && livingentity != entity1 && livingentity instanceof Player && entity1 instanceof ServerPlayer && !this.isSilent()) {
					((ServerPlayer) entity1).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
				}
			}
		} else {
			if (!this.level.isClientSide) {
				this.remove(RemovalReason.KILLED);
			}
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		if (!this.level.isClientSide())
			this.remove(Entity.RemovalReason.DISCARDED);
		this.setSoundEvent(SoundEvents.ARMOR_EQUIP_IRON);
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(DoomItems.ARGENT_BOLT.get());
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}
}