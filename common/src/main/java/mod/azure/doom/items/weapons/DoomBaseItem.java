package mod.azure.doom.items.weapons;

import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.Keybindings;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.Animation.LoopType;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.MCDoom;
import mod.azure.doom.client.DoomKeyBinds;
import mod.azure.doom.client.render.weapons.GunRender;
import mod.azure.doom.entities.projectiles.BulletEntity;
import mod.azure.doom.entities.projectiles.MeatHookEntity;
import mod.azure.doom.helper.CommonUtils;
import mod.azure.doom.helper.PlayerProperties;
import mod.azure.doom.items.enums.GunTypeEnum;
import mod.azure.doom.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class DoomBaseItem extends Item implements GeoItem {
    private int clipSize;
    protected final GunTypeEnum gunTypeEnum;
    private static final String firing = "firing";
    private static final String controller = "controller";
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    protected DoomBaseItem(GunTypeEnum gunTypeEnum, int maxClipSize) {
        super(new Item.Properties().stacksTo(1).durability(maxClipSize + 1));
        this.gunTypeEnum = gunTypeEnum;
        this.clipSize = maxClipSize;
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public static void shoot(Player player) {
        if (player.getMainHandItem().getDamageValue() < (player.getMainHandItem().getMaxDamage() - 1) && player.getMainHandItem().getItem() instanceof DoomBaseItem gunBase) {
            if (!player.getCooldowns().isOnCooldown(player.getMainHandItem().getItem()))
                gunBase.singleFire(player.getMainHandItem(), player.level(), player);
        } else {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    Services.SOUNDS_HELPER.getEMPTY(), SoundSource.PLAYERS, 0.25F, 1.3F);
        }
    }

    public static void shootHook(Player player) {
        final var stack = player.getMainHandItem();
        if (stack.getDamageValue() < stack.getMaxDamage() - 2 && !player.level().isClientSide() && !player.getCooldowns().isOnCooldown(
                stack.getItem()) && stack.getItem() instanceof DoomBaseItem gunItem && gunItem.getGunTypeEnum() == GunTypeEnum.SUPERSHOTGUN) {
            player.getCooldowns().addCooldown(gunItem, 5);
            if (!((PlayerProperties) player).hasMeatHook()) {
                final var hookshot = new MeatHookEntity(player.level(), player);
                hookshot.shootFromRotation(player, player.getXRot(), player.getYRot() + 10, 0.0F, 20.0F, 1.0F);
                hookshot.setProperties(stack, MCDoom.config.max_meathook_distance, 100, player.getXRot(),
                        player.getYRot(), 0f, 1.5F);
                hookshot.getEntityData().set(MeatHookEntity.FORCED_YAW, player.getYRot());
                hookshot.setVariant(0);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.CHAIN_FALL,
                        SoundSource.PLAYERS, 1.5F, 1.3F);
                player.level().addFreshEntity(hookshot);
            }
            ((PlayerProperties) player).setHasMeatHook(!((PlayerProperties) player).hasMeatHook());
        }
    }

    public static void reload(Player user, InteractionHand hand) {
        if (user.getMainHandItem().getItem() instanceof DoomBaseItem gunBase) {
            while (!user.isCreative() && user.getMainHandItem().getDamageValue() != 0 && user.getInventory().countItem(
                    gunBase.getAmmoType()) > 0) {
                CommonUtils.removeAmmo(gunBase.getAmmoType(), user);
                user.getCooldowns().addCooldown(gunBase, gunBase.getReloadCoolDown());
                user.getMainHandItem().hurtAndBreak(-gunBase.getReloadAmount(), user,
                        s -> user.broadcastBreakEvent(hand));
                user.getMainHandItem().setPopTime(3);
                if (gunBase.getReloadSound() != null)
                    user.level().playSound(null, user.getX(), user.getY(), user.getZ(), gunBase.getReloadSound(),
                            SoundSource.PLAYERS, 1.00F, 1.0F);
                if (!user.level().isClientSide && (gunBase.getGunTypeEnum() == GunTypeEnum.SUPERSHOTGUN || gunBase.getGunTypeEnum() == GunTypeEnum.DSHOTGUN || gunBase.getGunTypeEnum() == GunTypeEnum.SHOTGUN))
                    gunBase.triggerAnim(user,
                            GeoItem.getOrAssignId(user.getItemInHand(hand), (ServerLevel) user.level()),
                            DoomBaseItem.controller, "reload");
            }
        }
    }

    public GunTypeEnum getGunTypeEnum() {
        return this.gunTypeEnum;
    }

    public Item getAmmoType() {
        switch (this.getGunTypeEnum()) {
            case BALLISTA, DGAUSS -> {
                return Services.ITEMS_HELPER.getArgentBolts();
            }
            case BFG, BFG9000 -> {
                return Services.ITEMS_HELPER.getBFGCell();
            }
            case DPLASMA, PLAMSA -> {
                return Services.ITEMS_HELPER.getEngeryCell();
            }
            case DSHOTGUN, SHOTGUN, SUPERSHOTGUN -> {
                return Services.ITEMS_HELPER.getShells();
            }
            case HEAVYCANNON, PISTOL -> {
                return Services.ITEMS_HELPER.getBullets();
            }
            case ROCKETLAUNCHER -> {
                return Services.ITEMS_HELPER.getRocket();
            }
            case UNMAKER, UNMAYKR -> {
                return Services.ITEMS_HELPER.getUnmaykrBolts();
            }
            default -> {
                return Services.ITEMS_HELPER.getChaingunBullets();
            }
        }
    }

    public SoundEvent getReloadSound() {
        switch (this.getGunTypeEnum()) {
            case BALLISTA, BFG, BFG9000, CHAINGUN, DGAUSS, DPLASMA, HEAVYCANNON, PISTOL, PLAMSA, UNMAKER, UNMAYKR -> {
                return Services.SOUNDS_HELPER.getCLIPRELOAD();
            }
            case DSHOTGUN, SHOTGUN, SUPERSHOTGUN -> {
                return Services.SOUNDS_HELPER.getSHOTGUNRELOAD();
            }
            default -> {
                return SoundEvents.METAL_BREAK;
            }
        }
    }

    public SoundEvent getFiringSound() {
        switch (this.getGunTypeEnum()) {
            case BALLISTA, DGAUSS -> {
                return Services.SOUNDS_HELPER.getBALLISTA_FIRING();
            }
            case BFG, BFG9000 -> {
                return Services.SOUNDS_HELPER.getBFG_FIRING();
            }
            case CHAINGUN -> {
                return Services.SOUNDS_HELPER.getCHAINGUN_SHOOT();
            }
            case DPLASMA, PLAMSA -> {
                return Services.SOUNDS_HELPER.getPLASMA_FIRING();
            }
            case DSHOTGUN, SHOTGUN -> {
                return Services.SOUNDS_HELPER.getSHOTGUN_SHOOT();
            }
            case HEAVYCANNON -> {
                return Services.SOUNDS_HELPER.getHEAVY_CANNON();
            }
            case PISTOL -> {
                return Services.SOUNDS_HELPER.getPISTOL_HIT();
            }
            case ROCKETLAUNCHER -> {
                return Services.SOUNDS_HELPER.getROCKET_FIRING();
            }
            case SUPERSHOTGUN -> {
                return Services.SOUNDS_HELPER.getSUPER_SHOTGUN_SHOOT();
            }
            case UNMAKER, UNMAYKR -> {
                return Services.SOUNDS_HELPER.getUNMAKYR_FIRE();
            }
        }
        return null;
    }

    public int getReloadAmount() {
        switch (this.getGunTypeEnum()) {
            case BALLISTA, DGAUSS -> {
                return 1;
            }
            case BFG, BFG9000, DPLASMA, PLAMSA, UNMAKER, UNMAYKR -> {
                return 20;
            }
            case DSHOTGUN, SHOTGUN, SUPERSHOTGUN -> {
                return 4;
            }
            case HEAVYCANNON, PISTOL -> {
                return 10;
            }
            case ROCKETLAUNCHER -> {
                return 2;
            }
            default -> {
                return 50;
            }
        }
    }

    public int getCoolDown() {
        switch (this.gunTypeEnum) {
            case BALLISTA, BFG, BFG9000, DGAUSS, ROCKETLAUNCHER -> {
                return 40;
            }
            case DSHOTGUN, SHOTGUN -> {
                return 18;
            }
            case SUPERSHOTGUN -> {
                return 30;
            }
            case DPLASMA, PISTOL, PLAMSA, UNMAKER, UNMAYKR -> {
                return 5;
            }
            case HEAVYCANNON -> {
                return 4;
            }
            default -> {
                return 3;
            }
        }
    }

    public int getReloadCoolDown() {
        switch (this.gunTypeEnum) {
            case BALLISTA, BFG, BFG9000, DGAUSS, SUPERSHOTGUN -> {
                return 15;
            }
            case CHAINGUN, ROCKETLAUNCHER, UNMAKER, UNMAYKR -> {
                return 10;
            }
            case DPLASMA, DSHOTGUN, HEAVYCANNON, PISTOL, PLAMSA, SHOTGUN -> {
                return 5;
            }
        }
        return 0;
    }

    private void singleFire(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Player player) {
        player.getCooldowns().addCooldown(this, this.getCoolDown());
        CommonUtils.spawnLightSource(player, player.level().isWaterAt(player.blockPosition()));
        itemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
        if (this.getFiringSound() != null && !itemStack.getTag().getBoolean("isAltFiring"))
            level.playSound(null, player.getX(), player.getY(), player.getZ(), getFiringSound(), SoundSource.PLAYERS,
                    0.25F, 1.3F);
        Projectile bullet = null;
        switch (this.gunTypeEnum) {
            case BALLISTA -> {
                bullet = CommonUtils.createBullet(level, itemStack, player, MCDoom.config.argent_bolt_damage);
                ((BulletEntity) bullet).setParticle(3);
                bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
            }
            case DGAUSS -> {
                bullet = CommonUtils.createBullet(level, itemStack, player, MCDoom.config.argent_bolt_damage);
                ((BulletEntity) bullet).setParticle(4);
                bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
            }
            case BFG, BFG9000 -> {
                bullet = CommonUtils.createBFG(level, player);
                bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.75F, 1.0F);
            }
            case CHAINGUN -> {
                bullet = CommonUtils.createBullet(level, itemStack, player, MCDoom.config.chaingun_bullet_damage);
                ((BulletEntity) bullet).setParticle(2);
                bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
            }
            case DPLASMA, PLAMSA -> {
                if (itemStack.getTag().getBoolean("isAltFiring")) {
                    player.getCooldowns().addCooldown(this, 30);
                    if (!((PlayerProperties) player).hasMeatHook()) {
                        final var hookshot = new MeatHookEntity(player.level(), player);
                        hookshot.shootFromRotation(player, player.getXRot(), player.getYRot() + 10, 0.0F, 20.0F, 1.0F);
                        hookshot.setProperties(itemStack, MCDoom.config.max_meathook_distance, 100, player.getXRot(),
                                player.getYRot(), 0f, 1.5F);
                        hookshot.getEntityData().set(MeatHookEntity.FORCED_YAW, player.getYRot());
                        hookshot.setVariant(1);
                        player.level().addFreshEntity(hookshot);
                    }
                    ((PlayerProperties) player).setHasMeatHook(!((PlayerProperties) player).hasMeatHook());
                    itemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), getFiringSound(),
                            SoundSource.PLAYERS, 0.25F, 1.3F);
                } else {
                    bullet = CommonUtils.createBullet(level, itemStack, player, MCDoom.config.energycell_damage);
                    ((BulletEntity) bullet).setParticle(6);
                    bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), getFiringSound(),
                            SoundSource.PLAYERS, 0.25F, 1.3F);
                }
            }
            case DSHOTGUN -> {
                if (itemStack.getTag().getBoolean("isAltFiring")) {
                    bullet = CommonUtils.createBullet(level, itemStack, player, MCDoom.config.bullet_damage);
                    ((BulletEntity) bullet).setParticle(7);
                    bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
                    itemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
                    player.getCooldowns().addCooldown(this, 30);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            Services.SOUNDS_HELPER.getHEAVY_CANNON(), SoundSource.PLAYERS, 0.25F, 1.3F);
                } else {
                    bullet = CommonUtils.createBullet(level, itemStack, player, MCDoom.config.bullet_damage);
                    ((BulletEntity) bullet).setParticle(2);
                    bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), getFiringSound(),
                            SoundSource.PLAYERS, 0.25F, 1.3F);
                }
            }
            case SHOTGUN -> {
                bullet = CommonUtils.createBullet(level, itemStack, player, MCDoom.config.shotgun_damage);
                bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
            }
            case PISTOL -> {
                bullet = CommonUtils.createBullet(level, itemStack, player, MCDoom.config.bullet_damage);
                ((BulletEntity) bullet).setParticle(1);
                bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
            }
            case HEAVYCANNON -> {
                if (itemStack.getTag().getBoolean("isAltFiring")) {
                    bullet = CommonUtils.createBullet(level, itemStack, player, MCDoom.config.bullet_damage);
                    ((BulletEntity) bullet).setParticle(8);
                    bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
                    player.getCooldowns().addCooldown(this, this.getCoolDown());
                    itemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), getFiringSound(),
                            SoundSource.PLAYERS, 0.25F, 1.3F);
                } else {
                    bullet = CommonUtils.createBullet(level, itemStack, player, MCDoom.config.bullet_damage);
                    ((BulletEntity) bullet).setParticle(2);
                    bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), getFiringSound(),
                            SoundSource.PLAYERS, 0.25F, 1.3F);
                }
            }
            case ROCKETLAUNCHER -> {
                bullet = CommonUtils.createRocket(level, itemStack, player);
                bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.75F, 1.0F);
            }
            case SUPERSHOTGUN -> {
                bullet = CommonUtils.createBullet(level, itemStack, player, MCDoom.config.shotgun_damage);
                ((BulletEntity) bullet).setParticle(2);
                bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);
                var bullet2 = CommonUtils.createBullet(level, itemStack, player, MCDoom.config.shotgun_damage);
                bullet2.shootFromRotation(player, player.getXRot(), player.getYRot() - 1, 0.0F, 3.0F, 1.0F);
                bullet2.setParticle(2);
                if (EnchantmentHelper.getItemEnchantmentLevel(
                        mod.azure.azurelib.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                    bullet2.setSecondsOnFire(100);
                level.addFreshEntity(bullet2);
            }
            case UNMAKER, UNMAYKR -> {
                bullet = CommonUtils.createBullet(level, itemStack, player, MCDoom.config.unmaykr_damage);
                ((BulletEntity) bullet).setParticle(5);
                bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3.0F, 1.0F);

                var bullet1 = CommonUtils.createBullet(level, itemStack, player, MCDoom.config.unmaykr_damage);
                bullet1.shootFromRotation(player, player.getXRot(), player.getYRot() + 10, 0.0F, 3.0F, 1.0F);
                if (EnchantmentHelper.getItemEnchantmentLevel(
                        mod.azure.azurelib.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                    bullet1.setSecondsOnFire(100);
                level.addFreshEntity(bullet1);

                var bullet2 = CommonUtils.createBullet(level, itemStack, player, MCDoom.config.unmaykr_damage);
                bullet2.shootFromRotation(player, player.getXRot(), player.getYRot() - 10, 0.0F, 3.0F, 1.0F);
                if (EnchantmentHelper.getItemEnchantmentLevel(
                        mod.azure.azurelib.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                    bullet2.setSecondsOnFire(100);
                level.addFreshEntity(bullet2);
            }
        }
        if (bullet != null) {
            if (EnchantmentHelper.getItemEnchantmentLevel(
                    mod.azure.azurelib.platform.Services.PLATFORM.getIncendairyenchament(), itemStack) > 0)
                bullet.setSecondsOnFire(100);
            if (this.getGunTypeEnum() != GunTypeEnum.BFG && this.getGunTypeEnum() != GunTypeEnum.DPLASMA)
                bullet.moveTo(player.getX(), player.getY(0.5), player.getZ(), 0, 0);
            level.addFreshEntity(bullet);
        }
        if (gunTypeEnum != GunTypeEnum.PLAMSA)
            this.triggerAnim(player, GeoItem.getOrAssignId(itemStack, (ServerLevel) player.level()),
                    DoomBaseItem.controller, "firing");
        if (gunTypeEnum == GunTypeEnum.PLAMSA)
            this.triggerAnim(player, GeoItem.getOrAssignId(itemStack, (ServerLevel) player.level()),
                    DoomBaseItem.controller, "firing_faster");
    }

    public static void changeFireMode(@NotNull ItemStack stack, @NotNull Player player) {
        if (EnchantmentHelper.getItemEnchantmentLevel(Services.ITEMS_HELPER.getMicroEnchantment(),
                stack) > 0 || EnchantmentHelper.getItemEnchantmentLevel(Services.ITEMS_HELPER.getStickEnchantment(),
                stack) > 0 || EnchantmentHelper.getItemEnchantmentLevel(Services.ITEMS_HELPER.getMicrowaveEnchantment(),
                stack) > 0) {
            stack.getTag().putBoolean("isAltFiring", !stack.getOrCreateTag().getBoolean("isAltFiring"));
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LEVER_CLICK,
                    SoundSource.PLAYERS, 0.25F, 1.3F);
            player.sendSystemMessage(Component.literal("Changing Fire Mode"));
        }
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level world, @NotNull Entity entity, int slot, boolean selected) {
        CompoundTag compoundTag = stack.getOrCreateTag();
        if (world.isClientSide && entity instanceof Player player && player.getMainHandItem().getItem() instanceof DoomBaseItem && selected) {
            if (Keybindings.RELOAD.consumeClick()) {
                Services.NETWORK.reload(slot);
            }
            if (AzureLibMod.config.useVanillaUseKey) {
                if (Minecraft.getInstance().options.keyUse.isDown()) {
                    Services.NETWORK.shoot(slot);
                }
            } else {
                if (Keybindings.FIRE_WEAPON.isDown()) {
                    Services.NETWORK.shoot(slot);
                }
            }
            if (DoomKeyBinds.HOOK.consumeClick()) {
                Services.NETWORK.hook(slot);
            }
            if (DoomKeyBinds.FIRETYPE.consumeClick() && (this.getGunTypeEnum() == GunTypeEnum.DSHOTGUN || this.getGunTypeEnum() == GunTypeEnum.HEAVYCANNON || this.getGunTypeEnum() == GunTypeEnum.DPLASMA || this.getGunTypeEnum() == GunTypeEnum.PLAMSA)) {
                Services.NETWORK.changeFireMode(stack);
            }
        }
        if ((this.getGunTypeEnum() == GunTypeEnum.DSHOTGUN) && EnchantmentHelper.getItemEnchantmentLevel(
                Services.ITEMS_HELPER.getStickEnchantment(), stack) > 0 && !compoundTag.contains("isAltFiring")) {
            compoundTag.putBoolean("isAltFiring", false);
        }
        if ((this.getGunTypeEnum() == GunTypeEnum.HEAVYCANNON) && EnchantmentHelper.getItemEnchantmentLevel(
                Services.ITEMS_HELPER.getMicroEnchantment(), stack) > 0 && !compoundTag.contains("isAltFiring")) {
            compoundTag.putBoolean("isAltFiring", false);
        }
        if (this.getGunTypeEnum() == GunTypeEnum.DPLASMA && EnchantmentHelper.getItemEnchantmentLevel(
                Services.ITEMS_HELPER.getMicrowaveEnchantment(), stack) > 0 && !compoundTag.contains("isAltFiring")) {
            compoundTag.putBoolean("isAltFiring", false);
        }
        if (this.getGunTypeEnum() == GunTypeEnum.PLAMSA && EnchantmentHelper.getItemEnchantmentLevel(
                Services.ITEMS_HELPER.getMicrowaveEnchantment(), stack) > 0 && !compoundTag.contains("isAltFiring")) {
            compoundTag.putBoolean("isAltFiring", false);
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player user, @NotNull InteractionHand hand) {
        final var itemStack = user.getItemInHand(hand);
        user.startUsingItem(hand);
        return InteractionResultHolder.consume(itemStack);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> tooltip, @NotNull TooltipFlag tooltipFlag) {
        tooltip.add(Component.translatable(
                "Ammo: " + (itemStack.getMaxDamage() - itemStack.getDamageValue() - 1) + " / " + (itemStack.getMaxDamage() - 1)).withStyle(
                ChatFormatting.ITALIC));
        if (getGunTypeEnum() == GunTypeEnum.DGAUSS || getGunTypeEnum() == GunTypeEnum.DPLASMA || getGunTypeEnum() == GunTypeEnum.DSHOTGUN) {
            tooltip.add(Component.translatable("doom.doomed_credit.text").withStyle(ChatFormatting.RED).withStyle(
                    ChatFormatting.ITALIC));
            tooltip.add(Component.translatable("doom.doomed_credit1.text").withStyle(ChatFormatting.RED).withStyle(
                    ChatFormatting.ITALIC));
        }
        if (itemStack.getTag().contains("isAltFiring")) {
            tooltip.add(Component.translatable(
                    "Alt Fire: " + itemStack.getOrCreateTag().getBoolean("isAltFiring")).withStyle(
                    ChatFormatting.ITALIC));
        }
        super.appendHoverText(itemStack, level, tooltip, tooltipFlag);
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(
                new AnimationController<>(this, DoomBaseItem.controller, event -> PlayState.CONTINUE).triggerableAnim(
                        DoomBaseItem.firing,
                        RawAnimation.begin().then(DoomBaseItem.firing, LoopType.PLAY_ONCE)).triggerableAnim(
                        "firing_faster",
                        RawAnimation.begin().then("firing_faster", LoopType.PLAY_ONCE)).triggerableAnim("hook",
                        RawAnimation.begin().then("hook", LoopType.PLAY_ONCE)).triggerableAnim("reload",
                        RawAnimation.begin().then("reload", LoopType.PLAY_ONCE)));
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final GunRender<DoomBaseItem> renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) return new GunRender<DoomBaseItem>(getGunTypeEnum());
                return this.renderer;
            }
        });
    }
}