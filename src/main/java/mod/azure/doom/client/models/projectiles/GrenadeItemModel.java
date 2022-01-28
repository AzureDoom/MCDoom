package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.GrenadeItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GrenadeItemModel extends AnimatedGeoModel<GrenadeItem> {
	@Override
	public Identifier getModelLocation(GrenadeItem object) {
		return new Identifier(DoomMod.MODID, "geo/doomed_grenade.geo.json");
	}

	@Override
	public Identifier getTextureLocation(GrenadeItem object) {
		return new Identifier(DoomMod.MODID, "textures/items/doomed_grenade.png");
	}

	@Override
	public Identifier getAnimationFileLocation(GrenadeItem animatable) {
		return new Identifier(DoomMod.MODID, "animations/doomed_grenade.animation.json");
	}
}
