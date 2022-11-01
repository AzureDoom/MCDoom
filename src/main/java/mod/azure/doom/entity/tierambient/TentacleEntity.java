package mod.azure.doom.entity.tierambient;

import java.util.List;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
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

	public TentacleEntity(EntityType<TentacleEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	public static AttributeSupplier.Builder createAttributes() {
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
			this.parentEntity.setAttackingState(0);
		}

		@Override
		public void stop() {
			super.stop();
			this.parentEntity.setAttackingState(0);
		}

		public void tick() {
			LivingEntity livingEntity = this.parentEntity.getTarget();
			if (livingEntity != null) {
				if (this.parentEntity.hasLineOfSight(livingEntity) && parentEntity.distanceTo(livingEntity) <= 3.0D) {
					++this.attackTimer;
					if (this.attackTimer == 15) {
						float f2 = 3.0F;
						int k1 = Mth.floor(parentEntity.getX() - (double) f2 - 1.0D);
						int l1 = Mth.floor(parentEntity.getX() + (double) f2 + 1.0D);
						int i2 = Mth.floor(parentEntity.getY() - (double) f2 - 1.0D);
						int i1 = Mth.floor(parentEntity.getY() + (double) f2 + 1.0D);
						int j2 = Mth.floor(parentEntity.getZ() - (double) f2 - 1.0D);
						int j1 = Mth.floor(parentEntity.getZ() + (double) f2 + 1.0D);
						List<Entity> list = parentEntity.level.getEntities(parentEntity,
								new AABB((double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));
						for (int k2 = 0; k2 < list.size(); ++k2) {
							Entity entity = list.get(k2);
							if (entity.isAlive()) {
								this.parentEntity.doDamage();
							}
						}
						this.parentEntity.setAttackingState(1);
					}
					if (this.attackTimer == 40) {
						this.parentEntity.setAttackingState(0);
						this.attackTimer = -45;
					}
				} else if (this.attackTimer > 0) {
					--this.attackTimer;
					this.parentEntity.setAttackingState(0);
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
			double d12 = (double) (Mth.sqrt((float) entity.distanceToSqr(vector3d)) / f2);
			if (d12 <= 2.0D) {
				if (entity instanceof LivingEntity) {
					entity.hurt(DamageSource.indirectMagic(this, this.getTarget()),
							DoomConfig.SERVER.tentacle_melee_damage.get().floatValue());
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
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	public int tickTimer() {
		return tickCount;
	}

}
