package mod.azure.doom.entity.projectiles;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.tierheavy.CacodemonEntity;
import mod.azure.doom.util.registry.DoomSounds;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;

public class GrenadeEntity extends AbstractArrow implements GeoEntity {

	protected int timeInAir;
	protected boolean inAir;
	protected String type;
	private int ticksInAir;
	private static final EntityDataAccessor<Boolean> SPINNING = SynchedEntityData.defineId(GrenadeEntity.class,
			EntityDataSerializers.BOOLEAN);
	private LivingEntity shooter;
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

	public GrenadeEntity(EntityType<? extends GrenadeEntity> entityType, Level world) {
		super(entityType, world);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public GrenadeEntity(Level world, LivingEntity owner) {
		super(ProjectilesEntityRegister.GRENADE.get(), owner, world);
		this.shooter = owner;
	}

	protected GrenadeEntity(EntityType<? extends GrenadeEntity> type, double x, double y, double z, Level world) {
		this(type, world);
	}

	protected GrenadeEntity(EntityType<? extends GrenadeEntity> type, LivingEntity owner, Level world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		this.shooter = owner;
		if (owner instanceof Player) {
			this.pickup = AbstractArrow.Pickup.DISALLOWED;
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

	public GrenadeEntity(Level world, LivingEntity user, boolean spinning) {
		super(ProjectilesEntityRegister.GRENADE.get(), user, world);
		this.entityData.set(SPINNING, spinning);
		this.shooter = user;
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(SPINNING, false);
	}

	public boolean isSpinning() {
		return (Boolean) this.entityData.get(SPINNING);
	}

	public void setSpinning(boolean spin) {
		this.entityData.set(SPINNING, spin);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
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
		this.explode();
		level.playSound((Player) null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE,
				SoundSource.PLAYERS, 1.0F, 1.5F);
		super.remove(reason);
	}

	@Override
	protected void tickDespawn() {
		++this.ticksInAir;
		if (this.tickCount >= 40) {
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
		compound.putBoolean("State", this.isSpinning());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.ticksInAir = compound.getShort("life");
		this.setSpinning(compound.getBoolean("State"));
	}

	@Override
	public void tick() {
		super.tick();
		if (this.getDeltaMovement().x == 0)
			this.setSpinning(false);
		if (!this.isOnGround())
			this.setSpinning(true);
	}

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
		super.onHit(blockHitResult);
		if (!this.level.isClientSide())
			if (this.tickCount >= 46)
				this.remove(Entity.RemovalReason.DISCARDED);
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		if (!this.level.isClientSide()) {
			if (entity instanceof CacodemonEntity) {
				entity.hurt(DamageSource.playerAttack((Player) this.shooter), ((LivingEntity) entity).getMaxHealth());
				this.remove(Entity.RemovalReason.DISCARDED);
			} else {
				super.onHitEntity(entityHitResult);
				this.explode();
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
	}

	protected void explode() {
		this.level.getEntities(this, new AABB(this.blockPosition().above()).inflate(8)).forEach(e -> doDamage(this, e));
	}

	private void doDamage(Entity user, Entity target) {
		if (target instanceof LivingEntity) {
			target.invulnerableTime = 0;
			target.hurt(DamageSource.indirectMagic(this, target), DoomConfig.SERVER.grenade_damage.get().floatValue());
		}
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(Items.AIR);
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

}