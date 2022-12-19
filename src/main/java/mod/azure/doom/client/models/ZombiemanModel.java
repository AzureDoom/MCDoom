package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.ZombiemanEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ZombiemanModel extends GeoModel<ZombiemanEntity> {

	@Override
	public Identifier getModelResource(ZombiemanEntity object) {
		return new Identifier(DoomMod.MODID, "geo/shotgunzombie.geo.json");
	}

	@Override
	public Identifier getTextureResource(ZombiemanEntity object) {
		return new Identifier(DoomMod.MODID,
				"textures/entity/" + (object.getVariant() == 2 ? "zombieman64" : "eternalzombiemen") + ".png");
	}

	@Override
	public Identifier getAnimationResource(ZombiemanEntity object) {
		return new Identifier(DoomMod.MODID, "animations/shotgunzombie.animation.json");
	}

	@Override
	public void setCustomAnimations(ZombiemanEntity animatable, long instanceId,
			AnimationState<ZombiemanEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("bipedHead");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
			head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
		}
	}

	@Override
	public RenderLayer getRenderType(ZombiemanEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}