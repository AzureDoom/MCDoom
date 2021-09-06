package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.SentinelHammerItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SentinelHammerModel extends AnimatedGeoModel<SentinelHammerItem> {
	@Override
	public Identifier getModelLocation(SentinelHammerItem object) {
		return new Identifier(DoomMod.MODID, "geo/sentinelhammer.geo.json");
	}

	@Override
	public Identifier getTextureLocation(SentinelHammerItem object) {
		return new Identifier(DoomMod.MODID, "textures/items/sentinel_hammer.png");
	}

	@Override
	public Identifier getAnimationFileLocation(SentinelHammerItem animatable) {
		return new Identifier(DoomMod.MODID, "animations/sentinelhammer.animation.json");
	}
}
