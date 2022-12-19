package mod.azure.doom.item.weapons;

import java.util.function.Consumer;
import java.util.function.Supplier;

import io.netty.buffer.Unpooled;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.ClientInit;
import mod.azure.doom.client.render.weapons.UnmakerRender;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.projectiles.UnmaykrBoltEntity;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
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
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;

public class Unmaker extends DoomBaseItem {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public final String itemID;

	public Unmaker(String id) {
		super(new Item.Settings().maxCount(1).maxDamage(9000));
		this.itemID = id;
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return DoomTier.UNMAYKR.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
	}

	@Override
	public void usageTick(World worldIn, LivingEntity entityLiving, ItemStack stack, int count) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;

			if (stack.getDamage() < (stack.getMaxDamage() - 1)
					&& !playerentity.getItemCooldownManager().isCoolingDown(this)) {
				playerentity.getItemCooldownManager().set(this, 5);
				if (!worldIn.isClient) {
					UnmaykrBoltEntity abstractarrowentity = createArrow(worldIn, stack, playerentity);
					abstractarrowentity.setVelocity(playerentity, playerentity.getPitch(), playerentity.getYaw(), 0.0F,
							1.0F * 3.0F, 1.0F);
					UnmaykrBoltEntity abstractarrowentity2 = createArrow(worldIn, stack, playerentity);
					abstractarrowentity2.setVelocity(playerentity, playerentity.getPitch(), playerentity.getYaw() - 10,
							0.0F, 1.0F * 3.0F, 1.0F);
					UnmaykrBoltEntity abstractarrowentity1 = createArrow(worldIn, stack, playerentity);
					abstractarrowentity1.setVelocity(playerentity, playerentity.getPitch(), playerentity.getYaw() + 10,
							0.0F, 1.0F * 3.0F, 1.0F);

					abstractarrowentity.hasNoGravity();
					abstractarrowentity1.hasNoGravity();
					abstractarrowentity2.hasNoGravity();

					stack.damage(1, entityLiving, p -> p.sendToolBreakStatus(entityLiving.getActiveHand()));

					worldIn.spawnEntity(abstractarrowentity1);
					worldIn.spawnEntity(abstractarrowentity2);
					worldIn.spawnEntity(abstractarrowentity);
					worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(),
							playerentity.getZ(), DoomSounds.UNMAKYR_FIRE, SoundCategory.PLAYERS, 1.0F,
							1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
					triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerWorld) worldIn), "shoot_controller",
							"firing");
				}
				boolean isInsideWaterBlock = playerentity.world.isWater(playerentity.getBlockPos());
				spawnLightSource(entityLiving, isInsideWaterBlock);
			}
		}
	}

	public static float getArrowVelocity(int charge) {
		return 1.0F;
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof Unmaker) {
			while (!user.isCreative() && user.getStackInHand(hand).getDamage() != 0
					&& user.getInventory().count(DoomItems.UNMAKRY_BOLT) > 0) {
				removeAmmo(DoomItems.UNMAKRY_BOLT, user);
				user.getStackInHand(hand).damage(-20, user, s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setBobbingAnimationTime(3);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (world.isClient) {
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof Unmaker && ClientInit.reload.isPressed()
					&& selected) {
				PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
				passedData.writeBoolean(true);
				ClientPlayNetworking.send(DoomMod.UNMAYKR, passedData);
			}
		}
	}

	public UnmaykrBoltEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		float j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
		UnmaykrBoltEntity arrowentity = new UnmaykrBoltEntity(worldIn, shooter,
				(DoomConfig.unmaykr_damage + (j * 2.0F)));
		return arrowentity;
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final UnmakerRender renderer = new UnmakerRender();

			@Override
			public BuiltinModelItemRenderer getCustomRenderer() {
				return this.renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return this.renderProvider;
	}
}