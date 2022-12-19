package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.Unmaker;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class UnmakerModel extends GeoModel<Unmaker> {
	@Override
	public Identifier getModelResource(Unmaker object) {
		return new Identifier(DoomMod.MODID, "geo/unmaykr.geo.json");
	}

	@Override
	public Identifier getTextureResource(Unmaker object) {
		return new Identifier(DoomMod.MODID, "textures/item/unmaker.png");
	}

	@Override
	public Identifier getAnimationResource(Unmaker animatable) {
		return new Identifier(DoomMod.MODID, "animations/unmaykr.animation.json");
	}
}
