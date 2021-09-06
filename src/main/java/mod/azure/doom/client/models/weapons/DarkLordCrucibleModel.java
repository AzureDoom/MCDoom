package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.DarkLordCrucibleItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DarkLordCrucibleModel extends AnimatedGeoModel<DarkLordCrucibleItem> {
	@Override
	public Identifier getModelLocation(DarkLordCrucibleItem object) {
		return new Identifier(DoomMod.MODID, "geo/darklordcrucible.geo.json");
	}

	@Override
	public Identifier getTextureLocation(DarkLordCrucibleItem object) {
		return new Identifier(DoomMod.MODID, "textures/items/darklordcrucible.png");
	}

	@Override
	public Identifier getAnimationFileLocation(DarkLordCrucibleItem animatable) {
		return new Identifier(DoomMod.MODID, "animations/cruciblesword.animation.json");
	}
}
