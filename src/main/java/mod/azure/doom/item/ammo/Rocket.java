package mod.azure.doom.item.ammo;

import java.util.List;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.RocketEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class Rocket extends ArrowItem {

	public final float damage;

	public Rocket(float damageIn) {
		super(new Item.Settings().group(DoomMod.DoomWeaponItemGroup));
		this.damage = damageIn;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText("doom.rocket.text").formatted(Formatting.ITALIC));
		super.appendTooltip(stack, world, tooltip, context);
	}

	@Override
	public RocketEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		RocketEntity arrowentity = new RocketEntity(worldIn, shooter);
		arrowentity.setDamage(this.damage);
		return arrowentity;
	}

}