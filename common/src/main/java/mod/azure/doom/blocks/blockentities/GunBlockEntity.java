package mod.azure.doom.blocks.blockentities;

import mod.azure.azurelib.animatable.GeoBlockEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.client.gui.GunTableScreenHandler;
import mod.azure.doom.platform.Services;
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
import org.jetbrains.annotations.NotNull;

public class GunBlockEntity extends BlockEntity implements ImplementedInventory, MenuProvider, GeoBlockEntity {

    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    private final NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);

    public GunBlockEntity(BlockPos pos, BlockState state) {
        super(Services.ENTITIES_HELPER.getGunTableEntity(), pos, state);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, event -> event.setAndContinue(RawAnimation.begin().thenLoop("idle"))));
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        ContainerHelper.loadAllItems(nbt, items);
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        ContainerHelper.saveAllItems(nbt, items);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.doom.gun_table");
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory inventory, @NotNull Player player) {
        return new GunTableScreenHandler(syncId, inventory, ContainerLevelAccess.create(level, worldPosition));
    }
}