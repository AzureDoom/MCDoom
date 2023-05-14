package mod.azure.doom.config;

import mod.azure.azurelib.config.Config;
import mod.azure.azurelib.config.Configurable;
import mod.azure.doom.DoomMod;

@Config(id = DoomMod.MODID)
public class DoomConfig {

	@Configurable
	public boolean enable_all_villager_trades = true;
	@Configurable
	public boolean enable_weaponsmith_trades = true;
	@Configurable
	public boolean enable_toolsmith_trades = true;
	@Configurable
	public boolean enable_mason_trades = true;
	@Configurable
	public boolean enable_soulcube_effects = true;
	@Configurable
	public boolean enable_daisy_effects = true;

	@Configurable
	public int doom_armor_head_stat = 25;
	@Configurable
	public int doom_armor_chestplate_stat = 18;
	@Configurable
	public int doom_armor_leggings_stat = 20;
	@Configurable
	public int doom_armor_boots_stat = 15;
	@Configurable
	public float doom_armor_toughness = 24;
	@Configurable
	public float doom_armor_knockbackResistance = 4;

	@Configurable
	public int marauder_max_uses = 24;
	@Configurable
	public float marauder_axe_item_damage = 200F;
	@Configurable
	public int crucible_max_uses = 24;
	@Configurable
	public float crucible_damage = 200F;
	@Configurable
	public int darkcrucible_max_uses = 24;
	@Configurable
	public float darkcrucible_damage = 200F;
	@Configurable
	public int sentinelhammer_max_uses = 24;
	@Configurable
	public float sentinelhammer_damage = 25;
	@Configurable
	public boolean enable_block_breaking = false;
	@Configurable
	public boolean enable_noncenter = false;
	@Configurable
	public float argent_bolt_damage = 14.5F;
	@Configurable
	public float bfgball_damage = 100F;
	@Configurable
	public float bfgball_damage_dragon = 30F;
	@Configurable
	public float bfgball_damage_aoe = 10F;
	@Configurable
	public String[] bfg_damage_mob_whitelist = { "" };
	@Configurable
	public float bullet_damage = 5.5F;
	@Configurable
	public float chaingun_bullet_damage = 5.5F;
	@Configurable
	public float energycell_damage = 1.5F;
	@Configurable
	public float rocket_damage = 20F;
	@Configurable
	public float shotgun_damage = 10F;
	@Configurable
	public float unmaykr_damage = 2.5F;
	@Configurable
	public float grenade_damage = 30F;
	@Configurable
	public float chainsaw_damage = 2F;
	@Configurable
	public double max_meathook_distance = 32;

	@Configurable
	public String[] imp_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int imp_spawn_weight = 15;
	@Configurable
	public int imp_min_group = 1;
	@Configurable
	public int imp_max_group = 4;

	@Configurable
	public String[] pinky_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int pinky_spawn_weight = 15;
	@Configurable
	public int pinky_min_group = 2;
	@Configurable
	public int pinky_max_group = 4;

	@Configurable
	public String[] spectre_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int spectre_spawn_weight = 15;
	@Configurable
	public int spectre_min_group = 2;
	@Configurable
	public int spectre_max_group = 4;

	@Configurable
	public String[] lost_soul_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int lost_soul_spawn_weight = 25;
	@Configurable
	public int lost_soul_min_group = 1;
	@Configurable
	public int lost_soul_max_group = 3;

	@Configurable
	public String[] cacodemon_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int cacodemon_spawn_weight = 4;
	@Configurable
	public int cacodemon_min_group = 1;
	@Configurable
	public int cacodemon_max_group = 2;

	@Configurable
	public String[] archvile_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int archvile_spawn_weight = 4;
	@Configurable
	public int archvile_min_group = 1;
	@Configurable
	public int archvile_max_group = 2;

	@Configurable
	public String[] baron_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int baron_spawn_weight = 4;
	@Configurable
	public int baron_min_group = 1;
	@Configurable
	public int baron_max_group = 1;

