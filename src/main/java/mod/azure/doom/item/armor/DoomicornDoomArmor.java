package mod.azure.doom.item.armor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.azure.doom.DoomMod;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;

public class DoomicornDoomArmor extends GeoArmorItem implements IAnimatable {

	private AnimationFactory factory = new AnimationFactory(this);

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(
				new AnimationController<DoomicornDoomArmor>(this, "controller", 20, this::predicate));
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		LivingEntity livingEntity = event.getExtraDataOfType(LivingEntity.class).get(0);
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		if (livingEntity instanceof ArmorStand) {
			return PlayState.CONTINUE;
		} else if (livingEntity instanceof LocalPlayer) {
			LocalPlayer client = (LocalPlayer) livingEntity;
			List<Item> equipmentList = new ArrayList<>();
			client.getAllSlots().forEach((x) -> equipmentList.add(x.getItem()));
			List<Item> armorList = equipmentList.subList(2, 6);
			boolean isWearingAll = armorList.containsAll(
					Arrays.asList(DoomItems.DOOMICORN_DOOM_BOOTS.get(), DoomItems.DOOMICORN_DOOM_LEGGINGS.get(),
							DoomItems.DOOMICORN_DOOM_CHESTPLATE.get(), DoomItems.DOOMICORN_DOOM_HELMET.get()));
			return isWearingAll ? PlayState.CONTINUE : PlayState.STOP;
		}
		return PlayState.STOP;
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public DoomicornDoomArmor(ArmorMaterial materialIn, EquipmentSlot slot) {
		super(materialIn, slot, new Item.Properties().tab(DoomMod.DoomArmorItemGroup).stacksTo(1));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(new TranslatableComponent("doom.doomicornarmor.text").withStyle(ChatFormatting.YELLOW)
				.withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return false;
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		ItemStack stack = new ItemStack(this);
		stack.hasTag();
		stack.enchant(Enchantments.FIRE_PROTECTION, 1);
		if ((group == DoomMod.DoomArmorItemGroup) || (group == CreativeModeTab.TAB_SEARCH)) {
			items.add(stack);
		}
	}

	@Override
	public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
		stack.hasTag();
		stack.enchant(Enchantments.FIRE_PROTECTION, 1);
	}

}