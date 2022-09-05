package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.ImpStoneEntity;
import com.mojang.math.Vector3f;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ImpStoneModel extends AnimatedTickingGeoModel<ImpStoneEntity> {

	@Override
	public ResourceLocation getModelLocation(ImpStoneEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/imp2016.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ImpStoneEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/stoneimp.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ImpStoneEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/imp2016.animation.json");
	}

	@Override
	public void setLivingAnimations(ImpStoneEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(
					Vector3f.XP
							.rotation(Vector3f.XP
									.rotation(extraData.headPitch * ((float) Math.PI / 180F)).i())
							.i());
			head.setRotationY(
					Vector3f.YP
							.rotation(Vector3f.YP
									.rotation(extraData.netHeadYaw * ((float) Math.PI / 340F)).j())
							.j());
		}
	}
}