package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.ClassicDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedGeoModel;

public class ClassicModel extends AnimatedGeoModel<ClassicDoomArmor> {
	@Override
	public Identifier getModelResource(ClassicDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/classicarmor.geo.json");
	}

	@Override
	public Identifier getTextureResource(ClassicDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/classic_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationResource(ClassicDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}