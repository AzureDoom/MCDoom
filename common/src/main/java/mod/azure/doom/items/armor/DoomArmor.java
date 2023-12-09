package mod.azure.doom.items.armor;

import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.client.render.armors.DoomRender;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class DoomArmor extends ArmorItem implements GeoItem {

    protected final ArmorTypeEnum armorTypeEnum;
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    protected DoomArmor(ArmorMaterial materialIn, Type slot, ArmorTypeEnum armorTypeEnum) {
        super(materialIn, slot, new Properties().stacksTo(1));
        this.armorTypeEnum = armorTypeEnum;
    }

    public ArmorTypeEnum getArmorTypeEnum() {
        return this.armorTypeEnum;
    }

    // Create our armor model/renderer for Fabric and return it
    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private DoomRender renderer;

            @Override
            public HumanoidModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<LivingEntity> original) {
                if (renderer == null) renderer = new DoomRender(getArmorTypeEnum());

                renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, 20, state -> state.setAndContinue(RawAnimation.begin().thenLoop("idle"))));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
        switch (this.getArmorTypeEnum()) {
            case ASTRO ->
                    list.add(Component.translatable("doom.astroarmor.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

            case CLASSIC_GREEN, CLASSIC_INDIGO, CLASSIC_BRONZE, CLASSIC_RED ->
                    list.add(Component.translatable("doom.classicarmor.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

            case CRIMSON ->
                    list.add(Component.translatable("doom.crimsonarmor.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

            case CULTIST ->
                    list.add(Component.translatable("doom.cultistarmor.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

            case DARK_LORD ->
                    list.add(Component.translatable("doom.darklordarmor.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

            case DEMONIC, DEMONCIDE ->
                    list.add(Component.translatable("doom.demonicarmor.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

            case BRONZE, DOOM, HOTROD, TWENTYFIVE ->
                    list.add(Component.translatable("doom.doomarmor.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

            case DOOMICORN, NIGHTMARE, PURPLE_PONY ->
                    list.add(Component.translatable("doom.doomicornarmor.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

            case EMBER ->
                    list.add(Component.translatable("doom.emberarmor.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

            case GOLD ->
                    list.add(Component.translatable("doom.goldarmor.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

            case MAYKR ->
                    list.add(Component.translatable("doom.makyrarmor.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

            case MIDNIGHT ->
                    list.add(Component.translatable("doom.midnightarmor.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

            case MULLET1, MULLET2, MULLET3 ->
                    list.add(Component.translatable("doom.mulletarmor.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

            case PAINTER ->
                    list.add(Component.translatable("doom.painterarmor.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

            case PHOBOS ->
                    list.add(Component.translatable("doom.phobosarmor.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

            case PRAETOR ->
                    list.add(Component.translatable("doom.praetorarmor.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

            case SANTA ->
                    list.add(Component.translatable("doom.santadoomarmor.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

            case SENTINEL ->
                    list.add(Component.translatable("doom.sentinelarmor.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

            case ZOMBIE ->
                    list.add(Component.translatable("doom.zombiearmor.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

        }
        super.appendHoverText(itemStack, level, list, tooltipFlag);
    }

}