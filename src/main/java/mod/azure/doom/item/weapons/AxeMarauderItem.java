package mod.azure.doom.item.weapons;

import java.util.List;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.Keybindings;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.packets.DoomPacketHandler;
import mod.azure.doom.util.packets.weapons.AxeMarauderLoadingPacket;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.core.NonNullList;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.World;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AxeMarauderItem extends AxeItem {

	public AxeMarauderItem() {
		super(DoomTier.DOOM_HIGHTEIR, 36, -2.4F,
				new Item.Properties().tab(DoomMod.DoomWeaponItemGroup).stacksTo(1).durability(5));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("doom.marauder_axe1.text").withStyle(TextFormatting.RED)
				.withStyle(TextFormatting.ITALIC));
		tooltip.add(new TranslationTextComponent("doom.marauder_axe2.text").withStyle(TextFormatting.RED)
				.withStyle(TextFormatting.ITALIC));
		tooltip.add(new TranslationTextComponent("doom.marauder_axe3.text").withStyle(TextFormatting.RED)
				.withStyle(TextFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		Player playerentity = (Player) entityIn;
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
			while (user.getItemInHand(hand).getDamageValue() != 0
					&& user.getInventory().countItem(DoomItems.ARGENT_BLOCK.get()) > 0) {
				removeAmmo(DoomItems.ARGENT_BLOCK.get(), user);
				user.getItemInHand(hand).hurtAndBreak(-5, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
			}
		}
	}

	public static void removeAmmo(Item ammo, Player playerEntity) {
		if (!playerEntity.isCreative()) {
			for (ItemStack item : playerEntity.getInventory().offhand) {
				if (item.getItem() == ammo) {
					item.shrink(1);
					break;
				}
				for (ItemStack item1 : playerEntity.getInventory().items) {
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
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		ItemStack stack = new ItemStack(this);
		stack.hasTag();
		stack.enchant(Enchantments.SMITE, 10);
		stack.enchant(Enchantments.MOB_LOOTING, 10);
		stack.enchant(Enchantments.SHARPNESS, 10);
		stack.enchant(Enchantments.SWEEPING_EDGE, 10);
		if ((group == DoomMod.DoomWeaponItemGroup) || (group == ItemGroup.TAB_SEARCH)) {
			items.add(stack);
		}
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