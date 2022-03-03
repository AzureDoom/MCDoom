package mod.azure.doom.item.powerup;

import java.util.List;

import mod.azure.doom.DoomMod;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class MegaSphereItem extends Item {

	public MegaSphereItem() {
		super(new Item.Settings().group(DoomMod.DoomPowerUPItemGroup).maxCount(1));
	}

	@Override
	public void usageTick(World worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
		if (livingEntityIn instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) livingEntityIn;
			if (!worldIn.isClient) {
				livingEntityIn.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 600, 4));
				livingEntityIn.heal(40);
				livingEntityIn.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 600, 4));
				livingEntityIn.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 600, 4));
				if (!playerentity.getAbilities().creativeMode) {
					stack.decrement(1);
					if (stack.isEmpty()) {
						playerentity.getInventory().removeOne(stack);
					}
				}
			}
		}
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		user.setCurrentHand(hand);
		return TypedActionResult.consume(itemStack);
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 7000;
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		user.getStackInHand(hand);
		return ActionResult.CONSUME;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText("doom.mega.text").formatted(Formatting.ITALIC));
		super.appendTooltip(stack, world, tooltip, context);
	}

}