package mod.azure.doom.entity.projectiles.entity;

import mod.azure.doom.entity.tileentity.TickingLightEntity;
import mod.azure.doom.network.DoomEntityPacket;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class FireProjectile extends ExplosiveProjectileEntity {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private float directHitDamage = 2;
	private BlockPos lightBlockPos = null;
	private int idleTicks = 0;

	public FireProjectile(EntityType<FireProjectile> entitytype, World world) {
		super(entitytype, world);
	}

	public FireProjectile(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ,
			float directHitDamage) {
		super(ProjectilesEntityRegister.FIRE_MOB, shooter, accelX, accelY, accelZ, worldIn);
		this.directHitDamage = directHitDamage;
	}

	public FireProjectile(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(ProjectilesEntityRegister.FIRE_MOB, x, y, z, accelX, accelY, accelZ, worldIn);
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

	public void setDirectHitDamage(float directHitDamage) {
		this.directHitDamage = directHitDamage;
	}

	@Override
	public Packet<ClientPlayPacketListener> createSpawnPacket() {
		return DoomEntityPacket.createPacket(this);
	}

	@Override
	public boolean hasNoGravity() {
		return false;
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
		if (this.ticksInAir >= 40) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		boolean isInsideWaterBlock = world.isWater(getBlockPos());
		spawnLightSource(isInsideWaterBlock);
		if (this.world.isClient) {
			double d2 = this.getX() + (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getWidth() * 0.5D;
			double e2 = this.getY() + 0.05D + this.random.nextDouble();
			double f2 = this.getZ() + (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getWidth() * 0.5D;
			this.world.addParticle(ParticleTypes.FLAME, true, d2, e2, f2, 0, 0, 0);
			this.world.addParticle(ParticleTypes.SMOKE, true, d2, e2, f2, 0, 0, 0);
		}
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		BlockPos blockPos;
		super.onBlockHit(blockHitResult);
		if (this.world.isClient) {
			return;
		}
		Entity entity = this.getOwner();
		if ((!(entity instanceof MobEntity) || this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING))
				&& this.world.isAir(blockPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide()))) {
			this.world.setBlockState(blockPos, AbstractFireBlock.getState(this.world, blockPos));
		}
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		if (!this.world.isClient) {
			Entity entity = entityHitResult.getEntity();
			Entity entity2 = this.getOwner();
			entity.damage(DamageSource.mob((LivingEntity) entity2), directHitDamage);
			if (entity2 instanceof LivingEntity) {
				this.applyDamageEffects((LivingEntity) entity2, entity);
				this.remove(Entity.RemovalReason.DISCARDED);
				entity.setOnFireFor(15);
			}
		}
	}

	@Override
	public boolean doesRenderOnFire() {
		return false;
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

}
