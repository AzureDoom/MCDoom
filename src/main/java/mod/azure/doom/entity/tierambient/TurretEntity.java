package mod.azure.doom.entity.tierambient;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.projectiles.CustomSmallFireballEntity;
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

public class TurretEntity extends DemonEntity implements GeoEntity {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public TurretEntity(EntityType<TurretEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			if (entityData.get(STATE) == 1 && !(dead || getHealth() < 0.01 || isDeadOrDying()))
				return event.setAndContinue(RawAnimation.begin().thenLoop("attacking"));
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	public static AttributeSupplier.Builder createMobAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D).add(Attributes.MAX_HEALTH, DoomConfig.turret_health).add(Attributes.ATTACK_DAMAGE, 0.0D).add(Attributes.KNOCKBACK_RESISTANCE, 1.0f).add(Attributes.MOVEMENT_SPEED, 0.0D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
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
	protected void registerGoals() {
		goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, AbstractVillager.class, 8.0F));
		goalSelector.addGoal(1, new TurretEntity.AttackGoal(this));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers());
	}

	static class AttackGoal extends Goal {
		private final TurretEntity parentEntity;
		protected int attackTimer = 0;

		public AttackGoal(TurretEntity ghast) {
			parentEntity = ghast;
		}

		@Override
		public boolean canUse() {
			return parentEntity.getTarget() != null;
		}

		@Override
		public void start() {
			super.start();
			parentEntity.setAggressive(true);
		}

		@Override
		public void stop() {
			super.stop();
			parentEntity.setAggressive(false);
			parentEntity.setAttackingState(0);
			attackTimer = -1;
		}

		@Override
		public void tick() {
			final var livingentity = parentEntity.getTarget();
			if (parentEntity.hasLineOfSight(livingentity)) {
				final var world = parentEntity.level;
				++attackTimer;
				final var vector3d = parentEntity.getViewVector(1.0F);
				final var x = livingentity.getX() - (parentEntity.getX() + vector3d.x * 2.0D);
				final var y = livingentity.getY(0.5D) - (0.5D + parentEntity.getY(0.5D));
				final var z = livingentity.getZ() - (parentEntity.getZ() + vector3d.z * 2.0D);
				final var fireballentity = new CustomSmallFireballEntity(world, parentEntity, x, y, z, DoomConfig.turret_ranged_damage);
				if (attackTimer == 10)
					parentEntity.setAttackingState(1);
				if (attackTimer == 20) {
					fireballentity.setPos(parentEntity.getX() + vector3d.x, parentEntity.getY(0.5D) + 0.5D, fireballentity.getZ() + vector3d.z);
					world.addFreshEntity(fireballentity);
				}
				if (attackTimer >= 30) {
					parentEntity.setAttackingState(0);
					attackTimer = -40;
				}
			} else if (attackTimer > 0) {
				--attackTimer;
			}
			parentEntity.lookAt(livingentity, 30.0F, 30.0F);
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
