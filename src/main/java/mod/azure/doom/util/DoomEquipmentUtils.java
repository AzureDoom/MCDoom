package mod.azure.doom.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class DoomEquipmentUtils {
	public static String TAG = "doomguntag";

	public static boolean ruinedItemHasEnchantment(ItemStack ruinedItem, Enchantment enchantment) {
		if (ruinedItem.getTag() == null)
			return false;
		final var enchantMap = DoomEquipmentUtils.processEncodedEnchantments(ruinedItem.getTag().getString(TAG));
		if (enchantMap == null)
			return false;
		for (final Enchantment e : enchantMap.keySet())
			if (e == enchantment)
				return true;
		return false;
	}

	public static int compareItemsById(Item i1, Item i2) {
		return BuiltInRegistries.ITEM.getKey(i1).compareTo(BuiltInRegistries.ITEM.getKey(i2));
	}

	public static int generateRepairLevelCost(ItemStack repaired, int maxLevel) {
		final var targetLevel = maxLevel * (repaired.getMaxDamage() - repaired.getDamageValue()) / repaired.getMaxDamage();
		return Math.max(targetLevel, 1);
	}

	public static ItemStack generateRepairedItemForAnvilByFraction(ItemStack leftStack, double damageFraction) {
		final var maxDamage = new ItemStack(DoomItems.getItemMap().get(leftStack.getItem())).getMaxDamage();
		return generateRepairedItemForAnvilByDamage(leftStack, (int) (damageFraction * maxDamage));
	}

	public static ItemStack generateRepairedItemForAnvilByDamage(ItemStack leftStack, int targetDamage) {
		final var repaired = new ItemStack(DoomItems.getItemMap().get(leftStack.getItem()));
		final var tag = leftStack.getOrCreateTag();
		final var encodedEnch = tag.getString(TAG);
		if (!encodedEnch.isEmpty())
			tag.remove(TAG);
		final var enchantMap = DoomEquipmentUtils.processEncodedEnchantments(encodedEnch);
		if (enchantMap != null)
			for (final Map.Entry<Enchantment, Integer> enchant : enchantMap.entrySet())
				repaired.enchant(enchant.getKey(), enchant.getValue());
		repaired.setTag(repaired.getOrCreateTag().merge(tag));
		repaired.setDamageValue(targetDamage);
		return repaired;
	}

	public static Map<Enchantment, Integer> processEncodedEnchantments(String encodedEnchants) {
		if (encodedEnchants.isEmpty())
			return null;
		final Map<Enchantment, Integer> enchants = new HashMap<>();
		for (final var encodedEnchant : encodedEnchants.split(",")) {
			final var enchantItem = encodedEnchant.split(">");
			final var enchantKey = enchantItem[0].split(":");
			enchants.put(BuiltInRegistries.ENCHANTMENT.get(new ResourceLocation(enchantKey[0], enchantKey[1])), Integer.parseInt(enchantItem[1]));
		}
		return enchants.isEmpty() ? null : enchants;
	}

	public static void onSendEquipmentBreakStatusImpl(ServerPlayer serverPlayer, ItemStack breakingStack, boolean forceSet) {
		for (final Map.Entry<Item, Item> itemMap : DoomItems.getItemMap().entrySet()) {
			if (isVanillaItemStackBreaking(breakingStack, itemMap.getValue())) {
				final var ruinedStack = new ItemStack(itemMap.getKey());
				final var breakingNBT = breakingStack.getOrCreateTag();
				if (breakingNBT.contains("Damage"))
					breakingNBT.remove("Damage");
				if (breakingNBT.contains("RepairCost"))
					breakingNBT.remove("RepairCost");
				final var enchantTag = getTagForEnchantments(breakingStack, ruinedStack);
				if (enchantTag != null)
					breakingNBT.merge(enchantTag);
				if (breakingNBT.contains("Enchantments"))
					breakingNBT.remove("Enchantments");
				ruinedStack.setTag(breakingNBT);
				serverPlayer.getInventory().placeItemBackInInventory(ruinedStack);
			}
		}
	}

	public static CompoundTag getTagForEnchantments(ItemStack breakingStack, ItemStack ruinedStack) {
		final Set<String> enchantmentStrings = new HashSet<>();
		for (final var ench : EnchantmentHelper.getEnchantments(breakingStack).entrySet()) {
			enchantmentStrings.add(BuiltInRegistries.ENCHANTMENT.getKey(ench.getKey()) + ">" + ench.getValue());
		}
		if (!enchantmentStrings.isEmpty()) {
			var tag = ruinedStack.getTag();
			if (tag == null)
				tag = new CompoundTag();
			tag.putString(TAG, String.join(",", enchantmentStrings));
			return tag;
		}
		return null;
	}

	public static boolean isVanillaItemStackBreaking(ItemStack breakingStack, Item vanillaItem) {
		return breakingStack.sameItem(new ItemStack(vanillaItem)) && breakingStack.getMaxDamage() - breakingStack.getDamageValue() <= 0;
	}
}
