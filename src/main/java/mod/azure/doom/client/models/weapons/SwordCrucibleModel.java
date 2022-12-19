package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.SwordCrucibleItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SwordCrucibleModel extends GeoModel<SwordCrucibleItem> {
	@Override
	public Identifier getModelResource(SwordCrucibleItem object) {
		return new Identifier(DoomMod.MODID, "geo/cruciblesword.geo.json");
	}

	@Override
	public Identifier getTextureResource(SwordCrucibleItem object) {
		return new Identifier(DoomMod.MODID, "textures/item/crucible.png");
	}

	@Override
	public Identifier getAnimationResource(SwordCrucibleItem animatable) {
		return new Identifier(DoomMod.MODID, "animations/cruciblesword.animation.json");
	}
}
