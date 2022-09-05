package mod.azure.doom.item.eggs;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import mod.azure.doom.DoomMod;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.DispenserBlock;

public class DoomSpawnEgg extends SpawnEggItem {
	private Supplier<? extends EntityType<?>> typeGetter;

	public DoomSpawnEgg(Supplier<? extends EntityType<?>> typeIn) {
		super(null, 11022961, 11035249, new Item.Properties().stacksTo(64).tab(DoomMod.DoomEggItemGroup));
		typeGetter = typeIn;
		// Have to manually add dispenser behavior due to forge item registry event
		// running too late.
		DispenserBlock.registerBehavior(this, new DefaultDispenseItemBehavior() {
			public ItemStack execute(BlockSource source, ItemStack stack) {
				Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
				EntityType<?> entitytype = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
				entitytype.spawn(source.getLevel(), stack, null, source.getPos().relative(direction),
						MobSpawnType.DISPENSER, direction != Direction.UP, false);
				stack.shrink(1);
				return stack;
			}
		});
	}

	@Override
	public EntityType<?> getType(@Nullable CompoundTag p_208076_1_) {
		return typeGetter.get();
	}
}