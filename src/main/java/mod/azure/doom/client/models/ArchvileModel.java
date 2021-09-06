package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.ArchvileEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ArchvileModel extends AnimatedGeoModel<ArchvileEntity> {

	private static final Identifier[] TEX = {
			new Identifier(DoomMod.MODID, "textures/entity/archvile_flame_1.png"),
			new Identifier(DoomMod.MODID, "textures/entity/archvile_flame_2.png"),
			new Identifier(DoomMod.MODID, "textures/entity/archvile_flame_3.png"),
			new Identifier(DoomMod.MODID, "textures/entity/archvile_flame_4.png"),
			new Identifier(DoomMod.MODID, "textures/entity/archvile_flame_5.png"),
			new Identifier(DoomMod.MODID, "textures/entity/archvile_flame_6.png"),
			new Identifier(DoomMod.MODID, "textures/entity/archvile_flame_7.png"),
			new Identifier(DoomMod.MODID, "textures/entity/archvile_flame_8.png") };

	@Override
	public Identifier getModelLocation(ArchvileEntity object) {
		return new Identifier(DoomMod.MODID, "geo/archvile.geo.json");
	}

	@Override
	public Identifier getTextureLocation(ArchvileEntity object) {
		return (object.getAttckingState() == 1 ? TEX[(object.getFlameTimer())]
				: new Identifier(DoomMod.MODID, "textures/entity/archvile.png"));
	}

	@Override
	public Identifier getAnimationFileLocation(ArchvileEntity object) {
		return new Identifier(DoomMod.MODID, "animations/archvile_animation.json");
	}
}