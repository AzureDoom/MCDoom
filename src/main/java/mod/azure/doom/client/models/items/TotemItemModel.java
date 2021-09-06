package mod.azure.doom.client.models.items;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.DoomBlockItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TotemItemModel extends AnimatedGeoModel<DoomBlockItem> {
	@Override
	public Identifier getAnimationFileLocation(DoomBlockItem entity) {
		return new Identifier(DoomMod.MODID, "animations/totem.animation.json");
	}

	@Override
	public Identifier getModelLocation(DoomBlockItem animatable) {
		return new Identifier(DoomMod.MODID, "geo/totem.geo.json");
	}

	@Override
	public Identifier getTextureLocation(DoomBlockItem entity) {
		return new Identifier(DoomMod.MODID, "textures/blocks/totem.png");
	}
}
