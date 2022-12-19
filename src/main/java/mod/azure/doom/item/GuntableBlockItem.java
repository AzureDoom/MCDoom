package mod.azure.doom.item;

import java.util.function.Consumer;
import java.util.function.Supplier;

import mod.azure.doom.client.render.item.GunCraftingItemRender;
import net.minecraft.block.Block;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;

public class GuntableBlockItem extends DoomBlockItem {
	
	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public GuntableBlockItem(Block p_i48527_1_, Settings p_i48527_2_) {
		super(p_i48527_1_, p_i48527_2_);
	}
	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private GunCraftingItemRender renderer;

			@Override
			public BuiltinModelItemRenderer getCustomRenderer() {
				if (this.renderer == null)
					this.renderer = new GunCraftingItemRender();

				return this.renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return this.renderProvider;
	}

}
