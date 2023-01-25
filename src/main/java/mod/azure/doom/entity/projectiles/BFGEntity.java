package mod.azure.doom.entity.projectiles;

import java.util.List;
import java.util.Random;

import org.jetbrains.annotations.Nullable;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.tierambient.GoreNestEntity;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
import mod.azure.doom.entity.tierboss.GladiatorEntity;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import mod.azure.doom.entity.tileentity.TickingLightEntity;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomSounds;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class BFGEntity extends AbstractArrow implements GeoEntity {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private static final EntityDataAccessor<Integer> TARGET_ENTITY = SynchedEntityData.defineId(BFGEntity.class,
			EntityDataSerializers.INT);
	private LivingEntity cachedBeamTarget;
	private LivingEntity shooter;
	private BlockPos lightBlockPos = null;
	private int idleTicks = 0;
	private int beamTicks;
	Random rand = new Random();
	List<? extends String> whitelistEntries = DoomConfig.bfg_damage_mob_whitelist;
	int randomIndex = rand.nextInt(whitelistEntries.size());
	ResourceLocation randomElement1 = new ResourceLocation(whitelistEntries.get(randomIndex));
	EntityType<?> randomElement = BuiltInRegistries.ENTITY_TYPE.get(randomElement1);
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

	public BFGEntity(EntityType<? extends BFGEntity> entityType, Level world) {
		super(entityType, world);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public BFGEntity(Level world, LivingEntity owner) {
		super(ProjectilesEntityRegister.BFG_CELL, owner, world);
		this.shooter = owner;
	}

	protected BFGEntity(EntityType<? extends BFGEntity> type, double x, double y, double z, Level world) {
		this(type, world);
	}

	protected BFGEntity(EntityType<? extends BFGEntity> type, LivingEntity owner, Level world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		if (owner instanceof Player) {
			this.pickup = AbstractArrow.Pickup.DISALLOWED;
		}
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	protected void tickDespawn() {
		++this.ticksInAir;
//		if (this.tickCount >= 40) {
//			this.remove(RemovalReason.KILLED);
//		}
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
		boolean isInsideWaterBlock = level.isWaterAt(blockPosition());
		spawnLightSource(isInsideWaterBlock);
		if (this.tickCount >= 80) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		final AABB aabb = new AABB(this.blockPosition().above()).inflate(24D, 24D, 24D);
		this.getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
			Entity listEntity = randomElement.tryCast(e);
			if (!(e instanceof Player || e instanceof EnderDragon || e instanceof GoreNestEntity
					|| e instanceof IconofsinEntity || e instanceof ArchMakyrEntity || e instanceof GladiatorEntity
					|| e instanceof MotherDemonEntity)
					&& (e instanceof Monster || e instanceof Slime || e instanceof Phantom || e instanceof DemonEntity
							|| e instanceof Shulker || e instanceof Hoglin || (e == listEntity))) {
				if (e.isAlive()) {
					e.hurt(DamageSource.explosion(this, shooter), DoomConfig.bfgball_damage_aoe);
					this.setTargetedEntity(e.getId());
				}
			}
			if (e instanceof EnderDragon) {
				if (e.isAlive()) {
					((EnderDragon) e).head.hurt(DamageSource.playerAttack((Player) this.shooter),
							DoomConfig.bfgball_damage_dragon * 0.3F);
					this.setTargetedEntity(e.getId());
				}
			}
			if (e instanceof IconofsinEntity || e instanceof ArchMakyrEntity || e instanceof GladiatorEntity
					|| e instanceof MotherDemonEntity) {
				if (e.isAlive()) {
					e.hurt(DamageSource.playerAttack((Player) this.shooter), DoomConfig.bfgball_damage_aoe * 0.1F);
				}
			}
		});
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
		if (stack.getItem() == DoomItems.BFG_CELL) {
		}
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(DoomItems.BFG_CELL);
	}

	@Override
	public boolean isNoGravity() {
		if (this.isInWater())
			return false;
		return true;
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		if (!this.level.isClientSide())
			this.remove(Entity.RemovalReason.DISCARDED);
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		if (!this.level.isClientSide) {
			this.doDamage();
			this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F,
					DoomConfig.enable_block_breaking ? Level.ExplosionInteraction.BLOCK
							: Level.ExplosionInteraction.NONE);
			this.remove(RemovalReason.KILLED);
		}
		this.playSound(DoomSounds.BFG_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
	}

	public void doDamage() {
		final AABB aabb = new AABB(this.blockPosition().above()).inflate(24D, 24D, 24D);
		this.getCommandSenderWorld().getEntities(this, aabb).forEach(e -> {
			Entity listEntity = randomElement.tryCast(e);
			if (!(e instanceof Player || e instanceof EnderDragon || e instanceof GoreNestEntity
					|| e instanceof IconofsinEntity || e instanceof ArchMakyrEntity || e instanceof GladiatorEntity
					|| e instanceof MotherDemonEntity)
					&& (e instanceof Monster || e instanceof Slime || e instanceof Phantom || e instanceof DemonEntity
							|| e instanceof Shulker || e instanceof Hoglin || (e == listEntity))) {
				e.hurt(DamageSource.playerAttack((Player) this.shooter), DoomConfig.bfgball_damage);
				this.setTargetedEntity(e.getId());
				if (!this.level.isClientSide) {
					List<LivingEntity> list1 = this.level.getEntitiesOfClass(LivingEntity.class,
							this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D));
					AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(e.level, e.getX(), e.getY(), e.getZ());
					areaeffectcloudentity.setParticle(ParticleTypes.TOTEM_OF_UNDYING);
					areaeffectcloudentity.setRadius(3.0F);
					areaeffectcloudentity.setDuration(10);
					if (!list1.isEmpty()) {
						for (LivingEntity livingentity : list1) {
							double d0 = this.distanceToSqr(livingentity);
							if (d0 < 16.0D) {
								areaeffectcloudentity.setPos(e.getX(), e.getEyeY(), e.getZ());
							}
						}
					}
					e.level.addFreshEntity(areaeffectcloudentity);
				}
			}
			if (e instanceof EnderDragon) {
				if (e.isAlive()) {
					((EnderDragon) e).head.hurt(DamageSource.playerAttack((Player) this.shooter),
							DoomConfig.bfgball_damage_dragon * 0.3F);
				}
			}
			if (e instanceof IconofsinEntity || e instanceof ArchMakyrEntity || e instanceof GladiatorEntity
					|| e instanceof MotherDemonEntity) {
				if (e.isAlive()) {
					e.hurt(DamageSource.playerAttack((Player) this.shooter), DoomConfig.bfgball_damage * 0.1F);
				}
			}
		});
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(TARGET_ENTITY, 0);
	}

	private void setTargetedEntity(int entityId) {
		this.entityData.set(TARGET_ENTITY, entityId);
	}

	public boolean hasTargetedEntity() {
		return this.entityData.get(TARGET_ENTITY) != 0;
	}

	@Nullable
	public LivingEntity getTargetedEntity() {
		if (!this.hasTargetedEntity()) {
			return null;
		} else if (this.level.isClientSide) {
			if (this.cachedBeamTarget != null) {
				return this.cachedBeamTarget;
			} else {
				Entity entity = this.level.getEntity(this.entityData.get(TARGET_ENTITY));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget = (LivingEntity) entity;
					return this.cachedBeamTarget;
				} else {
					return null;
				}
			}
		} else {
			return this.getTarget();
		}
	}

	public float getBeamProgress(float tickDelta) {
		return ((float) this.beamTicks + tickDelta) / (float) this.getWarmupTime();
	}

	public int getWarmupTime() {
		return 80;
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		super.onSyncedDataUpdated(key);
		if (TARGET_ENTITY.equals(key)) {
			this.cachedBeamTarget = null;
		}
	}

	@Nullable
	public LivingEntity getTarget() {
		return this.cachedBeamTarget;
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}
}