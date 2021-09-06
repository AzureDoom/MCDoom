package mod.azure.doom.client.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class GunTableInventory implements Inventory {
	private final GunTableScreenHandler container;

	private final DefaultedList<ItemStack> stacks;


	public GunTableInventory(GunTableScreenHandler container) {
		this.stacks = DefaultedList.ofSize(6, ItemStack.EMPTY);
		this.container = container;
	}

	public int size() {
		return this.stacks.size();
	}

	public boolean isEmpty() {
		for (ItemStack stack : stacks) {
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public ItemStack getStack(int slot) {
		return this.stacks.get(slot);
	}

	public ItemStack removeStack(int slot, int amount) {
		ItemStack itemStack = Inventories.splitStack(this.stacks, slot, amount);
		if (!itemStack.isEmpty() && slot != 5) {
			this.container.onContentChanged(this);
		}
		return itemStack;
	}

	public ItemStack removeStack(int slot) {
		return Inventories.removeStack(this.stacks, slot);
	}

	public void setStack(int slot, ItemStack stack) {
		this.stacks.set(slot, stack);
		if (slot != 5)
		container.onContentChanged(this);
	}

	public boolean canPlayerUse(PlayerEntity player) {
		return true;
	}

	public void markDirty() {

	}

	public void clear() {
		this.stacks.clear();
	}

}