	@Configurable
	public String[] mancubus_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int mancubus_spawn_weight = 4;
	@Configurable
	public int mancubus_min_group = 1;
	@Configurable
	public int mancubus_max_group = 1;

	@Configurable
	public String[] revenant_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int revenant_spawn_weight = 4;
	@Configurable
	public int revenant_min_group = 1;
	@Configurable
	public int revenant_max_group = 4;

	@Configurable
	public String[] zombieman_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int zombieman_spawn_weight = 15;
	@Configurable
	public int zombieman_min_group = 1;
	@Configurable
	public int zombieman_max_group = 4;

	@Configurable
	public String[] arachnotron_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int arachnotron_spawn_weight = 15;
	@Configurable
	public int arachnotron_min_group = 1;
	@Configurable
	public int arachnotron_max_group = 4;

	@Configurable
	public String[] gargoyle_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int gargoyle_spawn_weight = 15;
	@Configurable
	public int gargoyle_min_group = 1;
	@Configurable
	public int gargoyle_max_group = 4;

	@Configurable
	public String[] chaingunner_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int chaingunner_spawn_weight = 15;
	@Configurable
	public int chaingunner_min_group = 1;
	@Configurable
	public int chaingunner_max_group = 4;

	@Configurable
	public String[] shotgunguy_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int shotgunguy_spawn_weight = 15;
	@Configurable
	public int shotgunguy_min_group = 1;
	@Configurable
	public int shotgunguy_max_group = 4;

	@Configurable
	public String[] marauder_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int marauder_spawn_weight = 3;
	@Configurable
	public int marauder_min_group = 1;
	@Configurable
	public int marauder_max_group = 1;

	@Configurable
	public String[] pain_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int pain_spawn_weight = 4;
	@Configurable
	public int pain_min_group = 1;
	@Configurable
	public int pain_max_group = 2;

	@Configurable
	public String[] hellknight_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int hellknight_spawn_weight = 4;
	@Configurable
	public int hellknight_min_group = 1;
	@Configurable
	public int hellknight_max_group = 2;

	@Configurable
	public String[] hellknight2016_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int hellknight2016_spawn_weight = 4;
	@Configurable
	public int hellknight2016_min_group = 1;
	@Configurable
	public int hellknight2016_max_group = 2;

	@Configurable
	public String[] cyberdemon_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int cyberdemon_spawn_weight = 4;
	@Configurable
	public int cyberdemon_min_group = 1;
	@Configurable
	public int cyberdemon_max_group = 2;

	@Configurable
	public String[] unwilling_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int unwilling_spawn_weight = 15;
	@Configurable
	public int unwilling_min_group = 1;
	@Configurable
	public int unwilling_max_group = 4;

	@Configurable
	public String[] possessed_scientist_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int possessed_scientist_spawn_weight = 15;
	@Configurable
	public int possessed_scientist_min_group = 1;
	@Configurable
	public int possessed_scientist_max_group = 4;

	@Configurable
	public String[] possessed_soldier_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int possessed_soldier_spawn_weight = 15;
	@Configurable
	public int possessed_soldier_min_group = 1;
	@Configurable
	public int possessed_soldier_max_group = 4;

	@Configurable
	public String[] mechazombie_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int mechazombie_spawn_weight = 15;
	@Configurable
	public int mechazombie_min_group = 1;
	@Configurable
	public int mechazombie_max_group = 4;

	@Configurable
	public String[] cueball_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int cueball_spawn_weight = 15;
	@Configurable
	public int cueball_min_group = 1;
	@Configurable
	public int cueball_max_group = 2;

	@Configurable
	public String[] prowler_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int prowler_spawn_weight = 15;
	@Configurable
	public int prowler_min_group = 1;
	@Configurable
	public int prowler_max_group = 2;

	@Configurable
	public String[] impstone_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int impstone_spawn_weight = 15;
	@Configurable
	public int impstone_min_group = 1;
	@Configurable
	public int impstone_max_group = 2;

