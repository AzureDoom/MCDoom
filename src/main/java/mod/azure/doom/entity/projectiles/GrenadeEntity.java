package mod.azure.doom.entity.projectiles;

import java.util.List;

import mod.azure.doom.entity.tierheavy.CacodemonEntity;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomEntities;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GrenadeEntity extends AbstractArrow implements IAnimatable {

	protected int timeInAir;
	protected boolean inAir;
	protected String type;
	private int ticksInAir;
	private LivingEntity shooter;

	public GrenadeEntity(EntityType<? extends GrenadeEntity> entityType, Level world) {
		super(entityType, world);
	}

	public GrenadeEntity(Level world, LivingEntity user) {
		super(DoomEntities.GRENADE.get(), user, world);
		this.shooter = user;
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("spin", true));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<GrenadeEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void remove(RemovalReason reason) {
		AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
		areaeffectcloudentity.setParticle(ParticleTypes.FLAME);
		areaeffectcloudentity.setRadius(6);
		areaeffectcloudentity.setDuration(1);
		areaeffectcloudentity.setPos(this.getX(), this.getY(), this.getZ());
		this.level.addFreshEntity(areaeffectcloudentity);
		this.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 1.0F);
		super.remove(reason);
	}

	@Override
	protected void tickDespawn() {
		++this.ticksInAir;
		if (this.ticksInAir >= 80) {
			this.remove(RemovalReason.KILLED);
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
		super.tick();
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(DoomItems.GRENADE.get());
	}

	public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

	@Override
	public void setSoundEvent(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return DoomSounds.BEEP.get();
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		this.setSoundEvent(SoundEvents.GENERIC_EXPLODE);
		if (!this.level.isClientSide()) {
			if (this.tickCount >= 46) {
				this.explode();
				this.remove(RemovalReason.DISCARDED);
			}
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		if (!this.level.isClientSide()) {
			if (entity instanceof CacodemonEntity) {
				entity.hurt(DamageSource.playerAttack((Player) this.shooter), ((LivingEntity) entity).getMaxHealth());
				this.remove(RemovalReason.DISCARDED);
			} else {
				this.explode();
				this.remove(RemovalReason.DISCARDED);
			}
		}
	}

	protected void explode() {
		double xn = Mth.floor(this.getX() - 5.0D);
		double xp = Mth.floor(this.getX() + 7.0D);
		double yn = Mth.floor(this.getY() - 5.0D);
		double yp = Mth.floor(this.getY() + 7.0D);
		double zn = Mth.floor(this.getZ() - 5.0D);
		double zp = Mth.floor(this.getZ() + 7.0D);
		List<Entity> list = this.level.getEntities(this, new AABB(xn, yn, zn, xp, yp, zp));
		Vec3 vec3d = new Vec3(this.getX(), this.getY(), this.getZ());
		this.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 1.0F);
		for (int x = 0; x < list.size(); ++x) {
			Entity entity = (Entity) list.get(x);
			double y = (double) (Mth.sqrt((float) entity.distanceToSqr(vec3d)) / 6);
			if (entity instanceof LivingEntity) {
				if (y <= 1.0D) {
					entity.hurt(DamageSource.playerAttack((Player) this.shooter),
							DoomConfig.SERVER.grenade_damage.get().floatValue());
				}
			}
		}
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

}