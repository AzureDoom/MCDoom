package mod.azure.doom.item.weapons;

import io.netty.buffer.Unpooled;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.ClientInit;
import mod.azure.doom.entity.projectiles.ChaingunBulletEntity;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
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
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Chaingun extends DoomBaseItem {

	public Chaingun() {
		super(new Item.Settings().group(DoomMod.DoomWeaponItemGroup).maxCount(1).maxDamage(201));
	}

	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return DoomTier.CHAINGUN.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
	}

	@Override
	public void usageTick(World worldIn, LivingEntity entityLiving, ItemStack stack, int count) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			if (stack.getDamage() < (stack.getMaxDamage() - 1)
					&& !playerentity.getItemCooldownManager().isCoolingDown(this)) {
				playerentity.getItemCooldownManager().set(this, 2);
				if (!worldIn.isClient) {
					ChaingunBulletEntity abstractarrowentity = createArrow(worldIn, stack, playerentity);
					abstractarrowentity.setVelocity(playerentity, playerentity.pitch, playerentity.yaw, 0.0F,
							1.0F * 3.0F, 1.0F);
					abstractarrowentity.hasNoGravity();

					stack.damage(1, entityLiving, p -> p.sendToolBreakStatus(entityLiving.getActiveHand()));
					worldIn.spawnEntity(abstractarrowentity);
					worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(),
							playerentity.getZ(), ModSoundEvents.CHAINGUN_SHOOT, SoundCategory.PLAYERS, 1.0F,
							1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
					if (!worldIn.isClient) {
						final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerWorld) worldIn);
						GeckoLibNetwork.syncAnimation(playerentity, this, id, ANIM_OPEN);
						for (PlayerEntity otherPlayer : PlayerLookup.tracking(playerentity)) {
							GeckoLibNetwork.syncAnimation(otherPlayer, this, id, ANIM_OPEN);
						}
					}
				}
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
		if (user.getStackInHand(hand).getItem() instanceof Chaingun) {
			while (user.getStackInHand(hand).getDamage() != 0
					&& user.getInventory().count(DoomItems.CHAINGUN_BULLETS) > 0) {
				removeAmmo(DoomItems.CHAINGUN_BULLETS, user);
				user.getStackInHand(hand).damage(-50, user, s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setBobbingAnimationTime(3);
				user.getEntityWorld().playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(),
						ModSoundEvents.CLIPRELOAD, SoundCategory.PLAYERS, 1.00F, 1.0F);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (world.isClient) {
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof Chaingun
					&& ClientInit.reload.isPressed() && selected) {
				PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
				passedData.writeBoolean(true);
				ClientPlayNetworking.send(DoomMod.CHAINGUN, passedData);
			}
		}
	}

	public ChaingunBulletEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		ChaingunBulletEntity arrowentity = new ChaingunBulletEntity(worldIn, shooter);
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