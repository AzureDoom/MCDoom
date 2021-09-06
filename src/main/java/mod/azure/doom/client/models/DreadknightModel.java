package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.Hellknight2016Entity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class DreadknightModel extends AnimatedGeoModel<Hellknight2016Entity> {

	public DreadknightModel() {
	}

	@Override
	public ResourceLocation getModelLocation(Hellknight2016Entity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/dreadknight.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(Hellknight2016Entity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/dreadknight.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(Hellknight2016Entity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/hellknight2016_animation.json");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setLivingAnimations(Hellknight2016Entity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch + 85) * ((float) Math.PI / 360F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 340F));
	}
}