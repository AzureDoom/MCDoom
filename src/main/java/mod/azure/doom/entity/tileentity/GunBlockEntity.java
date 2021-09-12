package mod.azure.doom.entity.tileentity;

import mod.azure.doom.client.gui.weapons.GunTableScreenHandler;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GunBlockEntity extends BlockEntity implements ImplementedInventory, MenuProvider, IAnimatable {

	private final AnimationFactory factory = new AnimationFactory(this);

	private <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	private final NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);

	public GunBlockEntity() {
		super(ModEntityTypes.GUN_TABLE_ENTITY.get());
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<GunBlockEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}

	@Override
	public AbstractContainerMenu createMenu(int syncId, Inventory inventory, Player player) {
		return new GunTableScreenHandler(syncId, inventory, ContainerLevelAccess.create(level, worldPosition));
	}

	@Override
	public Component getDisplayName() {
		return new TranslatableComponent("block.doom.gun_table");
	}

	@Override
	public NonNullList<ItemStack> getItems() {
		return items;
	}

	@Override
	public void load(BlockState state, CompoundTag tag) {
		super.load(state, tag);
		ContainerHelper.loadAllItems(tag, items);
	}

	@Override
	public CompoundTag save(CompoundTag tag) {
		ContainerHelper.saveAllItems(tag, items);
		return super.save(tag);
	}

}