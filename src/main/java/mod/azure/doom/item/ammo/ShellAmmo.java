package mod.azure.doom.item.ammo;

import java.util.List;

import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import mod.azure.doom.util.config.DoomConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ShellAmmo extends Item {

	public final float damage;

	public ShellAmmo(Properties properties, float damageIn) {
		super(properties);
		this.damage = damageIn;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(new TranslatableComponent("doom.shell.text").withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	public ShotgunShellEntity createArrow(Level world, ItemStack stack, LivingEntity shooter, boolean marauder) {
		ShotgunShellEntity arrowentity = new ShotgunShellEntity(world, shooter,
				marauder ? DoomConfig.SERVER.marauder_ssgdamage.get().floatValue()
						: DoomConfig.SERVER.shotgun_damage.get().floatValue());
		return arrowentity;
	}
}