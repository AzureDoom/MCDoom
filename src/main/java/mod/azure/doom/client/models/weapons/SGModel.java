package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.Shotgun;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SGModel extends AnimatedGeoModel<Shotgun> {
	@Override
	public Identifier getModelLocation(Shotgun object) {
		return new Identifier(DoomMod.MODID, "geo/shotgun.geo.json");
	}

	@Override
	public Identifier getTextureLocation(Shotgun object) {
		return new Identifier(DoomMod.MODID, "textures/items/shotgun.png");
	}

	@Override
	public Identifier getAnimationFileLocation(Shotgun animatable) {
		return new Identifier(DoomMod.MODID, "animations/shotgun.animation.json");
	}
}
