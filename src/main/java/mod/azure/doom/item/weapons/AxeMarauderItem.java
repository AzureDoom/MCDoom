package mod.azure.doom.item.weapons;

import java.util.List;

import mod.azure.azurelib.Keybindings;
import mod.azure.doom.DoomMod;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
import mod.azure.doom.entity.tierboss.GladiatorEntity;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import mod.azure.doom.entity.tierboss.SpiderMastermind2016Entity;
import mod.azure.doom.entity.tierboss.SpiderMastermindEntity;
import mod.azure.doom.util.packets.DoomPacketHandler;
import mod.azure.doom.util.packets.weapons.AxeMarauderLoadingPacket;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class AxeMarauderItem extends SwordItem {

	public AxeMarauderItem() {
		super(DoomMod.DOOM_HIGHTEIR, 1, -2.5f, new Item.Properties().stacksTo(1).durability(DoomConfig.SERVER.marauder_max_uses.get()));
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("doom.marauder_axe1.text").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
		tooltip.add(Component.translatable("doom.marauder_axe2.text").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
		tooltip.add(Component.translatable("doom.marauder_axe3.text").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity miner) {
		if (miner instanceof Player playerentity) {
			if (stack.getDamageValue() < stack.getMaxDamage() - 1) {
				if (playerentity.getMainHandItem().getItem() instanceof AxeMarauderItem) {
					final AABB aabb = new AABB(miner.blockPosition().above()).inflate(4D, 1D, 4D);
					miner.getCommandSenderWorld().getEntities(miner, aabb).forEach(e -> doDamage(playerentity, e));
					stack.hurtAndBreak(1, miner, p -> p.broadcastBreakEvent(playerentity.getUsedItemHand()));
				}
			}
		}
		return stack.getDamageValue() < stack.getMaxDamage() - 1 ? true : false;
	}

	private void doDamage(LivingEntity user, final Entity target) {
		if (target instanceof LivingEntity) {
			target.invulnerableTime = 0;
			target.hurt(user.damageSources().playerAttack((Player) user), target instanceof ArchMakyrEntity || target instanceof GladiatorEntity || target instanceof IconofsinEntity || target instanceof MotherDemonEntity || target instanceof SpiderMastermind2016Entity || target instanceof SpiderMastermindEntity ? DoomConfig.SERVER.marauder_axe_item_damage.get().floatValue() / 10F : DoomConfig.SERVER.marauder_axe_item_damage.get().floatValue());
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		final Player playerentity = (Player) entityIn;
		if (worldIn.isClientSide) {
			if (playerentity.getMainHandItem().getItem() instanceof AxeMarauderItem) {
				while (Keybindings.RELOAD.consumeClick() && isSelected) {
					DoomPacketHandler.MARAUDERAXE.sendToServer(new AxeMarauderLoadingPacket(itemSlot));
				}
			}
		}
	}

	public static void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof AxeMarauderItem) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(DoomItems.ARGENT_BLOCK.get()) > 0) {
				removeAmmo(DoomItems.ARGENT_BLOCK.get(), user);
				user.getItemInHand(hand).hurtAndBreak(-5, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
			}
		}
	}

	public static void removeAmmo(Item ammo, Player playerEntity) {
		if (!playerEntity.isCreative()) {
			for (final ItemStack item : playerEntity.getInventory().offhand) {
				if (item.getItem() == ammo) {
					item.shrink(1);
					break;
				}
				for (final ItemStack item1 : playerEntity.getInventory().items) {
					if (item1.getItem() == ammo) {
						item1.shrink(1);
						break;
					}
				}
			}
		}
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return false;
	}

	@Override
	public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
		stack.hasTag();
		stack.enchant(Enchantments.SMITE, 10);
		stack.enchant(Enchantments.MOB_LOOTING, 10);
		stack.enchant(Enchantments.SHARPNESS, 10);
		stack.enchant(Enchantments.SWEEPING_EDGE, 10);
	}

}