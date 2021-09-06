package mod.azure.doom.client.gui.weapons;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class DoomGunInventory implements IInventory {
	private final GunTableScreenHandler container;

	private final NonNullList<ItemStack> stacks;

	public DoomGunInventory(GunTableScreenHandler container) {
		this.stacks = NonNullList.withSize(6, ItemStack.EMPTY);
		this.container = container;
	}

	@Override
	public int getContainerSize() {
		return this.stacks.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack stack : stacks) {
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getItem(int slot) {
		return this.stacks.get(slot);
	}

	@Override
	public ItemStack removeItem(int slot, int amount) {
		ItemStack itemStack = ItemStackHelper.removeItem(this.stacks, slot, amount);
		if (!itemStack.isEmpty() && slot != 5) {
			this.container.onContentChanged(this);
		}
		return itemStack;
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		return ItemStackHelper.takeItem(this.stacks, slot);
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		this.stacks.set(slot, stack);
		if (slot != 5)
			container.onContentChanged(this);
	}

	@Override
	public boolean stillValid(PlayerEntity p_70300_1_) {
		return true;
	}

	@Override
	public void setChanged() {

	}

	@Override
	public void clearContent() {
		this.stacks.clear();
	}

}
