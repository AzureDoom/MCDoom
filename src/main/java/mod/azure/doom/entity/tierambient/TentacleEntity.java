package mod.azure.doom.entity.tierambient;

import java.util.Random;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.tierfodder.PossessedScientistEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class TentacleEntity extends DemonEntity implements IAnimatable, IAnimationTickable {

	private AnimationFactory factory = new AnimationFactory(this);

	public TentacleEntity(EntityType<? extends DemonEntity> type, World worldIn) {
		super(type, worldIn);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		if ((this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("death", false));
			return PlayState.CONTINUE;
		}
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
		if (this.dataTracker.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDead())) {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("attacking", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}

	@Override
	public int tickTimer() {
		return age;
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
	protected void updatePostDeath() {
		++this.deathTime;
		if (this.deathTime == 30) {
			this.remove(Entity.RemovalReason.KILLED);
			this.dropXp();
		}
	}

	public static boolean spawning(EntityType<PossessedScientistEntity> p_223337_0_, World p_223337_1_,
			SpawnReason reason, BlockPos p_223337_3_, Random p_223337_4_) {
		return p_223337_1_.getDifficulty() != Difficulty.PEACEFUL;
	}

	@Override
	public void takeKnockback(double strength, double x, double z) {
		super.takeKnockback(0, 0, 0);
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void tickCramming() {
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(8, new LookAtEntityGoal(this, MerchantEntity.class, 8.0F));
		this.goalSelector.add(9, new TentacleEntity.AttackGoal(this));
		this.targetSelector.add(2, new TargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new TargetGoal<>(this, MerchantEntity.class, true));
		this.targetSelector.add(2, new RevengeGoal(this).setGroupRevenge());
	}

	static class AttackGoal extends Goal {
		private final TentacleEntity entity;
		public int cooldown;

		public AttackGoal(TentacleEntity parentEntity) {
			this.entity = parentEntity;
		}

		public boolean canStart() {
			return this.entity.getTarget() != null;
		}

		public void start() {
			this.cooldown = 0;
			this.entity.setAttackingState(0);
		}

		@Override
		public void stop() {
			super.stop();
			this.entity.setAttackingState(0);
		}

		public void tick() {
			LivingEntity livingentity = this.entity.getTarget();
			if (livingentity != null) {
				this.entity.lookAtEntity(livingentity, 30.0F, 90.0F);
				final Box aabb2 = new Box(this.entity.getBlockPos()).expand(2D);
				if (this.entity.canSee(livingentity)) {
					++this.cooldown;
					if (this.entity.getCommandSenderWorld().getOtherEntities(this.entity, aabb2).contains(livingentity)) {
						if (this.cooldown == 2) {
							this.entity.getCommandSenderWorld().getOtherEntities(this.entity, aabb2).forEach(e -> {
								if ((e instanceof LivingEntity)) {
									e.damage(DamageSource.magic(this.entity, livingentity), DoomConfig.tentacle_melee_damage);
									livingentity.timeUntilRegen = 0;
								}
							});
							this.entity.setAttackingState(1);
						}
						if (this.cooldown >= 10) {
							this.entity.setAttackingState(0);
							this.cooldown = -5;
						}
					} else {
						--this.cooldown;
						this.entity.setAttackingState(0);
					}
				} else if (this.cooldown > 0) {
					--this.cooldown;
					this.entity.setAttackingState(0);
				}
			}
		}
	}

	public static DefaultAttributeContainer.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 5.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0f)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0D)
				.add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.0D);
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

}
