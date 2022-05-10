package mod.azure.doom.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@Inject(method = "tryUseTotem", at = @At(value = "HEAD"), cancellable = true)
	private void tryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> ci) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		if (source.isOutOfWorld()) {
			ci.setReturnValue(false);
		} else {
			ItemStack stack = TrinketsApi.getTrinketComponent(livingEntity).map(component -> {
				List<Pair<SlotReference, ItemStack>> res = component.getEquipped(DoomItems.SOULCUBE);
				return res.size() > 0 ? res.get(0).getRight() : ItemStack.EMPTY;
			}).orElse(ItemStack.EMPTY);

			if (!stack.isEmpty()) {
				stack.damage(1, livingEntity, p -> p.sendToolBreakStatus(livingEntity.getActiveHand()));
				livingEntity.setHealth(20.0F);
				livingEntity.clearStatusEffects();
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, 4));
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 100, 4));
				livingEntity.world.sendEntityStatus(livingEntity, (byte) 95);
				ci.setReturnValue(true);
			}
		}
	}

}