package mod.azure.doom.entity.attack;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.projectiles.entity.CustomFireballEntity;
import mod.azure.doom.entity.projectiles.entity.CustomSmallFireballEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;

public class FireballAttack extends AbstractRangedAttack {

	public boolean largeFireball;

	public FireballAttack(DemonEntity parentEntity, boolean largeFireball, double xOffSetModifier,
			double entityHeightFraction, double zOffSetModifier, float damage) {
		super(parentEntity, xOffSetModifier, entityHeightFraction, zOffSetModifier, damage);
		this.largeFireball = largeFireball;
	}

	public FireballAttack(DemonEntity parentEntity, boolean largeFireball) {
		super(parentEntity);
		this.largeFireball = largeFireball;
	}

	@Override
	public AttackSound getDefaultAttackSound() {
		return new AttackSound(SoundEvents.FIRECHARGE_USE, 1, 1);
	}

	@Override
	public Projectile getProjectile(Level world, double d2, double d3, double d4) {
		return largeFireball ? new CustomFireballEntity(world, this.parentEntity, d2, d3, d4, damage)
				: new CustomSmallFireballEntity(world, this.parentEntity, d2, d3, d4, damage);

	}

}
