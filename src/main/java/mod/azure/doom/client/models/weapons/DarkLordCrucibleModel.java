package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.DarkLordCrucibleItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class DarkLordCrucibleModel extends GeoModel<DarkLordCrucibleItem> {
	@Override
	public Identifier getModelResource(DarkLordCrucibleItem object) {
		return new Identifier(DoomMod.MODID, "geo/darklordcrucible.geo.json");
	}

	@Override
	public Identifier getTextureResource(DarkLordCrucibleItem object) {
		return new Identifier(DoomMod.MODID, "textures/item/darklordcrucible.png");
	}

	@Override
	public Identifier getAnimationResource(DarkLordCrucibleItem animatable) {
		return new Identifier(DoomMod.MODID, "animations/cruciblesword.animation.json");
	}
}
