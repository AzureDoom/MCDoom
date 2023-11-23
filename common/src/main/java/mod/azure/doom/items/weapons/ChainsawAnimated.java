package mod.azure.doom.items.weapons;

import mod.azure.azurelib.Keybindings;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.MCDoom;
import mod.azure.doom.client.render.weapons.ChainsawRender;
import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.helper.CommonUtils;
import mod.azure.doom.items.enums.DoomTier;
import mod.azure.doom.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ChainsawAnimated extends BaseSwordItem implements GeoItem {

    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public ChainsawAnimated() {
        super(DoomTier.DOOM_HIGHTEIR, 1, -3.1f, new Properties().stacksTo(1).durability(601));
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public static void reload(Player user, InteractionHand hand) {
        if (user.getItemInHand(hand).getItem() instanceof ChainsawAnimated) {
            while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(Services.ITEMS_HELPER.getGasItem()) > 0) {
                CommonUtils.removeAmmo(Services.ITEMS_HELPER.getGasItem(), user);
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
        if (level.isClientSide && entity instanceof Player player && player.getItemInHand(player.getUsedItemHand()).getItem() instanceof ChainsawAnimated)
            if (Keybindings.RELOAD.isDown() && selected && !player.getCooldowns().isOnCooldown(stack.getItem())) {
                Services.NETWORK.reloadChainsawA(slot);
            }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity miner) {
        if (miner instanceof Player playerentity && stack.getDamageValue() < stack.getMaxDamage() - 1 && playerentity.getMainHandItem().getItem() instanceof ChainsawAnimated) {
            final var aabb = new AABB(playerentity.blockPosition().above()).inflate(1D, 1D, 1D);
            playerentity.level().getEntities(playerentity, aabb).forEach(e -> doDamage(playerentity, e));
            playerentity.level().getEntities(playerentity, aabb).forEach(e -> doDeathCheck(playerentity, e, stack));
            playerentity.level().getEntities(playerentity, aabb).forEach(e -> damageItem(playerentity, stack));
            playerentity.level().getEntities(playerentity, aabb).forEach(this::addParticle);
            if (!Services.PLATFORM.isModLoaded("bettercombat"))
                playerentity.level().playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), Services.SOUNDS_HELPER.getCHAINSAW_IDLE(), SoundSource.PLAYERS, 0.05F, 1.0F / (playerentity.level().random.nextFloat() * 0.4F + 1.2F) + 0.25F * 0.5F);
        }
        return stack.getDamageValue() < stack.getMaxDamage() - 1;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "shoot_controller", event -> event.setAndContinue(RawAnimation.begin().thenLoop("running"))));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("Fuel: " + (stack.getMaxDamage() - stack.getDamageValue() - 1) + " / " + (stack.getMaxDamage() - 1)).withStyle(ChatFormatting.ITALIC));
    }

    private void doDamage(LivingEntity user, Entity target) {
        if (target instanceof LivingEntity) {
            target.setDeltaMovement(0, 0, 0);
            target.invulnerableTime = 0;
            target.hurt(user.damageSources().playerAttack((Player) user), MCDoom.config.chainsaw_damage);
            user.level().playSound(null, user.getX(), user.getY(), user.getZ(), Services.SOUNDS_HELPER.getCHAINSAW_ATTACKING(), SoundSource.PLAYERS, 0.3F, 1.0F / (user.level().random.nextFloat() * 0.4F + 1.2F) + 0.25F * 0.5F);
        }
    }

    private void doDeathCheck(LivingEntity user, Entity target, ItemStack stack) {
        final var givenList = Arrays.asList(Services.ITEMS_HELPER.getChaingunBullets(), Services.ITEMS_HELPER.getShells(), Services.ITEMS_HELPER.getArgentBolts(), Services.ITEMS_HELPER.getEngeryCell(), Services.ITEMS_HELPER.getRocket());
        if (target instanceof DemonEntity && !(target instanceof Player) && ((LivingEntity) target).isDeadOrDying() && user instanceof Player playerentity && stack.getDamageValue() < stack.getMaxDamage() - 1 && !playerentity.getCooldowns().isOnCooldown(this))
            for (@SuppressWarnings("unused") final var i = 0; i < 5; ) {
                final var randomIndex = user.getRandom().nextInt(givenList.size());
                final var randomElement = givenList.get(randomIndex);
                target.spawnAtLocation(randomElement);
                break;
            }
    }

    private void damageItem(LivingEntity user, ItemStack stack) {
        final var player = (Player) user;
        if (!player.getAbilities().instabuild) stack.setDamageValue(stack.getDamageValue() + 1);
        player.getCooldowns().addCooldown(this, 10);
    }

    private void addParticle(Entity target) {
        if (target instanceof LivingEntity)
            target.level().addParticle(ParticleTypes.CRIMSON_SPORE, target.getRandomX(0.5D), target.getRandomY(), target.getRandomZ(0.5D), 0.0D, 0D, 0D);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final ChainsawRender renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) return new ChainsawRender();
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

}