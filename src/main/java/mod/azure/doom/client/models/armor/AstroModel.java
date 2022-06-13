package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.AstroDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AstroModel extends AnimatedGeoModel<AstroDoomArmor> {
	@Override
	public Identifier getModelResource(AstroDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public Identifier getTextureResource(AstroDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/astro_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationResource(AstroDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}