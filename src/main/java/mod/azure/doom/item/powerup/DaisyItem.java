package mod.azure.doom.item.powerup;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;

import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import mod.azure.doom.DoomMod;
import mod.azure.doom.config.DoomConfig;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class DaisyItem extends TrinketItem {

	public DaisyItem() {
		super(new Item.Settings().maxCount(1));
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return false;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("doom.daisy1.text").formatted(Formatting.YELLOW).formatted(Formatting.ITALIC));
		tooltip.add(Text.translatable("doom.daisy2.text").formatted(Formatting.ITALIC));
		super.appendTooltip(stack, world, tooltip, context);
	}

	public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot,
			LivingEntity entity, UUID uuid) {
		var modifiers = super.getModifiers(stack, slot, entity, uuid);
		if (DoomConfig.enable_daisy_effects == true) {
			modifiers.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(uuid,
					DoomMod.MODID + ":movement_speed", 2.0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
			SlotAttributes.addSlotModifier(modifiers, "legs/belt", uuid, 1, EntityAttributeModifier.Operation.ADDITION);
		}
		return modifiers;
	}
}