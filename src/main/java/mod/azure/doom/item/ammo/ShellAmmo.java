package mod.azure.doom.item.ammo;

import java.util.List;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

public class ShellAmmo extends ArrowItem {

	public final float damage;

	public ShellAmmo(float damageIn) {
		super(new Item.Settings().group(DoomMod.DoomWeaponItemGroup));
		this.damage = damageIn;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText("\u00A7o" + "Loads shotgun with malicious intent."));
		super.appendTooltip(stack, world, tooltip, context);
	}

	public ShotgunShellEntity createArrow(World world, ItemStack stack, LivingEntity shooter, boolean marauder) {
		ShotgunShellEntity arrowentity = new ShotgunShellEntity(world, shooter,
				marauder ? DoomMod.config.stats.marauder_ssgdamage : DoomMod.config.weapons.shotgun_damage);
		arrowentity.initFromStack(stack);
		return arrowentity;
	}

}