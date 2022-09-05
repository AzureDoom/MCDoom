package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import com.mojang.math.Vector3f;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class IconofsinModel extends AnimatedTickingGeoModel<IconofsinEntity> {
	
	@Override
	public ResourceLocation getModelResource(IconofsinEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/icon.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(IconofsinEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/iconofsin.png");
	}

	@Override
	public ResourceLocation getAnimationResource(IconofsinEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/icon.animation.json");
	}

	@Override
	public void setLivingAnimations(IconofsinEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(Vector3f.XP
					.rotation((extraData.headPitch - 20) * ((float) Math.PI / 360F)).i());
			head.setRotationY(
					Vector3f.YP.rotation(extraData.netHeadYaw * ((float) Math.PI / 360F)).j());
		}
	}
}