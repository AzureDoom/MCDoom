package mod.azure.doom.entity.projectiles.entity;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.network.DoomEntityPacket;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class DoomFireEntity extends Entity implements IAnimatable {

	private int warmup;
	private boolean startedAttack;
	private int ticksLeft;
	private boolean playingAnimation;
	private LivingEntity owner;
	private UUID ownerUuid;
	private float damage;

	public DoomFireEntity(EntityType<DoomFireEntity> entityType, World world) {
		super(entityType, world);
		this.ticksLeft = 75;
	}

	public DoomFireEntity(World worldIn, double x, double y, double z, float yaw, int warmup, LivingEntity casterIn,
			float damage) {
		this(ProjectilesEntityRegister.FIRING, worldIn);
		this.warmup = warmup;
		this.setOwner(owner);
		this.updatePosition(x, y, z);
		this.damage = damage;
	}

	@Override
	protected void initDataTracker() {
	}

	public void setOwner(@Nullable LivingEntity owner) {
		this.owner = owner;
		this.ownerUuid = owner == null ? null : owner.getUuid();
	}

	@Nullable
	public LivingEntity getOwner() {
		if (this.owner == null && this.ownerUuid != null && this.world instanceof ServerWorld) {
			Entity entity = ((ServerWorld) this.world).getEntity(this.ownerUuid);
			if (entity instanceof LivingEntity) {
				this.owner = (LivingEntity) entity;
			}
		}

		return this.owner;
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound tag) {
		this.warmup = tag.getInt("Warmup");
		if (tag.containsUuid("Owner")) {
			this.ownerUuid = tag.getUuid("Owner");
		}

	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound tag) {
		tag.putInt("Warmup", this.warmup);
		if (this.ownerUuid != null) {
			tag.putUuid("Owner", this.ownerUuid);
		}

	}

	@Override
	public void remove(RemovalReason reason) {
		this.explode();
		super.remove(reason);
	}

	protected void explode() {
		this.getWorld().getOtherEntities(this, new Box(this.getBlockPos().up()).expand(8))
				.forEach(e -> doDamage(this, e));
	}

	private void doDamage(Entity user, Entity target) {
		if (target instanceof LivingEntity) {
			if (target instanceof DemonEntity)
				return;

			target.timeUntilRegen = 0;
			target.damage(DamageSource.mobProjectile(user, this.getOwner()), damage);
		}
	}

	public void tick() {
		super.tick();
		if (--this.warmup < 0) {
			if (!this.startedAttack) {
				this.world.sendEntityStatus(this, (byte) 4);
				this.startedAttack = true;
			}
			if (--this.ticksLeft < 0) {
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
		if (this.isAlive() && world.getBlockState(this.getBlockPos().up()).isAir()) {
			world.setBlockState(this.getBlockPos().up(), AbstractFireBlock.getState(world, this.getBlockPos().up()));
		}
		this.getWorld().getOtherEntities(this, new Box(this.getBlockPos().up()).expand(1)).forEach(e -> {
			if (e.isAlive() && !(e instanceof DemonEntity)) {
				e.damage(DamageSource.mobProjectile(e, this.getOwner()), damage);
				e.setFireTicks(60);
			}
		});
	}

	public void handleStatus(byte status) {
		super.handleStatus(status);
		if (status == 4) {
			this.playingAnimation = true;
		}

	}

	public float getAnimationProgress(float tickDelta) {
		if (!this.playingAnimation) {
			return 0.0F;
		} else {
			int i = this.ticksLeft - 2;
			return i <= 0 ? 1.0F : 1.0F - ((float) i - tickDelta) / 20.0F;
		}
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<DoomFireEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return DoomEntityPacket.createPacket(this);
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		return source == DamageSource.IN_WALL || source == DamageSource.ON_FIRE || source == DamageSource.IN_FIRE
				? false
				: super.damage(source, amount);
	}

}