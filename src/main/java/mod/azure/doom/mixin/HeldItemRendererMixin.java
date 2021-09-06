package mod.azure.doom.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.doom.item.weapons.DoomBaseItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.ItemStack;

@Mixin(value = HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {

	@Shadow
	@Final
	private final MinecraftClient client;
	@Shadow
	private float equipProgressMainHand;
	@Shadow
	private float equipProgressOffHand;
	@Shadow
	private ItemStack mainHand;
	@Shadow
	private ItemStack offHand;

	public HeldItemRendererMixin(MinecraftClient client) {
		this.client = client;
	}

	@Inject(method = "updateHeldItems", at = @At("TAIL"))
	public void fguns$cancelAnimation(CallbackInfo ci) {
		ClientPlayerEntity clientPlayerEntity = this.client.player;
		ItemStack itemStack = clientPlayerEntity.getMainHandStack();
		ItemStack itemStack2 = clientPlayerEntity.getOffHandStack();
		if ((this.mainHand.getItem() instanceof DoomBaseItem) && (itemStack.getItem() instanceof DoomBaseItem)
				&& ItemStack.areItemsEqualIgnoreDamage(mainHand, itemStack)) {
			this.equipProgressMainHand = 1;
			this.mainHand = itemStack;
		}
		if ((this.offHand.getItem() instanceof DoomBaseItem) && (itemStack2.getItem() instanceof DoomBaseItem)
				&& ItemStack.areItemsEqualIgnoreDamage(offHand, itemStack2)) {
			this.equipProgressOffHand = 1;
			this.offHand = itemStack2;
		}
	}
}
