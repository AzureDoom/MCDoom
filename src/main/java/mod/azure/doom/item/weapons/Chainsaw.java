package mod.azure.doom.item.weapons;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.netty.buffer.Unpooled;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.ClientInit;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class Chainsaw extends Item {

	public Chainsaw() {
		super(new Item.Settings().group(DoomMod.DoomWeaponItemGroup).maxCount(1).maxDamage(601));
	}

	@Override
	public boolean canRepair(ItemStack toRepair, ItemStack repair) {
		return DoomTier.CHAINSAW.getRepairIngredient().test(repair) || super.canRepair(toRepair, repair);
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText(
				"Fuel: " + (stack.getMaxDamage() - stack.getDamage() - 1) + " / " + (stack.getMaxDamage() - 1))
						.formatted(Formatting.ITALIC));
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		LivingEntity user = (LivingEntity) entityIn;
		PlayerEntity player = (PlayerEntity) entityIn;
		if (player.getMainHandStack().isItemEqualIgnoreDamage(stack) && stack.getDamage() < (stack.getMaxDamage() - 1)
				&& !player.getItemCooldownManager().isCoolingDown(this)) {
			final Box aabb = new Box(entityIn.getBlockPos().up()).expand(1D, 1D, 1D);
			entityIn.getEntityWorld().getOtherEntities(user, aabb).forEach(e -> doDamage(user, e));
			entityIn.getEntityWorld().getOtherEntities(user, aabb).forEach(e -> doDeathCheck(user, e, stack));
			entityIn.getEntityWorld().getOtherEntities(user, aabb).forEach(e -> damageItem(user, stack));
			entityIn.getEntityWorld().getOtherEntities(user, aabb).forEach(e -> addParticle(e));
		}
		if (isSelected && stack.getDamage() < (stack.getMaxDamage() - 1)) {
			worldIn.playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(), DoomSounds.CHAINSAW_IDLE,
					SoundCategory.PLAYERS, 0.05F, 1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 0.25F * 0.5F);
		}
		if (worldIn.isClient) {
			if (player.getMainHandStack().getItem() instanceof Chainsaw && ClientInit.reload.isPressed()
					&& isSelected) {
				PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
				passedData.writeBoolean(true);
				ClientPlayNetworking.send(DoomMod.CHAINSAW, passedData);
			}
		}
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return false;
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

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof Chainsaw) {
			while (!user.isCreative() && user.getStackInHand(hand).getDamage() != 0
					&& user.getInventory().count(DoomItems.GAS_BARREL) > 0) {
				removeAmmo(DoomItems.BULLETS, user);
				user.getStackInHand(hand).damage(-200, user, s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setBobbingAnimationTime(3);
			}
		}
	}

	private void doDamage(LivingEntity user, Entity target) {
		if (target instanceof LivingEntity) {
			target.timeUntilRegen = 0;
			target.damage(DamageSource.player((PlayerEntity) user), DoomConfig.chainsaw_damage);
			user.world.playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(),
					DoomSounds.CHAINSAW_ATTACKING, SoundCategory.PLAYERS, 0.05F,
					1.0F / (target.world.random.nextFloat() * 0.4F + 1.2F) + 0.25F * 0.5F);
		}
	}

	private void doDeathCheck(LivingEntity user, Entity target, ItemStack stack) {
		Random rand = new Random();
		List<Item> givenList = Arrays.asList(DoomItems.CHAINGUN_BULLETS, DoomItems.SHOTGUN_SHELLS,
				DoomItems.ARGENT_BOLT, DoomItems.SHOTGUN_SHELLS, DoomItems.ENERGY_CELLS, DoomItems.ROCKET);
		if (target instanceof DemonEntity && !(target instanceof PlayerEntity)) {
			if (((LivingEntity) target).isDead()) {
				if (user instanceof PlayerEntity) {
					PlayerEntity playerentity = (PlayerEntity) user;
					if (stack.getDamage() < (stack.getMaxDamage() - 1)
							&& !playerentity.getItemCooldownManager().isCoolingDown(this)) {
						for (int i = 0; i < 5;) {
							int randomIndex = rand.nextInt(givenList.size());
							Item randomElement = givenList.get(randomIndex);
							target.dropItem(randomElement);
							break;
						}
					}
				}
			}
		}
	}

	private void damageItem(LivingEntity user, ItemStack stack) {
		PlayerEntity player = (PlayerEntity) user;
		if (!player.getAbilities().creativeMode) {
			stack.setDamage(stack.getDamage() + 1);
		}
		player.getItemCooldownManager().set(this, 10);
	}

	private void addParticle(Entity target) {
		if (target instanceof LivingEntity) {
			target.world.addParticle(ParticleTypes.CRIMSON_SPORE, target.getParticleX(0.5D), target.getRandomBodyY(),
					target.getParticleZ(0.5D), 0.0D, 0D, 0D);
		}
	}

}