package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.SantaDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedGeoModel;

public class SantaModel extends AnimatedGeoModel<SantaDoomArmor> {
	@Override
	public Identifier getModelResource(SantaDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/santaarmor.geo.json");
	}

	@Override
	public Identifier getTextureResource(SantaDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/santa_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationResource(SantaDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}