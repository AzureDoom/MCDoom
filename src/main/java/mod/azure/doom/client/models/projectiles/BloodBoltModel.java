package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.BloodBoltEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BloodBoltModel extends AnimatedGeoModel<BloodBoltEntity> {
	@Override
	public Identifier getModelLocation(BloodBoltEntity object) {
		return new Identifier(DoomMod.MODID, "geo/bloodbolt.geo.json");
	}

	@Override
	public Identifier getTextureLocation(BloodBoltEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/projectiles/bloodbolt.png");
	}

	@Override
	public Identifier getAnimationFileLocation(BloodBoltEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/smallprojectile.animation.json");
	}
}
