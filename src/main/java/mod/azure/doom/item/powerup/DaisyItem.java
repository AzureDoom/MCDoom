package mod.azure.doom.item.powerup;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;

import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import mod.azure.doom.DoomMod;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class DaisyItem extends TrinketItem {

	public DaisyItem() {
		super(new Item.Properties().stacksTo(1));
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return false;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(Component.translatable("doom.daisy1.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
		list.add(Component.translatable("doom.daisy2.text").withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(itemStack, level, list, tooltipFlag);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
		final var modifiers = super.getModifiers(stack, slot, entity, uuid);
		if (DoomMod.config.enable_daisy_effects) {
			modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, DoomMod.MODID + ":movement_speed", 2.0, AttributeModifier.Operation.MULTIPLY_TOTAL));
			SlotAttributes.addSlotModifier(modifiers, "legs/belt", uuid, 1, AttributeModifier.Operation.ADDITION);
		}
		return modifiers;
	}
}