	@Configurable
	public String[] gorenest_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int gorenest_spawn_weight = 4;
	@Configurable
	public int gorenest_min_group = 1;
	@Configurable
	public int gorenest_max_group = 1;

	@Configurable
	public String[] tentacle_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int tentacle_spawn_weight = 4;
	@Configurable
	public int tentacle_min_group = 1;
	@Configurable
	public int tentacle_max_group = 1;

	@Configurable
	public String[] turret_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int turret_spawn_weight = 4;
	@Configurable
	public int turret_min_group = 1;
	@Configurable
	public int turret_max_group = 4;

	@Configurable
	public String[] spider_mastermind_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int spider_mastermind_spawn_weight = 4;
	@Configurable
	public int spider_mastermind_min_group = 1;
	@Configurable
	public int spider_mastermind_max_group = 1;

	@Configurable
	public String[] whiplash_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int whiplash_spawn_weight = 4;
	@Configurable
	public int whiplash_min_group = 1;
	@Configurable
	public int whiplash_max_group = 1;

	@Configurable
	public String[] doomhunter_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int doomhunter_spawn_weight = 4;
	@Configurable
	public int doomhunter_min_group = 1;
	@Configurable
	public int doomhunter_max_group = 1;

	@Configurable
	public String[] possessed_worker_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int possessed_worker_spawn_weight = 15;
	@Configurable
	public int possessed_worker_min_group = 1;
	@Configurable
	public int possessed_worker_max_group = 4;

	@Configurable
	public String[] armoredbaron_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int armoredbaron_spawn_weight = 4;
	@Configurable
	public int armoredbaron_min_group = 1;
	@Configurable
	public int armoredbaron_max_group = 1;

	@Configurable
	public String[] summoner_biomes = { "minecraft:nether_wastes", "minecraft:soul_sand_valley", "minecraft:crimson_forest", "minecraft:warped_forest", "minecraft:basalt_deltas" };
	@Configurable
	public int summoner_spawn_weight = 4;
	@Configurable
	public int summoner_min_group = 1;
	@Configurable
	public int summoner_max_group = 2;

	@Configurable

	public String[] motherdemon_biomes = { "" };
	@Configurable
	public int motherdemon_spawn_weight = 1;
	@Configurable
	public int motherdemon_min_group = 1;
	@Configurable
	public int motherdemon_max_group = 1;

	@Configurable
	public String[] maykrdrone_biomes = { "minecraft:small_end_islands", "minecraft:end_midlands", "minecraft:end_barrens", "minecraft:end_highlands" };
	@Configurable
	public int maykrdrone_spawn_weight = 15;
	@Configurable
	public int maykrdrone_min_group = 1;
	@Configurable
	public int maykrdrone_max_group = 2;

	@Configurable
	public String[] bloodmaykr_biomes = { "minecraft:small_end_islands", "minecraft:end_midlands", "minecraft:end_barrens", "minecraft:end_highlands" };
	@Configurable
	public int bloodmaykr_spawn_weight = 6;
	@Configurable
	public int bloodmaykr_min_group = 1;
	@Configurable
	public int bloodmaykr_max_group = 2;

	@Configurable
	public String[] archmaykr_biomes = { "" };
	@Configurable
	public int archmaykr_spawn_weight = 1;
	@Configurable
	public int archmaykr_min_group = 1;
	@Configurable
	public int archmaykr_max_group = 1;

	@Configurable
	public String[] gladiator_biomes = { "" };
	@Configurable
	public int gladiator_spawn_weight = 1;
	@Configurable
	public int gladiator_min_group = 1;
	@Configurable
	public int gladiator_max_group = 1;

	@Configurable
	public double motherdemon_health = 500;
	@Configurable
	public float motherdemon_ranged_damage = 14;
	@Configurable
	public float motherdemon_phaseone_damage_boos = 14;

	@Configurable
	public double cueball_health = 1;

	@Configurable
	public double tentacle_health = 5;
	@Configurable
	public float tentacle_melee_damage = 1;

	@Configurable
	public double turret_health = 5;
	@Configurable
	public float turret_ranged_damage = 6;

