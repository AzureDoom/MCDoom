package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.HellknightEntity;
import com.mojang.math.Vector3f;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class HellknightModel extends AnimatedTickingGeoModel<HellknightEntity> {

	@Override
	public ResourceLocation getModelLocation(HellknightEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/hellknight.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(HellknightEntity object) {
		return new ResourceLocation(DoomMod.MODID,
				"textures/entity/hellknight-" + (object.getVariant() == 2 ? "64" : "texturemap") + ".png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(HellknightEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/baron_hell_animation.json");
	}

	@Override
	public void setLivingAnimations(HellknightEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(Vector3f.XP
					.rotation((extraData.headPitch + 20) * ((float) Math.PI / 360F)).i());
			head.setRotationY(
					Vector3f.YP.rotation(extraData.netHeadYaw * ((float) Math.PI / 340F)).j());
		}
	}
}