package mod.azure.doom.item.ammo;

import java.util.List;
import mod.azure.doom.entity.projectiles.BulletEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ClipAmmo extends Item {

  public final float damage;

  public ClipAmmo(Properties properties, float damageIn) {
    super(properties);
    this.damage = damageIn;
  }

  @Override
  public void appendHoverText(ItemStack stack, Level worldIn,
                              List<Component> tooltip, TooltipFlag flagIn) {
    tooltip.add(new TranslatableComponent("doom.bullet.text")
                    .withStyle(ChatFormatting.ITALIC));
    super.appendHoverText(stack, worldIn, tooltip, flagIn);
  }

  public BulletEntity createArrow(Level worldIn, ItemStack stack,
                                  LivingEntity shooter) {
    BulletEntity arrowentity = new BulletEntity(worldIn, shooter);
    arrowentity.setBaseDamage(this.damage);
    return arrowentity;
  }
}