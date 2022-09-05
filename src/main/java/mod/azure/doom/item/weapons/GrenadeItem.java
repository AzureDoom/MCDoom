package mod.azure.doom.item.weapons;

import java.util.List;
import java.util.function.Consumer;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.render.projectiles.GrenadeItemRender;
import mod.azure.doom.entity.projectiles.GrenadeEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GrenadeItem extends Item implements IAnimatable {

	public AnimationFactory factory = new AnimationFactory(this);
	public String controllerName = "controller";

	public GrenadeItem() {
		super(new Item.Properties().tab(DoomMod.DoomWeaponItemGroup));
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		super.initializeClient(consumer);
		consumer.accept(new IClientItemExtensions() {
			private final BlockEntityWithoutLevelRenderer renderer = new GrenadeItemRender();

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return renderer;
			}
		});
	}

	public <P extends BlockItem & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController(this, controllerName, 1, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		ItemStack itemstack = user.getItemInHand(hand);
		if (!user.getCooldowns().isOnCooldown(this)) {
			user.getCooldowns().addCooldown(this, 25);
			if (!world.isClientSide) {
				GrenadeEntity snowball = new GrenadeEntity(world, user);
				snowball.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.05F, 1.0F);
				snowball.setBaseDamage(0);
				world.addFreshEntity(snowball);
			}
			if (!user.getAbilities().instabuild) {
				itemstack.shrink(1);
			}
			return InteractionResultHolder.sidedSuccess(itemstack, world.isClientSide());
		} else {
			return InteractionResultHolder.fail(itemstack);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("doom.doomed_credit.text").withStyle(ChatFormatting.RED)
				.withStyle(ChatFormatting.ITALIC));
		tooltip.add(Component.translatable("doom.doomed_credit1.text").withStyle(ChatFormatting.RED)
				.withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

}
