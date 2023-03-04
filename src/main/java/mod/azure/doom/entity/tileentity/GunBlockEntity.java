package mod.azure.doom.entity.tileentity;

import mod.azure.azurelib.animatable.GeoBlockEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.client.gui.weapons.GunTableScreenHandler;
import mod.azure.doom.util.registry.DoomEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GunBlockEntity extends BlockEntity implements ImplementedInventory, MenuProvider, GeoBlockEntity {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	private final NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);

	public GunBlockEntity(BlockPos pos, BlockState state) {
		super(DoomEntities.GUN_TABLE_ENTITY.get(), pos, state);
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> {
			return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}));
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		ContainerHelper.loadAllItems(nbt, items);
	}

	@Override
	public void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		ContainerHelper.saveAllItems(nbt, items);
	}

	@Override
	public NonNullList<ItemStack> getItems() {
		return items;
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("block.doom.gun_table");
	}

	@Override
	public AbstractContainerMenu createMenu(int syncId, Inventory inventory, Player player) {
		return new GunTableScreenHandler(syncId, inventory, ContainerLevelAccess.create(level, worldPosition));
	}
}