package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.SuperShotgun;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedGeoModel;

public class SSGModel extends AnimatedGeoModel<SuperShotgun> {
	@Override
	public Identifier getModelResource(SuperShotgun object) {
		return new Identifier(DoomMod.MODID, "geo/supershotgun.geo.json");
	}

	@Override
	public Identifier getTextureResource(SuperShotgun object) {
		return new Identifier(DoomMod.MODID, "textures/items/supershotgun.png");
	}

	@Override
	public Identifier getAnimationResource(SuperShotgun animatable) {
		return new Identifier(DoomMod.MODID, "animations/supershotgun.animation.json");
	}
}
