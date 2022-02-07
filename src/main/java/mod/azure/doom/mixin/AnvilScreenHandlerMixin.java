package mod.azure.doom.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.doom.item.weapons.AxeMarauderItem;
import mod.azure.doom.item.weapons.Chainsaw;
import mod.azure.doom.item.weapons.ChainsawAnimated;
import mod.azure.doom.item.weapons.DarkLordCrucibleItem;
import mod.azure.doom.item.weapons.DoomBaseItem;
import mod.azure.doom.item.weapons.SwordCrucibleItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

@Mixin(value = AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

	public AnvilScreenHandlerMixin(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory,
			ScreenHandlerContext context) {
		super(type, syncId, playerInventory, context);
	}

	@Inject(method = "updateResult", at = @At(value = "RETURN"))
	private void updateRuinedRepair(CallbackInfo ci) {
		ItemStack leftStack = this.input.getStack(0).copy();
		ItemStack rightStack = this.input.getStack(1).copy();
		if ((leftStack.getItem() instanceof DoomBaseItem || leftStack.getItem() instanceof AxeMarauderItem
				|| leftStack.getItem() instanceof DarkLordCrucibleItem
				|| leftStack.getItem() instanceof SwordCrucibleItem || leftStack.getItem() instanceof ChainsawAnimated
				|| leftStack.getItem() instanceof Chainsaw)
				&& (EnchantmentHelper.get(rightStack).containsKey(Enchantments.MENDING)
						|| EnchantmentHelper.get(rightStack).containsKey(Enchantments.UNBREAKING)
						|| EnchantmentHelper.get(rightStack).containsKey(Enchantments.INFINITY)
						|| EnchantmentHelper.get(rightStack).containsKey(Enchantments.FLAME)
						|| EnchantmentHelper.get(rightStack).containsKey(Enchantments.PUNCH))) {
			ItemStack repaired = ItemStack.EMPTY;
			this.output.setStack(0, repaired);
			this.sendContentUpdates();
		}
	}
}