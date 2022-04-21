package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.ArchvileEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedTickingGeoModel;

public class ArchvileModel extends AnimatedTickingGeoModel<ArchvileEntity> {

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
	public Identifier getModelResource(ArchvileEntity object) {
		return new Identifier(DoomMod.MODID,
				"geo/" + (object.getVariant() == 1 ? "archvile" : "archvileeternal") + ".geo.json");
	}

	@Override
	public Identifier getTextureResource(ArchvileEntity object) {
		return (object.getVariant() == 1
				? (object.getAttckingState() == 1 ? TEX[(object.getFlameTimer())]
						: new Identifier(DoomMod.MODID, "textures/entity/archvile.png"))
				: new Identifier(DoomMod.MODID, "textures/entity/archvileeternal.png"));
	}

	@Override
	public Identifier getAnimationResource(ArchvileEntity object) {
		return new Identifier(DoomMod.MODID,
				"animations/" + (object.getVariant() == 1 ? "archvile_" : "archvileeternal.") + "animation.json");
	}
}