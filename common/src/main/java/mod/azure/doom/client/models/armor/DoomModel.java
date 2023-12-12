package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.armor.DoomArmor;
import mod.azure.doom.items.enums.ArmorTypeEnum;
import net.minecraft.resources.ResourceLocation;

public class DoomModel<T extends DoomArmor> extends GeoModel<T> {

    protected final ArmorTypeEnum armorTypeEnum;

    public DoomModel(ArmorTypeEnum armorTypeEnum) {
        this.armorTypeEnum = armorTypeEnum;
    }

    @Override
    public ResourceLocation getModelResource(T object) {
        switch (armorTypeEnum) {
            case ASTRO, DOOM, BRONZE, CRIMSON, DEMONCIDE, GOLD, HOTROD, PHOBOS, PRAETOR, TWENTYFIVE -> {
                return MCDoom.modResource("geo/doomarmor.geo.json");
            }
            case CLASSIC_GREEN, CLASSIC_RED, CLASSIC_INDIGO, CLASSIC_BRONZE -> {
                return MCDoom.modResource("geo/classicarmor.geo.json");
            }
            case CULTIST -> {
                return MCDoom.modResource("geo/cultistarmor.geo.json");
            }
            case DARK_LORD -> {
                return MCDoom.modResource("geo/darklordarmor.geo.json");
            }
            case DEMONIC, EMBER, MIDNIGHT -> {
                return MCDoom.modResource("geo/doom1armor.geo.json");
            }
            case DOOMICORN, NIGHTMARE, PURPLE_PONY -> {
                return MCDoom.modResource("geo/doomicornarmor.geo.json");
            }
            case MAYKR -> {
                return MCDoom.modResource("geo/maykrarmor.geo.json");
            }
            case MULLET1, MULLET2, MULLET3 -> {
                return MCDoom.modResource("geo/mulletarmor.geo.json");
            }
            case PAINTER -> {
                return MCDoom.modResource("geo/painterarmor.geo.json");
            }
            case SANTA -> {
                return MCDoom.modResource("geo/santaarmor.geo.json");
            }
            case SENTINEL -> {
                return MCDoom.modResource("geo/sentinelarmor.geo.json");
            }
            case ZOMBIE -> {
                return MCDoom.modResource("geo/zombiearmor.geo.json");
            }
        }
        return MCDoom.modResource("geo/doomarmor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        switch (armorTypeEnum) {
            case ASTRO -> {
                return MCDoom.modResource("textures/models/armor/astro_armor_layer_1.png");
            }
            case BRONZE -> {
                return MCDoom.modResource("textures/models/armor/bronze_armor_layer_1.png");
            }
            case CLASSIC_GREEN -> {
                return MCDoom.modResource("textures/models/armor/classic_armor_layer_1.png");
            }
            case CLASSIC_RED -> {
                return MCDoom.modResource("textures/models/armor/classic_red_armor_layer_1.png");
            }
            case CLASSIC_INDIGO -> {
                return MCDoom.modResource("textures/models/armor/classic_indigo_armor_layer_1.png");
            }
            case CLASSIC_BRONZE -> {
                return MCDoom.modResource("textures/models/armor/classic_bronze_armor_layer_1.png");
            }
            case CRIMSON -> {
                return MCDoom.modResource("textures/models/armor/crimson_armor_layer_1.png");
            }
            case CULTIST -> {
                return MCDoom.modResource("textures/models/armor/cultist_armor_layer_1.png");
            }
            case DARK_LORD -> {
                return MCDoom.modResource("textures/models/armor/darklordarmor.png");
            }
            case DEMONCIDE -> {
                return MCDoom.modResource("textures/models/armor/demoncide_armor_layer_1.png");
            }
            case DEMONIC -> {
                return MCDoom.modResource("textures/models/armor/demonic_armor_layer_1.png");
            }
            case DOOM -> {
                return MCDoom.modResource("textures/models/armor/doom_armor_layer_1.png");
            }
            case DOOMICORN -> {
                return MCDoom.modResource("textures/models/armor/doomicorn_armor_layer_1.png");
            }
            case EMBER -> {
                return MCDoom.modResource("textures/models/armor/ember_armor_layer_1.png");
            }
            case GOLD -> {
                return MCDoom.modResource("textures/models/armor/gold_armor_layer_1.png");
            }
            case HOTROD -> {
                return MCDoom.modResource("textures/models/armor/hotrod_armor_layer_1.png");
            }
            case MAYKR -> {
                return MCDoom.modResource("textures/models/armor/maykr_armor_layer_1.png");
            }
            case MIDNIGHT -> {
                return MCDoom.modResource("textures/models/armor/midnight_armor_layer_1.png");
            }
            case MULLET1 -> {
                return MCDoom.modResource("textures/models/armor/redneck1_armor_layer_1.png");
            }
            case MULLET2 -> {
                return MCDoom.modResource("textures/models/armor/redneck2_armor_layer_1.png");
            }
            case MULLET3 -> {
                return MCDoom.modResource("textures/models/armor/redneck3_armor_layer_1.png");
            }
            case NIGHTMARE -> {
                return MCDoom.modResource("textures/models/armor/nightmare_armor_layer_1.png");
            }
            case PAINTER -> {
                return MCDoom.modResource("textures/models/armor/painter_armor_layer_1.png");
            }
            case PHOBOS -> {
                return MCDoom.modResource("textures/models/armor/phobos_armor_layer_1.png");
            }
            case PRAETOR -> {
                return MCDoom.modResource("textures/models/armor/praetor_armor_layer_1.png");
            }
            case PURPLE_PONY -> {
                return MCDoom.modResource("textures/models/armor/purplepony_armor_layer_1.png");
            }
            case SANTA -> {
                return MCDoom.modResource("textures/models/armor/santa_armor_layer_1.png");
            }
            case SENTINEL -> {
                return MCDoom.modResource("textures/models/armor/sentinel_armor_layer_1.png");
            }
            case TWENTYFIVE -> {
                return MCDoom.modResource("textures/models/armor/twenty_five_armor_layer_1.png");
            }
            case ZOMBIE -> {
                return MCDoom.modResource("textures/models/armor/zombie_armor_layer_1.png");
            }
        }
        return MCDoom.modResource("textures/models/armor/doom_armor_layer_1.png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        switch (armorTypeEnum) {
            case DARK_LORD -> {
                return MCDoom.modResource("animations/darklordarmor.animation.json");
            }
            case DOOMICORN, NIGHTMARE, PURPLE_PONY -> {
                return MCDoom.modResource("animations/doomicorn_animation.json");
            }
            default -> {
                return MCDoom.modResource("animations/armor_animation.json");
            }
        }
    }
}