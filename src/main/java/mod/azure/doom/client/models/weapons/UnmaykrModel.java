package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.Unmaykr;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class UnmaykrModel extends GeoModel<Unmaykr> {
	@Override
	public Identifier getModelResource(Unmaykr object) {
		return new Identifier(DoomMod.MODID, "geo/unmaykr.geo.json");
	}

	@Override
	public Identifier getTextureResource(Unmaykr object) {
		return new Identifier(DoomMod.MODID, "textures/item/unmaykr.png");
	}

	@Override
	public Identifier getAnimationResource(Unmaykr animatable) {
		return new Identifier(DoomMod.MODID, "animations/unmaykr.animation.json");
	}
}
