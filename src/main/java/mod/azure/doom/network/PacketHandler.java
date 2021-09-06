package mod.azure.doom.network;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.AxeMarauderItem;
import mod.azure.doom.item.weapons.BFG;
import mod.azure.doom.item.weapons.BFG9000;
import mod.azure.doom.item.weapons.Ballista;
import mod.azure.doom.item.weapons.Chaingun;
import mod.azure.doom.item.weapons.Chainsaw;
import mod.azure.doom.item.weapons.ChainsawAnimated;
import mod.azure.doom.item.weapons.DarkLordCrucibleItem;
import mod.azure.doom.item.weapons.HeavyCannon;
import mod.azure.doom.item.weapons.PistolItem;
import mod.azure.doom.item.weapons.PlasmaGun;
import mod.azure.doom.item.weapons.RocketLauncher;
import mod.azure.doom.item.weapons.SentinelHammerItem;
import mod.azure.doom.item.weapons.Shotgun;
import mod.azure.doom.item.weapons.SuperShotgun;
import mod.azure.doom.item.weapons.SwordCrucibleItem;
import mod.azure.doom.item.weapons.Unmaykr;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class PacketHandler {

	public static final Identifier lock_slot = new Identifier(DoomMod.MODID, "select_craft");

	public static void registerMessages() {
		ServerPlayNetworking.registerGlobalReceiver(lock_slot, new C2SMessageSelectCraft());
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.FALL_DISTANCE_PACKET_ID,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					float fallDistance = inputPacket.readFloat();
					player.fallDistance = fallDistance;
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.CRUCIBLE,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof SwordCrucibleItem) {
						((SwordCrucibleItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.MARAUDERAXE,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof AxeMarauderItem) {
						((AxeMarauderItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.CHAINSAW_ETERNAL,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof ChainsawAnimated) {
						((ChainsawAnimated) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.CHAINSAW,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof Chainsaw) {
						((Chainsaw) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.BALLISTA,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof Ballista) {
						((Ballista) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.BFG,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof BFG) {
						((BFG) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.BFG9000,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof BFG9000) {
						((BFG9000) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.CHAINGUN,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof Chaingun) {
						((Chaingun) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.PISTOL,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof PistolItem) {
						((PistolItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.PLASMA,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof PlasmaGun) {
						((PlasmaGun) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.ROCKETLAUNCHER,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof RocketLauncher) {
						((RocketLauncher) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.SHOTGUN,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof Shotgun) {
						((Shotgun) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.SUPERSHOTGUN,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof SuperShotgun) {
						((SuperShotgun) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.UNMAYKR,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof Unmaykr) {
						((Unmaykr) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.HEAVYCANNON,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof HeavyCannon) {
						((HeavyCannon) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.SENTINELHAMMER,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof SentinelHammerItem) {
						((SentinelHammerItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.DARKLORDCRUCIBLE,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandStack().getItem() instanceof DarkLordCrucibleItem) {
						((DarkLordCrucibleItem) player.getMainHandStack().getItem()).reload(player, Hand.MAIN_HAND);
					}
					;
				});
	}
}
