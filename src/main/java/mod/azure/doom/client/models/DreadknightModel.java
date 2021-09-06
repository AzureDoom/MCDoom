package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.Hellknight2016Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class DreadknightModel extends AnimatedGeoModel<Hellknight2016Entity> {

	public DreadknightModel() {
	}

	@Override
	public Identifier getModelLocation(Hellknight2016Entity object) {
		return new Identifier(DoomMod.MODID, "geo/dreadknight.geo.json");
	}

	@Override
	public Identifier getTextureLocation(Hellknight2016Entity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/dreadknight.png");
	}

	@Override
	public Identifier getAnimationFileLocation(Hellknight2016Entity object) {
		return new Identifier(DoomMod.MODID, "animations/hellknight2016_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(Hellknight2016Entity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX(
				Vec3f.POSITIVE_X.getRadialQuaternion((extraData.headPitch + 85) * ((float) Math.PI / 360F)).getX());
		head.setRotationY(Vec3f.POSITIVE_Y.getRadialQuaternion(extraData.netHeadYaw * ((float) Math.PI / 340F)).getY());
	}
}