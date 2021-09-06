package mod.azure.doom.item.ammo;

import java.util.List;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.ChaingunBulletEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class ChaingunAmmo extends ArrowItem {

	public final float damage;

	public ChaingunAmmo(float damageIn) {
		super(new Item.Settings().group(DoomMod.DoomWeaponItemGroup));
		this.damage = damageIn;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText("\u00A7o" + "Used for the Chaingun."));
		super.appendTooltip(stack, world, tooltip, context);
	}

	@Override
	public ChaingunBulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		ChaingunBulletEntity arrowentity = new ChaingunBulletEntity(worldIn, shooter);
		arrowentity.setDamage(this.damage);
		return arrowentity;
	}

}