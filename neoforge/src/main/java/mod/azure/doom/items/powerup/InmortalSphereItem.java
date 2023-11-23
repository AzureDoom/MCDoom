package mod.azure.doom.items.powerup;

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

import java.util.List;

public class InmortalSphereItem extends Item {

    public InmortalSphereItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public void onUseTick(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
        if (livingEntityIn instanceof ServerPlayer playerentity && !worldIn.isClientSide) {
            livingEntityIn.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 4));
            livingEntityIn.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 4));
            if (!playerentity.getAbilities().instabuild) {
                stack.shrink(1);
                if (stack.isEmpty()) {
                    playerentity.getInventory().removeItem(stack);
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
        final ItemStack itemstack = playerIn.getItemInHand(handIn);
        playerIn.startUsingItem(handIn);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("doom.inmortal.text").withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }

}