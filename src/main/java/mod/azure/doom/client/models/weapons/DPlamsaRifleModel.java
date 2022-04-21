package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.DPlasmaRifle;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedGeoModel;

public class DPlamsaRifleModel extends AnimatedGeoModel<DPlasmaRifle> {
	@Override
	public Identifier getModelResource(DPlasmaRifle object) {
		return new Identifier(DoomMod.MODID, "geo/doomed_plasma_rifle.geo.json");
	}

	@Override
	public Identifier getTextureResource(DPlasmaRifle object) {
		return new Identifier(DoomMod.MODID, "textures/items/doomed_plasma_rifle.png");
	}

	@Override
	public Identifier getAnimationResource(DPlasmaRifle animatable) {
		return new Identifier(DoomMod.MODID, "animations/doomed_plasma_rifle.animation.json");
	}
}
