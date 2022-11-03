package mod.azure.doom.entity.ai.goal;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.attack.AbstractRangedAttack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class RangedStaticAttackGoal extends Goal {

	private final DemonEntity parentEntity;
	public int attackTimer;
	private AbstractRangedAttack attack;
	private int seeTime = -1;
	private int statecheck;

	public RangedStaticAttackGoal(DemonEntity mob, AbstractRangedAttack attack, int state) {
		this.parentEntity = mob;
		this.attack = attack;
		this.statecheck = state;
	}

	public boolean canUse() {
		return this.parentEntity.getTarget() != null;
	}

	public void start() {
		this.attackTimer = 0;
	}

	public void stop() {
		this.parentEntity.setAttackingState(0);
	}

	public void tick() {
		if (this.parentEntity.getTarget() != null) {
			LivingEntity livingentity = this.parentEntity.getTarget();
			this.parentEntity.lookAt(livingentity, 30.0F, 30.0F);
			boolean inLineOfSight = this.parentEntity.getSensing().hasLineOfSight(livingentity);
			if (inLineOfSight != this.seeTime > 0) {
				this.seeTime = 0;
			}
			if (inLineOfSight) {
				++this.seeTime;
			} else {
				--this.seeTime;
			}
			this.parentEntity.getNavigation().moveTo(livingentity, 0.95F);
			this.parentEntity.getMoveControl().strafe(0.5F, -0.5F);
			this.attackTimer++;
			if (this.attackTimer == 1) {
				this.parentEntity.setAttackingState(statecheck);
			}
			if (this.attackTimer == 4) {
					this.attack.shoot();

					boolean isInsideWaterBlock = parentEntity.level.isWaterAt(parentEntity.blockPosition());
					parentEntity.spawnLightSource(this.parentEntity, isInsideWaterBlock);
			}
			if (this.attackTimer >= 8) {
				this.parentEntity.setAttackingState(0);
				this.attackTimer = -5;
			}
		}
	}

}
