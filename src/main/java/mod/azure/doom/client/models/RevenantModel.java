package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.RevenantEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class RevenantModel extends GeoModel<RevenantEntity> {

	@Override
	public Identifier getModelResource(RevenantEntity object) {
		return new Identifier(DoomMod.MODID, "geo/revenant.geo.json");
	}

	@Override
	public Identifier getTextureResource(RevenantEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/revenant_nojetpack.png");
	}

	@Override
	public Identifier getAnimationResource(RevenantEntity object) {
		return new Identifier(DoomMod.MODID, "animations/revenant.animation.json");
	}

	@Override
	public void setCustomAnimations(RevenantEntity animatable, long instanceId,
			AnimationState<RevenantEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("head");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
		}
	}

	@Override
	public RenderLayer getRenderType(RevenantEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}