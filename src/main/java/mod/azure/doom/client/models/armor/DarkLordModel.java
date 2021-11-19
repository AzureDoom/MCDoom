package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.DarkLordArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DarkLordModel extends AnimatedGeoModel<DarkLordArmor> {
	@Override
	public Identifier getModelLocation(DarkLordArmor object) {
		return new Identifier(DoomMod.MODID, "geo/darklordarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(DarkLordArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/darklordarmor.png");
	}

	@Override
	public Identifier getAnimationFileLocation(DarkLordArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/darklordarmor.animation.json");
	}
}