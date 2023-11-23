package mod.azure.doom.client.gui;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class DoomGunInventory implements Container {
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
        for (ItemStack stack : stacks)
            if (!stack.isEmpty()) return false;
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.stacks.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        var itemStack = ContainerHelper.removeItem(this.stacks, slot, amount);
        if (!itemStack.isEmpty() && slot != 5) this.container.onContentChanged(this);
        return itemStack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.stacks, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.stacks.set(slot, stack);
        if (slot != 5) container.onContentChanged(this);
    }

    @Override
    public boolean stillValid(Player player) {
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
