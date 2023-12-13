package mod.azure.doom.client.render.mobs.superheavy;

import mod.azure.doom.client.models.mobs.superheavy.ArchvileModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tiersuperheavy.ArchvileEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class ArchvileRender extends DoomMobRender<ArchvileEntity> {

    public ArchvileRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ArchvileModel());
    }

}