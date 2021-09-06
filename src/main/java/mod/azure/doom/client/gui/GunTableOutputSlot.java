package mod.azure.doom.client.gui;

import java.util.Optional;

import mod.azure.doom.util.recipes.GunTableRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

public class GunTableOutputSlot extends Slot {
	private final GunTableInventory gunTableInventory;
	private final PlayerEntity player;
	private int amount;

	public GunTableOutputSlot(PlayerEntity player, GunTableInventory gunTableInventory, int index, int x, int y) {
		super(gunTableInventory, index, x, y);
		this.player = player;
		this.gunTableInventory = gunTableInventory;
	}

	public boolean canInsert(ItemStack stack) {
		return false;
	}

	public ItemStack takeStack(int amount) {
		if (this.hasStack()) {
			this.amount += Math.min(amount, this.getStack().getCount());
		}

		return super.takeStack(amount);
	}

	protected void onCrafted(ItemStack stack, int amount) {
		this.amount += amount;
		this.onCrafted(stack);
	}

	protected void onCrafted(ItemStack stack) {
		stack.onCraft(this.player.world, this.player, this.amount);
		this.amount = 0;
	}

	public void onTakeItem(PlayerEntity player, ItemStack stack) {
		this.onCrafted(stack);
		Optional<GunTableRecipe> optionalGunTableRecipe = player.world.getRecipeManager()
				.getFirstMatch(GunTableRecipe.GUN_TABLE, gunTableInventory, player.world);
		if (optionalGunTableRecipe.isPresent()) {
			GunTableRecipe gunTableRecipe = optionalGunTableRecipe.get();
			DefaultedList<ItemStack> defaultedList = gunTableRecipe.getRemainder(gunTableInventory);

			for (int i = 0; i < defaultedList.size(); ++i) {
				ItemStack itemStack = this.gunTableInventory.getStack(i);
				ItemStack itemStack2 = defaultedList.get(i);
				if (!itemStack.isEmpty()) {
					this.gunTableInventory.removeStack(i, gunTableRecipe.countRequired(i));
					itemStack = this.gunTableInventory.getStack(i);
				}

				if (!itemStack2.isEmpty()) {
					if (itemStack.isEmpty()) {
						this.gunTableInventory.setStack(i, itemStack2);
					} else if (ItemStack.areItemsEqualIgnoreDamage(itemStack, itemStack2)
							&& ItemStack.areNbtEqual(itemStack, itemStack2)) {
						itemStack2.increment(itemStack.getCount());
						this.gunTableInventory.setStack(i, itemStack2);
					} else if (!this.player.getInventory().insertStack(itemStack2)) {
						this.player.dropItem(itemStack2, false);
					}
				}
			}
		}
		this.markDirty();
	}
}
