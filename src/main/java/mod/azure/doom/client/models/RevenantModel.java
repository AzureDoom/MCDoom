package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.RevenantEntity;
import com.mojang.math.Vector3f;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class RevenantModel extends AnimatedTickingGeoModel<RevenantEntity> {
	
	@Override
	public ResourceLocation getModelResource(RevenantEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/revenant.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RevenantEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_nojetpack.png");
	}

	@Override
	public ResourceLocation getAnimationResource(RevenantEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/revenant.animation.json");
	}

	@Override
	public void setLivingAnimations(RevenantEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
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