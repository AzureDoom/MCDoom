package mod.azure.doom.item.weapons;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import mod.azure.doom.client.render.item.GrenadeItemRender;
import mod.azure.doom.entity.projectiles.GrenadeEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GrenadeItem extends Item implements GeoItem {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public GrenadeItem() {
		super(new Item.Settings());
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "popup_controller", 0, state -> PlayState.CONTINUE));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		if (!user.getItemCooldownManager().isCoolingDown(this)) {
			user.getItemCooldownManager().set(this, 25);
			if (!world.isClient) {
				GrenadeEntity snowballEntity = new GrenadeEntity(world, user);
				snowballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.05F, 1.0F);
				snowballEntity.setDamage(0);
				world.spawnEntity(snowballEntity);
			}
			if (!user.getAbilities().creativeMode) {
				itemStack.decrement(1);
			}
			return TypedActionResult.success(itemStack, world.isClient());
		} else {
			return TypedActionResult.fail(itemStack);
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		tooltip.add(
				Text.translatable("doom.doomed_credit.text").formatted(Formatting.RED).formatted(Formatting.ITALIC));
		tooltip.add(
				Text.translatable("doom.doomed_credit1.text").formatted(Formatting.RED).formatted(Formatting.ITALIC));
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final GrenadeItemRender renderer = new GrenadeItemRender();

			@Override
			public BuiltinModelItemRenderer getCustomRenderer() {
				return this.renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return this.renderProvider;
	}

}
