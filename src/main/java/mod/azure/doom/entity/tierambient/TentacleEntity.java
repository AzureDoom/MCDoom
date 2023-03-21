package mod.azure.doom.entity.tierambient;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;
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

public class TentacleEntity extends DemonEntity implements GeoEntity {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public TentacleEntity(EntityType<TentacleEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			if (entityData.get(STATE) == 1 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenLoop("attacking"));
			if (dead || getHealth() < 0.01 || isDeadOrDying())
				return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("death"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D).add(Attributes.MAX_HEALTH, DoomConfig.tentacle_health).add(Attributes.ATTACK_DAMAGE, 0.0D).add(Attributes.KNOCKBACK_RESISTANCE, 1.0f).add(Attributes.MOVEMENT_SPEED, 0.0D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	protected void tickDeath() {
		++deathTime;
		if (deathTime == 30) {
			remove(RemovalReason.KILLED);
			dropExperience();
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
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, AbstractVillager.class, 8.0F));
		goalSelector.addGoal(9, new TentacleEntity.AttackGoal(this));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
	}

	static class AttackGoal extends Goal {
		private final TentacleEntity entity;
		public int cooldown;

		public AttackGoal(TentacleEntity parentEntity) {
			entity = parentEntity;
		}

		@Override
		public boolean canUse() {
			return entity.getTarget() != null;
		}

		@Override
		public void start() {
			cooldown = 0;
			entity.setAttackingState(0);
		}

		@Override
		public void stop() {
			super.stop();
			entity.setAttackingState(0);
		}

		@Override
		public void tick() {
			final var livingentity = entity.getTarget();
			if (livingentity != null) {
				entity.lookAt(livingentity, 30.0F, 90.0F);
				final var aabb2 = new AABB(entity.blockPosition()).inflate(2D);
				if (entity.hasLineOfSight(livingentity)) {
					++cooldown;
					if (entity.getCommandSenderWorld().getEntities(entity, aabb2).contains(livingentity)) {
						if (cooldown == 2) {
							entity.getCommandSenderWorld().getEntities(entity, aabb2).forEach(e -> {
								if (e instanceof LivingEntity) {
									e.hurt(DamageSource.indirectMagic(entity, livingentity), DoomConfig.tentacle_melee_damage);
									livingentity.invulnerableTime = 0;
								}
							});
							entity.setAttackingState(1);
						}
						if (cooldown >= 10) {
							entity.setAttackingState(0);
							cooldown = -5;
						}
					} else {
						--cooldown;
						entity.setAttackingState(0);
					}
				} else if (cooldown > 0) {
					--cooldown;
					entity.setAttackingState(0);
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
