package mod.azure.doom.items.weapons;

import mod.azure.azurelib.Keybindings;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.doom.client.render.weapons.SGRender;
import mod.azure.doom.helper.CommonUtils;
import mod.azure.doom.platform.Services;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Shotgun extends DoomBaseItem {

    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public Shotgun() {
        super(new Properties().stacksTo(1).durability(51));
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public static void reload(Player user, InteractionHand hand) {
        if (user.getItemInHand(hand).getItem() instanceof Shotgun gun) {
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
        if (level.isClientSide && entity instanceof Player player && player.getItemInHand(player.getUsedItemHand()).getItem() instanceof Shotgun)
            if (Keybindings.RELOAD.isDown() && selected && !player.getCooldowns().isOnCooldown(stack.getItem())) {
                Services.NETWORK.reloadSG(slot);
            }
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final SGRender renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) return new SGRender();
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }
}