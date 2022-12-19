package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.MulletDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class Mullet1Model extends GeoModel<MulletDoomArmor> {
	@Override
	public Identifier getModelResource(MulletDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/mulletarmor.geo.json");
	}

	@Override
	public Identifier getTextureResource(MulletDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/redneck1_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationResource(MulletDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}