package mod.azure.doom.item.weapons;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import io.netty.buffer.Unpooled;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.ClientInit;
import mod.azure.doom.client.render.weapons.SentinelHammerRender;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.registry.DoomItems;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SentinelHammerItem extends SwordItem implements GeoItem {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public SentinelHammerItem() {
		super(DoomTier.DOOM_HIGHTEIR, 1, -2.5f, new Item.Settings().maxCount(1).maxDamage(24));
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text
				.translatable(
						"Ammo: " + (stack.getMaxDamage() - stack.getDamage() - 1) + " / " + (stack.getMaxDamage() - 1))
				.formatted(Formatting.ITALIC));
		super.appendTooltip(stack, world, tooltip, context);
	}

	private void doDamage(LivingEntity user, Entity target) {
		if (target instanceof LivingEntity) {
			target.timeUntilRegen = 0;
			((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1000, 2));
			target.damage(DamageSource.player((PlayerEntity) user), 25F);
		}
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "popup_controller", 0, state -> PlayState.CONTINUE));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity miner) {
		if (miner instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) miner;
			if (stack.getDamage() < (stack.getMaxDamage() - 1)) {
				if (playerentity.getMainHandStack().getItem() instanceof SentinelHammerItem) {
					final Box aabb = new Box(miner.getBlockPos().up()).expand(5D, 5D, 5D);
					miner.getEntityWorld().getOtherEntities(miner, aabb).forEach(e -> doDamage(playerentity, e));
					stack.damage(1, miner, p -> p.sendToolBreakStatus(playerentity.getActiveHand()));
					AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(miner.world, miner.getX(),
							playerentity.getY(), playerentity.getZ());
					areaeffectcloudentity.setParticleType(ParticleTypes.CRIT);
					areaeffectcloudentity.setRadius(5.0F);
					areaeffectcloudentity.setDuration(20);
					areaeffectcloudentity.updatePosition(playerentity.getX(), playerentity.getY(), playerentity.getZ());
					playerentity.world.spawnEntity(areaeffectcloudentity);
				}
			}
		}
		return stack.getDamage() < (stack.getMaxDamage() - 1) ? true : false;
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		PlayerEntity playerentity = (PlayerEntity) entity;
		if (world.isClient) {
			if (playerentity.getMainHandStack().getItem() instanceof SentinelHammerItem && ClientInit.reload.isPressed()
					&& selected) {
				PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
				passedData.writeBoolean(true);
				ClientPlayNetworking.send(DoomMod.SENTINELHAMMER, passedData);
			}
		}
	}

	public void reload(PlayerEntity user, Hand hand) {
		if (user.getStackInHand(hand).getItem() instanceof SentinelHammerItem) {
			while (!user.isCreative() && user.getStackInHand(hand).getDamage() != 0
					&& user.getInventory().count(DoomItems.ARGENT_ENERGY.asItem()) > 0) {
				removeAmmo(DoomItems.ARGENT_ENERGY.asItem(), user);
				user.getStackInHand(hand).damage(-5, user, s -> user.sendToolBreakStatus(hand));
				user.getStackInHand(hand).setBobbingAnimationTime(3);
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

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final SentinelHammerRender renderer = new SentinelHammerRender();

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