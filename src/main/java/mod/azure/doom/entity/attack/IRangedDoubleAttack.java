
package mod.azure.doom.entity.attack;

import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public interface IRangedDoubleAttack {

	public Projectile getProjectile(Level world, double d2, double d3, double d4);

	public Projectile getProjectile2(Level world, double d2, double d3, double d4);

	public AttackSound getDefaultAttackSound();

}