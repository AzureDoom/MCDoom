package mod.azure.doom.entity.projectiles;

import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.network.DoomEntityPacket;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ChaingunBulletEntity extends PersistentProjectileEntity implements GeoEntity {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private float projectiledamage;
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public ChaingunBulletEntity(EntityType<? extends ChaingunBulletEntity> entityType, World world) {
		super(entityType, world);
		this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
	}

	public ChaingunBulletEntity(World world, LivingEntity owner, float damage) {
		super(ProjectilesEntityRegister.CHAINGUN_BULLET, owner, world);
		this.projectiledamage = damage;
	}
	
	public ChaingunBulletEntity(World world, LivingEntity owner) {
		super(ProjectilesEntityRegister.CHAINGUN_BULLET, owner, world);
	}

	protected ChaingunBulletEntity(EntityType<? extends ChaingunBulletEntity> type, double x, double y, double z,
			World world) {
		this(type, world);
	}

	protected ChaingunBulletEntity(EntityType<? extends ChaingunBulletEntity> type, LivingEntity owner, World world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		if (owner instanceof PlayerEntity) {
			this.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
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
	protected void onHit(LivingEntity living) {
		super.onHit(living);
		if (!(living instanceof PlayerEntity) && !(living instanceof IconofsinEntity)) {
			living.setVelocity(0, 0, 0);
			living.timeUntilRegen = 0;
		}
	}

	@Override
	public Packet<ClientPlayPacketListener> createSpawnPacket() {
		return DoomEntityPacket.createPacket(this);
	}

	@Override
	protected void age() {
		++this.ticksInAir;
		if (this.ticksInAir >= 40) {
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
		++this.ticksInAir;
		if (this.ticksInAir >= 80) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		if (this.world.isClient) {
			double d2 = this.getX() + (this.random.nextDouble()) * (double) this.getWidth() * 0.5D;
			double f2 = this.getZ() + (this.random.nextDouble()) * (double) this.getWidth() * 0.5D;
			this.world.addParticle(ParticleTypes.SMOKE, true, d2, this.getY(), f2, 0, 0, 0);
		}
	}

	public void initFromStack(ItemStack stack) {
		if (stack.getItem() == DoomItems.CHAINGUN_BULLETS) {
		}
	}

	@Override
	public boolean hasNoGravity() {
		if (this.isSubmergedInWater()) {
			return false;
		} else {
			return true;
		}
	}

	public SoundEvent hitSound = this.getHitSound();

	@Override
	public void setSound(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@Override
	protected SoundEvent getHitSound() {
		return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		if (!this.world.isClient) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		this.setSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON);
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		if (entityHitResult.getType() != HitResult.Type.ENTITY
				|| !((EntityHitResult) entityHitResult).getEntity().isPartOf(entity)) {
			if (!this.world.isClient) {
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
		Entity entity2 = this.getOwner();
		DamageSource damageSource2;
		if (entity2 == null) {
			damageSource2 = DamageSource.arrow(this, this);
		} else {
			damageSource2 = DamageSource.arrow(this, entity2);
			if (entity2 instanceof LivingEntity) {
				((LivingEntity) entity2).onAttacking(entity);
			}
		}
		if (entity.damage(damageSource2, projectiledamage)) {
			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) entity;
				if (!this.world.isClient && entity2 instanceof LivingEntity) {
					EnchantmentHelper.onUserDamaged(livingEntity, entity2);
					EnchantmentHelper.onTargetDamaged((LivingEntity) entity2, livingEntity);
					this.remove(Entity.RemovalReason.DISCARDED);
				}

				this.onHit(livingEntity);
				if (entity2 != null && livingEntity != entity2 && livingEntity instanceof PlayerEntity
						&& entity2 instanceof ServerPlayerEntity && !this.isSilent()) {
					((ServerPlayerEntity) entity2).networkHandler.sendPacket(
							new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
				}
			}
		} else {
			if (!this.world.isClient) {
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
	}

	@Override
	public ItemStack asItemStack() {
		return new ItemStack(DoomItems.CHAINGUN_BULLETS);
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