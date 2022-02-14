package mod.azure.doom.client.render.weapons;

import com.mojang.blaze3d.vertex.PoseStack;

import mod.azure.doom.client.models.weapons.BFGModel;
import mod.azure.doom.item.weapons.BFG;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class BFGRender extends GeoItemRenderer<BFG> {

	private TransformType currentTransform;
	
	public BFGRender() {
		super(new BFGModel());
	}

	@Override
	public void renderByItem(ItemStack itemStack, TransformType p_239207_2_, PoseStack matrixStack,
			MultiBufferSource bufferIn, int combinedLightIn, int p_239207_6_) {
		currentTransform = p_239207_2_;
		super.renderByItem(itemStack, p_239207_2_, matrixStack, bufferIn, combinedLightIn, p_239207_6_);
	}

	@Override
	public Integer getUniqueID(BFG animatable) {
		if (currentTransform == TransformType.GUI) {
			return -1;
		}
		return super.getUniqueID(animatable);
	}
}