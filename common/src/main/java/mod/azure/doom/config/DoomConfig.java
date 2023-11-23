package mod.azure.doom.config;

import mod.azure.azurelib.config.Config;
import mod.azure.azurelib.config.Configurable;
import mod.azure.doom.MCDoom;

@Config(id = MCDoom.MOD_ID)
public class DoomConfig {
    private String mancubus = "doom:mancubus";
    private String baron2016 = "doom:baron2016";
    private String whiplash = "doom:whiplash";
    private String cacodemon = "doom:cacodemon";
    private String painElemental = "doom:painelemental";
    private String imp = "doom:imp";
    private String prower = "doom:prowler";
    private String pinky = "doom:pinky";
    private String lostSoul = "doom:lost_soul";
    private String gargoyle = "doom:gargoyle";

    @Configurable
    @Configurable.Synchronized
    public boolean enable_all_villager_trades = true;

    @Configurable
    @Configurable.Synchronized
    public boolean enable_weaponsmith_trades = true;

    @Configurable
    @Configurable.Synchronized
    public boolean enable_toolsmith_trades = true;

    @Configurable
    @Configurable.Synchronized
    public boolean enable_mason_trades = true;

    @Configurable
    @Configurable.Synchronized
    public boolean enable_soulcube_effects = true;

    @Configurable
    @Configurable.Synchronized
    public boolean enable_daisy_effects = true;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int doom_armor_head_stat = 25;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int doom_armor_chestplate_stat = 18;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int doom_armor_leggings_stat = 20;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int doom_armor_boots_stat = 15;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float doom_armor_toughness = 24;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float doom_armor_knockbackResistance = 4;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int marauder_max_uses = 24;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float marauder_axe_item_damage = 200F;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int crucible_max_uses = 24;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float crucible_damage = 200F;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int darkcrucible_max_uses = 24;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float darkcrucible_damage = 200F;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int sentinelhammer_max_uses = 24;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float sentinelhammer_damage = 25;
    @Configurable
    @Configurable.Synchronized
    public boolean enable_block_breaking = false;
    @Configurable
    public boolean enable_noncenter = false;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float argent_bolt_damage = 14.5F;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float bfgball_damage = 100F;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float bfgball_damage_dragon = 30F;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float bfgball_damage_aoe = 10F;
    @Configurable
    public String[] bfg_damage_mob_whitelist = {""};
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float bullet_damage = 5.5F;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float chaingun_bullet_damage = 5.5F;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float energycell_damage = 1.5F;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float rocket_damage = 20F;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float shotgun_damage = 10F;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float unmaykr_damage = 2.5F;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float grenade_damage = 30F;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float chainsaw_damage = 2F;
    @Configurable
    public double max_meathook_distance = 32;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int imp_spawn_weight = 15;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int imp_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int imp_max_group = 4;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int pinky_spawn_weight = 15;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int pinky_min_group = 2;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int pinky_max_group = 4;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int spectre_spawn_weight = 15;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int spectre_min_group = 2;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int spectre_max_group = 4;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int lost_soul_spawn_weight = 25;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int lost_soul_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int lost_soul_max_group = 3;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int cacodemon_spawn_weight = 4;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int cacodemon_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int cacodemon_max_group = 2;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int archvile_spawn_weight = 4;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int archvile_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int archvile_max_group = 2;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int baron_spawn_weight = 4;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int baron_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int baron_max_group = 1;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int mancubus_spawn_weight = 4;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int mancubus_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int mancubus_max_group = 1;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int revenant_spawn_weight = 4;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int revenant_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int revenant_max_group = 4;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int zombieman_spawn_weight = 15;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int zombieman_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int zombieman_max_group = 4;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int arachnotron_spawn_weight = 15;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int arachnotron_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int arachnotron_max_group = 4;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int gargoyle_spawn_weight = 15;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int gargoyle_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int gargoyle_max_group = 4;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int chaingunner_spawn_weight = 15;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int chaingunner_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int chaingunner_max_group = 4;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int shotgunguy_spawn_weight = 15;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int shotgunguy_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int shotgunguy_max_group = 4;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int marauder_spawn_weight = 3;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int marauder_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int marauder_max_group = 1;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int pain_spawn_weight = 4;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int pain_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int pain_max_group = 2;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int hellknight_spawn_weight = 4;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int hellknight_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int hellknight_max_group = 2;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int hellknight2016_spawn_weight = 4;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int hellknight2016_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int hellknight2016_max_group = 2;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int cyberdemon_spawn_weight = 4;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int cyberdemon_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int cyberdemon_max_group = 2;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int unwilling_spawn_weight = 15;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int unwilling_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int unwilling_max_group = 4;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int possessed_scientist_spawn_weight = 15;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int possessed_scientist_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int possessed_scientist_max_group = 4;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int possessed_soldier_spawn_weight = 15;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int possessed_soldier_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int possessed_soldier_max_group = 4;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int mechazombie_spawn_weight = 15;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int mechazombie_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int mechazombie_max_group = 4;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int cueball_spawn_weight = 15;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int cueball_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int cueball_max_group = 2;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int prowler_spawn_weight = 15;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int prowler_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int prowler_max_group = 2;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int impstone_spawn_weight = 15;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int impstone_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int impstone_max_group = 2;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int gorenest_spawn_weight = 4;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int gorenest_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int gorenest_max_group = 1;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int tentacle_spawn_weight = 4;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int tentacle_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int tentacle_max_group = 1;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int turret_spawn_weight = 4;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int turret_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int turret_max_group = 4;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int spider_mastermind_spawn_weight = 4;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int spider_mastermind_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int spider_mastermind_max_group = 1;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int whiplash_spawn_weight = 4;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int whiplash_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int whiplash_max_group = 1;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int doomhunter_spawn_weight = 4;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int doomhunter_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int doomhunter_max_group = 1;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int possessed_worker_spawn_weight = 15;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int possessed_worker_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int possessed_worker_max_group = 4;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int armoredbaron_spawn_weight = 4;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int armoredbaron_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int armoredbaron_max_group = 1;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int summoner_spawn_weight = 4;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int summoner_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int summoner_max_group = 2;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int motherdemon_spawn_weight = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int motherdemon_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int motherdemon_max_group = 1;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int maykrdrone_spawn_weight = 15;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int maykrdrone_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int maykrdrone_max_group = 2;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int bloodmaykr_spawn_weight = 6;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int bloodmaykr_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int bloodmaykr_max_group = 2;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int archmaykr_spawn_weight = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int archmaykr_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int archmaykr_max_group = 1;

    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int gladiator_spawn_weight = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int gladiator_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int gladiator_max_group = 1;

