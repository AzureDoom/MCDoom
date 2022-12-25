package mod.azure.doom.network;

import io.netty.buffer.Unpooled;
import mod.azure.doom.DoomMod;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class DoomEntityPacket {
	public static final ResourceLocation ID = new ResourceLocation(DoomMod.MODID, "spawn_entity");

	public static Packet<ClientGamePacketListener> createPacket(Entity entity) {
		FriendlyByteBuf buf = createBuffer();
		buf.writeVarInt(BuiltInRegistries.ENTITY_TYPE.getId(entity.getType()));
		buf.writeUUID(entity.getUUID());
		buf.writeVarInt(entity.getId());
		buf.writeDouble(entity.getX());
		buf.writeDouble(entity.getY());
		buf.writeDouble(entity.getZ());
		buf.writeByte(Mth.floor(entity.getXRot() * 256.0F / 360.0F));
		buf.writeByte(Mth.floor(entity.getYRot() * 256.0F / 360.0F));
		buf.writeFloat(entity.getXRot());
		buf.writeFloat(entity.getYRot());
		return ServerPlayNetworking.createS2CPacket(ID, buf);
	}

	private static FriendlyByteBuf createBuffer() {
		return new FriendlyByteBuf(Unpooled.buffer());
	}

}