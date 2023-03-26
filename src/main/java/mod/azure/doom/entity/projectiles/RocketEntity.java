package mod.azure.doom.entity.projectiles;

import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.entities.TickingLightEntity;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomProjectiles;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class RocketEntity extends AbstractArrow implements GeoEntity {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private LivingEntity shooter;
	private float projectiledamage;
	private BlockPos lightBlockPos = null;
	private int idleTicks = 0;
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	public SoundEvent hitSound = getDefaultHitGroundSoundEvent();

	public RocketEntity(EntityType<? extends RocketEntity> entityType, Level world) {
		super(entityType, world);
		pickup = AbstractArrow.Pickup.DISALLOWED;
	}

	public RocketEntity(Level world, LivingEntity owner) {
		super(DoomProjectiles.ROCKET, owner, world);
		shooter = owner;
	}

	public RocketEntity(Level world, LivingEntity owner, float damage) {
		super(DoomProjectiles.ROCKET, owner, world);
		shooter = owner;
		projectiledamage = damage;
	}

	protected RocketEntity(EntityType<? extends RocketEntity> type, double x, double y, double z, Level world) {
		this(type, world);
	}

	protected RocketEntity(EntityType<? extends RocketEntity> type, LivingEntity owner, Level world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		setOwner(owner);
		if (owner instanceof Player)
			pickup = AbstractArrow.Pickup.DISALLOWED;
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
	protected void doPostHurtEffects(LivingEntity living) {
		super.doPostHurtEffects(living);
		if (!(living instanceof Player) && !(living instanceof IconofsinEntity)) {
			living.setDeltaMovement(0, 0, 0);
			living.invulnerableTime = 0;
		}
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	protected void tickDespawn() {
		++ticksInAir;
		if (tickCount >= 40)
			remove(RemovalReason.KILLED);
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		super.shoot(x, y, z, velocity, inaccuracy);
		ticksInAir = 0;
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
		if (ticksInAir >= 80)
			remove(Entity.RemovalReason.DISCARDED);
		final var isInsideWaterBlock = level.isWaterAt(blockPosition());
		spawnLightSource(isInsideWaterBlock);
		if (level.isClientSide())
			level.addParticle(ParticleTypes.SMOKE, true, this.getX() + random.nextDouble() * getBbWidth() * 0.5D, this.getY(), this.getZ() + random.nextDouble() * getBbWidth() * 0.5D, 0, 0, 0);
	}

	private void spawnLightSource(boolean isInWaterBlock) {
		if (lightBlockPos == null) {
			lightBlockPos = findFreeSpace(level, blockPosition(), 2);
			if (lightBlockPos == null)
				return;
			level.setBlockAndUpdate(lightBlockPos, AzureLibMod.TICKING_LIGHT_BLOCK.defaultBlockState());
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
					if (state.isAir() || state.getBlock().equals(AzureLibMod.TICKING_LIGHT_BLOCK))
						return offsetPos;
				}

		return null;
	}

	public void initFromStack(ItemStack stack) {
		if (stack.getItem() == DoomItems.ROCKET) {
		}
	}

	@Override
	public boolean isNoGravity() {
		if (isInWater())
			return false;
		return true;
	}

	@Override
	public void setSoundEvent(SoundEvent soundIn) {
		hitSound = soundIn;
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return DoomSounds.ROCKET_HIT;
	}

	@Override
	public void remove(RemovalReason reason) {
		final var areaeffectcloudentity = new AreaEffectCloud(level, this.getX(), this.getY(), this.getZ());
		areaeffectcloudentity.setParticle(ParticleTypes.LAVA);
		areaeffectcloudentity.setRadius(6);
		areaeffectcloudentity.setDuration(1);
		areaeffectcloudentity.absMoveTo(this.getX(), this.getY(), this.getZ());
		level.addFreshEntity(areaeffectcloudentity);
		level.playSound((Player) null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0F, 1.5F);
		super.remove(reason);
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		if (!level.isClientSide()) {
			doDamage();
			remove(Entity.RemovalReason.DISCARDED);
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		if (!level.isClientSide()) {
			doDamage();
			remove(Entity.RemovalReason.DISCARDED);
		}
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(DoomItems.ROCKET);
	}

	public void doDamage() {
		level.getEntities(this, new AABB(blockPosition().above()).inflate(4)).forEach(e -> {
			if (e instanceof LivingEntity)
				e.hurt(damageSources().playerAttack((Player) shooter), projectiledamage);
			level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 0.0F, Level.ExplosionInteraction.NONE);
		});

	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

}