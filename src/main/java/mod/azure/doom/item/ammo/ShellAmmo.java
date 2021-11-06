package mod.azure.doom.item.ammo;

import java.util.List;

import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import mod.azure.doom.util.config.DoomConfig;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ShellAmmo extends ArrowItem {

	public final float damage;

	public ShellAmmo(Properties properties, float damageIn) {
		super(properties);
		this.damage = damageIn;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("doom.shell.text").withStyle(TextFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.entity.player.PlayerEntity player) {
		int enchant = net.minecraft.enchantment.EnchantmentHelper
				.getItemEnchantmentLevel(net.minecraft.enchantment.Enchantments.INFINITY_ARROWS, bow);
		return enchant <= 0 ? false : this instanceof ShellAmmo;
	}

	public ShotgunShellEntity createArrow(World world, ItemStack stack, LivingEntity shooter, boolean marauder) {
		ShotgunShellEntity arrowentity = new ShotgunShellEntity(world, shooter,
				marauder ? DoomConfig.SERVER.marauder_ssgdamage.get().floatValue()
						: DoomConfig.SERVER.shotgun_damage.get().floatValue());
		arrowentity.setBaseDamage(this.damage);
		return arrowentity;
	}

}