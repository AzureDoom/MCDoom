package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.PossessedSoldierEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class PossessedSoldierModel extends GeoModel<PossessedSoldierEntity> {

	private static final Identifier[] TEX = {
			new Identifier(DoomMod.MODID, "textures/entity/possessedsoldier-eternal_flames_1.png"),
			new Identifier(DoomMod.MODID, "textures/entity/possessedsoldier-eternal_flames_2.png") };

	@Override
	public Identifier getModelResource(PossessedSoldierEntity object) {
		return new Identifier(
				DoomMod.MODID, "geo/"
						+ (object.getVariant() == 3 ? "possessedsoldier-shield"
								: object.getVariant() == 2 ? "possessedsoldier-eternal" : "possessedsoldier")
						+ ".geo.json");
	}

	@Override
	public Identifier getTextureResource(PossessedSoldierEntity object) {
		return (object.getVariant() == 2 && object.isAttacking() ? TEX[(object.getFlameTimer())]
				: object.getVariant() == 2 && !object.isAttacking() ? new Identifier(DoomMod.MODID, "textures/entity/possessedsoldier-eternal.png")
						:object.getVariant() == 3
						? new Identifier(DoomMod.MODID, "textures/entity/possessedsoldier-shield.png")
						: new Identifier(DoomMod.MODID, "textures/entity/possessedsoldier.png"));
	}

	@Override
	public Identifier getAnimationResource(PossessedSoldierEntity object) {
		return new Identifier(DoomMod.MODID, "animations/possessedsoldier.animation.json");
	}

	@Override
	public void setCustomAnimations(PossessedSoldierEntity animatable, long instanceId,
			AnimationState<PossessedSoldierEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("head");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
		}
	}

	@Override
	public RenderLayer getRenderType(PossessedSoldierEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}