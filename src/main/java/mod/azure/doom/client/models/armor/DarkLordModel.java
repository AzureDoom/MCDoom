package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.DarkLordArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class DarkLordModel extends GeoModel<DarkLordArmor> {
	@Override
	public Identifier getModelResource(DarkLordArmor object) {
		return new Identifier(DoomMod.MODID, "geo/darklordarmor.geo.json");
	}

	@Override
	public Identifier getTextureResource(DarkLordArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/darklordarmor.png");
	}

	@Override
	public Identifier getAnimationResource(DarkLordArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/darklordarmor.animation.json");
	}
}