package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.SentinelHammerItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SentinelHammerModel extends GeoModel<SentinelHammerItem> {
	@Override
	public Identifier getModelResource(SentinelHammerItem object) {
		return new Identifier(DoomMod.MODID, "geo/sentinelhammer.geo.json");
	}

	@Override
	public Identifier getTextureResource(SentinelHammerItem object) {
		return new Identifier(DoomMod.MODID, "textures/item/sentinel_hammer.png");
	}

	@Override
	public Identifier getAnimationResource(SentinelHammerItem animatable) {
		return new Identifier(DoomMod.MODID, "animations/sentinelhammer.animation.json");
	}
}
