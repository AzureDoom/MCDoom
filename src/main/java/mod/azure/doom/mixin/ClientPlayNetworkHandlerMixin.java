package mod.azure.doom.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.doom.entity.projectiles.MeatHookEntity;
import mod.azure.doom.util.registry.DoomProjectiles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.projectile.AbstractArrow;

@Mixin(ClientPacketListener.class)
public class ClientPlayNetworkHandlerMixin {
	@Shadow
	private ClientLevel level;

	@Inject(method = "handleAddEntity", at = @At("TAIL"))
	private void onEntitySpawn(ClientboundAddEntityPacket packet, CallbackInfo callbackInfo) {
		final var type = packet.getType();
		final var x = packet.getX();
		final var y = packet.getY();
		final var z = packet.getZ();
		AbstractArrow entity = null;

		if (type == DoomProjectiles.MEATHOOOK_ENTITY)
			entity = new MeatHookEntity(level, x, y, z);

		if (entity != null) {
			final var owner = level.getEntity(packet.getData());

			if (owner != null)
				entity.setOwner(owner);

			final var id = packet.getId();
			entity.syncPacketPositionCodec(x, y, z);
			entity.absMoveTo(x, y, z);
			entity.setYHeadRot(packet.getXRot() * 360 / 256f);
			entity.setYBodyRot(packet.getYRot() * 360 / 256f);
			entity.setId(id);
			entity.setUUID(packet.getUUID());
			level.putNonPlayerEntity(id, entity);
		}
	}
}