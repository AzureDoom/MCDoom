package mod.azure.doom.client.render.mobs.fodder;

import mod.azure.doom.client.models.mobs.fodder.UnwillingModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierfodder.UnwillingEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class UnwillingRender extends DoomMobRender<UnwillingEntity> {

    public UnwillingRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new UnwillingModel());
    }

}