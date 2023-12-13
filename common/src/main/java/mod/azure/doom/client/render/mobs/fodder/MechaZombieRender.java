package mod.azure.doom.client.render.mobs.fodder;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.fodder.MechaZombieModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierfodder.MechaZombieEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class MechaZombieRender extends DoomMobRender<MechaZombieEntity> {

    public MechaZombieRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new MechaZombieModel());
    }

}