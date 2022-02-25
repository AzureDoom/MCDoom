package mod.azure.doom.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.azure.doom.item.weapons.DoomBaseItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(VillagerEntity.class)
public abstract class VillagerMixin extends MerchantEntity {

	public VillagerMixin(EntityType<? extends MerchantEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(at = @At("RETURN"), method = "interactMob", cancellable = true)
	private void killVillager(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> ci) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.getItem() instanceof DoomBaseItem) {
			ci.setReturnValue(ActionResult.FAIL);
		}
	}
}
