package mod.azure.doom.item.weapons;

import org.quiltmc.qsl.networking.api.PlayerLookup;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import io.netty.buffer.Unpooled;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.ClientInit;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.ModSoundEvents;
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
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import software.bernie.geckolib3q.network.GeckoLibNetwork;
import software.bernie.geckolib3q.util.GeckoLibUtil;

public class Shotgun extends DoomBaseItem {

	public Shotgun() {
		super(new Item.Settings().group(DoomMod.DoomWeaponItemGroup).maxCount(1).maxDamage(51));
	}

	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return DoomTier.SHOTGUN.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int remainingUseTicks) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			if (stack.getDamage() < (stack.getMaxDamage() - 1)) {
				playerentity.getItemCooldownManager().set(this, 10);
				if (!worldIn.isClient) {
					ShotgunShellEntity abstractarrowentity = createArrow(worldIn, stack, playerentity);
					abstractarrowentity.setProperties(playerentity, playerentity.getPitch(), playerentity.getYaw(),
							0.0F, 1.0F * 3.0F, 1.0F);
					stack.damage(1, entityLiving, p -> p.sendToolBreakStatus(entityLiving.getActiveHand()));
					worldIn.spawnEntity(abstractarrowentity);
					worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(),
							playerentity.getZ(), ModSoundEvents.SHOTGUN_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.5F);
					if (!worldIn.isClient) {
						final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerWorld) worldIn);
						GeckoLibNetwork.syncAnimation(playerentity, this, id, ANIM_OPEN);
						for (PlayerEntity otherPlayer : PlayerLookup.tracking(playerentity)) {
							GeckoLibNetwork.syncAnimation(otherPlayer, this, id, ANIM_OPEN);
						}
					}
					boolean isInsideWaterBlock = playerentity.world.isWater(playerentity.getBlockPos());
					spawnLightSource(entityLiving, isInsideWaterBlock);
				}
			} else {
				worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(),
						ModSoundEvents.EMPTY, SoundCategory.PLAYERS, 1.0F, 1.5F);
			}
		}
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
		if (user.getStackInHand(hand).getItem() instanceof Shotgun) {
			while (!user.isCreative() && user.getStackInHand(hand).getDamage() != 0
					&& user.getInventory().count(DoomItems.SHOTGUN_SHELLS) > 0) {
				removeAmmo(DoomItems.SHOTGUN_SHELLS, user);
				user.getStackInHand(hand).damage(-4, user, s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setCooldown(3);
				user.getWorld().playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(),
						ModSoundEvents.SHOTGUNRELOAD, SoundCategory.PLAYERS, 1.00F, 1.0F);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (world.isClient) {
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof Shotgun && ClientInit.reload.isPressed()
					&& selected) {
				PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
				passedData.writeBoolean(true);
				ClientPlayNetworking.send(DoomMod.SHOTGUN, passedData);
			}
		}
	}

	public ShotgunShellEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		float j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
		ShotgunShellEntity arrowentity = new ShotgunShellEntity(worldIn, shooter,
				(DoomConfig.shotgun_damage + (j * 2.0F)));
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
}