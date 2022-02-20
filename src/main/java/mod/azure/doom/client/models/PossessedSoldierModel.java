package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.PossessedSoldierEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class PossessedSoldierModel extends AnimatedTickingGeoModel<PossessedSoldierEntity> {

	private static final Identifier[] TEX = {
			new Identifier(DoomMod.MODID, "textures/entity/possessedsoldier-eternal_flames_1.png"),
			new Identifier(DoomMod.MODID, "textures/entity/possessedsoldier-eternal_flames_2.png") };

	@Override
	public Identifier getModelLocation(PossessedSoldierEntity object) {
		return new Identifier(
				DoomMod.MODID, "geo/"
						+ (object.getVariant() == 3 ? "possessedsoldier-shield"
								: object.getVariant() == 2 ? "possessedsoldier-eternal" : "possessedsoldier")
						+ ".geo.json");
	}

	@Override
	public Identifier getTextureLocation(PossessedSoldierEntity object) {
		return (object.getVariant() == 2 && object.isAttacking() ? TEX[(object.getFlameTimer())]
				: object.getVariant() == 2 && !object.isAttacking() ? new Identifier(DoomMod.MODID, "textures/entity/possessedsoldier-eternal.png")
						:object.getVariant() == 3
						? new Identifier(DoomMod.MODID, "textures/entity/possessedsoldier-shield.png")
						: new Identifier(DoomMod.MODID, "textures/entity/possessedsoldier.png"));
	}

	@Override
	public Identifier getAnimationFileLocation(PossessedSoldierEntity object) {
		return new Identifier(DoomMod.MODID, "animations/possessedsoldier.animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(PossessedSoldierEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(
					Vec3f.POSITIVE_X.getRadialQuaternion(extraData.headPitch * ((float) Math.PI / 180F)).getX());
			head.setRotationY(
					Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 180F)).getY());
		}
	}
}