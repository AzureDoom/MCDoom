package mod.azure.doom.client.render.projectiles;

import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.projectiles.UnmaykrBoltEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class UnmaykrBulletRender extends ArrowRenderer<UnmaykrBoltEntity> {

	private static final ResourceLocation ARGENT_BOLT_TEXTURE = MCDoom.modResource("textures/entity/projectiles/argent_bolt.png");

	public UnmaykrBulletRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public ResourceLocation getTextureLocation(UnmaykrBoltEntity entity) {
		return ARGENT_BOLT_TEXTURE;
	}

}