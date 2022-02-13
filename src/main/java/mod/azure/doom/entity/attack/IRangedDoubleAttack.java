
package mod.azure.doom.entity.attack;

import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;

public interface IRangedDoubleAttack {

	public ProjectileEntity getProjectile(World world, double d2, double d3, double d4);
	
	public ProjectileEntity getProjectile2(World world, double d2, double d3, double d4);

	public AttackSound getDefaultAttackSound();

}