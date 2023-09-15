package mod.azure.doom.client.models;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.ArchvileEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ArchvileModel extends GeoModel<ArchvileEntity> {

	private static final ResourceLocation[] TEX = { DoomMod.modResource("textures/entity/archvile_flame_1.png"), DoomMod.modResource("textures/entity/archvile_flame_2.png"), DoomMod.modResource("textures/entity/archvile_flame_3.png"), DoomMod.modResource("textures/entity/archvile_flame_4.png"), DoomMod.modResource("textures/entity/archvile_flame_5.png"), DoomMod.modResource("textures/entity/archvile_flame_6.png"), DoomMod.modResource("textures/entity/archvile_flame_7.png"),
			DoomMod.modResource("textures/entity/archvile_flame_8.png") };

	@Override
	public ResourceLocation getModelResource(ArchvileEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/" + (object.getVariant() == 1 ? "archvile" : "archvileeternal") + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ArchvileEntity object) {
		return (object.getVariant() == 1 ? (object.getAttckingState() == 1 ? TEX[(object.getFlameTimer())] : DoomMod.modResource("textures/entity/archvile.png")) : DoomMod.modResource("textures/entity/archvileeternal.png"));
	}

	@Override
	public ResourceLocation getAnimationResource(ArchvileEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/" + (object.getVariant() == 1 ? "archvile_" : "archvileeternal.") + "animation.json");
	}

	@Override
	public RenderType getRenderType(ArchvileEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}

	@Override
	public void setCustomAnimations(ArchvileEntity animatable, long instanceId, AnimationState<ArchvileEntity> animationState) {
		CoreGeoBone body1 = getAnimationProcessor().getBone("thighs");
		CoreGeoBone body2 = getAnimationProcessor().getBone("root");

		if (animatable.getVariant() == 1)
			if (body1 != null)
				body1.setRotY(animationState.getData(DataTickets.ENTITY_MODEL_DATA).netHeadYaw() * Mth.DEG_TO_RAD);

		if (animatable.getVariant() == 2)
			if (body2 != null)
				body2.setRotY(animationState.getData(DataTickets.ENTITY_MODEL_DATA).netHeadYaw() * Mth.DEG_TO_RAD);

		super.setCustomAnimations(animatable, instanceId, animationState);
	}
}