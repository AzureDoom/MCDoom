package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.HellknightEntity;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class HellknightModel extends AnimatedTickingGeoModel<HellknightEntity> {

	public HellknightModel() {
	}

	@Override
	public Identifier getModelResource(HellknightEntity object) {
		return new Identifier(DoomMod.MODID, "geo/hellknight.geo.json");
	}

	@Override
	public Identifier getTextureResource(HellknightEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/hellknight-texturemap.png");
	}

	@Override
	public Identifier getAnimationResource(HellknightEntity object) {
		return new Identifier(DoomMod.MODID, "animations/baron_hell_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(HellknightEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(Vec3f.POSITIVE_X
					.getRadialQuaternion((extraData.headPitch + 20) * ((float) Math.PI / 360F)).getX());
			head.setRotationY(
					Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 340F)).getY());
		}
	}
}