	@Configurable
	public double icon_health = 1000;
	@Configurable
	public float icon_melee_damage = 7;
	@Configurable
	public float icon_phaseone_damage_boos = 7;
	@Configurable
	public String[] icon_wave_entries = { "doom:gladiator", "doom:mancubus", "doom:mancubus", "doom:marauder", "doom:marauder", "doom:firebronebaron", "doom:baron2016", "doom:baron2016", "doom:baron2016", "doom:baron2016", "doom:whiplash", "doom:whiplash", "doom:whiplash", "doom:whiplash", "doom:whiplash", "doom:gargoyle", "doom:gargoyle", "doom:gargoyle", "doom:gargoyle", "doom:gargoyle", "doom:gargoyle", "doom:gargoyle", "doom:gargoyle", "doom:gargoyle", "doom:gargoyle", "doom:cacodemon",
			"doom:cacodemon", "doom:cacodemon", "doom:cacodemon", "doom:cacodemon", "doom:cacodemon", "doom:cacodemon", "doom:cacodemon", "doom:cacodemon", "doom:cacodemon", "doom:painelemental", "doom:painelemental", "doom:painelemental", "doom:painelemental", "doom:painelemental", "doom:painelemental", "doom:painelemental", "doom:painelemental", "doom:painelemental", "doom:painelemental", "doom:imp", "doom:imp", "doom:imp", "doom:imp", "doom:imp", "doom:imp", "doom:imp", "doom:imp",
			"doom:imp", "doom:imp", "doom:prowler", "doom:prowler", "doom:prowler", "doom:prowler", "doom:prowler", "doom:prowler", "doom:prowler", "doom:prowler", "doom:prowler", "doom:prowler", "doom:imp", "doom:imp", "doom:imp", "doom:imp", "doom:imp", "doom:imp", "doom:imp", "doom:imp", "doom:imp", "doom:imp", "doom:imp", "doom:pinky", "doom:pinky", "doom:pinky", "doom:pinky", "doom:pinky", "doom:pinky", "doom:pinky", "doom:pinky", "doom:pinky", "doom:pinky", "doom:lost_soul",
			"doom:lost_soul", "doom:lost_soul", "doom:lost_soul", "doom:lost_soul", "doom:lost_soul", "doom:lost_soul", "doom:lost_soul", "doom:lost_soul", "doom:lost_soul" };

	@Configurable
	public double imp_health = 30;
	@Configurable
	public float imp_ranged_damage = 4;

	@Configurable
	public double pinky_health = 75;
	@Configurable
	public double pinky_melee_damage = 3;

	@Configurable
	public double spectre_health = 75;
	@Configurable
	public double spectre_melee_damage = 3;

	@Configurable
	public double lost_soul_health = 10;
	@Configurable
	public double lost_soul_melee_damage = 1;

	@Configurable
	public double cacodemon_health = 80;
	@Configurable
	public float cacodemon_ranged_damage = 5;

	@Configurable
	public double archvile_health = 100;
	@Configurable
	public float archvile_ranged_damage = 5;

	@Configurable
	public double summoner_health = 100;
	@Configurable
	public float summoner_ranged_damage = 3;
	@Configurable
	public String[] summoner__wave_entries = { "doom:imp", "doom:lost_soul", "doom:stone_imp" };

	@Configurable
	public double prowler_health = 15;
	@Configurable
	public float prowler_ranged_damage = 4;
	@Configurable
	public double prowler_melee_damage = 4;

	@Configurable
	public double maykrdrone_health = 50;
	@Configurable
	public float maykrdrone_ranged_damage = 5;

	@Configurable
	public double bloodmaykr_health = 100;
	@Configurable
	public float bloodmaykr_ranged_damage = 10;

	@Configurable
	public double archmaykr_health = 400;
	@Configurable
	public float archmaykr_ranged_damage = 14;
	@Configurable
	public float archmaykr_phaseone_damage_boost = 14;
	@Configurable
	public float archmaykr_phasetwo_damage_boost = 28;
	@Configurable
	public float archmaykr_phasethree_damage_boost = 42;
	@Configurable
	public float archmaykr_phasefour_damage_boost = 56;

