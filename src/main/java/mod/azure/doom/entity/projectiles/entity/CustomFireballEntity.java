package mod.azure.doom.entity.projectiles.entity;

import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class CustomFireballEntity extends LargeFireball {

	private float directHitDamage = 6.0F;

	public CustomFireballEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ, float directHitDamage) {
		super(worldIn, shooter, accelX, accelY, accelZ, 1);
		this.directHitDamage = directHitDamage;
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		if (!level.isClientSide()) {
			final var entity = entityHitResult.getEntity();
			final var entity2 = getOwner();
			if (!(entity instanceof DemonEntity))
				entity.hurt(DamageSource.mobAttack((LivingEntity) entity2), directHitDamage);
			if (entity2 instanceof LivingEntity) {
				if (!(entity instanceof DemonEntity))
					doEnchantDamageEffects((LivingEntity) entity2, entity);
				remove(Entity.RemovalReason.DISCARDED);
			}
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		super.onHitBlock(result);
		if (!this.level.isClientSide()) {
			var bl = DoomConfig.SERVER.enable_block_breaking.get();
			this.level.explode(null, this.getX(), this.getY(), this.getZ(), 1, true, bl ? Explosion.BlockInteraction.BREAK : Explosion.BlockInteraction.NONE);
			this.discard();
		}
	}
}
