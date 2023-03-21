package mod.azure.doom.entity.tileentity;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ImplementedInventory extends Container {

	NonNullList<ItemStack> getItems();

	static ImplementedInventory of(NonNullList<ItemStack> items) {
		return () -> items;
	}

	static ImplementedInventory ofSize(int size) {
		return of(NonNullList.withSize(size, ItemStack.EMPTY));
	}

	@Override
	default int getContainerSize() {
		return getItems().size();
	}

	@Override
	default boolean isEmpty() {
		for (var i = 0; i < getContainerSize(); i++) {
			final var stack = getItem(i);
			if (!stack.isEmpty())
				return false;
		}
		return true;
	}

	@Override
	default ItemStack getItem(int slot) {
		return getItems().get(slot);
	}

	@Override
	default ItemStack removeItem(int slot, int count) {
		final var result = ContainerHelper.removeItem(getItems(), slot, count);
		if (!result.isEmpty())
			setChanged();
		return result;
	}

	@Override
	default ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.takeItem(getItems(), slot);
	}

	@Override
	default void setItem(int slot, ItemStack stack) {
		getItems().set(slot, stack);
		if (stack.getCount() > getMaxStackSize())
			stack.setCount(getMaxStackSize());
	}

	@Override
	default void clearContent() {
		getItems().clear();
	}

	@Override
	default void setChanged() {
	}

	@Override
	default boolean stillValid(Player player) {
		return true;
	}
}