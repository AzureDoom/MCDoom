package mod.azure.doom.entity.projectiles;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.tierambient.GoreNestEntity;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
import mod.azure.doom.entity.tierboss.GladiatorEntity;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import mod.azure.doom.entity.tileentity.TickingLightEntity;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomEntities;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
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
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BFGEntity extends AbstractArrow implements IAnimatable {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private static final EntityDataAccessor<Integer> TARGET_ENTITY = SynchedEntityData.defineId(BFGEntity.class,
			EntityDataSerializers.INT);
	private LivingEntity targetedEntity;
	private LivingEntity shooter;
	private BlockPos lightBlockPos = null;
	private int idleTicks = 0;

	public BFGEntity(EntityType<? extends AbstractArrow> type, Level world) {
		super(type, world);
	}

	public BFGEntity(Level world, LivingEntity shooter) {
		super(DoomEntities.BFG_CELL.get(), shooter, world);
		this.shooter = shooter;
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<BFGEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	protected void tickDespawn() {
		++this.ticksInAir;
		if (this.tickCount >= 40) {
			this.remove(RemovalReason.KILLED);
		}
	}

	public DamageSource getDamageSource() {
		return DamageSource.arrow(this, this);
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
		boolean flag = this.isNoPhysics();
		Vec3 vector3d = this.getDeltaMovement();
		if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
			double f = vector3d.horizontalDistance();
			this.yRot = (float) (Mth.atan2(vector3d.x, vector3d.z) * (double) (180F / (float) Math.PI));
			this.xRot = (float) (Mth.atan2(vector3d.y, (double) f) * (double) (180F / (float) Math.PI));
			this.yRotO = this.getYRot();
			this.xRotO = this.getXRot();
		}

		if (this.tickCount >= 100) {
			this.remove(RemovalReason.KILLED);
		}

		if (this.inAir && !flag) {
			this.tickDespawn();

			++this.timeInAir;
		} else {
			this.timeInAir = 0;
			Vec3 vector3d2 = this.position();
			Vec3 vector3d3 = vector3d2.add(vector3d);
			HitResult raytraceresult = this.level.clip(
					new ClipContext(vector3d2, vector3d3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
			if (raytraceresult.getType() != HitResult.Type.MISS) {
				vector3d3 = raytraceresult.getLocation();
			}
			while (this.isAlive()) {
				EntityHitResult entityraytraceresult = this.findHitEntity(vector3d2, vector3d3);
				if (entityraytraceresult != null) {
					raytraceresult = entityraytraceresult;
				}
				if (raytraceresult != null && raytraceresult.getType() == HitResult.Type.ENTITY) {
					Entity entity = ((EntityHitResult) raytraceresult).getEntity();
					Entity entity1 = this.getOwner();
					if (entity instanceof Player && entity1 instanceof Player
							&& !((Player) entity1).canHarmPlayer((Player) entity)) {
						raytraceresult = null;
						entityraytraceresult = null;
					}
				}
				if (raytraceresult != null && raytraceresult.getType() != HitResult.Type.MISS && !flag
						&& !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
					this.onHit(raytraceresult);
					this.hasImpulse = true;
				}
				if (entityraytraceresult == null || this.getPierceLevel() <= 0) {
					break;
				}
				raytraceresult = null;
			}
			vector3d = this.getDeltaMovement();
			double d3 = vector3d.x;
			double d4 = vector3d.y;
			double d0 = vector3d.z;
			double d5 = this.getX() + d3;
			double d1 = this.getY() + d4;
			double d2 = this.getZ() + d0;
			double f1 = vector3d.horizontalDistance();
			if (flag) {
				this.yRot = (float) (Mth.atan2(-d3, -d0) * (double) (180F / (float) Math.PI));
			} else {
				this.yRot = (float) (Mth.atan2(d3, d0) * (double) (180F / (float) Math.PI));
			}
			this.xRot = (float) (Mth.atan2(d4, (double) f1) * (double) (180F / (float) Math.PI));
			this.xRot = lerpRotation(this.xRotO, this.getXRot());
			this.yRot = lerpRotation(this.yRotO, this.getYRot());
			float f2 = 0.99F;
			this.setDeltaMovement(vector3d.scale((double) f2));
			if (!this.isNoGravity() && !flag) {
				Vec3 vector3d4 = this.getDeltaMovement();
				this.setDeltaMovement(vector3d4.x, vector3d4.y - (double) 0.05F, vector3d4.z);
			}
			this.setPos(d5, d1, d2);
			this.checkInsideBlocks();
		}

		float f2 = 24.0F;
		int k1 = Mth.floor(this.getX() - (double) f2 - 1.0D);
		int l1 = Mth.floor(this.getX() + (double) f2 + 1.0D);
		int i2 = Mth.floor(this.getY() - (double) f2 - 1.0D);
		int i1 = Mth.floor(this.getY() + (double) f2 + 1.0D);
		int j2 = Mth.floor(this.getZ() - (double) f2 - 1.0D);
		int j1 = Mth.floor(this.getZ() + (double) f2 + 1.0D);
		List<Entity> list = this.level.getEntities(this,
				new AABB((double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));
		Vec3 vector3d1 = new Vec3(this.getX(), this.getY(), this.getZ());
		Random rand = new Random();
		List<? extends String> whitelistEntries = DoomConfig.SERVER.bfg_damage_mob_whitelist.get();
		int randomIndex = rand.nextInt(whitelistEntries.size());
		ResourceLocation randomElement1 = new ResourceLocation(whitelistEntries.get(randomIndex));
		EntityType<?> randomElement = ForgeRegistries.ENTITIES.getValue(randomElement1);
		for (int k2 = 0; k2 < list.size(); ++k2) {
			Entity entity = list.get(k2);
			Entity listEntity = randomElement.tryCast(entity);
			if (!(entity instanceof Player || entity instanceof EnderDragon || entity instanceof GoreNestEntity
					|| entity instanceof IconofsinEntity || entity instanceof ArchMakyrEntity
					|| entity instanceof GladiatorEntity || entity instanceof MotherDemonEntity)
					&& (entity instanceof Monster || entity instanceof Slime || entity instanceof Phantom
							|| entity instanceof DemonEntity || entity instanceof Shulker || entity instanceof Hoglin
							|| (entity == listEntity))) {
				double d12 = (double) (Mth.sqrt((float) entity.distanceToSqr(vector3d1)) / f2);
				if (d12 <= 1.0D) {
					if (entity.isAlive()) {
						entity.hurt(DamageSource.explosion(shooter),
								DoomConfig.SERVER.bfgball_damage_aoe.get().floatValue());
						this.setTargetedEntity(entity.getId());
					}
				}
			}
			if (entity instanceof EnderDragon) {
				if (entity.isAlive()) {
					((EnderDragon) entity).head.hurt(DamageSource.playerAttack((Player) this.shooter),
							DoomConfig.SERVER.bfgball_damage_dragon.get().floatValue() * 0.3F);
					this.setTargetedEntity(entity.getId());
				}
			}
			if (entity instanceof IconofsinEntity || entity instanceof ArchMakyrEntity
					|| entity instanceof GladiatorEntity || entity instanceof MotherDemonEntity) {
				if (entity.isAlive()) {
					entity.hurt(DamageSource.playerAttack((Player) this.shooter),
							DoomConfig.SERVER.bfgball_damage_aoe.get().floatValue() * 0.1F);
				}
			}
		}
	}

	private void spawnLightSource(boolean isInWaterBlock) {
		if (lightBlockPos == null) {
			lightBlockPos = findFreeSpace(level, blockPosition(), 2);
			if (lightBlockPos == null)
				return;
			level.setBlockAndUpdate(lightBlockPos, DoomBlocks.TICKING_LIGHT_BLOCK.get().defaultBlockState());
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
					if (state.isAir() || state.getBlock().equals(DoomBlocks.TICKING_LIGHT_BLOCK.get()))
						return offsetPos;
				}

		return null;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public boolean isNoGravity() {
		if (this.isInWater()) {
			return false;
		} else {
			return true;
		}
	}

	public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

	@Override
	protected void onHitBlock(BlockHitResult p_230299_1_) {
		super.onHitBlock(p_230299_1_);
		this.setSoundEvent(DoomSounds.BFG_HIT.get());
	}

	@Override
	public void setSoundEvent(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return DoomSounds.BFG_HIT.get();
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		if (!this.level.isClientSide) {
			this.doDamage();
			this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F,
					DoomConfig.SERVER.enable_block_breaking.get() ? Explosion.BlockInteraction.BREAK
							: Explosion.BlockInteraction.NONE);
			this.remove(RemovalReason.KILLED);
		}
		this.playSound(DoomSounds.BFG_HIT.get(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
	}

	protected void onHit(HitResult result) {
		super.onHit(result);
		Entity entity = this.getOwner();
		if (result.getType() != HitResult.Type.ENTITY || !((EntityHitResult) result).getEntity().is(entity)) {
			if (!this.level.isClientSide) {
				this.doDamage();
				this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F,
						DoomConfig.SERVER.enable_block_breaking.get() ? Explosion.BlockInteraction.BREAK
								: Explosion.BlockInteraction.NONE);
				this.remove(RemovalReason.KILLED);
			}
			this.playSound(DoomSounds.BFG_HIT.get(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
		}
	}

	public void doDamage() {
		float f2 = 24.0F;
		int k1 = Mth.floor(this.getX() - (double) f2 - 1.0D);
		int l1 = Mth.floor(this.getX() + (double) f2 + 1.0D);
		int i2 = Mth.floor(this.getY() - (double) f2 - 1.0D);
		int i1 = Mth.floor(this.getY() + (double) f2 + 1.0D);
		int j2 = Mth.floor(this.getZ() - (double) f2 - 1.0D);
		int j1 = Mth.floor(this.getZ() + (double) f2 + 1.0D);
		List<Entity> list = this.level.getEntities(this,
				new AABB((double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));
		Vec3 vector3d = new Vec3(this.getX(), this.getY(), this.getZ());
		Random rand = new Random();
		List<? extends String> whitelistEntries = DoomConfig.SERVER.bfg_damage_mob_whitelist.get();
		int randomIndex = rand.nextInt(whitelistEntries.size());
		ResourceLocation randomElement1 = new ResourceLocation(whitelistEntries.get(randomIndex));
		EntityType<?> randomElement = ForgeRegistries.ENTITIES.getValue(randomElement1);
		for (int k2 = 0; k2 < list.size(); ++k2) {
			Entity entity = list.get(k2);
			Entity listEntity = randomElement.tryCast(entity);
			if (!(entity instanceof Player || entity instanceof EnderDragon || entity instanceof GoreNestEntity
					|| entity instanceof IconofsinEntity || entity instanceof ArchMakyrEntity
					|| entity instanceof GladiatorEntity || entity instanceof MotherDemonEntity)
					&& (entity instanceof Monster || entity instanceof Slime || entity instanceof Phantom
							|| entity instanceof DemonEntity || entity instanceof Shulker || entity instanceof Hoglin
							|| (entity == listEntity))) {
				double d12 = (double) (Mth.sqrt((float) entity.distanceToSqr(vector3d)) / f2);
				if (d12 <= 1.0D) {
					entity.hurt(DamageSource.playerAttack((Player) this.shooter),
							DoomConfig.SERVER.bfgball_damage.get().floatValue());
					this.setTargetedEntity(entity.getId());
					if (!this.level.isClientSide) {
						List<LivingEntity> list1 = this.level.getEntitiesOfClass(LivingEntity.class,
								this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D));
						AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(entity.level, entity.getX(),
								entity.getY(), entity.getZ());
						areaeffectcloudentity.setParticle(ParticleTypes.TOTEM_OF_UNDYING);
						areaeffectcloudentity.setRadius(3.0F);
						areaeffectcloudentity.setDuration(10);
						if (!list1.isEmpty()) {
							for (LivingEntity livingentity : list1) {
								double d0 = this.distanceToSqr(livingentity);
								if (d0 < 16.0D) {
									areaeffectcloudentity.setPos(entity.getX(), entity.getEyeY(), entity.getZ());
								}
							}
						}
						entity.level.addFreshEntity(areaeffectcloudentity);
					}
				}
			}
			if (entity instanceof EnderDragon) {
				if (entity.isAlive()) {
					((EnderDragon) entity).head.hurt(DamageSource.playerAttack((Player) this.shooter),
							DoomConfig.SERVER.bfgball_damage_dragon.get().floatValue() * 0.3F);
				}
			}
			if (entity instanceof IconofsinEntity || entity instanceof ArchMakyrEntity
					|| entity instanceof GladiatorEntity || entity instanceof MotherDemonEntity) {
				if (entity.isAlive()) {
					entity.hurt(DamageSource.playerAttack((Player) this.shooter),
							DoomConfig.SERVER.bfgball_damage.get().floatValue() * 0.1F);
				}
			}
		}

	}

	@Override
	protected ItemStack getPickupItem() {
		return new ItemStack(DoomItems.BFG_CELL.get());
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
			if (this.targetedEntity != null) {
				return this.targetedEntity;
			} else {
				Entity entity = this.level.getEntity(this.entityData.get(TARGET_ENTITY));
				if (!(entity instanceof ServerPlayer) && entity instanceof LivingEntity) {
					this.targetedEntity = (LivingEntity) entity;
					return this.targetedEntity;
				} else {
					return null;
				}
			}
		} else {
			return this.getAttackTarget();
		}
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		super.onSyncedDataUpdated(key);
		if (TARGET_ENTITY.equals(key)) {
			this.targetedEntity = null;
		}

	}

	@Nullable
	public LivingEntity getAttackTarget() {
		return this.targetedEntity;
	}

}