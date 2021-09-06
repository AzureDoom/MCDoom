package mod.azure.doom.entity.projectiles;

import mod.azure.doom.item.weapons.SuperShotgun;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class HookEntity extends AbstractArrowEntity {
	private static final DataParameter<Integer> DATA_HOOKED_ENTITY = EntityDataManager.defineId(HookEntity.class,
			DataSerializers.INT);
	public double maxRange = 0D;
	public double maxSpeed = 0D;
	private boolean isPulling = false;
	private PlayerEntity owner;
	private Entity hookedEntity;
	private ItemStack stack;

	public HookEntity(EntityType<? extends AbstractArrowEntity> type, World world) {
		super(type, world);
		this.setNoGravity(true);
	}

	@OnlyIn(Dist.CLIENT)
	public HookEntity(World world, PlayerEntity player, double x, double y, double z) {
		super(ModEntityTypes.HOOK.get(), world);
		this.noCulling = true;
		this.setOwner(player);
		this.setPos(x, y, z);
		this.xo = this.getX();
		this.yo = this.getY();
		this.zo = this.getZ();
	}

	public HookEntity(World world) {
		super(ModEntityTypes.HOOK.get(), world);
		this.setNoGravity(true);
	}

	public HookEntity(EntityType<? extends AbstractArrowEntity> entityType, LivingEntity player, World world) {
		super(entityType, player, world);
		this.setNoGravity(true);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(DATA_HOOKED_ENTITY, 0);
	}

	@Override
	public void kill() {
		if (!level.isClientSide && owner != null) {
			owner.setNoGravity(false);
		}

		super.kill();
	}

	@OnlyIn(Dist.CLIENT)
	public boolean shouldRenderAtSqrDistance(double p_70112_1_) {
		return p_70112_1_ < 4096.0D;
	}

	public boolean canChangeDimensions() {
		return false;
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void onHitBlock(BlockRayTraceResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		//isPulling = true;
		if (!level.isClientSide && owner != null && hookedEntity == null) {
			owner.setNoGravity(true);
		}
	}

	@Override
	protected void onHitEntity(EntityRayTraceResult entityHitResult) {
		if (!level.isClientSide && owner != null && entityHitResult.getEntity() != owner) {
			if ((entityHitResult.getEntity() instanceof LivingEntity
					|| entityHitResult.getEntity() instanceof EnderDragonPartEntity) && hookedEntity == null) {
				hookedEntity = entityHitResult.getEntity();
				entityData.set(DATA_HOOKED_ENTITY, hookedEntity.getId() + 1);
				isPulling = true;
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (getOwner() instanceof PlayerEntity) {
			owner = (PlayerEntity) getOwner();

			if (!level.isClientSide) {
				if (this.hookedEntity != null) {
					if (!this.hookedEntity.isAlive()) {
						this.hookedEntity = null;
						remove();
					} else {

						this.absMoveTo(this.hookedEntity.getX(), -this.hookedEntity.getY(0.8D),
								this.hookedEntity.getZ());
					}
				}

				if (owner != null) {
					if (owner.isDeadOrDying() || !(owner.getMainHandItem().getItem() instanceof SuperShotgun
							|| owner.getOffhandItem().getItem() instanceof SuperShotgun)) {
						kill();
					}
				} else {
					kill();
				}

				if (owner.getMainHandItem() == stack || owner.getMainHandItem() == stack) {
					if (isPulling) {
						Entity target = owner;
						Entity origin = this;

						if (owner.isShiftKeyDown() && hookedEntity != null) {
							target = hookedEntity;
							origin = owner;
						}

						Vector3d distance = origin.position()
								.subtract(target.position().add(0, target.getBbHeight() / 1, 0));
						Vector3d motion = distance.normalize().multiply(distance.x, distance.y,distance.z);

						if (Math.abs(distance.y) < 0.1D)
							motion = new Vector3d(motion.x, 0, motion.z);
						if (new Vector3d(distance.x, 0, distance.z)
								.length() < new Vector3d(target.getBbWidth() / 1, 0, target.getBbWidth() / 1).length()
										/ 1.4)
							motion = new Vector3d(motion.x, 0, 0);

						target.setDeltaMovement(motion);
						target.hurtMarked = true;

					}
				} else {
					kill();
				}
			}
		}
	}

	public void shootFromRotation(ItemStack stack, double maxRange, double maxVelocity, float pitch, float yaw,
			float roll, float modifierZ) {
		float f = 0.017453292F;
		float x = -MathHelper.sin(yaw * f) * MathHelper.cos(pitch * f);
		float y = -MathHelper.sin((pitch + roll) * f);
		float z = MathHelper.cos(yaw * f) * MathHelper.cos(pitch * f);
		this.setDeltaMovement(x, y, z);

		this.stack = stack;
		this.maxRange = maxRange;
		this.maxSpeed = maxVelocity;
	}

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}
}
