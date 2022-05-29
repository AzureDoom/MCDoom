package mod.azure.doom.entity.projectiles.entity;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.network.EntityPacket;
import mod.azure.doom.util.registry.ModSoundEvents;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BloodBoltEntity extends ExplosiveProjectileEntity implements IAnimatable {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private float directHitDamage = 2;

	public BloodBoltEntity(EntityType<BloodBoltEntity> p_i50160_1_, World p_i50160_2_) {
		super(p_i50160_1_, p_i50160_2_);
	}

	@Override
	public boolean doesRenderOnFire() {
		return false;
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<BloodBoltEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public BloodBoltEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ,
			float directHitDamage) {
		super(ProjectilesEntityRegister.BLOODBOLT_MOB, shooter, accelX, accelY, accelZ, worldIn);
		this.directHitDamage = directHitDamage;
	}

	public BloodBoltEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(ProjectilesEntityRegister.BLOODBOLT_MOB, x, y, z, accelX, accelY, accelZ, worldIn);
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

	public void setDirectHitDamage(float directHitDamage) {
		this.directHitDamage = directHitDamage;
	}

	@Override
	protected ParticleEffect getParticleType() {
		return ParticleTypes.PORTAL;
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
			if (!(entity2 instanceof DemonEntity))
				entity.damage(DamageSource.mob((LivingEntity) entity2), directHitDamage);
			if (entity2 instanceof LivingEntity) {
				if (!(entity2 instanceof DemonEntity))
					this.applyDamageEffects((LivingEntity) entity2, entity);
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
		this.playSound(ModSoundEvents.UNMAKYR_FIRE, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
	}

}