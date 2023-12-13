package mod.azure.doom.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.azure.doom.entities.projectiles.MeatHookEntity;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRenderMixin {

    @Inject(method = "setupRotations", at = @At("HEAD"))
    private void microWaveShaking(LivingEntity livingEntity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick, CallbackInfo ci) {
        if (livingEntity.getFirstPassenger() != null && livingEntity.getFirstPassenger() instanceof MeatHookEntity) {
            rotationYaw += (float) (Math.cos((double) livingEntity.tickCount * 3.25) * Math.PI * 0.4000000059604645);
        }
    }
}
