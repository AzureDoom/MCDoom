package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;
import java.util.Random;
import java.util.SplittableRandom;

import mod.azure.doom.entity.attack.AbstractRangedAttack;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class IconAttackGoal extends Goal {
	private final IconofsinEntity entity;
	private double moveSpeedAmp = 1;
	private int attackTime = -1;
	private int summonTime = -1;
	private AbstractRangedAttack attack;

	public IconAttackGoal(IconofsinEntity mob, AbstractRangedAttack attack, double moveSpeedAmpIn) {
		this.entity = mob;
		this.moveSpeedAmp = moveSpeedAmpIn;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		this.attack = attack;
	}

	public boolean canUse() {
		return this.entity.getTarget() != null;
	}

	public boolean canContinueToUse() {
		return this.canUse();
	}

	public void start() {
		super.start();
		this.entity.setAggressive(true);
	}

	public void stop() {
		super.stop();
		this.entity.setAggressive(false);
		this.entity.setAttackingState(0);
		this.attackTime = -1;
	}

	public void tick() {
		LivingEntity livingentity = this.entity.getTarget();
		if (livingentity != null) {
			this.attackTime++;
			this.entity.lookAt(livingentity, 30.0F, 30.0F);
			this.entity.getNavigation().moveTo(livingentity, this.moveSpeedAmp);
			SplittableRandom random = new SplittableRandom();
			Random rand = new Random();
			float f = (float) Mth.atan2(livingentity.getZ() - entity.getZ(), livingentity.getX() - entity.getX());
			double d0 = Math.min(livingentity.getY(), livingentity.getY());
			double d1 = Math.max(livingentity.getY(), livingentity.getY()) + 1.0D;
			double d2 = this.entity.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
			double d3 = this.getAttackReachSqr(livingentity);
			if (this.attackTime == 1) {
				this.entity.setAttackingState(0);
				this.summonTime++;
			}
			if (this.attackTime == 4) {
				if (this.summonTime == 10) {
					entity.spawnWave(random.nextInt(0, 11), livingentity); // Summons roughly 2 minutes
					this.summonTime = -300;
				}
				if (d2 > d3) { // distance check
					if (livingentity.getSpeed() <= 0) { // Summon Fire on target
						for (int i = 1; i < 5; ++i) {
							float f1 = f + (float) i * (float) Math.PI * 0.4F;
							for (int y = 0; y < 5; ++y) {
								entity.spawnFlames(
										livingentity.getX() + (double) Mth.cos(f1) * rand.nextDouble() * 1.5D,
										livingentity.getZ() + (double) Mth.sin(f1) * rand.nextDouble() * 1.5D, d0, d1,
										f1, 0);
							}
						}
						if (entity.getHealth() < (entity.getMaxHealth() * 0.50)) {
							this.entity.setAttackingState(6); // no armor
						} else {
							this.entity.setAttackingState(5); // armor
						}
					} else if (livingentity.getSpeed() >= 1) { // shoots fireball
						this.attack.shoot();
						if (entity.getHealth() < (entity.getMaxHealth() * 0.50)) {
							this.entity.setAttackingState(2); // no armor
						} else {
							this.entity.setAttackingState(1); // armor
						}
					}
				} else { // melee if in range to melee
					if (entity.getHealth() < (entity.getMaxHealth() * 0.50)) {
						this.entity.setAttackingState(4); // no armor
					} else {
						this.entity.setAttackingState(3); // armor
					}
					if (d2 <= d3) {
						this.entity.doHurtTarget(livingentity);
					}
					livingentity.invulnerableTime = 0;
				}
			}
			if (this.attackTime == 8) {
				this.entity.setAttackingState(0);
			}
			if (this.attackTime == 25) {
				this.attackTime = -15;
			}
		}
	}

	protected double getAttackReachSqr(LivingEntity attackTarget) {
		return (double) (this.entity.getBbWidth() * 2.0F * this.entity.getBbWidth() * 2.0F + attackTarget.getBbWidth());
	}
}