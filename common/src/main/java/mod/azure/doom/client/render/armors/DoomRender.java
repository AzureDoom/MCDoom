package mod.azure.doom.client.render.armors;

import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoArmorRenderer;
import mod.azure.doom.client.models.armor.DoomModel;
import mod.azure.doom.items.armor.ArmorTypeEnum;
import mod.azure.doom.items.armor.DoomArmor;

public class DoomRender<T extends DoomArmor> extends GeoArmorRenderer<T> {
    public DoomRender(ArmorTypeEnum armorTypeEnum) {
        super(new DoomModel<>(armorTypeEnum));
    }

    @Override
    public GeoBone getLeftBootBone() {
        return this.model.getBone("armorRightBoot").orElse(null);
    }

    @Override
    public GeoBone getLeftLegBone() {
        return this.model.getBone("armorRightLeg").orElse(null);
    }

    @Override
    public GeoBone getRightBootBone() {
        return this.model.getBone("armorLeftBoot").orElse(null);
    }

    @Override
    public GeoBone getRightLegBone() {
        return this.model.getBone("armorLeftLeg").orElse(null);
    }
}