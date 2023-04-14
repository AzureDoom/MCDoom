package mod.azure.doom.item.weapons;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.client.Keybindings;
import mod.azure.doom.client.render.weapons.ChainsawRender;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.packets.DoomPacketHandler;
import mod.azure.doom.util.packets.weapons.ChainsawEternalLoadingPacket;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ChainsawAnimated extends Item implements GeoItem {

	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public ChainsawAnimated() {
		super(new Item.Properties().stacksTo(1).durability(601));
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "shoot_controller", event -> event.setAndContinue(RawAnimation.begin().thenLoop("running"))));
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return DoomTier.CHAINSAW.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("Fuel: " + (stack.getMaxDamage() - stack.getDamageValue() - 1) + " / " + (stack.getMaxDamage() - 1)).withStyle(ChatFormatting.ITALIC));
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		final LivingEntity user = (LivingEntity) entity;
		final Player player = (Player) entity;
		if (player.getMainHandItem().sameItem(stack) && stack.getDamageValue() < stack.getMaxDamage() - 1 && !player.getCooldowns().isOnCooldown(this)) {
			final AABB aabb = new AABB(entity.blockPosition().above()).inflate(1D, 1D, 1D);
			entity.getCommandSenderWorld().getEntities(user, aabb).forEach(e -> doDamage(user, e));
			entity.getCommandSenderWorld().getEntities(user, aabb).forEach(e -> doDeathCheck(user, e, stack));
			entity.getCommandSenderWorld().getEntities(user, aabb).forEach(e -> damageItem(user, stack));
			entity.getCommandSenderWorld().getEntities(user, aabb).forEach(this::addParticle);
		}
		if (selected && stack.getMaxDamage() < stack.getMaxDamage() - 1) {
			world.playSound((Player) null, user.getX(), user.getY(), user.getZ(), DoomSounds.CHAINSAW_IDLE.get(), SoundSource.PLAYERS, 0.05F, 1.0F / (world.random.nextFloat() * 0.4F + 1.2F) + 0.25F * 0.5F);
		}
		if (world.isClientSide)
			if (stack.getItem() instanceof ChainsawAnimated)
				while (Keybindings.RELOAD.consumeClick() && selected)
					DoomPacketHandler.CHAINSAW_ETERNAL.sendToServer(new ChainsawEternalLoadingPacket(slot));
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return false;
	}

	public static void removeAmmo(Item ammo, Player playerEntity) {
		if (!playerEntity.isCreative()) {
			for (final ItemStack item : playerEntity.getInventory().offhand) {
				if (item.getItem() == ammo) {
					item.shrink(1);
					break;
				}
				for (final ItemStack item1 : playerEntity.getInventory().items) {
					if (item1.getItem() == ammo) {
						item1.shrink(1);
						break;
					}
				}
			}
		}
	}

	public static void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof ChainsawAnimated) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(DoomItems.GAS_BARREL.get()) > 0) {
				removeAmmo(DoomItems.GAS_BARREL.get(), user);
				user.getItemInHand(hand).hurtAndBreak(-200, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
			}
		}
	}

	private void doDamage(LivingEntity user, Entity target) {
		if (target instanceof LivingEntity) {
			target.setDeltaMovement(0, 0, 0);
			target.invulnerableTime = 0;
			target.hurt(user.damageSources().playerAttack((Player) user), DoomConfig.SERVER.chainsaw_damage.get().floatValue());
			user.level.playSound((Player) null, user.getX(), user.getY(), user.getZ(), DoomSounds.CHAINSAW_ATTACKING.get(), SoundSource.PLAYERS, 0.3F, 1.0F / (user.level.random.nextFloat() * 0.4F + 1.2F) + 0.25F * 0.5F);
		}
	}

	private void doDeathCheck(LivingEntity user, Entity target, ItemStack stack) {
		final Random rand = new Random();
		final List<Item> givenList = Arrays.asList(DoomItems.CHAINGUN_BULLETS.get(), DoomItems.SHOTGUN_SHELLS.get(), DoomItems.ARGENT_BOLT.get(), DoomItems.SHOTGUN_SHELLS.get(), DoomItems.ENERGY_CELLS.get(), DoomItems.ROCKET.get());
		if (target instanceof DemonEntity && !(target instanceof Player)) {
			if (((LivingEntity) target).isDeadOrDying()) {
				if (user instanceof Player playerentity) {
					if (stack.getDamageValue() < stack.getMaxDamage() - 1 && !playerentity.getCooldowns().isOnCooldown(this)) {
						for (int i = 0; i < 5;) {
							final int randomIndex = rand.nextInt(givenList.size());
							final Item randomElement = givenList.get(randomIndex);
							target.spawnAtLocation(randomElement);
							break;
						}
					}
				}
			}
		}
	}

	private void damageItem(LivingEntity user, ItemStack stack) {
		final Player player = (Player) user;
		if (!player.getAbilities().instabuild) {
			stack.setDamageValue(stack.getDamageValue() + 1);
		}
		player.getCooldowns().addCooldown(this, 10);
	}

	private void addParticle(Entity target) {
		if (target instanceof LivingEntity) {
			target.level.addParticle(ParticleTypes.CRIMSON_SPORE, target.getRandomX(0.5D), target.getRandomY(), target.getRandomZ(0.5D), 0.0D, 0D, 0D);

		}
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			private final ChainsawRender renderer = new ChainsawRender();

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return renderer;
			}
		});
	}

}