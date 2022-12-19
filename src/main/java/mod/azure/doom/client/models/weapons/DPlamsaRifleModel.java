package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.DPlasmaRifle;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class DPlamsaRifleModel extends GeoModel<DPlasmaRifle> {
	@Override
	public Identifier getModelResource(DPlasmaRifle object) {
		return new Identifier(DoomMod.MODID, "geo/doomed_plasma_rifle.geo.json");
	}

	@Override
	public Identifier getTextureResource(DPlasmaRifle object) {
		return new Identifier(DoomMod.MODID, "textures/item/doomed_plasma_rifle.png");
	}

	@Override
	public Identifier getAnimationResource(DPlasmaRifle animatable) {
		return new Identifier(DoomMod.MODID, "animations/doomed_plasma_rifle.animation.json");
	}
}
