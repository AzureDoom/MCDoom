package mod.azure.doom.client.gui;

import java.util.Optional;

import mod.azure.doom.recipes.GunTableRecipe;
import mod.azure.doom.recipes.GunTableRecipe.Type;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;

public class GunTableOutputSlot extends Slot {
	private final DoomGunInventory gunTableInventory;
	private final Player player;
	private int removeCount;

	public GunTableOutputSlot(Player player, DoomGunInventory gunTableInventory, int index, int x, int y) {
		super(gunTableInventory, index, x, y);
		this.player = player;
		this.gunTableInventory = gunTableInventory;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack remove(int amount) {
		if (hasItem()) {
			removeCount += Math.min(amount, getItem().getCount());
		}

		return super.remove(amount);
	}

	@Override
	protected void onQuickCraft(ItemStack stack, int amount) {
		removeCount += amount;
		checkTakeAchievements(stack);
	}

	@Override
	protected void checkTakeAchievements(ItemStack stack) {
		stack.onCraftedBy(player.level(), player, removeCount);
		removeCount = 0;
	}

	@Override
	public void onTake(Player player, ItemStack stack) {
		checkTakeAchievements(stack);
		final Optional<GunTableRecipe> optionalGunTableRecipe = player.level().getRecipeManager().getRecipeFor(Type.INSTANCE, gunTableInventory, player.level());
		if (optionalGunTableRecipe.isPresent()) {
			final GunTableRecipe gunTableRecipe = optionalGunTableRecipe.get();
			final NonNullList<ItemStack> NonNullList = gunTableRecipe.getRemainingItems(gunTableInventory);

			for (int i = 0; i < NonNullList.size(); ++i) {
				ItemStack itemStack = gunTableInventory.getItem(i);
				final ItemStack itemStack2 = NonNullList.get(i);
				if (!itemStack.isEmpty()) {
					gunTableInventory.removeItem(i, gunTableRecipe.countRequired(i));
					itemStack = gunTableInventory.getItem(i);
				}

				if (!itemStack2.isEmpty()) {
					if (itemStack.isEmpty())
						gunTableInventory.setItem(i, itemStack2);
					else if (ItemStack.isSameItem(itemStack, itemStack2) && ItemStack.isSameItemSameTags(itemStack, itemStack2)) {
						itemStack2.shrink(itemStack.getCount());
						gunTableInventory.setItem(i, itemStack2);
					} else if (!this.player.getInventory().add(itemStack2)) {
						this.player.drop(itemStack2, false);
					}
				}
			}
		}
		MinecraftForge.EVENT_BUS.post(new ItemCraftedEvent(player, stack, container));
		setChanged();
	}
}
