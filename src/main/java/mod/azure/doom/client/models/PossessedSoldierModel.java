package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.PossessedSoldierEntity;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PossessedSoldierModel extends AnimatedTickingGeoModel<PossessedSoldierEntity> {

	private static final ResourceLocation[] TEX = {
			new ResourceLocation(DoomMod.MODID, "textures/entity/possessedsoldier-eternal_flames_1.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/possessedsoldier-eternal_flames_2.png") };

	@Override
	public ResourceLocation getModelLocation(PossessedSoldierEntity object) {
		return new ResourceLocation(
				DoomMod.MODID, "geo/"
						+ (object.getVariant() == 3 ? "possessedsoldier-shield"
								: object.getVariant() == 2 ? "possessedsoldier-eternal" : "possessedsoldier")
						+ ".geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(PossessedSoldierEntity object) {
		return (object.getVariant() == 2 && object.isAggressive() ? TEX[(object.getFlameTimer())]
				: object.getVariant() == 2 && !object.isAggressive() ? new ResourceLocation(DoomMod.MODID, "textures/entity/possessedsoldier-eternal.png")
						:object.getVariant() == 3
						? new ResourceLocation(DoomMod.MODID, "textures/entity/possessedsoldier-shield.png")
						: new ResourceLocation(DoomMod.MODID, "textures/entity/possessedsoldier.png"));
	}

	@Override
	public ResourceLocation getAnimationFileLocation(PossessedSoldierEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/possessedsoldier.animation.json");
	}

	@Override
	public void setLivingAnimations(PossessedSoldierEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(
					Vector3f.XP.rotation(extraData.headPitch * ((float) Math.PI / 180F)).i());
			head.setRotationY(
					Vector3f.YP.rotation(extraData.netHeadYaw * ((float) Math.PI / 180F)).j());
		}
	}
}