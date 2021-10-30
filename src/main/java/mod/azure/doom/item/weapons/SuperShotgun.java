package mod.azure.doom.item.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.Keybindings;
import mod.azure.doom.client.render.weapons.SSGRender;
import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.packets.DoomPacketHandler;
import mod.azure.doom.util.packets.weapons.SSGLoadingPacket;
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

public class SuperShotgun extends DoomBaseItem {

	public SuperShotgun() {
		super(new Item.Properties().tab(DoomMod.DoomWeaponItemGroup).stacksTo(1).durability(53)
				.setISTER(() -> SSGRender::new));
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return DoomTier.SHOTGUN.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

//	@Override
//	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
//		ItemStack stack = player.getItemInHand(hand);
//		if (!world.isClientSide) {
//			double maxRange = 24D;
//			double maxSpeed = 2D;
//			HookEntity hookshot = new HookEntity(ModEntityTypes.HOOK.get(), player, world);
//			hookshot.shootFromRotation(stack, maxRange, maxSpeed, player.xRot, player.yHeadRot, 0f,
//					1.5f * (float) (maxSpeed / 10));
//			world.addFreshEntity(hookshot);
//		}
//		return super.use(world, player, hand);
//	}

	@Override
	public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			if (stack.getDamageValue() < (stack.getMaxDamage() - 2)) {
				playerentity.getCooldowns().addCooldown(stack.getItem(), 24);
				if (!worldIn.isClientSide) {
					ShotgunShellEntity abstractarrowentity = createArrow(worldIn, stack, playerentity);
					abstractarrowentity = customeArrow(abstractarrowentity);
					abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot + 1, 0.0F,
							1.0F * 3.0F, 1.0F);
					abstractarrowentity.isNoGravity();
					worldIn.addFreshEntity(abstractarrowentity);
					ShotgunShellEntity abstractarrowentity1 = createArrow(worldIn, stack, playerentity);
					abstractarrowentity1 = customeArrow(abstractarrowentity1);
					abstractarrowentity1.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot - 1, 0.0F,
							1.0F * 3.0F, 1.0F);
					abstractarrowentity1.isNoGravity();
					worldIn.addFreshEntity(abstractarrowentity1);

					stack.hurtAndBreak(2, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(),
							playerentity.getZ(), ModSoundEvents.SUPER_SHOTGUN_SHOOT.get(), SoundCategory.PLAYERS, 1.0F,
							1.0F);
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

	public ShotgunShellEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		ShotgunShellEntity arrowentity = new ShotgunShellEntity(worldIn, shooter,
				DoomConfig.SERVER.shotgun_damage.get().floatValue());
		return arrowentity;
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (worldIn.isClientSide) {
			if (((PlayerEntity) entityIn).getMainHandItem().getItem() instanceof SuperShotgun) {
				while (Keybindings.RELOAD.consumeClick() && isSelected) {
					DoomPacketHandler.SUPERSHOTGUN.sendToServer(new SSGLoadingPacket(itemSlot));
				}
			}
		}
	}

	public static void reload(PlayerEntity user, Hand hand) {
		if (user.getItemInHand(hand).getItem() instanceof SuperShotgun) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0
					&& user.inventory.countItem(DoomItems.SHOTGUN_SHELLS.get()) > 0) {
				removeAmmo(DoomItems.SHOTGUN_SHELLS.get(), user);
				user.getItemInHand(hand).hurtAndBreak(-4, user, s -> user.broadcastBreakEvent(hand));
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

	public ShotgunShellEntity customeArrow(ShotgunShellEntity arrow) {
		return arrow;
	}
}