package mod.azure.doom.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.entities.projectiles.MeatHookEntity;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GeoEntityRenderer.class, remap = false)
public abstract class GeoEntityRendererMixin<T extends Entity & GeoEntity> {

    @Shadow
    public abstract T getAnimatable();

    @Inject(method = "applyRotations", at = @At("HEAD"))
    private void microWaveShaking(T animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick, CallbackInfo ci) {
        if (animatable.getFirstPassenger() != null && animatable.getFirstPassenger() instanceof MeatHookEntity) {
            rotationYaw += (float) (Math.cos((double) animatable.tickCount * 3.25) * Math.PI * 0.4000000059604645);
        }
    }
}
