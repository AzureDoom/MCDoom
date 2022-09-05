package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.GargoyleEntity;
import com.mojang.math.Vector3f;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class GargoyleModel extends AnimatedTickingGeoModel<GargoyleEntity> {

	public GargoyleModel() {
	}

	@Override
	public ResourceLocation getModelResource(GargoyleEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/gargoyleimp.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GargoyleEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/gargoyleimp.png");
	}

	@Override
	public ResourceLocation getAnimationResource(GargoyleEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/gargoyleimp.animation.json");
	}

	@Override
	public void setLivingAnimations(GargoyleEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationY(
					Vector3f.YP.rotation(extraData.netHeadYaw * ((float) Math.PI / 340F)).j());
		}
	}
}