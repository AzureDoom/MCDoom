package mod.azure.doom.item.weapons;

import mod.azure.azurelib.Keybindings;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.render.weapons.SwordCrucibleRender;
import mod.azure.doom.entity.tierboss.*;
import mod.azure.doom.util.packets.DoomPacketHandler;
import mod.azure.doom.util.packets.weapons.CrucibleLoadingPacket;
import mod.azure.doom.util.registry.DoomBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SwordCrucibleItem extends SwordItem implements GeoItem {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public SwordCrucibleItem() {
		super(DoomMod.DOOM_HIGHTEIR, 1, -2.5f, new Item.Properties().stacksTo(1).durability(DoomMod.config.crucible_max_uses));
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity miner) {
		if (miner instanceof Player playerentity) {
			if (stack.getDamageValue() < stack.getMaxDamage() - 1) {
				if (playerentity.getMainHandItem().getItem() instanceof SwordCrucibleItem) {
					final AABB aabb = new AABB(miner.blockPosition().above()).inflate(4D, 1D, 4D);
					miner.getCommandSenderWorld().getEntities(miner, aabb).forEach(e -> doDamage(playerentity, e));
					stack.hurtAndBreak(1, miner, p -> p.broadcastBreakEvent(playerentity.getUsedItemHand()));
				}
			}
		}
		return stack.getDamageValue() < stack.getMaxDamage() - 1 ? true : false;
	}

	private void doDamage(LivingEntity user, Entity target) {
		if (target instanceof LivingEntity) {
			target.invulnerableTime = 0;
			target.hurt(user.damageSources().playerAttack((Player) user), target instanceof ArchMakyrEntity || target instanceof GladiatorEntity || target instanceof IconofsinEntity || target instanceof MotherDemonEntity || target instanceof SpiderMastermind2016Entity || target instanceof SpiderMastermindEntity ? DoomMod.config.crucible_damage / 10F : DoomMod.config.crucible_damage);
		}
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "shoot_controller", event -> PlayState.CONTINUE).triggerableAnim("open", RawAnimation.begin().thenPlay("opening").thenLoop("open")).triggerableAnim("close", RawAnimation.begin().thenPlayAndHold("closed")));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("doom.crucible_sword.text").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
		tooltip.add(Component.translatable("Ammo: " + (stack.getMaxDamage() - stack.getDamageValue() - 1) + " / " + (stack.getMaxDamage() - 1)).withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	public static void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof SwordCrucibleItem) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(DoomBlocks.ARGENT_BLOCK.get().asItem()) > 0) {
				removeAmmo(DoomBlocks.ARGENT_BLOCK.get().asItem(), user);
				user.getItemInHand(hand).hurtAndBreak(-5, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		final Player playerentity = (Player) entity;
		if (world.isClientSide)
			if (stack.getItem() instanceof SwordCrucibleItem)
				while (Keybindings.RELOAD.consumeClick() && selected)
					DoomPacketHandler.CRUCIBLE.sendToServer(new CrucibleLoadingPacket(slot));
		if (!world.isClientSide())
			if (playerentity.getMainHandItem().is(this) && selected)
				triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) world), "shoot_controller", "open");
			else
				triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) world), "shoot_controller", "close");
	}

	public static void removeAmmo(Item ammo, Player Player) {
		if (!Player.isCreative()) {
			for (final ItemStack item : Player.getInventory().offhand) {
				if (item.getItem() == ammo) {
					item.shrink(1);
					break;
				}
				for (final ItemStack item1 : Player.getInventory().items) {
					if (item1.getItem() == ammo) {
						item1.shrink(1);
						break;
					}
				}
			}
		}
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final SwordCrucibleRender renderer = new SwordCrucibleRender();

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return renderProvider;
	}
}