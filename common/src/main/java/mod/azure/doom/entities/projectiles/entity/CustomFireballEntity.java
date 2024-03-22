package mod.azure.doom.entities.projectiles.entity;

import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.DemonEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class CustomFireballEntity extends LargeFireball {

    private final float directHitDamage;

    public CustomFireballEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ, float directHitDamage) {
        super(worldIn, shooter, accelX, accelY, accelZ, 1);
        this.directHitDamage = directHitDamage;
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        if (!level().isClientSide()) {
            final var entity = entityHitResult.getEntity();
            final var entity2 = getOwner();
            if (!(entity instanceof DemonEntity) && entity instanceof LivingEntity)
                entity.hurt(damageSources().mobAttack((LivingEntity) entity2), directHitDamage);
            if (entity2 instanceof LivingEntity livingEntity) {
                if (!(entity instanceof DemonEntity)) doEnchantDamageEffects(livingEntity, entity);
                remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide()) {
            var bl = MCDoom.config.enable_block_breaking;
            this.level().explode(null, this.getX(), this.getY(), this.getZ(), 1, true,
                    bl ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE);
            this.discard();
        }
    }
}
