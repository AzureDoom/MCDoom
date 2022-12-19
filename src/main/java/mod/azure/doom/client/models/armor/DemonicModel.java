package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.DemonicDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class DemonicModel extends GeoModel<DemonicDoomArmor> {
	@Override
	public Identifier getModelResource(DemonicDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doom1armor.geo.json");
	}

	@Override
	public Identifier getTextureResource(DemonicDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/demonic_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationResource(DemonicDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}