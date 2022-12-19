package mod.azure.doom.item.weapons;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import io.netty.buffer.Unpooled;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.ClientInit;
import mod.azure.doom.client.render.weapons.DGaussRender;
import mod.azure.doom.entity.projectiles.ArgentBoltEntity;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;

public class DGauss extends DoomBaseItem {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public DGauss() {
		super(new Item.Settings().maxCount(1).maxDamage(11));
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return DoomTier.BALLISTA.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int remainingUseTicks) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			if (stack.getDamage() < (stack.getMaxDamage() - 1)) {
				playerentity.getItemCooldownManager().set(this, 17);
				if (!worldIn.isClient) {
					ArgentBoltEntity abstractarrowentity = createArrow(worldIn, stack, playerentity);
					abstractarrowentity.setVelocity(playerentity, playerentity.getPitch(), playerentity.getYaw(), 0.0F,
							1.0F, 1.0F);
					stack.damage(1, entityLiving, p -> p.sendToolBreakStatus(entityLiving.getActiveHand()));
					worldIn.spawnEntity(abstractarrowentity);
					worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(),
							playerentity.getZ(), DoomSounds.BALLISTA_FIRING, SoundCategory.PLAYERS, 1.0F,
							1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 1F * 0.5F);
					Vec3d vec3d = playerentity.getVelocity();
					playerentity.setVelocity(vec3d.x, vec3d.y * 3, vec3d.z);
					playerentity.velocityDirty = true;
					triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerWorld) worldIn), "shoot_controller",
							"firing");
				}
				boolean isInsideWaterBlock = playerentity.world.isWater(playerentity.getBlockPos());
				spawnLightSource(entityLiving, isInsideWaterBlock);
			} else {
				worldIn.playSound((PlayerEntity) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(),
						DoomSounds.EMPTY, SoundCategory.PLAYERS, 1.0F, 1.5F);
			}
		}
	}

	public static float getArrowVelocity(int charge) {
		float f = charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		user.setCurrentHand(hand);
		return TypedActionResult.consume(itemStack);
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof DGauss) {
			while (!user.isCreative() && user.getStackInHand(hand).getDamage() != 0
					&& user.getInventory().count(DoomItems.ARGENT_BOLT) > 0) {
				removeAmmo(DoomItems.ARGENT_BOLT, user);
				user.getStackInHand(hand).damage(-1, user, s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setBobbingAnimationTime(3);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (world.isClient) {
			if (((PlayerEntity) entity).getMainHandStack().getItem() instanceof DGauss && ClientInit.reload.isPressed()
					&& selected) {
				PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
				passedData.writeBoolean(true);
				ClientPlayNetworking.send(DoomMod.DGAUSS, passedData);
			}
		}
	}

	public ArgentBoltEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
		ArgentBoltEntity arrowentity = new ArgentBoltEntity(worldIn, shooter);
		return arrowentity;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		tooltip.add(
				Text.translatable("doom.doomed_credit.text").formatted(Formatting.RED).formatted(Formatting.ITALIC));
		tooltip.add(
				Text.translatable("doom.doomed_credit1.text").formatted(Formatting.RED).formatted(Formatting.ITALIC));
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final DGaussRender renderer = new DGaussRender();

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