package mod.azure.doom.client.render.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.ArgentBoltEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ArgentBoltRender extends ArrowRenderer<ArgentBoltEntity> {

	private static final ResourceLocation ARGENT_BOLT_TEXTURE = new ResourceLocation(DoomMod.MODID,
			"textures/entity/projectiles/argent_bolt.png");

	public ArgentBoltRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public ResourceLocation getTextureLocation(ArgentBoltEntity entity) {
		return ARGENT_BOLT_TEXTURE;
	}

}