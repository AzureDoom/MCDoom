package mod.azure.doom.items.weapons;

import mod.azure.azurelib.Keybindings;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.MCDoom;
import mod.azure.doom.client.render.weapons.SentinelHammerRender;
import mod.azure.doom.helper.CommonUtils;
import mod.azure.doom.items.enums.DoomTier;
import mod.azure.doom.platform.Services;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SentinelHammerItem extends BaseSwordItem implements GeoItem {

    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public SentinelHammerItem() {
        super(DoomTier.DOOM_HIGHTEIR, 1, -2.5f, new Properties().stacksTo(1).durability(MCDoom.config.sentinelhammer_max_uses));
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public static void reload(Player user, InteractionHand hand) {
        if (user.getItemInHand(hand).getItem() instanceof SentinelHammerItem) {
            while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(Services.ITEMS_HELPER.getArgentEngery()) > 0) {
                CommonUtils.removeAmmo(Services.ITEMS_HELPER.getArgentEngery(), user);
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
        if (level.isClientSide && entity instanceof Player player && player.getItemInHand(player.getUsedItemHand()).getItem() instanceof SentinelHammerItem)
            if (Keybindings.RELOAD.isDown() && selected && !player.getCooldowns().isOnCooldown(stack.getItem())) {
                Services.NETWORK.reloadSentinel(slot);
            }
    }

    private void doDamage(ItemStack stack, LivingEntity user, Entity target) {
        if (target instanceof LivingEntity) {
            target.invulnerableTime = 0;
            if (EnchantmentHelper.getItemEnchantmentLevel(mod.azure.azurelib.platform.Services.PLATFORM.getIncendairyenchament(), stack) > 0)
                target.setSecondsOnFire(100);
            ((LivingEntity) target).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1000, 2));
            target.hurt(user.damageSources().playerAttack((Player) user), MCDoom.config.sentinelhammer_damage);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "popup_controller", 0, state -> PlayState.CONTINUE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity miner) {
        if (miner instanceof Player playerentity && stack.getDamageValue() < stack.getMaxDamage() - 1 && playerentity.getMainHandItem().getItem() instanceof SentinelHammerItem) {
            final var aabb = new AABB(miner.blockPosition().above()).inflate(5D, 5D, 5D);
            miner.level().getEntities(miner, aabb).forEach(e -> doDamage(stack, playerentity, e));
            stack.hurtAndBreak(1, miner, p -> p.broadcastBreakEvent(playerentity.getUsedItemHand()));
            final var areaeffectcloudentity = new AreaEffectCloud(miner.level(), miner.getX(), playerentity.getY(), playerentity.getZ());
            areaeffectcloudentity.setParticle(ParticleTypes.CRIT);
            areaeffectcloudentity.setRadius(5.0F);
            areaeffectcloudentity.setDuration(20);
            areaeffectcloudentity.setPos(playerentity.getX(), playerentity.getY(), playerentity.getZ());
            playerentity.level().addFreshEntity(areaeffectcloudentity);
        }
        return stack.getDamageValue() < stack.getMaxDamage() - 1;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final SentinelHammerRender renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) return new SentinelHammerRender();
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

}