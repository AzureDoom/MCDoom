package mod.azure.doom.client.render.weapons;

import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.azurelib.renderer.layer.AutoGlowingGeoLayer;
import mod.azure.doom.client.models.weapons.MeleeWeaponModel;
import mod.azure.doom.items.enums.MeleeWeaponEnum;
import net.minecraft.world.item.Item;

public class MeleeRender<T extends Item & GeoItem> extends GeoItemRenderer<T> {
    private final MeleeWeaponEnum meleeWeaponEnum;

    public MeleeRender(MeleeWeaponEnum meleeWeaponEnum) {
        super(new MeleeWeaponModel<>(meleeWeaponEnum));
        this.meleeWeaponEnum = meleeWeaponEnum;
        if (meleeWeaponEnum == MeleeWeaponEnum.MARAUDER_AXE || meleeWeaponEnum == MeleeWeaponEnum.SENTINEL_HAMMER || meleeWeaponEnum == MeleeWeaponEnum.CRUCIBLE || meleeWeaponEnum == MeleeWeaponEnum.DARK_CRUCIBLE)
            addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
