package mod.azure.doom.entity.projectiles.entity;

import java.util.List;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.tileentity.TickingLightEntity;
import mod.azure.doom.network.DoomEntityPacket;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomSounds;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
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
	public Packet<?> createSpawnPacket() {
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
		float q = 4.0F;
		int k2 = MathHelper.floor(this.getX() - (double) q - 1.0D);
		int l2 = MathHelper.floor(this.getX() + (double) q + 1.0D);
		int t = MathHelper.floor(this.getY() - (double) q - 1.0D);
		int u = MathHelper.floor(this.getY() + (double) q + 1.0D);
		int v = MathHelper.floor(this.getZ() - (double) q - 1.0D);
		int w = MathHelper.floor(this.getZ() + (double) q + 1.0D);
		List<Entity> list = this.world.getOtherEntities(this,
				new Box((double) k2, (double) t, (double) v, (double) l2, (double) u, (double) w));
		Vec3d vec3d2 = new Vec3d(this.getX(), this.getY(), this.getZ());
		for (int x = 0; x < list.size(); ++x) {
			Entity entity = (Entity) list.get(x);
			double y = (MathHelper.sqrt((float) entity.squaredDistanceTo(vec3d2)) / q);
			if (y <= 1.0D) {
				if (this.world.isClient) {
					double d2 = this.getX()
							+ (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getWidth() * 0.5D;
					double e2 = this.getY() + 0.05D + this.random.nextDouble();
					double f2 = this.getZ()
							+ (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getWidth() * 0.5D;
					this.world.addParticle(ParticleTypes.FLAME, true, d2, e2, f2, 0, 0, 0);
					this.world.addParticle(ParticleTypes.SMOKE, true, d2, e2, f2, 0, 0, 0);
				}
			}
		}

		List<Entity> list1 = this.world.getOtherEntities(this, new Box(this.getBlockPos().up()).expand(1D, 5D, 1D));
		for (int x = 0; x < list1.size(); ++x) {
			Entity entity = (Entity) list1.get(x);
			double y = (double) (MathHelper.sqrt(entity.distanceTo(this)));
			if (y <= 1.0D) {
				if (entity.isAlive()) {
					entity.damage(DamageSource.magic(entity, this.getOwner()), 3);
					if (!(entity instanceof FireProjectile && this.getOwner() instanceof DemonEntity)) {
						entity.setFireTicks(90);
					}
				}
			}
		}
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		if (!this.world.isClient) {
			Entity entity = entityHitResult.getEntity();
			Entity entity2 = this.getOwner();
			if (!(entity2 instanceof DemonEntity))
				entity.damage(DamageSource.mob((LivingEntity) entity2), directHitDamage);
			if (entity2 instanceof LivingEntity) {
				if (!(entity2 instanceof DemonEntity))
					this.applyDamageEffects((LivingEntity) entity2, entity);
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
		this.playSound(DoomSounds.UNMAKYR_FIRE, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
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