    @Configurable
    public double motherdemon_health = 500;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float motherdemon_ranged_damage = 14;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float motherdemon_phaseone_damage_boos = 14;

    @Configurable
    public double cueball_health = 1;

    @Configurable
    public double tentacle_health = 5;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float tentacle_melee_damage = 1;

    @Configurable
    public double turret_health = 5;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float turret_ranged_damage = 6;

    @Configurable
    public double icon_health = 1000;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float icon_melee_damage = 7;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float icon_phaseone_damage_boos = 7;
    @Configurable
    @Configurable.Synchronized
    public String[] icon_wave_entries = {"doom:gladiator", mancubus, mancubus, "doom:marauder", "doom:marauder", "doom:firebronebaron", baron2016, baron2016, baron2016, baron2016, whiplash, whiplash, whiplash, whiplash, whiplash, gargoyle, gargoyle, gargoyle, gargoyle, gargoyle, gargoyle, gargoyle, gargoyle, gargoyle, gargoyle, cacodemon,
            cacodemon, cacodemon, cacodemon, cacodemon, cacodemon, cacodemon, cacodemon, cacodemon, cacodemon, painElemental, painElemental, painElemental, painElemental, painElemental, painElemental, painElemental, painElemental, painElemental, painElemental, imp, imp, imp, imp, imp, imp, imp, imp,
            imp, imp, prower, prower, prower, prower, prower, prower, prower, prower, prower, prower, imp, imp, imp, imp, imp, imp, imp, imp, imp, imp, imp, pinky, pinky, pinky, pinky, pinky, pinky, pinky, pinky, pinky, pinky, lostSoul,
            lostSoul, lostSoul, lostSoul, lostSoul, lostSoul, lostSoul, lostSoul, lostSoul, lostSoul};

