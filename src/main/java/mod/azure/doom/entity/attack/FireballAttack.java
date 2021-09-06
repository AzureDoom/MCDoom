package mod.azure.doom.entity.attack;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.projectiles.CustomFireballEntity;
import mod.azure.doom.entity.projectiles.CustomSmallFireballEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

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
		return new AttackSound(SoundEvents.ITEM_FIRECHARGE_USE, 1, 1);
	}

	@Override
	public ProjectileEntity getProjectile(World world, double d2, double d3, double d4) {
		return largeFireball ? new CustomFireballEntity(world, this.parentEntity, d2, d3, d4, damage)
				: new CustomSmallFireballEntity(world, this.parentEntity, d2, d3, d4, damage);

	}

}
