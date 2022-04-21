package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.SentinelHammerItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedGeoModel;

public class SentinelHammerModel extends AnimatedGeoModel<SentinelHammerItem> {
	@Override
	public Identifier getModelResource(SentinelHammerItem object) {
		return new Identifier(DoomMod.MODID, "geo/sentinelhammer.geo.json");
	}

	@Override
	public Identifier getTextureResource(SentinelHammerItem object) {
		return new Identifier(DoomMod.MODID, "textures/items/sentinel_hammer.png");
	}

	@Override
	public Identifier getAnimationResource(SentinelHammerItem animatable) {
		return new Identifier(DoomMod.MODID, "animations/sentinelhammer.animation.json");
	}
}
