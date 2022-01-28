package mod.azure.doom.entity.projectiles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class CustomFireballEntity extends FireballEntity {

	private float directHitDamage = 6.0F;

	public CustomFireballEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ,
			float directHitDamage) {
		super(worldIn, shooter, accelX, accelY, accelZ, 1);
		this.directHitDamage = directHitDamage;
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		if (!this.world.isClient) {
			Entity entity = entityHitResult.getEntity();
			Entity entity2 = this.getOwner();
			entity.setOnFireFor(5);
			entity.damage(DamageSource.fireball(this, entity2), directHitDamage);
			if (entity2 instanceof LivingEntity) {
				this.applyDamageEffects((LivingEntity) entity2, entity);
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
	}
}
