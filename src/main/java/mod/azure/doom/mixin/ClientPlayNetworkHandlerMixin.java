package mod.azure.doom.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.doom.entity.projectiles.MeatHookEntity;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;

@Mixin(ClientPacketListener.class)
public class ClientPlayNetworkHandlerMixin {
	@Shadow
	private ClientLevel level;

	@Inject(method = "handleAddEntity", at = @At("TAIL"))
	private void onEntitySpawn(ClientboundAddEntityPacket packet, CallbackInfo callbackInfo) {
		EntityType<?> type = packet.getType();
		double x = packet.getX();
		double y = packet.getY();
		double z = packet.getZ();
		AbstractArrow entity = null;

		if (type == ModEntityTypes.MEATHOOOK_ENTITY.get())
			entity = new MeatHookEntity(level, x, y, z);

		if (entity != null) {
			Entity owner = level.getEntity(packet.getData());

			if (owner != null)
				entity.setOwner(owner);

			int id = packet.getId();
			entity.setPacketCoordinates(x, y, z);
			entity.moveTo(x, y, z);
			entity.setXRot((float) (packet.getxRot() * 360) / 256f);
			entity.setYRot((float) (packet.getyRot() * 360) / 256f);
			entity.setId(id);
			entity.setUUID(packet.getUUID());
			level.addEntity(id, entity);
		}
	}
}