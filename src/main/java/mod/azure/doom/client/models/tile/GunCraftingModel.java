package mod.azure.doom.client.models.tile;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tileentity.GunBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GunCraftingModel extends GeoModel<GunBlockEntity> {
	@Override
	public Identifier getAnimationResource(GunBlockEntity entity) {
		return new Identifier(DoomMod.MODID, "animations/gun_table.animation.json");
	}

	@Override
	public Identifier getModelResource(GunBlockEntity animatable) {
		return new Identifier(DoomMod.MODID, "geo/gun_table.geo.json");
	}

	@Override
	public Identifier getTextureResource(GunBlockEntity entity) {
		return new Identifier(DoomMod.MODID, "textures/block/gun_table.png");
	}
	
	@Override
	public RenderLayer getRenderType(GunBlockEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
