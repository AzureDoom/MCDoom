package mod.azure.doom.entity.tierambient;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TentacleEntity extends DemonEntity implements GeoEntity {

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public TentacleEntity(EntityType<TentacleEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			if (this.entityData.get(STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenLoop("attacking"));
			if (this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.SERVER.tentacle_health.get()).add(Attributes.ATTACK_DAMAGE, 0.0D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 1.0f).add(Attributes.MOVEMENT_SPEED, 0.0D)
				.add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 30) {
			this.remove(RemovalReason.KILLED);
			this.dropExperience();
		}
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void pushEntities() {
	}

	@Override
	public void knockback(double strength, double x, double z) {
		super.knockback(0, 0, 0);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, AbstractVillager.class, 8.0F));
		this.goalSelector.addGoal(9, new TentacleEntity.AttackGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this).setAlertOthers()));
	}

	static class AttackGoal extends Goal {
		private final TentacleEntity entity;
		public int cooldown;

		public AttackGoal(TentacleEntity parentEntity) {
			this.entity = parentEntity;
		}

		public boolean canUse() {
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
				this.entity.lookAt(livingentity, 30.0F, 90.0F);
				final AABB aabb2 = new AABB(this.entity.blockPosition()).inflate(2D);
				if (this.entity.hasLineOfSight(livingentity)) {
					++this.cooldown;
					if (this.entity.getCommandSenderWorld().getEntities(this.entity, aabb2).contains(livingentity)) {
						if (this.cooldown == 2) {
							this.entity.getCommandSenderWorld().getEntities(this.entity, aabb2).forEach(e -> {
								if ((e instanceof LivingEntity)) {
									e.hurt(DamageSource.indirectMagic(this.entity, livingentity),
											DoomConfig.SERVER.tentacle_melee_damage.get().floatValue());
									livingentity.invulnerableTime = 0;
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

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

}
