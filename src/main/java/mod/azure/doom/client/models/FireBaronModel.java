package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.FireBaronEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class FireBaronModel extends GeoModel<FireBaronEntity> {

	private static final Identifier[] TEX = { new Identifier(DoomMod.MODID, "textures/entity/firebaron.png"),
			new Identifier(DoomMod.MODID, "textures/entity/firebaron_1.png"),
			new Identifier(DoomMod.MODID, "textures/entity/firebaron_2.png") };

	@Override
	public Identifier getModelResource(FireBaronEntity object) {
		return new Identifier(DoomMod.MODID, "geo/baron2016.geo.json");
	}

	@Override
	public Identifier getTextureResource(FireBaronEntity object) {
		return TEX[(object.getFlameTimer())];
	}

	@Override
	public Identifier getAnimationResource(FireBaronEntity object) {
		return new Identifier(DoomMod.MODID, "animations/baron2016.animation.json");
	}

	@Override
	public void setCustomAnimations(FireBaronEntity animatable, long instanceId,
			AnimationState<FireBaronEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("neck");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX((entityData.headPitch() + 20) * ((float) Math.PI / 360F));
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 340F));
		}
	}

	@Override
	public RenderLayer getRenderType(FireBaronEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}