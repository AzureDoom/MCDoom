package mod.azure.doom.client.models;

import com.mojang.math.Vector3f;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.Revenant2016Entity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class Revenant2016Model extends AnimatedTickingGeoModel<Revenant2016Entity> {

	private static final ResourceLocation[] TEX = { new ResourceLocation(DoomMod.MODID, "textures/entity/revenant.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_1.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_2.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_3.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_4.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_5.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_6.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_7.png") };

	private static final ResourceLocation[] TEX2 = { new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_golden.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_golden_1.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_golden_2.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_golden_3.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_golden_4.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_golden_5.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_golden_6.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_golden_7.png") };

	private static final ResourceLocation[] TEX3 = { new ResourceLocation(DoomMod.MODID, "textures/entity/revenant2016.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant2016_1.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant2016_2.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant2016_3.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant2016_4.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant2016_5.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant2016_6.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant2016_7.png") };

	@Override
	public ResourceLocation getModelResource(Revenant2016Entity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/revenant.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Revenant2016Entity object) {
		return (object.getVariant() == 11 ? TEX2[(object.getFlameTimer())]
				: object.getVariant() == 2 ? TEX3[(object.getFlameTimer())] : TEX[(object.getFlameTimer())]);
	}

	@Override
	public ResourceLocation getAnimationResource(Revenant2016Entity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/revenant.animation.json");
	}

	@Override
	public void setLivingAnimations(Revenant2016Entity entity, Integer uniqueID, AnimationEvent customPredicate) {
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