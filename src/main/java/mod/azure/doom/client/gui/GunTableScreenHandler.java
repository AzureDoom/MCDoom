package mod.azure.doom.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mod.azure.doom.DoomMod;
import mod.azure.doom.mixin.IngredientAccess;
import mod.azure.doom.util.recipes.GunTableRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.Ingredient;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public class GunTableScreenHandler extends ScreenHandler {
	private final PlayerInventory playerInventory;
	private final GunTableInventory gunTableInventory;
	private final ScreenHandlerContext context;
	@SuppressWarnings("unused")
	private int recipeIndex;

	// client
	public GunTableScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
	}

	// server
	public GunTableScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(DoomMod.SCREEN_HANDLER_TYPE, syncId);
		this.playerInventory = playerInventory;
		this.gunTableInventory = new GunTableInventory(this);
		this.context = context;
		this.addSlot(new Slot(this.gunTableInventory, 0, 155, 13));
		this.addSlot(new Slot(this.gunTableInventory, 1, 175, 33));
		this.addSlot(new Slot(this.gunTableInventory, 2, 135, 33));
		this.addSlot(new Slot(this.gunTableInventory, 3, 142, 56));
		this.addSlot(new Slot(this.gunTableInventory, 4, 168, 56));
		this.addSlot(new GunTableOutputSlot(playerInventory.player, this.gunTableInventory, 5, 238, 38));

		int k;
		for (k = 0; k < 3; ++k) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 127 + j * 18, 84 + k * 18));
			}
		}

		for (k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInventory, k, 127 + k * 18, 142));
		}

	}

	protected static void updateResult(int syncId, World world, PlayerEntity player,
			GunTableInventory craftingInventory) {
		if (!world.isClient) {
			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
			ItemStack itemStack = ItemStack.EMPTY;
			Optional<GunTableRecipe> optional = world.getServer().getRecipeManager()
					.getFirstMatch(GunTableRecipe.GUN_TABLE, craftingInventory, world);
			if (optional.isPresent()) {
				GunTableRecipe craftingRecipe = optional.get();
				itemStack = craftingRecipe.craft(craftingInventory);
			}

			craftingInventory.setStack(5, itemStack);
			serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(syncId, 0, 5, itemStack));
		}
	}

	public void onContentChanged(Inventory inventory) {
		this.context.run((world, blockPos) -> {
			updateResult(this.syncId, world, this.playerInventory.player, this.gunTableInventory);
		});
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return canUse(context, player, DoomMod.GUN_TABLE);
	}

	public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
		return false;
	}

	public ItemStack transferSlot(PlayerEntity player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasStack()) {
			ItemStack itemStack2 = slot.getStack();
			itemStack = itemStack2.copy();
			if (index == 2) {
				if (!this.insertItem(itemStack2, 3, 39, true)) {
					return ItemStack.EMPTY;
				}
				slot.onQuickTransfer(itemStack2, itemStack);
			} else if (index != 0 && index != 1) {
				if (index >= 3 && index < 30) {
					if (!this.insertItem(itemStack2, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index >= 30 && index < 39 && !this.insertItem(itemStack2, 3, 30, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(itemStack2, 3, 39, false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack2.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}

			if (itemStack2.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTakeItem(player, itemStack2);
		}

		return itemStack;
	}

	public List<GunTableRecipe> getRecipes() {
		List<GunTableRecipe> list = new ArrayList<>(
				playerInventory.player.world.getRecipeManager().listAllOfType(GunTableRecipe.GUN_TABLE));
		list.sort(null);
		return list;
	}

	public void setRecipeIndex(int index) {
		recipeIndex = index;
	}

	public void switchTo(int recipeIndex) {
		// index out of bounds
		if (this.getRecipes().size() > recipeIndex) {
			GunTableRecipe gunTableRecipe = getRecipes().get(recipeIndex);
			for (int i = 0; i < 5; i++) {
				ItemStack slotStack = gunTableInventory.getStack(i);
				if (!slotStack.isEmpty()) {
					// if all positions can't be filled, transfer nothing
					if (!this.insertItem(slotStack, 6, 39, false)) {
						return;
					}
					gunTableInventory.setStack(i, slotStack);
				}
			}

			for (int i = 0; i < 5; i++) {
				Ingredient ingredient = gunTableRecipe.getIngredientForSlot(i);
				if (!ingredient.isEmpty()) {
					ItemStack[] possibleItems = ((IngredientAccess) (Object) ingredient).getMatchingStacksMod();
					if (possibleItems != null) {
						ItemStack first = new ItemStack(possibleItems[0].getItem(), gunTableRecipe.countRequired(i));
						autofill(i, first);
					}
				}
			}
		}
	}

	private void autofill(int slot, ItemStack stack) {
		if (!stack.isEmpty()) {
			int ingCount = stack.getCount();
			for (int index = 6; index < 42; ++index) {
				ItemStack slotStack = this.slots.get(index).getStack();
				if (!slotStack.isEmpty() && this.equals(stack, slotStack)) {
					ItemStack invStack = this.gunTableInventory.getStack(slot);
					int invStackCount = invStack.isEmpty() ? 0 : invStack.getCount();
					int countToMove = Math.min(ingCount - invStackCount, slotStack.getCount());
					ItemStack slotStackCopy = slotStack.copy();
					int l = invStackCount + countToMove;
					slotStack.decrement(countToMove);
					slotStackCopy.setCount(l);
					this.gunTableInventory.setStack(slot, slotStackCopy);
					if (l >= stack.getMaxCount()) {
						break;
					}
				}
			}
		}
	}

	private boolean equals(ItemStack itemStack, ItemStack otherItemStack) {
		return itemStack.getItem() == otherItemStack.getItem() && ItemStack.areNbtEqual(itemStack, otherItemStack);
	}

	public void close(PlayerEntity player) {
		super.close(player);
		if (!this.playerInventory.player.world.isClient) {
			if (player.isAlive()
					&& (!(player instanceof ServerPlayerEntity) || !((ServerPlayerEntity) player).isDisconnected())) {
				player.getInventory().offerOrDrop(this.gunTableInventory.removeStack(0));
				player.getInventory().offerOrDrop(this.gunTableInventory.removeStack(1));
				player.getInventory().offerOrDrop(this.gunTableInventory.removeStack(2));
				player.getInventory().offerOrDrop(this.gunTableInventory.removeStack(3));
				player.getInventory().offerOrDrop(this.gunTableInventory.removeStack(4));
			} else {
				ItemStack itemStack0 = this.gunTableInventory.removeStack(0);
				ItemStack itemStack1 = this.gunTableInventory.removeStack(1);
				ItemStack itemStack2 = this.gunTableInventory.removeStack(2);
				ItemStack itemStack3 = this.gunTableInventory.removeStack(3);
				ItemStack itemStack4 = this.gunTableInventory.removeStack(4);
				if (!itemStack0.isEmpty()) {
					player.dropItem(itemStack0, false);
				}

				if (!itemStack1.isEmpty()) {
					player.dropItem(itemStack1, false);
				}

				if (!itemStack2.isEmpty()) {
					player.dropItem(itemStack2, false);
				}

				if (!itemStack3.isEmpty()) {
					player.dropItem(itemStack3, false);
				}

				if (!itemStack4.isEmpty()) {
					player.dropItem(itemStack4, false);
				}
			}
		}
	}
}