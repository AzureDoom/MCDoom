package mod.azure.doom.entity.projectiles;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomParticles;
import mod.azure.doom.util.registry.DoomSounds;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
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

public class EnergyCellEntity extends AbstractArrow implements GeoEntity {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private float projectiledamage;
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

	public EnergyCellEntity(EntityType<? extends EnergyCellEntity> entityType, Level world) {
		super(entityType, world);
		this.pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public EnergyCellEntity(Level world, LivingEntity owner) {
		super(ProjectilesEntityRegister.ENERGY_CELL, owner, world);
	}

	public EnergyCellEntity(Level world, LivingEntity owner, float damage) {
		super(ProjectilesEntityRegister.ENERGY_CELL, owner, world);
		this.projectiledamage = damage;
	}

	protected EnergyCellEntity(EntityType<? extends EnergyCellEntity> type, double x, double y, double z, Level world) {
		this(type, world);
	}

	protected EnergyCellEntity(EntityType<? extends EnergyCellEntity> type, LivingEntity owner, Level world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		if (owner instanceof Player) {
			this.pickup = AbstractArrow.Pickup.DISALLOWED;
		}
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
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return EntityPacket.createPacket(this);
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
		if (this.ticksInAir >= 80) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		if (this.level.isClientSide()) {
			double d2 = this.getX() + (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getBbWidth() * 0.5D;
			double f2 = this.getZ() + (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getBbWidth() * 0.5D;
			this.level.addParticle(DoomParticles.PLASMA, true, d2, this.getY(), f2, 0, 0, 0);
		}
	}

	public void initFromStack(ItemStack stack) {
		if (stack.getItem() == DoomItems.ENERGY_CELLS) {
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
		return DoomSounds.PLASMA_HIT;
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		if (!this.level.isClientSide())
			this.remove(Entity.RemovalReason.DISCARDED);
		this.setSoundEvent(DoomSounds.PLASMA_HIT);
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		if (entityHitResult.getType() != HitResult.Type.ENTITY
				|| !((EntityHitResult) entityHitResult).getEntity().is(entity)) {
			if (!this.level.isClientSide) {
				this.remove(RemovalReason.KILLED);
			}
		}
		Entity entity1 = this.getOwner();
		DamageSource damagesource;
		if (entity1 == null) {
			damagesource = DamageSource.arrow(this, this);
		} else {
			damagesource = DamageSource.arrow(this, entity1);
			if (entity1 instanceof LivingEntity) {
				((LivingEntity) entity1).setLastHurtMob(entity);
			}
		}
		if (entity.hurt(damagesource, projectiledamage)) {
			if (entity instanceof LivingEntity) {
				LivingEntity livingentity = (LivingEntity) entity;
				if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
					EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
					EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingentity);
					this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.5F,
							Level.ExplosionInteraction.NONE);
					this.remove(RemovalReason.KILLED);
				}
				this.doPostHurtEffects(livingentity);
				if (entity1 != null && livingentity != entity1 && livingentity instanceof Player
						&& entity1 instanceof ServerPlayer && !this.isSilent()) {
					((ServerPlayer) entity1).connection
							.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
				}
			}
		} else {
			if (!this.level.isClientSide) {
				this.remove(RemovalReason.KILLED);
			}
		}
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(DoomItems.ENERGY_CELLS);
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}
}