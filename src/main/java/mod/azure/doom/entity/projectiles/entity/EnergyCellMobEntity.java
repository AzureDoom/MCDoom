package mod.azure.doom.entity.projectiles.entity;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.network.DoomEntityPacket;
import mod.azure.doom.util.registry.DoomSounds;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class EnergyCellMobEntity extends ExplosiveProjectileEntity implements IAnimatable {

	public int explosionPower = 1;
	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private float directHitDamage = 3F;
	private LivingEntity shooter;
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	public EnergyCellMobEntity(EntityType<? extends EnergyCellMobEntity> p_i50160_1_, World p_i50160_2_) {
		super(p_i50160_1_, p_i50160_2_);
	}

	public void setDirectHitDamage(float directHitDamage) {
		this.directHitDamage = directHitDamage;
	}

	public EnergyCellMobEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ,
			float directHitDamage) {
		super(ProjectilesEntityRegister.ENERGY_CELL_MOB, shooter, accelX, accelY, accelZ, worldIn);
		this.shooter = shooter;
		this.horizontalSpeed = 3.0F;
		this.directHitDamage = directHitDamage;
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", EDefaultLoopTypes.LOOP));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(
				new AnimationController<EnergyCellMobEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public EnergyCellMobEntity(World worldIn, double x, double y, double z, double accelX, double accelY,
			double accelZ) {
		super(ProjectilesEntityRegister.ENERGY_CELL_MOB, x, y, z, accelX, accelY, accelZ, worldIn);
	}

	@Override
	public void setVelocity(double x, double y, double z, float speed, float divergence) {
		super.setVelocity(x, y, z, speed, divergence);
		this.ticksInAir = 0;
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putShort("life", (short) this.ticksInAir);
	}

	@Override
	protected boolean isBurning() {
		return false;
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return DoomEntityPacket.createPacket(this);
	}

	@Override
	public boolean hasNoGravity() {
		if (this.isSubmergedInWater()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		if (!this.world.isClient) {
			Entity entity = entityHitResult.getEntity();
			Entity entity2 = this.getOwner();
			entity.setOnFireFor(5);
			if (!(entity2 instanceof DemonEntity))
				entity.damage(DamageSource.mob((LivingEntity) entity2), directHitDamage);
			if (entity2 instanceof LivingEntity) {
				if (!(entity2 instanceof DemonEntity))
					this.applyDamageEffects((LivingEntity) entity2, entity);
			}
		}
		this.playSound(DoomSounds.PLASMA_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
	}

	@Override
	protected void onCollision(HitResult result) {
		super.onCollision(result);
		if (!this.world.isClient) {
			this.explode();
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		this.playSound(DoomSounds.PLASMA_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
	}

	protected void explode() {
		this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 1.0F, false,
				Explosion.DestructionType.NONE);
	}

	public LivingEntity getShooter() {
		return shooter;
	}

	public void setShooter(LivingEntity shooter) {
		this.shooter = shooter;
	}
}