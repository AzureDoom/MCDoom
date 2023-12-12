package mod.azure.doom.mixins;

import mod.azure.doom.items.weapons.BaseSwordItem;
import mod.azure.doom.items.weapons.DoomBaseItem;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AnvilMenu.class)
public abstract class AnvilScreenHandlerMixin extends ItemCombinerMenu {

    protected AnvilScreenHandlerMixin(MenuType<?> type, int syncId, Inventory playerInventory, ContainerLevelAccess context) {
        super(type, syncId, playerInventory, context);
    }

    @Inject(method = "createResult", at = @At(value = "RETURN"))
    private void updateRuinedRepair(CallbackInfo ci) {
        final var leftStack = inputSlots.getItem(0).copy();
        final var rightStack = inputSlots.getItem(1).copy();
        if ((leftStack.getItem() instanceof DoomBaseItem || leftStack.getItem() instanceof BaseSwordItem) && EnchantmentHelper.getEnchantments(
                rightStack).containsKey(Enchantments.MENDING)) {
            final var repaired = ItemStack.EMPTY;
            resultSlots.setItem(0, repaired);
            broadcastChanges();
        }
    }
}