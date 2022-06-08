package mod.azure.doom.util;

public class SoulCubeHandler {

//	ClientLevel world;
//
//	@SubscribeEvent
//	public void attachCapabilities(AttachCapabilitiesEvent<ItemStack> evt) {
//		if (evt.getObject().getItem() != DoomItems.SOULCUBE.get()) {
//			return;
//		}
//		ICurio curio = new ICurio() {
//
//			@Override
//			public boolean canRightClickEquip() {
//				return true;
//			}
//
//			@Override
//			public ItemStack getStack() {
//				return new ItemStack(DoomItems.SOULCUBE.get());
//			}
//		};
//
//		ICapabilityProvider provider = new ICapabilityProvider() {
//			private final LazyOptional<ICurio> curioOpt = LazyOptional.of(() -> curio);
//
//			@Nonnull
//			@Override
//			public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//				return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
//			}
//		};
//		evt.addCapability(CuriosCapability.ID_ITEM, provider);
//	}
//
//	@SubscribeEvent(priority = EventPriority.HIGHEST)
//	public void onLivingDeath(LivingDeathEvent evt) {
//		if (soulCube(evt.getEntityLiving(), evt.getSource())) {
//			evt.setCanceled(true);
//		}
//	}
//
//	private boolean soulCube(LivingEntity livingEntity, DamageSource source) {
//		if (source.isBypassInvul()) {
//			return false;
//		}
//		for (ItemStack held : livingEntity.getHandSlots()) {
//			if (held.getItem() == DoomItems.SOULCUBE.get()) {
//				return false;
//			}
//		}
//		return CuriosApi.getCuriosHelper().findFirstCurio(livingEntity, DoomItems.SOULCUBE.get()).map(soulcube -> {
//			this.activateSoulCube(livingEntity, soulcube.stack());
//			return true;
//		}).orElse(false);
//	}
//
//	private void activateSoulCube(LivingEntity livingEntity, ItemStack soulcube) {
//		ItemStack copy = soulcube.copy();
//		soulcube.hurtAndBreak(1, livingEntity, (entity) -> {
//			entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
//		});
//
//		if (livingEntity instanceof ServerPlayer) {
//			ServerPlayer serverPlayer = (ServerPlayer) livingEntity;
//			CriteriaTriggers.USED_TOTEM.trigger(serverPlayer, copy);
//		}
//		if (livingEntity instanceof Player) {
//			livingEntity.setHealth(20.0F);
//			livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 4));
//			livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 4));
//			livingEntity.level.broadcastEntityEvent(livingEntity, (byte) 90);
//		}
//	}
}