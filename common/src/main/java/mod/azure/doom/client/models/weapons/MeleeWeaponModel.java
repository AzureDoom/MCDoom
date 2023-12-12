package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.enums.MeleeWeaponEnum;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class MeleeWeaponModel<T extends Item & GeoItem> extends GeoModel<T> {
    private final MeleeWeaponEnum meleeWeaponEnum;

    public MeleeWeaponModel(MeleeWeaponEnum meleeWeaponEnum) {
        super();
        this.meleeWeaponEnum = meleeWeaponEnum;
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        switch (meleeWeaponEnum) {
            case CHAINSAW -> {
                return MCDoom.modResource("geo/chainsaw.geo.json");
            }
            case CHAINSAW_64 -> {
                return MCDoom.modResource("geo/chainsaw64.geo.json");
            }
            case ETERNAL_CHAINSAW -> {
                return MCDoom.modResource("geo/chainsaw_eternal.geo.json");
            }
            case MARAUDER_AXE -> {
                return MCDoom.modResource("geo/marauderaxe.geo.json");
            }
            case DARK_CRUCIBLE -> {
                return MCDoom.modResource("geo/darklordcrucible.geo.json");
            }
            case CRUCIBLE -> {
                return MCDoom.modResource("geo/cruciblesword.geo.json");
            }
            default -> {
                return MCDoom.modResource("geo/sentinelhammer.geo.json");
            }
        }
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        switch (meleeWeaponEnum) {
            case CHAINSAW -> {
                return MCDoom.modResource("textures/item/chainsaw.png");
            }
            case CHAINSAW_64 -> {
                return MCDoom.modResource("textures/item/chainsawsixyfour.png");
            }
            case ETERNAL_CHAINSAW -> {
                return MCDoom.modResource("textures/item/chainsaweternal.png");
            }
            case MARAUDER_AXE -> {
                return MCDoom.modResource("textures/item/marauderaxe.png");
            }
            case DARK_CRUCIBLE -> {
                return MCDoom.modResource("textures/item/darklordcrucible.png");
            }
            case CRUCIBLE -> {
                return MCDoom.modResource("textures/item/crucible.png");
            }
            default -> {
                return MCDoom.modResource("textures/item/sentinel_hammer.png");
            }
        }
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        switch (meleeWeaponEnum) {
            case CHAINSAW, CHAINSAW_64, MARAUDER_AXE -> {
                return MCDoom.modResource("animations/empty.animation.json");
            }
            case ETERNAL_CHAINSAW -> {
                return MCDoom.modResource("animations/chainsaw.animation.json");
            }
            case DARK_CRUCIBLE, CRUCIBLE -> {
                return MCDoom.modResource("animations/cruciblesword.animation.json");
            }
            default -> {
                return MCDoom.modResource("animations/sentinelhammer.animation.json");
            }
        }
    }
}
