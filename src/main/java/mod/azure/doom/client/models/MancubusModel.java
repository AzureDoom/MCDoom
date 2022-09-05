package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.MancubusEntity;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class MancubusModel extends AnimatedTickingGeoModel<MancubusEntity> {
	
	@Override
	public ResourceLocation getModelLocation(MancubusEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/mancubus.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(MancubusEntity object) {
		return new ResourceLocation(DoomMod.MODID,
				"textures/entity/" + (object.getVariant() == 2 ? "cyber_mancubus" : "mancubus") + ".png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(MancubusEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/mancubus_animation.json");
	}

	@Override
	public void setLivingAnimations(MancubusEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
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