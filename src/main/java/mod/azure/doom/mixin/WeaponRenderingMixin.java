package mod.azure.doom.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.azure.doom.item.weapons.DoomBaseItem;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

@Mixin(PlayerRenderer.class)
public class WeaponRenderingMixin {

	@Inject(method = "getArmPose", at = @At(value = "TAIL"), cancellable = true)
	private static BipedModel.ArmPose tryItemPose(AbstractClientPlayerEntity player, Hand hand,
			CallbackInfoReturnable<Boolean> ci) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (itemstack.getItem() instanceof DoomBaseItem) {
			return BipedModel.ArmPose.BOW_AND_ARROW;
		}
		return BipedModel.ArmPose.ITEM;
	}
}
