package mod.azure.doom.client.render.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.ArgentBoltEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ArgentBoltRender extends ProjectileEntityRenderer<ArgentBoltEntity> {

	private static final Identifier ARGENT_BOLT_TEXTURE = new Identifier(DoomMod.MODID,
			"textures/entity/projectiles/argent_bolt.png");

	public ArgentBoltRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public Identifier getTexture(ArgentBoltEntity entity) {
		return ARGENT_BOLT_TEXTURE;
	}
	
	@Override
	public void render(ArgentBoltEntity persistentProjectileEntity, float f, float g, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int i) {
		super.render(persistentProjectileEntity, f, g, matrixStack, vertexConsumerProvider, i);
		matrixStack.push();
		matrixStack.scale(0, 0, 0);
		matrixStack.pop();
	}

}