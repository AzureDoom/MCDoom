package mod.azure.doom.items.weapons;

import mod.azure.azurelib.Keybindings;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierboss.*;
import mod.azure.doom.helper.CommonUtils;
import mod.azure.doom.items.enums.DoomTier;
import mod.azure.doom.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class AxeMarauderItem extends BaseSwordItem {

    public AxeMarauderItem() {
        super(DoomTier.DOOM_HIGHTEIR, 1, -2.5f, new Properties().stacksTo(1).durability(MCDoom.config.marauder_max_uses));
    }

    public static void reload(Player user, InteractionHand hand) {
        if (user.getItemInHand(hand).getItem() instanceof AxeMarauderItem) {
            while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(Services.ITEMS_HELPER.getArgentBlock()) > 0) {
                CommonUtils.removeAmmo(Services.ITEMS_HELPER.getArgentBlock(), user);
                user.getItemInHand(hand).hurtAndBreak(-5, user, s -> user.broadcastBreakEvent(hand));
                user.getItemInHand(hand).setPopTime(3);
            }
        }
    }

    /**
     * Sends reloading packet from the client to the server when pressing {@link Keybindings#RELOAD} keymap
     */
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (level.isClientSide && entity instanceof Player player && player.getItemInHand(player.getUsedItemHand()).getItem() instanceof AxeMarauderItem)
            if (Keybindings.RELOAD.isDown() && selected && !player.getCooldowns().isOnCooldown(stack.getItem())) {
                Services.NETWORK.reloadAxe(slot);
            }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("doom.marauder_axe1.text").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
        tooltip.add(Component.translatable("doom.marauder_axe2.text").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
        tooltip.add(Component.translatable("doom.marauder_axe3.text").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity miner) {
        if (miner instanceof Player playerentity && stack.getDamageValue() < stack.getMaxDamage() - 1 && playerentity.getMainHandItem().getItem() instanceof AxeMarauderItem) {
            final var aabb = new AABB(miner.blockPosition().above()).inflate(4D, 1D, 4D);
            miner.getCommandSenderWorld().getEntities(miner, aabb).forEach(e -> doDamage(playerentity, e));
            stack.hurtAndBreak(1, miner, p -> p.broadcastBreakEvent(playerentity.getUsedItemHand()));
        }
        return stack.getDamageValue() < stack.getMaxDamage() - 1;
    }

    private void doDamage(LivingEntity user, Entity target) {
        if (target instanceof LivingEntity) {
            target.invulnerableTime = 0;
            target.hurt(user.damageSources().playerAttack((Player) user), target instanceof ArchMakyrEntity || target instanceof GladiatorEntity || target instanceof IconofsinEntity || target instanceof MotherDemonEntity || target instanceof SpiderMastermind2016Entity || target instanceof SpiderMastermindEntity ? MCDoom.config.marauder_axe_item_damage / 10F : MCDoom.config.marauder_axe_item_damage);
        }
    }

}