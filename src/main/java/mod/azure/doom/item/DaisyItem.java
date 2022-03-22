package mod.azure.doom.item;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mod.azure.doom.DoomMod;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
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
import top.theillusivec4.curios.api.type.capability.ICurio;

public class DaisyItem extends Item {

	public DaisyItem() {
		super(new Item.Properties().tab(DoomMod.DoomPowerUPItemGroup).stacksTo(1));
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(new TranslatableComponent("doom.daisy1.text").withStyle(ChatFormatting.YELLOW)
				.withStyle(ChatFormatting.ITALIC));
		tooltip.add(new TranslatableComponent("doom.daisy2.text").withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundTag unused) {
		ICurio curio = new ICurio() {
			@Override
			public boolean canRightClickEquip() {
				return true;
			}

			@Override
			public void onEquip(String identifier, int index, LivingEntity livingEntity) {
				if (livingEntity instanceof Player) {
					startPowers((Player) livingEntity);
				}
			}

			@Override
			public void onUnequip(String identifier, int index, LivingEntity livingEntity) {
				if (livingEntity instanceof Player) {
					stopPowers((Player) livingEntity);
				}
			}

			private void startPowers(Player player) {
				player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10000000, 2));
			}

			private void stopPowers(Player player) {
				player.removeEffect(MobEffects.MOVEMENT_SPEED);
			}

			@Override
			public void curioTick(String identifier, int index, LivingEntity livingEntity) {
				if (livingEntity instanceof Player) {
					Player player = ((Player) livingEntity);
					startPowers(player);
				}
			}

			@Override
			public boolean canEquip(String identifier, LivingEntity entityLivingBase) {
				return !CuriosApi.getCuriosHelper().findFirstCurio(entityLivingBase, DoomItems.DAISY.get()).isPresent();
			}

			@Override
			public ItemStack getStack() {
				return new ItemStack(DoomItems.DAISY.get());
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

	public static boolean isRingInCuriosSlot(ItemStack belt, LivingEntity player) {
		return CuriosApi.getCuriosHelper().findFirstCurio(player, belt.getItem()).isPresent();
	}
}