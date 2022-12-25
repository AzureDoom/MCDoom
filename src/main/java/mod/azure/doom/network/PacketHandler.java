package mod.azure.doom.network;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.AxeMarauderItem;
import mod.azure.doom.item.weapons.BFG;
import mod.azure.doom.item.weapons.BFG9000;
import mod.azure.doom.item.weapons.Ballista;
import mod.azure.doom.item.weapons.Chaingun;
import mod.azure.doom.item.weapons.Chainsaw;
import mod.azure.doom.item.weapons.ChainsawAnimated;
import mod.azure.doom.item.weapons.DGauss;
import mod.azure.doom.item.weapons.DPlasmaRifle;
import mod.azure.doom.item.weapons.DShotgun;
import mod.azure.doom.item.weapons.DarkLordCrucibleItem;
import mod.azure.doom.item.weapons.HeavyCannon;
import mod.azure.doom.item.weapons.PistolItem;
import mod.azure.doom.item.weapons.PlasmaGun;
import mod.azure.doom.item.weapons.RocketLauncher;
import mod.azure.doom.item.weapons.SentinelHammerItem;
import mod.azure.doom.item.weapons.Shotgun;
import mod.azure.doom.item.weapons.SuperShotgun;
import mod.azure.doom.item.weapons.SwordCrucibleItem;
import mod.azure.doom.item.weapons.Unmaker;
import mod.azure.doom.item.weapons.Unmaykr;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;

public class PacketHandler {

	public static final ResourceLocation lock_slot = new ResourceLocation(DoomMod.MODID, "select_craft");

	public static void registerMessages() {
		ServerPlayNetworking.registerGlobalReceiver(lock_slot, new C2SMessageSelectCraft());
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.CRUCIBLE,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof SwordCrucibleItem)
						SwordCrucibleItem.reload(player, InteractionHand.MAIN_HAND);
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.MARAUDERAXE,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof AxeMarauderItem)
						AxeMarauderItem.reload(player, InteractionHand.MAIN_HAND);
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.CHAINSAW_ETERNAL,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof ChainsawAnimated)
						ChainsawAnimated.reload(player, InteractionHand.MAIN_HAND);
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.CHAINSAW,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof Chainsaw)
						Chainsaw.reload(player, InteractionHand.MAIN_HAND);
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.BALLISTA,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof Ballista)
						Ballista.reload(player, InteractionHand.MAIN_HAND);
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.BFG,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof BFG)
						BFG.reload(player, InteractionHand.MAIN_HAND);
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.BFG9000,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof BFG9000)
						BFG9000.reload(player, InteractionHand.MAIN_HAND);
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.CHAINGUN,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof Chaingun)
						Chaingun.reload(player, InteractionHand.MAIN_HAND);
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.PISTOL,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof PistolItem)
						PistolItem.reload(player, InteractionHand.MAIN_HAND);
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.PLASMA,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof PlasmaGun)
						PlasmaGun.reload(player, InteractionHand.MAIN_HAND);
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.ROCKETLAUNCHER,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof RocketLauncher)
						RocketLauncher.reload(player, InteractionHand.MAIN_HAND);
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.SHOTGUN,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof Shotgun)
						Shotgun.reload(player, InteractionHand.MAIN_HAND);
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.SUPERSHOTGUN,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof SuperShotgun)
						SuperShotgun.reload(player, InteractionHand.MAIN_HAND);
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.UNMAYKR,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof Unmaykr)
						Unmaykr.reload(player, InteractionHand.MAIN_HAND);
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.UNMAKER,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof Unmaker)
						Unmaker.reload(player, InteractionHand.MAIN_HAND);
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.HEAVYCANNON,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof HeavyCannon)
						HeavyCannon.reload(player, InteractionHand.MAIN_HAND);
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.SENTINELHAMMER,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof SentinelHammerItem)
						SentinelHammerItem.reload(player, InteractionHand.MAIN_HAND);
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.DARKLORDCRUCIBLE,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof DarkLordCrucibleItem)
						DarkLordCrucibleItem.reload(player, InteractionHand.MAIN_HAND);
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.DSG,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof DShotgun)
						DShotgun.reload(player, InteractionHand.MAIN_HAND);
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.DGAUSS,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof DGauss)
						DGauss.reload(player, InteractionHand.MAIN_HAND);
				});
		ServerPlayNetworking.registerGlobalReceiver(DoomMod.DPLASMARIFLE,
				(server, player, serverPlayNetworkHandler, inputPacket, packetSender) -> {
					if (player.getMainHandItem().getItem() instanceof DPlasmaRifle)
						DPlasmaRifle.reload(player, InteractionHand.MAIN_HAND);
				});
	}
}
