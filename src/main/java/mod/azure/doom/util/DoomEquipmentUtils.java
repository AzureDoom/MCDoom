package mod.azure.doom.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DoomEquipmentUtils {
	public static String TAG = "doomguntag";

	public static boolean ruinedItemHasEnchantment(ItemStack ruinedItem, Enchantment enchantment) {
		if (ruinedItem.getTag() == null)
			return false;
		String tagString = ruinedItem.getTag().getString(TAG);
		Map<Enchantment, Integer> enchantMap = DoomEquipmentUtils.processEncodedEnchantments(tagString);
		if (enchantMap == null)
			return false;
		for (Enchantment e : enchantMap.keySet()) {
			if (e == enchantment)
				return true;
		}
		return false;
	}

	public static int compareItemsById(Item i1, Item i2) {
		return Registry.ITEM.getId(i1).compareTo(Registry.ITEM.getId(i2));
	}

	public static int generateRepairLevelCost(ItemStack repaired, int maxLevel) {
		int targetLevel = maxLevel * (repaired.getMaxDamage() - repaired.getDamage()) / repaired.getMaxDamage();
		return Math.max(targetLevel, 1);
	}

	public static ItemStack generateRepairedItemForAnvilByFraction(ItemStack leftStack, double damageFraction) {
		int maxDamage = new ItemStack(DoomItems.getItemMap().get(leftStack.getItem())).getMaxDamage();
		return generateRepairedItemForAnvilByDamage(leftStack, (int) (damageFraction * (double) maxDamage));
	}

	public static ItemStack generateRepairedItemForAnvilByDamage(ItemStack leftStack, int targetDamage) {
		ItemStack repaired = new ItemStack(DoomItems.getItemMap().get(leftStack.getItem()));
		NbtCompound tag = leftStack.getOrCreateTag();
		String encodedEnch = tag.getString(TAG);
		if (!encodedEnch.isEmpty())
			tag.remove(TAG);
		Map<Enchantment, Integer> enchantMap = DoomEquipmentUtils.processEncodedEnchantments(encodedEnch);
		if (enchantMap != null) {
			for (Map.Entry<Enchantment, Integer> enchant : enchantMap.entrySet()) {
				repaired.addEnchantment(enchant.getKey(), enchant.getValue());
			}
		}
		repaired.setTag(repaired.getOrCreateTag().copyFrom(tag));
		repaired.setDamage(targetDamage);
		return repaired;
	}

	public static Map<Enchantment, Integer> processEncodedEnchantments(String encodedEnchants) {
		if (encodedEnchants.isEmpty())
			return null;
		Map<Enchantment, Integer> enchants = new HashMap<>();
		for (String encodedEnchant : encodedEnchants.split(",")) {
			String[] enchantItem = encodedEnchant.split(">");
			String[] enchantKey = enchantItem[0].split(":");
			int enchantLevel = Integer.parseInt(enchantItem[1]);
			enchants.put(Registry.ENCHANTMENT.get(new Identifier(enchantKey[0], enchantKey[1])), enchantLevel);
		}
		return enchants.isEmpty() ? null : enchants;
	}

	public static void onSendEquipmentBreakStatusImpl(ServerPlayerEntity serverPlayer, ItemStack breakingStack,
			boolean forceSet) {
		for (Map.Entry<Item, Item> itemMap : DoomItems.getItemMap().entrySet()) {
			if (isVanillaItemStackBreaking(breakingStack, itemMap.getValue())) {
				// Directly copy over breaking Item's NBT, removing specific fields
				ItemStack ruinedStack = new ItemStack(itemMap.getKey());
				NbtCompound breakingNBT = breakingStack.getOrCreateTag();
				if (breakingNBT.contains("Damage"))
					breakingNBT.remove("Damage");
				if (breakingNBT.contains("RepairCost"))
					breakingNBT.remove("RepairCost");
				// Set enchantment NBT data
				NbtCompound enchantTag = getTagForEnchantments(breakingStack, ruinedStack);
				if (enchantTag != null)
					breakingNBT.copyFrom(enchantTag);
				if (breakingNBT.contains("Enchantments"))
					breakingNBT.remove("Enchantments");
				ruinedStack.setTag(breakingNBT);
				serverPlayer.inventory.offerOrDrop(serverPlayer.world, ruinedStack);
			}
		}
	}

	public static NbtCompound getTagForEnchantments(ItemStack breakingStack, ItemStack ruinedStack) {
		Set<String> enchantmentStrings = new HashSet<>();
		for (Map.Entry<Enchantment, Integer> ench : EnchantmentHelper.get(breakingStack).entrySet()) {
			String enchantString = Registry.ENCHANTMENT.getId(ench.getKey()) + ">" + ench.getValue();
			enchantmentStrings.add(enchantString);
		}
		if (!enchantmentStrings.isEmpty()) {
			NbtCompound tag = ruinedStack.getTag();
			if (tag == null)
				tag = new NbtCompound();
			tag.putString(TAG, String.join(",", enchantmentStrings));
			return tag;
		}
		return null;
	}

	public static boolean isVanillaItemStackBreaking(ItemStack breakingStack, Item vanillaItem) {
		return breakingStack.isItemEqualIgnoreDamage(new ItemStack(vanillaItem))
				&& breakingStack.getMaxDamage() - breakingStack.getDamage() <= 0;
	}
}
