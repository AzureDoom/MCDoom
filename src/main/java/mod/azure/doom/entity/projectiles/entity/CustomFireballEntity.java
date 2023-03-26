package mod.azure.doom.entity.projectiles.entity;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class CustomFireballEntity extends LargeFireball {

	private float directHitDamage = 6.0F;

	public CustomFireballEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ, float directHitDamage) {
		super(worldIn, shooter, accelX, accelY, accelZ, 1);
		this.directHitDamage = directHitDamage;
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		if (!this.level.isClientSide()) {
			var entity = entityHitResult.getEntity();
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

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (!this.level.isClientSide()) {
			var bl = DoomConfig.enable_block_breaking;
			this.level.explode(null, this.getX(), this.getY(), this.getZ(), 1, true, bl ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE);
			this.discard();
		}
	}
}
