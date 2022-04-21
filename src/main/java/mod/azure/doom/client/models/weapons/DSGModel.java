package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.DShotgun;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedGeoModel;

public class DSGModel extends AnimatedGeoModel<DShotgun> {
	@Override
	public Identifier getModelResource(DShotgun object) {
		return new Identifier(DoomMod.MODID, "geo/doomed_shotgun.geo.json");
	}

	@Override
	public Identifier getTextureResource(DShotgun object) {
		return new Identifier(DoomMod.MODID, "textures/items/doomed_shotgun.png");
	}

	@Override
	public Identifier getAnimationResource(DShotgun animatable) {
		return new Identifier(DoomMod.MODID, "animations/doomed_shotgun.animation.json");
	}
}
