package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.ClassicIndigoDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ClassicIndigoModel extends GeoModel<ClassicIndigoDoomArmor> {
	@Override
	public Identifier getModelResource(ClassicIndigoDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/classicarmor.geo.json");
	}

	@Override
	public Identifier getTextureResource(ClassicIndigoDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/classic_indigo_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationResource(ClassicIndigoDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}