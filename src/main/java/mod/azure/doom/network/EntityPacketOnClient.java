package mod.azure.doom.network;

import java.util.UUID;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.registry.Registry;

public class EntityPacketOnClient {
	public static void onPacket(MinecraftClient context, PacketByteBuf byteBuf) {
		EntityType<?> type = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
		UUID entityUUID = byteBuf.readUuid();
		int entityID = byteBuf.readVarInt();
		double x = byteBuf.readDouble();
		double y = byteBuf.readDouble();
		double z = byteBuf.readDouble();
		float pitch = (byteBuf.readByte() * 360) / 256.0F;
		float yaw = (byteBuf.readByte() * 360) / 256.0F;
		context.execute(() -> {
			ClientWorld world = MinecraftClient.getInstance().world;
			Entity entity = type.create(world);
			if (entity != null) {
				entity.updatePosition(x, y, z);
				entity.updateTrackedPosition(x, y, z);
				entity.setPitch(pitch);
				entity.setYaw(yaw);
				entity.setId(entityID);
				entity.setUuid(entityUUID);
				world.addEntity(entityID, entity);
			}
		});
	}
}