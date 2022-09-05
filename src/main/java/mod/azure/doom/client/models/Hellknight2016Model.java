package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.Hellknight2016Entity;
import com.mojang.math.Vector3f;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class Hellknight2016Model extends AnimatedTickingGeoModel<Hellknight2016Entity> {

	@Override
	public ResourceLocation getModelResource(Hellknight2016Entity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/hellknight2016.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Hellknight2016Entity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/hellknight2016.png");
	}

	@Override
	public ResourceLocation getAnimationResource(Hellknight2016Entity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/hellknight2016_animation.json");
	}

	@Override
	public void setLivingAnimations(Hellknight2016Entity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(Vector3f.XP
					.rotation((extraData.headPitch + 85) * ((float) Math.PI / 360F)).i());
			head.setRotationY(
					Vector3f.YP.rotation(extraData.netHeadYaw * ((float) Math.PI / 340F)).j());
		}
	}
}