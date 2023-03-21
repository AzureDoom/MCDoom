package mod.azure.doom.item.weapons;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import io.netty.buffer.Unpooled;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.ClientInit;
import mod.azure.doom.client.render.weapons.DSGRender;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class DShotgun extends DoomBaseItem {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public DShotgun() {
		super(new Item.Properties().stacksTo(1).durability(51));
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return DoomTier.SHOTGUN.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof Player playerentity) {
			if (stack.getDamageValue() < stack.getMaxDamage() - 1) {
				playerentity.getCooldowns().addCooldown(this, 10);
				if (!worldIn.isClientSide) {
					final ShotgunShellEntity abstractarrowentity = createArrow(worldIn, stack, playerentity);
					abstractarrowentity.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(), 0.0F, 1.0F * 3.0F, 1.0F);
					stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					worldIn.addFreshEntity(abstractarrowentity);
					worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), DoomSounds.SHOTGUN_SHOOT, SoundSource.PLAYERS, 1.0F, 1.5F);
					triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) worldIn), "shoot_controller", "firing");
				}
				final boolean isInsideWaterBlock = playerentity.level.isWaterAt(playerentity.blockPosition());
				spawnLightSource(entityLiving, isInsideWaterBlock);
			} else {
				worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), DoomSounds.EMPTY, SoundSource.PLAYERS, 1.0F, 1.5F);
			}
		}
	}

	public static void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof DShotgun) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(DoomItems.SHOTGUN_SHELLS) > 0) {
				removeAmmo(DoomItems.SHOTGUN_SHELLS, user);
				user.getItemInHand(hand).hurtAndBreak(-4, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
				user.getCommandSenderWorld().playSound((Player) null, user.getX(), user.getY(), user.getZ(), DoomSounds.SHOTGUNRELOAD, SoundSource.PLAYERS, 1.00F, 1.0F);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (world.isClientSide) {
			if (((Player) entity).getMainHandItem().getItem() instanceof DShotgun && ClientInit.reload.consumeClick() && selected) {
				final FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
				passedData.writeBoolean(true);
				ClientPlayNetworking.send(DoomMod.DSG, passedData);
			}
		}
	}

	public ShotgunShellEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		final float j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
		final ShotgunShellEntity arrowentity = new ShotgunShellEntity(worldIn, shooter, DoomConfig.shotgun_damage + j * 2.0F);
		return arrowentity;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("doom.doomed_credit.text").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
		tooltip.add(Component.translatable("doom.doomed_credit1.text").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final DSGRender renderer = new DSGRender();

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return renderProvider;
	}

}