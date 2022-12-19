package mod.azure.doom.item.weapons;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import io.netty.buffer.Unpooled;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.ClientInit;
import mod.azure.doom.client.render.weapons.SwordCrucibleRender;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
import mod.azure.doom.entity.tierboss.GladiatorEntity;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import mod.azure.doom.entity.tierboss.SpiderMastermind2016Entity;
import mod.azure.doom.entity.tierboss.SpiderMastermindEntity;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.registry.DoomBlocks;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SwordCrucibleItem extends SwordItem implements GeoItem {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public SwordCrucibleItem() {
		super(DoomTier.DOOM_HIGHTEIR, 1, -2.5f, new Item.Settings().maxCount(1).maxDamage(24));
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity miner) {
		if (miner instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) miner;
			if (stack.getDamage() < (stack.getMaxDamage() - 1)) {
				if (playerentity.getMainHandStack().getItem() instanceof SwordCrucibleItem) {
					final Box aabb = new Box(playerentity.getBlockPos().up()).expand(4D, 1D, 4D);
					playerentity.getEntityWorld().getOtherEntities(playerentity, aabb)
							.forEach(e -> doDamage(playerentity, e));
					stack.damage(1, playerentity, p -> p.sendToolBreakStatus(playerentity.getActiveHand()));
				}
			}
		}
		return stack.getDamage() < (stack.getMaxDamage() - 1) ? true : false;
	}

	private void doDamage(LivingEntity user, Entity target) {
		if (target instanceof LivingEntity) {
			target.timeUntilRegen = 0;
			target.damage(DamageSource.player((PlayerEntity) user),
					!(target instanceof ArchMakyrEntity) || !(target instanceof GladiatorEntity)
							|| !(target instanceof IconofsinEntity) || !(target instanceof MotherDemonEntity)
							|| !(target instanceof SpiderMastermind2016Entity)
							|| !(target instanceof SpiderMastermindEntity) ? 30F : 200F);
		}
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "shoot_controller", event -> {
//			if (MinecraftClient.getInstance().player.getMainHandStack() == event.getData(DataTickets.ITEMSTACK))
//				return event.setAndContinue(RawAnimation.begin().thenPlay("opening").thenLoop("open"));
//			return event.setAndContinue(RawAnimation.begin().thenPlayAndHold("closed"));
			return PlayState.STOP;
		}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(
				Text.translatable("doom.crucible_sword.text").formatted(Formatting.RED).formatted(Formatting.ITALIC));
		tooltip.add(Text
				.translatable(
						"Ammo: " + (stack.getMaxDamage() - stack.getDamage() - 1) + " / " + (stack.getMaxDamage() - 1))
				.formatted(Formatting.ITALIC));
		super.appendTooltip(stack, world, tooltip, context);
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof SwordCrucibleItem) {
			while (!user.isCreative() && user.getStackInHand(hand).getDamage() != 0
					&& user.getInventory().count(DoomBlocks.ARGENT_BLOCK.asItem()) > 0) {
				removeAmmo(DoomBlocks.ARGENT_BLOCK.asItem(), user);
				user.getStackInHand(hand).damage(-5, user, s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setBobbingAnimationTime(3);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		PlayerEntity playerentity = (PlayerEntity) entity;
		if (world.isClient) {
			if (playerentity.getMainHandStack().getItem() instanceof SwordCrucibleItem && ClientInit.reload.isPressed()
					&& selected) {
				PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
				passedData.writeBoolean(true);
				ClientPlayNetworking.send(DoomMod.CRUCIBLE, passedData);
			}
		}
	}

	public void removeAmmo(Item ammo, PlayerEntity playerEntity) {
		if (!playerEntity.isCreative()) {
			for (ItemStack item : playerEntity.getInventory().offHand) {
				if (item.getItem() == ammo) {
					item.decrement(1);
					break;
				}
				for (ItemStack item1 : playerEntity.getInventory().main) {
					if (item1.getItem() == ammo) {
						item1.decrement(1);
						break;
					}
				}
			}
		}
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return false;
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 72000;
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final SwordCrucibleRender renderer = new SwordCrucibleRender();

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