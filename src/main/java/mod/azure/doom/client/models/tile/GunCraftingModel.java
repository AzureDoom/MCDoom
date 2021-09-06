package mod.azure.doom.client.models.tile;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tileentity.GunBlockEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GunCraftingModel extends AnimatedGeoModel<GunBlockEntity> {
	@Override
	public ResourceLocation getAnimationFileLocation(GunBlockEntity entity) {
		return new ResourceLocation(DoomMod.MODID, "animations/gun_table.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(GunBlockEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "geo/gun_table.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(GunBlockEntity entity) {
		return new ResourceLocation(DoomMod.MODID, "textures/blocks/gun_table.png");
	}
}
