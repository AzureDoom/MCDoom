package mod.azure.doom.entity.projectiles.entity;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.network.DoomEntityPacket;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DoomFireEntity extends Entity implements GeoEntity {

	private int warmupDelayTicks;
	private boolean sentSpikeEvent;
	private int lifeTicks = 75;
	private boolean clientSideAttackStarted;
	private LivingEntity caster;
	private UUID casterUuid;
	private float damage;
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public DoomFireEntity(EntityType<DoomFireEntity> entityType, Level world) {
		super(entityType, world);
		this.lifeTicks = 75;
	}

	public DoomFireEntity(Level worldIn, double x, double y, double z, float yaw, int warmup, LivingEntity casterIn,
			float damage) {
		this(ProjectilesEntityRegister.FIRING, worldIn);
		this.warmupDelayTicks = warmup;
		this.setCaster(casterIn);
		this.absMoveTo(x, y, z);
		this.damage = damage;
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
	protected void defineSynchedData() {
	}

	public void setCaster(@Nullable LivingEntity owner) {
		this.caster = owner;
		this.casterUuid = owner == null ? null : owner.getUUID();
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
		this.level.getEntities(this, new AABB(this.blockPosition().above()).inflate(8)).forEach(e -> doDamage(this, e));
	}

	private void doDamage(Entity user, Entity target) {
		if (target instanceof LivingEntity) {
			if (target instanceof DemonEntity)
				return;

			target.invulnerableTime = 0;
			target.hurt(DamageSource.indirectMobAttack(this, (LivingEntity) target), damage);
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
			level.setBlockAndUpdate(this.blockPosition().above(),
					BaseFireBlock.getState(level, this.blockPosition().above()));
		}
		this.level.getEntities(this, new AABB(this.blockPosition().above()).inflate(1)).forEach(e -> {
			if (e.isAlive() && !(e instanceof DemonEntity)) {
				e.hurt(DamageSource.indirectMobAttack(e, this.getCaster()), damage);
				e.setRemainingFireTicks(60);
			}
		});
	}

	@Environment(EnvType.CLIENT)
	public void handleEntityEvent(byte status) {
		super.handleEntityEvent(status);
		if (status == 4) {
			this.clientSideAttackStarted = true;
		}

	}

	@Environment(EnvType.CLIENT)
	public float getAnimationProgress(float tickDelta) {
		if (!this.clientSideAttackStarted) {
			return 0.0F;
		} else {
			int i = this.lifeTicks - 2;
			return i <= 0 ? 1.0F : 1.0F - ((float) i - tickDelta) / 20.0F;
		}
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return DoomEntityPacket.createPacket(this);
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		return source == DamageSource.IN_WALL || source == DamageSource.ON_FIRE || source == DamageSource.IN_FIRE
				? false
				: super.hurt(source, amount);
	}

}