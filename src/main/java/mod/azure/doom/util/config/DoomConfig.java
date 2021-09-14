package mod.azure.doom.util.config;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class DoomConfig {
	public static class Server {
		public final ConfigValue<Integer> crucible_marauder_max_damage;
		public final ConfigValue<Boolean> enable_block_breaking;
		public final ConfigValue<Float> argent_bolt_damage;
		public final ConfigValue<Float> bfgball_damage;
		public final ConfigValue<Float> bfgball_damage_dragon;
		public final ConfigValue<Float> bfgball_damage_aoe;
		public final ConfigValue<Float> bullet_damage;
		public final ConfigValue<Float> chaingun_bullet_damage;
		public final ConfigValue<Float> energycell_damage;
		public final ConfigValue<Float> rocket_damage;
		public final ConfigValue<Float> shotgun_damage;
		public final ConfigValue<Float> unmaykr_damage;

		public final ConfigValue<List<String>> imp_biomes;
		public final ConfigValue<Integer> imp_spawn_weight;
		public final ConfigValue<Integer> imp_min_group;
		public final ConfigValue<Integer> imp_max_group;

		public final ConfigValue<List<String>> pinky_biomes;
		public final ConfigValue<Integer> pinky_spawn_weight;
		public final ConfigValue<Integer> pinky_min_group;
		public final ConfigValue<Integer> pinky_max_group;

		public final ConfigValue<List<String>> spectre_biomes;
		public final ConfigValue<Integer> spectre_spawn_weight;
		public final ConfigValue<Integer> spectre_min_group;
		public final ConfigValue<Integer> spectre_max_group;

		public final ConfigValue<List<String>> lost_soul_biomes;
		public final ConfigValue<Integer> lost_soul_spawn_weight;
		public final ConfigValue<Integer> lost_soul_min_group;
		public final ConfigValue<Integer> lost_soul_max_group;

		public final ConfigValue<List<String>> cacodemon_biomes;
		public final ConfigValue<Integer> cacodemon_spawn_weight;
		public final ConfigValue<Integer> cacodemon_min_group;
		public final ConfigValue<Integer> cacodemon_max_group;

		public final ConfigValue<List<String>> archvile_biomes;
		public final ConfigValue<Integer> archvile_spawn_weight;
		public final ConfigValue<Integer> archvile_min_group;
		public final ConfigValue<Integer> archvile_max_group;

		public final ConfigValue<List<String>> baron_biomes;
		public final ConfigValue<Integer> baron_spawn_weight;
		public final ConfigValue<Integer> baron_min_group;
		public final ConfigValue<Integer> baron_max_group;

		public final ConfigValue<List<String>> mancubus_biomes;
		public final ConfigValue<Integer> mancubus_spawn_weight;
		public final ConfigValue<Integer> mancubus_min_group;
		public final ConfigValue<Integer> mancubus_max_group;

		public final ConfigValue<List<String>> revenant_biomes;
		public final ConfigValue<Integer> revenant_spawn_weight;
		public final ConfigValue<Integer> revenant_min_group;
		public final ConfigValue<Integer> revenant_max_group;

		public final ConfigValue<List<String>> zombieman_biomes;
		public final ConfigValue<Integer> zombieman_spawn_weight;
		public final ConfigValue<Integer> zombieman_min_group;
		public final ConfigValue<Integer> zombieman_max_group;

		public final ConfigValue<List<String>> arachnotron_biomes;
		public final ConfigValue<Integer> arachnotron_spawn_weight;
		public final ConfigValue<Integer> arachnotron_min_group;
		public final ConfigValue<Integer> arachnotron_max_group;

		public final ConfigValue<List<String>> imp2016_biomes;
		public final ConfigValue<Integer> imp2016_spawn_weight;
		public final ConfigValue<Integer> imp2016_min_group;
		public final ConfigValue<Integer> imp2016_max_group;

		public final ConfigValue<List<String>> gargoyle_biomes;
		public final ConfigValue<Integer> gargoyle_spawn_weight;
		public final ConfigValue<Integer> gargoyle_min_group;
		public final ConfigValue<Integer> gargoyle_max_group;

		public final ConfigValue<List<String>> nightmare_biomes;
		public final ConfigValue<Integer> nightmare_spawn_weight;
		public final ConfigValue<Integer> nightmare_min_group;
		public final ConfigValue<Integer> nightmare_max_group;

		public final ConfigValue<List<String>> chaingunner_biomes;
		public final ConfigValue<Integer> chaingunner_spawn_weight;
		public final ConfigValue<Integer> chaingunner_min_group;
		public final ConfigValue<Integer> chaingunner_max_group;

		public final ConfigValue<List<String>> shotgunguy_biomes;
		public final ConfigValue<Integer> shotgunguy_spawn_weight;
		public final ConfigValue<Integer> shotgunguy_min_group;
		public final ConfigValue<Integer> shotgunguy_max_group;

		public final ConfigValue<List<String>> marauder_biomes;
		public final ConfigValue<Integer> marauder_spawn_weight;
		public final ConfigValue<Integer> marauder_min_group;
		public final ConfigValue<Integer> marauder_max_group;

		public final ConfigValue<List<String>> pain_biomes;
		public final ConfigValue<Integer> pain_spawn_weight;
		public final ConfigValue<Integer> pain_min_group;
		public final ConfigValue<Integer> pain_max_group;

		public final ConfigValue<List<String>> hellknight_biomes;
		public final ConfigValue<Integer> hellknight_spawn_weight;
		public final ConfigValue<Integer> hellknight_min_group;
		public final ConfigValue<Integer> hellknight_max_group;

		public final ConfigValue<List<String>> hellknight2016_biomes;
		public final ConfigValue<Integer> hellknight2016_spawn_weight;
		public final ConfigValue<Integer> hellknight2016_min_group;
		public final ConfigValue<Integer> hellknight2016_max_group;

		public final ConfigValue<List<String>> cyberdemon_biomes;
		public final ConfigValue<Integer> cyberdemon_spawn_weight;
		public final ConfigValue<Integer> cyberdemon_min_group;
		public final ConfigValue<Integer> cyberdemon_max_group;

		public final ConfigValue<List<String>> unwilling_biomes;
		public final ConfigValue<Integer> unwilling_spawn_weight;
		public final ConfigValue<Integer> unwilling_min_group;
		public final ConfigValue<Integer> unwilling_max_group;

		public final ConfigValue<List<String>> possessed_scientist_biomes;
		public final ConfigValue<Integer> possessed_scientist_spawn_weight;
		public final ConfigValue<Integer> possessed_scientist_min_group;
		public final ConfigValue<Integer> possessed_scientist_max_group;

		public final ConfigValue<List<String>> possessed_soldier_biomes;
		public final ConfigValue<Integer> possessed_soldier_spawn_weight;
		public final ConfigValue<Integer> possessed_soldier_min_group;
		public final ConfigValue<Integer> possessed_soldier_max_group;

		public final ConfigValue<List<String>> mechazombie_biomes;
		public final ConfigValue<Integer> mechazombie_spawn_weight;
		public final ConfigValue<Integer> mechazombie_min_group;
		public final ConfigValue<Integer> mechazombie_max_group;

		public final ConfigValue<List<String>> cueball_biomes;
		public final ConfigValue<Integer> cueball_spawn_weight;
		public final ConfigValue<Integer> cueball_min_group;
		public final ConfigValue<Integer> cueball_max_group;

		public final ConfigValue<List<String>> prowler_biomes;
		public final ConfigValue<Integer> prowler_spawn_weight;
		public final ConfigValue<Integer> prowler_min_group;
		public final ConfigValue<Integer> prowler_max_group;

		public final ConfigValue<List<String>> impstone_biomes;
		public final ConfigValue<Integer> impstone_spawn_weight;
		public final ConfigValue<Integer> impstone_min_group;
		public final ConfigValue<Integer> impstone_max_group;

		public final ConfigValue<List<String>> gorenest_biomes;
		public final ConfigValue<Integer> gorenest_spawn_weight;
		public final ConfigValue<Integer> gorenest_min_group;
		public final ConfigValue<Integer> gorenest_max_group;

		public final ConfigValue<List<String>> tentacle_biomes;
		public final ConfigValue<Integer> tentacle_spawn_weight;
		public final ConfigValue<Integer> tentacle_min_group;
		public final ConfigValue<Integer> tentacle_max_group;

		public final ConfigValue<List<String>> cyberdemon2016_biomes;
		public final ConfigValue<Integer> cyberdemon2016_spawn_weight;
		public final ConfigValue<Integer> cyberdemon2016_min_group;
		public final ConfigValue<Integer> cyberdemon2016_max_group;

		public final ConfigValue<List<String>> tyrant_biomes;
		public final ConfigValue<Integer> tyrant_spawn_weight;
		public final ConfigValue<Integer> tyrant_min_group;
		public final ConfigValue<Integer> tyrant_max_group;

		public final ConfigValue<List<String>> spider_mastermind_biomes;
		public final ConfigValue<Integer> spider_mastermind_spawn_weight;
		public final ConfigValue<Integer> spider_mastermind_min_group;
		public final ConfigValue<Integer> spider_mastermind_max_group;

		public final ConfigValue<List<String>> whiplash_biomes;
		public final ConfigValue<Integer> whiplash_spawn_weight;
		public final ConfigValue<Integer> whiplash_min_group;
		public final ConfigValue<Integer> whiplash_max_group;

		public final ConfigValue<List<String>> doomhunter_biomes;
		public final ConfigValue<Integer> doomhunter_spawn_weight;
		public final ConfigValue<Integer> doomhunter_min_group;
		public final ConfigValue<Integer> doomhunter_max_group;

		public final ConfigValue<List<String>> possessed_worker_biomes;
		public final ConfigValue<Integer> possessed_worker_spawn_weight;
		public final ConfigValue<Integer> possessed_worker_min_group;
		public final ConfigValue<Integer> possessed_worker_max_group;

		public final ConfigValue<List<String>> armoredbaron_biomes;
		public final ConfigValue<Integer> armoredbaron_spawn_weight;
		public final ConfigValue<Integer> armoredbaron_min_group;
		public final ConfigValue<Integer> armoredbaron_max_group;

		public final ConfigValue<List<String>> summoner_biomes;
		public final ConfigValue<Integer> summoner_spawn_weight;
		public final ConfigValue<Integer> summoner_min_group;
		public final ConfigValue<Integer> summoner_max_group;

		public final ConfigValue<Double> imp_health;
		public final ConfigValue<Float> imp_ranged_damage;
		public final ConfigValue<Double> imp_melee_damage;

		public final ConfigValue<Double> pinky_health;
		public final ConfigValue<Double> pinky_melee_damage;

		public final ConfigValue<Double> spectre_health;
		public final ConfigValue<Double> spectre_melee_damage;

		public final ConfigValue<Double> lost_soul_health;
		public final ConfigValue<Double> lost_soul_melee_damage;

		public final ConfigValue<Double> cacodemon_health;
		public final ConfigValue<Float> cacodemon_ranged_damage;

		public final ConfigValue<Double> archvile_health;

		public final ConfigValue<Double> summoner_health;
		public final ConfigValue<Double> summoner_melee_damage;

		public final ConfigValue<Double> prowler_health;
		public final ConfigValue<Double> prowler_melee_damage;
		public final ConfigValue<Float> prowler_ranged_damage;

		public final ConfigValue<Double> maykrdrone_health;
		public final ConfigValue<Float> maykrdrone_ranged_damage;

		public final ConfigValue<Double> bloodmaykr_health;
		public final ConfigValue<Float> bloodmaykr_ranged_damage;
		public final ConfigValue<Double> bloodmaykr_melee_damage;

		public final ConfigValue<Double> archmaykr_health;
		public final ConfigValue<Float> archmaykr_ranged_damage;
		public final ConfigValue<Double> archmaykr_melee_damage;

		public final ConfigValue<Double> baron_health;
		public final ConfigValue<Float> baron_ranged_damage;
		public final ConfigValue<Double> baron_melee_damage;

		public final ConfigValue<Double> mancubus_health;
		public final ConfigValue<Double> mancubus_melee_damage;
		public final ConfigValue<Float> mancubus_ranged_damage;

		public final ConfigValue<Double> revenant_health;
		public final ConfigValue<Float> revenant_ranged_damage;
		public final ConfigValue<Double> revenant_melee_damage;

		public final ConfigValue<Double> spider_mastermind_health;
		public final ConfigValue<Float> spider_mastermind_ranged_damage;
		public final ConfigValue<Double> spider_mastermind_melee_damage;

		public final ConfigValue<Double> zombieman_health;
		public final ConfigValue<Double> zombieman_melee_damage;

		public final ConfigValue<Double> arachnotron_health;
		public final ConfigValue<Float> arachnotron_ranged_damage;

		public final ConfigValue<Double> imp2016_health;
		public final ConfigValue<Float> imp2016_ranged_damage;
		public final ConfigValue<Double> imp2016_melee_damage;

		public final ConfigValue<Double> impstone_health;
		public final ConfigValue<Double> impstone_melee_damage;

		public final ConfigValue<Double> gargoyle_health;
		public final ConfigValue<Float> gargoyle_ranged_damage;
		public final ConfigValue<Double> gargoyle_melee_damage;

		public final ConfigValue<Double> nightmare_imp_health;
		public final ConfigValue<Float> nightmare_ranged_damage;
		public final ConfigValue<Double> nightmare_melee_damage;

		public final ConfigValue<Double> chaingunner_health;
		public final ConfigValue<Double> chaingunner_melee_damage;

		public final ConfigValue<Double> shotgunguy_health;
		public final ConfigValue<Double> shotgunguy_melee_damage;

		public final ConfigValue<Double> marauder_health;
		public final ConfigValue<Double> marauder_melee_damage;

		public final ConfigValue<Double> pain_health;

		public final ConfigValue<Double> hellknight_health;
		public final ConfigValue<Float> hellknight_ranged_damage;
		public final ConfigValue<Double> hellknight_melee_damage;

		public final ConfigValue<Double> hellknight2016_health;
		public final ConfigValue<Double> hellknight2016_melee_damage;

		public final ConfigValue<Double> cyberdemon_health;
		public final ConfigValue<Float> cyberdemon_ranged_damage;
		public final ConfigValue<Double> cyberdemon_melee_damage;

		public final ConfigValue<Double> doomhunter_health;
		public final ConfigValue<Float> doomhunter_ranged_damage;
		public final ConfigValue<Double> doomhunter_melee_damage;

		public final ConfigValue<Double> whiplash_health;
		public final ConfigValue<Double> whiplash_melee_damage;

		public final ConfigValue<Double> armoredbaron_health;
		public final ConfigValue<Float> armoredbaron_ranged_damage;
		public final ConfigValue<Double> armoredbaron_melee_damage;

		public final ConfigValue<Double> unwilling_health;
		public final ConfigValue<Double> unwilling_melee_damage;

		public final ConfigValue<Double> possessed_scientist_health;
		public final ConfigValue<Double> possessed_scientist_melee_damage;

		public final ConfigValue<Double> possessed_soldier_health;
		public final ConfigValue<Float> possessed_soldier_ranged_damage;
		public final ConfigValue<Double> possessed_soldier_melee_damage;

		public final ConfigValue<Double> mechazombie_health;
		public final ConfigValue<Float> mechazombie_ranged_damage;
		public final ConfigValue<Double> mechazombie_melee_damage;

		public final ConfigValue<Double> gorenest_health;

		public final ConfigValue<Double> cyberdemon2016_health;
		public final ConfigValue<Float> cyberdemon2016_ranged_damage;
		public final ConfigValue<Double> cyberdemon2016_melee_damage;

		public final ConfigValue<Double> cueball_health;

		public final ConfigValue<Double> tentacle_health;
		public final ConfigValue<Float> tentacle_melee_damage;

		public final ConfigValue<Double> turret_health;
		public final ConfigValue<Float> turret_ranged_damage;

		public final ConfigValue<Double> icon_health;
		public final ConfigValue<Float> icon_melee_damage;

		public Server(ForgeConfigSpec.Builder builder) {
			builder.push("Weapons");
			this.crucible_marauder_max_damage = builder.translation("text.doom.config.marauder_axe_damage")
					.defineInRange("Max Damage of Marauder Axe", 5, 1, Integer.MAX_VALUE);
			this.enable_block_breaking = builder.translation("text.doom.config.enable_block_breaking")
					.define("Should Rockets/BFG Break Blocks", false);
			this.argent_bolt_damage = builder.translation("text.doom.config.argent_bolt_damage")
					.define("Argent Bolts Damage", 14.5F);
			this.bfgball_damage = builder.translation("text.doom.config.bfgball_damage").define("BFGBall Damage",
					100.5F);
			this.bfgball_damage_dragon = builder.translation("text.doom.config.bfgball_damage_dragon")
					.define("BFG Dragon Damage", 30.5F);
			this.bfgball_damage_aoe = builder.translation("text.doom.config.bfgball_damage_aoe")
					.define("BFG AoE Damage", 10.5F);
			this.bullet_damage = builder.translation("text.doom.config.bullet_damage").define("Bullet Damage", 5.5F);
			this.chaingun_bullet_damage = builder.translation("text.doom.config.chaingun_bullet_damage")
					.define("Chaingun Damage", 5.5F);
			this.energycell_damage = builder.translation("text.doom.config.energycell_damage")
					.define("Plasma Gun Damage", 1.5F);
			this.rocket_damage = builder.translation("text.doom.config.rocket_damage").define("Rocket Damage", 20.5F);
			this.shotgun_damage = builder.translation("text.doom.config.shotgun_damage").define("Shotgun Damage",
					10.5F);
			this.unmaykr_damage = builder.translation("text.doom.config.unmaykr_damage").define("Unmaykr Damage", 2.5F);
			builder.pop();
			
			builder.push("Mob Settings:Imps");
			this.imp_biomes = builder.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.imp_biomes").define("Imp Biomes", Arrays.asList("#nether"));
			this.imp_spawn_weight = builder.translation("text.doom.config.imp_spawn_weight")
					.defineInRange("Imp Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.imp_min_group = builder.translation("text.doom.config.imp_min_group").defineInRange("Imp Min Group", 1,
					1, Integer.MAX_VALUE);
			this.imp_max_group = builder.translation("text.doom.config.imp_max_group").defineInRange("Imp Max Group", 4,
					1, Integer.MAX_VALUE);
			this.imp_health = builder.translation("text.doom.config.imp_health").comment("Sets Imp Max Health")
					.defineInRange("Sets Imp Max health", 30, 1, Double.MAX_VALUE);
			this.imp_ranged_damage = builder.translation("text.doom.config.imp_ranged_damage")
					.define("Sets Imp Ranged Damage damage", 4F);
			this.imp_melee_damage = builder.translation("text.doom.config.imp_melee_damage")
					.defineInRange("Sets Imp Melee Damage", 4, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Pinky");
			this.pinky_biomes = builder.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.pinky_biomes").define("Pinky Biomes", Arrays.asList("#nether"));
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
					.translation("text.doom.config.spectre_biomes").define("Spectre Biomes", Arrays.asList("#nether"));
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
					.define("Lost Soul Biomes", Arrays.asList("#nether"));
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
					.define("Cacodemon Biomes", Arrays.asList("#nether"));
			this.cacodemon_spawn_weight = builder.translation("text.doom.config.cacodemon_spawn_weight")
					.defineInRange("Cacodemon Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.cacodemon_min_group = builder.translation("text.doom.config.cacodemon_min_group")
					.defineInRange("Cacodemon Min Group", 1, 1, Integer.MAX_VALUE);
			this.cacodemon_max_group = builder.translation("text.doom.config.cacodemon_max_group")
					.defineInRange("Cacodemon Max Group", 2, 1, Integer.MAX_VALUE);
			this.cacodemon_health = builder.translation("text.doom.config.cacodemon_health")
					.defineInRange("Sets Cacodemon Max Health", 80, 1, Double.MAX_VALUE);
			this.cacodemon_ranged_damage = builder.translation("text.doom.config.cacodemon_ranged_damage")
					.comment("Sets Cacodemon Ranged Damage").define("Sets Cacodemon Ranged Damage damage", 5F);
			builder.pop();

			builder.push("Mob Settings:Archvile");
			this.archvile_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.archvile_biomes")
					.define("Archvile Biomes", Arrays.asList("#nether"));
			this.archvile_spawn_weight = builder.translation("text.doom.config.archvile_spawn_weight")
					.defineInRange("Archvile Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.archvile_min_group = builder.translation("text.doom.config.archvile_min_group")
					.defineInRange("Archvile Min Group", 1, 1, Integer.MAX_VALUE);
			this.archvile_max_group = builder.translation("text.doom.config.archvile_max_group")
					.defineInRange("Archvile Max Group", 2, 1, Integer.MAX_VALUE);
			this.archvile_health = builder.translation("text.doom.config.archvile_health")
					.defineInRange("Sets Archvile Max Health", 100, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Baron of Hell");
			this.baron_biomes = builder.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.baron_biomes").define("Baron Biomes", Arrays.asList("#nether"));
			this.baron_spawn_weight = builder.translation("text.doom.config.baron_spawn_weight")
					.defineInRange("Baron of Hell Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.baron_min_group = builder.translation("text.doom.config.baron_min_group")
					.defineInRange("Baron of Hell Min Group", 1, 1, Integer.MAX_VALUE);
			this.baron_max_group = builder.translation("text.doom.config.baron_max_group")
					.defineInRange("Baron of Hell Max Group", 1, 1, Integer.MAX_VALUE);
			this.baron_health = builder.translation("text.doom.config.baron_health")
					.defineInRange("Sets Baron of Hell Max Health", 180, 1, Double.MAX_VALUE);
			this.baron_ranged_damage = builder.translation("text.doom.config.baron_ranged_damage")
					.define("Sets Baron of Hell Ranged Damage", 6F);
			this.baron_melee_damage = builder.translation("text.doom.config.baron_melee_damage")
					.defineInRange("Sets Baron of Hell Melee Damage", 7, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Mancubus");
			this.mancubus_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.mancubus_biomes")
					.define("Mancubus Biomes", Arrays.asList("#nether"));
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
					.define("Sets Mancubus Ranged Damage", 6F);
			builder.pop();

			builder.push("Mob Settings:Revenant");
			this.revenant_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.revenant_biomes")
					.define("Revenant Biomes", Arrays.asList("#nether"));
			this.revenant_spawn_weight = builder.translation("text.doom.config.revenant_spawn_weight")
					.defineInRange("Revenant Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.revenant_min_group = builder.translation("text.doom.config.revenant_min_group")
					.defineInRange("Revenant Min Group", 1, 1, Integer.MAX_VALUE);
			this.revenant_max_group = builder.translation("text.doom.config.revenant_max_group")
					.defineInRange("Revenant Max Group", 4, 1, Integer.MAX_VALUE);
			this.revenant_health = builder.translation("text.doom.config.revenant_health")
					.defineInRange("Sets Revenant Max Health", 45, 1, Double.MAX_VALUE);
			this.revenant_ranged_damage = builder.translation("text.doom.config.revenant_ranged_damage")
					.define("Sets Revenant Ranged Damage", 5F);
			this.revenant_melee_damage = builder.translation("text.doom.config.revenant_melee_damage")
					.defineInRange("Sets Revenant Melee Damage", 3, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Zombieman");
			this.zombieman_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.zombieman_biomes")
					.define("Zombieman Biomes", Arrays.asList("#nether"));
			this.zombieman_spawn_weight = builder.translation("text.doom.config.zombieman_spawn_weight")
					.defineInRange("Zombieman Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.zombieman_min_group = builder.translation("text.doom.config.zombieman_min_group")
					.defineInRange("Zombieman Min Group", 1, 1, Integer.MAX_VALUE);
			this.zombieman_max_group = builder.translation("text.doom.config.zombieman_max_group")
					.defineInRange("Zombieman Max Group", 4, 1, Integer.MAX_VALUE);
			this.zombieman_health = builder.translation("text.doom.config.zombieman_health")
					.defineInRange("Sets Zombieman Max Health", 15, 1, Double.MAX_VALUE);
			this.zombieman_melee_damage = builder.translation("text.doom.config.zombieman_melee_damage")
					.defineInRange("Sets Zombieman Melee Damage", 2, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Arachnotron");
			this.arachnotron_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.arachnotron_biomes")
					.define("Arachnotron Biomes", Arrays.asList("#nether"));
			this.arachnotron_spawn_weight = builder.translation("text.doom.config.arachnotron_spawn_weight")
					.defineInRange("Arachnotron Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.arachnotron_min_group = builder.translation("text.doom.config.arachnotron_min_group")
					.defineInRange("Arachnotron Min Group", 1, 1, Integer.MAX_VALUE);
			this.arachnotron_max_group = builder.translation("text.doom.config.arachnotron_max_group")
					.defineInRange("Arachnotron Max Group", 4, 1, Integer.MAX_VALUE);
			this.arachnotron_health = builder.translation("text.doom.config.arachnotron_health")
					.defineInRange("Sets Arachnotron Max Health", 30, 1, Double.MAX_VALUE);
			this.arachnotron_ranged_damage = builder.translation("text.doom.config.arachnotron_ranged_damage")
					.define("Sets Arachnotron Ranged Damage", 5F);
			builder.pop();

			builder.push("Mob Settings:Imps 2016");
			this.imp2016_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.imp2016_biomes").define("Imp 2016 Biomes", Arrays.asList("#nether"));
			this.imp2016_spawn_weight = builder.translation("text.doom.config.imp2016_spawn_weight")
					.defineInRange("Imp 2016 Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.imp2016_min_group = builder.translation("text.doom.config.imp2016_min_group")
					.defineInRange("Imp 2016 Min Group", 1, 1, Integer.MAX_VALUE);
			this.imp2016_max_group = builder.translation("text.doom.config.imp2016_max_group")
					.defineInRange("Imp 2016 Max Group", 4, 1, Integer.MAX_VALUE);
			this.imp2016_health = builder.translation("text.doom.config.imp2016_health")
					.defineInRange("Sets Imp 2016 Max Health", 30, 1, Double.MAX_VALUE);
			this.imp2016_ranged_damage = builder.translation("text.doom.config.imp2016_ranged_damage")
					.define("Sets Imp 2016 Ranged Damage", 4F);
			this.imp2016_melee_damage = builder.translation("text.doom.config.imp2016_melee_damage")
					.defineInRange("Sets Imp 2016 Melee Damage", 2, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Gargoyle");
			this.gargoyle_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.gargoyle_biomes")
					.define("Gargoyle Biomes", Arrays.asList("#nether"));
			this.gargoyle_spawn_weight = builder.translation("text.doom.config.gargoyle_spawn_weight")
					.defineInRange("Gargoyle Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.gargoyle_min_group = builder.translation("text.doom.config.gargoyle_min_group")
					.defineInRange("Gargoyle Min Group", 1, 1, Integer.MAX_VALUE);
			this.gargoyle_max_group = builder.translation("text.doom.config.gargoyle_max_group")
					.defineInRange("Gargoyle Max Group", 4, 1, Integer.MAX_VALUE);
			this.gargoyle_health = builder.translation("text.doom.config.gargoyle_health")
					.defineInRange("Sets Gargoyle Max Health", 30, 1, Double.MAX_VALUE);
			this.gargoyle_ranged_damage = builder.translation("text.doom.config.gargoyle_ranged_damage")
					.define("Sets Gargoyle Ranged Damage", 5F);
			this.gargoyle_melee_damage = builder.translation("text.doom.config.gargoyle_melee_damage")
					.defineInRange("Sets Gargoyle Melee Damage", 2, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Nightmare Imp");
			this.nightmare_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.nightmare_biomes")
					.define("Nightmare Imp Biomes", Arrays.asList("#nether"));
			this.nightmare_spawn_weight = builder.translation("text.doom.config.nightmare_spawn_weight")
					.defineInRange("Nightmare Imp Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.nightmare_min_group = builder.translation("text.doom.config.nightmare_min_group")
					.defineInRange("Nightmare Imp Min Group", 1, 1, Integer.MAX_VALUE);
			this.nightmare_max_group = builder.translation("text.doom.config.nightmare_max_group")
					.defineInRange("Nightmare Imp Max Group", 4, 1, Integer.MAX_VALUE);
			this.nightmare_imp_health = builder.translation("text.doom.config.nightmare_imp_health")
					.defineInRange("Sets Nightmare Imp Max Health", 30, 1, Double.MAX_VALUE);
			this.nightmare_ranged_damage = builder.translation("text.doom.config.nightmare_ranged_damage")
					.define("Sets Nightmare Imp Ranged Damage", 4F);
			this.nightmare_melee_damage = builder.translation("text.doom.config.nightmare_melee_damage")
					.defineInRange("Sets Nightmare Imp Melee Damage", 2, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Chaingunner");
			this.chaingunner_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.chaingunner_biomes")
					.define("Chaingunner Biomes", Arrays.asList("#nether"));
			this.chaingunner_spawn_weight = builder.translation("text.doom.config.chaingunner_spawn_weight")
					.defineInRange("Chaingunner Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.chaingunner_min_group = builder.translation("text.doom.config.chaingunner_min_group")
					.defineInRange("Chaingunner Min Group", 1, 1, Integer.MAX_VALUE);
			this.chaingunner_max_group = builder.translation("text.doom.config.chaingunner_max_group")
					.defineInRange("Chaingunner Max Group", 4, 1, Integer.MAX_VALUE);
			this.chaingunner_health = builder.translation("text.doom.config.chaingunner_health")
					.defineInRange("Sets Chaingunner Max Health", 15, 1, Double.MAX_VALUE);
			this.chaingunner_melee_damage = builder.translation("text.doom.config.chaingunner_melee_damage")
					.defineInRange("Sets Chaingunner Melee Damage", 2, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Shotgun Guy");
			this.shotgunguy_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.shotgunguy_biomes")
					.define("Shotgun Guy Biomes", Arrays.asList("#nether"));
			this.shotgunguy_spawn_weight = builder.translation("text.doom.config.shotgunguy_spawn_weight")
					.defineInRange("Shotgun Guy Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.shotgunguy_min_group = builder.translation("text.doom.config.shotgunguy_min_group")
					.defineInRange("Shotgun Guy Min Group", 1, 1, Integer.MAX_VALUE);
			this.shotgunguy_max_group = builder.translation("text.doom.config.shotgunguy_max_group")
					.defineInRange("Shotgun Guy Max Group", 4, 1, Integer.MAX_VALUE);
			this.shotgunguy_health = builder.translation("text.doom.config.shotgunguy_health")
					.defineInRange("Sets Shotgun Guy Max Health", 15, 1, Double.MAX_VALUE);
			this.shotgunguy_melee_damage = builder.translation("text.doom.config.shotgunguy_melee_damage")
					.defineInRange("Sets Shotgun Guy Melee Damage", 2, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Marauder");
			this.marauder_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.marauder_biomes")
					.define("Marauder Biomes", Arrays.asList("#nether"));
			this.marauder_spawn_weight = builder.translation("text.doom.config.marauder_spawn_weight")
					.defineInRange("Marauder Spawn Weight", 3, 1, Integer.MAX_VALUE);
			this.marauder_min_group = builder.translation("text.doom.config.marauder_min_group")
					.defineInRange("Marauder Min Group", 1, 1, Integer.MAX_VALUE);
			this.marauder_max_group = builder.translation("text.doom.config.marauder_max_group")
					.defineInRange("Marauder Max Group", 1, 1, Integer.MAX_VALUE);
			this.marauder_health = builder.translation("text.doom.config.marauder_health")
					.defineInRange("Sets Marauder Max Health", 300, 1, Double.MAX_VALUE);
			this.marauder_melee_damage = builder.translation("text.doom.config.marauder_melee_damage")
					.defineInRange("Sets Marauder Melee Damage", 6, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Pain Elemental");
			this.pain_biomes = builder.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.pain_biomes")
					.define("Pain Elemental Biomes", Arrays.asList("#nether"));
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
					.define("Hellknight Biomes", Arrays.asList("#nether"));
			this.hellknight_spawn_weight = builder.translation("text.doom.config.hellknight_spawn_weight")
					.defineInRange("Hellknight Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.hellknight_min_group = builder.translation("text.doom.config.hellknight_min_group")
					.defineInRange("Hellknight Min Group", 1, 1, Integer.MAX_VALUE);
			this.hellknight_max_group = builder.translation("text.doom.config.hellknight_max_group")
					.defineInRange("Hellknight Max Group", 2, 1, Integer.MAX_VALUE);
			this.hellknight_health = builder.translation("text.doom.config.hellknight_health")
					.defineInRange("Sets Hellknight Max Health", 90, 1, Double.MAX_VALUE);
			this.hellknight_ranged_damage = builder.translation("text.doom.config.hellknight_ranged_damage")
					.define("Sets the Ranged Damage", 6F);
			this.hellknight_melee_damage = builder.translation("text.doom.config.hellknight_melee_damage")
					.defineInRange("Sets Hellknight Melee Damage", 4, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Hellknight 2016");
			this.hellknight2016_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.hellknight2016_biomes")
					.define("Hellknight 2016 Biomes", Arrays.asList("#nether"));
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
					.define("Cyberdemon Biomes", Arrays.asList("#nether"));
			this.cyberdemon_spawn_weight = builder.translation("text.doom.config.cyberdemon_spawn_weight")
					.defineInRange("Cyberdemon Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.cyberdemon_min_group = builder.translation("text.doom.config.cyberdemon_min_group")
					.defineInRange("Cyberdemon Min Group", 1, 1, Integer.MAX_VALUE);
			this.cyberdemon_max_group = builder.translation("text.doom.config.cyberdemon_max_group")
					.defineInRange("Cyberdemon Max Group", 1, 1, Integer.MAX_VALUE);
			this.cyberdemon_health = builder.translation("text.doom.config.cyberdemon_health")
					.defineInRange("Sets Cyberdemon Max Health", 300, 1, Double.MAX_VALUE);
			this.cyberdemon_ranged_damage = builder.translation("text.doom.config.cyberdemon_ranged_damage")
					.define("Sets Cyberdemon Ranged Damage", 9F);
			this.cyberdemon_melee_damage = builder.translation("text.doom.config.cyberdemon_melee_damage")
					.defineInRange("Sets Cyberdemon Melee Damage", 7, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Unwilling");
			this.unwilling_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.unwilling_biomes")
					.define("Unwilling Biomes", Arrays.asList("#nether"));
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
					.define("Possessed Scientist Biomes", Arrays.asList("#nether"));
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
					.define("Possessed Soldier Biomes", Arrays.asList("#nether"));
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
					.define("Sets Possessed Solider Ranged Damage", 2F);
			this.possessed_soldier_melee_damage = builder.translation("text.doom.config.possessed_soldier_melee_damage")
					.defineInRange("Sets Possessed Solider Melee Damage", 2, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Mechazombie");
			this.mechazombie_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.imp_biomes").define("Mechazombie Biomes", Arrays.asList("#nether"));
			this.mechazombie_spawn_weight = builder.translation("text.doom.config.mechazombie_spawn_weight")
					.defineInRange("Mechazombie Spawn Weight", 15, 1, Integer.MAX_VALUE);
			this.mechazombie_min_group = builder.translation("text.doom.config.mechazombie_min_group")
					.defineInRange("Mechazombie Min Group", 1, 1, Integer.MAX_VALUE);
			this.mechazombie_max_group = builder.translation("text.doom.config.mechazombie_max_group")
					.defineInRange("Mechazombie Max Group", 4, 1, Integer.MAX_VALUE);
			this.mechazombie_health = builder.translation("text.doom.config.mechazombie_health")
					.defineInRange("Sets the Mechazombie Health", 25, 1, Double.MAX_VALUE);
			this.mechazombie_ranged_damage = builder.translation("text.doom.config.mechazombie_ranged_damage")
					.define("Sets Mechazombie Ranged Damage", 3F);
			this.mechazombie_melee_damage = builder.translation("text.doom.config.mechazombie_melee_damage")
					.defineInRange("Sets Mechazombie Melee Damage", 5, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Cueball");
			this.cueball_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.cueball_biomes").define("Cueball Biomes", Arrays.asList("#nether"));
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
					.translation("text.doom.config.prowler_biomes").define("Prowler Biomes", Arrays.asList("#nether"));
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
					.define("Sets Prowler Ranged Damage", 4F);
			builder.pop();

			builder.push("Mob Settings:Stone Imps");
			this.impstone_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.impstone_biomes")
					.define("Stone Imp Biomes", Arrays.asList("#nether"));
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
					.define("Gorenest Biomes", Arrays.asList("#nether"));
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
					.define("Tentacle Biomes", Arrays.asList("#nether"));
			this.tentacle_spawn_weight = builder.translation("text.doom.config.tentacle_spawn_weight")
					.defineInRange("Tentacle Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.tentacle_min_group = builder.translation("text.doom.config.tentacle_min_group")
					.defineInRange("Tentacle Min Group", 1, 1, Integer.MAX_VALUE);
			this.tentacle_max_group = builder.translation("text.doom.config.tentacle_max_group")
					.defineInRange("Tentacle Max Group", 1, 1, Integer.MAX_VALUE);
			this.tentacle_health = builder.translation("text.doom.config.tentacle_health")
					.defineInRange("Sets Tentacle Max Health", 5, 1, Double.MAX_VALUE);
			this.tentacle_melee_damage = builder.translation("text.doom.config.tentacle_melee_damage")
					.define("Sets Tentacle Melee Damage", 1F);
			builder.pop();

			builder.push("Mob Settings:Cyberdemon 2016");
			this.cyberdemon2016_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.cyberdemon2016_biomes")
					.define("Cyberdemon 2016 Biomes", Arrays.asList("#nether"));
			this.cyberdemon2016_spawn_weight = builder.translation("text.doom.config.cyberdemon2016_spawn_weight")
					.defineInRange("Cyberdemon 2016 Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.cyberdemon2016_min_group = builder.translation("text.doom.config.cyberdemon2016_min_group")
					.defineInRange("Cyberdemon 2016 Min Group", 1, 1, Integer.MAX_VALUE);
			this.cyberdemon2016_max_group = builder.translation("text.doom.config.cyberdemon2016_max_group")
					.defineInRange("Cyberdemon 2016 Max Group", 1, 1, Integer.MAX_VALUE);
			this.cyberdemon2016_health = builder.translation("text.doom.config.cyberdemon2016_health")
					.defineInRange("Sets Cyberdemon 2016 Max Health", 300, 1, Double.MAX_VALUE);
			this.cyberdemon2016_ranged_damage = builder.translation("text.doom.config.cyberdemon2016_ranged_damage")
					.define("Sets Cyberdemon 2016 Ranged Damage", 9F);
			this.cyberdemon2016_melee_damage = builder.translation("text.doom.config.cyberdemon2016_melee_damage")
					.defineInRange("Sets Cyberdemon 2016 Melee Damage", 7, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Tyrant");
			this.tyrant_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.tyrant_biomes").define("Tyrant Biomes", Arrays.asList("#nether"));
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
					.define("Spider Mastermind Biomes", Arrays.asList("#nether"));
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
					.define("Sets Spider Mastermind Ranged Damage", 7F);
			this.spider_mastermind_melee_damage = builder.translation("text.doom.config.spider_mastermind_melee_damage")
					.defineInRange("Sets Spider Mastermind Melee Damage", 4, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Whiplash");
			this.whiplash_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.whiplash_biomes")
					.define("Whiplash Biomes", Arrays.asList("#nether"));
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
					.define("Doom Hunter Biomes", Arrays.asList("#nether"));
			this.doomhunter_spawn_weight = builder.translation("text.doom.config.doomhunter_spawn_weight")
					.defineInRange("Doom Hunter Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.doomhunter_min_group = builder.translation("text.doom.config.doomhunter_min_group")
					.defineInRange("Doom Hunter Min Group", 1, 1, Integer.MAX_VALUE);
			this.doomhunter_max_group = builder.translation("text.doom.config.doomhunter_max_group")
					.defineInRange("Doom Hunter Max Group", 1, 1, Integer.MAX_VALUE);
			this.doomhunter_health = builder.translation("text.doom.config.doomhunter_health")
					.defineInRange("Sets Doomhunter Max Health", 150, 1, Double.MAX_VALUE);
			this.doomhunter_ranged_damage = builder.translation("text.doom.config.doomhunter_ranged_damage")
					.define("Sets Doomhunter Ranged Damage", 7F);
			this.doomhunter_melee_damage = builder.translation("text.doom.config.doomhunter_melee_damage")
					.defineInRange("Sets Doomhunter Melee Damage", 5, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Possessed Worker");
			this.possessed_worker_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.possessed_worker_biomes")
					.define("Possessed Worker Biomes", Arrays.asList("#nether"));
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
					.define("Armored Baron Biomes", Arrays.asList("#nether"));
			this.armoredbaron_spawn_weight = builder.translation("text.doom.config.armoredbaron_spawn_weight")
					.defineInRange("Armored Baron Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.armoredbaron_min_group = builder.translation("text.doom.config.armoredbaron_min_group")
					.defineInRange("Armored Baron Min Group", 1, 1, Integer.MAX_VALUE);
			this.armoredbaron_max_group = builder.translation("text.doom.config.armoredbaron_max_group")
					.defineInRange("Armored Baron Max Group", 1, 1, Integer.MAX_VALUE);
			this.armoredbaron_health = builder.translation("text.doom.config.armoredbaron_health")
					.defineInRange("Sets Armored Baron Max Health", 240, 1, Double.MAX_VALUE);
			this.armoredbaron_ranged_damage = builder.translation("text.doom.config.armoredbaron_ranged_damage")
					.define("Sets Armored Baron Ranged Damage", 6F);
			this.armoredbaron_melee_damage = builder.translation("text.doom.config.armoredbaron_melee_damage")
					.defineInRange("Sets Armored Baron Melee Damage", 7, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Summoner");
			this.summoner_biomes = builder
					.comment("Supports Biome Registry Names (minecraft:desert) or Biomes Tag with #")
					.translation("text.doom.config.summoner_biomes")
					.define("Summoner Biomes", Arrays.asList("#nether"));
			this.summoner_spawn_weight = builder.translation("text.doom.config.summoner_spawn_weight")
					.defineInRange("Summoner Spawn Weight", 4, 1, Integer.MAX_VALUE);
			this.summoner_min_group = builder.translation("text.doom.config.summoner_min_group")
					.defineInRange("Summoner Min Group", 1, 1, Integer.MAX_VALUE);
			this.summoner_max_group = builder.translation("text.doom.config.summoner_max_group")
					.defineInRange("Summoner Max Group", 2, 1, Integer.MAX_VALUE);
			this.summoner_health = builder.translation("text.doom.config.summoner_health")
					.defineInRange("Sets Summoner Max Health", 100, 1, Double.MAX_VALUE);
			this.summoner_melee_damage = builder.translation("text.doom.config.summoner_melee_damage")
					.defineInRange("Sets Summoner Melee Damage", 3, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Maykr Drone");
			this.maykrdrone_health = builder.translation("text.doom.config.maykrdrone_health")
					.defineInRange("Sets Maykr Drone Max Health", 20, 1, Double.MAX_VALUE);
			this.maykrdrone_ranged_damage = builder.translation("text.doom.config.maykrdrone_ranged_damage")
					.define("Sets Maykr Drone Ranged Damage", 2F);
			builder.pop();

			builder.push("Mob Settings:Blood Maykr");
			this.bloodmaykr_health = builder.translation("text.doom.config.bloodmaykr_health")
					.defineInRange("Sets Blood Maykr Max Health", 45, 1, Double.MAX_VALUE);
			this.bloodmaykr_ranged_damage = builder.translation("text.doom.config.bloodmaykr_ranged_damage")
					.define("Sets Blood Maykr Ranged Damage", 5F);
			this.bloodmaykr_melee_damage = builder.translation("text.doom.config.bloodmaykr_melee_damage")
					.defineInRange("Sets Blood Maykr Melee Damage", 4, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Archmaykr");
			this.archmaykr_health = builder.translation("text.doom.config.archmaykr_health")
					.defineInRange("Sets Archmaykr Max Health", 400, 1, Double.MAX_VALUE);
			this.archmaykr_ranged_damage = builder.translation("text.doom.config.archmaykr_ranged_damage")
					.define("Sets Archmaykr Ranged Damage", 14F);
			this.archmaykr_melee_damage = builder.translation("text.doom.config.archmaykr_melee_damage")
					.defineInRange("Sets Archmaykr Melee Damage", 6, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Turret");
			this.turret_health = builder.translation("text.doom.config.turret_health")
					.defineInRange("Sets Turret Max Health", 5, 1, Double.MAX_VALUE);
			this.turret_ranged_damage = builder.translation("text.doom.config.turret_ranged_damage")
					.define("Sets Turret Ranged Damage", 6F);
			builder.pop();

			builder.push("Mob Settings:Icon of Sin");
			this.icon_health = builder.translation("text.doom.config.icon_health")
					.defineInRange("Sets Icon of Sin Max Health", 1000, 1, Double.MAX_VALUE);
			this.icon_melee_damage = builder.translation("text.doom.config.icon_melee_damage")
					.define("Sets Icon of Sin Melee Damage", 7F);
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
}
