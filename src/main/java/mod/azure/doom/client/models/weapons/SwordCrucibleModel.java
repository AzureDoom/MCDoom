package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.SwordCrucibleItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SwordCrucibleModel extends AnimatedGeoModel<SwordCrucibleItem> {
	@Override
	public ResourceLocation getModelLocation(SwordCrucibleItem object) {
		return new ResourceLocation(DoomMod.MODID, "geo/cruciblesword.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SwordCrucibleItem object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/crucible.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(SwordCrucibleItem animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/cruciblesword.animation.json");
	}
}
