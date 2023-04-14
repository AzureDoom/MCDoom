package mod.azure.doom.entity.projectiles.entity;

import mod.azure.azurelib.AzureLibMod.AzureBlocks;
import mod.azure.azurelib.entities.TickingLightEntity;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.registry.DoomProjectiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

public class FireProjectile extends AbstractHurtingProjectile {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private float directHitDamage = 2;
	private BlockPos lightBlockPos = null;
	private int idleTicks = 0;

	public FireProjectile(EntityType<FireProjectile> entitytype, Level world) {
		super(entitytype, world);
	}

	public FireProjectile(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ, float directHitDamage) {
		super(DoomProjectiles.FIRE_MOB.get(), shooter, accelX, accelY, accelZ, worldIn);
		this.directHitDamage = directHitDamage;
	}

	public FireProjectile(Level worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(DoomProjectiles.FIRE_MOB.get(), x, y, z, accelX, accelY, accelZ, worldIn);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putShort("life", (short) ticksInAir);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		ticksInAir = compound.getShort("life");
	}

	public void setDirectHitDamage(float directHitDamage) {
		this.directHitDamage = directHitDamage;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public boolean isNoGravity() {
		return false;
	}

	@Override
	public void tick() {
		final var idleOpt = 100;
		if (getDeltaMovement().lengthSqr() < 0.01)
			idleTicks++;
		else
			idleTicks = 0;
		if (idleOpt <= 0 || idleTicks < idleOpt)
			super.tick();
		++ticksInAir;
		if (ticksInAir >= 40) 
			remove(Entity.RemovalReason.DISCARDED);
		final var isInsideWaterBlock = level.isWaterAt(blockPosition());
		spawnLightSource(isInsideWaterBlock);
		if (level.isClientSide()) {
			level.addParticle(ParticleTypes.FLAME, true, this.getX() + (random.nextDouble() * 2.0D - 1.0D) * getBbWidth() * 0.5D, this.getY(), this.getZ() + (random.nextDouble() * 2.0D - 1.0D) * getBbWidth() * 0.5D, 0, 0, 0);
			level.addParticle(ParticleTypes.SMOKE, true, this.getX() + (random.nextDouble() * 2.0D - 1.0D) * getBbWidth() * 0.5D, this.getY(), this.getZ() + (random.nextDouble() * 2.0D - 1.0D) * getBbWidth() * 0.5D, 0, 0, 0);
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		final var blockpos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
		if (!level.isClientSide) {
			return;
		}
		final var entity = getOwner();
		if (!(entity instanceof Mob) || level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
			if (level.isEmptyBlock(blockpos)) 
				level.setBlockAndUpdate(blockpos, BaseFireBlock.getState(level, blockpos));
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		if (!level.isClientSide) {
			final var entity = entityHitResult.getEntity();
			final var entity1 = getOwner();
			remove(RemovalReason.KILLED);
			if (entity1 instanceof LivingEntity) {
				if (!(entity instanceof DemonEntity)) {
					entity.hurt(DamageSource.mobAttack((LivingEntity) entity1), directHitDamage);
					entity.setSecondsOnFire(15);
					doEnchantDamageEffects((LivingEntity) entity1, entity);
				}
			}
		}
	}

	@Override
	protected boolean shouldBurn() {
		return false;
	}

	private void spawnLightSource(boolean isInWaterBlock) {
		if (lightBlockPos == null) {
			lightBlockPos = findFreeSpace(level, blockPosition(), 2);
			if (lightBlockPos == null)
				return;
			level.setBlockAndUpdate(lightBlockPos, AzureBlocks.TICKING_LIGHT_BLOCK.get().defaultBlockState());
		} else if (checkDistance(lightBlockPos, blockPosition(), 2)) {
			final var blockEntity = level.getBlockEntity(lightBlockPos);
			if (blockEntity instanceof TickingLightEntity) 
				((TickingLightEntity) blockEntity).refresh(isInWaterBlock ? 20 : 0);
			else
				lightBlockPos = null;
		} else
			lightBlockPos = null;
	}

	private boolean checkDistance(BlockPos blockPosA, BlockPos blockPosB, int distance) {
		return Math.abs(blockPosA.getX() - blockPosB.getX()) <= distance && Math.abs(blockPosA.getY() - blockPosB.getY()) <= distance && Math.abs(blockPosA.getZ() - blockPosB.getZ()) <= distance;
	}

	private BlockPos findFreeSpace(Level world, BlockPos blockPos, int maxDistance) {
		if (blockPos == null)
			return null;

		final var offsets = new int[maxDistance * 2 + 1];
		offsets[0] = 0;
		for (var i = 2; i <= maxDistance * 2; i += 2) {
			offsets[i - 1] = i / 2;
			offsets[i] = -i / 2;
		}
		for (final var x : offsets)
			for (final var y : offsets)
				for (final var z : offsets) {
					final var offsetPos = blockPos.offset(x, y, z);
					final var state = world.getBlockState(offsetPos);
					if (state.isAir() || state.getBlock().equals(AzureBlocks.TICKING_LIGHT_BLOCK.get()))
						return offsetPos;
				}

		return null;
	}
	
	@Override
	public boolean displayFireAnimation() {
		return false;
	}

}
