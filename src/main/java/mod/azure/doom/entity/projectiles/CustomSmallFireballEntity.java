package mod.azure.doom.entity.projectiles;

import mod.azure.doom.entity.DemonEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class CustomSmallFireballEntity extends SmallFireballEntity {

	private float directHitDamage = 5.0F;

	public CustomSmallFireballEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ,
			float directHitDamage) {
		super(worldIn, shooter, accelX, accelY, accelZ);
		this.directHitDamage = directHitDamage;
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		if (!this.world.isClient) {
			Entity entity = entityHitResult.getEntity();
			if (!entity.isFireImmune()) {
				Entity entity2 = this.getOwner();
				entity.setOnFireFor(5);
				if (!(entity2 instanceof DemonEntity))
					entity.damage(DamageSource.fireball(this, entity2), directHitDamage);
				if (entity2 instanceof LivingEntity) {
					if (!(entity2 instanceof DemonEntity))
						this.applyDamageEffects((LivingEntity) entity2, entity);
					this.remove(Entity.RemovalReason.DISCARDED);
				}
			}
		}
	}

}
