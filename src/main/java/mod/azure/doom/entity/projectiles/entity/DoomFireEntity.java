package mod.azure.doom.entity.projectiles.entity;

import java.util.UUID;

import javax.annotation.Nullable;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.registry.DoomEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class DoomFireEntity extends Entity implements IAnimatable {

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<DoomFireEntity>(this, "controller", 0, this::predicate));
	}

	private int warmupDelayTicks;
	private boolean sentSpikeEvent;
	private int lifeTicks = 75;
	private boolean clientSideAttackStarted;
	private LivingEntity caster;
	private UUID casterUuid;
	private float damage;

	public DoomFireEntity(EntityType<DoomFireEntity> p_i50170_1_, Level p_i50170_2_) {
		super(p_i50170_1_, p_i50170_2_);
	}

	public DoomFireEntity(Level worldIn, double x, double y, double z, float p_i47276_8_, int p_i47276_9_,
			LivingEntity casterIn, float damage) {
		this(DoomEntities.FIRING.get(), worldIn);
		this.warmupDelayTicks = p_i47276_9_;
		this.setCaster(casterIn);
		this.yRot = p_i47276_8_ * (180F / (float) Math.PI);
		this.setPos(x, y, z);
		this.damage = damage;
	}

	protected void defineSynchedData() {
	}

	public void setCaster(@Nullable LivingEntity p_190549_1_) {
		this.caster = p_190549_1_;
		this.casterUuid = p_190549_1_ == null ? null : p_190549_1_.getUUID();
	}

	@Nullable
	public LivingEntity getCaster() {
		if (this.caster == null && this.casterUuid != null && this.level instanceof ServerLevel) {
			Entity entity = ((ServerLevel) this.level).getEntity(this.casterUuid);
			if (entity instanceof LivingEntity) {
				this.caster = (LivingEntity) entity;
			}
		}

		return this.caster;
	}

	protected void readAdditionalSaveData(CompoundTag compound) {
		this.warmupDelayTicks = compound.getInt("Warmup");
		if (compound.hasUUID("Owner")) {
			this.casterUuid = compound.getUUID("Owner");
		}
	}

	protected void addAdditionalSaveData(CompoundTag compound) {
		compound.putInt("Warmup", this.warmupDelayTicks);
		if (this.casterUuid != null) {
			compound.putUUID("Owner", this.casterUuid);
		}
	}

	@Override
	public void remove(RemovalReason reason) {
		this.explode();
		super.remove(reason);
	}

	protected void explode() {
		this.level.getEntities(this, new AABB(this.blockPosition().above()).inflate(8))
				.forEach(e -> doDamage(this, e));
	}

	private void doDamage(Entity user, Entity target) {
		if (target instanceof LivingEntity) {
			if (target instanceof DemonEntity)
				return;

			target.invulnerableTime = 0;
			target.hurt(DamageSource.indirectMagic(this, target), damage);
		}
	}

	public void tick() {
		super.tick();
		if (--this.warmupDelayTicks < 0) {
			if (!this.sentSpikeEvent) {
				this.level.broadcastEntityEvent(this, (byte) 4);
				this.sentSpikeEvent = true;
			}

			if (--this.lifeTicks < 0) {
				this.remove(RemovalReason.KILLED);
			}
		}
		if (this.isAlive() && level.getBlockState(this.blockPosition().above()).isAir()) {
			level.setBlockAndUpdate(this.blockPosition().above(), BaseFireBlock.getState(level, this.blockPosition().above()));
		}
		this.level.getEntities(this, new AABB(this.blockPosition().above()).inflate(1)).forEach(e -> {
			if (e.isAlive() && !(e instanceof DemonEntity)) {
				e.hurt(DamageSource.indirectMobAttack(e, this.getCaster()), damage);
				e.setRemainingFireTicks(60);
			}
		});
	}

	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte id) {
		super.handleEntityEvent(id);
		if (id == 4) {
			this.clientSideAttackStarted = true;
		}

	}

	@OnlyIn(Dist.CLIENT)
	public float getAnimationProgress(float partialTicks) {
		if (!this.clientSideAttackStarted) {
			return 0.0F;
		} else {
			int i = this.lifeTicks - 2;
			return i <= 0 ? 1.0F : 1.0F - ((float) i - partialTicks) / 20.0F;
		}
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		return source == DamageSource.IN_WALL || source == DamageSource.ON_FIRE || source == DamageSource.IN_FIRE
				? false
				: super.hurt(source, amount);
	}

}