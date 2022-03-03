package mod.azure.doom.item.weapons;

import java.util.List;

import io.netty.buffer.Unpooled;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.ClientInit;
import mod.azure.doom.entity.projectiles.MeatHookEntity;
import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import mod.azure.doom.util.PlayerProperties;
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
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class SuperShotgun extends DoomBaseItem {

	public SuperShotgun() {
		super(new Item.Settings().group(DoomMod.DoomWeaponItemGroup).maxCount(1).maxDamage(53));
	}

	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return DoomTier.SHOTGUN.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int remainingUseTicks) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			if (stack.getDamage() < (stack.getMaxDamage() - 2)) {
				if (playerentity.getMainHandStack().getItem() instanceof SuperShotgun) {
					playerentity.getItemCooldownManager().set(this, 24);
					if (!worldIn.isClient) {
						ShotgunShellEntity abstractarrowentity = createArrow(worldIn, stack, playerentity);
						abstractarrowentity.setVelocity(playerentity, playerentity.getPitch(), playerentity.getYaw() + 1, 0.0F,
								1.0F * 3.0F, 1.0F);
						worldIn.spawnEntity(abstractarrowentity);
						ShotgunShellEntity abstractarrowentity1 = createArrow(worldIn, stack, playerentity);
						abstractarrowentity1.setVelocity(playerentity, playerentity.getPitch(), playerentity.getYaw() - 1, 0.0F,
								1.0F * 3.0F, 1.0F);
						worldIn.spawnEntity(abstractarrowentity1);

						stack.damage(2, entityLiving, p -> p.sendToolBreakStatus(entityLiving.getActiveHand()));
						worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(),
								playerentity.getZ(), ModSoundEvents.SUPER_SHOTGUN_SHOOT, SoundCategory.PLAYERS, 1.0F,
								1.0F);
						final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerWorld) worldIn);
						GeckoLibNetwork.syncAnimation(playerentity, this, id, ANIM_OPEN);
						for (PlayerEntity otherPlayer : PlayerLookup.tracking(playerentity)) {
							GeckoLibNetwork.syncAnimation(otherPlayer, this, id, ANIM_OPEN);
						}
						worldIn.setBlockState(playerentity.getCameraBlockPos(), DoomBlocks.TICKING_LIGHT_BLOCK.getDefaultState());
					}
				}
			} else {
				worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(),
						ModSoundEvents.EMPTY, SoundCategory.PLAYERS, 1.0F, 1.5F);
				((PlayerProperties) playerentity).setHasMeatHook(false);
			}
		}
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getOffHandStack();
		if (stack.getDamage() < (stack.getMaxDamage() - 2)) {
			if (!world.isClient && stack.getItem() instanceof SuperShotgun) {
				if (!((PlayerProperties) user).hasMeatHook()) {
					MeatHookEntity hookshot = new MeatHookEntity(world, user);
					hookshot.setProperties(stack, DoomMod.config.weapons.max_meathook_distance, 10, user.getPitch(),
							user.getYaw(), 0f, 1.5f);
					hookshot.getDataTracker().set(MeatHookEntity.FORCED_YAW, user.getYaw());
					world.spawnEntity(hookshot);
				}
				((PlayerProperties) user).setHasMeatHook(!((PlayerProperties) user).hasMeatHook());
			}
		}
		return super.use(world, user, hand);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		((PlayerProperties) user).setHasMeatHook(false);
		return super.finishUsing(stack, world, user);
	}

	public static float getArrowVelocity(int charge) {
		float f = (float) charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof SuperShotgun) {
			while (!user.isCreative() && user.getStackInHand(hand).getDamage() != 0
					&& user.getInventory().count(DoomItems.SHOTGUN_SHELLS) > 0) {
				removeAmmo(DoomItems.SHOTGUN_SHELLS, user);
				user.getStackInHand(hand).damage(-4, user, s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setBobbingAnimationTime(3);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (world.isClient) {
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof SuperShotgun
					&& ClientInit.reload.isPressed() && selected) {
				PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
				passedData.writeBoolean(true);
				ClientPlayNetworking.send(DoomMod.SUPERSHOTGUN, passedData);
			}
		}
		if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof SuperShotgun && selected
				&& ((PlayerProperties) entity).hasMeatHook()) {
			((PlayerProperties) entity).setHasMeatHook(false);
		}
	}

	public ShotgunShellEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		float j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
		ShotgunShellEntity arrowentity = new ShotgunShellEntity(worldIn, shooter,
				(DoomMod.config.weapons.shotgun_damage + (j * 2.0F)));
		return arrowentity;
	}

	public static float getPullProgress(int useTicks) {
		float f = (float) useTicks / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
	}

}