package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.DarkLordArmor;
import net.minecraft.resources.ResourceLocation;

public class DarkLordModel extends GeoModel<DarkLordArmor> {
	@Override
	public ResourceLocation getModelResource(DarkLordArmor object) {
		return DoomMod.modResource("geo/darklordarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DarkLordArmor object) {
		return DoomMod.modResource("textures/models/armor/darklordarmor.png");
	}

	@Override
	public ResourceLocation getAnimationResource(DarkLordArmor animatable) {
		return DoomMod.modResource("animations/darklordarmor.animation.json");
	}
}