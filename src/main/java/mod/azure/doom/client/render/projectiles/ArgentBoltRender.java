package mod.azure.doom.client.render.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.ArgentBoltEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class ArgentBoltRender extends ProjectileEntityRenderer<ArgentBoltEntity> {

	private static final Identifier ARGENT_BOLT_TEXTURE = new Identifier(DoomMod.MODID,
			"textures/entity/projectiles/argent_bolt.png");

	public ArgentBoltRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public Identifier getTexture(ArgentBoltEntity entity) {
		return ARGENT_BOLT_TEXTURE;
	}

}