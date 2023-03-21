package mod.azure.doom.item.armor;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.client.render.armors.NightmareRender;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class NightmareDoomArmor extends ArmorItem implements GeoItem {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public NightmareDoomArmor(ArmorMaterial materialIn, EquipmentSlot slot) {
		super(materialIn, slot, new Item.Properties().stacksTo(1));
	}

	// Create our armor model/renderer for Fabric and return it
	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private NightmareRender renderer;

			@Override
			public HumanoidModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<LivingEntity> original) {
				if (renderer == null)
					renderer = new NightmareRender();

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
		list.add(Component.translatable("doom.nightmarearmor.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(itemStack, level, list, tooltipFlag);
	}

}