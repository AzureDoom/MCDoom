package mod.azure.doom.item.weapons;

import java.util.List;
import java.util.function.Consumer;

import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.Keybindings;
import mod.azure.doom.client.render.weapons.SwordCrucibleRender;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
import mod.azure.doom.entity.tierboss.GladiatorEntity;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import mod.azure.doom.entity.tierboss.SpiderMastermind2016Entity;
import mod.azure.doom.entity.tierboss.SpiderMastermindEntity;
import mod.azure.doom.util.packets.DoomPacketHandler;
import mod.azure.doom.util.packets.weapons.CrucibleLoadingPacket;
import mod.azure.doom.util.registry.DoomBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class SwordCrucibleItem extends SwordItem implements GeoItem {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public SwordCrucibleItem() {
		super(DoomMod.DOOM_HIGHTEIR, 1, -2.5f,
				new Item.Properties().stacksTo(1).durability(DoomConfig.SERVER.crucible_max_uses.get()));
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity miner) {
		if (miner instanceof Player) {
			Player playerentity = (Player) miner;
			if (stack.getDamageValue() < (stack.getMaxDamage() - 1)) {
				if (playerentity.getMainHandItem().getItem() instanceof SwordCrucibleItem) {
					final AABB aabb = new AABB(miner.blockPosition().above()).inflate(4D, 1D, 4D);
					miner.getCommandSenderWorld().getEntities(miner, aabb).forEach(e -> doDamage(playerentity, e));
					stack.hurtAndBreak(1, miner, p -> p.broadcastBreakEvent(playerentity.getUsedItemHand()));
				}
			}
		}
		return stack.getDamageValue() < (stack.getMaxDamage() - 1) ? true : false;
	}

	private void doDamage(LivingEntity user, Entity target) {
		if (target instanceof LivingEntity) {
			target.invulnerableTime = 0;
			target.hurt(DamageSource.playerAttack((Player) user),
					(target instanceof ArchMakyrEntity) || (target instanceof GladiatorEntity)
							|| (target instanceof IconofsinEntity) || (target instanceof MotherDemonEntity)
							|| (target instanceof SpiderMastermind2016Entity)
							|| (target instanceof SpiderMastermindEntity)
									? (DoomConfig.SERVER.crucible_damage.get().floatValue() / 10F)
									: DoomConfig.SERVER.crucible_damage.get().floatValue());
		}
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "shoot_controller", event -> PlayState.CONTINUE)
				.triggerableAnim("open", RawAnimation.begin().thenPlay("opening").thenLoop("open"))
				.triggerableAnim("close", RawAnimation.begin().thenPlayAndHold("closed")));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("doom.crucible_sword.text").withStyle(ChatFormatting.RED)
				.withStyle(ChatFormatting.ITALIC));
		tooltip.add(Component.translatable(
				"Ammo: " + (stack.getMaxDamage() - stack.getDamageValue() - 1) + " / " + (stack.getMaxDamage() - 1))
				.withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	public static void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof SwordCrucibleItem) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0
					&& user.getInventory().countItem(DoomBlocks.ARGENT_BLOCK.get().asItem()) > 0) {
				removeAmmo(DoomBlocks.ARGENT_BLOCK.get().asItem(), user);
				user.getItemInHand(hand).hurtAndBreak(-5, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		Player playerentity = (Player) entity;
		if (world.isClientSide)
			if (stack.getItem() instanceof SwordCrucibleItem)
				while (Keybindings.RELOAD.consumeClick() && selected)
					DoomPacketHandler.CRUCIBLE.sendToServer(new CrucibleLoadingPacket(slot));
		if (!world.isClientSide())
			if (playerentity.getMainHandItem().is(this) && selected)
				triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) world), "shoot_controller",
						"open");
			else
				triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) world), "shoot_controller",
						"close");
	}

	public static void removeAmmo(Item ammo, Player Player) {
		if (!Player.isCreative()) {
			for (ItemStack item : Player.getInventory().offhand) {
				if (item.getItem() == ammo) {
					item.shrink(1);
					break;
				}
				for (ItemStack item1 : Player.getInventory().items) {
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
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			private final SwordCrucibleRender renderer = new SwordCrucibleRender();

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return this.renderer;
			}
		});
	}
}