package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.DShotgun;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DSGModel extends AnimatedGeoModel<DShotgun> {
	@Override
	public Identifier getModelLocation(DShotgun object) {
		return new Identifier(DoomMod.MODID, "geo/doomed_shotgun.geo.json");
	}

	@Override
	public Identifier getTextureLocation(DShotgun object) {
		return new Identifier(DoomMod.MODID, "textures/items/doomed_shotgun.png");
	}

	@Override
	public Identifier getAnimationFileLocation(DShotgun animatable) {
		return new Identifier(DoomMod.MODID, "animations/doomed_shotgun.animation.json");
	}
}
