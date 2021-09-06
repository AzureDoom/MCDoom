package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.SuperShotgun;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SSGModel extends AnimatedGeoModel<SuperShotgun> {
	@Override
	public Identifier getModelLocation(SuperShotgun object) {
		return new Identifier(DoomMod.MODID, "geo/supershotgun.geo.json");
	}

	@Override
	public Identifier getTextureLocation(SuperShotgun object) {
		return new Identifier(DoomMod.MODID, "textures/items/supershotgun.png");
	}

	@Override
	public Identifier getAnimationFileLocation(SuperShotgun animatable) {
		return new Identifier(DoomMod.MODID, "animations/supershotgun.animation.json");
	}
}
