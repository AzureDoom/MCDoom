package mod.azure.doom.registry;

import mod.azure.doom.MCDoom;
import mod.azure.doom.NeoForgeMCDoomMod;
import mod.azure.doom.items.E1M1MusicDisc;
import mod.azure.doom.items.UnopenedItem;
import mod.azure.doom.items.ammo.*;
import mod.azure.doom.items.argent.ArgentEnergyItem;
import mod.azure.doom.items.argent.ArgentPlateItem;
import mod.azure.doom.items.armor.ArmorTypeEnum;
import mod.azure.doom.items.armor.DoomArmor;
import mod.azure.doom.items.blockitems.GuntableBlockItem;
import mod.azure.doom.items.blockitems.TotemBlockItem;
import mod.azure.doom.items.enums.DAMat;
import mod.azure.doom.items.powerup.*;
import mod.azure.doom.items.tools.*;
import mod.azure.doom.items.weapons.*;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public record NeoDoomItems() {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MCDoom.MOD_ID);

    // BLOCKS
    public static final RegistryObject<Item> TOTEM = ITEMS.register("totem", () -> new TotemBlockItem(NeoDoomBlocks.TOTEM.get(), new Item.Properties()));
    public static final RegistryObject<Item> GUN_TABLE = ITEMS.register("gun_table", () -> new GuntableBlockItem(NeoDoomBlocks.GUN_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ITEM = ITEMS.register("barrel", () -> new BlockItem(NeoDoomBlocks.BARREL_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARGENT_BLOCK = ITEMS.register("argent_block", () -> new BlockItem(NeoDoomBlocks.ARGENT_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARGENT_LAMP_BLOCK = ITEMS.register("argent_lamp_block", () -> new BlockItem(NeoDoomBlocks.ARGENT_LAMP_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> DOOM_SAND = ITEMS.register("doom_sand", () -> new BlockItem(NeoDoomBlocks.DOOM_SAND.get(), new Item.Properties()));
    public static final RegistryObject<Item> ICON_WALL1 = ITEMS.register("icon_wall1", () -> new BlockItem(NeoDoomBlocks.ICON_WALL1.get(), new Item.Properties()));
    public static final RegistryObject<Item> ICON_WALL2 = ITEMS.register("icon_wall2", () -> new BlockItem(NeoDoomBlocks.ICON_WALL2.get(), new Item.Properties()));
    public static final RegistryObject<Item> ICON_WALL3 = ITEMS.register("icon_wall3", () -> new BlockItem(NeoDoomBlocks.ICON_WALL3.get(), new Item.Properties()));
    public static final RegistryObject<Item> ICON_WALL4 = ITEMS.register("icon_wall4", () -> new BlockItem(NeoDoomBlocks.ICON_WALL4.get(), new Item.Properties()));
    public static final RegistryObject<Item> ICON_WALL5 = ITEMS.register("icon_wall5", () -> new BlockItem(NeoDoomBlocks.ICON_WALL5.get(), new Item.Properties()));
    public static final RegistryObject<Item> ICON_WALL6 = ITEMS.register("icon_wall6", () -> new BlockItem(NeoDoomBlocks.ICON_WALL6.get(), new Item.Properties()));
    public static final RegistryObject<Item> ICON_WALL7 = ITEMS.register("icon_wall7", () -> new BlockItem(NeoDoomBlocks.ICON_WALL7.get(), new Item.Properties()));
    public static final RegistryObject<Item> ICON_WALL8 = ITEMS.register("icon_wall8", () -> new BlockItem(NeoDoomBlocks.ICON_WALL8.get(), new Item.Properties()));
    public static final RegistryObject<Item> ICON_WALL9 = ITEMS.register("icon_wall9", () -> new BlockItem(NeoDoomBlocks.ICON_WALL9.get(), new Item.Properties()));
    public static final RegistryObject<Item> ICON_WALL10 = ITEMS.register("icon_wall10", () -> new BlockItem(NeoDoomBlocks.ICON_WALL10.get(), new Item.Properties()));
    public static final RegistryObject<Item> ICON_WALL11 = ITEMS.register("icon_wall11", () -> new BlockItem(NeoDoomBlocks.ICON_WALL11.get(), new Item.Properties()));
    public static final RegistryObject<Item> ICON_WALL12 = ITEMS.register("icon_wall12", () -> new BlockItem(NeoDoomBlocks.ICON_WALL12.get(), new Item.Properties()));
    public static final RegistryObject<Item> ICON_WALL13 = ITEMS.register("icon_wall13", () -> new BlockItem(NeoDoomBlocks.ICON_WALL13.get(), new Item.Properties()));
    public static final RegistryObject<Item> ICON_WALL14 = ITEMS.register("icon_wall14", () -> new BlockItem(NeoDoomBlocks.ICON_WALL14.get(), new Item.Properties()));
    public static final RegistryObject<Item> ICON_WALL15 = ITEMS.register("icon_wall15", () -> new BlockItem(NeoDoomBlocks.ICON_WALL15.get(), new Item.Properties()));
    public static final RegistryObject<Item> ICON_WALL16 = ITEMS.register("icon_wall16", () -> new BlockItem(NeoDoomBlocks.ICON_WALL16.get(), new Item.Properties()));

    public static final RegistryObject<Item> E1M1_1 = ITEMS.register("e1m1_block1", () -> new BlockItem(NeoDoomBlocks.E1M1_1.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_2 = ITEMS.register("e1m1_block2", () -> new BlockItem(NeoDoomBlocks.E1M1_2.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_3 = ITEMS.register("e1m1_block3", () -> new BlockItem(NeoDoomBlocks.E1M1_3.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_4 = ITEMS.register("e1m1_block4", () -> new BlockItem(NeoDoomBlocks.E1M1_4.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_5 = ITEMS.register("e1m1_block5", () -> new BlockItem(NeoDoomBlocks.E1M1_5.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_6 = ITEMS.register("e1m1_block6", () -> new BlockItem(NeoDoomBlocks.E1M1_6.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_7 = ITEMS.register("e1m1_block7", () -> new BlockItem(NeoDoomBlocks.E1M1_7.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_8 = ITEMS.register("e1m1_block8", () -> new BlockItem(NeoDoomBlocks.E1M1_8.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_9 = ITEMS.register("e1m1_block9", () -> new BlockItem(NeoDoomBlocks.E1M1_9.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_10 = ITEMS.register("e1m1_block10", () -> new BlockItem(NeoDoomBlocks.E1M1_10.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_11 = ITEMS.register("e1m1_block11", () -> new BlockItem(NeoDoomBlocks.E1M1_11.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_12 = ITEMS.register("e1m1_block12", () -> new BlockItem(NeoDoomBlocks.E1M1_12.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_13 = ITEMS.register("e1m1_block13", () -> new BlockItem(NeoDoomBlocks.E1M1_13.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_14 = ITEMS.register("e1m1_block14", () -> new BlockItem(NeoDoomBlocks.E1M1_14.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_15 = ITEMS.register("e1m1_block15", () -> new BlockItem(NeoDoomBlocks.E1M1_15.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_16 = ITEMS.register("e1m1_block16", () -> new BlockItem(NeoDoomBlocks.E1M1_16.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_17 = ITEMS.register("e1m1_block17", () -> new BlockItem(NeoDoomBlocks.E1M1_17.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_18 = ITEMS.register("e1m1_block18", () -> new BlockItem(NeoDoomBlocks.E1M1_18.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_19 = ITEMS.register("e1m1_block19", () -> new BlockItem(NeoDoomBlocks.E1M1_19.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_20 = ITEMS.register("e1m1_block20", () -> new BlockItem(NeoDoomBlocks.E1M1_20.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_21 = ITEMS.register("e1m1_block21", () -> new BlockItem(NeoDoomBlocks.E1M1_21.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_22 = ITEMS.register("e1m1_block22", () -> new BlockItem(NeoDoomBlocks.E1M1_22.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_23 = ITEMS.register("e1m1_block23", () -> new BlockItem(NeoDoomBlocks.E1M1_23.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_24 = ITEMS.register("e1m1_block24", () -> new BlockItem(NeoDoomBlocks.E1M1_24.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_25 = ITEMS.register("e1m1_block25", () -> new BlockItem(NeoDoomBlocks.E1M1_25.get(), new Item.Properties()));
    public static final RegistryObject<Item> E1M1_26 = ITEMS.register("e1m1_block26", () -> new BlockItem(NeoDoomBlocks.E1M1_26.get(), new Item.Properties()));

    public static final RegistryObject<Item> JUMP_PAD = ITEMS.register("jump_pad", () -> new BlockItem(NeoDoomBlocks.JUMP_PAD.get(), new Item.Properties()));

    // AMMO
    public static final RegistryObject<Item> SHOTGUN_SHELLS = ITEMS.register("shotgun_shells", ShellAmmo::new);
    public static final RegistryObject<Item> ARGENT_BOLT = ITEMS.register("argent_bolt", ArgentBolt::new);
    public static final RegistryObject<Item> UNMAKRY_BOLT = ITEMS.register("unmaykr_bolt", UnmaykrBolt::new);
    public static final RegistryObject<Item> ENERGY_CELLS = ITEMS.register("energy_cells", EnergyCell::new);
    public static final RegistryObject<Item> CHAINGUN_BULLETS = ITEMS.register("chaingunbullets", ChaingunAmmo::new);
    public static final RegistryObject<ClipAmmo> BULLETS = ITEMS.register("bullets", ClipAmmo::new);
    public static final RegistryObject<Item> BFG_CELL = ITEMS.register("bfg_cell", BFGCell::new);
    public static final RegistryObject<Item> ROCKET = ITEMS.register("rocket", Rocket::new);
    public static final RegistryObject<Item> GAS_BARREL = ITEMS.register("gas_barrel", () -> new Item(new Item.Properties()));

    // MISC
    public static final RegistryObject<Item> ICON_ICON = ITEMS.register("icon_icon", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARGENT_ENERGY = ITEMS.register("argent_energy", ArgentEnergyItem::new);
    public static final RegistryObject<Item> ARGENT_PLATE = ITEMS.register("argent_plate", ArgentPlateItem::new);
    public static final RegistryObject<Item> DAISY = ITEMS.register("daisy", DaisyItem::new);
    public static final RegistryObject<Item> E1M1_MUSIC_DISC = ITEMS.register("e1m1_music_disc", () -> new E1M1MusicDisc(NeoDoomSounds.E1M1.get()));
    public static final RegistryObject<Item> GEOF_MUSIC_DISC = ITEMS.register("netherambient_geoffplaysguitar_music_disc", () -> new E1M1MusicDisc(NeoDoomSounds.NETHERAMBIENT_GEOFFPLAYSGUITAR.get()));
    public static final RegistryObject<Item> INMORTAL = ITEMS.register("inmortalsphere", InmortalSphereItem::new);
    public static final RegistryObject<Item> INVISIBLE = ITEMS.register("invisiblesphere", InvisibleSphereItem::new);
    public static final RegistryObject<Item> MEGA = ITEMS.register("megasphere", MegaSphereItem::new);
    public static final RegistryObject<Item> POWER = ITEMS.register("powersphere", PowerSphereItem::new);
    public static final RegistryObject<Item> SOULCUBE = ITEMS.register("soulcube", SoulCubeItem::new);

    public static final RegistryObject<Item> IMP_SPAWN_EGG = ITEMS.register("imp_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.IMP, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> ARACHNOTRON_SPAWN_EGG = ITEMS.register("arachnotron_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.ARACHNOTRON, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> LOST_SOUL_SPAWN_EGG = ITEMS.register("lost_soul_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.LOST_SOUL, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> LOST_SOUL_ETERNAL_SPAWN_EGG = ITEMS.register("lost_soul_eternal_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.LOST_SOUL_ETERNAL, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> PINKY_SPAWN_EGG = ITEMS.register("pinky_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.PINKY, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> ARCHVILE_SPAWN_EGG = ITEMS.register("archvile_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.ARCHVILE, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> BARON_SPAWN_EGG = ITEMS.register("baron_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.BARON, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> CACODEMON_SPAWN_EGG = ITEMS.register("cacodemon_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.CACODEMON, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> MANCUBUS_SPAWN_EGG = ITEMS.register("mancubus_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.MANCUBUS, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> SPIDERMASTERMIND_SPAWN_EGG = ITEMS.register("spidermastermind_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.SPIDERMASTERMIND, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> ZOMBIEMAN_SPAWN_EGG = ITEMS.register("zombieman_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.ZOMBIEMAN, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> CHAINGUNNER_SPAWN_EGG = ITEMS.register("chaingunner_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.CHAINGUNNER, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> HELLKNIGHT_SPAWN_EGG = ITEMS.register("hellknight_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.HELLKNIGHT, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> MARAUDER_SPAWN_EGG = ITEMS.register("marauder_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.MARAUDER, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> PAIN_SPAWN_EGG = ITEMS.register("pain_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.PAIN, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> REVENANT_SPAWN_EGG = ITEMS.register("revenant_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.REVENANT, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> SHOTGUNGUY_SPAWN_EGG = ITEMS.register("shotgunguy_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.SHOTGUNGUY, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> CYBERDEMON_SPAWN_EGG = ITEMS.register("cyberdemon_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.CYBERDEMON, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> ICON_SPAWN_EGG = ITEMS.register("icon_of_sin_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.ICONOFSIN, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> UNWILLING_SPAWN_EGG = ITEMS.register("unwilling_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.UNWILLING, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> MECH_ZOMBIE_SPAWN_EGG = ITEMS.register("mechazombie_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.MECHAZOMBIE, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> GORE_NEST_SPAWN_EGG = ITEMS.register("gorenest_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.GORE_NEST, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> POSSESSED_SCIENTIST_SPAWN_EGG = ITEMS.register("possessed_scientist_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.POSSESSEDSCIENTIST, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> POSSESSED_SOLDIER_SPAWN_EGG = ITEMS.register("possessed_soldier_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.POSSESSEDSOLDIER, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> HELLKNIGHT2016_SPAWN_EGG = ITEMS.register("hellknight2016_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.HELLKNIGHT2016, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> GARGOYLE_SPAWN_EGG = ITEMS.register("gargoyle_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.GARGOYLE, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> SPECTRE_SPAWN_EGG = ITEMS.register("spectre_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.SPECTRE, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> CUEBALL_SPAWN_EGG = ITEMS.register("cueball_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.CUEBALL, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> PROWLER_SPAWN_EGG = ITEMS.register("prowler_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.PROWLER, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> DREADKNIGHT_SPAWN_EGG = ITEMS.register("dreadknight_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.DREADKNIGHT, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> STONEIMP_SPAWN_EGG = ITEMS.register("stoneimp_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.IMP_STONE, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> POSSESSED_WORKER_SPAWN_EGG = ITEMS.register("possessed_worker_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.POSSESSEDWORKER, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> DOOMHUNTER_SPAWN_EGG = ITEMS.register("doom_hunter_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.DOOMHUNTER, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> WHIPLASH_SPAWN_EGG = ITEMS.register("whiplash_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.WHIPLASH, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> BARON2016_SPAWN_EGG = ITEMS.register("baron2016_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.BARON2016, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> FIREBORNE_BARON_SPAWN_EGG = ITEMS.register("firebronebaron_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.FIREBARON, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> ARMORED_BARON_SPAWN_EGG = ITEMS.register("armoredbaron_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.ARMORBARON, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> MAYKR_DRONE_SPAWN_EGG = ITEMS.register("maykr_drone_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.MAYKRDRONE, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> BLOOD_MAYKR_SPAWN_EGG = ITEMS.register("blood_maykr_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.BLOODMAYKR, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> ARCH_MAKYR_SPAWN_EGG = ITEMS.register("arch_maykr_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.ARCHMAKER, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> ARACHNOTRONETERNAL_SPAWN_EGG = ITEMS.register("arachnotroneternal_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.ARACHNOTRONETERNAL, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> SPIDERMASTERMIND2016_SPAWN_EGG = ITEMS.register("spidermastermind2016_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.SPIDERMASTERMIND2016, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> TENTACLE_SPAWN_EGG = ITEMS.register("tentacle_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.TENTACLE, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> MOTHERDEMON_SPAWN_EGG = ITEMS.register("motherdemon_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.MOTHERDEMON, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> TURRET_SPAWN_EGG = ITEMS.register("turret_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.TURRET, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> SUMMONER_SPAWN_EGG = ITEMS.register("summoner_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.SUMMONER, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> REVENANT2016_SPAWN_EGG = ITEMS.register("revenant2016_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.REVENANT2016, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> GLADIATOR_SPAWN_EGG = ITEMS.register("gladiator_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.GLADIATOR, 11022961, 11035249, new Item.Properties()));
    public static final RegistryObject<Item> CARCASS_SPAWN_EGG = ITEMS.register("carcass_spawn_egg", () -> new ForgeSpawnEggItem(NeoDoomEntities.CARCASS, 0xe4c7be, 0x5a575a, new Item.Properties()));

    // WEAPONS AND TOOLS
    public static final RegistryObject<Item> CRUCIBLESWORD = ITEMS.register("cruciblesword", SwordCrucibleItem::new);
    public static final RegistryObject<Item> ARGENT_AXE = ITEMS.register("argent_axe", () -> new ArgentAxe(NeoForgeMCDoomMod.ARGENT_TIER));
    public static final RegistryObject<Item> ARGENT_HOE = ITEMS.register("argent_hoe", () -> new ArgentHoe(NeoForgeMCDoomMod.ARGENT_TIER));
    public static final RegistryObject<Item> ARGENT_PAXEL = ITEMS.register("argent_paxel", () -> new ArgentPaxel(NeoForgeMCDoomMod.ARGENT_TIER));
    public static final RegistryObject<Item> ARGENT_PICKAXE = ITEMS.register("argent_pickaxe", () -> new ArgentPickaxe(NeoForgeMCDoomMod.ARGENT_TIER));
    public static final RegistryObject<Item> ARGENT_SHOVEL = ITEMS.register("argent_shovel", () -> new ArgentShovel(NeoForgeMCDoomMod.ARGENT_TIER));
    public static final RegistryObject<Item> ARGENT_SWORD = ITEMS.register("argent_sword", () -> new ArgentSword(NeoForgeMCDoomMod.ARGENT_TIER));
    public static final RegistryObject<Item> CHAINSAW = ITEMS.register("chainsaw", Chainsaw::new);
    public static final RegistryObject<Item> CHAINSAW_ETERNAL = ITEMS.register("chainsaweternal", ChainsawAnimated::new);
    public static final RegistryObject<Item> CHAINSAW64 = ITEMS.register("chainsaw64", Chainsaw::new);
    public static final RegistryObject<Item> SWORD_CLOSED = ITEMS.register("cruciblesword_closed", UnopenedItem::new);
    public static final RegistryObject<Item> AXE_OPEN = ITEMS.register("axe_marauder_open", AxeMarauderItem::new);
    public static final RegistryObject<Item> AXE_CLOSED = ITEMS.register("axe_marauder_closed", UnopenedItem::new);
    public static final RegistryObject<Item> SSG = ITEMS.register("supershotgun", SuperShotgun::new);
    public static final RegistryObject<Item> SG = ITEMS.register("shotgun", Shotgun::new);
    public static final RegistryObject<Item> BFG = ITEMS.register("bfg9000", BFG9000::new);
    public static final RegistryObject<Item> BFG_ETERNAL = ITEMS.register("bfg_eternal", mod.azure.doom.items.weapons.BFG::new);
    public static final RegistryObject<Item> PLASMAGUN = ITEMS.register("plasmagun", PlasmaGun::new);
    public static final RegistryObject<Item> ROCKETLAUNCHER = ITEMS.register("rocketlauncher", RocketLauncher::new);
    public static final RegistryObject<Item> UNMAYKR = ITEMS.register("unmaykr", () -> new Unmaykr("white"));
    public static final RegistryObject<Item> UNMAKER = ITEMS.register("unmaker", () -> new Unmaker("demon"));
    public static final RegistryObject<Item> BALLISTA = ITEMS.register("ballista", Ballista::new);
    public static final RegistryObject<Item> CHAINGUN = ITEMS.register("chaingun", Chaingun::new);
    public static final RegistryObject<Item> PISTOL = ITEMS.register("pistol", PistolItem::new);
    public static final RegistryObject<Item> HEAVYCANNON = ITEMS.register("heavycannon", HeavyCannon::new);
    public static final RegistryObject<Item> SENTINELHAMMER = ITEMS.register("sentinelhammer", SentinelHammerItem::new);
    public static final RegistryObject<Item> DARKLORDCRUCIBLE = ITEMS.register("darklordcrucible", DarkLordCrucibleItem::new);
    public static final RegistryObject<Item> DSG = ITEMS.register("doomed_shotgun", DShotgun::new);
    public static final RegistryObject<Item> DGAUSS = ITEMS.register("doomed_gauss", DGauss::new);
    public static final RegistryObject<Item> GRENADE = ITEMS.register("doomed_grenade", GrenadeItem::new);
    public static final RegistryObject<Item> DPLASMARIFLE = ITEMS.register("doomed_plasma_rifle", DPlasmaRifle::new);

    // ARMOR
    public static final RegistryObject<Item> DOOM_HELMET = ITEMS.register("doom_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.DOOM) {
    });
    public static final RegistryObject<Item> DOOM_CHESTPLATE = ITEMS.register("doom_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.DOOM) {
    });
    public static final RegistryObject<Item> DOOM_LEGGINGS = ITEMS.register("doom_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.DOOM) {
    });
    public static final RegistryObject<Item> DOOM_BOOTS = ITEMS.register("doom_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.DOOM) {
    });
    public static final RegistryObject<Item> PRAETOR_DOOM_HELMET = ITEMS.register("praetor_doom_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.PRAETOR) {
    });
    public static final RegistryObject<Item> PRAETOR_DOOM_CHESTPLATE = ITEMS.register("praetor_doom_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.PRAETOR) {
    });
    public static final RegistryObject<Item> PRAETOR_DOOM_LEGGINGS = ITEMS.register("praetor_doom_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.PRAETOR) {
    });
    public static final RegistryObject<Item> PRAETOR_DOOM_BOOTS = ITEMS.register("praetor_doom_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.PRAETOR) {
    });
    public static final RegistryObject<Item> ASTRO_DOOM_HELMET = ITEMS.register("astro_doom_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.ASTRO) {
    });
    public static final RegistryObject<Item> ASTRO_DOOM_CHESTPLATE = ITEMS.register("astro_doom_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.ASTRO) {
    });
    public static final RegistryObject<Item> ASTRO_DOOM_LEGGINGS = ITEMS.register("astro_doom_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.ASTRO) {
    });
    public static final RegistryObject<Item> ASTRO_DOOM_BOOTS = ITEMS.register("astro_doom_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.ASTRO) {
    });
    public static final RegistryObject<Item> CRIMSON_DOOM_HELMET = ITEMS.register("crimson_doom_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.CRIMSON) {
    });
    public static final RegistryObject<Item> CRIMSON_DOOM_CHESTPLATE = ITEMS.register("crimson_doom_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.CRIMSON) {
    });
    public static final RegistryObject<Item> CRIMSON_DOOM_LEGGINGS = ITEMS.register("crimson_doom_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.CRIMSON) {
    });
    public static final RegistryObject<Item> CRIMSON_DOOM_BOOTS = ITEMS.register("crimson_doom_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.CRIMSON) {
    });
    public static final RegistryObject<Item> MIDNIGHT_DOOM_HELMET = ITEMS.register("midnight_doom_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.MIDNIGHT) {
    });
    public static final RegistryObject<Item> MIDNIGHT_DOOM_CHESTPLATE = ITEMS.register("midnight_doom_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.MIDNIGHT) {
    });
    public static final RegistryObject<Item> MIDNIGHT_DOOM_LEGGINGS = ITEMS.register("midnight_doom_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.MIDNIGHT) {
    });
    public static final RegistryObject<Item> MIDNIGHT_DOOM_BOOTS = ITEMS.register("midnight_doom_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.MIDNIGHT) {
    });
    public static final RegistryObject<Item> DEMONIC_DOOM_HELMET = ITEMS.register("demonic_doom_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.DEMONIC) {
    });
    public static final RegistryObject<Item> DEMONIC_DOOM_CHESTPLATE = ITEMS.register("demonic_doom_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.DEMONIC) {
    });
    public static final RegistryObject<Item> DEMONIC_DOOM_LEGGINGS = ITEMS.register("demonic_doom_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.DEMONIC) {
    });
    public static final RegistryObject<Item> DEMONIC_DOOM_BOOTS = ITEMS.register("demonic_doom_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.DEMONIC) {
    });
    public static final RegistryObject<Item> DEMONCIDE_DOOM_HELMET = ITEMS.register("demoncide_doom_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.DEMONCIDE) {
    });
    public static final RegistryObject<Item> DEMONCIDE_DOOM_CHESTPLATE = ITEMS.register("demoncide_doom_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.DEMONCIDE) {
    });
    public static final RegistryObject<Item> DEMONCIDE_DOOM_LEGGINGS = ITEMS.register("demoncide_doom_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.DEMONCIDE) {
    });
    public static final RegistryObject<Item> DEMONCIDE_DOOM_BOOTS = ITEMS.register("demoncide_doom_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.DEMONCIDE) {
    });
    public static final RegistryObject<Item> SENTINEL_DOOM_HELMET = ITEMS.register("sentinel_doom_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.SENTINEL) {
    });
    public static final RegistryObject<Item> SENTINEL_DOOM_CHESTPLATE = ITEMS.register("sentinel_doom_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.SENTINEL) {
    });
    public static final RegistryObject<Item> SENTINEL_DOOM_LEGGINGS = ITEMS.register("sentinel_doom_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.SENTINEL) {
    });
    public static final RegistryObject<Item> SENTINEL_DOOM_BOOTS = ITEMS.register("sentinel_doom_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.SENTINEL) {
    });
    public static final RegistryObject<Item> EMBER_DOOM_HELMET = ITEMS.register("ember_doom_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.EMBER) {
    });
    public static final RegistryObject<Item> EMBER_DOOM_CHESTPLATE = ITEMS.register("ember_doom_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.EMBER) {
    });
    public static final RegistryObject<Item> EMBER_DOOM_LEGGINGS = ITEMS.register("ember_doom_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.EMBER) {
    });
    public static final RegistryObject<Item> EMBER_DOOM_BOOTS = ITEMS.register("ember_doom_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.EMBER) {
    });
    public static final RegistryObject<Item> ZOMBIE_DOOM_HELMET = ITEMS.register("zombie_doom_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.ZOMBIE) {
    });
    public static final RegistryObject<Item> ZOMBIE_DOOM_CHESTPLATE = ITEMS.register("zombie_doom_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.ZOMBIE) {
    });
    public static final RegistryObject<Item> ZOMBIE_DOOM_LEGGINGS = ITEMS.register("zombie_doom_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.ZOMBIE) {
    });
    public static final RegistryObject<Item> ZOMBIE_DOOM_BOOTS = ITEMS.register("zombie_doom_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.ZOMBIE) {
    });
    public static final RegistryObject<Item> PHOBOS_DOOM_HELMET = ITEMS.register("phobos_doom_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.PHOBOS) {
    });
    public static final RegistryObject<Item> PHOBOS_DOOM_CHESTPLATE = ITEMS.register("phobos_doom_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.PHOBOS) {
    });
    public static final RegistryObject<Item> PHOBOS_DOOM_LEGGINGS = ITEMS.register("phobos_doom_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.PHOBOS) {
    });
    public static final RegistryObject<Item> PHOBOS_DOOM_BOOTS = ITEMS.register("phobos_doom_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.PHOBOS) {
    });
    public static final RegistryObject<Item> NIGHTMARE_DOOM_HELMET = ITEMS.register("nightmare_doom_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.NIGHTMARE) {
    });
    public static final RegistryObject<Item> NIGHTMARE_DOOM_CHESTPLATE = ITEMS.register("nightmare_doom_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.NIGHTMARE) {
    });
    public static final RegistryObject<Item> NIGHTMARE_DOOM_LEGGINGS = ITEMS.register("nightmare_doom_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.NIGHTMARE) {
    });
    public static final RegistryObject<Item> NIGHTMARE_DOOM_BOOTS = ITEMS.register("nightmare_doom_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.NIGHTMARE) {
    });
    public static final RegistryObject<Item> PURPLEPONY_DOOM_HELMET = ITEMS.register("purplepony_doom_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.PURPLE_PONY) {
    });
    public static final RegistryObject<Item> PURPLEPONY_DOOM_CHESTPLATE = ITEMS.register("purplepony_doom_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.PURPLE_PONY) {
    });
    public static final RegistryObject<Item> PURPLEPONY_DOOM_LEGGINGS = ITEMS.register("purplepony_doom_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.PURPLE_PONY) {
    });
    public static final RegistryObject<Item> PURPLEPONY_DOOM_BOOTS = ITEMS.register("purplepony_doom_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.PURPLE_PONY) {
    });
    public static final RegistryObject<Item> DOOMICORN_DOOM_HELMET = ITEMS.register("doomicorn_doom_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.DOOMICORN) {
    });
    public static final RegistryObject<Item> DOOMICORN_DOOM_CHESTPLATE = ITEMS.register("doomicorn_doom_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.DOOMICORN) {
    });
    public static final RegistryObject<Item> DOOMICORN_DOOM_LEGGINGS = ITEMS.register("doomicorn_doom_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.DOOMICORN) {
    });
    public static final RegistryObject<Item> DOOMICORN_DOOM_BOOTS = ITEMS.register("doomicorn_doom_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.DOOMICORN) {
    });
    public static final RegistryObject<Item> GOLD_DOOM_HELMET = ITEMS.register("gold_doom_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.GOLD) {
    });
    public static final RegistryObject<Item> GOLD_DOOM_CHESTPLATE = ITEMS.register("gold_doom_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.GOLD) {
    });
    public static final RegistryObject<Item> GOLD_DOOM_LEGGINGS = ITEMS.register("gold_doom_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.GOLD) {
    });
    public static final RegistryObject<Item> GOLD_DOOM_BOOTS = ITEMS.register("gold_doom_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.GOLD) {
    });
    public static final RegistryObject<Item> TWENTY_FIVE_DOOM_HELMET = ITEMS.register("twenty_five_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.TWENTYFIVE) {
    });
    public static final RegistryObject<Item> TWENTY_FIVE_DOOM_CHESTPLATE = ITEMS.register("twenty_five_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.TWENTYFIVE) {
    });
    public static final RegistryObject<Item> TWENTY_FIVE_DOOM_LEGGINGS = ITEMS.register("twenty_five_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.TWENTYFIVE) {
    });
    public static final RegistryObject<Item> TWENTY_FIVE_DOOM_BOOTS = ITEMS.register("twenty_five_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.TWENTYFIVE) {
    });
    public static final RegistryObject<Item> BRONZE_DOOM_HELMET = ITEMS.register("bronze_doom_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.BRONZE) {
    });
    public static final RegistryObject<Item> BRONZE_DOOM_CHESTPLATE = ITEMS.register("bronze_doom_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.BRONZE) {
    });
    public static final RegistryObject<Item> BRONZE_DOOM_LEGGINGS = ITEMS.register("bronze_doom_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.BRONZE) {
    });
    public static final RegistryObject<Item> BRONZE_DOOM_BOOTS = ITEMS.register("bronze_doom_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.BRONZE) {
    });
    public static final RegistryObject<Item> CULTIST_DOOM_HELMET = ITEMS.register("cultist_doom_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.CULTIST) {
    });
    public static final RegistryObject<Item> CULTIST_DOOM_CHESTPLATE = ITEMS.register("cultist_doom_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.CULTIST) {
    });
    public static final RegistryObject<Item> CULTIST_DOOM_LEGGINGS = ITEMS.register("cultist_doom_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.CULTIST) {
    });
    public static final RegistryObject<Item> CULTIST_DOOM_BOOTS = ITEMS.register("cultist_doom_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.CULTIST) {
    });
    public static final RegistryObject<Item> MAYKR_DOOM_HELMET = ITEMS.register("maykr_doom_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.MAYKR) {
    });
    public static final RegistryObject<Item> MAYKR_DOOM_CHESTPLATE = ITEMS.register("maykr_doom_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.MAYKR) {
    });
    public static final RegistryObject<Item> MAYKR_DOOM_LEGGINGS = ITEMS.register("maykr_doom_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.MAYKR) {
    });
    public static final RegistryObject<Item> MAYKR_DOOM_BOOTS = ITEMS.register("maykr_doom_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.MAYKR) {
    });
    public static final RegistryObject<Item> PAINTER_DOOM_HELMET = ITEMS.register("painter_doom_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.PAINTER) {
    });
    public static final RegistryObject<Item> PAINTER_DOOM_CHESTPLATE = ITEMS.register("painter_doom_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.PAINTER) {
    });
    public static final RegistryObject<Item> CLASSIC_DOOM_HELMET = ITEMS.register("classic_doom_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.CLASSIC_GREEN) {
    });
    public static final RegistryObject<Item> CLASSIC_DOOM_CHESTPLATE = ITEMS.register("classic_doom_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.CLASSIC_GREEN) {
    });
    public static final RegistryObject<Item> CLASSIC_DOOM_LEGGINGS = ITEMS.register("classic_doom_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.CLASSIC_GREEN) {
    });
    public static final RegistryObject<Item> CLASSIC_RED_DOOM_CHESTPLATE = ITEMS.register("classic_red_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.CLASSIC_RED) {
    });
    public static final RegistryObject<Item> CLASSIC_RED_DOOM_LEGGINGS = ITEMS.register("classic_red_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.CLASSIC_RED) {
    });
    public static final RegistryObject<Item> CLASSIC_INDIGO_DOOM_CHESTPLATE = ITEMS.register("classic_black_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.CLASSIC_INDIGO) {
    });
    public static final RegistryObject<Item> CLASSIC_INDIGO_DOOM_LEGGINGS = ITEMS.register("classic_black_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.CLASSIC_INDIGO) {
    });
    public static final RegistryObject<Item> CLASSIC_BRONZE_DOOM_CHESTPLATE = ITEMS.register("classic_bronze_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.CLASSIC_BRONZE) {
    });
    public static final RegistryObject<Item> CLASSIC_BRONZE_DOOM_LEGGINGS = ITEMS.register("classic_bronze_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.CLASSIC_BRONZE) {
    });
    public static final RegistryObject<Item> CLASSIC_DOOM_BOOTS = ITEMS.register("classic_doom_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.CLASSIC_GREEN) {
    });
    public static final RegistryObject<Item> MULLET_DOOM_HELMET1 = ITEMS.register("redneck_doom1_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.MULLET1) {
    });
    public static final RegistryObject<Item> MULLET_DOOM_CHESTPLATE1 = ITEMS.register("redneck_doom1_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.MULLET1) {
    });
    public static final RegistryObject<Item> MULLET_DOOM_CHESTPLATE2 = ITEMS.register("redneck_doom2_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.MULLET2) {
    });
    public static final RegistryObject<Item> MULLET_DOOM_CHESTPLATE3 = ITEMS.register("redneck_doom3_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.MULLET3) {
    });
    public static final RegistryObject<Item> MULLET_DOOM_LEGGINGS1 = ITEMS.register("redneck_doom1_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.MULLET1) {
    });
    public static final RegistryObject<Item> MULLET_DOOM_BOOTS1 = ITEMS.register("redneck_doom1_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.MULLET1) {
    });
    public static final RegistryObject<Item> HOTROD_HELMET = ITEMS.register("hotrod_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.HOTROD) {
    });
    public static final RegistryObject<Item> HOTROD_CHESTPLATE = ITEMS.register("hotrod_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.HOTROD) {
    });
    public static final RegistryObject<Item> HOTROD_LEGGINGS = ITEMS.register("hotrod_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.HOTROD) {
    });
    public static final RegistryObject<Item> HOTROD_BOOTS = ITEMS.register("hotrod_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.HOTROD) {
    });
    public static final RegistryObject<Item> SANTA_HELMET = ITEMS.register("santa_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.SANTA) {
    });
    public static final RegistryObject<Item> DARKLORD_HELMET = ITEMS.register("darklord_helmet", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.HELMET, ArmorTypeEnum.DARK_LORD) {
    });
    public static final RegistryObject<Item> DARKLORD_CHESTPLATE = ITEMS.register("darklord_chestplate", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.CHESTPLATE, ArmorTypeEnum.DARK_LORD) {
    });
    public static final RegistryObject<Item> DARKLORD_LEGGINGS = ITEMS.register("darklord_leggings", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.LEGGINGS, ArmorTypeEnum.DARK_LORD) {
    });
    public static final RegistryObject<Item> DARKLORD_BOOTS = ITEMS.register("darklord_boots", () -> new DoomArmor(DAMat.DOOM_ARMOR, ArmorItem.Type.BOOTS, ArmorTypeEnum.DARK_LORD) {
    });
}
