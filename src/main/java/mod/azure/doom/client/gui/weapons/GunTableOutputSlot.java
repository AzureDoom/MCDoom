package mod.azure.doom.client.gui.weapons;

import java.util.Optional;

import mod.azure.doom.recipes.GunTableRecipe;
import mod.azure.doom.util.PMMOCompat;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.ModList;

public class GunTableOutputSlot extends Slot {
	private final DoomGunInventory gunTableInventory;
	private final PlayerEntity player;
	private int removeCount;

	public GunTableOutputSlot(PlayerEntity player, DoomGunInventory gunTableInventory, int index, int x, int y) {
		super(gunTableInventory, index, x, y);
		this.player = player;
		this.gunTableInventory = gunTableInventory;
	}

	public boolean mayPlace(ItemStack stack) {
		return false;
	}

	public ItemStack remove(int amount) {
		if (this.hasItem()) {
			this.removeCount += Math.min(amount, this.getItem().getCount());
		}

		return super.remove(amount);
	}

	protected void onQuickCraft(ItemStack stack, int amount) {
		this.removeCount += amount;
		this.checkTakeAchievements(stack);
	}

	protected void checkTakeAchievements(ItemStack stack) {
		stack.onCraftedBy(this.player.level, this.player, this.removeCount);
		this.removeCount = 0;
	}

	public ItemStack onTake(PlayerEntity player, ItemStack stack) {
		this.checkTakeAchievements(stack);
		Optional<GunTableRecipe> optionalGunTableRecipe = player.level.getRecipeManager()
				.getRecipeFor(GunTableRecipe.GUN_TABLE, gunTableInventory, player.level);
		if (optionalGunTableRecipe.isPresent()) {
			GunTableRecipe gunTableRecipe = optionalGunTableRecipe.get();
			NonNullList<ItemStack> NonNullList = gunTableRecipe.getRemainingItems(gunTableInventory);

			for (int i = 0; i < NonNullList.size(); ++i) {
				ItemStack itemStack = this.gunTableInventory.getItem(i);
				ItemStack itemStack2 = NonNullList.get(i);
				if (!itemStack.isEmpty()) {
					this.gunTableInventory.removeItem(i, gunTableRecipe.countRequired(i));
					itemStack = this.gunTableInventory.getItem(i);
				}

				if (!itemStack2.isEmpty()) {
					if (itemStack.isEmpty()) {
						this.gunTableInventory.setItem(i, itemStack2);
					} else if (ItemStack.isSameIgnoreDurability(itemStack, itemStack2)
							&& ItemStack.isSame(itemStack, itemStack2)) {
						itemStack2.shrink(itemStack.getCount());
						this.gunTableInventory.setItem(i, itemStack2);
					} else if (!this.player.inventory.add(itemStack2)) {
						this.player.drop(itemStack2, false);
					}
				}
			}
		}
		if (ModList.get().isLoaded("pmmo")) {
			PMMOCompat.awardCrafting(stack);
		}
		return stack;
	}
}
