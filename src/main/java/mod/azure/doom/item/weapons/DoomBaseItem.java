package mod.azure.doom.item.weapons;

import java.util.List;

import mod.azure.doom.entity.tileentity.TickingLightEntity;
import mod.azure.doom.util.registry.DoomBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3q.network.GeckoLibNetwork;
import software.bernie.geckolib3q.network.ISyncable;
import software.bernie.geckolib3q.util.GeckoLibUtil;

public class DoomBaseItem extends Item implements IAnimatable, ISyncable {

	public AnimationFactory factory = new AnimationFactory(this);
	public String controllerName = "controller";
	public static final int ANIM_OPEN = 0;
	public static final int ANIM_OPEN_FASTER = 2;
	public static final int ANIM_HOOK = 1;
	private BlockPos lightBlockPos = null;

	public <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		return PlayState.CONTINUE;
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
				controller.setAnimation(new AnimationBuilder().addAnimation("firing", false));
			}
		}
		if (state == ANIM_OPEN_FASTER) {
			final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, controllerName);
			if (controller.getAnimationState() == AnimationState.Stopped) {
				controller.markNeedsReload();
				controller.setAnimationSpeed(2);
				controller.setAnimation(new AnimationBuilder().addAnimation("firing", false));
			}
		}
		if (state == ANIM_HOOK) {
			final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, controllerName);
			if (controller.getAnimationState() == AnimationState.Stopped) {
				controller.markNeedsReload();
				controller.setAnimation(new AnimationBuilder().addAnimation("hook", false));
			}
		}
	}

	public DoomBaseItem(Settings properties) {
		super(properties);
		GeckoLibNetwork.registerSyncable(this);
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
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		user.setCurrentHand(hand);
		return TypedActionResult.consume(itemStack);
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
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText(
				"Ammo: " + (stack.getMaxDamage() - stack.getDamage() - 1) + " / " + (stack.getMaxDamage() - 1))
						.formatted(Formatting.ITALIC));
	}

	protected void spawnLightSource(Entity entity, boolean isInWaterBlock) {
		if (lightBlockPos == null) {
			lightBlockPos = findFreeSpace(entity.world, entity.getBlockPos(), 2);
			if (lightBlockPos == null)
				return;
			entity.world.setBlockState(lightBlockPos, DoomBlocks.TICKING_LIGHT_BLOCK.getDefaultState());
		} else if (checkDistance(lightBlockPos, entity.getBlockPos(), 2)) {
			BlockEntity blockEntity = entity.world.getBlockEntity(lightBlockPos);
			if (blockEntity instanceof TickingLightEntity) {
				((TickingLightEntity) blockEntity).refresh(isInWaterBlock ? 20 : 0);
			} else
				lightBlockPos = null;
		} else
			lightBlockPos = null;
	}

	private boolean checkDistance(BlockPos blockPosA, BlockPos blockPosB, int distance) {
		return Math.abs(blockPosA.getX() - blockPosB.getX()) <= distance
				&& Math.abs(blockPosA.getY() - blockPosB.getY()) <= distance
				&& Math.abs(blockPosA.getZ() - blockPosB.getZ()) <= distance;
	}

	private BlockPos findFreeSpace(World world, BlockPos blockPos, int maxDistance) {
		if (blockPos == null)
			return null;

		int[] offsets = new int[maxDistance * 2 + 1];
		offsets[0] = 0;
		for (int i = 2; i <= maxDistance * 2; i += 2) {
			offsets[i - 1] = i / 2;
			offsets[i] = -i / 2;
		}
		for (int x : offsets)
			for (int y : offsets)
				for (int z : offsets) {
					BlockPos offsetPos = blockPos.add(x, y, z);
					BlockState state = world.getBlockState(offsetPos);
					if (state.isAir() || state.getBlock().equals(DoomBlocks.TICKING_LIGHT_BLOCK))
						return offsetPos;
				}

		return null;
	}

}