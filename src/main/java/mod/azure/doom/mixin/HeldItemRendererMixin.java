package mod.azure.doom.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.doom.item.weapons.DoomBaseItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.ItemStack;

@Mixin(value = ItemInHandRenderer.class)
public abstract class HeldItemRendererMixin {

	@Shadow
	@Final
	private final Minecraft minecraft;
	@Shadow
	private float mainHandHeight;
	@Shadow
	private float offHandHeight;
	@Shadow
	private ItemStack mainHandItem;
	@Shadow
	private ItemStack offHandItem;

	public HeldItemRendererMixin(Minecraft client) {
		minecraft = client;
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void fguns$cancelAnimation(CallbackInfo ci) {
		final var clientPlayerEntity = minecraft.player;
		final var itemStack = clientPlayerEntity.getMainHandItem();
		final var itemStack2 = clientPlayerEntity.getOffhandItem();
		if (mainHandItem.getItem() instanceof DoomBaseItem && itemStack.getItem() instanceof DoomBaseItem && ItemStack.isSame(mainHandItem, itemStack)) {
			mainHandHeight = 1;
			mainHandItem = itemStack;
		}
		if (offHandItem.getItem() instanceof DoomBaseItem && itemStack2.getItem() instanceof DoomBaseItem && ItemStack.isSame(offHandItem, itemStack2)) {
			offHandHeight = 1;
			offHandItem = itemStack2;
		}
	}
}
