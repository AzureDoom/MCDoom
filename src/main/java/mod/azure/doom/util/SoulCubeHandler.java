package mod.azure.doom.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class SoulCubeHandler {

	ClientWorld world;

	@SubscribeEvent
	public void attachCapabilities(AttachCapabilitiesEvent<ItemStack> evt) {
		if (evt.getObject().getItem() != DoomItems.SOULCUBE.get()) {
			return;
		}
		ICurio curio = new ICurio() {

			@Override
			public boolean canRightClickEquip() {
				return true;
			}
		};

		ICapabilityProvider provider = new ICapabilityProvider() {
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
		if (soulCube(evt.getEntityLiving(), evt.getSource())) {
			evt.setCanceled(true);
		}
	}

	private boolean soulCube(LivingEntity livingEntity, DamageSource source) {
		if (source.isBypassInvul()) {
			return false;
		}
		for (ItemStack held : livingEntity.getHandSlots()) {
			if (held.getItem() == DoomItems.SOULCUBE.get()) {
				return false;
			}
		}
		return CuriosApi.getCuriosHelper().findEquippedCurio(DoomItems.SOULCUBE.get(), livingEntity).map(soulcube -> {
			this.activateSoulCube(livingEntity, soulcube.getRight());
			return true;
		}).orElse(false);
	}

	private void activateSoulCube(LivingEntity livingEntity, ItemStack soulcube) {
		ItemStack copy = soulcube.copy();
		soulcube.hurtAndBreak(1, livingEntity, (entity) -> {
			entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
		});

		if (livingEntity instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverPlayer = (ServerPlayerEntity) livingEntity;
			CriteriaTriggers.USED_TOTEM.trigger(serverPlayer, copy);
		}
		if (livingEntity instanceof PlayerEntity) {
			livingEntity.setHealth(20.0F);
			livingEntity.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 100, 4));
			livingEntity.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 100, 4));
			livingEntity.level.broadcastEntityEvent(livingEntity, (byte) 90);
		}
		
		if (soulcube.isEmpty()) {
			ServerPlayerEntity playerentity = (ServerPlayerEntity) livingEntity;
			if (ModList.get().isLoaded("pmmo")) {
				PMMOCompat.awardSoulXp(playerentity);
			}
		}
	}
}