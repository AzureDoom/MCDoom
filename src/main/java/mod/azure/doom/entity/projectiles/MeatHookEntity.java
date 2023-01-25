package mod.azure.doom.entity.projectiles;

import mod.azure.doom.util.PlayerProperties;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;

public class MeatHookEntity extends AbstractArrow implements GeoEntity {
	private static final EntityDataAccessor<Integer> HOOKED_ENTITY_ID = SynchedEntityData.defineId(MeatHookEntity.class,
			EntityDataSerializers.INT);
	public static final EntityDataAccessor<Float> FORCED_YAW = SynchedEntityData.defineId(MeatHookEntity.class,
			EntityDataSerializers.FLOAT);
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	private double maxRange = 0D;
	private double maxSpeed = 0D;
	private boolean isPulling = false;
	private Entity hookedEntity;
	private ItemStack stack;

	public MeatHookEntity(EntityType<? extends AbstractArrow> type, Player owner, Level world) {
		super(type, owner, world);
		this.setNoGravity(true);
		this.setBaseDamage(0);
	}

	public MeatHookEntity(Level world, LivingEntity owner) {
		super(ProjectilesEntityRegister.MEATHOOOK_ENTITY.get(), owner, world);
		this.setNoGravity(true);
		this.setBaseDamage(0);
	}

	public MeatHookEntity(Level world, double x, double y, double z) {
		super(ProjectilesEntityRegister.MEATHOOOK_ENTITY.get(), x, y, z, world);
		this.setNoGravity(true);
		this.setBaseDamage(0);
	}

	public MeatHookEntity(Level world) {
		super(ProjectilesEntityRegister.MEATHOOOK_ENTITY.get(), world);
		this.setNoGravity(true);
		this.setBaseDamage(0);
	}

	public MeatHookEntity(EntityType<? extends AbstractArrow> entityType, Level world) {
		super(entityType, world);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
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
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(HOOKED_ENTITY_ID, 0);
		this.entityData.define(FORCED_YAW, 0f);
	}

	@Override
	public void tick() {
		super.tick();
		if (getOwner()instanceof Player owner) {
			setYRot(entityData.get(FORCED_YAW));

			if (isPulling && tickCount % 2 == 0)
				level.playSound(null, getOwner().blockPosition(), SoundEvents.CHAIN_PLACE, SoundSource.PLAYERS, 1F, 1F);

			if (!level.isClientSide()) {
				if (owner.isDeadOrDying() || !((PlayerProperties) owner).hasMeatHook()
						|| owner.distanceTo(this) > maxRange)
					kill();

				if (this.hookedEntity != null) {
					if (this.hookedEntity.isRemoved()) {
						this.hookedEntity = null;
						onClientRemoval();
					} else {
						this.absMoveTo(this.hookedEntity.getX(), this.hookedEntity.getY(0.8D),
								this.hookedEntity.getZ());
					}
				}

				if (owner.getOffhandItem() == stack) {
					if (isPulling) {
						Entity target = owner;
						Entity origin = this;

						if (owner.isScoping() && hookedEntity != null) {
							target = hookedEntity;
							origin = owner;
						}
						double pullSpeed = 0.75D;
						Vec3 distance = origin.position()
								.subtract(target.position().add(0, target.getBbHeight() / 2, 0));
						Vec3 motion = distance.normalize().scale((pullSpeed * distance.length()) / 6D);

						if (Math.abs(distance.y) < 0.01D) {
							motion = new Vec3(motion.x, 0, motion.z);
							kill();
						}
						if (new Vec3(distance.x, 0, distance.z)
								.length() < new Vec3(target.getBbWidth() / 2, 0, target.getBbWidth() / 2).length()
										/ 1.4) {
							motion = new Vec3(motion.x, motion.y, motion.z);
							kill();
						}
						target.fallDistance = 0;
						target.setDeltaMovement(motion);
						target.hurtMarked = true;
					}
				} else {
					kill();
				}
			}
		} else {
			kill();
		}
	}

	@Override
	public void kill() {
		if (!level.isClientSide() && getOwner()instanceof Player owner) {
			((PlayerProperties) owner).setHasMeatHook(false);
			owner.setNoGravity(false);
		}

		super.kill();
	}

	@Override
	public boolean shouldRenderAtSqrDistance(double distance) {
		return true;
	}

	@Override
	protected float getWaterInertia() {
		return super.getWaterInertia();
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		isPulling = true;

		if (!level.isClientSide() && getOwner()instanceof Player owner && hookedEntity == null) {
			owner.setNoGravity(true);
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		if (!level.isClientSide() && getOwner()instanceof Player owner && entityHitResult.getEntity() != owner) {
			if ((entityHitResult.getEntity() instanceof LivingEntity
					|| entityHitResult.getEntity() instanceof EnderDragonPart) && hookedEntity == null) {
				hookedEntity = entityHitResult.getEntity();
				entityData.set(HOOKED_ENTITY_ID, hookedEntity.getId() + 1);
				isPulling = true;
			}
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		entityData.set(FORCED_YAW, tag.getFloat("ForcedYaw"));

		maxRange = tag.getDouble("maxRange");
		maxSpeed = tag.getDouble("maxSpeed");
		isPulling = tag.getBoolean("isPulling");
		stack = ItemStack.of(tag.getCompound("hookshotItem"));

		if (level.getEntity(tag.getInt("owner"))instanceof Player owner)
			setOwner(owner);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putFloat("ForcedYaw", entityData.get(FORCED_YAW));
		tag.putDouble("maxRange", maxRange);
		tag.putDouble("maxSpeed", maxSpeed);
		tag.putBoolean("isPulling", isPulling);
		tag.put("hookshotItem", stack.save(new CompoundTag()));

		if (getOwner()instanceof Player owner)
			tag.putInt("owner", owner.getId());
	}

	public void setProperties(ItemStack stack, double maxRange, double maxVelocity, float pitch, float yaw, float roll,
			float modifierZ) {
		float f = 0.017453292F;
		float x = -Mth.sin(yaw * f) * Mth.cos(pitch * f);
		float y = -Mth.sin((pitch + roll) * f);
		float z = Mth.cos(yaw * f) * Mth.cos(pitch * f);
		this.shoot(x, y, z, modifierZ, 0);

		this.stack = stack;
		this.maxRange = maxRange;
		this.maxSpeed = maxVelocity;
	}
}