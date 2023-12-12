package mod.azure.doom.items.powerup;

import mod.azure.doom.MCDoom;
import mod.azure.doom.registry.NeoDoomItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class DaisyItem extends Item {

    public DaisyItem() {
        super(new Properties().stacksTo(1));
    }

    public static boolean isRingInCuriosSlot(ItemStack belt, LivingEntity player) {
        return CuriosApi.getCurio(belt).isPresent();
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("doom.daisy1.text").withStyle(ChatFormatting.YELLOW).withStyle(
                ChatFormatting.ITALIC));
        tooltip.add(Component.translatable("doom.daisy2.text").withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundTag unused) {
        final ICurio curio = new ICurio() {
            @Override
            public boolean canEquipFromUse(SlotContext slotContext) {
                return true;
            }

            @Override
            public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                if (slotContext.entity() instanceof Player player && MCDoom.config.enable_daisy_effects) {
                    startPowers(player);
                }
            }

            @Override
            public void onUnequip(SlotContext slotContext, ItemStack prevStack) {
                if (slotContext.entity() instanceof Player player && MCDoom.config.enable_daisy_effects) {
                    stopPowers(player);
                }
            }

            private void startPowers(Player player) {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10000000, 2));
            }

            private void stopPowers(Player player) {
                player.removeEffect(MobEffects.MOVEMENT_SPEED);
            }

            @Override
            public void curioTick(SlotContext slotContext) {
                if (slotContext.entity() instanceof Player player) {
                    startPowers(player);
                }
            }

            @Override
            public boolean canEquip(SlotContext slotContext) {
                return !CuriosApi.getCuriosHelper().findFirstCurio(slotContext.entity(),
                        NeoDoomItems.DAISY.get()).isPresent();
            }

            @Override
            public ItemStack getStack() {
                return new ItemStack(NeoDoomItems.DAISY.get());
            }
        };

        return new ICapabilityProvider() {
            private final LazyOptional<ICurio> curioOpt = LazyOptional.of(() -> curio);

            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {

                return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
            }
        };
    }
}