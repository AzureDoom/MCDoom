package mod.azure.doom.entity.projectiles;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomProjectiles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

public class ChaingunBulletEntity extends AbstractArrow implements GeoEntity {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private float projectiledamage;
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

	public ChaingunBulletEntity(EntityType<? extends ChaingunBulletEntity> entityType, Level world) {
		super(entityType, world);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public ChaingunBulletEntity(Level world, LivingEntity owner, float damage) {
		super(DoomProjectiles.CHAINGUN_BULLET.get(), owner, world);
		this.projectiledamage = damage;
	}

	public ChaingunBulletEntity(Level world, LivingEntity owner) {
		super(DoomProjectiles.CHAINGUN_BULLET.get(), owner, world);
	}

	protected ChaingunBulletEntity(EntityType<? extends ChaingunBulletEntity> type, double x, double y, double z, Level world) {
		this(type, world);
	}

	protected ChaingunBulletEntity(EntityType<? extends ChaingunBulletEntity> type, LivingEntity owner, Level world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		if (owner instanceof Player)
			this.pickup = AbstractArrow.Pickup.DISALLOWED;
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
	protected void doPostHurtEffects(LivingEntity living) {
		super.doPostHurtEffects(living);
		if (!(living instanceof Player) && !(living instanceof IconofsinEntity)) {
			living.setDeltaMovement(0, 0, 0);
			living.invulnerableTime = 0;
		}
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void tickDespawn() {
		++this.ticksInAir;
		if (this.tickCount >= 40)
			this.remove(RemovalReason.KILLED);
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
		++this.ticksInAir;
		if (this.ticksInAir >= 80)
			this.remove(Entity.RemovalReason.DISCARDED);
		if (this.level().isClientSide())
			this.level().addParticle(ParticleTypes.SMOKE, true, this.getX() + (this.random.nextDouble()) * (double) this.getBbWidth() * 0.5D, this.getY(), this.getZ() + (this.random.nextDouble()) * (double) this.getBbWidth() * 0.5D, 0, 0, 0);
	}

	public void initFromStack(ItemStack stack) {
		if (stack.getItem() == DoomItems.CHAINGUN_BULLETS.get()) {
		}
	}

	@Override
	public boolean isNoGravity() {
		if (this.isInWater())
			return false;
		return true;
	}

	@Override
	public void setSoundEvent(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.ARMOR_EQUIP_IRON;
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		if (!this.level().isClientSide())
			this.remove(Entity.RemovalReason.DISCARDED);
		this.setSoundEvent(SoundEvents.ARMOR_EQUIP_IRON);
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		var entity = entityHitResult.getEntity();
		if (entityHitResult.getType() != HitResult.Type.ENTITY || !((EntityHitResult) entityHitResult).getEntity().is(entity))
			if (!this.level().isClientSide)
				this.remove(RemovalReason.KILLED);
		var entity1 = this.getOwner();
		DamageSource damagesource;
		if (entity1 == null)
			damagesource = damageSources().arrow(this, this);
		else {
			damagesource = damageSources().arrow(this, entity1);
			if (entity1 instanceof LivingEntity)
				((LivingEntity) entity1).setLastHurtMob(entity);
		}
		if (entity.hurt(damagesource, projectiledamage)) {
			if (entity instanceof LivingEntity) {
				var livingentity = (LivingEntity) entity;
				if (!this.level().isClientSide && entity1 instanceof LivingEntity) {
					EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
					EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingentity);
					this.remove(RemovalReason.KILLED);
				}
				this.doPostHurtEffects(livingentity);
				if (entity1 != null && livingentity != entity1 && livingentity instanceof Player && entity1 instanceof ServerPlayer && !this.isSilent())
					((ServerPlayer) entity1).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
			}
		} else {
			if (!this.level().isClientSide)
				this.remove(RemovalReason.KILLED);
		}
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(DoomItems.CHAINGUN_BULLETS.get());
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}
}