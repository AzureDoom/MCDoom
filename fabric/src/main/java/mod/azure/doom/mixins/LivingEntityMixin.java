package mod.azure.doom.mixins;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import mod.azure.doom.MCDoom;
import mod.azure.doom.registry.FabricDoomItems;
import net.minecraft.util.Tuple;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "checkTotemDeathProtection", at = @At(value = "HEAD"), cancellable = true)
    private void tryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> ci) {
        final var livingEntity = (LivingEntity) (Object) this;
        if (MCDoom.config.enable_soulcube_effects && livingEntity instanceof Player player) {
            final var stack = TrinketsApi.getTrinketComponent(player).map(component -> {
                final List<Tuple<SlotReference, ItemStack>> res = component.getEquipped(FabricDoomItems.SOULCUBE);
                return res.isEmpty() ? res.get(0).getB() : ItemStack.EMPTY;
            }).orElse(ItemStack.EMPTY);

            if (!stack.isEmpty()) {
                stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
                player.setHealth(20.0F);
                player.removeAllEffects();
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 4));
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 4));
                player.level().broadcastEntityEvent(player, (byte) 95);
                ci.setReturnValue(true);
            }
        }
    }

}