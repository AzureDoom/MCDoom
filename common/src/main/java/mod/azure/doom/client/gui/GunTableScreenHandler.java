package mod.azure.doom.client.gui;

import mod.azure.doom.platform.Services;
import mod.azure.doom.recipes.GunTableRecipe;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GunTableScreenHandler extends AbstractContainerMenu {
    protected final Inventory playerInventory;
    protected final DoomGunInventory gunTableInventory;
    protected final ContainerLevelAccess context;
    protected int recipeIndex;
    protected static Level level;

    // client
    public GunTableScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, ContainerLevelAccess.NULL);
    }

    // server
    public GunTableScreenHandler(int syncId, Inventory playerInventory, ContainerLevelAccess context) {
        super(Services.PLATFORM.getGunScreenHandler(), syncId);
        this.playerInventory = playerInventory;
        this.gunTableInventory = new DoomGunInventory(this);
        GunTableScreenHandler.level = playerInventory.player.level();
        this.context = context;
        this.addSlot(new Slot(this.gunTableInventory, 0, 155, 13));
        this.addSlot(new Slot(this.gunTableInventory, 1, 175, 33));
        this.addSlot(new Slot(this.gunTableInventory, 2, 135, 33));
        this.addSlot(new Slot(this.gunTableInventory, 3, 142, 56));
        this.addSlot(new Slot(this.gunTableInventory, 4, 168, 56));
        this.addSlot(new GunTableOutputSlot(playerInventory.player, this.gunTableInventory, 5, 238, 38));

        int k;
        for (k = 0; k < 3; ++k)
            for (var j = 0; j < 9; ++j)
                this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 127 + j * 18, 84 + k * 18));

        for (k = 0; k < 9; ++k)
            this.addSlot(new Slot(playerInventory, k, 127 + k * 18, 142));
    }

    protected static void updateResult(int syncId, Level world, Player player, DoomGunInventory craftingInventory) {
        if (!world.isClientSide()) {
            var serverPlayerEntity = (ServerPlayer) player;
            var itemStack = ItemStack.EMPTY;
            var optional = Objects.requireNonNull(world.getServer()).getRecipeManager().getRecipeFor(GunTableRecipe.Type.INSTANCE, craftingInventory, world);
            if (optional.isPresent()) itemStack = optional.get().assemble(craftingInventory, level.registryAccess());

            craftingInventory.setItem(5, itemStack);
            serverPlayerEntity.connection.send(new ClientboundContainerSetSlotPacket(syncId, 0, 5, itemStack));
        }
    }

    public void onContentChanged() {
        this.context.execute((world, blockPos) -> updateResult(this.containerId, world, this.playerInventory.player, this.gunTableInventory));
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(context, player, Services.BLOCKS_HELPER.getGunTable());
    }

    @Override
    public boolean canTakeItemForPickAll(@NotNull ItemStack stack, @NotNull Slot slot) {
        return false;
    }

    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        var itemStack = ItemStack.EMPTY;
        var slot = this.slots.get(index);
        if (slot.hasItem()) {
            var itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (index == 2) {
                if (!this.moveItemStackTo(itemStack2, 3, 39, true)) return ItemStack.EMPTY;
                slot.onQuickCraft(itemStack2, itemStack);
            } else if (index >= 3 && index < 30)
                if (!this.moveItemStackTo(itemStack2, 30, 39, false)) return ItemStack.EMPTY;
                else {
                    if (!this.moveItemStackTo(itemStack2, 3, 39, false)) return ItemStack.EMPTY;
                }

            if (itemStack2.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();

            if (itemStack2.getCount() == itemStack.getCount()) return ItemStack.EMPTY;

            slot.onTake(player, itemStack2);
        }

        return itemStack;
    }

    public List<GunTableRecipe> getRecipes() {
        var list = new ArrayList<>(playerInventory.player.level().getRecipeManager().getAllRecipesFor(GunTableRecipe.Type.INSTANCE));
        list.sort(null);
        return list;
    }

    public void setRecipeIndex(int index) {
        recipeIndex = index;
    }

    public void switchTo(int recipeIndex) {
        if (this.getRecipes().size() > recipeIndex) {
            var gunTableRecipe = getRecipes().get(recipeIndex);
            for (var i = 0; i < 5; i++) {
                var slotStack = gunTableInventory.getItem(i);
                if (!slotStack.isEmpty()) {
                    if (!this.moveItemStackTo(slotStack, 6, 39, false)) return;
                    gunTableInventory.setItem(i, slotStack);
                }
            }

            for (var i = 0; i < 5; i++) {
                var ingredient = gunTableRecipe.getIngredientForSlot(i);
                if (!ingredient.isEmpty()) {
                    var possibleItems = ingredient.getItems();
                    moveFromInventoryToPaymentSlot(i, new ItemStack(possibleItems[0].getItem(), gunTableRecipe.countRequired(i)));
                }
            }
        }
    }

    private void moveFromInventoryToPaymentSlot(int slot, ItemStack stack) {
        if (!stack.isEmpty()) {
            var ingCount = stack.getCount();
            for (var index = 6; index < 42; ++index) {
                var slotStack = this.slots.get(index).getItem();
                if (!slotStack.isEmpty() && this.equals(stack, slotStack)) {
                    var invStack = this.gunTableInventory.getItem(slot);
                    var invStackCount = invStack.isEmpty() ? 0 : invStack.getCount();
                    var countToMove = Math.min(ingCount - invStackCount, slotStack.getCount());
                    var slotStackCopy = slotStack.copy();
                    var l = invStackCount + countToMove;
                    slotStack.shrink(countToMove);
                    slotStackCopy.setCount(l);
                    this.gunTableInventory.setItem(slot, slotStackCopy);
                    if (l >= stack.getMaxStackSize()) break;
                }
            }
        }
    }

    private boolean equals(ItemStack itemStack, ItemStack otherItemStack) {
        return itemStack.getItem() == otherItemStack.getItem() && ItemStack.isSameItemSameTags(itemStack, otherItemStack);
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        if (!this.playerInventory.player.level().isClientSide) {
            if (player.isAlive() && (!(player instanceof ServerPlayer serverPlayer) || !serverPlayer.hasDisconnected())) {
                player.getInventory().placeItemBackInInventory(this.gunTableInventory.removeItemNoUpdate(0));
                player.getInventory().placeItemBackInInventory(this.gunTableInventory.removeItemNoUpdate(1));
                player.getInventory().placeItemBackInInventory(this.gunTableInventory.removeItemNoUpdate(2));
                player.getInventory().placeItemBackInInventory(this.gunTableInventory.removeItemNoUpdate(3));
                player.getInventory().placeItemBackInInventory(this.gunTableInventory.removeItemNoUpdate(4));
            } else {
                var itemStack0 = this.gunTableInventory.removeItemNoUpdate(0);
                var itemStack1 = this.gunTableInventory.removeItemNoUpdate(1);
                var itemStack2 = this.gunTableInventory.removeItemNoUpdate(2);
                var itemStack3 = this.gunTableInventory.removeItemNoUpdate(3);
                var itemStack4 = this.gunTableInventory.removeItemNoUpdate(4);
                if (!itemStack0.isEmpty()) player.drop(itemStack0, false);

                if (!itemStack1.isEmpty()) player.drop(itemStack1, false);

                if (!itemStack2.isEmpty()) player.drop(itemStack2, false);

                if (!itemStack3.isEmpty()) player.drop(itemStack3, false);

                if (!itemStack4.isEmpty()) player.drop(itemStack4, false);
            }
        }
    }
}