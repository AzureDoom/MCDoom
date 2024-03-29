package mod.azure.doom.registry;

import mod.azure.azurelib.items.AzureSpawnEgg;
import mod.azure.doom.MCDoom;
import mod.azure.doom.items.E1M1MusicDisc;
import mod.azure.doom.items.UnopenedItem;
import mod.azure.doom.items.ammo.AmmoItem;
import mod.azure.doom.items.argent.ArgentEnergyItem;
import mod.azure.doom.items.argent.ArgentPlateItem;
import mod.azure.doom.items.armor.DoomArmor;
import mod.azure.doom.items.enums.*;
import mod.azure.doom.items.powerup.*;
import mod.azure.doom.items.tools.*;
import mod.azure.doom.items.weapons.ArgentSword;
import mod.azure.doom.items.weapons.BaseSwordItem;
import mod.azure.doom.items.weapons.DoomBaseItem;
import mod.azure.doom.items.weapons.GrenadeItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public record FabricDoomItems() {

    public static DoomBaseItem SG;
    public static DoomBaseItem BFG;
    public static DoomBaseItem BFG_ETERNAL;
    public static DaisyItem DAISY;
    public static AmmoItem ROCKET;
    public static DoomBaseItem DGAUSS;
    public static DoomBaseItem BALLISTA;
    public static DoomBaseItem CHAINGUN;
    public static BaseSwordItem CHAINSAW;
    public static DoomBaseItem PISTOL;
    public static DoomBaseItem DSG;
    public static AmmoItem BULLETS;
    public static AmmoItem BFG_CELL;
    public static DoomBaseItem UNMAYKR;
    public static DoomBaseItem UNMAKER;
    public static BaseSwordItem CHAINSAW64;
    public static DoomBaseItem PLASMAGUN;
    public static ArgentAxe ARGENT_AXE;
    public static ArgentHoe ARGENT_HOE;
    public static DoomBaseItem SSG;
    public static SoulCubeItem SOULCUBE;
    public static MegaSphereItem MEGA;
    public static GrenadeItem GRENADE;
    public static DoomBaseItem HEAVYCANNON;
    public static Item ICON_ICON;
    public static AmmoItem ARGENT_BOLT;
    public static ArgentPaxel ARGENT_PAXEL;
    public static ArgentSword ARGENT_SWORD;
    public static PowerSphereItem POWER;
    public static Item GAS_BARREL;
    public static AmmoItem ENERGY_CELLS;
    public static ArgentShovel ARGENT_SHOVEL;
    public static AmmoItem SHOTGUN_SHELLS;
    public static AmmoItem UNMAKRY_BOLT;
    public static UnopenedItem AXE_CLOSED;
    public static ArgentPickaxe ARGENT_PICKAXE;
    public static ArgentPlateItem ARGENT_PLATE;
    public static BaseSwordItem AXE_OPEN;
    public static DoomBaseItem DPLASMARIFLE;
    public static UnopenedItem SWORD_CLOSED;
    public static DoomBaseItem ROCKETLAUNCHER;
    public static ArgentEnergyItem ARGENT_ENERGY;
    public static InmortalSphereItem INMORTAL;
    public static AmmoItem CHAINGUN_BULLETS;
    public static BaseSwordItem CRUCIBLESWORD;
    public static InvisibleSphereItem INVISIBLE;
    public static BaseSwordItem CHAINSAW_ETERNAL;
    public static BaseSwordItem SENTINELHAMMER;
    public static BaseSwordItem DARKLORDCRUCIBLE;
    public static E1M1MusicDisc E1M1_MUSIC_DISC;
    public static E1M1MusicDisc GEOF_MUSIC_DISC;

    // Spawn Eggs
    public static AzureSpawnEgg ARACHNOTRON_SPAWN_EGG;
    public static AzureSpawnEgg IMP_SPAWN_EGG;
    public static AzureSpawnEgg PINKY_SPAWN_EGG;
    public static AzureSpawnEgg ARCHVILE_SPAWN_EGG;
    public static AzureSpawnEgg BARON_SPAWN_EGG;
    public static AzureSpawnEgg CACODEMON_SPAWN_EGG;
    public static AzureSpawnEgg MANCUBUS_SPAWN_EGG;
    public static AzureSpawnEgg LOST_SOUL_SPAWN_EGG;
    public static AzureSpawnEgg LOST_SOUL_ETERNAL_SPAWN_EGG;
    public static AzureSpawnEgg SPIDERMASTERMIND_SPAWN_EGG;
    public static AzureSpawnEgg ZOMBIEMAN_SPAWN_EGG;
    public static AzureSpawnEgg CHAINGUNNER_SPAWN_EGG;
    public static AzureSpawnEgg HELLKNIGHT_SPAWN_EGG;
    public static AzureSpawnEgg MARAUDER_SPAWN_EGG;
    public static AzureSpawnEgg PAIN_SPAWN_EGG;
    public static AzureSpawnEgg REVENANT_SPAWN_EGG;
    public static AzureSpawnEgg SHOTGUNGUY_SPAWN_EGG;
    public static AzureSpawnEgg CYBERDEMON_SPAWN_EGG;
    public static AzureSpawnEgg ICON_SPAWN_EGG;
    public static AzureSpawnEgg UNWILLING_SPAWN_EGG;
    public static AzureSpawnEgg POSSESSED_SCIENTIST_SPAWN_EGG;
    public static AzureSpawnEgg POSSESSED_SOLDIER_SPAWN_EGG;
    public static AzureSpawnEgg GORE_NEST_SPAWN_EGG;
    public static AzureSpawnEgg MECH_ZOMBIE_SPAWN_EGG;
    public static AzureSpawnEgg HELLKNIGHT2016_SPAWN_EGG;
    public static AzureSpawnEgg GARGOYLE_SPAWN_EGG;
    public static AzureSpawnEgg SPECTRE_SPAWN_EGG;
    public static AzureSpawnEgg CUEBALL_SPAWN_EGG;
    public static AzureSpawnEgg PROWLER_SPAWN_EGG;
    public static AzureSpawnEgg DREADKNIGHT_SPAWN_EGG;
    public static AzureSpawnEgg STONEIMP_SPAWN_EGG;
    public static AzureSpawnEgg POSSESSED_WORKER_SPAWN_EGG;
    public static AzureSpawnEgg DOOMHUNTER_SPAWN_EGG;
    public static AzureSpawnEgg WHIPLASH_SPAWN_EGG;
    public static AzureSpawnEgg BARON2016_SPAWN_EGG;
    public static AzureSpawnEgg FIREBORNE_BARON_SPAWN_EGG;
    public static AzureSpawnEgg ARMORED_BARON_SPAWN_EGG;
    public static AzureSpawnEgg MAYKR_DRONE_SPAWN_EGG;
    public static AzureSpawnEgg BLOOD_MAYKR_SPAWN_EGG;
    public static AzureSpawnEgg ARCH_MAKYR_SPAWN_EGG;
    public static AzureSpawnEgg ARACHNOTRONETERNAL_SPAWN_EGG;
    public static AzureSpawnEgg SPIDERMASTERMIND2016_SPAWN_EGG;
    public static AzureSpawnEgg TENTACLE_SPAWN_EGG;
    public static AzureSpawnEgg MOTHERDEMON_SPAWN_EGG;
    public static AzureSpawnEgg TURRET_SPAWN_EGG;
    public static AzureSpawnEgg SUMMONER_SPAWN_EGG;
    public static AzureSpawnEgg REVENANT2016_SPAWN_EGG;
    public static AzureSpawnEgg GLADIATOR_SPAWN_EGG;
    public static AzureSpawnEgg CARCASS_SPAWN_EGG;

    // Armor
    public static DoomArmor DOOM_HELMET;
    public static DoomArmor DOOM_CHESTPLATE;
    public static DoomArmor DOOM_LEGGINGS;
    public static DoomArmor DOOM_BOOTS;
    public static DoomArmor PRAETOR_DOOM_HELMET;
    public static DoomArmor PRAETOR_DOOM_CHESTPLATE;
    public static DoomArmor PRAETOR_DOOM_LEGGINGS;
    public static DoomArmor PRAETOR_DOOM_BOOTS;
    public static DoomArmor ASTRO_DOOM_HELMET;
    public static DoomArmor ASTRO_DOOM_CHESTPLATE;
    public static DoomArmor ASTRO_DOOM_LEGGINGS;
    public static DoomArmor ASTRO_DOOM_BOOTS;
    public static DoomArmor CRIMSON_DOOM_HELMET;
    public static DoomArmor CRIMSON_DOOM_CHESTPLATE;
    public static DoomArmor CRIMSON_DOOM_LEGGINGS;
    public static DoomArmor CRIMSON_DOOM_BOOTS;
    public static DoomArmor MIDNIGHT_DOOM_HELMET;
    public static DoomArmor MIDNIGHT_DOOM_CHESTPLATE;
    public static DoomArmor MIDNIGHT_DOOM_LEGGINGS;
    public static DoomArmor MIDNIGHT_DOOM_BOOTS;
    public static DoomArmor DEMONIC_DOOM_HELMET;
    public static DoomArmor DEMONIC_DOOM_CHESTPLATE;
    public static DoomArmor DEMONIC_DOOM_LEGGINGS;
    public static DoomArmor DEMONIC_DOOM_BOOTS;
    public static DoomArmor DEMONCIDE_DOOM_HELMET;
    public static DoomArmor DEMONCIDE_DOOM_CHESTPLATE;
    public static DoomArmor DEMONCIDE_DOOM_LEGGINGS;
    public static DoomArmor DEMONCIDE_DOOM_BOOTS;
    public static DoomArmor SENTINEL_DOOM_HELMET;
    public static DoomArmor SENTINEL_DOOM_CHESTPLATE;
    public static DoomArmor SENTINEL_DOOM_LEGGINGS;
    public static DoomArmor SENTINEL_DOOM_BOOTS;
    public static DoomArmor EMBER_DOOM_HELMET;
    public static DoomArmor EMBER_DOOM_CHESTPLATE;
    public static DoomArmor EMBER_DOOM_LEGGINGS;
    public static DoomArmor EMBER_DOOM_BOOTS;
    public static DoomArmor ZOMBIE_DOOM_HELMET;
    public static DoomArmor ZOMBIE_DOOM_CHESTPLATE;
    public static DoomArmor ZOMBIE_DOOM_LEGGINGS;
    public static DoomArmor ZOMBIE_DOOM_BOOTS;
    public static DoomArmor PHOBOS_DOOM_HELMET;
    public static DoomArmor PHOBOS_DOOM_CHESTPLATE;
    public static DoomArmor PHOBOS_DOOM_LEGGINGS;
    public static DoomArmor PHOBOS_DOOM_BOOTS;
    public static DoomArmor NIGHTMARE_DOOM_HELMET;
    public static DoomArmor NIGHTMARE_DOOM_CHESTPLATE;
    public static DoomArmor NIGHTMARE_DOOM_LEGGINGS;
    public static DoomArmor NIGHTMARE_DOOM_BOOTS;
    public static DoomArmor PURPLEPONY_DOOM_HELMET;
    public static DoomArmor PURPLEPONY_DOOM_CHESTPLATE;
    public static DoomArmor PURPLEPONY_DOOM_LEGGINGS;
    public static DoomArmor PURPLEPONY_DOOM_BOOTS;
    public static DoomArmor DOOMICORN_DOOM_HELMET;
    public static DoomArmor DOOMICORN_DOOM_CHESTPLATE;
    public static DoomArmor DOOMICORN_DOOM_LEGGINGS;
    public static DoomArmor DOOMICORN_DOOM_BOOTS;
    public static DoomArmor GOLD_DOOM_HELMET;
    public static DoomArmor GOLD_DOOM_CHESTPLATE;
    public static DoomArmor GOLD_DOOM_LEGGINGS;
    public static DoomArmor GOLD_DOOM_BOOTS;
    public static DoomArmor TWENTY_FIVE_DOOM_HELMET;
    public static DoomArmor TWENTY_FIVE_DOOM_CHESTPLATE;
    public static DoomArmor TWENTY_FIVE_DOOM_LEGGINGS;
    public static DoomArmor TWENTY_FIVE_DOOM_BOOTS;
    public static DoomArmor BRONZE_DOOM_HELMET;
    public static DoomArmor BRONZE_DOOM_CHESTPLATE;
    public static DoomArmor BRONZE_DOOM_LEGGINGS;
    public static DoomArmor BRONZE_DOOM_BOOTS;
    public static DoomArmor CULTIST_DOOM_HELMET;
    public static DoomArmor CULTIST_DOOM_CHESTPLATE;
    public static DoomArmor CULTIST_DOOM_LEGGINGS;
    public static DoomArmor CULTIST_DOOM_BOOTS;
    public static DoomArmor MAYKR_DOOM_HELMET;
    public static DoomArmor MAYKR_DOOM_CHESTPLATE;
    public static DoomArmor MAYKR_DOOM_LEGGINGS;
    public static DoomArmor MAYKR_DOOM_BOOTS;
    public static DoomArmor PAINTER_DOOM_HELMET;
    public static DoomArmor PAINTER_DOOM_CHESTPLATE;
    public static DoomArmor CLASSIC_DOOM_HELMET;
    public static DoomArmor CLASSIC_DOOM_CHESTPLATE;
    public static DoomArmor CLASSIC_DOOM_LEGGINGS;
    public static DoomArmor CLASSIC_RED_DOOM_CHESTPLATE;
    public static DoomArmor CLASSIC_RED_DOOM_LEGGINGS;
    public static DoomArmor CLASSIC_INDIGO_DOOM_CHESTPLATE;
    public static DoomArmor CLASSIC_INDIGO_DOOM_LEGGINGS;
    public static DoomArmor CLASSIC_BRONZE_DOOM_CHESTPLATE;
    public static DoomArmor CLASSIC_BRONZE_DOOM_LEGGINGS;
    public static DoomArmor CLASSIC_DOOM_BOOTS;
    public static DoomArmor MULLET_DOOM_HELMET1;
    public static DoomArmor MULLET_DOOM_CHESTPLATE1;
    public static DoomArmor MULLET_DOOM_CHESTPLATE2;
    public static DoomArmor MULLET_DOOM_CHESTPLATE3;
    public static DoomArmor MULLET_DOOM_LEGGINGS1;
    public static DoomArmor MULLET_DOOM_BOOTS1;
    public static DoomArmor HOTROD_HELMET;
    public static DoomArmor HOTROD_CHESTPLATE;
    public static DoomArmor HOTROD_LEGGINGS;
    public static DoomArmor HOTROD_BOOTS;
    public static DoomArmor SANTA_HELMET;
    public static DoomArmor DARKLORD_HELMET;
    public static DoomArmor DARKLORD_CHESTPLATE;
    public static DoomArmor DARKLORD_LEGGINGS;
    public static DoomArmor DARKLORD_BOOTS;
    public static Item[] ITEMS = {CHAINSAW_ETERNAL, CRUCIBLESWORD, ROCKETLAUNCHER, AXE_OPEN, HEAVYCANNON, SSG, PLASMAGUN, CHAINSAW64, CHAINSAW, CHAINGUN, BALLISTA, UNMAYKR, BFG_ETERNAL, BFG, SG, PISTOL};

    static <T extends Item> T item(String id, T c) {
        Registry.register(BuiltInRegistries.ITEM, MCDoom.modResource(id), c);
        return c;
    }

    public static Map<Item, Item> getItemMap() {
        final Map<Item, Item> vanillaItemMap = new HashMap<>();
        for (final Item i : FabricDoomItems.ITEMS) {
            vanillaItemMap.put(
                    BuiltInRegistries.ITEM.get(MCDoom.modResource(BuiltInRegistries.ITEM.getKey(i).getPath())), i);
        }
        return vanillaItemMap;
    }

    public static void initItems() {
        SG = item("shotgun", new DoomBaseItem(GunTypeEnum.SHOTGUN, 50) {
        });
        BFG = item("bfg9000", new DoomBaseItem(GunTypeEnum.BFG9000, 400) {
        });
        PISTOL = item("pistol", new DoomBaseItem(GunTypeEnum.PISTOL, 200) {
        });
        BFG_ETERNAL = item("bfg_eternal", new DoomBaseItem(GunTypeEnum.BFG, 400) {
        });
        DGAUSS = item("doomed_gauss", new DoomBaseItem(GunTypeEnum.DGAUSS, 10) {
        });
        BALLISTA = item("ballista", new DoomBaseItem(GunTypeEnum.BALLISTA, 10) {
        });
        CHAINGUN = item("chaingun", new DoomBaseItem(GunTypeEnum.CHAINGUN, 200) {
        });
        UNMAYKR = item("unmaykr", new DoomBaseItem(GunTypeEnum.UNMAYKR, 9000) {
        });
        UNMAKER = item("unmaker", new DoomBaseItem(GunTypeEnum.UNMAKER, 9000) {
        });
        DSG = item("doomed_shotgun", new DoomBaseItem(GunTypeEnum.DSHOTGUN, 50) {
        });
        PLASMAGUN = item("plasmagun", new DoomBaseItem(GunTypeEnum.PLAMSA, 400) {
        });
        SSG = item("supershotgun", new DoomBaseItem(GunTypeEnum.SUPERSHOTGUN, 52) {
        });
        HEAVYCANNON = item("heavycannon", new DoomBaseItem(GunTypeEnum.HEAVYCANNON, 200) {
        });
        DPLASMARIFLE = item("doomed_plasma_rifle", new DoomBaseItem(GunTypeEnum.DPLASMA, 400) {
        });
        ROCKETLAUNCHER = item("rocketlauncher", new DoomBaseItem(GunTypeEnum.ROCKETLAUNCHER, 50) {
        });
        AXE_OPEN = item("axe_marauder_open",
                new BaseSwordItem(MeleeWeaponEnum.MARAUDER_AXE, MCDoom.config.marauder_max_uses) {
                });
        CHAINSAW64 = item("chainsaw64", new BaseSwordItem(MeleeWeaponEnum.CHAINSAW_64, 600) {
        });
        CHAINSAW = item("chainsaw", new BaseSwordItem(MeleeWeaponEnum.CHAINSAW, 600) {
        });
        CHAINSAW_ETERNAL = item("chainsaweternal", new BaseSwordItem(MeleeWeaponEnum.ETERNAL_CHAINSAW, 600) {
        });
        SENTINELHAMMER = item("sentinelhammer",
                new BaseSwordItem(MeleeWeaponEnum.SENTINEL_HAMMER, MCDoom.config.sentinelhammer_max_uses) {
                });
        DARKLORDCRUCIBLE = item("darklordcrucible",
                new BaseSwordItem(MeleeWeaponEnum.DARK_CRUCIBLE, MCDoom.config.darkcrucible_max_uses) {
                });
        CRUCIBLESWORD = item("cruciblesword",
                new BaseSwordItem(MeleeWeaponEnum.CRUCIBLE, MCDoom.config.crucible_max_uses) {
                });
        DAISY = item("daisy", new DaisyItem());
        ROCKET = item("rocket", new AmmoItem(AmmoEnum.ROCKET));
        BULLETS = item("bullets", new AmmoItem(AmmoEnum.CLIP));
        BFG_CELL = item("bfg_cell", new AmmoItem(AmmoEnum.BFG_CELL));
        ARGENT_HOE = item("argent_hoe", new ArgentHoe(DoomTier.DOOM));
        ARGENT_AXE = item("argent_axe", new ArgentAxe(DoomTier.DOOM));
        SOULCUBE = item("soulcube", new SoulCubeItem());
        MEGA = item("megasphere", new MegaSphereItem());
        GRENADE = item("doomed_grenade", new GrenadeItem());
        ICON_ICON = item("icon_icon", new Item(new Item.Properties()));
        ARGENT_BOLT = item("argent_bolt", new AmmoItem(AmmoEnum.ARGENT_BOLT));
        ARGENT_PAXEL = item("argent_paxel", new ArgentPaxel(DoomTier.DOOM));
        ARGENT_SWORD = item("argent_sword", new ArgentSword(DoomTier.DOOM));
        POWER = item("powersphere", new PowerSphereItem());
        GAS_BARREL = item("gas_barrel", new Item(new Item.Properties()));
        ENERGY_CELLS = item("energy_cells", new AmmoItem(AmmoEnum.ENGERY));
        ARGENT_SHOVEL = item("argent_shovel", new ArgentShovel(DoomTier.DOOM));
        SHOTGUN_SHELLS = item("shotgun_shells", new AmmoItem(AmmoEnum.SHELL));
        UNMAKRY_BOLT = item("unmaykr_bolt", new AmmoItem(AmmoEnum.UNMAYKR_BOLT));
        AXE_CLOSED = item("axe_marauder_closed", new UnopenedItem());
        ARGENT_PICKAXE = item("argent_pickaxe", new ArgentPickaxe(DoomTier.DOOM));
        ARGENT_PLATE = item("argent_plate", new ArgentPlateItem());
        SWORD_CLOSED = item("cruciblesword_closed", new UnopenedItem());
        ARGENT_ENERGY = item("argent_energy", new ArgentEnergyItem());
        INMORTAL = item("inmortalsphere", new InmortalSphereItem());
        CHAINGUN_BULLETS = item("chaingunbullets", new AmmoItem(AmmoEnum.CHAINGUN));
        INVISIBLE = item("invisiblesphere", new InvisibleSphereItem());
        E1M1_MUSIC_DISC = item("e1m1_music_disc", new E1M1MusicDisc(FabricDoomSounds.E1M1));
        GEOF_MUSIC_DISC = item("netherambient_geoffplaysguitar_music_disc",
                new E1M1MusicDisc(FabricDoomSounds.NETHERAMBIENT_GEOFFPLAYSGUITAR));
    }

    public static void initEggs() {
        ARACHNOTRON_SPAWN_EGG = item("arachnotron_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.ARACHNOTRON, 11022961, 11035249));
        IMP_SPAWN_EGG = item("imp_spawn_egg", new AzureSpawnEgg(FabricDoomEntities.IMP, 11022961, 11035249));
        PINKY_SPAWN_EGG = item("pinky_spawn_egg", new AzureSpawnEgg(FabricDoomEntities.PINKY, 11022961, 11035249));
        ARCHVILE_SPAWN_EGG = item("archvile_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.ARCHVILE, 11022961, 11035249));
        BARON_SPAWN_EGG = item("baron_spawn_egg", new AzureSpawnEgg(FabricDoomEntities.BARON, 11022961, 11035249));
        CACODEMON_SPAWN_EGG = item("cacodemon_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.CACODEMON, 11022961, 11035249));
        MANCUBUS_SPAWN_EGG = item("mancubus_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.MANCUBUS, 11022961, 11035249));
        LOST_SOUL_SPAWN_EGG = item("lost_soul_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.LOST_SOUL, 11022961, 11035249));
        LOST_SOUL_ETERNAL_SPAWN_EGG = item("lost_soul_eternal_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.LOST_SOUL_ETERNAL, 11022961, 11035249));
        SPIDERMASTERMIND_SPAWN_EGG = item("spidermastermind_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.SPIDERMASTERMIND, 11022961, 11035249));
        ZOMBIEMAN_SPAWN_EGG = item("zombieman_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.ZOMBIEMAN, 11022961, 11035249));
        CHAINGUNNER_SPAWN_EGG = item("chaingunner_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.CHAINGUNNER, 11022961, 11035249));
        HELLKNIGHT_SPAWN_EGG = item("hellknight_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.HELLKNIGHT, 11022961, 11035249));
        MARAUDER_SPAWN_EGG = item("marauder_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.MARAUDER, 11022961, 11035249));
        PAIN_SPAWN_EGG = item("pain_spawn_egg", new AzureSpawnEgg(FabricDoomEntities.PAIN, 11022961, 11035249));
        REVENANT_SPAWN_EGG = item("revenant_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.REVENANT, 11022961, 11035249));
        SHOTGUNGUY_SPAWN_EGG = item("shotgunguy_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.SHOTGUNGUY, 11022961, 11035249));
        CYBERDEMON_SPAWN_EGG = item("cyberdemon_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.CYBERDEMON, 11022961, 11035249));
        ICON_SPAWN_EGG = item("icon_of_sin_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.ICONOFSIN, 11022961, 11035249));
        UNWILLING_SPAWN_EGG = item("unwilling_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.UNWILLING, 11022961, 11035249));
        POSSESSED_SCIENTIST_SPAWN_EGG = item("possessed_scientist_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.POSSESSEDSCIENTIST, 11022961, 11035249));
        POSSESSED_SOLDIER_SPAWN_EGG = item("possessed_soldier_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.POSSESSEDSOLDIER, 11022961, 11035249));
        GORE_NEST_SPAWN_EGG = item("gorenest_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.GORE_NEST, 11022961, 11035249));
        MECH_ZOMBIE_SPAWN_EGG = item("mechazombie_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.MECHAZOMBIE, 11022961, 11035249));
        HELLKNIGHT2016_SPAWN_EGG = item("hellknight2016_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.HELLKNIGHT2016, 11022961, 11035249));
        GARGOYLE_SPAWN_EGG = item("gargoyle_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.GARGOYLE, 11022961, 11035249));
        SPECTRE_SPAWN_EGG = item("spectre_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.SPECTRE, 11022961, 11035249));
        CUEBALL_SPAWN_EGG = item("cueball_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.CUEBALL, 11022961, 11035249));
        PROWLER_SPAWN_EGG = item("prowler_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.PROWLER, 11022961, 11035249));
        DREADKNIGHT_SPAWN_EGG = item("dreadknight_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.DREADKNIGHT, 11022961, 11035249));
        STONEIMP_SPAWN_EGG = item("stoneimp_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.IMP_STONE, 11022961, 11035249));
        POSSESSED_WORKER_SPAWN_EGG = item("possessed_worker_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.POSSESSEDWORKER, 11022961, 11035249));
        DOOMHUNTER_SPAWN_EGG = item("doom_hunter_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.DOOMHUNTER, 11022961, 11035249));
        WHIPLASH_SPAWN_EGG = item("whiplash_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.WHIPLASH, 11022961, 11035249));
        BARON2016_SPAWN_EGG = item("baron2016_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.BARON2016, 11022961, 11035249));
        FIREBORNE_BARON_SPAWN_EGG = item("firebronebaron_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.FIREBARON, 11022961, 11035249));
        ARMORED_BARON_SPAWN_EGG = item("armoredbaron_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.ARMORBARON, 11022961, 11035249));
        MAYKR_DRONE_SPAWN_EGG = item("maykr_drone_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.MAYKRDRONE, 11022961, 11035249));
        BLOOD_MAYKR_SPAWN_EGG = item("blood_maykr_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.BLOODMAYKR, 11022961, 11035249));
        ARCH_MAKYR_SPAWN_EGG = item("arch_maykr_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.ARCHMAKER, 11022961, 11035249));
        ARACHNOTRONETERNAL_SPAWN_EGG = item("arachnotroneternal_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.ARACHNOTRONETERNAL, 11022961, 11035249));
        SPIDERMASTERMIND2016_SPAWN_EGG = item("spidermastermind2016_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.SPIDERMASTERMIND2016, 11022961, 11035249));
        TENTACLE_SPAWN_EGG = item("tentacle_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.TENTACLE, 11022961, 11035249));
        MOTHERDEMON_SPAWN_EGG = item("motherdemon_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.MOTHERDEMON, 11022961, 11035249));
        TURRET_SPAWN_EGG = item("turret_spawn_egg", new AzureSpawnEgg(FabricDoomEntities.TURRET, 11022961, 11035249));
        SUMMONER_SPAWN_EGG = item("summoner_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.SUMMONER, 11022961, 11035249));
        REVENANT2016_SPAWN_EGG = item("revenant2016_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.REVENANT2016, 11022961, 11035249));
        GLADIATOR_SPAWN_EGG = item("gladiator_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.GLADIATOR, 11022961, 11035249));
        CARCASS_SPAWN_EGG = item("carcass_spawn_egg",
                new AzureSpawnEgg(FabricDoomEntities.CARCASS, 0xe4c7be, 0x5a575a));
    }

    public static void initArmor() {
        // Armor
        DOOM_HELMET = item("doom_helmet", new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.DOOM) {
        });
        DOOM_CHESTPLATE = item("doom_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.DOOM) {
                });
        DOOM_LEGGINGS = item("doom_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.DOOM) {
                });
        DOOM_BOOTS = item("doom_boots", new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.DOOM) {
        });
        PRAETOR_DOOM_HELMET = item("praetor_doom_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.PRAETOR) {
                });
        PRAETOR_DOOM_CHESTPLATE = item("praetor_doom_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.PRAETOR) {
                });
        PRAETOR_DOOM_LEGGINGS = item("praetor_doom_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.PRAETOR) {
                });
        PRAETOR_DOOM_BOOTS = item("praetor_doom_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.PRAETOR) {
                });
        ASTRO_DOOM_HELMET = item("astro_doom_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.ASTRO) {
                });
        ASTRO_DOOM_CHESTPLATE = item("astro_doom_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.ASTRO) {
                });
        ASTRO_DOOM_LEGGINGS = item("astro_doom_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.ASTRO) {
                });
        ASTRO_DOOM_BOOTS = item("astro_doom_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.ASTRO) {
                });
        CRIMSON_DOOM_HELMET = item("crimson_doom_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.CRIMSON) {
                });
        CRIMSON_DOOM_CHESTPLATE = item("crimson_doom_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.CRIMSON) {
                });
        CRIMSON_DOOM_LEGGINGS = item("crimson_doom_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.CRIMSON) {
                });
        CRIMSON_DOOM_BOOTS = item("crimson_doom_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.CRIMSON) {
                });
        MIDNIGHT_DOOM_HELMET = item("midnight_doom_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.MIDNIGHT) {
                });
        MIDNIGHT_DOOM_CHESTPLATE = item("midnight_doom_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.MIDNIGHT) {
                });
        MIDNIGHT_DOOM_LEGGINGS = item("midnight_doom_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.MIDNIGHT) {
                });
        MIDNIGHT_DOOM_BOOTS = item("midnight_doom_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.MIDNIGHT) {
                });
        DEMONIC_DOOM_HELMET = item("demonic_doom_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.DEMONIC) {
                });
        DEMONIC_DOOM_CHESTPLATE = item("demonic_doom_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.DEMONIC) {
                });
        DEMONIC_DOOM_LEGGINGS = item("demonic_doom_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.DEMONIC) {
                });
        DEMONIC_DOOM_BOOTS = item("demonic_doom_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.DEMONIC) {
                });
        DEMONCIDE_DOOM_HELMET = item("demoncide_doom_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.DEMONCIDE) {
                });
        DEMONCIDE_DOOM_CHESTPLATE = item("demoncide_doom_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.DEMONCIDE) {
                });
        DEMONCIDE_DOOM_LEGGINGS = item("demoncide_doom_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.DEMONCIDE) {
                });
        DEMONCIDE_DOOM_BOOTS = item("demoncide_doom_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.DEMONCIDE) {
                });
        SENTINEL_DOOM_HELMET = item("sentinel_doom_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.SENTINEL) {
                });
        SENTINEL_DOOM_CHESTPLATE = item("sentinel_doom_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.SENTINEL) {
                });
        SENTINEL_DOOM_LEGGINGS = item("sentinel_doom_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.SENTINEL) {
                });
        SENTINEL_DOOM_BOOTS = item("sentinel_doom_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.SENTINEL) {
                });
        EMBER_DOOM_HELMET = item("ember_doom_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.EMBER) {
                });
        EMBER_DOOM_CHESTPLATE = item("ember_doom_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.EMBER) {
                });
        EMBER_DOOM_LEGGINGS = item("ember_doom_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.EMBER) {
                });
        EMBER_DOOM_BOOTS = item("ember_doom_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.EMBER) {
                });
        ZOMBIE_DOOM_HELMET = item("zombie_doom_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.ZOMBIE) {
                });
        ZOMBIE_DOOM_CHESTPLATE = item("zombie_doom_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.ZOMBIE) {
                });
        ZOMBIE_DOOM_LEGGINGS = item("zombie_doom_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.ZOMBIE) {
                });
        ZOMBIE_DOOM_BOOTS = item("zombie_doom_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.ZOMBIE) {
                });
        PHOBOS_DOOM_HELMET = item("phobos_doom_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.PHOBOS) {
                });
        PHOBOS_DOOM_CHESTPLATE = item("phobos_doom_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.PHOBOS) {
                });
        PHOBOS_DOOM_LEGGINGS = item("phobos_doom_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.PHOBOS) {
                });
        PHOBOS_DOOM_BOOTS = item("phobos_doom_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.PHOBOS) {
                });
        NIGHTMARE_DOOM_HELMET = item("nightmare_doom_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.NIGHTMARE) {
                });
        NIGHTMARE_DOOM_CHESTPLATE = item("nightmare_doom_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.NIGHTMARE) {
                });
        NIGHTMARE_DOOM_LEGGINGS = item("nightmare_doom_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.NIGHTMARE) {
                });
        NIGHTMARE_DOOM_BOOTS = item("nightmare_doom_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.NIGHTMARE) {
                });
        PURPLEPONY_DOOM_HELMET = item("purplepony_doom_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.PURPLE_PONY) {
                });
        PURPLEPONY_DOOM_CHESTPLATE = item("purplepony_doom_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.PURPLE_PONY) {
                });
        PURPLEPONY_DOOM_LEGGINGS = item("purplepony_doom_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.PURPLE_PONY) {
                });
        PURPLEPONY_DOOM_BOOTS = item("purplepony_doom_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.PURPLE_PONY) {
                });
        DOOMICORN_DOOM_HELMET = item("doomicorn_doom_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.DOOMICORN) {
                });
        DOOMICORN_DOOM_CHESTPLATE = item("doomicorn_doom_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.DOOMICORN) {
                });
        DOOMICORN_DOOM_LEGGINGS = item("doomicorn_doom_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.DOOMICORN) {
                });
        DOOMICORN_DOOM_BOOTS = item("doomicorn_doom_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.DOOMICORN) {
                });
        GOLD_DOOM_HELMET = item("gold_doom_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.GOLD) {
                });
        GOLD_DOOM_CHESTPLATE = item("gold_doom_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.GOLD) {
                });
        GOLD_DOOM_LEGGINGS = item("gold_doom_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.GOLD) {
                });
        GOLD_DOOM_BOOTS = item("gold_doom_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.GOLD) {
                });
        TWENTY_FIVE_DOOM_HELMET = item("twenty_five_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.TWENTYFIVE) {
                });
        TWENTY_FIVE_DOOM_CHESTPLATE = item("twenty_five_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.TWENTYFIVE) {
                });
        TWENTY_FIVE_DOOM_LEGGINGS = item("twenty_five_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.TWENTYFIVE) {
                });
        TWENTY_FIVE_DOOM_BOOTS = item("twenty_five_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.TWENTYFIVE) {
                });
        BRONZE_DOOM_HELMET = item("bronze_doom_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.BRONZE) {
                });
        BRONZE_DOOM_CHESTPLATE = item("bronze_doom_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.BRONZE) {
                });
        BRONZE_DOOM_LEGGINGS = item("bronze_doom_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.BRONZE) {
                });
        BRONZE_DOOM_BOOTS = item("bronze_doom_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.BRONZE) {
                });
        CULTIST_DOOM_HELMET = item("cultist_doom_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.CULTIST) {
                });
        CULTIST_DOOM_CHESTPLATE = item("cultist_doom_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.CULTIST) {
                });
        CULTIST_DOOM_LEGGINGS = item("cultist_doom_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.CULTIST) {
                });
        CULTIST_DOOM_BOOTS = item("cultist_doom_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.CULTIST) {
                });
        MAYKR_DOOM_HELMET = item("maykr_doom_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.MAYKR) {
                });
        MAYKR_DOOM_CHESTPLATE = item("maykr_doom_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.MAYKR) {
                });
        MAYKR_DOOM_LEGGINGS = item("maykr_doom_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.MAYKR) {
                });
        MAYKR_DOOM_BOOTS = item("maykr_doom_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.MAYKR) {
                });
        PAINTER_DOOM_HELMET = item("painter_doom_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.PAINTER) {
                });
        PAINTER_DOOM_CHESTPLATE = item("painter_doom_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.PAINTER) {
                });
        CLASSIC_DOOM_HELMET = item("classic_doom_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.CLASSIC_GREEN) {
                });
        CLASSIC_DOOM_CHESTPLATE = item("classic_doom_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.CLASSIC_GREEN) {
                });
        CLASSIC_DOOM_LEGGINGS = item("classic_doom_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.CLASSIC_GREEN) {
                });
        CLASSIC_RED_DOOM_CHESTPLATE = item("classic_red_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.CLASSIC_RED) {
                });
        CLASSIC_RED_DOOM_LEGGINGS = item("classic_red_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.CLASSIC_RED) {
                });
        CLASSIC_INDIGO_DOOM_CHESTPLATE = item("classic_black_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.CLASSIC_INDIGO) {
                });
        CLASSIC_INDIGO_DOOM_LEGGINGS = item("classic_black_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.CLASSIC_INDIGO) {
                });
        CLASSIC_BRONZE_DOOM_CHESTPLATE = item("classic_bronze_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.CLASSIC_BRONZE) {
                });
        CLASSIC_BRONZE_DOOM_LEGGINGS = item("classic_bronze_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.CLASSIC_BRONZE) {
                });
        CLASSIC_DOOM_BOOTS = item("classic_doom_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.CLASSIC_GREEN) {
                });
        MULLET_DOOM_HELMET1 = item("redneck_doom1_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.MULLET1) {
                });
        MULLET_DOOM_CHESTPLATE1 = item("redneck_doom1_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.MULLET1) {
                });
        MULLET_DOOM_CHESTPLATE2 = item("redneck_doom2_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.MULLET2) {
                });
        MULLET_DOOM_CHESTPLATE3 = item("redneck_doom3_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.MULLET3) {
                });
        MULLET_DOOM_LEGGINGS1 = item("redneck_doom1_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.MULLET1) {
                });
        MULLET_DOOM_BOOTS1 = item("redneck_doom1_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.MULLET1) {
                });
        HOTROD_HELMET = item("hotrod_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.HOTROD) {
                });
        HOTROD_CHESTPLATE = item("hotrod_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.HOTROD) {
                });
        HOTROD_LEGGINGS = item("hotrod_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.HOTROD) {
                });
        HOTROD_BOOTS = item("hotrod_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.HOTROD) {
                });
        SANTA_HELMET = item("santa_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.SANTA) {
                });
        DARKLORD_HELMET = item("darklord_helmet",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.DARK_LORD) {
                });
        DARKLORD_CHESTPLATE = item("darklord_chestplate",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.DARK_LORD) {
                });
        DARKLORD_LEGGINGS = item("darklord_leggings",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.DARK_LORD) {
                });
        DARKLORD_BOOTS = item("darklord_boots",
                new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.DARK_LORD) {
                });
    }
}
