package mod.azure.doom.items.weapons;

import mod.azure.azurelib.Keybindings;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.Animation;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.MCDoom;
import mod.azure.doom.client.render.weapons.MeleeRender;
import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.entities.tierboss.*;
import mod.azure.doom.helper.CommonUtils;
import mod.azure.doom.items.enums.DoomTier;
import mod.azure.doom.items.enums.MeleeWeaponEnum;
import mod.azure.doom.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class BaseSwordItem extends SwordItem implements GeoItem {
    private static final String controller = "controller";
    protected final MeleeWeaponEnum meleeWeaponEnum;
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    protected BaseSwordItem(MeleeWeaponEnum meleeWeaponEnum, int maxUses) {
        super(DoomTier.DOOM_HIGHTEIR, 1, -2.5f, new Properties().stacksTo(1).durability(maxUses + 1));
        this.meleeWeaponEnum = meleeWeaponEnum;
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public static void reload(Player user, InteractionHand hand) {
        if (user.getItemInHand(hand).getItem() instanceof BaseSwordItem swordItem) {
            while (!user.isCreative() && user.getItemInHand(
                    hand).getDamageValue() != 0 && user.getInventory().countItem(swordItem.getAmmoType()) > 0) {
                CommonUtils.removeAmmo(swordItem.getAmmoType(), user);
                user.getCooldowns().addCooldown(swordItem, 5);
                user.getItemInHand(hand).hurtAndBreak(-5, user, s -> user.broadcastBreakEvent(hand));
                user.getItemInHand(hand).setPopTime(3);
            }
        }
    }

    public MeleeWeaponEnum getmeleeWeaponEnum() {
        return this.meleeWeaponEnum;
    }

    public Item getAmmoType() {
        switch (this.getmeleeWeaponEnum()) {
            case CHAINSAW, CHAINSAW_64, ETERNAL_CHAINSAW -> {
                return Services.ITEMS_HELPER.getGasItem();
            }
            case MARAUDER_AXE, DARK_CRUCIBLE, CRUCIBLE -> {
                return Services.ITEMS_HELPER.getArgentBlock();
            }
            default -> {
                return Services.ITEMS_HELPER.getArgentEngery();
            }
        }
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int slot, boolean selected) {
        final var playerentity = (Player) entity;
        if (!level.isClientSide && (this.meleeWeaponEnum == MeleeWeaponEnum.CRUCIBLE || this.meleeWeaponEnum == MeleeWeaponEnum.DARK_CRUCIBLE)) {
            if (playerentity.getMainHandItem().is(this) && selected) {
                triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) level), controller, "open");
            } else triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) level), controller, "close");
        }
        if (!level.isClientSide && this.meleeWeaponEnum == MeleeWeaponEnum.ETERNAL_CHAINSAW) {
            triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) level), controller, "running");
        }
        if (level.isClientSide && playerentity.getMainHandItem().getItem() instanceof BaseSwordItem && selected) {
            if (Keybindings.RELOAD.consumeClick()) {
                Services.NETWORK.reloadMelee(slot);
            }
        }
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity livingEntity) {
        if (livingEntity instanceof Player playerentity && stack.getDamageValue() < stack.getMaxDamage() - 1 && playerentity.getMainHandItem().getItem() instanceof BaseSwordItem swordItem) {
            final var aabb = new AABB(playerentity.blockPosition().above()).inflate(5D, 5D, 5D);
            playerentity.level().getEntities(playerentity, aabb).forEach(e -> doDamage(stack, playerentity, e));
            if (swordItem.getmeleeWeaponEnum() == MeleeWeaponEnum.CHAINSAW || swordItem.getmeleeWeaponEnum() == MeleeWeaponEnum.CHAINSAW_64 || swordItem.getmeleeWeaponEnum() == MeleeWeaponEnum.ETERNAL_CHAINSAW) {
                playerentity.level().getEntities(playerentity, aabb).forEach(e -> doDeathCheck(playerentity, e, stack));
                playerentity.level().getEntities(playerentity, aabb).forEach(this::addParticle);
            }
            stack.hurtAndBreak(1, playerentity, p -> p.broadcastBreakEvent(playerentity.getUsedItemHand()));
            if (swordItem.getmeleeWeaponEnum() == MeleeWeaponEnum.SENTINEL_HAMMER) {
                final var aoeEntity = new AreaEffectCloud(playerentity.level(), playerentity.getX(),
                        playerentity.getY(), playerentity.getZ());
                aoeEntity.setParticle(ParticleTypes.CRIT);
                aoeEntity.setRadius(5.0F);
                aoeEntity.setDuration(20);
                aoeEntity.setPos(playerentity.getX(), playerentity.getY(), playerentity.getZ());
                playerentity.level().addFreshEntity(aoeEntity);
            }
        }
        return stack.getDamageValue() < stack.getMaxDamage() - 1;
    }

    private void doDamage(ItemStack stack, LivingEntity user, Entity target) {
        if (target instanceof LivingEntity livingEntity) {
            target.invulnerableTime = 0;
            if (EnchantmentHelper.getItemEnchantmentLevel(
                    mod.azure.azurelib.platform.Services.PLATFORM.getIncendairyenchament(), stack) > 0)
                target.setSecondsOnFire(100);
            switch (getmeleeWeaponEnum()) {
                case CHAINSAW, CHAINSAW_64, ETERNAL_CHAINSAW -> {
                    target.hurt(user.damageSources().playerAttack((Player) user), MCDoom.config.chainsaw_damage);
                    user.level().playSound(null, user.getX(), user.getY(), user.getZ(),
                            Services.SOUNDS_HELPER.getCHAINSAW_ATTACKING(), SoundSource.PLAYERS, 0.3F,
                            1.0F / (user.level().random.nextFloat() * 0.4F + 1.2F) + 0.25F * 0.5F);
                }
                case MARAUDER_AXE -> target.hurt(user.damageSources().playerAttack((Player) user),
                        target instanceof ArchMakyrEntity || target instanceof GladiatorEntity || target instanceof IconofsinEntity || target instanceof MotherDemonEntity || target instanceof SpiderMastermindEntity ? MCDoom.config.marauder_axe_item_damage / 10F : MCDoom.config.marauder_axe_item_damage);
                case DARK_CRUCIBLE -> target.hurt(user.damageSources().playerAttack((Player) user),
                        target instanceof ArchMakyrEntity || target instanceof GladiatorEntity || target instanceof IconofsinEntity || target instanceof MotherDemonEntity || target instanceof SpiderMastermindEntity ? MCDoom.config.darkcrucible_damage / 10F : MCDoom.config.darkcrucible_damage);
                case SENTINEL_HAMMER -> {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1000, 2));
                    target.hurt(user.damageSources().playerAttack((Player) user), MCDoom.config.sentinelhammer_damage);
                }
                case CRUCIBLE -> target.hurt(user.damageSources().playerAttack((Player) user),
                        target instanceof ArchMakyrEntity || target instanceof GladiatorEntity || target instanceof IconofsinEntity || target instanceof MotherDemonEntity || target instanceof SpiderMastermindEntity ? MCDoom.config.crucible_damage / 10F : MCDoom.config.crucible_damage);
            }
        }
    }

    private void doDeathCheck(LivingEntity user, Entity target, ItemStack stack) {
        final var givenList = Arrays.asList(Services.ITEMS_HELPER.getChaingunBullets(),
                Services.ITEMS_HELPER.getShells(), Services.ITEMS_HELPER.getArgentBolts(),
                Services.ITEMS_HELPER.getEngeryCell(), Services.ITEMS_HELPER.getRocket());
        if (target instanceof DemonEntity && ((LivingEntity) target).isDeadOrDying() && user instanceof Player playerentity && stack.getDamageValue() < stack.getMaxDamage() - 1 && !playerentity.getCooldowns().isOnCooldown(
                this)) for (@SuppressWarnings("unused") final var i = 0; i < 5; ) {
            final var randomIndex = user.getRandom().nextInt(givenList.size());
            final var randomElement = givenList.get(randomIndex);
            target.spawnAtLocation(randomElement);
            break;
        }
    }

    private void addParticle(Entity target) {
        if (target instanceof LivingEntity)
            target.level().addParticle(ParticleTypes.CRIMSON_SPORE, target.getRandomX(0.5D), target.getRandomY(),
                    target.getRandomZ(0.5D), 0.0D, 0D, 0D);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        if (this.getmeleeWeaponEnum() == MeleeWeaponEnum.CHAINSAW || this.getmeleeWeaponEnum() == MeleeWeaponEnum.CHAINSAW_64 || this.getmeleeWeaponEnum() == MeleeWeaponEnum.ETERNAL_CHAINSAW) {
            tooltip.add(Component.translatable(
                    "Fuel: " + (stack.getMaxDamage() - stack.getDamageValue() - 1) + " / " + (stack.getMaxDamage() - 1)).withStyle(
                    ChatFormatting.ITALIC));
        }
        if (this.getmeleeWeaponEnum() == MeleeWeaponEnum.DARK_CRUCIBLE || this.getmeleeWeaponEnum() == MeleeWeaponEnum.CRUCIBLE)
            tooltip.add(Component.translatable("doom.crucible_sword.text").withStyle(ChatFormatting.RED).withStyle(
                    ChatFormatting.ITALIC));
        if (this.getmeleeWeaponEnum() == MeleeWeaponEnum.MARAUDER_AXE) {
            tooltip.add(Component.translatable("doom.marauder_axe1.text").withStyle(ChatFormatting.RED).withStyle(
                    ChatFormatting.ITALIC));
            tooltip.add(Component.translatable("doom.marauder_axe2.text").withStyle(ChatFormatting.RED).withStyle(
                    ChatFormatting.ITALIC));
            tooltip.add(Component.translatable("doom.marauder_axe3.text").withStyle(ChatFormatting.RED).withStyle(
                    ChatFormatting.ITALIC));
        }
        if (this.getmeleeWeaponEnum() == MeleeWeaponEnum.SENTINEL_HAMMER || this.getmeleeWeaponEnum() == MeleeWeaponEnum.DARK_CRUCIBLE || this.getmeleeWeaponEnum() == MeleeWeaponEnum.CRUCIBLE || this.getmeleeWeaponEnum() == MeleeWeaponEnum.MARAUDER_AXE) {
            tooltip.add(Component.translatable(
                    "Ammo: " + (stack.getMaxDamage() - stack.getDamageValue() - 1) + " / " + (stack.getMaxDamage() - 1)).withStyle(
                    ChatFormatting.ITALIC));
        }
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
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
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                new AnimationController<>(this, BaseSwordItem.controller, event -> PlayState.CONTINUE).triggerableAnim(
                        "open", RawAnimation.begin().thenPlay("opening").thenLoop("open")).triggerableAnim("close",
                        RawAnimation.begin().thenPlayAndHold("closed")).triggerableAnim("running",
                        RawAnimation.begin().then("running", Animation.LoopType.LOOP)));
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final MeleeRender<BaseSwordItem> renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) return new MeleeRender<BaseSwordItem>(getmeleeWeaponEnum());
                return this.renderer;
            }
        });
    }
}
