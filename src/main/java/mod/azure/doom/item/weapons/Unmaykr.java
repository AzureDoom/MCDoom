package mod.azure.doom.item.weapons;

import java.util.function.Consumer;
import java.util.function.Supplier;

import io.netty.buffer.Unpooled;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.ClientInit;
import mod.azure.doom.client.render.weapons.UnmaykrRender;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.projectiles.UnmaykrBoltEntity;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class Unmaykr extends DoomBaseItem {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public final String itemID;

	public Unmaykr(String id) {
		super(new Item.Properties().stacksTo(1).durability(9000));
		itemID = id;
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return DoomTier.UNMAYKR.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public void onUseTick(Level worldIn, LivingEntity entityLiving, ItemStack stack, int count) {
		if (entityLiving instanceof Player playerentity) {
			if (stack.getDamageValue() < stack.getMaxDamage() - 1 && !playerentity.getCooldowns().isOnCooldown(this)) {
				playerentity.getCooldowns().addCooldown(this, 5);
				if (!worldIn.isClientSide) {
					final UnmaykrBoltEntity abstractarrowentity = createArrow(worldIn, stack, playerentity);
					abstractarrowentity.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(), 0.0F, 1.0F * 3.0F, 1.0F);
					final UnmaykrBoltEntity abstractarrowentity1 = createArrow(worldIn, stack, playerentity);
					abstractarrowentity1.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot() + 10, 0.0F, 1.0F * 3.0F, 1.0F);
					final UnmaykrBoltEntity abstractarrowentity2 = createArrow(worldIn, stack, playerentity);
					abstractarrowentity2.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot() - 10, 0.0F, 1.0F * 3.0F, 1.0F);
					stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					worldIn.addFreshEntity(abstractarrowentity);
					worldIn.addFreshEntity(abstractarrowentity1);
					worldIn.addFreshEntity(abstractarrowentity2);
					worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), DoomSounds.UNMAKYR_FIRE, SoundSource.PLAYERS, 1.0F, 1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 0.25F * 0.5F);
					triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) worldIn), "shoot_controller", "firing");
				}
				final boolean isInsideWaterBlock = playerentity.level.isWaterAt(playerentity.blockPosition());
				spawnLightSource(entityLiving, isInsideWaterBlock);
			}
		}
	}

	public static float getArrowVelocity(int charge) {
		return 1.0F;
	}

	public static void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof Unmaykr) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(DoomItems.UNMAKRY_BOLT) > 0) {
				removeAmmo(DoomItems.UNMAKRY_BOLT, user);
				user.getItemInHand(hand).hurtAndBreak(-20, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (world.isClientSide) {
			if (((Player) entity).getMainHandItem().getItem() instanceof Unmaykr && ClientInit.reload.consumeClick() && selected) {
				final FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
				passedData.writeBoolean(true);
				ClientPlayNetworking.send(DoomMod.UNMAYKR, passedData);
			}
		}
	}

	public UnmaykrBoltEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		final float j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
		final UnmaykrBoltEntity arrowentity = new UnmaykrBoltEntity(worldIn, shooter, DoomConfig.unmaykr_damage + j * 2.0F);
		return arrowentity;
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final UnmaykrRender renderer = new UnmaykrRender();

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