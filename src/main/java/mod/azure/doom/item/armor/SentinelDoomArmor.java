package mod.azure.doom.item.armor;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import mod.azure.doom.client.render.armors.SentinelRender;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SentinelDoomArmor extends ArmorItem implements GeoItem {

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public SentinelDoomArmor(ArmorMaterial materialIn, EquipmentSlot slot) {
		super(materialIn, slot, new Item.Settings().maxCount(1));
	}

	// Create our armor model/renderer for Fabric and return it
	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private SentinelRender renderer;

			@Override
			public BipedEntityModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
					EquipmentSlot equipmentSlot, BipedEntityModel<LivingEntity> original) {
				if (this.renderer == null)
					this.renderer = new SentinelRender();

				this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
				return this.renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return this.renderProvider;
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, 20, state -> {
			return state.setAndContinue(RawAnimation.begin().thenLoop("idle"));
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("doom.sentinelarmor.text").formatted(Formatting.YELLOW)
				.formatted(Formatting.ITALIC));
		super.appendTooltip(stack, world, tooltip, context);
	}

}