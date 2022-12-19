package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.Hellknight2016Entity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class Hellknight2016Model extends GeoModel<Hellknight2016Entity> {

	@Override
	public Identifier getModelResource(Hellknight2016Entity object) {
		return new Identifier(DoomMod.MODID, "geo/hellknight2016.geo.json");
	}

	@Override
	public Identifier getTextureResource(Hellknight2016Entity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/hellknight2016.png");
	}

	@Override
	public Identifier getAnimationResource(Hellknight2016Entity object) {
		return new Identifier(DoomMod.MODID, "animations/hellknight2016_animation.json");
	}

	@Override
	public void setCustomAnimations(Hellknight2016Entity animatable, long instanceId,
			AnimationState<Hellknight2016Entity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("neck");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX((entityData.headPitch() + 85) * ((float) Math.PI / 360F));
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 340F));
		}
	}

	@Override
	public RenderLayer getRenderType(Hellknight2016Entity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}