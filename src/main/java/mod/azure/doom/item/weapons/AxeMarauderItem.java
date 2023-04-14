package mod.azure.doom.item.weapons;

import java.util.List;

import io.netty.buffer.Unpooled;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.ClientInit;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
import mod.azure.doom.entity.tierboss.GladiatorEntity;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import mod.azure.doom.entity.tierboss.SpiderMastermind2016Entity;
import mod.azure.doom.entity.tierboss.SpiderMastermindEntity;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.registry.DoomBlocks;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class AxeMarauderItem extends SwordItem {

	public AxeMarauderItem() {
		super(DoomTier.DOOM_HIGHTEIR, -5, -2.5f, new Item.Properties().stacksTo(1).durability(DoomConfig.marauder_max_uses).tab(DoomMod.DoomWeaponItemGroup));
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("doom.marauder_axe1.text").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
		tooltip.add(Component.translatable("doom.marauder_axe2.text").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
		tooltip.add(Component.translatable("doom.marauder_axe3.text").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity miner) {
		if (miner instanceof Player playerentity) {
			if (stack.getDamageValue() < stack.getMaxDamage() - 1) {
				if (playerentity.getMainHandItem().getItem() instanceof AxeMarauderItem) {
					final var aabb = new AABB(miner.blockPosition().above()).inflate(4D, 1D, 4D);
					miner.getCommandSenderWorld().getEntities(miner, aabb).forEach(e -> doDamage(playerentity, e));
					stack.hurtAndBreak(1, miner, p -> p.broadcastBreakEvent(playerentity.getUsedItemHand()));
				}
			}
		}
		return stack.getDamageValue() < stack.getMaxDamage() - 1 ? true : false;
	}

	private void doDamage(LivingEntity user, final Entity target) {
		if (target instanceof LivingEntity) {
			target.invulnerableTime = 0;
			target.hurt(DamageSource.playerAttack((Player) user), target instanceof ArchMakyrEntity || target instanceof GladiatorEntity || target instanceof IconofsinEntity || target instanceof MotherDemonEntity || target instanceof SpiderMastermind2016Entity || target instanceof SpiderMastermindEntity ? DoomConfig.marauder_axe_item_damage / 10F : DoomConfig.marauder_axe_item_damage);
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		final Player playerentity = (Player) entity;
		if (world.isClientSide)
			if (playerentity.getMainHandItem().getItem() instanceof AxeMarauderItem && ClientInit.reload.consumeClick() && selected) {
				final var passedData = new FriendlyByteBuf(Unpooled.buffer());
				passedData.writeBoolean(true);
				ClientPlayNetworking.send(DoomMod.MARAUDERAXE, passedData);
			}
	}

	public static void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof AxeMarauderItem) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(DoomBlocks.ARGENT_BLOCK.asItem()) > 0) {
				removeAmmo(DoomBlocks.ARGENT_BLOCK.asItem(), user);
				user.getItemInHand(hand).hurtAndBreak(-5, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
			}
		}
	}

	public static void removeAmmo(Item ammo, Player playerEntity) {
		if (!playerEntity.isCreative()) {
			for (final var item : playerEntity.getInventory().offhand) {
				if (item.getItem() == ammo) {
					item.shrink(1);
					break;
				}
				for (final var item1 : playerEntity.getInventory().items) {
					if (item1.getItem() == ammo) {
						item1.shrink(1);
						break;
					}
				}
			}
		}
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return false;
	}

	@Override
	public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
		stack.hasTag();
		stack.enchant(Enchantments.SMITE, 10);
		stack.enchant(Enchantments.MOB_LOOTING, 10);
		stack.enchant(Enchantments.SHARPNESS, 10);
		stack.enchant(Enchantments.SWEEPING_EDGE, 10);
	}

}