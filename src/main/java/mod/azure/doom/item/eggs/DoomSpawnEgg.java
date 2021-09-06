package mod.azure.doom.item.eggs;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import mod.azure.doom.DoomMod;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;

public class DoomSpawnEgg extends SpawnEggItem {
	private Supplier<? extends EntityType<?>> typeGetter;

	public DoomSpawnEgg(Supplier<? extends EntityType<?>> typeIn) {
		super(null, 11022961, 11035249, new Item.Properties().stacksTo(1).tab(DoomMod.DoomEggItemGroup));
		typeGetter = typeIn;
		// Have to manually add dispenser behavior due to forge item registry event
		// running too late.
		DispenserBlock.registerBehavior(this, new DefaultDispenseItemBehavior() {
			public ItemStack execute(IBlockSource source, ItemStack stack) {
				Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
				EntityType<?> entitytype = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
				entitytype.spawn(source.getLevel(), stack, null, source.getPos().relative(direction),
						SpawnReason.DISPENSER, direction != Direction.UP, false);
				stack.shrink(1);
				return stack;
			}
		});
	}

	@Override
	public EntityType<?> getType(@Nullable CompoundNBT p_208076_1_) {
		return typeGetter.get();
	}

}