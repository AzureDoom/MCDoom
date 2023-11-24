package mod.azure.doom.items.weapons;

import mod.azure.azurelib.Keybindings;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.doom.MCDoom;
import mod.azure.doom.client.render.weapons.SSGRender;
import mod.azure.doom.entities.projectiles.MeatHookEntity;
import mod.azure.doom.helper.CommonUtils;
import mod.azure.doom.helper.PlayerProperties;
import mod.azure.doom.platform.Services;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SuperShotgun extends DoomBaseItem {

    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public SuperShotgun() {
        super(new Properties().stacksTo(1).durability(53));
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public static void reload(Player user, InteractionHand hand) {
        if (user.getItemInHand(hand).getItem() instanceof SuperShotgun gun) {
            while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(Services.ITEMS_HELPER.getShells()) > 0) {
                CommonUtils.removeAmmo(Services.ITEMS_HELPER.getShells(), user);
                user.getItemInHand(hand).hurtAndBreak(-4, user, s -> user.broadcastBreakEvent(hand));
                user.getItemInHand(hand).setPopTime(3);
                user.level().playSound(null, user.getX(), user.getY(), user.getZ(), Services.SOUNDS_HELPER.getSHOTGUNRELOAD(), SoundSource.PLAYERS, 1.0F, 1.5F);
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
                final var bullet = CommonUtils.createShell(level, stack, playerentity);
                bullet.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(), 0.0F, 3.0F, 1.0F);
                if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.platform.Services.PLATFORM.getIncendairyenchament(), stack) > 0)
                    bullet.setSecondsOnFire(100);
                level.addFreshEntity(bullet);
                final var bullet2 = CommonUtils.createShell(level, stack, playerentity);
                bullet2.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot() - 1, 0.0F, 3.0F, 1.0F);
                if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.platform.Services.PLATFORM.getIncendairyenchament(), stack) > 0)
                    bullet2.setSecondsOnFire(100);
                level.addFreshEntity(bullet2);
                stack.hurtAndBreak(1, playerentity, p -> p.broadcastBreakEvent(playerentity.getUsedItemHand()));
                level.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), Services.SOUNDS_HELPER.getSUPER_SHOTGUN_SHOOT(), SoundSource.PLAYERS, 1.0F, 1.0F / (level.random.nextFloat() * 0.4F + 1.2F) + 0.25F * 0.5F);
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
        if (level.isClientSide && entity instanceof Player player && player.getItemInHand(player.getUsedItemHand()).getItem() instanceof SuperShotgun)
            if (Keybindings.RELOAD.isDown() && selected && !player.getCooldowns().isOnCooldown(stack.getItem())) {
                Services.NETWORK.reloadSSG(slot);
            }
        if (((Player) entity).getMainHandItem().getItem() instanceof SuperShotgun && selected && ((PlayerProperties) entity).hasMeatHook())
            ((PlayerProperties) entity).setHasMeatHook(false);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        final var stack = player.getOffhandItem();
        if (stack.getDamageValue() < stack.getMaxDamage() - 2 && !world.isClientSide() && stack.getItem() instanceof SuperShotgun) {
            player.getCooldowns().addCooldown(this, 5);
            if (!((PlayerProperties) player).hasMeatHook()) {
                final var hookshot = new MeatHookEntity(world, player);
                hookshot.setProperties(stack, MCDoom.config.max_meathook_distance, 10, player.getXRot(), player.getYRot(), 0f, 1.5f * (1));
                hookshot.getEntityData().set(MeatHookEntity.FORCED_YAW, player.getYRot());
                world.addFreshEntity(hookshot);
            }
            ((PlayerProperties) player).setHasMeatHook(!((PlayerProperties) player).hasMeatHook());
        }
        return super.use(world, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        ((PlayerProperties) user).setHasMeatHook(false);
        return super.finishUsingItem(stack, world, user);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final SSGRender renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) return new SSGRender();
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }
}