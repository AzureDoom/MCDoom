package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.DarkLordArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DarkLordModel extends AnimatedGeoModel<DarkLordArmor> {
	@Override
	public ResourceLocation getModelResource(DarkLordArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/darklordarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DarkLordArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/darklordarmor.png");
	}

	@Override
	public ResourceLocation getAnimationResource(DarkLordArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/darklordarmor.animation.json");
	}
}