package mod.azure.doom.entity.projectiles;

import java.util.List;

import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.ModEntityTypes;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class RocketEntity extends AbstractArrowEntity implements IAnimatable {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private LivingEntity shooter;

	public RocketEntity(EntityType<? extends AbstractArrowEntity> type, World world) {
		super(type, world);
	}

	public RocketEntity(World world, LivingEntity owner) {
		super(ModEntityTypes.ROCKET.get(), owner, world);
		this.shooter = owner;
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<RocketEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	protected void tickDespawn() {
		++this.ticksInAir;
		if (this.tickCount >= 40) {
			this.remove();
		}
	}

	@Override
	protected void doPostHurtEffects(LivingEntity living) {
		super.doPostHurtEffects(living);
		if (!(living instanceof PlayerEntity) && !(living instanceof IconofsinEntity)) {
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
	public void addAdditionalSaveData(CompoundNBT compound) {
		super.addAdditionalSaveData(compound);
		compound.putShort("life", (short) this.ticksInAir);
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT compound) {
		super.readAdditionalSaveData(compound);
		this.ticksInAir = compound.getShort("life");
	}

	@Override
	public void tick() {
		super.tick();
		boolean flag = this.isNoPhysics();
		Vector3d vector3d = this.getDeltaMovement();
		if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
			float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
			this.yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (double) (180F / (float) Math.PI));
			this.xRot = (float) (MathHelper.atan2(vector3d.y, (double) f) * (double) (180F / (float) Math.PI));
			this.yRotO = this.yRot;
			this.xRotO = this.xRot;
		}

		if (this.tickCount >= 100) {
			this.remove();
		}

		if (this.inAir && !flag) {
			this.tickDespawn();

			++this.timeInAir;
		} else {
			this.timeInAir = 0;
			Vector3d vector3d2 = this.position();
			Vector3d vector3d3 = vector3d2.add(vector3d);
			RayTraceResult raytraceresult = this.level.clip(new RayTraceContext(vector3d2, vector3d3,
					RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
			if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
				vector3d3 = raytraceresult.getLocation();
			}
			while (this.isAlive()) {
				EntityRayTraceResult entityraytraceresult = this.findHitEntity(vector3d2, vector3d3);
				if (entityraytraceresult != null) {
					raytraceresult = entityraytraceresult;
				}
				if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
					Entity entity = ((EntityRayTraceResult) raytraceresult).getEntity();
					Entity entity1 = this.getOwner();
					if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity
							&& !((PlayerEntity) entity1).canHarmPlayer((PlayerEntity) entity)) {
						raytraceresult = null;
						entityraytraceresult = null;
					}
				}
				if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag
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
			float f1 = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
			if (flag) {
				this.yRot = (float) (MathHelper.atan2(-d3, -d0) * (double) (180F / (float) Math.PI));
			} else {
				this.yRot = (float) (MathHelper.atan2(d3, d0) * (double) (180F / (float) Math.PI));
			}
			this.xRot = (float) (MathHelper.atan2(d4, (double) f1) * (double) (180F / (float) Math.PI));
			this.xRot = lerpRotation(this.xRotO, this.xRot);
			this.yRot = lerpRotation(this.yRotO, this.yRot);
			float f2 = 0.99F;
			this.setDeltaMovement(vector3d.scale((double) f2));
			if (!this.isNoGravity() && !flag) {
				Vector3d vector3d4 = this.getDeltaMovement();
				this.setDeltaMovement(vector3d4.x, vector3d4.y - (double) 0.05F, vector3d4.z);
			}
			this.setPos(d5, d1, d2);
			this.checkInsideBlocks();
		}
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(DoomItems.ROCKET.get());
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
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
	protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
		super.onHitBlock(p_230299_1_);
		this.setSoundEvent(ModSoundEvents.ROCKET_HIT.get());
	}

	@Override
	public void setSoundEvent(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return ModSoundEvents.ROCKET_HIT.get();
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
		Entity entity = this.getOwner();
		if (p_213868_1_.getType() != RayTraceResult.Type.ENTITY
				|| !((EntityRayTraceResult) p_213868_1_).getEntity().is(entity)) {
			if (!this.level.isClientSide) {
				this.doDamage();
				this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F,
						DoomConfig.SERVER.enable_block_breaking.get() ? Explosion.Mode.BREAK : Explosion.Mode.NONE);
				this.remove();
			}
		}
	}

	@Override
	protected void onHit(RayTraceResult result) {
		super.onHit(result);
		Entity entity = this.getOwner();
		if (result.getType() != RayTraceResult.Type.ENTITY || !((EntityRayTraceResult) result).getEntity().is(entity)) {
			if (!this.level.isClientSide) {
				this.doDamage();
				this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F,
						DoomConfig.SERVER.enable_block_breaking.get() ? Explosion.Mode.BREAK : Explosion.Mode.NONE);
				this.remove();
			}
		}
	}

	public void doDamage() {
		float f2 = 4.0F;
		int k1 = MathHelper.floor(this.getX() - (double) f2 - 1.0D);
		int l1 = MathHelper.floor(this.getX() + (double) f2 + 1.0D);
		int i2 = MathHelper.floor(this.getY() - (double) f2 - 1.0D);
		int i1 = MathHelper.floor(this.getY() + (double) f2 + 1.0D);
		int j2 = MathHelper.floor(this.getZ() - (double) f2 - 1.0D);
		int j1 = MathHelper.floor(this.getZ() + (double) f2 + 1.0D);
		List<Entity> list = this.level.getEntities(this,
				new AxisAlignedBB((double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));
		Vector3d vector3d = new Vector3d(this.getX(), this.getY(), this.getZ());
		for (int k2 = 0; k2 < list.size(); ++k2) {
			Entity entity = list.get(k2);
			double d12 = (double) (MathHelper.sqrt(entity.distanceToSqr(vector3d)) / f2);
			if (d12 <= 1.0D) {
				if (entity instanceof LivingEntity) {
					entity.hurt(DamageSource.playerAttack((PlayerEntity) this.shooter),
							DoomConfig.SERVER.rocket_damage.get().floatValue());
				}
			}
		}
	}
}