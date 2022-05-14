package mod.azure.doom.entity.projectiles;

import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.entity.tileentity.TickingLightEntity;
import mod.azure.doom.network.EntityPacket;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomParticles;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
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
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UnmaykrBoltEntity extends PersistentProjectileEntity {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private float projectiledamage;
	private BlockPos lightBlockPos = null;
	private int idleTicks = 0;

	public UnmaykrBoltEntity(EntityType<? extends UnmaykrBoltEntity> entityType, World world) {
		super(entityType, world);
		this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
	}

	public UnmaykrBoltEntity(World world, LivingEntity owner) {
		super(ProjectilesEntityRegister.UNMAYKR, owner, world);
	}

	public UnmaykrBoltEntity(World world, LivingEntity owner, float damage) {
		super(ProjectilesEntityRegister.UNMAYKR, owner, world);
		this.projectiledamage = damage;
	}

	protected UnmaykrBoltEntity(EntityType<? extends UnmaykrBoltEntity> type, double x, double y, double z,
			World world) {
		this(type, world);
	}

	protected UnmaykrBoltEntity(EntityType<? extends UnmaykrBoltEntity> type, LivingEntity owner, World world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		if (owner instanceof PlayerEntity) {
			this.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
		}

	}

	@Override
	public Packet<?> createSpawnPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	protected void age() {
		++this.ticksInAir;
		if (this.ticksInAir >= 40) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
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
		int idleOpt = 100;
		if (getVelocity().lengthSquared() < 0.01)
			idleTicks++;
		else
			idleTicks = 0;
		if (idleOpt <= 0 || idleTicks < idleOpt)
			super.tick();
		++this.ticksInAir;
		if (this.ticksInAir >= 80) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		boolean isInsideWaterBlock = world.isWater(getBlockPos());
		spawnLightSource(isInsideWaterBlock);
		if (this.world.isClient) {
			double d2 = this.getX() + (this.random.nextDouble()) * (double) this.getWidth() * 0.5D;
			double f2 = this.getZ() + (this.random.nextDouble()) * (double) this.getWidth() * 0.5D;
			this.world.addParticle(DoomParticles.UNMAYKR, true, d2, this.getY(), f2, 0, 0, 0);
		}
	}

	private void spawnLightSource(boolean isInWaterBlock) {
		if (lightBlockPos == null) {
			lightBlockPos = findFreeSpace(world, getBlockPos(), 2);
			if (lightBlockPos == null)
				return;
			world.setBlockState(lightBlockPos, DoomBlocks.TICKING_LIGHT_BLOCK.getDefaultState());
		} else if (checkDistance(lightBlockPos, getBlockPos(), 2)) {
			BlockEntity blockEntity = world.getBlockEntity(lightBlockPos);
			if (blockEntity instanceof TickingLightEntity) {
				((TickingLightEntity) blockEntity).refresh(isInWaterBlock ? 20 : 0);
			} else
				lightBlockPos = null;
		} else
			lightBlockPos = null;
	}

	private boolean checkDistance(BlockPos blockPosA, BlockPos blockPosB, int distance) {
		return Math.abs(blockPosA.getX() - blockPosB.getX()) <= distance
				&& Math.abs(blockPosA.getY() - blockPosB.getY()) <= distance
				&& Math.abs(blockPosA.getZ() - blockPosB.getZ()) <= distance;
	}

	private BlockPos findFreeSpace(World world, BlockPos blockPos, int maxDistance) {
		if (blockPos == null)
			return null;

		int[] offsets = new int[maxDistance * 2 + 1];
		offsets[0] = 0;
		for (int i = 2; i <= maxDistance * 2; i += 2) {
			offsets[i - 1] = i / 2;
			offsets[i] = -i / 2;
		}
		for (int x : offsets)
			for (int y : offsets)
				for (int z : offsets) {
					BlockPos offsetPos = blockPos.add(x, y, z);
					BlockState state = world.getBlockState(offsetPos);
					if (state.isAir() || state.getBlock().equals(DoomBlocks.TICKING_LIGHT_BLOCK))
						return offsetPos;
				}

		return null;
	}

	public void initFromStack(ItemStack stack) {
		if (stack.getItem() == DoomItems.UNMAKRY_BOLT) {
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
			damageSource2 = DamageSource.magic(this, this);
		} else {
			damageSource2 = DamageSource.magic(this, entity2);
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
		return new ItemStack(DoomItems.UNMAKRY_BOLT);
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