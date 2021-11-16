package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.ArchvileEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class ArchvileModel extends AnimatedTickingGeoModel<ArchvileEntity> {

	private static final ResourceLocation[] TEX = {
			new ResourceLocation(DoomMod.MODID, "textures/entity/archvile_flame_1.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/archvile_flame_2.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/archvile_flame_3.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/archvile_flame_4.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/archvile_flame_5.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/archvile_flame_6.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/archvile_flame_7.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/archvile_flame_8.png") };

	@Override
	public ResourceLocation getModelLocation(ArchvileEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/archvile.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ArchvileEntity object) {
		return (object.getAttckingState() == 1 ? TEX[(object.getFlameTimer())]
				: new ResourceLocation(DoomMod.MODID, "textures/entity/archvile.png"));
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ArchvileEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/archvile_animation.json");
	}
}