package mod.azure.doom.item.powerup;

import java.util.List;

import mod.azure.doom.DoomMod;
import mod.azure.doom.util.PMMOCompat;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;

public class MegaSphereItem extends Item {

	public MegaSphereItem() {
		super(new Item.Properties().tab(DoomMod.DoomPowerUPItemGroup).stacksTo(1));
	}

	@Override
	public void onUseTick(World worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
		if (livingEntityIn instanceof ServerPlayerEntity) {
			ServerPlayerEntity playerentity = (ServerPlayerEntity) livingEntityIn;
			if (!worldIn.isClientSide)
				livingEntityIn.addEffect(new EffectInstance(Effects.HEALTH_BOOST, 600, 4));
			livingEntityIn.heal(40);
			livingEntityIn.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 600, 4));
			livingEntityIn.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 600, 4));
			if (ModList.get().isLoaded("pmmo")) {
				PMMOCompat.awardMegaXp(playerentity);
			}
			if (!playerentity.abilities.instabuild) {
				stack.shrink(1);
				if (stack.isEmpty()) {
					playerentity.inventory.removeItem(stack);
				}
			}
		}
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 7000;
	}

	@Override
	public UseAction getUseAnimation(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		playerIn.startUsingItem(handIn);
		return ActionResult.consume(itemstack);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("doom.mega.text").withStyle(TextFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return false;
	}

}