package mod.azure.doom.client.render.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.projectiles.BulletEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BulletsRender extends ArrowRenderer<BulletEntity> {
    private static final ResourceLocation ARGENT_BOLT_TEXTURE = MCDoom.modResource(
            "textures/entity/projectiles/argent_bolt.png");

    public BulletsRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BulletEntity entity) {
        return ARGENT_BOLT_TEXTURE;
    }

    @Override
    public void render(@NotNull BulletEntity persistentProjectileEntity, float f, float g, @NotNull PoseStack matrixStack, @NotNull MultiBufferSource vertexConsumerProvider, int i) {
        super.render(persistentProjectileEntity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pushPose();
        matrixStack.scale(0, 0, 0);
        matrixStack.popPose();
    }
}