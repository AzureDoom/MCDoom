package mod.azure.doom.entity.projectiles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

public class CustomSmallFireballEntity extends SmallFireballEntity {

	public CustomSmallFireballEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ,
			float directHitDamage) {
		super(worldIn, shooter, accelX, accelY, accelZ);
		this.directHitDamage = directHitDamage;
	}

	private float directHitDamage = 5.0F;

	@Override
	protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
		if (!this.level.isClientSide) {
			Entity entity = p_213868_1_.getEntity();
			if (!entity.fireImmune()) {
				Entity entity1 = this.getOwner();
				int i = entity.getRemainingFireTicks();
				entity.setSecondsOnFire(5);
				boolean flag = entity.hurt(DamageSource.fireball(this, entity1), directHitDamage);
				if (!flag) {
					entity.setRemainingFireTicks(i);
				} else if (entity1 instanceof LivingEntity) {
					this.doEnchantDamageEffects((LivingEntity) entity1, entity);
				}
			}

		}
	}

}
