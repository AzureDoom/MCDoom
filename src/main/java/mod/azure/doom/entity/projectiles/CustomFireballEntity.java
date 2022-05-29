package mod.azure.doom.entity.projectiles;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

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
			if (!(entity2 instanceof DemonEntity))
				entity.damage(DamageSource.magic(this, entity2), directHitDamage);
			if (entity2 instanceof LivingEntity) {
				if (!(entity2 instanceof DemonEntity))
					this.applyDamageEffects((LivingEntity) entity2, entity);
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
	}

	@Override
	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (!this.world.isClient) {
			boolean bl = DoomConfig.enable_block_breaking;
			this.world.createExplosion(null, this.getX(), this.getY(), this.getZ(), 1, true,
					bl ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE);
			this.discard();
		}
	}
}
