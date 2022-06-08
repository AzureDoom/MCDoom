package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.ArgentEnergyItem;
import mod.azure.doom.item.ArgentPlateItem;
import mod.azure.doom.item.E1M1MusicDisc;
import mod.azure.doom.item.GunTableBlockItem;
import mod.azure.doom.item.TotemBlockItem;
import mod.azure.doom.item.UnopenedItem;
import mod.azure.doom.item.ammo.ArgentBolt;
import mod.azure.doom.item.ammo.BFGCell;
import mod.azure.doom.item.ammo.ChaingunAmmo;
import mod.azure.doom.item.ammo.ClipAmmo;
import mod.azure.doom.item.ammo.EnergyCell;
import mod.azure.doom.item.ammo.Rocket;
import mod.azure.doom.item.ammo.ShellAmmo;
import mod.azure.doom.item.ammo.UnmaykrBolt;
import mod.azure.doom.item.armor.AstroDoomArmor;
import mod.azure.doom.item.armor.BronzeDoomArmor;
import mod.azure.doom.item.armor.ClassicBronzeDoomArmor;
import mod.azure.doom.item.armor.ClassicDoomArmor;
import mod.azure.doom.item.armor.ClassicIndigoDoomArmor;
import mod.azure.doom.item.armor.ClassicRedDoomArmor;
import mod.azure.doom.item.armor.CrimsonDoomArmor;
import mod.azure.doom.item.armor.CultistDoomArmor;
import mod.azure.doom.item.armor.DarkLordArmor;
import mod.azure.doom.item.armor.DemoncideDoomArmor;
import mod.azure.doom.item.armor.DemonicDoomArmor;
import mod.azure.doom.item.armor.DoomArmor;
import mod.azure.doom.item.armor.DoomicornDoomArmor;
import mod.azure.doom.item.armor.EmberDoomArmor;
import mod.azure.doom.item.armor.GoldDoomArmor;
import mod.azure.doom.item.armor.HotrodDoomArmor;
import mod.azure.doom.item.armor.MaykrDoomArmor;
import mod.azure.doom.item.armor.MidnightDoomArmor;
import mod.azure.doom.item.armor.Mullet2DoomArmor;
import mod.azure.doom.item.armor.Mullet3DoomArmor;
import mod.azure.doom.item.armor.MulletDoomArmor;
import mod.azure.doom.item.armor.NightmareDoomArmor;
import mod.azure.doom.item.armor.PainterDoomArmor;
import mod.azure.doom.item.armor.PhobosDoomArmor;
import mod.azure.doom.item.armor.PraetorDoomArmor;
import mod.azure.doom.item.armor.PurplePonyDoomArmor;
import mod.azure.doom.item.armor.SantaDoomArmor;
import mod.azure.doom.item.armor.SentinelDoomArmor;
import mod.azure.doom.item.armor.TwentyFiveDoomArmor;
import mod.azure.doom.item.armor.ZombieDoomArmor;
import mod.azure.doom.item.eggs.DoomSpawnEgg;
import mod.azure.doom.item.powerup.DaisyItem;
import mod.azure.doom.item.powerup.InmortalSphereItem;
import mod.azure.doom.item.powerup.InvisibleSphereItem;
import mod.azure.doom.item.powerup.MegaSphereItem;
import mod.azure.doom.item.powerup.PowerSphereItem;
import mod.azure.doom.item.powerup.SoulCubeItem;
import mod.azure.doom.item.tools.ArgentAxe;
import mod.azure.doom.item.tools.ArgentHoe;
import mod.azure.doom.item.tools.ArgentPaxel;
import mod.azure.doom.item.tools.ArgentPickaxe;
import mod.azure.doom.item.tools.ArgentShovel;
import mod.azure.doom.item.weapons.ArgentSword;
import mod.azure.doom.item.weapons.AxeMarauderItem;
import mod.azure.doom.item.weapons.BFG;
import mod.azure.doom.item.weapons.BFG9000;
import mod.azure.doom.item.weapons.Ballista;
import mod.azure.doom.item.weapons.Chaingun;
import mod.azure.doom.item.weapons.Chainsaw;
import mod.azure.doom.item.weapons.ChainsawAnimated;
import mod.azure.doom.item.weapons.DGauss;
import mod.azure.doom.item.weapons.DPlasmaRifle;
import mod.azure.doom.item.weapons.DShotgun;
import mod.azure.doom.item.weapons.DarkLordCrucibleItem;
import mod.azure.doom.item.weapons.GrenadeItem;
import mod.azure.doom.item.weapons.HeavyCannon;
import mod.azure.doom.item.weapons.PistolItem;
import mod.azure.doom.item.weapons.PlasmaGun;
import mod.azure.doom.item.weapons.RocketLauncher;
import mod.azure.doom.item.weapons.SentinelHammerItem;
import mod.azure.doom.item.weapons.Shotgun;
import mod.azure.doom.item.weapons.SuperShotgun;
import mod.azure.doom.item.weapons.SwordCrucibleItem;
import mod.azure.doom.item.weapons.Unmaykr;
import mod.azure.doom.util.enums.DoomArmorMaterial;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DoomItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DoomMod.MODID);

	// BLOCKS
	public static final RegistryObject<Item> TOTEM = ITEMS.register("totem",
			() -> new TotemBlockItem(DoomBlocks.TOTEM.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> GUN_TABLE = ITEMS.register("gun_table",
			() -> new GunTableBlockItem(DoomBlocks.GUN_TABLE.get(),
					new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> ITEM = ITEMS.register("barrel",
			() -> new BlockItem(DoomBlocks.BARREL_BLOCK.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> ARGENT_BLOCK = ITEMS.register("argent_block",
			() -> new BlockItem(DoomBlocks.ARGENT_BLOCK.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> ARGENT_LAMP_BLOCK = ITEMS.register("argent_lamp_block",
			() -> new BlockItem(DoomBlocks.ARGENT_LAMP_BLOCK.get(),
					new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> DOOM_SAND = ITEMS.register("doom_sand",
			() -> new BlockItem(DoomBlocks.DOOM_SAND.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> DOOM_WALL1 = ITEMS.register("icon_wall1",
			() -> new BlockItem(DoomBlocks.DOOM_WALL1.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> DOOM_WALL2 = ITEMS.register("icon_wall2",
			() -> new BlockItem(DoomBlocks.DOOM_WALL2.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> DOOM_WALL3 = ITEMS.register("icon_wall3",
			() -> new BlockItem(DoomBlocks.DOOM_WALL3.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> DOOM_WALL4 = ITEMS.register("icon_wall4",
			() -> new BlockItem(DoomBlocks.DOOM_WALL4.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> DOOM_WALL5 = ITEMS.register("icon_wall5",
			() -> new BlockItem(DoomBlocks.DOOM_WALL5.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> DOOM_WALL6 = ITEMS.register("icon_wall6",
			() -> new BlockItem(DoomBlocks.DOOM_WALL6.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> DOOM_WALL7 = ITEMS.register("icon_wall7",
			() -> new BlockItem(DoomBlocks.DOOM_WALL7.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> DOOM_WALL8 = ITEMS.register("icon_wall8",
			() -> new BlockItem(DoomBlocks.DOOM_WALL8.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> DOOM_WALL9 = ITEMS.register("icon_wall9",
			() -> new BlockItem(DoomBlocks.DOOM_WALL9.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> DOOM_WALL10 = ITEMS.register("icon_wall10",
			() -> new BlockItem(DoomBlocks.DOOM_WALL10.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> DOOM_WALL11 = ITEMS.register("icon_wall11",
			() -> new BlockItem(DoomBlocks.DOOM_WALL11.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> DOOM_WALL12 = ITEMS.register("icon_wall12",
			() -> new BlockItem(DoomBlocks.DOOM_WALL12.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> DOOM_WALL13 = ITEMS.register("icon_wall13",
			() -> new BlockItem(DoomBlocks.DOOM_WALL13.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> DOOM_WALL14 = ITEMS.register("icon_wall14",
			() -> new BlockItem(DoomBlocks.DOOM_WALL14.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> DOOM_WALL15 = ITEMS.register("icon_wall15",
			() -> new BlockItem(DoomBlocks.DOOM_WALL15.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> DOOM_WALL16 = ITEMS.register("icon_wall16",
			() -> new BlockItem(DoomBlocks.DOOM_WALL16.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));

	public static final RegistryObject<Item> E1M1_1 = ITEMS.register("e1m1_block1",
			() -> new BlockItem(DoomBlocks.E1M1_1.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_2 = ITEMS.register("e1m1_block2",
			() -> new BlockItem(DoomBlocks.E1M1_2.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_3 = ITEMS.register("e1m1_block3",
			() -> new BlockItem(DoomBlocks.E1M1_3.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_4 = ITEMS.register("e1m1_block4",
			() -> new BlockItem(DoomBlocks.E1M1_4.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_5 = ITEMS.register("e1m1_block5",
			() -> new BlockItem(DoomBlocks.E1M1_5.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_6 = ITEMS.register("e1m1_block6",
			() -> new BlockItem(DoomBlocks.E1M1_6.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_7 = ITEMS.register("e1m1_block7",
			() -> new BlockItem(DoomBlocks.E1M1_7.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_8 = ITEMS.register("e1m1_block8",
			() -> new BlockItem(DoomBlocks.E1M1_8.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_9 = ITEMS.register("e1m1_block9",
			() -> new BlockItem(DoomBlocks.E1M1_9.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_10 = ITEMS.register("e1m1_block10",
			() -> new BlockItem(DoomBlocks.E1M1_10.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_11 = ITEMS.register("e1m1_block11",
			() -> new BlockItem(DoomBlocks.E1M1_11.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_12 = ITEMS.register("e1m1_block12",
			() -> new BlockItem(DoomBlocks.E1M1_12.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_13 = ITEMS.register("e1m1_block13",
			() -> new BlockItem(DoomBlocks.E1M1_13.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_14 = ITEMS.register("e1m1_block14",
			() -> new BlockItem(DoomBlocks.E1M1_14.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_15 = ITEMS.register("e1m1_block15",
			() -> new BlockItem(DoomBlocks.E1M1_15.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_16 = ITEMS.register("e1m1_block16",
			() -> new BlockItem(DoomBlocks.E1M1_16.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_17 = ITEMS.register("e1m1_block17",
			() -> new BlockItem(DoomBlocks.E1M1_17.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_18 = ITEMS.register("e1m1_block18",
			() -> new BlockItem(DoomBlocks.E1M1_18.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_19 = ITEMS.register("e1m1_block19",
			() -> new BlockItem(DoomBlocks.E1M1_19.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_20 = ITEMS.register("e1m1_block20",
			() -> new BlockItem(DoomBlocks.E1M1_20.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_21 = ITEMS.register("e1m1_block21",
			() -> new BlockItem(DoomBlocks.E1M1_21.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_22 = ITEMS.register("e1m1_block22",
			() -> new BlockItem(DoomBlocks.E1M1_22.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_23 = ITEMS.register("e1m1_block23",
			() -> new BlockItem(DoomBlocks.E1M1_23.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_24 = ITEMS.register("e1m1_block24",
			() -> new BlockItem(DoomBlocks.E1M1_24.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_25 = ITEMS.register("e1m1_block25",
			() -> new BlockItem(DoomBlocks.E1M1_25.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));
	public static final RegistryObject<Item> E1M1_26 = ITEMS.register("e1m1_block26",
			() -> new BlockItem(DoomBlocks.E1M1_26.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));

	public static final RegistryObject<Item> JUMP_PAD = ITEMS.register("jump_pad",
			() -> new BlockItem(DoomBlocks.JUMP_PAD.get(), new Item.Properties().tab(DoomMod.DoomBlockItemGroup)));

	// AMMO
	public static final RegistryObject<Item> SHOTGUN_SHELLS = ITEMS.register("shotgun_shells", () -> new ShellAmmo());
	public static final RegistryObject<Item> ARGENT_BOLT = ITEMS.register("argent_bolt", () -> new ArgentBolt());
	public static final RegistryObject<Item> UNMAKRY_BOLT = ITEMS.register("unmaykr_bolt", () -> new UnmaykrBolt());
	public static final RegistryObject<Item> ENERGY_CELLS = ITEMS.register("energy_cells", () -> new EnergyCell());
	public static final RegistryObject<Item> CHAINGUN_BULLETS = ITEMS.register("chaingunbullets",
			() -> new ChaingunAmmo());
	public static final RegistryObject<ClipAmmo> BULLETS = ITEMS.register("bullets", () -> new ClipAmmo());
	public static final RegistryObject<Item> BFG_CELL = ITEMS.register("bfg_cell", () -> new BFGCell());
	public static final RegistryObject<Item> ROCKET = ITEMS.register("rocket", () -> new Rocket());
	public static final RegistryObject<Item> GAS_BARREL = ITEMS.register("gas_barrel",
			() -> new Item(new Item.Properties().tab(DoomMod.DoomWeaponItemGroup)));

	// MISC
	public static final RegistryObject<Item> ICON_ICON = ITEMS.register("icon_icon",
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ARGENT_ENERGY = ITEMS.register("argent_energy",
			() -> new ArgentEnergyItem());
	public static final RegistryObject<Item> ARGENT_PLATE = ITEMS.register("argent_plate", () -> new ArgentPlateItem());
	public static final RegistryObject<Item> DAISY = ITEMS.register("daisy", () -> new DaisyItem());
	public static final RegistryObject<Item> E1M1_MUSIC_DISC = ITEMS.register("e1m1_music_disc",
			() -> new E1M1MusicDisc(DoomSounds.E1M1));
	public static final RegistryObject<Item> GEOF_MUSIC_DISC = ITEMS.register(
			"netherambient_geoffplaysguitar_music_disc",
			() -> new E1M1MusicDisc(DoomSounds.NETHERAMBIENT_GEOFFPLAYSGUITAR));
	public static final RegistryObject<Item> INMORTAL = ITEMS.register("inmortalsphere",
			() -> new InmortalSphereItem());
	public static final RegistryObject<Item> INVISIBLE = ITEMS.register("invisiblesphere",
			() -> new InvisibleSphereItem());
	public static final RegistryObject<Item> MEGA = ITEMS.register("megasphere", () -> new MegaSphereItem());
	public static final RegistryObject<Item> POWER = ITEMS.register("powersphere", () -> new PowerSphereItem());
	public static final RegistryObject<Item> SOULCUBE = ITEMS.register("soulcube", () -> new SoulCubeItem());
	public static final RegistryObject<Item> IMP_SPAWN_EGG = ITEMS.register("imp_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.IMP));
	public static final RegistryObject<Item> ARACHNOTRON_SPAWN_EGG = ITEMS.register("arachnotron_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.ARACHNOTRON));
	public static final RegistryObject<Item> LOST_SOUL_SPAWN_EGG = ITEMS.register("lost_soul_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.LOST_SOUL));
	public static final RegistryObject<Item> LOST_SOUL_ETERNAL_SPAWN_EGG = ITEMS.register("lost_soul_eternal_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.LOST_SOUL_ETERNAL));
	public static final RegistryObject<Item> PINKY_SPAWN_EGG = ITEMS.register("pinky_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.PINKY));
	public static final RegistryObject<Item> ARCHVILE_SPAWN_EGG = ITEMS.register("archvile_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.ARCHVILE));
	public static final RegistryObject<Item> BARON_SPAWN_EGG = ITEMS.register("baron_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.BARON));
	public static final RegistryObject<Item> CACODEMON_SPAWN_EGG = ITEMS.register("cacodemon_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.CACODEMON));
	public static final RegistryObject<Item> MANCUBUS_SPAWN_EGG = ITEMS.register("mancubus_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.MANCUBUS));
	public static final RegistryObject<Item> SPIDERMASTERMIND_SPAWN_EGG = ITEMS.register("spidermastermind_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.SPIDERMASTERMIND));
	public static final RegistryObject<Item> ZOMBIEMAN_SPAWN_EGG = ITEMS.register("zombieman_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.ZOMBIEMAN));
	public static final RegistryObject<Item> CHAINGUNNER_SPAWN_EGG = ITEMS.register("chaingunner_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.CHAINGUNNER));
	public static final RegistryObject<Item> HELLKNIGHT_SPAWN_EGG = ITEMS.register("hellknight_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.HELLKNIGHT));
	public static final RegistryObject<Item> MARAUDER_SPAWN_EGG = ITEMS.register("marauder_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.MARAUDER));
	public static final RegistryObject<Item> PAIN_SPAWN_EGG = ITEMS.register("pain_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.PAIN));
	public static final RegistryObject<Item> REVENANT_SPAWN_EGG = ITEMS.register("revenant_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.REVENANT));
	public static final RegistryObject<Item> SHOTGUNGUY_SPAWN_EGG = ITEMS.register("shotgunguy_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.SHOTGUNGUY));
	public static final RegistryObject<Item> CYBERDEMON_SPAWN_EGG = ITEMS.register("cyberdemon_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.CYBERDEMON));
	public static final RegistryObject<Item> ICON_SPAWN_EGG = ITEMS.register("icon_of_sin_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.ICONOFSIN));
	public static final RegistryObject<Item> UNWILLING_SPAWN_EGG = ITEMS.register("unwilling_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.UNWILLING));
	public static final RegistryObject<Item> MECHAZOMBIE_SPAWN_EGG = ITEMS.register("mechazombie_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.MECHAZOMBIE));
	public static final RegistryObject<Item> GORENEST_SPAWN_EGG = ITEMS.register("gorenest_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.GORE_NEST));
	public static final RegistryObject<Item> POSSESSED_SCIENTIST_SPAWN_EGG = ITEMS
			.register("possessed_scientist_spawn_egg", () -> new DoomSpawnEgg(DoomEntities.POSSESSEDSCIENTIST));
	public static final RegistryObject<Item> POSSESSED_SOLDIER_SPAWN_EGG = ITEMS.register("possessed_soldier_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.POSSESSEDSOLDIER));
	public static final RegistryObject<Item> HELLKNIGHT2016_SPAWN_EGG = ITEMS.register("hellknight2016_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.HELLKNIGHT2016));
	public static final RegistryObject<Item> GARGOYLE_SPAWN_EGG = ITEMS.register("gargoyle_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.GARGOYLE));
	public static final RegistryObject<Item> SPECTRE_SPAWN_EGG = ITEMS.register("spectre_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.SPECTRE));
	public static final RegistryObject<Item> CUEBALL_SPAWN_EGG = ITEMS.register("cueball_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.CUEBALL));
	public static final RegistryObject<Item> PROWLER_SPAWN_EGG = ITEMS.register("prowler_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.PROWLER));
	public static final RegistryObject<Item> DREADKNIGHT_SPAWN_EGG = ITEMS.register("dreadknight_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.DREADKNIGHT));
	public static final RegistryObject<Item> STONEIMP_SPAWN_EGG = ITEMS.register("stoneimp_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.IMP_STONE));
	public static final RegistryObject<Item> POSSESSED_WORKER_SPAWN_EGG = ITEMS.register("possessed_worker_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.POSSESSEDWORKER));
	public static final RegistryObject<Item> DOOMHUNTER_SPAWN_EGG = ITEMS.register("doom_hunter_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.DOOMHUNTER));
	public static final RegistryObject<Item> WHIPLASH_SPAWN_EGG = ITEMS.register("whiplash_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.WHIPLASH));
	public static final RegistryObject<Item> BARON2016_SPAWN_EGG = ITEMS.register("baron2016_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.BARON2016));
	public static final RegistryObject<Item> FIREBORNE_BARON_SPAWN_EGG = ITEMS.register("firebronebaron_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.FIREBARON));
	public static final RegistryObject<Item> ARMORED_BARON_SPAWN_EGG = ITEMS.register("armoredbaron_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.ARMORBARON));
	public static final RegistryObject<Item> MAYKR_DRONE_SPAWN_EGG = ITEMS.register("maykr_drone_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.MAYKRDRONE));
	public static final RegistryObject<Item> BLOOD_MAYKR_SPAWN_EGG = ITEMS.register("blood_maykr_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.BLOODMAYKR));
	public static final RegistryObject<Item> ARCH_MAKYR_SPAWN_EGG = ITEMS.register("arch_maykr_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.ARCHMAKER));
	public static final RegistryObject<Item> ARACHNOTRONETERNAL_SPAWN_EGG = ITEMS
			.register("arachnotroneternal_spawn_egg", () -> new DoomSpawnEgg(DoomEntities.ARACHNOTRONETERNAL));
	public static final RegistryObject<Item> SPIDERMASTERMIND2016_SPAWN_EGG = ITEMS
			.register("spidermastermind2016_spawn_egg", () -> new DoomSpawnEgg(DoomEntities.SPIDERMASTERMIND2016));
	public static final RegistryObject<Item> TENTACLE_SPAWN_EGG = ITEMS.register("tentacle_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.TENTACLE));
	public static final RegistryObject<Item> MOTHERDEMON_SPAWN_EGG = ITEMS.register("motherdemon_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.MOTHERDEMON));
	public static final RegistryObject<Item> TURRET_SPAWN_EGG = ITEMS.register("turret_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.TURRET));
	public static final RegistryObject<Item> SUMMONER_SPAWN_EGG = ITEMS.register("summoner_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.SUMMONER));
	public static final RegistryObject<Item> REVENANT2016_SPAWN_EGG = ITEMS.register("revenant2016_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.REVENANT2016));
	public static final RegistryObject<Item> GLADIATOR_SPAWN_EGG = ITEMS.register("gladiator_spawn_egg",
			() -> new DoomSpawnEgg(DoomEntities.GLADIATOR));

	// WEAPONS AND TOOLS
	public static final RegistryObject<Item> CRUCIBLESWORD = ITEMS.register("cruciblesword",
			() -> new SwordCrucibleItem());
	public static final RegistryObject<Item> ARGENT_AXE = ITEMS.register("argent_axe", () -> new ArgentAxe());
	public static final RegistryObject<Item> ARGENT_HOE = ITEMS.register("argent_hoe", () -> new ArgentHoe());
	public static final RegistryObject<Item> ARGENT_PAXEL = ITEMS.register("argent_paxel", () -> new ArgentPaxel());
	public static final RegistryObject<Item> ARGENT_PICKAXE = ITEMS.register("argent_pickaxe",
			() -> new ArgentPickaxe());
	public static final RegistryObject<Item> ARGENT_SHOVEL = ITEMS.register("argent_shovel", () -> new ArgentShovel());
	public static final RegistryObject<Item> ARGENT_SWORD = ITEMS.register("argent_sword", () -> new ArgentSword());
	public static final RegistryObject<Item> CHAINSAW = ITEMS.register("chainsaw", () -> new Chainsaw());
	public static final RegistryObject<Item> CHAINSAW_ETERNAL = ITEMS.register("chainsaweternal",
			() -> new ChainsawAnimated());
	public static final RegistryObject<Item> CHAINSAW64 = ITEMS.register("chainsaw64", () -> new Chainsaw());
	public static final RegistryObject<Item> SWORD_CLOSED = ITEMS.register("cruciblesword_closed",
			() -> new UnopenedItem());
	public static final RegistryObject<Item> AXE_OPEN = ITEMS.register("axe_marauder_open",
			() -> new AxeMarauderItem());
	public static final RegistryObject<Item> AXE_CLOSED = ITEMS.register("axe_marauder_closed",
			() -> new UnopenedItem());
	public static final RegistryObject<Item> SSG = ITEMS.register("supershotgun", () -> new SuperShotgun());
	public static final RegistryObject<Item> SG = ITEMS.register("shotgun", () -> new Shotgun());
	public static final RegistryObject<Item> BFG = ITEMS.register("bfg9000", () -> new BFG9000());
	public static final RegistryObject<Item> BFG_ETERNAL = ITEMS.register("bfg_eternal", () -> new BFG());
	public static final RegistryObject<Item> PLASMAGUN = ITEMS.register("plasmagun", () -> new PlasmaGun());
	public static final RegistryObject<Item> ROCKETLAUNCHER = ITEMS.register("rocketlauncher",
			() -> new RocketLauncher());
	public static final RegistryObject<Item> UNMAYKR = ITEMS.register("unmaykr", () -> new Unmaykr());
	public static final RegistryObject<Item> BALLISTA = ITEMS.register("ballista", () -> new Ballista());
	public static final RegistryObject<Item> CHAINGUN = ITEMS.register("chaingun", () -> new Chaingun());
	public static final RegistryObject<Item> PISTOL = ITEMS.register("pistol", () -> new PistolItem());
	public static final RegistryObject<Item> HEAVYCANNON = ITEMS.register("heavycannon", () -> new HeavyCannon());
	public static final RegistryObject<Item> SENTINELHAMMER = ITEMS.register("sentinelhammer",
			() -> new SentinelHammerItem());
	public static final RegistryObject<Item> DARKLORDCRUCIBLE = ITEMS.register("darklordcrucible",
			() -> new DarkLordCrucibleItem());
	public static final RegistryObject<Item> DSG = ITEMS.register("doomed_shotgun", () -> new DShotgun());
	public static final RegistryObject<Item> DGAUSS = ITEMS.register("doomed_gauss", () -> new DGauss());
	public static final RegistryObject<Item> GRENADE = ITEMS.register("doomed_grenade", () -> new GrenadeItem());
	public static final RegistryObject<Item> DPLASMARIFLE = ITEMS.register("doomed_plasma_rifle",
			() -> new DPlasmaRifle());

	// ARMOR
	public static final RegistryObject<Item> DOOM_HELMET = ITEMS.register("doom_helmet",
			() -> new DoomArmor(DoomArmorMaterial.DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> DOOM_CHESTPLATE = ITEMS.register("doom_chestplate",
			() -> new DoomArmor(DoomArmorMaterial.DOOM_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> DOOM_LEGGINGS = ITEMS.register("doom_leggings",
			() -> new DoomArmor(DoomArmorMaterial.DOOM_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> DOOM_BOOTS = ITEMS.register("doom_boots",
			() -> new DoomArmor(DoomArmorMaterial.DOOM_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> PRAETOR_DOOM_HELMET = ITEMS.register("praetor_doom_helmet",
			() -> new PraetorDoomArmor(DoomArmorMaterial.PRAETOR_DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> PRAETOR_DOOM_CHESTPLATE = ITEMS.register("praetor_doom_chestplate",
			() -> new PraetorDoomArmor(DoomArmorMaterial.PRAETOR_DOOM_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> PRAETOR_DOOM_LEGGINGS = ITEMS.register("praetor_doom_leggings",
			() -> new PraetorDoomArmor(DoomArmorMaterial.PRAETOR_DOOM_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> PRAETOR_DOOM_BOOTS = ITEMS.register("praetor_doom_boots",
			() -> new PraetorDoomArmor(DoomArmorMaterial.PRAETOR_DOOM_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> ASTRO_DOOM_HELMET = ITEMS.register("astro_doom_helmet",
			() -> new AstroDoomArmor(DoomArmorMaterial.ASTRO_DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> ASTRO_DOOM_CHESTPLATE = ITEMS.register("astro_doom_chestplate",
			() -> new AstroDoomArmor(DoomArmorMaterial.ASTRO_DOOM_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> ASTRO_DOOM_LEGGINGS = ITEMS.register("astro_doom_leggings",
			() -> new AstroDoomArmor(DoomArmorMaterial.ASTRO_DOOM_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> ASTRO_DOOM_BOOTS = ITEMS.register("astro_doom_boots",
			() -> new AstroDoomArmor(DoomArmorMaterial.ASTRO_DOOM_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> CRIMSON_DOOM_HELMET = ITEMS.register("crimson_doom_helmet",
			() -> new CrimsonDoomArmor(DoomArmorMaterial.CRIMSON_DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> CRIMSON_DOOM_CHESTPLATE = ITEMS.register("crimson_doom_chestplate",
			() -> new CrimsonDoomArmor(DoomArmorMaterial.CRIMSON_DOOM_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> CRIMSON_DOOM_LEGGINGS = ITEMS.register("crimson_doom_leggings",
			() -> new CrimsonDoomArmor(DoomArmorMaterial.CRIMSON_DOOM_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> CRIMSON_DOOM_BOOTS = ITEMS.register("crimson_doom_boots",
			() -> new CrimsonDoomArmor(DoomArmorMaterial.CRIMSON_DOOM_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> MIDNIGHT_DOOM_HELMET = ITEMS.register("midnight_doom_helmet",
			() -> new MidnightDoomArmor(DoomArmorMaterial.MIDNIGHT_DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> MIDNIGHT_DOOM_CHESTPLATE = ITEMS.register("midnight_doom_chestplate",
			() -> new MidnightDoomArmor(DoomArmorMaterial.MIDNIGHT_DOOM_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> MIDNIGHT_DOOM_LEGGINGS = ITEMS.register("midnight_doom_leggings",
			() -> new MidnightDoomArmor(DoomArmorMaterial.MIDNIGHT_DOOM_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> MIDNIGHT_DOOM_BOOTS = ITEMS.register("midnight_doom_boots",
			() -> new MidnightDoomArmor(DoomArmorMaterial.MIDNIGHT_DOOM_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> DEMONIC_DOOM_HELMET = ITEMS.register("demonic_doom_helmet",
			() -> new DemonicDoomArmor(DoomArmorMaterial.DEMONIC_DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> DEMONIC_DOOM_CHESTPLATE = ITEMS.register("demonic_doom_chestplate",
			() -> new DemonicDoomArmor(DoomArmorMaterial.DEMONIC_DOOM_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> DEMONIC_DOOM_LEGGINGS = ITEMS.register("demonic_doom_leggings",
			() -> new DemonicDoomArmor(DoomArmorMaterial.DEMONIC_DOOM_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> DEMONIC_DOOM_BOOTS = ITEMS.register("demonic_doom_boots",
			() -> new DemonicDoomArmor(DoomArmorMaterial.DEMONIC_DOOM_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> DEMONCIDE_DOOM_HELMET = ITEMS.register("demoncide_doom_helmet",
			() -> new DemoncideDoomArmor(DoomArmorMaterial.DEMONCIDE_DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> DEMONCIDE_DOOM_CHESTPLATE = ITEMS.register("demoncide_doom_chestplate",
			() -> new DemoncideDoomArmor(DoomArmorMaterial.DEMONCIDE_DOOM_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> DEMONCIDE_DOOM_LEGGINGS = ITEMS.register("demoncide_doom_leggings",
			() -> new DemoncideDoomArmor(DoomArmorMaterial.DEMONCIDE_DOOM_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> DEMONCIDE_DOOM_BOOTS = ITEMS.register("demoncide_doom_boots",
			() -> new DemoncideDoomArmor(DoomArmorMaterial.DEMONCIDE_DOOM_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> SENTINEL_DOOM_HELMET = ITEMS.register("sentinel_doom_helmet",
			() -> new SentinelDoomArmor(DoomArmorMaterial.SENTINEL_DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> SENTINEL_DOOM_CHESTPLATE = ITEMS.register("sentinel_doom_chestplate",
			() -> new SentinelDoomArmor(DoomArmorMaterial.SENTINEL_DOOM_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> SENTINEL_DOOM_LEGGINGS = ITEMS.register("sentinel_doom_leggings",
			() -> new SentinelDoomArmor(DoomArmorMaterial.SENTINEL_DOOM_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> SENTINEL_DOOM_BOOTS = ITEMS.register("sentinel_doom_boots",
			() -> new SentinelDoomArmor(DoomArmorMaterial.SENTINEL_DOOM_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> EMBER_DOOM_HELMET = ITEMS.register("ember_doom_helmet",
			() -> new EmberDoomArmor(DoomArmorMaterial.EMBER_DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> EMBER_DOOM_CHESTPLATE = ITEMS.register("ember_doom_chestplate",
			() -> new EmberDoomArmor(DoomArmorMaterial.EMBER_DOOM_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> EMBER_DOOM_LEGGINGS = ITEMS.register("ember_doom_leggings",
			() -> new EmberDoomArmor(DoomArmorMaterial.EMBER_DOOM_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> EMBER_DOOM_BOOTS = ITEMS.register("ember_doom_boots",
			() -> new EmberDoomArmor(DoomArmorMaterial.EMBER_DOOM_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> ZOMBIE_DOOM_HELMET = ITEMS.register("zombie_doom_helmet",
			() -> new ZombieDoomArmor(DoomArmorMaterial.ZOMBIE_DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> ZOMBIE_DOOM_CHESTPLATE = ITEMS.register("zombie_doom_chestplate",
			() -> new ZombieDoomArmor(DoomArmorMaterial.ZOMBIE_DOOM_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> ZOMBIE_DOOM_LEGGINGS = ITEMS.register("zombie_doom_leggings",
			() -> new ZombieDoomArmor(DoomArmorMaterial.ZOMBIE_DOOM_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> ZOMBIE_DOOM_BOOTS = ITEMS.register("zombie_doom_boots",
			() -> new ZombieDoomArmor(DoomArmorMaterial.ZOMBIE_DOOM_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> PHOBOS_DOOM_HELMET = ITEMS.register("phobos_doom_helmet",
			() -> new PhobosDoomArmor(DoomArmorMaterial.PHOBOS_DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> PHOBOS_DOOM_CHESTPLATE = ITEMS.register("phobos_doom_chestplate",
			() -> new PhobosDoomArmor(DoomArmorMaterial.PHOBOS_DOOM_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> PHOBOS_DOOM_LEGGINGS = ITEMS.register("phobos_doom_leggings",
			() -> new PhobosDoomArmor(DoomArmorMaterial.PHOBOS_DOOM_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> PHOBOS_DOOM_BOOTS = ITEMS.register("phobos_doom_boots",
			() -> new PhobosDoomArmor(DoomArmorMaterial.PHOBOS_DOOM_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> NIGHTMARE_DOOM_HELMET = ITEMS.register("nightmare_doom_helmet",
			() -> new NightmareDoomArmor(DoomArmorMaterial.NIGHTMARE_DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> NIGHTMARE_DOOM_CHESTPLATE = ITEMS.register("nightmare_doom_chestplate",
			() -> new NightmareDoomArmor(DoomArmorMaterial.NIGHTMARE_DOOM_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> NIGHTMARE_DOOM_LEGGINGS = ITEMS.register("nightmare_doom_leggings",
			() -> new NightmareDoomArmor(DoomArmorMaterial.NIGHTMARE_DOOM_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> NIGHTMARE_DOOM_BOOTS = ITEMS.register("nightmare_doom_boots",
			() -> new NightmareDoomArmor(DoomArmorMaterial.NIGHTMARE_DOOM_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> PURPLEPONY_DOOM_HELMET = ITEMS.register("purplepony_doom_helmet",
			() -> new PurplePonyDoomArmor(DoomArmorMaterial.PURPLEPONY_DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> PURPLEPONY_DOOM_CHESTPLATE = ITEMS.register("purplepony_doom_chestplate",
			() -> new PurplePonyDoomArmor(DoomArmorMaterial.PURPLEPONY_DOOM_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> PURPLEPONY_DOOM_LEGGINGS = ITEMS.register("purplepony_doom_leggings",
			() -> new PurplePonyDoomArmor(DoomArmorMaterial.PURPLEPONY_DOOM_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> PURPLEPONY_DOOM_BOOTS = ITEMS.register("purplepony_doom_boots",
			() -> new PurplePonyDoomArmor(DoomArmorMaterial.PURPLEPONY_DOOM_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> DOOMICORN_DOOM_HELMET = ITEMS.register("doomicorn_doom_helmet",
			() -> new DoomicornDoomArmor(DoomArmorMaterial.DOOMICORN_DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> DOOMICORN_DOOM_CHESTPLATE = ITEMS.register("doomicorn_doom_chestplate",
			() -> new DoomicornDoomArmor(DoomArmorMaterial.DOOMICORN_DOOM_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> DOOMICORN_DOOM_LEGGINGS = ITEMS.register("doomicorn_doom_leggings",
			() -> new DoomicornDoomArmor(DoomArmorMaterial.DOOMICORN_DOOM_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> DOOMICORN_DOOM_BOOTS = ITEMS.register("doomicorn_doom_boots",
			() -> new DoomicornDoomArmor(DoomArmorMaterial.DOOMICORN_DOOM_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> GOLD_DOOM_HELMET = ITEMS.register("gold_doom_helmet",
			() -> new GoldDoomArmor(DoomArmorMaterial.GOLD_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> GOLD_DOOM_CHESTPLATE = ITEMS.register("gold_doom_chestplate",
			() -> new GoldDoomArmor(DoomArmorMaterial.GOLD_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> GOLD_DOOM_LEGGINGS = ITEMS.register("gold_doom_leggings",
			() -> new GoldDoomArmor(DoomArmorMaterial.GOLD_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> GOLD_DOOM_BOOTS = ITEMS.register("gold_doom_boots",
			() -> new GoldDoomArmor(DoomArmorMaterial.GOLD_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> TWENTY_FIVE_DOOM_HELMET = ITEMS.register("twenty_five_helmet",
			() -> new TwentyFiveDoomArmor(DoomArmorMaterial.TWENTY_FIVE_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> TWENTY_FIVE_DOOM_CHESTPLATE = ITEMS.register("twenty_five_chestplate",
			() -> new TwentyFiveDoomArmor(DoomArmorMaterial.TWENTY_FIVE_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> TWENTY_FIVE_DOOM_LEGGINGS = ITEMS.register("twenty_five_leggings",
			() -> new TwentyFiveDoomArmor(DoomArmorMaterial.TWENTY_FIVE_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> TWENTY_FIVE_DOOM_BOOTS = ITEMS.register("twenty_five_boots",
			() -> new TwentyFiveDoomArmor(DoomArmorMaterial.TWENTY_FIVE_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> BRONZE_DOOM_HELMET = ITEMS.register("bronze_doom_helmet",
			() -> new BronzeDoomArmor(DoomArmorMaterial.BRONZE_DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> BRONZE_DOOM_CHESTPLATE = ITEMS.register("bronze_doom_chestplate",
			() -> new BronzeDoomArmor(DoomArmorMaterial.BRONZE_DOOM_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> BRONZE_DOOM_LEGGINGS = ITEMS.register("bronze_doom_leggings",
			() -> new BronzeDoomArmor(DoomArmorMaterial.BRONZE_DOOM_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> BRONZE_DOOM_BOOTS = ITEMS.register("bronze_doom_boots",
			() -> new BronzeDoomArmor(DoomArmorMaterial.BRONZE_DOOM_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> CULTIST_DOOM_HELMET = ITEMS.register("cultist_doom_helmet",
			() -> new CultistDoomArmor(DoomArmorMaterial.CULTIST_DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> CULTIST_DOOM_CHESTPLATE = ITEMS.register("cultist_doom_chestplate",
			() -> new CultistDoomArmor(DoomArmorMaterial.CULTIST_DOOM_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> CULTIST_DOOM_LEGGINGS = ITEMS.register("cultist_doom_leggings",
			() -> new CultistDoomArmor(DoomArmorMaterial.CULTIST_DOOM_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> CULTIST_DOOM_BOOTS = ITEMS.register("cultist_doom_boots",
			() -> new CultistDoomArmor(DoomArmorMaterial.CULTIST_DOOM_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> MAYKR_DOOM_HELMET = ITEMS.register("maykr_doom_helmet",
			() -> new MaykrDoomArmor(DoomArmorMaterial.MAYKR_DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> MAYKR_DOOM_CHESTPLATE = ITEMS.register("maykr_doom_chestplate",
			() -> new MaykrDoomArmor(DoomArmorMaterial.MAYKR_DOOM_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> MAYKR_DOOM_LEGGINGS = ITEMS.register("maykr_doom_leggings",
			() -> new MaykrDoomArmor(DoomArmorMaterial.MAYKR_DOOM_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> MAYKR_DOOM_BOOTS = ITEMS.register("maykr_doom_boots",
			() -> new MaykrDoomArmor(DoomArmorMaterial.MAYKR_DOOM_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> PAINTER_DOOM_HELMET = ITEMS.register("painter_doom_helmet",
			() -> new PainterDoomArmor(DoomArmorMaterial.PAINTER_DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> PAINTER_DOOM_CHESTPLATE = ITEMS.register("painter_doom_chestplate",
			() -> new PainterDoomArmor(DoomArmorMaterial.PAINTER_DOOM_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> CLASSIC_DOOM_HELMET = ITEMS.register("classic_doom_helmet",
			() -> new ClassicDoomArmor(DoomArmorMaterial.CLASSIC_DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> CLASSIC_DOOM_CHESTPLATE = ITEMS.register("classic_doom_chestplate",
			() -> new ClassicDoomArmor(DoomArmorMaterial.CLASSIC_DOOM_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> CLASSIC_DOOM_LEGGINGS = ITEMS.register("classic_doom_leggings",
			() -> new ClassicDoomArmor(DoomArmorMaterial.CLASSIC_DOOM_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> CLASSIC_RED_DOOM_CHESTPLATE = ITEMS.register("classic_red_chestplate",
			() -> new ClassicRedDoomArmor(DoomArmorMaterial.CLASSIC_RED_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> CLASSIC_RED_DOOM_LEGGINGS = ITEMS.register("classic_red_leggings",
			() -> new ClassicRedDoomArmor(DoomArmorMaterial.CLASSIC_RED_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> CLASSIC_INDIGO_DOOM_CHESTPLATE = ITEMS.register("classic_black_chestplate",
			() -> new ClassicIndigoDoomArmor(DoomArmorMaterial.CLASSIC_INDIGO_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> CLASSIC_INDIGO_DOOM_LEGGINGS = ITEMS.register("classic_black_leggings",
			() -> new ClassicIndigoDoomArmor(DoomArmorMaterial.CLASSIC_INDIGO_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> CLASSIC_BRONZE_DOOM_CHESTPLATE = ITEMS.register(
			"classic_bronze_chestplate",
			() -> new ClassicBronzeDoomArmor(DoomArmorMaterial.CLASSIC_BRONZE_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> CLASSIC_BRONZE_DOOM_LEGGINGS = ITEMS.register("classic_bronze_leggings",
			() -> new ClassicBronzeDoomArmor(DoomArmorMaterial.CLASSIC_BRONZE_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> CLASSIC_DOOM_BOOTS = ITEMS.register("classic_doom_boots",
			() -> new ClassicDoomArmor(DoomArmorMaterial.CLASSIC_DOOM_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> MULLET_DOOM_HELMET1 = ITEMS.register("redneck_doom1_helmet",
			() -> new MulletDoomArmor(DoomArmorMaterial.REDNECK1_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> MULLET_DOOM_CHESTPLATE1 = ITEMS.register("redneck_doom1_chestplate",
			() -> new MulletDoomArmor(DoomArmorMaterial.REDNECK1_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> MULLET_DOOM_CHESTPLATE2 = ITEMS.register("redneck_doom2_chestplate",
			() -> new Mullet2DoomArmor(DoomArmorMaterial.REDNECK2_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> MULLET_DOOM_CHESTPLATE3 = ITEMS.register("redneck_doom3_chestplate",
			() -> new Mullet3DoomArmor(DoomArmorMaterial.REDNECK3_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> MULLET_DOOM_LEGGINGS1 = ITEMS.register("redneck_doom1_leggings",
			() -> new MulletDoomArmor(DoomArmorMaterial.REDNECK1_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> MULLET_DOOM_BOOTS1 = ITEMS.register("redneck_doom1_boots",
			() -> new MulletDoomArmor(DoomArmorMaterial.REDNECK1_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> HOTROD_HELMET = ITEMS.register("hotrod_helmet",
			() -> new HotrodDoomArmor(DoomArmorMaterial.HOTROD_DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> HOTROD_CHESTPLATE = ITEMS.register("hotrod_chestplate",
			() -> new HotrodDoomArmor(DoomArmorMaterial.HOTROD_DOOM_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> HOTROD_LEGGINGS = ITEMS.register("hotrod_leggings",
			() -> new HotrodDoomArmor(DoomArmorMaterial.HOTROD_DOOM_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> HOTROD_BOOTS = ITEMS.register("hotrod_boots",
			() -> new HotrodDoomArmor(DoomArmorMaterial.HOTROD_DOOM_ARMOR, EquipmentSlot.FEET));
	public static final RegistryObject<Item> SANTA_HELMET = ITEMS.register("santa_helmet",
			() -> new SantaDoomArmor(DoomArmorMaterial.DOOM_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> DARKLORD_HELMET = ITEMS.register("darklord_helmet",
			() -> new DarkLordArmor(DoomArmorMaterial.DARKLORD_ARMOR, EquipmentSlot.HEAD));
	public static final RegistryObject<Item> DARKLORD_CHESTPLATE = ITEMS.register("darklord_chestplate",
			() -> new DarkLordArmor(DoomArmorMaterial.DARKLORD_ARMOR, EquipmentSlot.CHEST));
	public static final RegistryObject<Item> DARKLORD_LEGGINGS = ITEMS.register("darklord_leggings",
			() -> new DarkLordArmor(DoomArmorMaterial.DARKLORD_ARMOR, EquipmentSlot.LEGS));
	public static final RegistryObject<Item> DARKLORD_BOOTS = ITEMS.register("darklord_boots",
			() -> new DarkLordArmor(DoomArmorMaterial.DARKLORD_ARMOR, EquipmentSlot.FEET));
}