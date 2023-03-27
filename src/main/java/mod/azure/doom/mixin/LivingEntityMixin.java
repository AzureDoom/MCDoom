package mod.azure.doom.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.util.Tuple;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@Inject(method = "checkTotemDeathProtection", at = @At(value = "HEAD"), cancellable = true)
	private void tryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> ci) {
		final var livingEntity = (LivingEntity) (Object) this;
		if (DoomConfig.enable_soulcube_effects) {
			final var stack = TrinketsApi.getTrinketComponent(livingEntity).map(component -> {
				final List<Tuple<SlotReference, ItemStack>> res = component.getEquipped(DoomItems.SOULCUBE);
				return res.size() > 0 ? res.get(0).getB() : ItemStack.EMPTY;
			}).orElse(ItemStack.EMPTY);

			if (!stack.isEmpty()) {
				stack.hurtAndBreak(1, livingEntity, p -> p.broadcastBreakEvent(livingEntity.getUsedItemHand()));
				livingEntity.setHealth(20.0F);
				livingEntity.removeAllEffects();
				livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 4));
				livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 4));
				livingEntity.level.broadcastEntityEvent(livingEntity, (byte) 95);
				ci.setReturnValue(true);
			}
		}
	}

}