package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.SwordCrucibleItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SwordCrucibleModel extends AnimatedGeoModel<SwordCrucibleItem> {
	@Override
	public Identifier getModelLocation(SwordCrucibleItem object) {
		return new Identifier(DoomMod.MODID, "geo/cruciblesword.geo.json");
	}

	@Override
	public Identifier getTextureLocation(SwordCrucibleItem object) {
		return new Identifier(DoomMod.MODID, "textures/items/crucible.png");
	}

	@Override
	public Identifier getAnimationFileLocation(SwordCrucibleItem animatable) {
		return new Identifier(DoomMod.MODID, "animations/cruciblesword.animation.json");
	}
}
