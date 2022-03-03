package mod.azure.doom.item.weapons;

import java.util.List;

import io.netty.buffer.Unpooled;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.ClientInit;
import mod.azure.doom.entity.projectiles.EnergyCellEntity;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.registry.DoomBlocks;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class DPlasmaRifle extends DoomBaseItem {

	public DPlasmaRifle() {
		super(new Item.Settings().group(DoomMod.DoomWeaponItemGroup).maxCount(1).maxDamage(401));
	}

	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return DoomTier.PLASMA.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
	}

	@Override
	public void usageTick(World worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
		if (livingEntityIn instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) livingEntityIn;
			if (stack.getDamage() < (stack.getMaxDamage() - 1)) {
				if (!playerentity.getItemCooldownManager().isCoolingDown(this)) {
					playerentity.getItemCooldownManager().set(this, 5);
					if (!worldIn.isClient) {
						EnergyCellEntity abstractarrowentity = createArrow(worldIn, stack, playerentity);
						abstractarrowentity.setVelocity(playerentity, playerentity.getPitch(), playerentity.getYaw(), 0.0F,
								0.15F * 3.0F, 1.0F);
						abstractarrowentity.hasNoGravity();

						stack.damage(1, livingEntityIn, p -> p.sendToolBreakStatus(livingEntityIn.getActiveHand()));
						worldIn.spawnEntity(abstractarrowentity);
						worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(),
								playerentity.getZ(), ModSoundEvents.PLASMA_FIRING, SoundCategory.PLAYERS, 1.0F,
								1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
						if (!worldIn.isClient) {
							final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerWorld) worldIn);
							GeckoLibNetwork.syncAnimation(playerentity, this, id, ANIM_OPEN);
							for (PlayerEntity otherPlayer : PlayerLookup.tracking(playerentity)) {
								GeckoLibNetwork.syncAnimation(otherPlayer, this, id, ANIM_OPEN);
							}
						}
						worldIn.setBlockState(playerentity.getCameraBlockPos(), DoomBlocks.TICKING_LIGHT_BLOCK.getDefaultState());
					}
				}
			} else {
				worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(),
						ModSoundEvents.EMPTY, SoundCategory.PLAYERS, 1.0F, 1.5F);
			}
		}
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof DPlasmaRifle) {
			while (!user.isCreative() && user.getStackInHand(hand).getDamage() != 0
					&& user.getInventory().count(DoomItems.ENERGY_CELLS) > 0) {
				removeAmmo(DoomItems.ENERGY_CELLS, user);
				user.getStackInHand(hand).damage(-20, user, s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setBobbingAnimationTime(3);
				user.getEntityWorld().playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(),
						ModSoundEvents.CLIPRELOAD, SoundCategory.PLAYERS, 1.00F, 1.0F);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (world.isClient) {
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof DPlasmaRifle
					&& ClientInit.reload.isPressed() && selected) {
				PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
				passedData.writeBoolean(true);
				ClientPlayNetworking.send(DoomMod.DPLASMARIFLE, passedData);
			}
		}
	}

	public EnergyCellEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		float j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
		EnergyCellEntity arrowentity = new EnergyCellEntity(worldIn, shooter,
				(DoomMod.config.weapons.energycell_damage + (j * 2.0F)));
		return arrowentity;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		tooltip.add(
				new TranslatableText("doom.doomed_credit.text").formatted(Formatting.RED).formatted(Formatting.ITALIC));
		tooltip.add(
				new TranslatableText("doom.doomed_credit1.text").formatted(Formatting.RED).formatted(Formatting.ITALIC));
	}
}