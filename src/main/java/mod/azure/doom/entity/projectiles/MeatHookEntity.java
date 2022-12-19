package mod.azure.doom.entity.projectiles;

import mod.azure.doom.util.PlayerProperties;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MeatHookEntity extends PersistentProjectileEntity implements GeoEntity {
	private static final TrackedData<Integer> HOOKED_ENTITY_ID = DataTracker.registerData(MeatHookEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	public static final TrackedData<Float> FORCED_YAW = DataTracker.registerData(MeatHookEntity.class,
			TrackedDataHandlerRegistry.FLOAT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private double maxRange = 0D;
	private double maxSpeed = 0D;
	private boolean isPulling = false;
	private Entity hookedEntity;
	private ItemStack stack;

	public MeatHookEntity(EntityType<? extends PersistentProjectileEntity> type, PlayerEntity owner, World world) {
		super(type, owner, world);
		this.setNoGravity(true);
		this.setDamage(0);
	}

	public MeatHookEntity(World world, LivingEntity owner) {
		super(ProjectilesEntityRegister.MEATHOOOK_ENTITY, owner, world);
		this.setNoGravity(true);
		this.setDamage(0);
	}

	public MeatHookEntity(World world, double x, double y, double z) {
		super(ProjectilesEntityRegister.MEATHOOOK_ENTITY, x, y, z, world);
		this.setNoGravity(true);
		this.setDamage(0);
	}

	public MeatHookEntity(World world) {
		super(ProjectilesEntityRegister.MEATHOOOK_ENTITY, world);
		this.setNoGravity(true);
		this.setDamage(0);
	}

	public MeatHookEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
		this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
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
	protected void initDataTracker() {
		super.initDataTracker();
		this.getDataTracker().startTracking(HOOKED_ENTITY_ID, 0);
		this.getDataTracker().startTracking(FORCED_YAW, 0f);
	}

	@Override
	public void tick() {
		super.tick();
		if (getOwner()instanceof PlayerEntity owner) {
			setYaw(dataTracker.get(FORCED_YAW));

			if (isPulling && age % 2 == 0)
				world.playSound(null, getOwner().getBlockPos(), SoundEvents.BLOCK_CHAIN_PLACE, SoundCategory.PLAYERS,
						1F, 1F);

			if (!world.isClient) {
				if (owner.isDead() || !((PlayerProperties) owner).hasMeatHook() || owner.distanceTo(this) > maxRange)
					kill();

				if (this.hookedEntity != null) {
					if (this.hookedEntity.isRemoved()) {
						this.hookedEntity = null;
						onRemoved();
					} else {
						this.updatePosition(this.hookedEntity.getX(), this.hookedEntity.getBodyY(0.8D),
								this.hookedEntity.getZ());
					}
				}

				if (owner.getOffHandStack() == stack) {
					if (isPulling) {
						Entity target = owner;
						Entity origin = this;

						if (owner.isSneaking() && hookedEntity != null) {
							target = hookedEntity;
							origin = owner;
						}
						double pullSpeed = 0.75D;
						Vec3d distance = origin.getPos().subtract(target.getPos().add(0, target.getHeight() / 2, 0));
						Vec3d motion = distance.normalize().multiply((pullSpeed * distance.length()) / 6D);

						if (Math.abs(distance.y) < 0.1D) {
							motion = new Vec3d(motion.x, 0, motion.z);
							kill();
						}
						if (new Vec3d(distance.x, 0, distance.z)
								.length() < new Vec3d(target.getWidth() / 2, 0, target.getWidth() / 2).length() / 1.4) {
							motion = new Vec3d(0, motion.y, 0);
							kill();
						}
						target.fallDistance = 0;
						target.setVelocity(motion);
						target.velocityModified = true;
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
		if (!world.isClient && getOwner()instanceof PlayerEntity owner) {
			((PlayerProperties) owner).setHasMeatHook(false);
			owner.setNoGravity(false);
		}

		super.kill();
	}

	@Override
	public boolean shouldRender(double distance) {
		return true;
	}

	@Override
	protected float getDragInWater() {
		return super.getDragInWater();
	}

	@Override
	public boolean canUsePortals() {
		return false;
	}

	@Override
	protected ItemStack asItemStack() {
		return ItemStack.EMPTY;
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		isPulling = true;

		if (!world.isClient && getOwner()instanceof PlayerEntity owner && hookedEntity == null) {
			owner.setNoGravity(true);
		}
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		if (!world.isClient && getOwner()instanceof PlayerEntity owner && entityHitResult.getEntity() != owner) {
			if ((entityHitResult.getEntity() instanceof LivingEntity
					|| entityHitResult.getEntity() instanceof EnderDragonPart) && hookedEntity == null) {
				hookedEntity = entityHitResult.getEntity();
				dataTracker.set(HOOKED_ENTITY_ID, hookedEntity.getId() + 1);
				isPulling = true;
			}
		}
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		dataTracker.set(FORCED_YAW, tag.getFloat("ForcedYaw"));

		maxRange = tag.getDouble("maxRange");
		maxSpeed = tag.getDouble("maxSpeed");
		isPulling = tag.getBoolean("isPulling");

		if (world.getEntityById(tag.getInt("owner"))instanceof PlayerEntity owner)
			setOwner(owner);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putFloat("ForcedYaw", dataTracker.get(FORCED_YAW));
		tag.putDouble("maxRange", maxRange);
		tag.putDouble("maxSpeed", maxSpeed);
		tag.putBoolean("isPulling", isPulling);

		if (getOwner()instanceof PlayerEntity owner)
			tag.putInt("owner", owner.getId());
	}

	public void setProperties(ItemStack stack, double maxRange, double maxVelocity, float pitch, float yaw, float roll,
			float modifierZ) {
		float f = 0.017453292F;
		float x = -MathHelper.sin(yaw * f) * MathHelper.cos(pitch * f);
		float y = -MathHelper.sin((pitch + roll) * f);
		float z = MathHelper.cos(yaw * f) * MathHelper.cos(pitch * f);
		this.setVelocity(x, y, z, modifierZ, 0);

		this.stack = stack;
		this.maxRange = maxRange;
		this.maxSpeed = maxVelocity;
	}
}