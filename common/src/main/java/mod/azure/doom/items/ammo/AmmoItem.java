package mod.azure.doom.items.ammo;

import mod.azure.doom.items.enums.AmmoEnum;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class AmmoItem extends Item {
    private AmmoEnum ammoEnum;

    public AmmoItem(AmmoEnum ammoEnum) {
        super(new Properties());
        this.ammoEnum = ammoEnum;
    }

    public AmmoEnum getAmmoEnum() {
        return ammoEnum;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
        switch (getAmmoEnum()) {
            case ARGENT_BOLT ->
                    list.add(Component.translatable("doom.argentbolt.text").withStyle(ChatFormatting.ITALIC));
            case BFG_CELL -> list.add(Component.translatable("doom.bfgcell.text").withStyle(ChatFormatting.ITALIC));
            case CHAINGUN -> list.add(Component.translatable("doom.chaingun.text").withStyle(ChatFormatting.ITALIC));
            case CLIP -> list.add(Component.translatable("doom.bullet.text").withStyle(ChatFormatting.ITALIC));
            case ENGERY -> list.add(Component.translatable("doom.energycell.text").withStyle(ChatFormatting.ITALIC));
            case ROCKET -> list.add(Component.translatable("doom.rocket.text").withStyle(ChatFormatting.ITALIC));
            case SHELL -> list.add(Component.translatable("doom.shell.text").withStyle(ChatFormatting.ITALIC));
            case UNMAYKR_BOLT -> list.add(Component.translatable("doom.unmaykr.text").withStyle(ChatFormatting.ITALIC));
        }
        super.appendHoverText(itemStack, level, list, tooltipFlag);
    }
}
