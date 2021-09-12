package mod.azure.doom.item.ammo;

import java.util.List;

import mod.azure.doom.entity.projectiles.BFGEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.world.item.Item.Properties;

public class BFGCell extends ArrowItem {

	public final float damage;

	public BFGCell(Properties properties, float damageIn) {
		super(properties);
		this.damage = damageIn;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new StringTextComponent("\u00A7o" + "Used for the BFG."));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.entity.player.PlayerEntity player) {
		int enchant = net.minecraft.enchantment.EnchantmentHelper
				.getItemEnchantmentLevel(net.minecraft.enchantment.Enchantments.INFINITY_ARROWS, bow);
		return enchant <= 0 ? false : this instanceof BFGCell;
	}

	@Override
	public BFGEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		BFGEntity arrowentity = new BFGEntity(worldIn, shooter);
		arrowentity.setBaseDamage(this.damage);
		return arrowentity;
	}

}