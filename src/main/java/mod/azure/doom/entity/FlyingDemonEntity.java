package mod.azure.doom.entity;

import java.util.Random;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import mod.azure.doom.DoomMod;
import mod.azure.doom.config.DoomConfig.MobStats;
import mod.azure.doom.network.EntityPacket;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.fluid.Fluid;
import net.minecraft.network.Packet;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class FlyingDemonEntity extends FlyingEntity implements Angerable, Monster {

	private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(FlyingDemonEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	public static final TrackedData<Integer> STATE = DataTracker.registerData(FlyingDemonEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
	private UUID targetUuid;

	public static MobStats config = DoomMod.config.stats;

	protected FlyingDemonEntity(EntityType<? extends FlyingEntity> type, World worldIn) {
		super(type, worldIn);
		this.ignoreCameraFrustum = true;
		this.moveControl = new FlyingDemonEntity.MoveController(this);
	}

	static class MoveController extends MoveControl {
		private final FlyingDemonEntity ghast;
		private int collisionCheckCooldown;

		public MoveController(FlyingDemonEntity ghast) {
			super(ghast);
			this.ghast = ghast;
		}

		public void tick() {
			if (this.state == MoveControl.State.MOVE_TO) {
				if (this.collisionCheckCooldown-- <= 0) {
					this.collisionCheckCooldown += this.ghast.getRandom().nextInt(5) + 2;
					Vec3d vec3d = new Vec3d(this.targetX - this.ghast.getX(), this.targetY - this.ghast.getY(),
							this.targetZ - this.ghast.getZ());
					double d = vec3d.length();
					vec3d = vec3d.normalize();
					if (this.willCollide(vec3d, MathHelper.ceil(d))) {
						this.ghast.setVelocity(this.ghast.getVelocity().add(vec3d.multiply(0.1D)));
					} else {
						this.state = MoveControl.State.WAIT;
					}
				}
			}
		}

		private boolean willCollide(Vec3d direction, int steps) {
			Box box = this.ghast.getBoundingBox();
			for (int i = 1; i < steps; ++i) {
				box = box.offset(direction);
				if (!this.ghast.world.isSpaceEmpty(this.ghast, box)) {
					return false;
				}
			}
			return true;
		}
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	public boolean canWalkOnFluid(Fluid fluid) {
		return fluid.isIn(FluidTags.LAVA);
	}

	public int getAttckingState() {
		return this.dataTracker.get(STATE);
	}

	public void setAttackingState(int time) {
		this.dataTracker.set(STATE, time);
	}

	public static boolean canSpawnInDark(EntityType<? extends FlyingEntity> type, ServerWorldAccess serverWorldAccess,
			SpawnReason spawnReason, BlockPos pos, Random random) {
		if (serverWorldAccess.getDifficulty() == Difficulty.PEACEFUL)
			return false;
		if ((spawnReason != SpawnReason.CHUNK_GENERATION && spawnReason != SpawnReason.NATURAL))
			return !serverWorldAccess.getBlockState(pos.down()).isOf(Blocks.NETHER_WART_BLOCK);
		return !serverWorldAccess.getBlockState(pos.down()).isOf(Blocks.NETHER_WART_BLOCK);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(ANGER_TIME, 0);
		this.dataTracker.startTracking(STATE, 0);
	}

	@Override
	public int getAngerTime() {
		return this.dataTracker.get(ANGER_TIME);
	}

	@Override
	public void setAngerTime(int ticks) {
		this.dataTracker.set(ANGER_TIME, ticks);
	}

	@Override
	public UUID getAngryAt() {
		return this.targetUuid;
	}

	@Override
	public void setAngryAt(@Nullable UUID uuid) {
		this.targetUuid = uuid;
	}

	@Override
	public void chooseRandomAngerTime() {
		this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
	}

	@Override
	protected boolean isDisallowedInPeaceful() {
		return true;
	}

	@Override
	protected void updatePostDeath() {
		++this.deathTime;
		if (this.deathTime == 40) {
			this.remove(Entity.RemovalReason.KILLED);
		}
	}

}