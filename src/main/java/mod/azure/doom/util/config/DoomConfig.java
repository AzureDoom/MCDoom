package mod.azure.doom.util.config;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.common.collect.Lists;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class DoomConfig {
	public static class Server {
		public final ConfigValue<Integer> crucible_marauder_max_damage;
		public final ConfigValue<Boolean> enable_block_breaking;
		public final ConfigValue<Boolean> enable_noncenter;
		public final ConfigValue<Double> argent_bolt_damage;
		public final ConfigValue<Double> bfgball_damage;
		public final ConfigValue<Double> bfgball_damage_dragon;
		public final ConfigValue<Double> bfgball_damage_aoe;
		public final ConfigValue<Double> bullet_damage;
		public final ConfigValue<Double> chaingun_bullet_damage;
		public final ConfigValue<Double> energycell_damage;
		public final ConfigValue<Double> rocket_damage;
		public final ConfigValue<Double> shotgun_damage;
		public final ConfigValue<Double> unmaykr_damage;
		public final ConfigValue<Double> grenade_damage;

		public final ConfigValue<List<? extends String>> imp_biomes;
		public final ConfigValue<Integer> imp_spawn_weight;
		public final ConfigValue<Integer> imp_min_group;
		public final ConfigValue<Integer> imp_max_group;

		public final ConfigValue<List<? extends String>> pinky_biomes;
		public final ConfigValue<Integer> pinky_spawn_weight;
		public final ConfigValue<Integer> pinky_min_group;
		public final ConfigValue<Integer> pinky_max_group;

		public final ConfigValue<List<? extends String>> spectre_biomes;
		public final ConfigValue<Integer> spectre_spawn_weight;
		public final ConfigValue<Integer> spectre_min_group;
		public final ConfigValue<Integer> spectre_max_group;

		public final ConfigValue<List<? extends String>> lost_soul_biomes;
		public final ConfigValue<Integer> lost_soul_spawn_weight;
		public final ConfigValue<Integer> lost_soul_min_group;
		public final ConfigValue<Integer> lost_soul_max_group;

		public final ConfigValue<List<? extends String>> cacodemon_biomes;
		public final ConfigValue<Integer> cacodemon_spawn_weight;
		public final ConfigValue<Integer> cacodemon_min_group;
		public final ConfigValue<Integer> cacodemon_max_group;

		public final ConfigValue<List<? extends String>> archvile_biomes;
		public final ConfigValue<Integer> archvile_spawn_weight;
		public final ConfigValue<Integer> archvile_min_group;
		public final ConfigValue<Integer> archvile_max_group;

		public final ConfigValue<List<? extends String>> baron_biomes;
		public final ConfigValue<Integer> baron_spawn_weight;
		public final ConfigValue<Integer> baron_min_group;
		public final ConfigValue<Integer> baron_max_group;

		public final ConfigValue<List<? extends String>> gladiator_biomes;
		public final ConfigValue<Integer> gladiator_spawn_weight;
		public final ConfigValue<Integer> gladiator_min_group;
		public final ConfigValue<Integer> gladiator_max_group;

		public final ConfigValue<List<? extends String>> mancubus_biomes;
		public final ConfigValue<Integer> mancubus_spawn_weight;
		public final ConfigValue<Integer> mancubus_min_group;
		public final ConfigValue<Integer> mancubus_max_group;

		public final ConfigValue<List<? extends String>> revenant_biomes;
		public final ConfigValue<Integer> revenant_spawn_weight;
		public final ConfigValue<Integer> revenant_min_group;
		public final ConfigValue<Integer> revenant_max_group;

		public final ConfigValue<List<? extends String>> zombieman_biomes;
		public final ConfigValue<Integer> zombieman_spawn_weight;
		public final ConfigValue<Integer> zombieman_min_group;
		public final ConfigValue<Integer> zombieman_max_group;

		public final ConfigValue<List<? extends String>> arachnotron_biomes;
		public final ConfigValue<Integer> arachnotron_spawn_weight;
		public final ConfigValue<Integer> arachnotron_min_group;
		public final ConfigValue<Integer> arachnotron_max_group;

		public final ConfigValue<List<? extends String>> imp2016_biomes;
		public final ConfigValue<Integer> imp2016_spawn_weight;
		public final ConfigValue<Integer> imp2016_min_group;
		public final ConfigValue<Integer> imp2016_max_group;

		public final ConfigValue<List<? extends String>> gargoyle_biomes;
		public final ConfigValue<Integer> gargoyle_spawn_weight;
		public final ConfigValue<Integer> gargoyle_min_group;
		public final ConfigValue<Integer> gargoyle_max_group;

		public final ConfigValue<List<? extends String>> nightmare_biomes;
		public final ConfigValue<Integer> nightmare_spawn_weight;
		public final ConfigValue<Integer> nightmare_min_group;
		public final ConfigValue<Integer> nightmare_max_group;

		public final ConfigValue<List<? extends String>> chaingunner_biomes;
		public final ConfigValue<Integer> chaingunner_spawn_weight;
		public final ConfigValue<Integer> chaingunner_min_group;
		public final ConfigValue<Integer> chaingunner_max_group;

		public final ConfigValue<List<? extends String>> shotgunguy_biomes;
		public final ConfigValue<Integer> shotgunguy_spawn_weight;
		public final ConfigValue<Integer> shotgunguy_min_group;
		public final ConfigValue<Integer> shotgunguy_max_group;

		public final ConfigValue<List<? extends String>> marauder_biomes;
		public final ConfigValue<Integer> marauder_spawn_weight;
		public final ConfigValue<Integer> marauder_min_group;
		public final ConfigValue<Integer> marauder_max_group;

		public final ConfigValue<List<? extends String>> pain_biomes;
		public final ConfigValue<Integer> pain_spawn_weight;
		public final ConfigValue<Integer> pain_min_group;
		public final ConfigValue<Integer> pain_max_group;

		public final ConfigValue<List<? extends String>> hellknight_biomes;
		public final ConfigValue<Integer> hellknight_spawn_weight;
		public final ConfigValue<Integer> hellknight_min_group;
		public final ConfigValue<Integer> hellknight_max_group;

		public final ConfigValue<List<? extends String>> hellknight2016_biomes;
		public final ConfigValue<Integer> hellknight2016_spawn_weight;
		public final ConfigValue<Integer> hellknight2016_min_group;
		public final ConfigValue<Integer> hellknight2016_max_group;

		public final ConfigValue<List<? extends String>> cyberdemon_biomes;
		public final ConfigValue<Integer> cyberdemon_spawn_weight;
		public final ConfigValue<Integer> cyberdemon_min_group;
		public final ConfigValue<Integer> cyberdemon_max_group;

		public final ConfigValue<List<? extends String>> unwilling_biomes;
		public final ConfigValue<Integer> unwilling_spawn_weight;
		public final ConfigValue<Integer> unwilling_min_group;
		public final ConfigValue<Integer> unwilling_max_group;

		public final ConfigValue<List<? extends String>> possessed_scientist_biomes;
		public final ConfigValue<Integer> possessed_scientist_spawn_weight;
		public final ConfigValue<Integer> possessed_scientist_min_group;
		public final ConfigValue<Integer> possessed_scientist_max_group;

		public final ConfigValue<List<? extends String>> possessed_soldier_biomes;
		public final ConfigValue<Integer> possessed_soldier_spawn_weight;
		public final ConfigValue<Integer> possessed_soldier_min_group;
		public final ConfigValue<Integer> possessed_soldier_max_group;

		public final ConfigValue<List<? extends String>> mechazombie_biomes;
		public final ConfigValue<Integer> mechazombie_spawn_weight;
		public final ConfigValue<Integer> mechazombie_min_group;
		public final ConfigValue<Integer> mechazombie_max_group;

		public final ConfigValue<List<? extends String>> cueball_biomes;
		public final ConfigValue<Integer> cueball_spawn_weight;
		public final ConfigValue<Integer> cueball_min_group;
		public final ConfigValue<Integer> cueball_max_group;

		public final ConfigValue<List<? extends String>> prowler_biomes;
		public final ConfigValue<Integer> prowler_spawn_weight;
		public final ConfigValue<Integer> prowler_min_group;
		public final ConfigValue<Integer> prowler_max_group;

		public final ConfigValue<List<? extends String>> impstone_biomes;
		public final ConfigValue<Integer> impstone_spawn_weight;
		public final ConfigValue<Integer> impstone_min_group;
		public final ConfigValue<Integer> impstone_max_group;

		public final ConfigValue<List<? extends String>> gorenest_biomes;
		public final ConfigValue<Integer> gorenest_spawn_weight;
		public final ConfigValue<Integer> gorenest_min_group;
		public final ConfigValue<Integer> gorenest_max_group;

		public final ConfigValue<List<? extends String>> tentacle_biomes;
		public final ConfigValue<Integer> tentacle_spawn_weight;
		public final ConfigValue<Integer> tentacle_min_group;
		public final ConfigValue<Integer> tentacle_max_group;

		public final ConfigValue<List<? extends String>> cyberdemon2016_biomes;
		public final ConfigValue<Integer> cyberdemon2016_spawn_weight;
		public final ConfigValue<Integer> cyberdemon2016_min_group;
		public final ConfigValue<Integer> cyberdemon2016_max_group;

		public final ConfigValue<List<? extends String>> tyrant_biomes;
		public final ConfigValue<Integer> tyrant_spawn_weight;
		public final ConfigValue<Integer> tyrant_min_group;
		public final ConfigValue<Integer> tyrant_max_group;

		public final ConfigValue<List<? extends String>> spider_mastermind_biomes;
		public final ConfigValue<Integer> spider_mastermind_spawn_weight;
		public final ConfigValue<Integer> spider_mastermind_min_group;
		public final ConfigValue<Integer> spider_mastermind_max_group;

		public final ConfigValue<List<? extends String>> whiplash_biomes;
		public final ConfigValue<Integer> whiplash_spawn_weight;
		public final ConfigValue<Integer> whiplash_min_group;
		public final ConfigValue<Integer> whiplash_max_group;

		public final ConfigValue<List<? extends String>> doomhunter_biomes;
		public final ConfigValue<Integer> doomhunter_spawn_weight;
		public final ConfigValue<Integer> doomhunter_min_group;
		public final ConfigValue<Integer> doomhunter_max_group;

		public final ConfigValue<List<? extends String>> possessed_worker_biomes;
		public final ConfigValue<Integer> possessed_worker_spawn_weight;
		public final ConfigValue<Integer> possessed_worker_min_group;
		public final ConfigValue<Integer> possessed_worker_max_group;

		public final ConfigValue<List<? extends String>> armoredbaron_biomes;
		public final ConfigValue<Integer> armoredbaron_spawn_weight;
		public final ConfigValue<Integer> armoredbaron_min_group;
		public final ConfigValue<Integer> armoredbaron_max_group;

		public final ConfigValue<List<? extends String>> summoner_biomes;
		public final ConfigValue<Integer> summoner_spawn_weight;
		public final ConfigValue<Integer> summoner_min_group;
		public final ConfigValue<Integer> summoner_max_group;

		public final ConfigValue<List<? extends String>> motherdemon_biomes;
		public final ConfigValue<Integer> motherdemon_spawn_weight;
		public final ConfigValue<Integer> motherdemon_min_group;
		public final ConfigValue<Integer> motherdemon_max_group;

		public final ConfigValue<List<? extends String>> maykrdrone_biomes;
		public final ConfigValue<Integer> maykrdrone_spawn_weight;
		public final ConfigValue<Integer> maykrdrone_min_group;
		public final ConfigValue<Integer> maykrdrone_max_group;

		public final ConfigValue<List<? extends String>> bloodmaykr_biomes;
		public final ConfigValue<Integer> bloodmaykr_spawn_weight;
		public final ConfigValue<Integer> bloodmaykr_min_group;
		public final ConfigValue<Integer> bloodmaykr_max_group;

		public final ConfigValue<List<? extends String>> archmaykr_biomes;
		public final ConfigValue<Integer> archmaykr_spawn_weight;
		public final ConfigValue<Integer> archmaykr_min_group;
		public final ConfigValue<Integer> archmaykr_max_group;

		public final ConfigValue<Double> motherdemon_health;
		public final ConfigValue<Double> motherdemon_ranged_damage;

		public final ConfigValue<Double> imp_health;
		public final ConfigValue<Double> imp_ranged_damage;

		public final ConfigValue<Double> pinky_health;
		public final ConfigValue<Double> pinky_melee_damage;

		public final ConfigValue<Double> spectre_health;
		public final ConfigValue<Double> spectre_melee_damage;

		public final ConfigValue<Double> lost_soul_health;
		public final ConfigValue<Double> lost_soul_melee_damage;

		public final ConfigValue<Double> cacodemon_health;
		public final ConfigValue<Double> cacodemon_ranged_damage;

		public final ConfigValue<Double> archvile_health;
		public final ConfigValue<Double> archvile_ranged_damage;

		public final ConfigValue<Double> summoner_health;
		public final ConfigValue<Double> summoner_ranged_damage;

		public final ConfigValue<Double> prowler_health;
		public final ConfigValue<Double> prowler_melee_damage;
		public final ConfigValue<Double> prowler_ranged_damage;

		public final ConfigValue<Double> maykrdrone_health;
		public final ConfigValue<Double> maykrdrone_ranged_damage;

		public final ConfigValue<Double> bloodmaykr_health;
		public final ConfigValue<Double> bloodmaykr_ranged_damage;

		public final ConfigValue<Double> archmaykr_health;
		public final ConfigValue<Double> archmaykr_ranged_damage;

		public final ConfigValue<Double> baron_health;
		public final ConfigValue<Double> baron_ranged_damage;
		public final ConfigValue<Double> baron_melee_damage;

		public final ConfigValue<Double> gladiator_health;
		public final ConfigValue<Double> gladiator_ranged_damage;
		public final ConfigValue<Double> gladiator_melee_damage;

		public final ConfigValue<Double> mancubus_health;
		public final ConfigValue<Double> mancubus_melee_damage;
		public final ConfigValue<Double> mancubus_ranged_damage;

		public final ConfigValue<Double> revenant_health;
		public final ConfigValue<Double> revenant_ranged_damage;
		public final ConfigValue<Double> revenant_melee_damage;

		public final ConfigValue<Double> spider_mastermind_health;
		public final ConfigValue<Double> spider_mastermind_ranged_damage;
		public final ConfigValue<Double> spider_mastermind_melee_damage;

		public final ConfigValue<Double> zombieman_health;

		public final ConfigValue<Double> arachnotron_health;
		public final ConfigValue<Double> arachnotron_ranged_damage;

		public final ConfigValue<Double> imp2016_health;
		public final ConfigValue<Double> imp2016_ranged_damage;

		public final ConfigValue<Double> impstone_health;
		public final ConfigValue<Double> impstone_melee_damage;

		public final ConfigValue<Double> gargoyle_health;
		public final ConfigValue<Double> gargoyle_ranged_damage;
		public final ConfigValue<Double> gargoyle_melee_damage;

		public final ConfigValue<Double> nightmare_imp_health;
		public final ConfigValue<Double> nightmare_ranged_damage;

		public final ConfigValue<Double> chaingunner_health;

		public final ConfigValue<Double> shotgunguy_health;

		public final ConfigValue<Double> marauder_health;
		public final ConfigValue<Double> marauder_axe_damage;
		public final ConfigValue<Double> marauder_ssgdamage;

		public final ConfigValue<Double> pain_health;

		public final ConfigValue<Double> hellknight_health;
		public final ConfigValue<Double> hellknight_ranged_damage;
		public final ConfigValue<Double> hellknight_melee_damage;

		public final ConfigValue<Double> hellknight2016_health;
		public final ConfigValue<Double> hellknight2016_melee_damage;

		public final ConfigValue<Double> cyberdemon_health;
		public final ConfigValue<Double> cyberdemon_ranged_damage;
		public final ConfigValue<Double> cyberdemon_melee_damage;

		public final ConfigValue<Double> doomhunter_health;
		public final ConfigValue<Double> doomhunter_ranged_damage;
		public final ConfigValue<Double> doomhunter_melee_damage;

		public final ConfigValue<Double> whiplash_health;
		public final ConfigValue<Double> whiplash_melee_damage;

		public final ConfigValue<Double> armoredbaron_health;
		public final ConfigValue<Double> armoredbaron_melee_damage;

		public final ConfigValue<Double> unwilling_health;
		public final ConfigValue<Double> unwilling_melee_damage;

		public final ConfigValue<Double> possessed_scientist_health;
		public final ConfigValue<Double> possessed_scientist_melee_damage;

		public final ConfigValue<Double> possessed_soldier_health;
		public final ConfigValue<Double> possessed_soldier_ranged_damage;

		public final ConfigValue<Double> mechazombie_health;
		public final ConfigValue<Double> mechazombie_ranged_damage;

		public final ConfigValue<Double> gorenest_health;

		public final ConfigValue<Double> cyberdemon2016_health;
		public final ConfigValue<Double> cyberdemon2016_ranged_damage;
		public final ConfigValue<Double> cyberdemon2016_melee_damage;

		public final ConfigValue<Double> cueball_health;

		public final ConfigValue<Double> tentacle_health;
		public final ConfigValue<Double> tentacle_melee_damage;

		public final ConfigValue<Double> turret_health;
		public final ConfigValue<Double> turret_ranged_damage;

		public final ConfigValue<Double> icon_health;
		public final ConfigValue<Double> icon_melee_damage;
		public final ConfigValue<Boolean> enable_all_villager_trades;
		public final ConfigValue<Boolean> enable_weaponsmith_trades;
		public final ConfigValue<Boolean> enable_toolsmith_trades;
		public final ConfigValue<Boolean> enable_mason_trades;

		public final ConfigValue<List<? extends String>> icon_wave_entries;

		public Server(ForgeConfigSpec.Builder builder) {
			builder.push("Misc");
			this.enable_all_villager_trades = builder.translation("text.doom.config.enable_all_villager_trades")
					.define("Villager Trades Toggle", true);
			this.enable_weaponsmith_trades = builder.translation("text.doom.config.enable_weaponsmith_trades")
					.define("Weapon Trades Toggle", true);
			this.enable_toolsmith_trades = builder.translation("text.doom.config.enable_toolsmith_trades")
					.define("Toolsmith Trades Toggle", true);
			this.enable_mason_trades = builder.translation("text.doom.config.enable_mason_trades")
					.define("Mason Trades Toggle", true);
			builder.pop();
			builder.push("Weapons");
			this.crucible_marauder_max_damage = builder.translation("text.doom.config.marauder_axe_damage")
					.defineInRange("Max Damage of Marauder Axe", 5, 1, Integer.MAX_VALUE);
			this.enable_block_breaking = builder.translation("text.doom.config.enable_block_breaking")
					.define("Should Rockets/BFG Break Blocks", false);
			this.enable_noncenter = builder.translation("text.doom.config.enable_noncenter")
					.define("Should Weapons Be Centered", false);
			this.argent_bolt_damage = builder.translation("text.doom.config.argent_bolt_damage")
					.defineInRange("Argent Bolts Damage", 14.5, 1, Double.MAX_VALUE);
			this.bfgball_damage = builder.translation("text.doom.config.bfgball_damage").defineInRange("BFGBall Damage",
					100.5, 1, Double.MAX_VALUE);
			this.bfgball_damage_dragon = builder.translation("text.doom.config.bfgball_damage_dragon")
					.defineInRange("BFG Dragon Damage", 30.5, 1, Double.MAX_VALUE);
			this.bfgball_damage_aoe = builder.translation("text.doom.config.bfgball_damage_aoe")
					.defineInRange("BFG AoE Damage", 10.5, 1, Double.MAX_VALUE);
			this.bullet_damage = builder.translation("text.doom.config.bullet_damage").defineInRange("Bullet Damage",
					5.5, 1, Double.MAX_VALUE);
			this.chaingun_bullet_damage = builder.translation("text.doom.config.chaingun_bullet_damage")
					.defineInRange("Chaingun Damage", 5.5, 1, Double.MAX_VALUE);
			this.energycell_damage = builder.translation("text.doom.config.energycell_damage")
					.defineInRange("Plasma Gun Damage", 1.5, 1, Double.MAX_VALUE);
			this.rocket_damage = builder.translation("text.doom.config.rocket_damage").defineInRange("Rocket Damage",
					20.5, 1, Double.MAX_VALUE);
			this.shotgun_damage = builder.translation("text.doom.config.shotgun_damage").defineInRange("Shotgun Damage",
					10.5, 1, Double.MAX_VALUE);
			this.unmaykr_damage = builder.translation("text.doom.config.unmaykr_damage").defineInRange("Unmaykr Damage",
					2.5, 1, Double.MAX_VALUE);
			this.grenade_damage = builder.translation("text.doom.config.grenade_damage").defineInRange("Grenade Damage",
					30, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Imps");
			this.imp_biomes = builder.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.imp_biomes")
					.defineList("Imp Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.imp_spawn_weight = builder.translation("text.doom.config.imp_spawn_weight")
					.defineInRange("Imp Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.imp_min_group = builder.translation("text.doom.config.imp_min_group").defineInRange("Imp Min Group", 1,
					1, Integer.MAX_VALUE);
			this.imp_max_group = builder.translation("text.doom.config.imp_max_group").defineInRange("Imp Max Group", 4,
					1, Integer.MAX_VALUE);
			this.imp_health = builder.translation("text.doom.config.imp_health").comment("Sets Imp Max Health")
					.defineInRange("Sets Imp Max health", 30, 1, Double.MAX_VALUE);
			this.imp_ranged_damage = builder.translation("text.doom.config.imp_ranged_damage")
					.defineInRange("Sets Imp Ranged Damage damage", 4, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Pinky");
			this.pinky_biomes = builder.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.pinky_biomes")
					.defineList("Pinky Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.pinky_spawn_weight = builder.translation("text.doom.config.pinky_spawn_weight")
					.defineInRange("Pinky Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.pinky_min_group = builder.translation("text.doom.config.pinky_min_group")
					.defineInRange("Pinky Min Group", 2, 1, Integer.MAX_VALUE);
			this.pinky_max_group = builder.translation("text.doom.config.pinky_max_group")
					.defineInRange("Pinky Max Group", 4, 1, Integer.MAX_VALUE);
			this.pinky_health = builder.translation("text.doom.config.pinky_health").comment("Sets Pinky Max Health")
					.defineInRange("Sets Pinky Max Health", 75, 1, Double.MAX_VALUE);
			this.pinky_melee_damage = builder.translation("text.doom.config.pinky_melee_damage")
					.defineInRange("Sets Pinky Melee Damage", 3, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Spectre");
			this.spectre_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.spectre_biomes")
					.defineList("Spectre Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.spectre_spawn_weight = builder.translation("text.doom.config.spectre_spawn_weight")
					.defineInRange("Spectre Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.spectre_min_group = builder.translation("text.doom.config.spectre_min_group")
					.defineInRange("Spectre Min Group", 2, 1, Integer.MAX_VALUE);
			this.spectre_max_group = builder.translation("text.doom.config.spectre_max_group")
					.defineInRange("Spectre Max Group", 4, 1, Integer.MAX_VALUE);
			this.spectre_health = builder.translation("text.doom.config.spectre_health")
					.defineInRange("Sets Spectre Max Health", 75, 1, Double.MAX_VALUE);
			this.spectre_melee_damage = builder.translation("text.doom.config.spectre_melee_damage")
					.defineInRange("Sets Spectre Melee Damage", 3, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Lost Soul");
			this.lost_soul_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.lost_soul_biomes")
					.defineList("Lost Soul Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.lost_soul_spawn_weight = builder.translation("text.doom.config.lost_soul_spawn_weight")
					.defineInRange("Lost Soul Spawn Weight", 25, 1, Integer.MAX_VALUE);
			this.lost_soul_min_group = builder.translation("text.doom.config.lost_soul_min_group")
					.defineInRange("Lost Soul Min Group", 1, 1, Integer.MAX_VALUE);
			this.lost_soul_max_group = builder.translation("text.doom.config.lost_soul_max_group")
					.defineInRange("Lost Soul Max Group", 3, 1, Integer.MAX_VALUE);
			this.lost_soul_health = builder.translation("text.doom.config.lost_soul_health")
					.defineInRange("Sets Lost Soul Max Health", 10, 1, Double.MAX_VALUE);
			this.lost_soul_melee_damage = builder.translation("text.doom.config.lost_soul_melee_damage")
					.defineInRange("Sets Lost Soul Melee Damage", 1, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Cacodemon");
			this.cacodemon_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.cacodemon_biomes")
					.defineList("Cacodemon Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.cacodemon_spawn_weight = builder.translation("text.doom.config.cacodemon_spawn_weight")
					.defineInRange("Cacodemon Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.cacodemon_min_group = builder.translation("text.doom.config.cacodemon_min_group")
					.defineInRange("Cacodemon Min Group", 1, 1, Integer.MAX_VALUE);
			this.cacodemon_max_group = builder.translation("text.doom.config.cacodemon_max_group")
					.defineInRange("Cacodemon Max Group", 2, 1, Integer.MAX_VALUE);
			this.cacodemon_health = builder.translation("text.doom.config.cacodemon_health")
					.defineInRange("Sets Cacodemon Max Health", 80, 1, Double.MAX_VALUE);
			this.cacodemon_ranged_damage = builder.translation("text.doom.config.cacodemon_ranged_damage")
					.comment("Sets Cacodemon Ranged Damage")
					.defineInRange("Sets Cacodemon Ranged Damage damage", 5, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Archvile");
			this.archvile_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.archvile_biomes")
					.defineList("Archvile Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.archvile_spawn_weight = builder.translation("text.doom.config.archvile_spawn_weight")
					.defineInRange("Archvile Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.archvile_min_group = builder.translation("text.doom.config.archvile_min_group")
					.defineInRange("Archvile Min Group", 1, 1, Integer.MAX_VALUE);
			this.archvile_max_group = builder.translation("text.doom.config.archvile_max_group")
					.defineInRange("Archvile Max Group", 2, 1, Integer.MAX_VALUE);
			this.archvile_health = builder.translation("text.doom.config.archvile_health")
					.defineInRange("Sets Archvile Max Health", 100, 1, Double.MAX_VALUE);
			this.archvile_ranged_damage = builder.translation("text.doom.config.archvile_ranged_damage")
					.defineInRange("Sets Archvile Ranged Damage damage", 5, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Baron of Hell");
			this.baron_biomes = builder.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.baron_biomes")
					.defineList("Baron Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.baron_spawn_weight = builder.translation("text.doom.config.baron_spawn_weight")
					.defineInRange("Baron of Hell Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.baron_min_group = builder.translation("text.doom.config.baron_min_group")
					.defineInRange("Baron of Hell Min Group", 1, 1, Integer.MAX_VALUE);
			this.baron_max_group = builder.translation("text.doom.config.baron_max_group")
					.defineInRange("Baron of Hell Max Group", 1, 1, Integer.MAX_VALUE);
			this.baron_health = builder.translation("text.doom.config.baron_health")
					.defineInRange("Sets Baron of Hell Max Health", 180, 1, Double.MAX_VALUE);
			this.baron_ranged_damage = builder.translation("text.doom.config.baron_ranged_damage")
					.defineInRange("Sets Baron of Hell Ranged Damage", 6, 1, Double.MAX_VALUE);
			this.baron_melee_damage = builder.translation("text.doom.config.baron_melee_damage")
					.defineInRange("Sets Baron of Hell Melee Damage", 7, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Gladiator");
			this.gladiator_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.baron_biomes")
					.defineList("Gladiator Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.gladiator_spawn_weight = builder.translation("text.doom.config.gladiator_spawn_weight")
					.defineInRange("Gladiator Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.gladiator_min_group = builder.translation("text.doom.config.gladiator_min_group")
					.defineInRange("Gladiator Min Group", 1, 1, Integer.MAX_VALUE);
			this.gladiator_max_group = builder.translation("text.doom.config.gladiator_max_group")
					.defineInRange("Gladiator Max Group", 1, 1, Integer.MAX_VALUE);
			this.gladiator_health = builder.translation("text.doom.config.gladiator_health")
					.defineInRange("Sets Gladiator Max Health", 240, 1, Double.MAX_VALUE);
			this.gladiator_ranged_damage = builder.translation("text.doom.config.gladiator_ranged_damage")
					.defineInRange("Sets Gladiator Ranged Damage", 6, 1, Double.MAX_VALUE);
			this.gladiator_melee_damage = builder.translation("text.doom.config.gladiator_melee_damage")
					.defineInRange("Sets Gladiator Melee Damage", 7, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Mancubus");
			this.mancubus_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.mancubus_biomes")
					.defineList("Mancubus Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.mancubus_spawn_weight = builder.translation("text.doom.config.mancubus_spawn_weight")
					.defineInRange("Mancubus Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.mancubus_min_group = builder.translation("text.doom.config.mancubus_min_group")
					.defineInRange("Mancubus Min Group", 1, 1, Integer.MAX_VALUE);
			this.mancubus_max_group = builder.translation("text.doom.config.mancubus_max_group")
					.defineInRange("Mancubus Max Group", 1, 1, Integer.MAX_VALUE);
			this.mancubus_health = builder.translation("text.doom.config.mancubus_health")
					.defineInRange("Sets Mancubus Max Health", 80, 1, Double.MAX_VALUE);
			this.mancubus_melee_damage = builder.translation("text.doom.config.mancubus_melee_damage")
					.defineInRange("Sets Mancubus Melee Damage", 4, 1, Double.MAX_VALUE);
			this.mancubus_ranged_damage = builder.translation("text.doom.config.mancubus_ranged_damage")
					.defineInRange("Sets Mancubus Ranged Damage", 6, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Revenant");
			this.revenant_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.revenant_biomes")
					.defineList("Revenant Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.revenant_spawn_weight = builder.translation("text.doom.config.revenant_spawn_weight")
					.defineInRange("Revenant Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.revenant_min_group = builder.translation("text.doom.config.revenant_min_group")
					.defineInRange("Revenant Min Group", 1, 1, Integer.MAX_VALUE);
			this.revenant_max_group = builder.translation("text.doom.config.revenant_max_group")
					.defineInRange("Revenant Max Group", 4, 1, Integer.MAX_VALUE);
			this.revenant_health = builder.translation("text.doom.config.revenant_health")
					.defineInRange("Sets Revenant Max Health", 45, 1, Double.MAX_VALUE);
			this.revenant_ranged_damage = builder.translation("text.doom.config.revenant_ranged_damage")
					.defineInRange("Sets Revenant Ranged Damage", 5, 1, Double.MAX_VALUE);
			this.revenant_melee_damage = builder.translation("text.doom.config.revenant_melee_damage")
					.defineInRange("Sets Revenant Melee Damage", 3, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Zombieman");
			this.zombieman_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.zombieman_biomes")
					.defineList("Zombieman Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.zombieman_spawn_weight = builder.translation("text.doom.config.zombieman_spawn_weight")
					.defineInRange("Zombieman Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.zombieman_min_group = builder.translation("text.doom.config.zombieman_min_group")
					.defineInRange("Zombieman Min Group", 1, 1, Integer.MAX_VALUE);
			this.zombieman_max_group = builder.translation("text.doom.config.zombieman_max_group")
					.defineInRange("Zombieman Max Group", 4, 1, Integer.MAX_VALUE);
			this.zombieman_health = builder.translation("text.doom.config.zombieman_health")
					.defineInRange("Sets Zombieman Max Health", 15, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Arachnotron");
			this.arachnotron_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.arachnotron_biomes")
					.defineList("Arachnotron Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.arachnotron_spawn_weight = builder.translation("text.doom.config.arachnotron_spawn_weight")
					.defineInRange("Arachnotron Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.arachnotron_min_group = builder.translation("text.doom.config.arachnotron_min_group")
					.defineInRange("Arachnotron Min Group", 1, 1, Integer.MAX_VALUE);
			this.arachnotron_max_group = builder.translation("text.doom.config.arachnotron_max_group")
					.defineInRange("Arachnotron Max Group", 4, 1, Integer.MAX_VALUE);
			this.arachnotron_health = builder.translation("text.doom.config.arachnotron_health")
					.defineInRange("Sets Arachnotron Max Health", 30, 1, Double.MAX_VALUE);
			this.arachnotron_ranged_damage = builder.translation("text.doom.config.arachnotron_ranged_damage")
					.defineInRange("Sets Arachnotron Ranged Damage", 5, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Imps 2016");
			this.imp2016_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.imp2016_biomes")
					.defineList("Imp 2016 Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.imp2016_spawn_weight = builder.translation("text.doom.config.imp2016_spawn_weight")
					.defineInRange("Imp 2016 Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.imp2016_min_group = builder.translation("text.doom.config.imp2016_min_group")
					.defineInRange("Imp 2016 Min Group", 1, 1, Integer.MAX_VALUE);
			this.imp2016_max_group = builder.translation("text.doom.config.imp2016_max_group")
					.defineInRange("Imp 2016 Max Group", 4, 1, Integer.MAX_VALUE);
			this.imp2016_health = builder.translation("text.doom.config.imp2016_health")
					.defineInRange("Sets Imp 2016 Max Health", 30, 1, Double.MAX_VALUE);
			this.imp2016_ranged_damage = builder.translation("text.doom.config.imp2016_ranged_damage")
					.defineInRange("Sets Imp 2016 Ranged Damage", 4, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Gargoyle");
			this.gargoyle_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.gargoyle_biomes")
					.defineList("Gargoyle Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.gargoyle_spawn_weight = builder.translation("text.doom.config.gargoyle_spawn_weight")
					.defineInRange("Gargoyle Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.gargoyle_min_group = builder.translation("text.doom.config.gargoyle_min_group")
					.defineInRange("Gargoyle Min Group", 1, 1, Integer.MAX_VALUE);
			this.gargoyle_max_group = builder.translation("text.doom.config.gargoyle_max_group")
					.defineInRange("Gargoyle Max Group", 4, 1, Integer.MAX_VALUE);
			this.gargoyle_health = builder.translation("text.doom.config.gargoyle_health")
					.defineInRange("Sets Gargoyle Max Health", 30, 1, Double.MAX_VALUE);
			this.gargoyle_ranged_damage = builder.translation("text.doom.config.gargoyle_ranged_damage")
					.defineInRange("Sets Gargoyle Ranged Damage", 5, 1, Double.MAX_VALUE);
			this.gargoyle_melee_damage = builder.translation("text.doom.config.gargoyle_melee_damage")
					.defineInRange("Sets Gargoyle Melee Damage", 2, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Nightmare Imp");
			this.nightmare_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.nightmare_biomes")
					.defineList("Nightmare Imp Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.nightmare_spawn_weight = builder.translation("text.doom.config.nightmare_spawn_weight")
					.defineInRange("Nightmare Imp Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.nightmare_min_group = builder.translation("text.doom.config.nightmare_min_group")
					.defineInRange("Nightmare Imp Min Group", 1, 1, Integer.MAX_VALUE);
			this.nightmare_max_group = builder.translation("text.doom.config.nightmare_max_group")
					.defineInRange("Nightmare Imp Max Group", 4, 1, Integer.MAX_VALUE);
			this.nightmare_imp_health = builder.translation("text.doom.config.nightmare_imp_health")
					.defineInRange("Sets Nightmare Imp Max Health", 30, 1, Double.MAX_VALUE);
			this.nightmare_ranged_damage = builder.translation("text.doom.config.nightmare_ranged_damage")
					.defineInRange("Sets Nightmare Imp Ranged Damage", 4, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Chaingunner");
			this.chaingunner_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.chaingunner_biomes")
					.defineList("Chaingunner Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.chaingunner_spawn_weight = builder.translation("text.doom.config.chaingunner_spawn_weight")
					.defineInRange("Chaingunner Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.chaingunner_min_group = builder.translation("text.doom.config.chaingunner_min_group")
					.defineInRange("Chaingunner Min Group", 1, 1, Integer.MAX_VALUE);
			this.chaingunner_max_group = builder.translation("text.doom.config.chaingunner_max_group")
					.defineInRange("Chaingunner Max Group", 4, 1, Integer.MAX_VALUE);
			this.chaingunner_health = builder.translation("text.doom.config.chaingunner_health")
					.defineInRange("Sets Chaingunner Max Health", 15, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Shotgun Guy");
			this.shotgunguy_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.shotgunguy_biomes")
					.defineList("Shotgun Guy Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.shotgunguy_spawn_weight = builder.translation("text.doom.config.shotgunguy_spawn_weight")
					.defineInRange("Shotgun Guy Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.shotgunguy_min_group = builder.translation("text.doom.config.shotgunguy_min_group")
					.defineInRange("Shotgun Guy Min Group", 1, 1, Integer.MAX_VALUE);
			this.shotgunguy_max_group = builder.translation("text.doom.config.shotgunguy_max_group")
					.defineInRange("Shotgun Guy Max Group", 4, 1, Integer.MAX_VALUE);
			this.shotgunguy_health = builder.translation("text.doom.config.shotgunguy_health")
					.defineInRange("Sets Shotgun Guy Max Health", 15, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Marauder");
			this.marauder_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.marauder_biomes")
					.defineList("Marauder Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.marauder_spawn_weight = builder.translation("text.doom.config.marauder_spawn_weight")
					.defineInRange("Marauder Spawn Weight", 3, 1, Integer.MAX_VALUE);
			this.marauder_min_group = builder.translation("text.doom.config.marauder_min_group")
					.defineInRange("Marauder Min Group", 1, 1, Integer.MAX_VALUE);
			this.marauder_max_group = builder.translation("text.doom.config.marauder_max_group")
					.defineInRange("Marauder Max Group", 1, 1, Integer.MAX_VALUE);
			this.marauder_health = builder.translation("text.doom.config.marauder_health")
					.defineInRange("Sets Marauder Max Health", 300, 1, Double.MAX_VALUE);
			this.marauder_axe_damage = builder.translation("text.doom.config.marauder_axe_damage")
					.defineInRange("Sets Marauder Axe Damage", 6, 1, Double.MAX_VALUE);
			this.marauder_ssgdamage = builder.translation("text.doom.config.marauder_ssgdamage")
					.defineInRange("Sets Marauder Shotgun Damage", 10.5, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Pain Elemental");
			this.pain_biomes = builder.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.pain_biomes")
					.defineList("Pain Elemental Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.pain_spawn_weight = builder.translation("text.doom.config.pain_spawn_weight")
					.defineInRange("Pain Elemental Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.pain_min_group = builder.translation("text.doom.config.pain_min_group")
					.defineInRange("Pain Elemental Min Group", 1, 1, Integer.MAX_VALUE);
			this.pain_max_group = builder.translation("text.doom.config.pain_max_group")
					.defineInRange("Pain Elemental Max Group", 2, 1, Integer.MAX_VALUE);
			this.pain_health = builder.translation("text.doom.config.pain_health")
					.defineInRange("Sets Pain Elemental Max Health", 80, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Hellknight");
			this.hellknight_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.hellknight_biomes")
					.defineList("Hellknight Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.hellknight_spawn_weight = builder.translation("text.doom.config.hellknight_spawn_weight")
					.defineInRange("Hellknight Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.hellknight_min_group = builder.translation("text.doom.config.hellknight_min_group")
					.defineInRange("Hellknight Min Group", 1, 1, Integer.MAX_VALUE);
			this.hellknight_max_group = builder.translation("text.doom.config.hellknight_max_group")
					.defineInRange("Hellknight Max Group", 2, 1, Integer.MAX_VALUE);
			this.hellknight_health = builder.translation("text.doom.config.hellknight_health")
					.defineInRange("Sets Hellknight Max Health", 90, 1, Double.MAX_VALUE);
			this.hellknight_ranged_damage = builder.translation("text.doom.config.hellknight_ranged_damage")
					.defineInRange("Sets the Ranged Damage", 6, 1, Double.MAX_VALUE);
			this.hellknight_melee_damage = builder.translation("text.doom.config.hellknight_melee_damage")
					.defineInRange("Sets Hellknight Melee Damage", 4, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Hellknight 2016");
			this.hellknight2016_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.hellknight2016_biomes")
					.defineList("Hellknight 2016 Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.hellknight2016_spawn_weight = builder.translation("text.doom.config.hellknight2016_spawn_weight")
					.defineInRange("Hellknight 2016 Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.hellknight2016_min_group = builder.translation("text.doom.config.hellknight2016_min_group")
					.defineInRange("Hellknight 2016 Min Group", 1, 1, Integer.MAX_VALUE);
			this.hellknight2016_max_group = builder.translation("text.doom.config.hellknight2016_max_group")
					.defineInRange("Hellknight 2016 Max Group", 2, 1, Integer.MAX_VALUE);
			this.hellknight2016_health = builder.translation("text.doom.config.hellknight2016_health")
					.defineInRange("Sets Hellknight 2016 Max Health", 90, 1, Double.MAX_VALUE);
			this.hellknight2016_melee_damage = builder.translation("text.doom.config.hellknight2016_melee_damage")
					.defineInRange("Sets Hellknight 2016 Melee Damage", 4, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Cyberdemon");
			this.cyberdemon_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.cyberdemon_biomes")
					.defineList("Cyberdemon Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.cyberdemon_spawn_weight = builder.translation("text.doom.config.cyberdemon_spawn_weight")
					.defineInRange("Cyberdemon Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.cyberdemon_min_group = builder.translation("text.doom.config.cyberdemon_min_group")
					.defineInRange("Cyberdemon Min Group", 1, 1, Integer.MAX_VALUE);
			this.cyberdemon_max_group = builder.translation("text.doom.config.cyberdemon_max_group")
					.defineInRange("Cyberdemon Max Group", 1, 1, Integer.MAX_VALUE);
			this.cyberdemon_health = builder.translation("text.doom.config.cyberdemon_health")
					.defineInRange("Sets Cyberdemon Max Health", 300, 1, Double.MAX_VALUE);
			this.cyberdemon_ranged_damage = builder.translation("text.doom.config.cyberdemon_ranged_damage")
					.defineInRange("Sets Cyberdemon Ranged Damage", 9, 1, Double.MAX_VALUE);
			this.cyberdemon_melee_damage = builder.translation("text.doom.config.cyberdemon_melee_damage")
					.defineInRange("Sets Cyberdemon Melee Damage", 7, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Unwilling");
			this.unwilling_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.unwilling_biomes")
					.defineList("Unwilling Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.unwilling_spawn_weight = builder.translation("text.doom.config.unwilling_spawn_weight")
					.defineInRange("Unwilling Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.unwilling_min_group = builder.translation("text.doom.config.unwilling_min_group")
					.defineInRange("Unwilling Min Group", 1, 1, Integer.MAX_VALUE);
			this.unwilling_max_group = builder.translation("text.doom.config.unwilling_max_group")
					.defineInRange("Unwilling Max Group", 4, 1, Integer.MAX_VALUE);
			this.unwilling_health = builder.translation("text.doom.config.unwilling_health")
					.defineInRange("Sets Unwilling Max Health", 15, 1, Double.MAX_VALUE);
			this.unwilling_melee_damage = builder.translation("text.doom.config.unwilling_melee_damage")
					.defineInRange("Sets Unwilling Melee Damage", 2, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Possessed Scientist");
			this.possessed_scientist_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.possessed_scientist_biomes")
					.defineList("Possessed Scientist Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.possessed_scientist_spawn_weight = builder
					.translation("text.doom.config.possessed_scientist_spawn_weight")
					.defineInRange("Possessed Scientist Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.possessed_scientist_min_group = builder.translation("text.doom.config.possessed_scientist_min_group")
					.defineInRange("Possessed Scientist Min Group", 1, 1, Integer.MAX_VALUE);
			this.possessed_scientist_max_group = builder.translation("text.doom.config.possessed_scientist_max_group")
					.defineInRange("Possessed Scientist Max Group", 4, 1, Integer.MAX_VALUE);
			this.possessed_scientist_health = builder.translation("text.doom.config.possessed_scientist_health")
					.defineInRange("Sets Possessed Scientist/Worker Max Health", 15, 1, Double.MAX_VALUE);
			this.possessed_scientist_melee_damage = builder
					.translation("text.doom.config.possessed_scientist_melee_damage")
					.defineInRange("Sets Possessed Scientist/Worker Melee Damage", 2, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Possessed Soldier");
			this.possessed_soldier_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.possessed_soldier_biomes")
					.defineList("Possessed Soldier Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.possessed_soldier_spawn_weight = builder.translation("text.doom.config.possessed_soldier_spawn_weight")
					.defineInRange("Possessed Soldier Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.possessed_soldier_min_group = builder.translation("text.doom.config.possessed_soldier_min_group")
					.defineInRange("Possessed Soldier Min Group", 1, 1, Integer.MAX_VALUE);
			this.possessed_soldier_max_group = builder.translation("text.doom.config.possessed_soldier_max_group")
					.defineInRange("Possessed Soldier Max Group", 4, 1, Integer.MAX_VALUE);
			this.possessed_soldier_health = builder.translation("text.doom.config.possessed_soldier_health")
					.defineInRange("Sets Possessed Solider Max Health", 15, 1, Double.MAX_VALUE);
			this.possessed_soldier_ranged_damage = builder
					.translation("text.doom.config.possessed_soldier_ranged_damage")
					.defineInRange("Sets Possessed Solider Ranged Damage", 2, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Mechazombie");
			this.mechazombie_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.imp_biomes")
					.defineList("Mechazombie Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.mechazombie_spawn_weight = builder.translation("text.doom.config.mechazombie_spawn_weight")
					.defineInRange("Mechazombie Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.mechazombie_min_group = builder.translation("text.doom.config.mechazombie_min_group")
					.defineInRange("Mechazombie Min Group", 1, 1, Integer.MAX_VALUE);
			this.mechazombie_max_group = builder.translation("text.doom.config.mechazombie_max_group")
					.defineInRange("Mechazombie Max Group", 4, 1, Integer.MAX_VALUE);
			this.mechazombie_health = builder.translation("text.doom.config.mechazombie_health")
					.defineInRange("Sets the Mechazombie Health", 25, 1, Double.MAX_VALUE);
			this.mechazombie_ranged_damage = builder.translation("text.doom.config.mechazombie_ranged_damage")
					.defineInRange("Sets Mechazombie Ranged Damage", 3, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Cueball");
			this.cueball_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.cueball_biomes")
					.defineList("Cueball Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.cueball_spawn_weight = builder.translation("text.doom.config.cueball_spawn_weight")
					.defineInRange("Cueball Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.cueball_min_group = builder.translation("text.doom.config.cueball_min_group")
					.defineInRange("Cueball Min Group", 1, 1, Integer.MAX_VALUE);
			this.cueball_max_group = builder.translation("text.doom.config.cueball_max_group")
					.defineInRange("Cueball Max Group", 2, 1, Integer.MAX_VALUE);
			this.cueball_health = builder.translation("text.doom.config.cueball_health")
					.defineInRange("Sets Cueball Max Health", 1, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Prowler");
			this.prowler_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.prowler_biomes")
					.defineList("Prowler Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.prowler_spawn_weight = builder.translation("text.doom.config.prowler_spawn_weight")
					.defineInRange("Prowler Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.prowler_min_group = builder.translation("text.doom.config.prowler_min_group")
					.defineInRange("Prowler Min Group", 1, 1, Integer.MAX_VALUE);
			this.prowler_max_group = builder.translation("text.doom.config.prowler_max_group")
					.defineInRange("Prowler Max Group", 2, 1, Integer.MAX_VALUE);
			this.prowler_health = builder.translation("text.doom.config.prowler_health")
					.defineInRange("Sets Prowler Max Health", 15, 1, Double.MAX_VALUE);
			this.prowler_melee_damage = builder.translation("text.doom.config.prowler_melee_damage")
					.defineInRange("Sets the Prowler Damage", 4, 1, Double.MAX_VALUE);
			this.prowler_ranged_damage = builder.translation("text.doom.config.prowler_ranged_damage")
					.defineInRange("Sets Prowler Ranged Damage", 4, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Stone Imps");
			this.impstone_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.impstone_biomes")
					.defineList("Stone Imp Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.impstone_spawn_weight = builder.translation("text.doom.config.impstone_spawn_weight")
					.defineInRange("Stone Imp Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.impstone_min_group = builder.translation("text.doom.config.impstone_min_group")
					.defineInRange("Stone Imp Min Group", 1, 1, Integer.MAX_VALUE);
			this.impstone_max_group = builder.translation("text.doom.config.impstone_max_group")
					.defineInRange("Stone Imp Max Group", 2, 1, Integer.MAX_VALUE);
			this.impstone_health = builder.translation("text.doom.config.impstone_health")
					.defineInRange("Sets Stone Imp Max Health", 15, 1, Double.MAX_VALUE);
			this.impstone_melee_damage = builder.translation("text.doom.config.impstone_melee_damage")
					.defineInRange("Sets Stone Imp Melee Damage", 2, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Gorenest");
			this.gorenest_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.gorenest_biomes")
					.defineList("Gorenest Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.gorenest_spawn_weight = builder.translation("text.doom.config.gorenest_spawn_weight")
					.defineInRange("Gorenest Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.gorenest_min_group = builder.translation("text.doom.config.gorenest_min_group")
					.defineInRange("Gorenest Min Group", 1, 1, Integer.MAX_VALUE);
			this.gorenest_max_group = builder.translation("text.doom.config.gorenest_max_group")
					.defineInRange("Gorenest Max Group", 1, 1, Integer.MAX_VALUE);
			this.gorenest_health = builder.translation("text.doom.config.gorenest_health")
					.defineInRange("Sets Gorenest Max Health", 5, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Tentacle");
			this.tentacle_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.tentacle_biomes")
					.defineList("Tentacle Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.tentacle_spawn_weight = builder.translation("text.doom.config.tentacle_spawn_weight")
					.defineInRange("Tentacle Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.tentacle_min_group = builder.translation("text.doom.config.tentacle_min_group")
					.defineInRange("Tentacle Min Group", 1, 1, Integer.MAX_VALUE);
			this.tentacle_max_group = builder.translation("text.doom.config.tentacle_max_group")
					.defineInRange("Tentacle Max Group", 1, 1, Integer.MAX_VALUE);
			this.tentacle_health = builder.translation("text.doom.config.tentacle_health")
					.defineInRange("Sets Tentacle Max Health", 5, 1, Double.MAX_VALUE);
			this.tentacle_melee_damage = builder.translation("text.doom.config.tentacle_melee_damage")
					.defineInRange("Sets Tentacle Melee Damage", 1, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Cyberdemon 2016");
			this.cyberdemon2016_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.cyberdemon2016_biomes")
					.defineList("Cyberdemon 2016 Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.cyberdemon2016_spawn_weight = builder.translation("text.doom.config.cyberdemon2016_spawn_weight")
					.defineInRange("Cyberdemon 2016 Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.cyberdemon2016_min_group = builder.translation("text.doom.config.cyberdemon2016_min_group")
					.defineInRange("Cyberdemon 2016 Min Group", 1, 1, Integer.MAX_VALUE);
			this.cyberdemon2016_max_group = builder.translation("text.doom.config.cyberdemon2016_max_group")
					.defineInRange("Cyberdemon 2016 Max Group", 1, 1, Integer.MAX_VALUE);
			this.cyberdemon2016_health = builder.translation("text.doom.config.cyberdemon2016_health")
					.defineInRange("Sets Cyberdemon 2016 Max Health", 300, 1, Double.MAX_VALUE);
			this.cyberdemon2016_ranged_damage = builder.translation("text.doom.config.cyberdemon2016_ranged_damage")
					.defineInRange("Sets Cyberdemon 2016 Ranged Damage", 9, 1, Double.MAX_VALUE);
			this.cyberdemon2016_melee_damage = builder.translation("text.doom.config.cyberdemon2016_melee_damage")
					.defineInRange("Sets Cyberdemon 2016 Melee Damage", 7, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Tyrant");
			this.tyrant_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.tyrant_biomes")
					.defineList("Tyrant Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.tyrant_spawn_weight = builder.translation("text.doom.config.tyrant_spawn_weight")
					.defineInRange("Tyrant Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.tyrant_min_group = builder.translation("text.doom.config.tyrant_min_group")
					.defineInRange("Tyrant Min Group", 1, 1, Integer.MAX_VALUE);
			this.tyrant_max_group = builder.translation("text.doom.config.tyrant_max_group")
					.defineInRange("Tyrant Max Group", 1, 1, Integer.MAX_VALUE);

			builder.pop();

			builder.push("Mob Settings:Spider Mastermind");
			this.spider_mastermind_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.spider_mastermind_biomes")
					.defineList("Spider Mastermind Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.spider_mastermind_spawn_weight = builder.translation("text.doom.config.spider_mastermind_spawn_weight")
					.defineInRange("Spider Mastermind Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.spider_mastermind_min_group = builder.translation("text.doom.config.spider_mastermind_min_group")
					.defineInRange("Spider Mastermind Min Group", 1, 1, Integer.MAX_VALUE);
			this.spider_mastermind_max_group = builder.translation("text.doom.config.spider_mastermind_max_group")
					.defineInRange("Spider Mastermind Max Group", 1, 1, Integer.MAX_VALUE);
			this.spider_mastermind_health = builder.translation("text.doom.config.spider_mastermind_health")
					.defineInRange("Sets Spider Mastermind Max Health", 300, 1, Double.MAX_VALUE);
			this.spider_mastermind_ranged_damage = builder
					.translation("text.doom.config.spider_mastermind_ranged_damage")
					.defineInRange("Sets Spider Mastermind Ranged Damage", 7, 1, Double.MAX_VALUE);
			this.spider_mastermind_melee_damage = builder.translation("text.doom.config.spider_mastermind_melee_damage")
					.defineInRange("Sets Spider Mastermind Melee Damage", 4, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Whiplash");
			this.whiplash_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.whiplash_biomes")
					.defineList("Whiplash Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.whiplash_spawn_weight = builder.translation("text.doom.config.whiplash_spawn_weight")
					.defineInRange("Whiplash Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.whiplash_min_group = builder.translation("text.doom.config.whiplash_min_group")
					.defineInRange("Whiplash Min Group", 1, 1, Integer.MAX_VALUE);
			this.whiplash_max_group = builder.translation("text.doom.config.whiplash_max_group")
					.defineInRange("Whiplash Max Group", 1, 1, Integer.MAX_VALUE);
			this.whiplash_health = builder.translation("text.doom.config.whiplash_health")
					.defineInRange("Sets Whiplash Max Health", 90, 1, Double.MAX_VALUE);
			this.whiplash_melee_damage = builder.translation("text.doom.config.whiplash_melee_damage")
					.defineInRange("Sets Whiplash Melee Damage", 4, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Doom Hunter");
			this.doomhunter_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.doomhunter_biomes")
					.defineList("Doom Hunter Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.doomhunter_spawn_weight = builder.translation("text.doom.config.doomhunter_spawn_weight")
					.defineInRange("Doom Hunter Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.doomhunter_min_group = builder.translation("text.doom.config.doomhunter_min_group")
					.defineInRange("Doom Hunter Min Group", 1, 1, Integer.MAX_VALUE);
			this.doomhunter_max_group = builder.translation("text.doom.config.doomhunter_max_group")
					.defineInRange("Doom Hunter Max Group", 1, 1, Integer.MAX_VALUE);
			this.doomhunter_health = builder.translation("text.doom.config.doomhunter_health")
					.defineInRange("Sets Doomhunter Max Health", 150, 1, Double.MAX_VALUE);
			this.doomhunter_ranged_damage = builder.translation("text.doom.config.doomhunter_ranged_damage")
					.defineInRange("Sets Doomhunter Ranged Damage", 7, 1, Double.MAX_VALUE);
			this.doomhunter_melee_damage = builder.translation("text.doom.config.doomhunter_melee_damage")
					.defineInRange("Sets Doomhunter Melee Damage", 5, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Possessed Worker");
			this.possessed_worker_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.possessed_worker_biomes")
					.defineList("Possessed Worker Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.possessed_worker_spawn_weight = builder.translation("text.doom.config.possessed_worker_spawn_weight")
					.defineInRange("Possessed Worker Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.possessed_worker_min_group = builder.translation("text.doom.config.possessed_worker_min_group")
					.defineInRange("Possessed Worker Min Group", 1, 1, Integer.MAX_VALUE);
			this.possessed_worker_max_group = builder.translation("text.doom.config.possessed_worker_max_group")
					.defineInRange("Possessed Worker Max Group", 4, 1, Integer.MAX_VALUE);

			builder.pop();
			builder.push("Mob Settings:Armored Baron");
			this.armoredbaron_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.armoredbaron_biomes")
					.defineList("Armored Baron Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.armoredbaron_spawn_weight = builder.translation("text.doom.config.armoredbaron_spawn_weight")
					.defineInRange("Armored Baron Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.armoredbaron_min_group = builder.translation("text.doom.config.armoredbaron_min_group")
					.defineInRange("Armored Baron Min Group", 1, 1, Integer.MAX_VALUE);
			this.armoredbaron_max_group = builder.translation("text.doom.config.armoredbaron_max_group")
					.defineInRange("Armored Baron Max Group", 1, 1, Integer.MAX_VALUE);
			this.armoredbaron_health = builder.translation("text.doom.config.armoredbaron_health")
					.defineInRange("Sets Armored Baron Max Health", 240, 1, Double.MAX_VALUE);
			this.armoredbaron_melee_damage = builder.translation("text.doom.config.armoredbaron_melee_damage")
					.defineInRange("Sets Armored Baron Melee Damage", 7, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Summoner");
			this.summoner_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.summoner_biomes")
					.defineList("Summoner Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.summoner_spawn_weight = builder.translation("text.doom.config.summoner_spawn_weight")
					.defineInRange("Summoner Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.summoner_min_group = builder.translation("text.doom.config.summoner_min_group")
					.defineInRange("Summoner Min Group", 1, 1, Integer.MAX_VALUE);
			this.summoner_max_group = builder.translation("text.doom.config.summoner_max_group")
					.defineInRange("Summoner Max Group", 2, 1, Integer.MAX_VALUE);
			this.summoner_health = builder.translation("text.doom.config.summoner_health")
					.defineInRange("Sets Summoner Max Health", 100, 1, Double.MAX_VALUE);
			this.summoner_ranged_damage = builder.translation("text.doom.config.summoner_ranged_damage")
					.defineInRange("Sets Summoner Ranged Damage", 3, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Maykr Drone");
			this.maykrdrone_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.maykrdrone_biomes").defineList(
							"Maykr Drone Biomes", Lists.newArrayList("minecraft:small_end_islands",
									"minecraft:end_midlands", "minecraft:end_barrens", "minecraft:end_highlands"),
							o -> o instanceof String);
			this.maykrdrone_spawn_weight = builder.translation("text.doom.config.maykrdrone_spawn_weight")
					.defineInRange("Maykr Drone Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.maykrdrone_min_group = builder.translation("text.doom.config.maykrdrone_min_group")
					.defineInRange("Maykr Drone Min Group", 1, 1, Integer.MAX_VALUE);
			this.maykrdrone_max_group = builder.translation("text.doom.config.maykrdrone_max_group")
					.defineInRange("Maykr Drone Max Group", 2, 1, Integer.MAX_VALUE);
			this.maykrdrone_health = builder.translation("text.doom.config.maykrdrone_health")
					.defineInRange("Sets Maykr Drone Max Health", 20, 1, Double.MAX_VALUE);
			this.maykrdrone_ranged_damage = builder.translation("text.doom.config.maykrdrone_ranged_damage")
					.defineInRange("Sets Maykr Drone Ranged Damage", 2, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Blood Maykr");
			this.bloodmaykr_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.bloodmaykr_biomes").defineList(
							"Blood Maykr Biomes", Lists.newArrayList("minecraft:small_end_islands",
									"minecraft:end_midlands", "minecraft:end_barrens", "minecraft:end_highlands"),
							o -> o instanceof String);
			this.bloodmaykr_spawn_weight = builder.translation("text.doom.config.bloodmaykr_spawn_weight")
					.defineInRange("Blood Maykr Spawn Weight", 6, 1, Integer.MAX_VALUE);
			this.bloodmaykr_min_group = builder.translation("text.doom.config.bloodmaykr_min_group")
					.defineInRange("Blood Maykr Min Group", 1, 1, Integer.MAX_VALUE);
			this.bloodmaykr_max_group = builder.translation("text.doom.config.bloodmaykr_max_group")
					.defineInRange("Blood Maykr Max Group", 1, 1, Integer.MAX_VALUE);
			this.bloodmaykr_health = builder.translation("text.doom.config.bloodmaykr_health")
					.defineInRange("Sets Blood Maykr Max Health", 45, 1, Double.MAX_VALUE);
			this.bloodmaykr_ranged_damage = builder.translation("text.doom.config.bloodmaykr_ranged_damage")
					.defineInRange("Sets Blood Maykr Ranged Damage", 5, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Archmaykr");
			this.archmaykr_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.archmaykr_biomes").defineList(
							"Archmaykr Biomes", Lists.newArrayList("minecraft:small_end_islands",
									"minecraft:end_midlands", "minecraft:end_barrens", "minecraft:end_highlands"),
							o -> o instanceof String);
			this.archmaykr_spawn_weight = builder.translation("text.doom.config.archmaykr_spawn_weight")
					.defineInRange("Archmaykr Spawn Weight", 1, 1, Integer.MAX_VALUE);
			this.archmaykr_min_group = builder.translation("text.doom.config.archmaykr_min_group")
					.defineInRange("Archmaykr Min Group", 1, 1, Integer.MAX_VALUE);
			this.archmaykr_max_group = builder.translation("text.doom.config.archmaykr_max_group")
					.defineInRange("Archmaykr Max Group", 1, 1, Integer.MAX_VALUE);
			this.archmaykr_health = builder.translation("text.doom.config.archmaykr_health")
					.defineInRange("Sets Archmaykr Max Health", 400, 1, Double.MAX_VALUE);
			this.archmaykr_ranged_damage = builder.translation("text.doom.config.archmaykr_ranged_damage")
					.defineInRange("Sets Archmaykr Ranged Damage", 14, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Motherdemon");
			this.motherdemon_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.motherdemon_biomes")
					.defineList("Motherdemon Biomes", Lists.newArrayList("#nether"), o -> o instanceof String);
			this.motherdemon_spawn_weight = builder.translation("text.doom.config.archmaykr_spawn_weight")
					.defineInRange("Motherdemon Spawn Weight", 1, 1, Integer.MAX_VALUE);
			this.motherdemon_min_group = builder.translation("text.doom.config.motherdemon_spawn_weight")
					.defineInRange("Motherdemon Min Group", 1, 1, Integer.MAX_VALUE);
			this.motherdemon_max_group = builder.translation("text.doom.config.motherdemon_max_group")
					.defineInRange("Motherdemon Max Group", 1, 1, Integer.MAX_VALUE);
			this.motherdemon_health = builder.translation("text.doom.config.motherdemon_health")
					.defineInRange("Sets Motherdemon Max Health", 500, 1, Double.MAX_VALUE);
			this.motherdemon_ranged_damage = builder.translation("text.doom.config.motherdemon_ranged_damage")
					.defineInRange("Sets Motherdemon Ranged Damage", 14, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Turret");
			this.turret_health = builder.translation("text.doom.config.turret_health")
					.defineInRange("Sets Turret Max Health", 5, 1, Double.MAX_VALUE);
			this.turret_ranged_damage = builder.translation("text.doom.config.turret_ranged_damage")
					.defineInRange("Sets Turret Ranged Damage", 6, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Icon of Sin");
			this.icon_health = builder.translation("text.doom.config.icon_health")
					.defineInRange("Sets Icon of Sin Max Health", 1000, 1, Double.MAX_VALUE);
			this.icon_melee_damage = builder.translation("text.doom.config.icon_melee_damage")
					.defineInRange("Sets Icon of Sin Melee Damage", 7, 1, Double.MAX_VALUE);
			this.icon_wave_entries = builder.comment("Icon of Sin Wave Mobs List")
					.translation("text.doom.config.icon_wave_entries")
					.defineList("Icon of Sin Wave Mobs", Lists.newArrayList("doom:gladiator", "doom:mancubus",
							"doom:mancubus", "doom:marauder", "doom:marauder", "doom:firebronebaron", "doom:baron2016",
							"doom:baron2016", "doom:baron2016", "doom:baron2016", "doom:whiplash", "doom:whiplash",
							"doom:whiplash", "doom:whiplash", "doom:whiplash", "doom:gargoyle", "doom:gargoyle",
							"doom:gargoyle", "doom:gargoyle", "doom:gargoyle", "doom:gargoyle", "doom:gargoyle",
							"doom:gargoyle", "doom:gargoyle", "doom:gargoyle", "doom:cacodemon", "doom:cacodemon",
							"doom:cacodemon", "doom:cacodemon", "doom:cacodemon", "doom:cacodemon", "doom:cacodemon",
							"doom:cacodemon", "doom:cacodemon", "doom:cacodemon", "doom:painelemental",
							"doom:painelemental", "doom:painelemental", "doom:painelemental", "doom:painelemental",
							"doom:painelemental", "doom:painelemental", "doom:painelemental", "doom:painelemental",
							"doom:painelemental", "doom:imp", "doom:imp", "doom:imp", "doom:imp", "doom:imp",
							"doom:imp", "doom:imp", "doom:imp", "doom:imp", "doom:imp", "doom:prowler", "doom:prowler",
							"doom:prowler", "doom:prowler", "doom:prowler", "doom:prowler", "doom:prowler",
							"doom:prowler", "doom:prowler", "doom:prowler", "doom:nightmare_imp", "doom:nightmare_imp",
							"doom:nightmare_imp", "doom:nightmare_imp", "doom:nightmare_imp", "doom:nightmare_imp",
							"doom:nightmare_imp", "doom:nightmare_imp", "doom:nightmare_imp", "doom:nightmare_imp",
							"doom:nightmare_imp", "doom:pinky2016", "doom:pinky2016", "doom:pinky2016",
							"doom:pinky2016", "doom:pinky2016", "doom:pinky2016", "doom:pinky2016", "doom:pinky2016",
							"doom:pinky2016", "doom:pinky2016", "doom:lost_soul", "doom:lost_soul", "doom:lost_soul",
							"doom:lost_soul", "doom:lost_soul", "doom:lost_soul", "doom:lost_soul", "doom:lost_soul",
							"doom:lost_soul", "doom:lost_soul"), o -> o instanceof String);
			builder.pop();

		}
	}

	public static final Server SERVER;
	public static final ForgeConfigSpec SERVER_SPEC;

	static {
		Pair<Server, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Server::new);
		SERVER = commonSpecPair.getLeft();
		SERVER_SPEC = commonSpecPair.getRight();
	}

	public static void loadConfig(ForgeConfigSpec config, String path) {
		final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave()
				.writingMode(WritingMode.REPLACE).build();
		file.load();
		config.setConfig(file);
	}

}
