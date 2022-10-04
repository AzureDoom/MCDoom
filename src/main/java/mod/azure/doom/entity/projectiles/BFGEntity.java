package mod.azure.doom.entity.projectiles;

import java.util.List;
import java.util.Random;

import org.jetbrains.annotations.Nullable;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.tierambient.GoreNestEntity;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
import mod.azure.doom.entity.tierboss.GladiatorEntity;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import mod.azure.doom.entity.tileentity.TickingLightEntity;
import mod.azure.doom.network.DoomEntityPacket;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomSounds;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BFGEntity extends PersistentProjectileEntity implements IAnimatable {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private static final TrackedData<Integer> BEAM_TARGET_ID = DataTracker.registerData(BFGEntity.class,
			TrackedDataHandlerRegistry.INTEGER);
	private LivingEntity cachedBeamTarget;
	private LivingEntity shooter;
	private BlockPos lightBlockPos = null;
	private int idleTicks = 0;
	Random rand = new Random();
	List<? extends String> whitelistEntries = DoomConfig.bfg_damage_mob_whitelist;
	int randomIndex = rand.nextInt(whitelistEntries.size());
	Identifier randomElement1 = new Identifier(whitelistEntries.get(randomIndex));
	EntityType<?> randomElement = Registry.ENTITY_TYPE.get(randomElement1);

	public BFGEntity(EntityType<? extends BFGEntity> entityType, World world) {
		super(entityType, world);
		this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
	}

	public BFGEntity(World world, LivingEntity owner) {
		super(ProjectilesEntityRegister.BFG_CELL, owner, world);
		this.shooter = owner;
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<BFGEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	protected BFGEntity(EntityType<? extends BFGEntity> type, double x, double y, double z, World world) {
		this(type, world);
	}

	protected BFGEntity(EntityType<? extends BFGEntity> type, LivingEntity owner, World world) {
		this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
		this.setOwner(owner);
		if (owner instanceof PlayerEntity) {
			this.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
		}

	}

	@Override
	public Packet<?> createSpawnPacket() {
		return DoomEntityPacket.createPacket(this);
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
		boolean isInsideWaterBlock = world.isWater(getBlockPos());
		spawnLightSource(isInsideWaterBlock);
		if (this.age >= 80) {
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		final Box aabb = new Box(this.getBlockPos().up()).expand(24D, 24D, 24D);
		this.getCommandSenderWorld().getOtherEntities(this, aabb).forEach(e -> {
			Entity listEntity = randomElement.downcast(e);
			if (!(e instanceof PlayerEntity || e instanceof EnderDragonEntity || e instanceof GoreNestEntity
					|| e instanceof IconofsinEntity || e instanceof ArchMakyrEntity || e instanceof GladiatorEntity
					|| e instanceof MotherDemonEntity)
					&& (e instanceof HostileEntity || e instanceof SlimeEntity || e instanceof PhantomEntity || e instanceof DemonEntity
							|| e instanceof ShulkerEntity || e instanceof HoglinEntity || (e == listEntity))) {
				if (e.isAlive()) {
					e.damage(DamageSource.explosion(shooter), DoomConfig.bfgball_damage_aoe);
					this.setBeamTarget(e.getId());
				}
			}
			if (e instanceof EnderDragonEntity) {
				if (e.isAlive()) {
					((EnderDragonEntity) e).head.damage(DamageSource.player((PlayerEntity) this.shooter),
							DoomConfig.bfgball_damage_dragon * 0.3F);
					this.setBeamTarget(e.getId());
				}
			}
			if (e instanceof IconofsinEntity || e instanceof ArchMakyrEntity || e instanceof GladiatorEntity
					|| e instanceof MotherDemonEntity) {
				if (e.isAlive()) {
					e.damage(DamageSource.player((PlayerEntity) this.shooter),
							DoomConfig.bfgball_damage_aoe * 0.1F);
				}
			}
		});
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
		if (stack.getItem() == DoomItems.BFG_CELL) {
		}
	}

	@Override
	public ItemStack asItemStack() {
		return new ItemStack(DoomItems.BFG_CELL);
	}

	@Override
	public boolean hasNoGravity() {
		if (this.isSubmergedInWater()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		if (!this.world.isClient) {
			this.doDamage();
			this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 1.0F, false,
					DoomConfig.enable_block_breaking ? Explosion.DestructionType.BREAK
							: Explosion.DestructionType.NONE);
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		this.playSound(DoomSounds.BFG_HIT, 1.0F, 1.0F);
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		if (!this.world.isClient) {
			this.doDamage();
			this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 1.0F, false,
					DoomConfig.enable_block_breaking ? Explosion.DestructionType.BREAK
							: Explosion.DestructionType.NONE);
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		this.playSound(DoomSounds.BFG_HIT, 1.0F, 1.0F);
	}

	public void doDamage() {
		final Box aabb = new Box(this.getBlockPos().up()).expand(24D, 24D, 24D);
		this.getCommandSenderWorld().getOtherEntities(this, aabb).forEach(e -> {
			Entity listEntity = randomElement.downcast(e);
			if (!(e instanceof PlayerEntity || e instanceof EnderDragonEntity || e instanceof GoreNestEntity
					|| e instanceof IconofsinEntity || e instanceof ArchMakyrEntity || e instanceof GladiatorEntity
					|| e instanceof MotherDemonEntity)
					&& (e instanceof HostileEntity || e instanceof SlimeEntity || e instanceof PhantomEntity
							|| e instanceof DemonEntity || e instanceof ShulkerEntity || e instanceof HoglinEntity
							|| (e == listEntity))) {
				e.damage(DamageSource.player((PlayerEntity) this.shooter), DoomConfig.bfgball_damage);
				this.setBeamTarget(e.getId());
				if (!this.world.isClient()) {
					List<LivingEntity> list1 = this.world.getNonSpectatingEntities(LivingEntity.class,
							this.getBoundingBox().expand(4.0D, 2.0D, 4.0D));
					AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(e.world, e.getX(), e.getY(),
							e.getZ());
					areaeffectcloudentity.setParticleType(ParticleTypes.TOTEM_OF_UNDYING);
					areaeffectcloudentity.setRadius(3.0F);
					areaeffectcloudentity.setDuration(10);
					if (!list1.isEmpty()) {
						for (LivingEntity livingentity : list1) {
							double d0 = this.squaredDistanceTo(livingentity);
							if (d0 < 16.0D) {
								areaeffectcloudentity.setPos(e.getX(), e.getEyeY(), e.getZ());
							}
						}
					}
					e.world.spawnEntity(areaeffectcloudentity);
				}
			}
			if (e instanceof EnderDragonEntity) {
				if (e.isAlive()) {
					((EnderDragonEntity) e).head.damage(DamageSource.player((PlayerEntity) this.shooter),
							DoomConfig.bfgball_damage_dragon * 0.3F);
				}
			}
			if (e instanceof IconofsinEntity || e instanceof ArchMakyrEntity || e instanceof GladiatorEntity
					|| e instanceof MotherDemonEntity) {
				if (e.isAlive()) {
					e.damage(DamageSource.player((PlayerEntity) this.shooter), DoomConfig.bfgball_damage * 0.1F);
				}
			}
		});
	}

	@Override
	public boolean shouldRender(double distance) {
		return true;
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(BEAM_TARGET_ID, 0);
	}

	private void setBeamTarget(int entityId) {
		this.dataTracker.set(BEAM_TARGET_ID, entityId);
	}

	public boolean hasBeamTarget() {
		return (Integer) this.dataTracker.get(BEAM_TARGET_ID) != 0;
	}

	@Nullable
	public LivingEntity getBeamTarget() {
		if (!this.hasBeamTarget()) {
			return null;
		} else if (this.world.isClient) {
			if (this.cachedBeamTarget != null) {
				return this.cachedBeamTarget;
			} else {
				Entity entity = this.world.getEntityById((Integer) this.dataTracker.get(BEAM_TARGET_ID));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget = (LivingEntity) entity;
					return this.cachedBeamTarget;
				} else {
					return null;
				}
			}
		} else {
			return this.getTarget();
		}
	}

	@Override
	public void onTrackedDataSet(TrackedData<?> data) {
		super.onTrackedDataSet(data);
		if (BEAM_TARGET_ID.equals(data)) {
			this.cachedBeamTarget = null;
		}
	}

	@Nullable
	public LivingEntity getTarget() {
		return this.cachedBeamTarget;
	}

	@Override
	public boolean doesRenderOnFire() {
		return false;
	}
}