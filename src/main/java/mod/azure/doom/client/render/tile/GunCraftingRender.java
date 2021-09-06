package mod.azure.doom.client.render.tile;

import mod.azure.doom.client.models.tile.GunCraftingModel;
import mod.azure.doom.entity.tileentity.GunBlockEntity;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class GunCraftingRender extends GeoBlockRenderer<GunBlockEntity> {

	private static final ItemStack gun = new ItemStack(DoomItems.PISTOL);
	private VertexConsumerProvider rtb;
	private Identifier whTexture;
	
	public GunCraftingRender() {
		super(new GunCraftingModel());
	}

	@Override
	public RenderLayer getRenderType(GunBlockEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderEarly(GunBlockEntity animatable, MatrixStack stackIn, float ticks,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		this.rtb = renderTypeBuffer;
		this.whTexture = this.getTextureLocation(animatable);
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn,
				red, green, blue, partialTicks);
	}

	@Override
	public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float alpha) {
		if (bone.getName().equals("group")) {
			stack.push();
			stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-40));
			stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(0));
			stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-5));
			stack.translate(-0.10D, 0.96D, 0.9D);
			stack.scale(0.5f, 0.5f, 0.5f);
			MinecraftClient.getInstance().getItemRenderer().renderItem(gun, Mode.THIRD_PERSON_RIGHT_HAND, packedLightIn,
					packedOverlayIn, stack, this.rtb, 0);
			stack.pop();
			bufferIn = rtb.getBuffer(RenderLayer.getEntityTranslucent(whTexture));
		}
		super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

}