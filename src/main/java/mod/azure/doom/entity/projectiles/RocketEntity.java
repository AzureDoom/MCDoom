package mod.azure.doom.entity.projectiles;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.entity.tileentity.TickingLightEntity;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomSounds;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class RocketEntity extends AbstractArrow implements GeoEntity {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private LivingEntity shooter;
	private float projectiledamage;
	private BlockPos lightBlockPos = null;
	private int idleTicks = 0;
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

	public RocketEntity(EntityType<? extends RocketEntity> entityType, Level world) {
		super(entityType, world);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public RocketEntity(Level world, LivingEntity owner) {
		super(ProjectilesEntityRegister.ROCKET, owner, world);
		this.shooter = owner;
	}

	public RocketEntity(Level world, LivingEntity owner, float damage) {
		super(ProjectilesEntityRegister.ROCKET, owner, world);
		this.shooter = owner;
		this.projectiledamage = damage;
	}

	protected RocketEntity(EntityType<? extends RocketEntity> type, double x, double y, double z, Level world) {
		this(type, world);
	}

	protected RocketEntity(EntityType<? extends RocketEntity> type, LivingEntity owner, Level world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		if (owner instanceof Player) {
			this.pickup = AbstractArrow.Pickup.DISALLOWED;
		}
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			return PlayState.CONTINUE;
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
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
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	protected void tickDespawn() {
		++this.ticksInAir;
		if (this.tickCount >= 40) {
			this.remove(RemovalReason.KILLED);
		}
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
		int idleOpt = 100;
		if (getDeltaMovement().lengthSqr() < 0.01)
			idleTicks++;
		else
			idleTicks = 0;
		if (idleOpt <= 0 || idleTicks < idleOpt)
			super.tick();
		++this.ticksInAir;
		if (this.ticksInAir >= 80) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		boolean isInsideWaterBlock = level.isWaterAt(blockPosition());
		spawnLightSource(isInsideWaterBlock);
		if (this.level.isClientSide()) {
			double d2 = this.getX() + (this.random.nextDouble()) * (double) this.getBbWidth() * 0.5D;
			double f2 = this.getZ() + (this.random.nextDouble()) * (double) this.getBbWidth() * 0.5D;
			this.level.addParticle(ParticleTypes.SMOKE, true, d2, this.getY(), f2, 0, 0, 0);
		}
	}

	private void spawnLightSource(boolean isInWaterBlock) {
		if (lightBlockPos == null) {
			lightBlockPos = findFreeSpace(level, blockPosition(), 2);
			if (lightBlockPos == null)
				return;
			level.setBlockAndUpdate(lightBlockPos, DoomBlocks.TICKING_LIGHT_BLOCK.defaultBlockState());
		} else if (checkDistance(lightBlockPos, blockPosition(), 2)) {
			BlockEntity blockEntity = level.getBlockEntity(lightBlockPos);
			if (blockEntity instanceof TickingLightEntity) {
				((TickingLightEntity) blockEntity).refresh(isInWaterBlock ? 20 : 0);
			} else
				lightBlockPos = null;
		} else
			lightBlockPos = null;
	}

	private boolean checkDistance(BlockPos blockPosA, BlockPos blockPosB, int distance) {
		return Math.abs(blockPosA.getX() - blockPosB.getX()) <= distance
				&& Math.abs(blockPosA.getY() - blockPosB.getY()) <= distance
				&& Math.abs(blockPosA.getZ() - blockPosB.getZ()) <= distance;
	}

	private BlockPos findFreeSpace(Level world, BlockPos blockPos, int maxDistance) {
		if (blockPos == null)
			return null;

		int[] offsets = new int[maxDistance * 2 + 1];
		offsets[0] = 0;
		for (int i = 2; i <= maxDistance * 2; i += 2) {
			offsets[i - 1] = i / 2;
			offsets[i] = -i / 2;
		}
		for (int x : offsets)
			for (int y : offsets)
				for (int z : offsets) {
					BlockPos offsetPos = blockPos.offset(x, y, z);
					BlockState state = world.getBlockState(offsetPos);
					if (state.isAir() || state.getBlock().equals(DoomBlocks.TICKING_LIGHT_BLOCK))
						return offsetPos;
				}

		return null;
	}

	public void initFromStack(ItemStack stack) {
		if (stack.getItem() == DoomItems.ROCKET) {
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
		return DoomSounds.ROCKET_HIT;
	}

	@Override
	public void remove(RemovalReason reason) {
		AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
		areaeffectcloudentity.setParticle(ParticleTypes.LAVA);
		areaeffectcloudentity.setRadius(6);
		areaeffectcloudentity.setDuration(1);
		areaeffectcloudentity.absMoveTo(this.getX(), this.getY(), this.getZ());
		this.level.addFreshEntity(areaeffectcloudentity);
		level.playSound((Player) null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE,
				SoundSource.PLAYERS, 1.0F, 1.5F);
		super.remove(reason);
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		if (!this.level.isClientSide()) {
			this.doDamage();
			this.remove(Entity.RemovalReason.DISCARDED);
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		if (!this.level.isClientSide()) {
			this.doDamage();
			this.remove(Entity.RemovalReason.DISCARDED);
		}
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(DoomItems.ROCKET);
	}

	public void doDamage() {
		this.level.getEntities(this, new AABB(this.blockPosition().above()).inflate(4)).forEach(e -> {
			if (e instanceof LivingEntity) {
				e.hurt(DamageSource.playerAttack((Player) this.shooter), projectiledamage);
			}
			this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 0.0F,
					Level.ExplosionInteraction.NONE);
		});

	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

}