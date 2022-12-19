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
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class EnergyCellMobEntity extends ExplosiveProjectileEntity implements GeoEntity {

	public int explosionPower = 1;
	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private float directHitDamage = 3F;
	private LivingEntity shooter;
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

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

	public EnergyCellMobEntity(World worldIn, double x, double y, double z, double accelX, double accelY,
			double accelZ) {
		super(ProjectilesEntityRegister.ENERGY_CELL_MOB, x, y, z, accelX, accelY, accelZ, worldIn);
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
	public Packet<ClientPlayPacketListener> createSpawnPacket() {
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
				World.ExplosionSourceType.NONE);
	}

	public LivingEntity getShooter() {
		return shooter;
	}

	public void setShooter(LivingEntity shooter) {
		this.shooter = shooter;
	}
}