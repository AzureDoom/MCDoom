package mod.azure.doom.items.weapons;

import mod.azure.azurelib.Keybindings;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.MCDoom;
import mod.azure.doom.client.render.weapons.DarkLordCrucibleRender;
import mod.azure.doom.entities.tierboss.*;
import mod.azure.doom.helper.CommonUtils;
import mod.azure.doom.items.enums.DoomTier;
import mod.azure.doom.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DarkLordCrucibleItem extends BaseSwordItem implements GeoItem {

    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public DarkLordCrucibleItem() {
        super(DoomTier.DOOM_HIGHTEIR, 1, -2.5f, new Properties().stacksTo(1).durability(MCDoom.config.darkcrucible_max_uses));
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public static void reload(Player user, InteractionHand hand) {
        if (user.getItemInHand(hand).getItem() instanceof DarkLordCrucibleItem) {
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
        final Player playerentity = (Player) entity;
        if (!level.isClientSide)
            if (playerentity.getMainHandItem().is(this) && selected)
                triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) level), "shoot_controller", "open");
            else
                triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) level), "shoot_controller", "close");
        if (level.isClientSide && entity instanceof Player player && player.getItemInHand(player.getUsedItemHand()).getItem() instanceof DarkLordCrucibleItem)
            if (Keybindings.RELOAD.isDown() && selected && !player.getCooldowns().isOnCooldown(stack.getItem())) {
                Services.NETWORK.reloadDLCrucible(slot);
            }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity miner) {
        if (miner instanceof Player playerentity && stack.getDamageValue() < stack.getMaxDamage() - 1 && playerentity.getMainHandItem().getItem() instanceof DarkLordCrucibleItem) {
            final var aabb = new AABB(miner.blockPosition().above()).inflate(4D, 1D, 4D);
            miner.getCommandSenderWorld().getEntities(miner, aabb).forEach(e -> doDamage(playerentity, e));
            stack.hurtAndBreak(1, miner, p -> p.broadcastBreakEvent(playerentity.getUsedItemHand()));
        }
        return stack.getDamageValue() < stack.getMaxDamage() - 1;
    }

    private void doDamage(LivingEntity user, Entity target) {
        if (target instanceof LivingEntity) {
            target.invulnerableTime = 0;
            target.hurt(user.damageSources().playerAttack((Player) user), target instanceof ArchMakyrEntity || target instanceof GladiatorEntity || target instanceof IconofsinEntity || target instanceof MotherDemonEntity || target instanceof SpiderMastermind2016Entity || target instanceof SpiderMastermindEntity ? MCDoom.config.darkcrucible_damage / 10F : MCDoom.config.darkcrucible_damage);
        }
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "shoot_controller", event -> PlayState.CONTINUE).triggerableAnim("open", RawAnimation.begin().thenPlay("opening").thenLoop("open")).triggerableAnim("close", RawAnimation.begin().thenPlayAndHold("closed")));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("Ammo: " + (stack.getMaxDamage() - stack.getDamageValue() - 1) + " / " + (stack.getMaxDamage() - 1)).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final DarkLordCrucibleRender renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null)
                    return new DarkLordCrucibleRender();
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }
}