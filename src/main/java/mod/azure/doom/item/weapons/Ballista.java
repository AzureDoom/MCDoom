package mod.azure.doom.item.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.Keybindings;
import mod.azure.doom.client.render.weapons.BallistaRender;
import mod.azure.doom.entity.projectiles.ArgentBoltEntity;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.packets.DoomPacketHandler;
import mod.azure.doom.util.packets.weapons.BallistaLoadingPacket;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Ballista extends DoomBaseItem {

	public Ballista() {
		super(new Item.Properties().tab(DoomMod.DoomWeaponItemGroup).stacksTo(1).durability(11)
				.setISTER(() -> BallistaRender::new));
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return DoomTier.BALLISTA.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			if (stack.getDamageValue() < (stack.getMaxDamage() - 1)) {
				playerentity.getCooldowns().addCooldown(this, 25);
				if (!worldIn.isClientSide) {
					ArgentBoltEntity abstractarrowentity = createArrow(worldIn, stack, playerentity);
					abstractarrowentity = customeArrow(abstractarrowentity);
					abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F,
							1.0F * 3.0F, 1.0F);
					abstractarrowentity.isNoGravity();

					stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					worldIn.addFreshEntity(abstractarrowentity);
					worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(),
							playerentity.getZ(), ModSoundEvents.BALLISTA_FIRING.get(), SoundCategory.PLAYERS, 1.0F,
							1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.25F * 0.5F);
					if (!worldIn.isClientSide) {
						final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerWorld) worldIn);
						final PacketDistributor.PacketTarget target = PacketDistributor.TRACKING_ENTITY_AND_SELF
								.with(() -> playerentity);
						GeckoLibNetwork.syncAnimation(target, this, id, ANIM_OPEN);
					}
				}
			}
		}
	}

	public ArgentBoltEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		ArgentBoltEntity arrowentity = new ArgentBoltEntity(worldIn, shooter);
		return arrowentity;
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (worldIn.isClientSide) {
			if (((PlayerEntity) entityIn).getMainHandItem().getItem() instanceof Ballista) {
				while (Keybindings.RELOAD.consumeClick() && isSelected) {
					DoomPacketHandler.BALLISTA.sendToServer(new BallistaLoadingPacket(itemSlot));
				}
			}
		}
	}

	public static void reload(PlayerEntity user, Hand hand) {
		if (user.getItemInHand(hand).getItem() instanceof Ballista) {
			while (user.getItemInHand(hand).getDamageValue() != 0
					&& user.inventory.countItem(DoomItems.ARGENT_BOLT.get()) > 0) {
				removeAmmo(DoomItems.ARGENT_BOLT.get(), user);
				user.getItemInHand(hand).hurtAndBreak(-1, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
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

	public ArgentBoltEntity customeArrow(ArgentBoltEntity arrow) {
		return arrow;
	}
}