package mod.azure.doom.entity.projectiles;

import java.util.List;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.tierheavy.CacodemonEntity;
import mod.azure.doom.network.EntityPacket;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomSounds;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GrenadeEntity extends PersistentProjectileEntity implements IAnimatable {

	protected int timeInAir;
	protected boolean inAir;
	protected String type;
	private int ticksInAir;
	private static final TrackedData<Boolean> SPINNING = DataTracker.registerData(GrenadeEntity.class,
			TrackedDataHandlerRegistry.BOOLEAN);
	private LivingEntity shooter;

	public GrenadeEntity(EntityType<? extends GrenadeEntity> entityType, World world) {
		super(entityType, world);
		this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
	}

	public GrenadeEntity(World world, LivingEntity owner) {
		super(ProjectilesEntityRegister.GRENADE, owner, world);
		this.shooter = owner;
	}

	protected GrenadeEntity(EntityType<? extends GrenadeEntity> type, double x, double y, double z, World world) {
		this(type, world);
	}

	protected GrenadeEntity(EntityType<? extends GrenadeEntity> type, LivingEntity owner, World world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		this.shooter = owner;
		if (owner instanceof PlayerEntity) {
			this.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
		}

	}

	public GrenadeEntity(World world, LivingEntity user, boolean spinning) {
		super(ProjectilesEntityRegister.GRENADE, user, world);
		this.dataTracker.set(SPINNING, spinning);
		this.shooter = user;
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(SPINNING, false);
	}

	public boolean isSpinning() {
		return (Boolean) this.dataTracker.get(SPINNING);
	}

	public void setSpinning(boolean spin) {
		this.dataTracker.set(SPINNING, spin);
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
	public Packet<?> createSpawnPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	public void remove(RemovalReason reason) {
		AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(),
				this.getZ());
		areaeffectcloudentity.setParticleType(ParticleTypes.FLAME);
		areaeffectcloudentity.setRadius(6);
		areaeffectcloudentity.setDuration(1);
		areaeffectcloudentity.updatePosition(this.getX(), this.getY(), this.getZ());
		this.world.spawnEntity(areaeffectcloudentity);
		world.playSound((PlayerEntity) null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE,
				SoundCategory.PLAYERS, 1.0F, 1.5F);
		super.remove(reason);
	}

	@Override
	public void age() {
		++this.ticksInAir;
		if (this.ticksInAir >= 80) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
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
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.ticksInAir = tag.getShort("life");
	}

	@Override
	public void tick() {
		super.tick();
	}

	public void initFromStack(ItemStack stack) {
		if (stack.getItem() == DoomItems.GRENADE) {
		}
	}

	public SoundEvent hitSound = this.getHitSound();

	@Override
	public void setSound(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@Override
	protected SoundEvent getHitSound() {
		return DoomSounds.BEEP;
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		if (!this.world.isClient) {
			if (this.age >= 46) {
				this.explode();
				this.dataTracker.set(SPINNING, false);
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
		this.setSound(SoundEvents.ENTITY_GENERIC_EXPLODE);
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		if (!this.world.isClient) {
			if (entity instanceof CacodemonEntity) {
				entity.damage(DamageSource.player((PlayerEntity) this.shooter), ((LivingEntity) entity).getMaxHealth());
				this.remove(Entity.RemovalReason.DISCARDED);
			} else {
				super.onEntityHit(entityHitResult);
				this.explode();
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
	}

	protected void explode() {
		double xn = MathHelper.floor(this.getX() - 5.0D);
		double xp = MathHelper.floor(this.getX() + 7.0D);
		double yn = MathHelper.floor(this.getY() - 5.0D);
		double yp = MathHelper.floor(this.getY() + 7.0D);
		double zn = MathHelper.floor(this.getZ() - 5.0D);
		double zp = MathHelper.floor(this.getZ() + 7.0D);
		List<Entity> list = this.world.getOtherEntities(this, new Box(xn, yn, zn, xp, yp, zp));
		Vec3d vec3d = new Vec3d(this.getX(), this.getY(), this.getZ());

		for (int x = 0; x < list.size(); ++x) {
			Entity entity = (Entity) list.get(x);
			double y = (double) (MathHelper.sqrt((float) entity.squaredDistanceTo(vec3d)) / 6);
			if (entity instanceof LivingEntity) {
				if (y <= 1.0D) {
					entity.damage(DamageSource.player((PlayerEntity) this.shooter), DoomConfig.grenade_damage);
				}
			}
		}
	}

	@Override
	public ItemStack asItemStack() {
		return new ItemStack(DoomItems.GRENADE);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double distance) {
		return true;
	}

	@Override
	public boolean doesRenderOnFire() {
		return false;
	}

}