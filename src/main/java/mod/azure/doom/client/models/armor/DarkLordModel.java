package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.DarkLordArmor;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DarkLordModel extends AnimatedGeoModel<DarkLordArmor> {
	@Override
	public ResourceLocation getModelLocation(DarkLordArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/darklordarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(DarkLordArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/darklordarmor.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(DarkLordArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/darklordarmor.animation.json");
	}
}