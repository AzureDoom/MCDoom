package mod.azure.doom.items.weapons;

import mod.azure.azurelib.Keybindings;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.doom.client.render.weapons.DSGRender;
import mod.azure.doom.helper.CommonUtils;
import mod.azure.doom.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DShotgun extends DoomBaseItem {

    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public DShotgun() {
        super(new Properties().stacksTo(1).durability(51));
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public static void reload(Player user, InteractionHand hand) {
        if (user.getItemInHand(hand).getItem() instanceof DShotgun gun) {
            while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(Services.ITEMS_HELPER.getShells()) > 0) {
                CommonUtils.removeAmmo(Services.ITEMS_HELPER.getShells(), user);
                user.getItemInHand(hand).hurtAndBreak(-4, user, s -> user.broadcastBreakEvent(hand));
                user.getItemInHand(hand).setPopTime(3);
                user.level().playSound(null, user.getX(), user.getY(), user.getZ(), Services.SOUNDS_HELPER.getSHOTGUNRELOAD(), SoundSource.PLAYERS, 1.00F, 1.0F);
                if (!user.level().isClientSide)
                    gun.triggerAnim(user, GeoItem.getOrAssignId(user.getItemInHand(hand), (ServerLevel) user.level()), "shoot_controller", "reload");
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player playerentity) if (stack.getDamageValue() < stack.getMaxDamage() - 1) {
            playerentity.getCooldowns().addCooldown(stack.getItem(), 5);
            if (!level.isClientSide) {
                Projectile bullet = CommonUtils.createShell(level, stack, playerentity);
                bullet.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(), 0.0F, 3.0F, 1.0F);
                level.addFreshEntity(bullet);
                stack.hurtAndBreak(1, playerentity, p -> p.broadcastBreakEvent(playerentity.getUsedItemHand()));
                level.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), Services.SOUNDS_HELPER.getSHOTGUN_SHOOT(), SoundSource.PLAYERS, 1.0F, 1.0F / (level.random.nextFloat() * 0.4F + 1.2F) + 0.25F * 0.5F);
                triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) level), "shoot_controller", "firing");
            }
            CommonUtils.spawnLightSource(playerentity, playerentity.level().isWaterAt(playerentity.blockPosition()));
        } else {
            level.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), Services.SOUNDS_HELPER.getEMPTY(), SoundSource.PLAYERS, 1.0F, 1.5F);
        }
    }

    /**
     * Sends reloading packet from the client to the server when pressing {@link Keybindings#RELOAD} keymap
     */
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (level.isClientSide && entity instanceof Player player && player.getItemInHand(player.getUsedItemHand()).getItem() instanceof DShotgun)
            if (Keybindings.RELOAD.isDown() && selected && !player.getCooldowns().isOnCooldown(stack.getItem())) {
                Services.NETWORK.reloadDSG(slot);
            }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("doom.doomed_credit.text").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
        tooltip.add(Component.translatable("doom.doomed_credit1.text").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final DSGRender renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) return new DSGRender();
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

}