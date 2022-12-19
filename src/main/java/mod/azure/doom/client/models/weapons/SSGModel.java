package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.SuperShotgun;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SSGModel extends GeoModel<SuperShotgun> {
	@Override
	public Identifier getModelResource(SuperShotgun object) {
		return new Identifier(DoomMod.MODID, "geo/supershotgun.geo.json");
	}

	@Override
	public Identifier getTextureResource(SuperShotgun object) {
		return new Identifier(DoomMod.MODID, "textures/item/supershotgun.png");
	}

	@Override
	public Identifier getAnimationResource(SuperShotgun animatable) {
		return new Identifier(DoomMod.MODID, "animations/supershotgun.animation.json");
	}
}
