package mod.azure.doom.client.render.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.UnmaykrBoltEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class UnmaykrBulletRender extends ProjectileEntityRenderer<UnmaykrBoltEntity> {

	private static final Identifier ARGENT_BOLT_TEXTURE = new Identifier(DoomMod.MODID,
			"textures/entity/projectiles/argent_bolt.png");

	public UnmaykrBulletRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public Identifier getTexture(UnmaykrBoltEntity entity) {
		return ARGENT_BOLT_TEXTURE;
	}

}