	@Configurable
	public double baron_health = 180;
	@Configurable
	public float baron_ranged_damage = 6;
	@Configurable
	public double baron_melee_damage = 7;

	@Configurable
	public double gladiator_health = 240;
	@Configurable
	public float gladiator_ranged_damage = 6;
	@Configurable
	public float gladiator_phaseone_damage_boost = 6;
	@Configurable
	public double gladiator_melee_damage = 7;

	@Configurable
	public double mancubus_health = 80;
	@Configurable
	public double mancubus_melee_damage = 4;
	@Configurable
	public float mancubus_ranged_damage = 6;

	@Configurable
	public double revenant_health = 45;
	@Configurable
	public float revenant_ranged_damage = 5;

	@Configurable
	public double spider_mastermind_health = 300;
	@Configurable
	public float spider_mastermind_ranged_damage = 7;
	@Configurable
	public double spider_mastermind_melee_damage = 4;

	@Configurable
	public double zombieman_health = 15;

	@Configurable
	public double arachnotron_health = 30;
	@Configurable
	public float arachnotron_ranged_damage = 5;

	@Configurable
	public double impstone_health = 15;
	@Configurable
	public double impstone_melee_damage = 2;

	@Configurable
	public double gargoyle_health = 30;
	@Configurable
	public float gargoyle_ranged_damage = 5;
	@Configurable
	public double gargoyle_melee_damage = 2;

	@Configurable
	public double chaingunner_health = 15;

	@Configurable
	public double shotgunguy_health = 15;

	@Configurable
	public double marauder_health = 300;
	@Configurable
	public double marauder_axe_damage = 6;
	@Configurable
	public float marauder_ssgdamage = 10.5F;

	@Configurable
	public double pain_health = 80;

	@Configurable
	public double hellknight_health = 90;
	@Configurable
	public float hellknight_ranged_damage = 6;
	@Configurable
	public double hellknight_melee_damage = 4;

	@Configurable
	public double hellknight2016_health = 90;
	@Configurable
	public double hellknight2016_melee_damage = 4;

	@Configurable
	public double cyberdemon_health = 300;
	@Configurable
	public float cyberdemon_ranged_damage = 9;
	@Configurable
	public double cyberdemon_melee_damage = 7;

	@Configurable
	public double doomhunter_health = 150;
	@Configurable
	public float doomhunter_ranged_damage = 7;
	@Configurable
	public double doomhunter_melee_damage = 5;
	@Configurable
	public float doomhunter_extra_phase_two_damage = 5;

	@Configurable
	public double whiplash_health = 90;
	@Configurable
	public double whiplash_melee_damage = 4;

	@Configurable
	public double armoredbaron_health = 240;
	@Configurable
	public double armoredbaron_melee_damage = 7;

	@Configurable
	public double unwilling_health = 15;
	@Configurable
	public double unwilling_melee_damage = 2;

	@Configurable
	public double possessed_scientist_health = 15;
	@Configurable
	public double possessed_scientist_melee_damage = 2;

	@Configurable
	public double possessed_soldier_health = 15;
	@Configurable
	public float possessed_soldier_ranged_damage = 2;

	@Configurable
	public double mechazombie_health = 25;
	@Configurable
	public float mechazombie_ranged_damage = 3;

	@Configurable
	public double gorenest_health = 5;
	@Configurable
	public String[] gorenest_wave_entries = { "doom:hellknight", "doom:possessed_scientist", "doom:imp", "doom:pinky", "doom:cacodemon", "doom:chaingunner", "doom:gargoyle", "doom:hellknight2016", "doom:lost_soul", "doom:possessed_soldier", "doom:shotgunguy", "doom:unwilling", "doom:zombieman", "doom:arachnotron", "doom:archvile", "doom:mechazombie", "doom:painelemental", "doom:mancubus" };
}