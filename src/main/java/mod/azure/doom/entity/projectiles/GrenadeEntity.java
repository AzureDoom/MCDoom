package mod.azure.doom.entity.projectiles;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.tierheavy.CacodemonEntity;
import mod.azure.doom.network.DoomEntityPacket;
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
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GrenadeEntity extends PersistentProjectileEntity implements GeoEntity {

	protected int timeInAir;
	protected boolean inAir;
	protected String type;
	private int ticksInAir;
	private static final TrackedData<Boolean> SPINNING = DataTracker.registerData(GrenadeEntity.class,
			TrackedDataHandlerRegistry.BOOLEAN);
	private LivingEntity shooter;
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

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
			this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
		}
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			if (this.inGroundTime == 0)
				return event.setAndContinue(RawAnimation.begin().thenLoop("spin"));
			return PlayState.STOP;
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
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

	@Override
	public Packet<ClientPlayPacketListener> createSpawnPacket() {
		return DoomEntityPacket.createPacket(this);
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
		this.explode();
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
		tag.putBoolean("State", this.isSpinning());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.ticksInAir = tag.getShort("life");
		this.setSpinning(tag.getBoolean("State"));
	}

	@Override
	public void tick() {
		super.tick();
		if (this.getVelocity().x == 0)
			this.setSpinning(false);
		if (!this.isOnGround())
			this.setSpinning(true);
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
		this.getEntityWorld().getOtherEntities(this, new Box(this.getBlockPos().up()).expand(8))
				.forEach(e -> doDamage(this, e));
	}

	private void doDamage(Entity user, Entity target) {
		if (target instanceof LivingEntity) {
			target.timeUntilRegen = 0;
			target.damage(DamageSource.magic(this, target), DoomConfig.grenade_damage);
		}
	}

	@Override
	public ItemStack asItemStack() {
		return new ItemStack(Items.AIR);
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