    @Configurable
    public double imp_health = 30;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
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
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float cacodemon_ranged_damage = 5;

    @Configurable
    public double archvile_health = 100;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float archvile_ranged_damage = 5;

    @Configurable
    public double summoner_health = 100;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float summoner_ranged_damage = 3;
    @Configurable
    @Configurable.Synchronized
    public String[] summoner_wave_entries = {imp, lostSoul, "doom:stone_imp"};

    @Configurable
    public double prowler_health = 15;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float prowler_ranged_damage = 4;
    @Configurable
    public double prowler_melee_damage = 4;

    @Configurable
    public double maykrdrone_health = 50;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float maykrdrone_ranged_damage = 5;

    @Configurable
    public double bloodmaykr_health = 100;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float bloodmaykr_ranged_damage = 10;

    @Configurable
    public double archmaykr_health = 400;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float archmaykr_ranged_damage = 14;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float archmaykr_phaseone_damage_boost = 14;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float archmaykr_phasetwo_damage_boost = 28;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float archmaykr_phasethree_damage_boost = 42;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float archmaykr_phasefour_damage_boost = 56;

    @Configurable
    public double baron_health = 180;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float baron_ranged_damage = 6;
    @Configurable
    public double baron_melee_damage = 7;

    @Configurable
    public double gladiator_health = 240;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float gladiator_ranged_damage = 6;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float gladiator_phaseone_damage_boost = 6;
    @Configurable
    public double gladiator_melee_damage = 7;

    @Configurable
    public double mancubus_health = 80;
    @Configurable
    public double mancubus_melee_damage = 4;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float mancubus_ranged_damage = 6;

    @Configurable
    public double revenant_health = 45;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float revenant_ranged_damage = 5;

    @Configurable
    public double spider_mastermind_health = 300;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float spider_mastermind_ranged_damage = 7;
    @Configurable
    public double spider_mastermind_melee_damage = 4;

    @Configurable
    public double zombieman_health = 15;

    @Configurable
    public double arachnotron_health = 30;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float arachnotron_ranged_damage = 5;

    @Configurable
    public double impstone_health = 15;
    @Configurable
    public double impstone_melee_damage = 2;

    @Configurable
    public double gargoyle_health = 30;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
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
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float marauder_ssgdamage = 10.5F;

    @Configurable
    public double pain_health = 80;

    @Configurable
    public double hellknight_health = 90;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
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
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float cyberdemon_ranged_damage = 9;
    @Configurable
    public double cyberdemon_melee_damage = 7;

    @Configurable
    public double doomhunter_health = 150;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float doomhunter_ranged_damage = 7;
    @Configurable
    public double doomhunter_melee_damage = 5;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
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
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float possessed_soldier_ranged_damage = 2;

    @Configurable
    public double mechazombie_health = 25;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float mechazombie_ranged_damage = 3;


    @Configurable
    public double carcass_health = 40;
    @Configurable
    public double carcass_melee_damage = 6;
    @Configurable
    @Configurable.Synchronized
    @Configurable.DecimalRange(min = 1)
    public float carcass_ranged_damage = 6;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int carcass_spawn_weight = 5;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int carcass_min_group = 1;
    @Configurable
    @Configurable.Synchronized
    @Configurable.Range(min = 1)
    public int carcass_max_group = 1;

    @Configurable
    public double gorenest_health = 5;
    @Configurable
    @Configurable.Synchronized
    public String[] gorenest_wave_entries = {"doom:hellknight", "doom:possessed_scientist", imp, pinky, cacodemon, "doom:chaingunner", gargoyle, "doom:hellknight2016", lostSoul, "doom:possessed_soldier", "doom:shotgunguy", "doom:unwilling", "doom:zombieman", "doom:arachnotron", "doom:archvile", "doom:mechazombie", painElemental, mancubus};
}