package mod.azure.doom.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import mod.azure.doom.util.StructureModdedLootImporter;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;

@Mixin(LootTable.class)
public class LootTableMixin {

	@Inject(method = "generateLoot(Lnet/minecraft/loot/context/LootContext;)Ljava/util/List;", at = @At(value = "RETURN"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private void modifyLoot(LootContext context, CallbackInfoReturnable<List<ItemStack>> cir, List<ItemStack> list) {
		List<ItemStack> newList = StructureModdedLootImporter.checkAndGetModifiedLoot(context,
				(LootTable) (Object) this, list);
		if (!newList.isEmpty())
			cir.setReturnValue(newList);
	}
}