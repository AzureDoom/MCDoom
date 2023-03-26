package mod.azure.doom.entity.projectiles.entity;

import mod.azure.doom.entity.DemonEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class CustomSmallFireballEntity extends SmallFireball {

	private float directHitDamage = 5.0F;

	public CustomSmallFireballEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ, float directHitDamage) {
		super(worldIn, shooter, accelX, accelY, accelZ);
		this.directHitDamage = directHitDamage;
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		if (!this.level.isClientSide()) {
			var entity = entityHitResult.getEntity();
			if (!entity.fireImmune()) {
				var entity2 = this.getOwner();
				entity.setSecondsOnFire(5);
				if (!(entity2 instanceof DemonEntity))
					entity.hurt(damageSources().fireball(this, entity2), directHitDamage);
				if (entity2 instanceof LivingEntity) {
					if (!(entity2 instanceof DemonEntity))
						this.doEnchantDamageEffects((LivingEntity) entity2, entity);
					this.remove(Entity.RemovalReason.DISCARDED);
				}
			}
		}
	}

}
