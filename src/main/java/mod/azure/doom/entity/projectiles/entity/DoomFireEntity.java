package mod.azure.doom.entity.projectiles.entity;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.registry.DoomProjectiles;
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

public class DoomFireEntity extends Entity implements GeoEntity {

	private int warmupDelayTicks;
	private boolean sentSpikeEvent;
	private int lifeTicks = 75;
	private boolean clientSideAttackStarted;
	private LivingEntity caster;
	private UUID casterUuid;
	private float damage;
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public DoomFireEntity(EntityType<DoomFireEntity> entityType, Level world) {
		super(entityType, world);
		lifeTicks = 75;
	}

	public DoomFireEntity(Level worldIn, double x, double y, double z, float yaw, int warmup, LivingEntity casterIn, float damage) {
		this(DoomProjectiles.FIRING, worldIn);
		warmupDelayTicks = warmup;
		setCaster(casterIn);
		this.absMoveTo(x, y, z);
		this.damage = damage;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> PlayState.CONTINUE));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	protected void defineSynchedData() {
	}

	public void setCaster(@Nullable LivingEntity owner) {
		caster = owner;
		casterUuid = owner == null ? null : owner.getUUID();
	}

	@Nullable
	public LivingEntity getCaster() {
		if (caster == null && casterUuid != null && level instanceof ServerLevel) {
			final var entity = ((ServerLevel) level).getEntity(casterUuid);
			if (entity instanceof LivingEntity)
				caster = (LivingEntity) entity;
		}

		return caster;
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {
		warmupDelayTicks = compound.getInt("Warmup");
		if (compound.hasUUID("Owner"))
			casterUuid = compound.getUUID("Owner");
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
		compound.putInt("Warmup", warmupDelayTicks);
		if (casterUuid != null)
			compound.putUUID("Owner", casterUuid);
	}

	@Override
	public void remove(RemovalReason reason) {
		explode();
		super.remove(reason);
	}

	protected void explode() {
		level.getEntities(this, new AABB(blockPosition().above()).inflate(8)).forEach(e -> doDamage(this, e));
	}

	private void doDamage(Entity user, Entity target) {
		if (target instanceof LivingEntity) {
			if (target instanceof DemonEntity)
				return;

			target.invulnerableTime = 0;
			target.hurt(damageSources().indirectMagic(this, (LivingEntity) target), damage);
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (--warmupDelayTicks < 0) {
			if (!sentSpikeEvent) {
				level.broadcastEntityEvent(this, (byte) 4);
				sentSpikeEvent = true;
			}

			if (--lifeTicks < 0)
				remove(RemovalReason.KILLED);
		}
		if (isAlive() && level.getBlockState(blockPosition().above()).isAir())
			level.setBlockAndUpdate(blockPosition().above(), BaseFireBlock.getState(level, blockPosition().above()));
		level.getEntities(this, new AABB(blockPosition().above()).inflate(1)).forEach(e -> {
			if (e.isAlive() && !(e instanceof DemonEntity)) {
				e.hurt(damageSources().onFire(), damage);
				e.setRemainingFireTicks(60);
			}
		});
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void handleEntityEvent(byte status) {
		super.handleEntityEvent(status);
		if (status == 4)
			clientSideAttackStarted = true;
	}

	@Environment(EnvType.CLIENT)
	public float getAnimationProgress(float tickDelta) {
		if (!clientSideAttackStarted)
			return 0.0F;
		else
			return lifeTicks - 2 <= 0 ? 1.0F : 1.0F - (lifeTicks - 2 - tickDelta) / 20.0F;
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		return source == damageSources().inWall() || source == damageSources().onFire() || source == damageSources().inFire() ? false : super.hurt(source, amount);
	}
	
	@Override
	public boolean displayFireAnimation() {
		return false;
	}

}