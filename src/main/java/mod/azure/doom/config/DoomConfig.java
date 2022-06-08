package mod.azure.doom.config;

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
		public final ConfigValue<List<? extends String>> bfg_damage_mob_whitelist;
		public final ConfigValue<Double> bullet_damage;
		public final ConfigValue<Double> chaingun_bullet_damage;
		public final ConfigValue<Double> energycell_damage;
		public final ConfigValue<Double> rocket_damage;
		public final ConfigValue<Double> shotgun_damage;
		public final ConfigValue<Double> unmaykr_damage;
		public final ConfigValue<Double> grenade_damage;
		public final ConfigValue<Double> max_meathook_distance;

		public final ConfigValue<Double> motherdemon_health;
		public final ConfigValue<Double> motherdemon_ranged_damage;
		public final ConfigValue<Double> motherdemon_phaseone_damage_boos;

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
		public final ConfigValue<Double> archmaykr_phaseone_damage_boost;
		public final ConfigValue<Double> archmaykr_phasetwo_damage_boost;
		public final ConfigValue<Double> archmaykr_phasethree_damage_boost;
		public final ConfigValue<Double> archmaykr_phasefour_damage_boost;

		public final ConfigValue<Double> baron_health;
		public final ConfigValue<Double> baron_ranged_damage;
		public final ConfigValue<Double> baron_melee_damage;

		public final ConfigValue<Double> gladiator_health;
		public final ConfigValue<Double> gladiator_ranged_damage;
		public final ConfigValue<Double> gladiator_phaseone_damage_boost;
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

		public final ConfigValue<Double> impstone_health;
		public final ConfigValue<Double> impstone_melee_damage;

		public final ConfigValue<Double> gargoyle_health;
		public final ConfigValue<Double> gargoyle_ranged_damage;
		public final ConfigValue<Double> gargoyle_melee_damage;

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
		public final ConfigValue<Double> doomhunter_extra_phase_two_damage;

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

		public final ConfigValue<Double> cueball_health;

		public final ConfigValue<Double> tentacle_health;
		public final ConfigValue<Double> tentacle_melee_damage;

		public final ConfigValue<Double> turret_health;
		public final ConfigValue<Double> turret_ranged_damage;

		public final ConfigValue<Double> icon_health;
		public final ConfigValue<Double> icon_melee_damage;
		public final ConfigValue<Double> icon_phaseone_damage_boos;
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
			this.bfg_damage_mob_whitelist = builder.comment("Adds mob to list of mobs that can hurt by the BFG. Supports Registry Names (minecraft:cow)")
					.translation("text.doom.config.bfg_damage_mob_whitelist")
					.defineList("BFG Damage Whitelist", Lists.newArrayList(""), o -> o instanceof String);
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
			this.max_meathook_distance = builder.translation("text.doom.config.max_meathook_distance")
					.defineInRange("Max Meathook Distance", 32, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Imps");
			this.imp_health = builder.translation("text.doom.config.imp_health").comment("Sets Imp Max Health")
					.defineInRange("Sets Imp Max health", 30, 1, Double.MAX_VALUE);
			this.imp_ranged_damage = builder.translation("text.doom.config.imp_ranged_damage")
					.defineInRange("Sets Imp Ranged Damage damage", 4, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Pinky");
			this.pinky_health = builder.translation("text.doom.config.pinky_health").comment("Sets Pinky Max Health")
					.defineInRange("Sets Pinky Max Health", 75, 1, Double.MAX_VALUE);
			this.pinky_melee_damage = builder.translation("text.doom.config.pinky_melee_damage")
					.defineInRange("Sets Pinky Melee Damage", 3, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Spectre");
			this.spectre_health = builder.translation("text.doom.config.spectre_health")
					.defineInRange("Sets Spectre Max Health", 75, 1, Double.MAX_VALUE);
			this.spectre_melee_damage = builder.translation("text.doom.config.spectre_melee_damage")
					.defineInRange("Sets Spectre Melee Damage", 3, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Lost Soul");
			this.lost_soul_health = builder.translation("text.doom.config.lost_soul_health")
					.defineInRange("Sets Lost Soul Max Health", 10, 1, Double.MAX_VALUE);
			this.lost_soul_melee_damage = builder.translation("text.doom.config.lost_soul_melee_damage")
					.defineInRange("Sets Lost Soul Melee Damage", 1, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Cacodemon");
			this.cacodemon_health = builder.translation("text.doom.config.cacodemon_health")
					.defineInRange("Sets Cacodemon Max Health", 80, 1, Double.MAX_VALUE);
			this.cacodemon_ranged_damage = builder.translation("text.doom.config.cacodemon_ranged_damage")
					.comment("Sets Cacodemon Ranged Damage")
					.defineInRange("Sets Cacodemon Ranged Damage damage", 5, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Archvile");
			this.archvile_health = builder.translation("text.doom.config.archvile_health")
					.defineInRange("Sets Archvile Max Health", 100, 1, Double.MAX_VALUE);
			this.archvile_ranged_damage = builder.translation("text.doom.config.archvile_ranged_damage")
					.defineInRange("Sets Archvile Ranged Damage damage", 5, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Baron of Hell");
			this.baron_health = builder.translation("text.doom.config.baron_health")
					.defineInRange("Sets Baron of Hell Max Health", 180, 1, Double.MAX_VALUE);
			this.baron_ranged_damage = builder.translation("text.doom.config.baron_ranged_damage")
					.defineInRange("Sets Baron of Hell Ranged Damage", 6, 1, Double.MAX_VALUE);
			this.baron_melee_damage = builder.translation("text.doom.config.baron_melee_damage")
					.defineInRange("Sets Baron of Hell Melee Damage", 7, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Gladiator");
			this.gladiator_health = builder.translation("text.doom.config.gladiator_health")
					.defineInRange("Sets Gladiator Max Health", 240, 1, Double.MAX_VALUE);
			this.gladiator_ranged_damage = builder.translation("text.doom.config.gladiator_ranged_damage")
					.defineInRange("Sets Gladiator Ranged Damage", 6, 1, Double.MAX_VALUE);
			this.gladiator_phaseone_damage_boost = builder
					.translation("text.doom.config.gladiator_phaseone_damage_boost")
					.defineInRange("Sets Gladiator Ranged Damage For 2nd Phase", 6, 1, Double.MAX_VALUE);
			this.gladiator_melee_damage = builder.translation("text.doom.config.gladiator_melee_damage")
					.defineInRange("Sets Gladiator Melee Damage", 7, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Mancubus");
			this.mancubus_health = builder.translation("text.doom.config.mancubus_health")
					.defineInRange("Sets Mancubus Max Health", 80, 1, Double.MAX_VALUE);
			this.mancubus_melee_damage = builder.translation("text.doom.config.mancubus_melee_damage")
					.defineInRange("Sets Mancubus Melee Damage", 4, 1, Double.MAX_VALUE);
			this.mancubus_ranged_damage = builder.translation("text.doom.config.mancubus_ranged_damage")
					.defineInRange("Sets Mancubus Ranged Damage", 6, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Revenant");
			this.revenant_health = builder.translation("text.doom.config.revenant_health")
					.defineInRange("Sets Revenant Max Health", 45, 1, Double.MAX_VALUE);
			this.revenant_ranged_damage = builder.translation("text.doom.config.revenant_ranged_damage")
					.defineInRange("Sets Revenant Ranged Damage", 5, 1, Double.MAX_VALUE);
			this.revenant_melee_damage = builder.translation("text.doom.config.revenant_melee_damage")
					.defineInRange("Sets Revenant Melee Damage", 3, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Zombieman");
			this.zombieman_health = builder.translation("text.doom.config.zombieman_health")
					.defineInRange("Sets Zombieman Max Health", 15, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Arachnotron");
			this.arachnotron_health = builder.translation("text.doom.config.arachnotron_health")
					.defineInRange("Sets Arachnotron Max Health", 30, 1, Double.MAX_VALUE);
			this.arachnotron_ranged_damage = builder.translation("text.doom.config.arachnotron_ranged_damage")
					.defineInRange("Sets Arachnotron Ranged Damage", 5, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Gargoyle");
			this.gargoyle_health = builder.translation("text.doom.config.gargoyle_health")
					.defineInRange("Sets Gargoyle Max Health", 30, 1, Double.MAX_VALUE);
			this.gargoyle_ranged_damage = builder.translation("text.doom.config.gargoyle_ranged_damage")
					.defineInRange("Sets Gargoyle Ranged Damage", 5, 1, Double.MAX_VALUE);
			this.gargoyle_melee_damage = builder.translation("text.doom.config.gargoyle_melee_damage")
					.defineInRange("Sets Gargoyle Melee Damage", 2, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Chaingunner");
			this.chaingunner_health = builder.translation("text.doom.config.chaingunner_health")
					.defineInRange("Sets Chaingunner Max Health", 15, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Shotgun Guy");
			this.shotgunguy_health = builder.translation("text.doom.config.shotgunguy_health")
					.defineInRange("Sets Shotgun Guy Max Health", 15, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Marauder");
			this.marauder_health = builder.translation("text.doom.config.marauder_health")
					.defineInRange("Sets Marauder Max Health", 300, 1, Double.MAX_VALUE);
			this.marauder_axe_damage = builder.translation("text.doom.config.marauder_axe_damage")
					.defineInRange("Sets Marauder Axe Damage", 6, 1, Double.MAX_VALUE);
			this.marauder_ssgdamage = builder.translation("text.doom.config.marauder_ssgdamage")
					.defineInRange("Sets Marauder Shotgun Damage", 10.5, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Pain Elemental");
			this.pain_health = builder.translation("text.doom.config.pain_health")
					.defineInRange("Sets Pain Elemental Max Health", 80, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Hellknight");
			this.hellknight_health = builder.translation("text.doom.config.hellknight_health")
					.defineInRange("Sets Hellknight Max Health", 90, 1, Double.MAX_VALUE);
			this.hellknight_ranged_damage = builder.translation("text.doom.config.hellknight_ranged_damage")
					.defineInRange("Sets the Ranged Damage", 6, 1, Double.MAX_VALUE);
			this.hellknight_melee_damage = builder.translation("text.doom.config.hellknight_melee_damage")
					.defineInRange("Sets Hellknight Melee Damage", 4, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Hellknight 2016");
			this.hellknight2016_health = builder.translation("text.doom.config.hellknight2016_health")
					.defineInRange("Sets Hellknight 2016 Max Health", 90, 1, Double.MAX_VALUE);
			this.hellknight2016_melee_damage = builder.translation("text.doom.config.hellknight2016_melee_damage")
					.defineInRange("Sets Hellknight 2016 Melee Damage", 4, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Cyberdemon");
			this.cyberdemon_health = builder.translation("text.doom.config.cyberdemon_health")
					.defineInRange("Sets Cyberdemon Max Health", 300, 1, Double.MAX_VALUE);
			this.cyberdemon_ranged_damage = builder.translation("text.doom.config.cyberdemon_ranged_damage")
					.defineInRange("Sets Cyberdemon Ranged Damage", 9, 1, Double.MAX_VALUE);
			this.cyberdemon_melee_damage = builder.translation("text.doom.config.cyberdemon_melee_damage")
					.defineInRange("Sets Cyberdemon Melee Damage", 7, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Unwilling");
			this.unwilling_health = builder.translation("text.doom.config.unwilling_health")
					.defineInRange("Sets Unwilling Max Health", 15, 1, Double.MAX_VALUE);
			this.unwilling_melee_damage = builder.translation("text.doom.config.unwilling_melee_damage")
					.defineInRange("Sets Unwilling Melee Damage", 2, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Possessed Scientist");
			this.possessed_scientist_health = builder.translation("text.doom.config.possessed_scientist_health")
					.defineInRange("Sets Possessed Scientist/Worker Max Health", 15, 1, Double.MAX_VALUE);
			this.possessed_scientist_melee_damage = builder
					.translation("text.doom.config.possessed_scientist_melee_damage")
					.defineInRange("Sets Possessed Scientist/Worker Melee Damage", 2, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Possessed Soldier");
			this.possessed_soldier_health = builder.translation("text.doom.config.possessed_soldier_health")
					.defineInRange("Sets Possessed Solider Max Health", 15, 1, Double.MAX_VALUE);
			this.possessed_soldier_ranged_damage = builder
					.translation("text.doom.config.possessed_soldier_ranged_damage")
					.defineInRange("Sets Possessed Solider Ranged Damage", 2, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Mechazombie");
			this.mechazombie_health = builder.translation("text.doom.config.mechazombie_health")
					.defineInRange("Sets the Mechazombie Health", 25, 1, Double.MAX_VALUE);
			this.mechazombie_ranged_damage = builder.translation("text.doom.config.mechazombie_ranged_damage")
					.defineInRange("Sets Mechazombie Ranged Damage", 3, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Cueball");
			this.cueball_health = builder.translation("text.doom.config.cueball_health")
					.defineInRange("Sets Cueball Max Health", 1, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Prowler");
			this.prowler_health = builder.translation("text.doom.config.prowler_health")
					.defineInRange("Sets Prowler Max Health", 15, 1, Double.MAX_VALUE);
			this.prowler_melee_damage = builder.translation("text.doom.config.prowler_melee_damage")
					.defineInRange("Sets the Prowler Damage", 4, 1, Double.MAX_VALUE);
			this.prowler_ranged_damage = builder.translation("text.doom.config.prowler_ranged_damage")
					.defineInRange("Sets Prowler Ranged Damage", 4, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Stone Imps");
			this.impstone_health = builder.translation("text.doom.config.impstone_health")
					.defineInRange("Sets Stone Imp Max Health", 15, 1, Double.MAX_VALUE);
			this.impstone_melee_damage = builder.translation("text.doom.config.impstone_melee_damage")
					.defineInRange("Sets Stone Imp Melee Damage", 2, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Gorenest");
			this.gorenest_health = builder.translation("text.doom.config.gorenest_health")
					.defineInRange("Sets Gorenest Max Health", 5, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Tentacle");
			this.tentacle_health = builder.translation("text.doom.config.tentacle_health")
					.defineInRange("Sets Tentacle Max Health", 5, 1, Double.MAX_VALUE);
			this.tentacle_melee_damage = builder.translation("text.doom.config.tentacle_melee_damage")
					.defineInRange("Sets Tentacle Melee Damage", 1, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Spider Mastermind");
			this.spider_mastermind_health = builder.translation("text.doom.config.spider_mastermind_health")
					.defineInRange("Sets Spider Mastermind Max Health", 300, 1, Double.MAX_VALUE);
			this.spider_mastermind_ranged_damage = builder
					.translation("text.doom.config.spider_mastermind_ranged_damage")
					.defineInRange("Sets Spider Mastermind Ranged Damage", 7, 1, Double.MAX_VALUE);
			this.spider_mastermind_melee_damage = builder.translation("text.doom.config.spider_mastermind_melee_damage")
					.defineInRange("Sets Spider Mastermind Melee Damage", 4, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Whiplash");
			this.whiplash_health = builder.translation("text.doom.config.whiplash_health")
					.defineInRange("Sets Whiplash Max Health", 90, 1, Double.MAX_VALUE);
			this.whiplash_melee_damage = builder.translation("text.doom.config.whiplash_melee_damage")
					.defineInRange("Sets Whiplash Melee Damage", 4, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Doom Hunter");
			this.doomhunter_health = builder.translation("text.doom.config.doomhunter_health")
					.defineInRange("Sets Doomhunter Max Health", 150, 1, Double.MAX_VALUE);
			this.doomhunter_ranged_damage = builder.translation("text.doom.config.doomhunter_ranged_damage")
					.defineInRange("Sets Doomhunter Ranged Damage", 7, 1, Double.MAX_VALUE);
			this.doomhunter_melee_damage = builder.translation("text.doom.config.doomhunter_melee_damage")
					.defineInRange("Sets Doomhunter Melee Damage", 5, 1, Double.MAX_VALUE);
			this.doomhunter_extra_phase_two_damage = builder
					.translation("text.doom.config.doomhunter_extra_phase_two_damage")
					.defineInRange("Sets Doomhunter Melee Damage For 2nd Phase", 5, 1, Double.MAX_VALUE);
			builder.pop();
			
			builder.push("Mob Settings:Armored Baron");
			this.armoredbaron_health = builder.translation("text.doom.config.armoredbaron_health")
					.defineInRange("Sets Armored Baron Max Health", 240, 1, Double.MAX_VALUE);
			this.armoredbaron_melee_damage = builder.translation("text.doom.config.armoredbaron_melee_damage")
					.defineInRange("Sets Armored Baron Melee Damage", 7, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Summoner");
			this.summoner_health = builder.translation("text.doom.config.summoner_health")
					.defineInRange("Sets Summoner Max Health", 100, 1, Double.MAX_VALUE);
			this.summoner_ranged_damage = builder.translation("text.doom.config.summoner_ranged_damage")
					.defineInRange("Sets Summoner Ranged Damage", 3, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Maykr Drone");
			this.maykrdrone_health = builder.translation("text.doom.config.maykrdrone_health")
					.defineInRange("Sets Maykr Drone Max Health", 50, 1, Double.MAX_VALUE);
			this.maykrdrone_ranged_damage = builder.translation("text.doom.config.maykrdrone_ranged_damage")
					.defineInRange("Sets Maykr Drone Ranged Damage", 5, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Blood Maykr");
			this.bloodmaykr_health = builder.translation("text.doom.config.bloodmaykr_health")
					.defineInRange("Sets Blood Maykr Max Health", 100, 1, Double.MAX_VALUE);
			this.bloodmaykr_ranged_damage = builder.translation("text.doom.config.bloodmaykr_ranged_damage")
					.defineInRange("Sets Blood Maykr Ranged Damage", 10, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Archmaykr");
			this.archmaykr_health = builder.translation("text.doom.config.archmaykr_health")
					.defineInRange("Sets Archmaykr Max Health", 400, 1, Double.MAX_VALUE);
			this.archmaykr_ranged_damage = builder.translation("text.doom.config.archmaykr_ranged_damage")
					.defineInRange("Sets Archmaykr Ranged Damage", 14, 1, Double.MAX_VALUE);
			this.archmaykr_phaseone_damage_boost = builder
					.translation("text.doom.config.archmaykr_phaseone_damage_boost")
					.defineInRange("Sets Archmaykr Ranged Damage For 2nd Phase", 14, 1, Double.MAX_VALUE);
			this.archmaykr_phasetwo_damage_boost = builder
					.translation("text.doom.config.archmaykr_phasetwo_damage_boost")
					.defineInRange("Sets Archmaykr Ranged Damage For 3nd Phase", 28, 1, Double.MAX_VALUE);
			this.archmaykr_phasethree_damage_boost = builder
					.translation("text.doom.config.archmaykr_phasethree_damage_boost")
					.defineInRange("Sets Archmaykr Ranged Damage For 4th Phase", 42, 1, Double.MAX_VALUE);
			this.archmaykr_phasefour_damage_boost = builder
					.translation("text.doom.config.archmaykr_phasefour_damage_boost")
					.defineInRange("Sets Archmaykr Ranged Damage For 5th Phase", 56, 1, Double.MAX_VALUE);
			builder.pop();

			builder.push("Mob Settings:Motherdemon");
			this.motherdemon_health = builder.translation("text.doom.config.motherdemon_health")
					.defineInRange("Sets Motherdemon Max Health", 500, 1, Double.MAX_VALUE);
			this.motherdemon_ranged_damage = builder.translation("text.doom.config.motherdemon_ranged_damage")
					.defineInRange("Sets Motherdemon Ranged Damage", 14, 1, Double.MAX_VALUE);
			this.motherdemon_phaseone_damage_boos = builder
					.translation("text.doom.config.motherdemon_phaseone_damage_boos")
					.defineInRange("Sets Motherdemon Extra Ranged Damage For 2nd Phase", 14, 1, Double.MAX_VALUE);
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
			this.icon_phaseone_damage_boos = builder.translation("text.doom.config.icon_phaseone_damage_boos")
					.defineInRange("Sets Icon of Sin Extra Melee Damage For 2nd Phase", 7, 1, Double.MAX_VALUE);
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
							"doom:prowler", "doom:prowler", "doom:prowler", "doom:imp", "doom:imp", "doom:imp",
							"doom:imp", "doom:imp", "doom:imp", "doom:imp", "doom:imp", "doom:imp", "doom:imp",
							"doom:imp", "doom:pinky", "doom:pinky", "doom:pinky", "doom:pinky", "doom:pinky",
							"doom:pinky", "doom:pinky", "doom:pinky", "doom:pinky", "doom:pinky", "doom:lost_soul",
							"doom:lost_soul", "doom:lost_soul", "doom:lost_soul", "doom:lost_soul", "doom:lost_soul",
							"doom:lost_soul", "doom:lost_soul", "doom:lost_soul", "doom:lost_soul"),
							o -> o instanceof String);
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
