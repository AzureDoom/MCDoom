package mod.azure.doom.item.weapons;

import java.util.List;
import java.util.function.Consumer;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.Keybindings;
import mod.azure.doom.client.render.weapons.SentinelHammerRender;
import mod.azure.doom.util.packets.DoomPacketHandler;
import mod.azure.doom.util.packets.weapons.SentinelHammerLoadingPacket;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.IItemRenderProperties;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class SentinelHammerItem extends Item implements IAnimatable, ISyncable {

	public AnimationFactory factory = new AnimationFactory(this);
	public String controllerName = "controller";
	public static final int ANIM_OPEN = 0;

	public SentinelHammerItem() {
		super(new Item.Properties().tab(DoomMod.DoomWeaponItemGroup).stacksTo(1).durability(5));
		GeckoLibNetwork.registerSyncable(this);
	}

	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		super.initializeClient(consumer);
		consumer.accept(new IItemRenderProperties() {
			private final BlockEntityWithoutLevelRenderer renderer = new SentinelHammerRender();

			@Override
			public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
				return renderer;
			}
		});
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable(
				"Ammo: " + (stack.getMaxDamage() - stack.getDamageValue() - 1) + " / " + (stack.getMaxDamage() - 1))
				.withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity miner) {
		if (miner instanceof Player) {
			Player playerentity = (Player) miner;
			if (!playerentity.getCooldowns().isOnCooldown(this)
					&& playerentity.getMainHandItem().getItem() instanceof SentinelHammerItem) {
				playerentity.getCooldowns().addCooldown(this, 200);
				final AABB aabb = new AABB(miner.blockPosition().above()).inflate(5D, 5D, 5D);
				miner.getCommandSenderWorld().getEntities(miner, aabb).forEach(e -> doDamage(playerentity, e));
				stack.hurtAndBreak(1, miner, p -> p.broadcastBreakEvent(playerentity.getUsedItemHand()));
				AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(miner.level, miner.getX(),
						playerentity.getY(), playerentity.getZ());
				areaeffectcloudentity.setParticle(ParticleTypes.CRIT);
				areaeffectcloudentity.setRadius(5.0F);
				areaeffectcloudentity.setDuration(20);
				areaeffectcloudentity.setPos(playerentity.getX(), playerentity.getY(), playerentity.getZ());
				playerentity.level.addFreshEntity(areaeffectcloudentity);
			}
		}
		return true;
	}

	private void doDamage(LivingEntity user, final Entity target) {
		if (target instanceof LivingEntity) {
			target.invulnerableTime = 0;
			((LivingEntity) target).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1000, 2));
			target.hurt(DamageSource.playerAttack((Player) user), 25F);
		}
	}

	public <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		return PlayState.CONTINUE;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
				controller.setAnimation(new AnimationBuilder().addAnimation("using", false));
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		Player Player = (Player) entityIn;
		if (worldIn.isClientSide) {
			if (Player.getMainHandItem().getItem() instanceof SentinelHammerItem) {
				while (Keybindings.RELOAD.consumeClick() && isSelected) {
					DoomPacketHandler.SENTINELHAMMER.sendToServer(new SentinelHammerLoadingPacket(itemSlot));
				}
			}
		}
	}

	public static void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof SentinelHammerItem) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0
					&& user.getInventory().countItem(DoomItems.ARGENT_ENERGY.get()) > 0) {
				removeAmmo(DoomItems.ARGENT_ENERGY.get(), user);
				user.getItemInHand(hand).hurtAndBreak(-5, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
			}
		}
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
	public boolean isFoil(ItemStack stack) {
		return false;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 7200;
	}

}