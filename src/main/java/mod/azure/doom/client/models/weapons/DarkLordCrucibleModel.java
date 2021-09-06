package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.DarkLordCrucibleItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DarkLordCrucibleModel extends AnimatedGeoModel<DarkLordCrucibleItem> {
	@Override
	public ResourceLocation getModelLocation(DarkLordCrucibleItem object) {
		return new ResourceLocation(DoomMod.MODID, "geo/darklordcrucible.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(DarkLordCrucibleItem object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/darklordcrucible.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(DarkLordCrucibleItem animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/cruciblesword.animation.json");
	}
}
