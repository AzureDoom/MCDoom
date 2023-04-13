package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;
import java.util.SplittableRandom;

import mod.azure.doom.entity.attack.AbstractRangedAttack;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

public class IconAttackGoal extends Goal {
	private final IconofsinEntity entity;
	private double moveSpeedAmp = 1;
	private int attackTime = -1;
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
			int randomAttack = random.nextInt(0, 4);
			if (this.attackTime == 4) {
				if (randomAttack == 1) {// Summon Fire on target
					for (var i = 1; i < 5; ++i) {
						var f1 = (float) Mth.atan2(livingentity.getZ() - entity.getZ(), livingentity.getX() - entity.getX()) + (float) i * (float) Math.PI * 0.4F;
						for (var y = 0; y < 5; ++y) {
							entity.spawnFlames(livingentity.getX() + (double) Mth.cos(f1) * livingentity.getRandom().nextDouble() * 1.5D, livingentity.getZ() + (double) Mth.sin(f1) * livingentity.getRandom().nextDouble() * 1.5D, Math.min(livingentity.getY(), livingentity.getY()), Math.max(livingentity.getY(), livingentity.getY()) + 1.0D, f1, 0);
						}
					}
					if (entity.getHealth() < (entity.getMaxHealth() * 0.50))
						this.entity.setAttackingState(6); // no armor
					else
						this.entity.setAttackingState(5); // armor
				} else if (randomAttack == 2) { // shoots fireball
					this.attack.shoot();
					if (entity.getHealth() < (entity.getMaxHealth() * 0.50)) 
						this.entity.setAttackingState(2); // no armor
					else 
						this.entity.setAttackingState(1); // armor
				} else { // melee if in range to melee
					if (entity.getHealth() < (entity.getMaxHealth() * 0.50)) 
						this.entity.setAttackingState(4); // no armor
					else 
						this.entity.setAttackingState(3); // armor
					this.entity.doHurtTarget(livingentity);
					this.entity.level.explode(this.entity, livingentity.getX(), livingentity.getY(), livingentity.getZ(), 3.0F, false, Level.ExplosionInteraction.BLOCK);
					livingentity.invulnerableTime = 0;
				}
			}
		}
	}

	protected double getAttackReachSqr(LivingEntity attackTarget) {
		return (double) (this.entity.getBbWidth() * 2.0F * this.entity.getBbWidth() * 2.0F + attackTarget.getBbWidth());
	}
}
