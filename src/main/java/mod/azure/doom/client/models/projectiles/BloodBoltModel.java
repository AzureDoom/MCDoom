package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.BloodBoltEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BloodBoltModel extends GeoModel<BloodBoltEntity> {
	@Override
	public Identifier getModelResource(BloodBoltEntity object) {
		return new Identifier(DoomMod.MODID, "geo/bloodbolt.geo.json");
	}

	@Override
	public Identifier getTextureResource(BloodBoltEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/projectiles/bloodbolt.png");
	}

	@Override
	public Identifier getAnimationResource(BloodBoltEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/bloodbolt.animation.json");
	}
	
	@Override
	public RenderLayer getRenderType(BloodBoltEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
