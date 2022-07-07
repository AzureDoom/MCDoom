package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.IconofsinModel;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class IconofsinRender extends GeoEntityRenderer<IconofsinEntity> {

	public IconofsinRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new IconofsinModel());
	}

	@Override
	public RenderLayer getRenderType(IconofsinEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(this.getTextureResource(animatable));
	}

	@Override
	public void render(GeoModel model, IconofsinEntity animatable, float partialTicks, RenderLayer type,
			MatrixStack matrixStackIn, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
			int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder,
				packedLightIn, packedOverlayIn, red, green, blue, alpha);
		float health = animatable.getHealth();
		float maxhealth = animatable.getMaxHealth();
		if (animatable.getDataTracker().get(IconofsinEntity.DEATH_STATE) == 1) {
			model.getBone("rShoulderArmor").get().setHidden(true);
			model.getBone("rArmArmor1").get().setHidden(true);
			model.getBone("rArmArmor2").get().setHidden(true);
			model.getBone("lShoulderArmor").get().setHidden(true);
			model.getBone("lArmArmor1").get().setHidden(true);
			model.getBone("lArmArmor2").get().setHidden(true);
			model.getBone("chestArmor").get().setHidden(true);
			model.getBone("lPeckArmor").get().setHidden(true);
			model.getBone("rPeckArmor").get().setHidden(true);
			model.getBone("thighArmor").get().setHidden(true);
			model.getBone("spineArmor1").get().setHidden(true);
			model.getBone("spineArmor2").get().setHidden(true);
			model.getBone("spineArmor3").get().setHidden(true);
			model.getBone("neckArmor").get().setHidden(true);
			model.getBone("lHHorn1").get().setHidden(true);
			model.getBone("lHHorn2").get().setHidden(true);
			model.getBone("lHHorn3").get().setHidden(true);
			model.getBone("lHHorn4").get().setHidden(true);
			model.getBone("rHHorn1").get().setHidden(true);
			model.getBone("rHHorn2").get().setHidden(true);
			model.getBone("rHHorn3").get().setHidden(true);
			model.getBone("rHHorn4").get().setHidden(true);
			model.getBone("helmetBase").get().setHidden(true);
			model.getBone("helmetLeft").get().setHidden(true);
			model.getBone("helmetRight").get().setHidden(true);
			model.getBone("helmetMiddle").get().setHidden(true);
			model.getBone("helmetTop").get().setHidden(true);
			model.getBone("lLegArmor1").get().setHidden(true);
			model.getBone("lLegArmor2").get().setHidden(true);
			model.getBone("lLegArmor3").get().setHidden(true);
			model.getBone("lFootArmor").get().setHidden(true);
			model.getBone("lToe1Armor_2").get().setHidden(true);
			model.getBone("lToe2Armor_2").get().setHidden(true);
			model.getBone("lToe3Armor_2").get().setHidden(true);
			model.getBone("lToe1Armor_1").get().setHidden(true);
			model.getBone("lToe2Armor_1").get().setHidden(true);
			model.getBone("lToe3Armor_1").get().setHidden(true);
			model.getBone("rLegArmor1").get().setHidden(true);
			model.getBone("rLegArmor2").get().setHidden(true);
			model.getBone("rLegArmor3").get().setHidden(true);
			model.getBone("rFootArmor").get().setHidden(true);
			model.getBone("rToe1Armor_2").get().setHidden(true);
			model.getBone("rToe2Armor_2").get().setHidden(true);
			model.getBone("rToe3Armor_2").get().setHidden(true);
			model.getBone("rToe1Armor_1").get().setHidden(true);
			model.getBone("rToe2Armor_1").get().setHidden(true);
			model.getBone("rToe3Armor_1").get().setHidden(true);
		}
		if (animatable.getDataTracker().get(IconofsinEntity.DEATH_STATE) == 0) {
			if (health >= (maxhealth * 0.90)) {
				model.getBone("rShoulderArmor").get().setHidden(false);
				model.getBone("rArmArmor1").get().setHidden(false);
				model.getBone("rArmArmor2").get().setHidden(false);
				model.getBone("lShoulderArmor").get().setHidden(false);
				model.getBone("lArmArmor1").get().setHidden(false);
				model.getBone("lArmArmor2").get().setHidden(false);
				model.getBone("chestArmor").get().setHidden(false);
				model.getBone("lPeckArmor").get().setHidden(false);
				model.getBone("rPeckArmor").get().setHidden(false);
				model.getBone("thighArmor").get().setHidden(false);
				model.getBone("spineArmor1").get().setHidden(false);
				model.getBone("spineArmor2").get().setHidden(false);
				model.getBone("spineArmor3").get().setHidden(false);
				model.getBone("neckArmor").get().setHidden(false);
				model.getBone("lHHorn1").get().setHidden(false);
				model.getBone("lHHorn2").get().setHidden(false);
				model.getBone("lHHorn3").get().setHidden(false);
				model.getBone("lHHorn4").get().setHidden(false);
				model.getBone("rHHorn1").get().setHidden(false);
				model.getBone("rHHorn2").get().setHidden(false);
				model.getBone("rHHorn3").get().setHidden(false);
				model.getBone("rHHorn4").get().setHidden(false);
				model.getBone("helmetBase").get().setHidden(false);
				model.getBone("helmetLeft").get().setHidden(false);
				model.getBone("helmetRight").get().setHidden(false);
				model.getBone("helmetMiddle").get().setHidden(false);
				model.getBone("helmetTop").get().setHidden(false);
				model.getBone("lLegArmor1").get().setHidden(false);
				model.getBone("lLegArmor2").get().setHidden(false);
				model.getBone("lLegArmor3").get().setHidden(false);
				model.getBone("lFootArmor").get().setHidden(false);
				model.getBone("lToe1Armor_2").get().setHidden(false);
				model.getBone("lToe2Armor_2").get().setHidden(false);
				model.getBone("lToe3Armor_2").get().setHidden(false);
				model.getBone("lToe1Armor_1").get().setHidden(false);
				model.getBone("lToe2Armor_1").get().setHidden(false);
				model.getBone("lToe3Armor_1").get().setHidden(false);
				model.getBone("rLegArmor1").get().setHidden(false);
				model.getBone("rLegArmor2").get().setHidden(false);
				model.getBone("rLegArmor3").get().setHidden(false);
				model.getBone("rFootArmor").get().setHidden(false);
				model.getBone("rToe1Armor_2").get().setHidden(false);
				model.getBone("rToe2Armor_2").get().setHidden(false);
				model.getBone("rToe3Armor_2").get().setHidden(false);
				model.getBone("rToe1Armor_1").get().setHidden(false);
				model.getBone("rToe2Armor_1").get().setHidden(false);
				model.getBone("rToe3Armor_1").get().setHidden(false);
			}
			if (health < (maxhealth * 0.80)) {
				model.getBone("rShoulderArmor").get().setHidden(true);
				model.getBone("rArmArmor1").get().setHidden(true);
				model.getBone("rArmArmor2").get().setHidden(true);
			}
			if (health < (maxhealth * 0.70)) {
				model.getBone("lShoulderArmor").get().setHidden(true);
				model.getBone("lArmArmor1").get().setHidden(true);
				model.getBone("lArmArmor2").get().setHidden(true);
			}
			if (health < (maxhealth * 0.60)) {
				model.getBone("chestArmor").get().setHidden(true);
				model.getBone("lPeckArmor").get().setHidden(true);
				model.getBone("rPeckArmor").get().setHidden(true);
			}
			if (health < (maxhealth * 0.50)) {
				model.getBone("thighArmor").get().setHidden(true);
			}
			if (health < (maxhealth * 0.40)) {
				model.getBone("spineArmor1").get().setHidden(true);
				model.getBone("spineArmor2").get().setHidden(true);
				model.getBone("spineArmor3").get().setHidden(true);
			}
			if (health < (maxhealth * 0.35)) {
				model.getBone("neckArmor").get().setHidden(true);
			}
			if (health < (maxhealth * 0.30)) {
				model.getBone("lHHorn1").get().setHidden(true);
				model.getBone("lHHorn2").get().setHidden(true);
				model.getBone("lHHorn3").get().setHidden(true);
				model.getBone("lHHorn4").get().setHidden(true);
				model.getBone("rHHorn1").get().setHidden(true);
				model.getBone("rHHorn2").get().setHidden(true);
				model.getBone("rHHorn3").get().setHidden(true);
				model.getBone("rHHorn4").get().setHidden(true);
			}
			if (health < (maxhealth * 0.25)) {
				model.getBone("helmetBase").get().setHidden(true);
				model.getBone("helmetLeft").get().setHidden(true);
				model.getBone("helmetRight").get().setHidden(true);
				model.getBone("helmetMiddle").get().setHidden(true);
				model.getBone("helmetTop").get().setHidden(true);
			}
			if (health < (maxhealth * 0.15)) {
				model.getBone("lLegArmor1").get().setHidden(true);
				model.getBone("lLegArmor2").get().setHidden(true);
				model.getBone("lLegArmor3").get().setHidden(true);
				model.getBone("lFootArmor").get().setHidden(true);
				model.getBone("lToe1Armor_2").get().setHidden(true);
				model.getBone("lToe2Armor_2").get().setHidden(true);
				model.getBone("lToe3Armor_2").get().setHidden(true);
				model.getBone("lToe1Armor_1").get().setHidden(true);
				model.getBone("lToe2Armor_1").get().setHidden(true);
				model.getBone("lToe3Armor_1").get().setHidden(true);
			}
			if (health < (maxhealth * 0.01)) {
				model.getBone("rLegArmor1").get().setHidden(true);
				model.getBone("rLegArmor2").get().setHidden(true);
				model.getBone("rLegArmor3").get().setHidden(true);
				model.getBone("rFootArmor").get().setHidden(true);
				model.getBone("rToe1Armor_2").get().setHidden(true);
				model.getBone("rToe2Armor_2").get().setHidden(true);
				model.getBone("rToe3Armor_2").get().setHidden(true);
				model.getBone("rToe1Armor_1").get().setHidden(true);
				model.getBone("rToe2Armor_1").get().setHidden(true);
				model.getBone("rToe3Armor_1").get().setHidden(true);
			}
		}
	}

	@Override
	public void renderEarly(IconofsinEntity animatable, MatrixStack stackIn, float ticks,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn,
				red, green, blue, partialTicks);
		stackIn.scale(2F, 2F, 2F);
	}

	@Override
	protected float getDeathMaxRotation(IconofsinEntity entityLivingBaseIn) {
		return 0.0F;
	}

}