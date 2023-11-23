package mod.azure.doom.mixins;

import mod.azure.doom.entities.projectiles.MeatHookEntity;
import mod.azure.doom.platform.Services;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPlayNetworkHandlerMixin {
    @Shadow
    private ClientLevel level;

    @Inject(method = "handleAddEntity", at = @At("TAIL"))
    private void onEntitySpawn(ClientboundAddEntityPacket packet, CallbackInfo callbackInfo) {
        final EntityType<?> type = packet.getType();
        final double x = packet.getX();
        final double y = packet.getY();
        final double z = packet.getZ();
        AbstractArrow entity = null;

        if (type == Services.ENTITIES_HELPER.getMeatHookEntity()) entity = new MeatHookEntity(level, x, y, z);

        if (entity != null) {
            final Entity owner = level.getEntity(packet.getData());

            if (owner != null) entity.setOwner(owner);

            final int id = packet.getId();
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