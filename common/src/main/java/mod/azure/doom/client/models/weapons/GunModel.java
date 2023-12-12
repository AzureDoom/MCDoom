package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.enums.GunTypeEnum;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class GunModel<T extends Item & GeoItem> extends GeoModel<T> {
    private final GunTypeEnum gunTypeEnum;

    public GunModel(GunTypeEnum gunTypeEnum) {
        super();
        this.gunTypeEnum = gunTypeEnum;
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        switch (gunTypeEnum) {
            case BALLISTA -> {
                return MCDoom.modResource("geo/ballista.geo.json");
            }
            case BFG -> {
                return MCDoom.modResource("geo/bfgeternal.geo.json");
            }
            case BFG9000 -> {
                return MCDoom.modResource("geo/bfg9000.geo.json");
            }
            case DGAUSS -> {
                return MCDoom.modResource("geo/doomed_gauss_cannon.geo.json");
            }
            case DPLASMA -> {
                return MCDoom.modResource("geo/doomed_plasma_rifle.geo.json");
            }
            case DSHOTGUN -> {
                return MCDoom.modResource("geo/doomed_shotgun.geo.json");
            }
            case HEAVYCANNON -> {
                return MCDoom.modResource("geo/heavycannon.geo.json");
            }
            case PISTOL -> {
                return MCDoom.modResource("geo/pistol.geo.json");
            }
            case PLAMSA -> {
                return MCDoom.modResource("geo/plasmagun.geo.json");
            }
            case ROCKETLAUNCHER -> {
                return MCDoom.modResource("geo/rocketlauncher.geo.json");
            }
            case SHOTGUN -> {
                return MCDoom.modResource("geo/shotgun.geo.json");
            }
            case SUPERSHOTGUN -> {
                return MCDoom.modResource("geo/supershotgun.geo.json");
            }
            case UNMAKER, UNMAYKR -> {
                return MCDoom.modResource("geo/unmaykr.geo.json");
            }
            default -> {
                return MCDoom.modResource("geo/chaingun.geo.json");
            }
        }
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        switch (gunTypeEnum) {
            case BALLISTA -> {
                return MCDoom.modResource("textures/item/ballista.png");
            }
            case BFG -> {
                return MCDoom.modResource("textures/item/bfgeternal.png");
            }
            case BFG9000 -> {
                return MCDoom.modResource("textures/item/bfg9000.png");
            }
            case DGAUSS -> {
                return MCDoom.modResource("textures/item/doomed_gauss_cannon.png");
            }
            case DPLASMA -> {
                return MCDoom.modResource("textures/item/doomed_plasma_rifle.png");
            }
            case DSHOTGUN -> {
                return MCDoom.modResource("textures/item/doomed_shotgun.png");
            }
            case HEAVYCANNON -> {
                return MCDoom.modResource("textures/item/heavycannon.png");
            }
            case PISTOL -> {
                return MCDoom.modResource("textures/item/pistol.png");
            }
            case PLAMSA -> {
                return MCDoom.modResource("textures/item/rifle.png");
            }
            case ROCKETLAUNCHER -> {
                return MCDoom.modResource("textures/item/rocketlauncher.png");
            }
            case SHOTGUN -> {
                return MCDoom.modResource("textures/item/shotgun.png");
            }
            case SUPERSHOTGUN -> {
                return MCDoom.modResource("textures/item/supershotgun.png");
            }
            case UNMAKER -> {
                return MCDoom.modResource("textures/item/unmaker.png");
            }
            case UNMAYKR -> {
                return MCDoom.modResource("textures/item/unmaykr.png");
            }
            default -> {
                return MCDoom.modResource("textures/item/chainguneternal.png");
            }
        }
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        switch (gunTypeEnum) {
            case BALLISTA -> {
                return MCDoom.modResource("animations/ballista.animation.json");
            }
            case BFG -> {
                return MCDoom.modResource("animations/bfg.animation.json");
            }
            case BFG9000 -> {
                return MCDoom.modResource("animations/bfg9000.animation.json");
            }
            case DGAUSS -> {
                return MCDoom.modResource("animations/doomed_gauss_cannon.animation.json");
            }
            case DPLASMA -> {
                return MCDoom.modResource("animations/doomed_plasma_rifle.animation.json");
            }
            case DSHOTGUN -> {
                return MCDoom.modResource("animations/doomed_shotgun.animation.json");
            }
            case HEAVYCANNON -> {
                return MCDoom.modResource("animations/heavycannon.animation.json");
            }
            case PISTOL -> {
                return MCDoom.modResource("animations/pistol.animation.json");
            }
            case PLAMSA -> {
                return MCDoom.modResource("animations/plasmagun.animation.json");
            }
            case ROCKETLAUNCHER -> {
                return MCDoom.modResource("animations/rocketlauncher.animation.json");
            }
            case SHOTGUN -> {
                return MCDoom.modResource("animations/shotgun.animation.json");
            }
            case SUPERSHOTGUN -> {
                return MCDoom.modResource("animations/supershotgun.animation.json");
            }
            case UNMAKER, UNMAYKR -> {
                return MCDoom.modResource("animations/unmaykr.animation.json");
            }
            default -> {
                return MCDoom.modResource("animations/chaingun.animation.json");
            }
        }
    }
}
