package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.DoomHunterEntity;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class DoomHunterModel extends AnimatedTickingGeoModel<DoomHunterEntity> {

	private static final ResourceLocation[] TEX = { new ResourceLocation(DoomMod.MODID, "textures/entity/doomhunter.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/doomhunter_1.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/doomhunter_2.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/doomhunter_3.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/doomhunter_4.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/doomhunter_5.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/doomhunter_6.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/doomhunter_7.png") };

	@Override
	public ResourceLocation getModelResource(DoomHunterEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomhunter.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DoomHunterEntity object) {
		return TEX[(object.getFlameTimer())];
	}

	@Override
	public ResourceLocation getAnimationResource(DoomHunterEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/doomhunter.animation.json");
	}

	@Override
	public void setLivingAnimations(DoomHunterEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("neck");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(
					Vector3f.XP.rotation(extraData.headPitch * ((float) Math.PI / 270F)).i());
			head.setRotationY(
					Vector3f.YP.rotation(extraData.netHeadYaw * ((float) Math.PI / 270F)).j());
		}
	}

}