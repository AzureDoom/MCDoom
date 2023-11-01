package mod.azure.doom.item.weapons;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import mod.azure.azurelib.Keybindings;
import mod.azure.doom.DoomMod;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.packets.DoomPacketHandler;
import mod.azure.doom.util.packets.weapons.ChainsawLoadingPacket;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.DustParticleOptions;
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

public class Chainsaw extends Item {

	public Chainsaw() {
		super(new Item.Properties().stacksTo(1).durability(601));
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

	@Override
	public boolean isFoil(ItemStack stack) {
		return false;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
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
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		final LivingEntity user = (LivingEntity) entityIn;
		final Player player = (Player) entityIn;
		if (player.getMainHandItem().getItem() instanceof Chainsaw && stack.getDamageValue() < stack.getMaxDamage() - 1 && !player.getCooldowns().isOnCooldown(this)) {
			final AABB aabb = new AABB(entityIn.blockPosition().above()).inflate(1D, 1D, 1D);
			entityIn.level().getEntities(user, aabb).forEach(e -> doDamage(user, e));
			entityIn.level().getEntities(user, aabb).forEach(e -> doDeathCheck(user, e, stack));
			entityIn.level().getEntities(user, aabb).forEach(e -> damageItem(user, stack));
			entityIn.level().getEntities(user, aabb).forEach(this::addParticle);
			worldIn.playSound((Player) null, user.getX(), user.getY(), user.getZ(), DoomSounds.CHAINSAW_IDLE.get(), SoundSource.PLAYERS, 0.05F, 1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 0.25F * 0.5F);
		}
		if (worldIn.isClientSide)
			if (player.getMainHandItem().getItem() instanceof Chainsaw && Keybindings.RELOAD.consumeClick() && isSelected)
				DoomPacketHandler.CHAINSAW.sendToServer(new ChainsawLoadingPacket(itemSlot));
	}

	public static void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof Chainsaw) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(DoomItems.GAS_BARREL.get()) > 0) {
				removeAmmo(DoomItems.GAS_BARREL.get(), user);
				user.getItemInHand(hand).hurtAndBreak(-200, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
			}
		}
	}

	private void doDamage(LivingEntity user, final Entity target) {
		if (target instanceof LivingEntity) {
			target.invulnerableTime = 0;
			target.hurt(user.damageSources().playerAttack((Player) user), DoomMod.config.chainsaw_damage);
			user.level().playSound((Player) null, user.getX(), user.getY(), user.getZ(), DoomSounds.CHAINSAW_ATTACKING.get(), SoundSource.PLAYERS, 0.3F, 1.0F / (user.level().random.nextFloat() * 0.4F + 1.2F) + 0.25F * 0.5F);
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
			target.level().addParticle(DustParticleOptions.REDSTONE, target.getRandomX(0.5D), target.getRandomY(), target.getRandomZ(0.5D), 0.0D, 0D, 0D);
		}
	}

}