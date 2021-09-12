package mod.azure.doom.entity.tierambient;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.config.Config;
import mod.azure.doom.util.config.EntityConfig;
import mod.azure.doom.util.config.EntityDefaults.EntityConfigType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class TentacleEntity extends DemonEntity implements IAnimatable {

	private AnimationFactory factory = new AnimationFactory(this);
	public static EntityConfig config = Config.SERVER.entityConfig.get(EntityConfigType.TENTACLE);

	public TentacleEntity(EntityType<TentacleEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return config.pushAttributes(Mob.createMobAttributes().add(Attributes.FOLLOW_RANGE, 35.0D)
				.add(Attributes.ATTACK_DAMAGE, 1.0D));
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 30) {
			this.remove();
		}
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if ((this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.entityData.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<TentacleEntity>(this, "controller", 0, this::predicate));
		data.addAnimationController(new AnimationController<TentacleEntity>(this, "controller1", 0, this::predicate1));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public static boolean spawning(EntityType<TentacleEntity> p_223337_0_, LevelAccessor p_223337_1_, MobSpawnType reason,
			BlockPos p_223337_3_, Random p_223337_4_) {
		return passPeacefulAndYCheck(config, p_223337_1_, reason, p_223337_3_, p_223337_4_);
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void pushEntities() {
	}

	@Override
	public void knockback(float strength, double ratioX, double ratioZ) {
		super.knockback(0, 0, 0);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, AbstractVillager.class, 8.0F));
		this.goalSelector.addGoal(9, new TentacleEntity.AttackGoal(this, 15));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this).setAlertOthers()));
	}

	static class AttackGoal extends Goal {
		private final TentacleEntity parentEntity;
		public int attackTimer;

		public AttackGoal(TentacleEntity ghast, int attackCooldownIn) {
			this.parentEntity = ghast;
		}

		public boolean canUse() {
			return this.parentEntity.getTarget() != null;
		}

		public void start() {
			this.attackTimer = 0;
		}

		@Override
		public void stop() {
			super.stop();
			this.parentEntity.setAttackingState(0);
		}

		public void tick() {
			LivingEntity livingentity = this.parentEntity.getTarget();
			if (livingentity != null) {
				if (this.parentEntity.canSee(livingentity)) {
					if (parentEntity.distanceTo(livingentity) < 3.0D) {
						++this.attackTimer;
						if (this.attackTimer == 15) {
							this.parentEntity.doDamage();
							this.parentEntity.setAttackingState(1);
						}
						if (this.attackTimer == 40) {
							this.parentEntity.setAttackingState(0);
							this.attackTimer = -45;
						}
					}
				} else if (this.attackTimer > 0) {
					--this.attackTimer;
				}
			}
		}

	}

	public void doDamage() {
		float f2 = 4.0F;
		int k1 = Mth.floor(this.getX() - (double) f2 - 1.0D);
		int l1 = Mth.floor(this.getX() + (double) f2 + 1.0D);
		int i2 = Mth.floor(this.getY() - (double) f2 - 1.0D);
		int i1 = Mth.floor(this.getY() + (double) f2 + 1.0D);
		int j2 = Mth.floor(this.getZ() - (double) f2 - 1.0D);
		int j1 = Mth.floor(this.getZ() + (double) f2 + 1.0D);
		List<Entity> list = this.level.getEntities(this,
				new AABB((double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));
		Vec3 vector3d = new Vec3(this.getX(), this.getY(), this.getZ());
		for (int k2 = 0; k2 < list.size(); ++k2) {
			Entity entity = list.get(k2);
			double d12 = (double) (Mth.sqrt(entity.distanceToSqr(vector3d)) / f2);
			if (d12 <= 2.0D) {
				if (entity instanceof LivingEntity) {
					entity.hurt(DamageSource.indirectMagic(this, this.getTarget()), 1);
				}
			}
		}
	}

	@Nullable
	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
			@Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
		return spawnDataIn;
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	public CreatureAttribute getMobType() {
		return CreatureAttribute.UNDEAD;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

}
