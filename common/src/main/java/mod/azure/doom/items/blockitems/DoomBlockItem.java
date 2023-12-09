package mod.azure.doom.items.blockitems;

import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.client.render.item.GunCraftingItemRender;
import mod.azure.doom.client.render.item.TotemItemRender;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class DoomBlockItem extends BlockItem implements GeoItem {
    private String id;

    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    protected DoomBlockItem(Block block, Properties properties, String id) {
        super(block, properties);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "popup_controller", 0, state -> PlayState.CONTINUE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private GeoItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    if (getId().equalsIgnoreCase("gun_table"))
                        renderer = new GunCraftingItemRender();
                    if (getId().equalsIgnoreCase("totem"))
                        renderer = new TotemItemRender();
                }

                return renderer;
            }
        });
    }

}
