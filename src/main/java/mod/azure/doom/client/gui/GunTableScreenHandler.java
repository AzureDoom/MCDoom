package mod.azure.doom.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mod.azure.doom.recipes.GunTableRecipe;
import mod.azure.doom.recipes.GunTableRecipe.Type;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomScreens;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class GunTableScreenHandler extends AbstractContainerMenu {
	private final Inventory playerInventory;
	private final DoomGunInventory gunTableInventory;
	private final ContainerLevelAccess context;
	@SuppressWarnings("unused")
	private int recipeIndex;

	// client
	public GunTableScreenHandler(int syncId, Inventory playerInventory) {
		this(syncId, playerInventory, ContainerLevelAccess.NULL);
	}

	// server
	public GunTableScreenHandler(int syncId, Inventory playerInventory, ContainerLevelAccess context) {
		super(DoomScreens.SCREEN_HANDLER_TYPE.get(), syncId);
		this.playerInventory = playerInventory;
		gunTableInventory = new DoomGunInventory(this);
		this.context = context;
		addSlot(new Slot(gunTableInventory, 0, 155, 13));
		addSlot(new Slot(gunTableInventory, 1, 175, 33));
		addSlot(new Slot(gunTableInventory, 2, 135, 33));
		addSlot(new Slot(gunTableInventory, 3, 142, 56));
		addSlot(new Slot(gunTableInventory, 4, 168, 56));
		addSlot(new GunTableOutputSlot(playerInventory.player, gunTableInventory, 5, 238, 38));

		int k;
		for (k = 0; k < 3; ++k) {
			for (int j = 0; j < 9; ++j) {
				addSlot(new Slot(playerInventory, j + k * 9 + 9, 127 + j * 18, 84 + k * 18));
			}
		}

		for (k = 0; k < 9; ++k) {
			addSlot(new Slot(playerInventory, k, 127 + k * 18, 142));
		}

	}

	protected static void updateResult(int syncId, Level world, Player player, DoomGunInventory craftingInventory) {
		if (!world.isClientSide()) {
			final ServerPlayer serverPlayerEntity = (ServerPlayer) player;
			ItemStack itemStack = ItemStack.EMPTY;
			final Optional<GunTableRecipe> optional = world.getServer().getRecipeManager().getRecipeFor(Type.INSTANCE, craftingInventory, world);
			if (optional.isPresent()) {
				var craftingRecipe = optional.get();
				itemStack = craftingRecipe.assemble(craftingInventory);
			}

			craftingInventory.setItem(5, itemStack);
			serverPlayerEntity.connection.send(new ClientboundContainerSetSlotPacket(syncId, 0, 5, itemStack));
		}
	}

	public void onContentChanged(Container inventory) {
		context.execute((world, blockPos) -> {
			updateResult(containerId, world, playerInventory.player, gunTableInventory);
		});
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(context, player, DoomBlocks.GUN_TABLE.get());
	}

	@Override
	public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
		return false;
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		final Slot slot = slots.get(index);
		if (slot != null && slot.hasItem()) {
			final ItemStack itemStack2 = slot.getItem();
			itemStack = itemStack2.copy();
			if (index == 2) {
				if (!moveItemStackTo(itemStack2, 3, 39, true)) {
					return ItemStack.EMPTY;
				}
				slot.onQuickCraft(itemStack2, itemStack);
			} else if (index != 0 && index != 1) {
				if (index >= 3 && index < 30) {
					if (!moveItemStackTo(itemStack2, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 30 && index < 39 && !moveItemStackTo(itemStack2, 3, 30, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!moveItemStackTo(itemStack2, 3, 39, false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack2.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (itemStack2.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, itemStack2);
		}

		return itemStack;
	}

	public List<GunTableRecipe> getRecipes() {
		final List<GunTableRecipe> list = new ArrayList<>(playerInventory.player.level.getRecipeManager().getAllRecipesFor(Type.INSTANCE));
		list.sort(null);
		return list;
	}

	public void setRecipeIndex(int index) {
		recipeIndex = index;
	}

	public void switchTo(int recipeIndex) {
		// index out of bounds
		if (getRecipes().size() > recipeIndex) {
			final GunTableRecipe gunTableRecipe = getRecipes().get(recipeIndex);
			for (int i = 0; i < 5; i++) {
				final ItemStack slotStack = gunTableInventory.getItem(i);
				if (!slotStack.isEmpty()) {
					// if all positions can't be filled, transfer nothing
					if (!moveItemStackTo(slotStack, 6, 39, false)) {
						return;
					}
					gunTableInventory.setItem(i, slotStack);
				}
			}

			for (int i = 0; i < 5; i++) {
				final Ingredient ingredient = gunTableRecipe.getIngredientForSlot(i);
				if (!ingredient.isEmpty()) {
					final ItemStack[] possibleItems = ingredient.getItems();
					if (possibleItems != null) {
						final ItemStack first = new ItemStack(possibleItems[0].getItem(), gunTableRecipe.countRequired(i));
						moveFromInventoryToPaymentSlot(i, first);
					}
				}
			}
		}
	}

	private void moveFromInventoryToPaymentSlot(int slot, ItemStack stack) {
		if (!stack.isEmpty()) {
			final int ingCount = stack.getCount();
			for (int index = 6; index < 42; ++index) {
				final ItemStack slotStack = slots.get(index).getItem();
				if (!slotStack.isEmpty() && this.equals(stack, slotStack)) {
					final ItemStack invStack = gunTableInventory.getItem(slot);
					final int invStackCount = invStack.isEmpty() ? 0 : invStack.getCount();
					final int countToMove = Math.min(ingCount - invStackCount, slotStack.getCount());
					final ItemStack slotStackCopy = slotStack.copy();
					final int l = invStackCount + countToMove;
					slotStack.shrink(countToMove);
					slotStackCopy.setCount(l);
					gunTableInventory.setItem(slot, slotStackCopy);
					if (l >= stack.getMaxStackSize()) {
						break;
					}
				}
			}
		}
	}

	private boolean equals(ItemStack itemStack, ItemStack otherItemStack) {
		return itemStack.getItem() == otherItemStack.getItem() && ItemStack.isSame(itemStack, otherItemStack);
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		if (!playerInventory.player.level.isClientSide) {
			if (player.isAlive() && (!(player instanceof ServerPlayer) || !((ServerPlayer) player).hasDisconnected())) {
				player.getInventory().placeItemBackInInventory(gunTableInventory.removeItemNoUpdate(0));
				player.getInventory().placeItemBackInInventory(gunTableInventory.removeItemNoUpdate(1));
				player.getInventory().placeItemBackInInventory(gunTableInventory.removeItemNoUpdate(2));
				player.getInventory().placeItemBackInInventory(gunTableInventory.removeItemNoUpdate(3));
				player.getInventory().placeItemBackInInventory(gunTableInventory.removeItemNoUpdate(4));
			} else {
				final ItemStack itemStack0 = gunTableInventory.removeItemNoUpdate(0);
				final ItemStack itemStack1 = gunTableInventory.removeItemNoUpdate(1);
				final ItemStack itemStack2 = gunTableInventory.removeItemNoUpdate(2);
				final ItemStack itemStack3 = gunTableInventory.removeItemNoUpdate(3);
				final ItemStack itemStack4 = gunTableInventory.removeItemNoUpdate(4);
				if (!itemStack0.isEmpty()) {
					player.drop(itemStack0, false);
				}

				if (!itemStack1.isEmpty()) {
					player.drop(itemStack1, false);
				}

				if (!itemStack2.isEmpty()) {
					player.drop(itemStack2, false);
				}

				if (!itemStack3.isEmpty()) {
					player.drop(itemStack3, false);
				}

				if (!itemStack4.isEmpty()) {
					player.drop(itemStack4, false);
				}
			}
		}
	}
}