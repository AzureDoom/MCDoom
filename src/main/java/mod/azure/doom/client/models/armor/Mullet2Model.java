package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.Mullet2DoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class Mullet2Model extends GeoModel<Mullet2DoomArmor> {
	@Override
	public Identifier getModelResource(Mullet2DoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/mulletarmor.geo.json");
	}

	@Override
	public Identifier getTextureResource(Mullet2DoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/redneck2_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationResource(Mullet2DoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}