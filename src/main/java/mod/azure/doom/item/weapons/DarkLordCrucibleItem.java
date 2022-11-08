package mod.azure.doom.item.weapons;

import java.util.List;

import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import io.netty.buffer.Unpooled;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.ClientInit;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
import mod.azure.doom.entity.tierboss.GladiatorEntity;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import mod.azure.doom.entity.tierboss.SpiderMastermind2016Entity;
import mod.azure.doom.entity.tierboss.SpiderMastermindEntity;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.registry.DoomBlocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
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
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class DarkLordCrucibleItem extends SwordItem implements IAnimatable, ISyncable {

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	public String controllerName = "controller";
	public static final int ANIM_OPEN = 0;

	public DarkLordCrucibleItem() {
		super(DoomTier.DOOM, 1, -2.5f,
				new Item.Settings().group(DoomMod.DoomWeaponItemGroup).maxCount(1).maxDamage(24));
		GeckoLibNetwork.registerSyncable(this);
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
				if (playerentity.getMainHandStack().getItem() instanceof DarkLordCrucibleItem) {
					playerentity.getItemCooldownManager().set(this, 200);
					final Box aabb = new Box(playerentity.getBlockPos().up()).expand(4D, 1D, 4D);
					playerentity.getWorld().getOtherEntities(playerentity, aabb)
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

	public <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		if (MinecraftClient.getInstance().player.getInventory().getMainHandStack()
				.getItem() instanceof DarkLordCrucibleItem) {
			event.getController().setAnimation(new AnimationBuilder()
					.addAnimation("opening", EDefaultLoopTypes.PLAY_ONCE).addAnimation("open", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		} else {
			event.getController().setAnimation(new AnimationBuilder().addAnimation("closed", EDefaultLoopTypes.LOOP));
			return PlayState.CONTINUE;
		}
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController(this, controllerName, 1, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public void onAnimationSync(int id, int state) {
		if (state == ANIM_OPEN) {
			final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, controllerName);
			if (controller.getAnimationState() == AnimationState.Stopped) {
				controller.markNeedsReload();
				controller.setAnimation(new AnimationBuilder().addAnimation("using", EDefaultLoopTypes.PLAY_ONCE));
			}
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text
				.translatable(
						"Ammo: " + (stack.getMaxDamage() - stack.getDamage() - 1) + " / " + (stack.getMaxDamage() - 1))
				.formatted(Formatting.ITALIC));
		super.appendTooltip(stack, world, tooltip, context);
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof DarkLordCrucibleItem) {
			while (!user.isCreative() && user.getStackInHand(hand).getDamage() != 0
					&& user.getInventory().count(DoomBlocks.ARGENT_BLOCK.asItem()) > 0) {
				removeAmmo(DoomBlocks.ARGENT_BLOCK.asItem(), user);
				user.getStackInHand(hand).damage(-5, user, s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setCooldown(3);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		PlayerEntity playerentity = (PlayerEntity) entity;
		if (world.isClient) {
			if (playerentity.getMainHandStack().getItem() instanceof DarkLordCrucibleItem
					&& ClientInit.reload.isPressed() && selected) {
				PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
				passedData.writeBoolean(true);
				ClientPlayNetworking.send(DoomMod.DARKLORDCRUCIBLE, passedData);
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
}