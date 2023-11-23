package mod.azure.doom.items.ammo;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class Rocket extends Item {

    public Rocket() {
        super(new Properties());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("doom.rocket.text").withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(itemStack, level, list, tooltipFlag);
    }

}