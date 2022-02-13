package mod.azure.doom.entity.projectiles.entity;

import mod.azure.doom.DoomMod;
import mod.azure.doom.network.EntityPacket;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GladiatorMaceEntity extends ExplosiveProjectileEntity implements IAnimatable {

	public int explosionPower = 1;
	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private LivingEntity shooter;

	public GladiatorMaceEntity(EntityType<? extends GladiatorMaceEntity> p_i50160_1_, World p_i50160_2_) {
		super(p_i50160_1_, p_i50160_2_);
	}

	public GladiatorMaceEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
		super(ProjectilesEntityRegister.GLADIATOR_MACE, shooter, accelX, accelY, accelZ, worldIn);
		this.shooter = shooter;
	}

	public GladiatorMaceEntity(World worldIn, double x, double y, double z, double accelX, double accelY,
			double accelZ) {
		super(ProjectilesEntityRegister.GLADIATOR_MACE, x, y, z, accelX, accelY, accelZ, worldIn);
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("throwing", true));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(
				new AnimationController<GladiatorMaceEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public boolean doesRenderOnFire() {
		return false;
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
		return EntityPacket.createPacket(this);
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
			entity.damage(DamageSource.mob((LivingEntity) entity2), DoomMod.config.stats.gladiator_ranged_damage);
			if (entity2 instanceof LivingEntity) {
				this.applyDamageEffects((LivingEntity) entity2, entity);
			}
		}
		this.playSound(SoundEvents.BLOCK_NETHERITE_BLOCK_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
	}

	@Override
	protected void onCollision(HitResult result) {
		super.onCollision(result);
		if (!this.world.isClient) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		this.playSound(SoundEvents.BLOCK_NETHERITE_BLOCK_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
	}

	public LivingEntity getShooter() {
		return shooter;
	}

	public void setShooter(LivingEntity shooter) {
		this.shooter = shooter;
	}

}