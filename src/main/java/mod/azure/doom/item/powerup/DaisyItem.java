package mod.azure.doom.item.powerup;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;

import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import mod.azure.doom.DoomMod;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
//import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class DaisyItem extends TrinketItem {

	public DaisyItem() {
		super(new Item.Settings().group(DoomMod.DoomPowerUPItemGroup).maxCount(1));
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return false;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText("doom.daisy1.text").formatted(Formatting.YELLOW).formatted(Formatting.ITALIC));
		tooltip.add(new TranslatableText("doom.daisy2.text").formatted(Formatting.ITALIC));
		super.appendTooltip(stack, world, tooltip, context);
	}

	public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot,
			LivingEntity entity, UUID uuid) {
		var modifiers = super.getModifiers(stack, slot, entity, uuid);
		modifiers.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(uuid,
				DoomMod.MODID + ":movement_speed", 2.0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
		// If the player has access to ring slots, this will give them an extra one
		SlotAttributes.addSlotModifier(modifiers, "legs/belt", uuid, 1, EntityAttributeModifier.Operation.ADDITION);
		return modifiers;
	}
}