package mod.azure.doom.item.powerup;

import java.util.List;

import mod.azure.doom.DoomMod;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class InvisibleSphereItem extends Item {

	public InvisibleSphereItem() {
		super(new Item.Properties().tab(DoomMod.DoomPowerUPItemGroup).stacksTo(1));
	}

	@Override
	public void onUseTick(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
		if (livingEntityIn instanceof ServerPlayer) {
			ServerPlayer playerentity = (ServerPlayer) livingEntityIn;
			if (!worldIn.isClientSide) {
				livingEntityIn.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 1200, 1));
				if (!playerentity.getAbilities().instabuild) {
					stack.shrink(1);
					if (stack.isEmpty()) {
						playerentity.getInventory().removeItem(stack);
					}
				}
			}
		}
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 7000;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.NONE;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		playerIn.startUsingItem(handIn);
		return InteractionResultHolder.consume(itemstack);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("doom.invisible.text").withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return false;
	}

}