package mod.azure.doom.helper;

import mod.azure.doom.registry.NeoDoomItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SoulCubeHandler {

    ClientLevel world;

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<ItemStack> evt) {
        if (evt.getObject().getItem() != NeoDoomItems.SOULCUBE.get()) {
            return;
        }
        final ICurio curio = new ICurio() {

            @Override
            public boolean canEquipFromUse(SlotContext slotContext) {
                return true;
            }

            @Override
            public ItemStack getStack() {
                return new ItemStack(NeoDoomItems.SOULCUBE.get());
            }
        };

        final ICapabilityProvider provider = new ICapabilityProvider() {
            private final LazyOptional<ICurio> curioOpt = LazyOptional.of(() -> curio);

            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
            }
        };
        evt.addCapability(CuriosCapability.ID_ITEM, provider);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLivingDeath(LivingDeathEvent evt) {
        if (soulCube(evt.getEntity(), evt.getSource())) {
            evt.setCanceled(true);
        }
    }

    private boolean soulCube(LivingEntity livingEntity, DamageSource source) {
        for (final ItemStack held : livingEntity.getHandSlots()) {
            if (held.getItem() == NeoDoomItems.SOULCUBE.get()) {
                return false;
            }
        }
        return CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, NeoDoomItems.SOULCUBE.get()).map(soulcube -> {
            activateSoulCube(livingEntity, soulcube.stack());
            return true;
        }).orElse(false);
    }

    private void activateSoulCube(LivingEntity livingEntity, ItemStack soulcube) {
        final ItemStack copy = soulcube.copy();
        soulcube.hurtAndBreak(1, livingEntity, entity -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));

        if (livingEntity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.USED_TOTEM.trigger(serverPlayer, copy);
        }
        if (livingEntity instanceof Player) {
            livingEntity.setHealth(20.0F);
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 4));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 4));
            livingEntity.level().broadcastEntityEvent(livingEntity, (byte) 90);
        }
    }
}