package mod.azure.doom.items.ammo;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class BFGCell extends Item {

    public BFGCell() {
        super(new Properties());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("doom.bfgcell.text").withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(itemStack, level, list, tooltipFlag);
    }

}