package mod.azure.doom.items.argent;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ArgentEnergyItem extends Item {

    public ArgentEnergyItem() {
        super(new Properties());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("doom.argent_engery1.text").withStyle(ChatFormatting.RED).withStyle(
                ChatFormatting.ITALIC));
        list.add(Component.translatable("doom.argent_engery2.text").withStyle(ChatFormatting.RED).withStyle(
                ChatFormatting.ITALIC));
        list.add(Component.translatable("doom.argent_engery3.text").withStyle(ChatFormatting.RED).withStyle(
                ChatFormatting.ITALIC));
        super.appendHoverText(itemStack, level, list, tooltipFlag);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public boolean isFireResistant() {
        return true;
    }
}