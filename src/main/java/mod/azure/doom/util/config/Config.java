package mod.azure.doom.util.config;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import mod.azure.doom.DoomMod;
import mod.azure.doom.util.config.EntityDefaults.EntityConfigType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class Config {

	public static final ServerConfig SERVER;
	public static final ForgeConfigSpec SERVER_SPEC;
	public static final ForgeConfigSpec BIOME_SPEC;
	public static final BiomeConfig BIOME;

	static {
		{
			final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder()
					.configure(ServerConfig::new);
			SERVER_SPEC = specPair.getRight();
			SERVER = specPair.getLeft();
		}
		{
			final Pair<BiomeConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder()
					.configure(BiomeConfig::new);
			BIOME = specPair.getLeft();
			BIOME_SPEC = specPair.getRight();
		}
	}

	public static class ServerConfig {

		private ForgeConfigSpec.IntValue specCRUCIBLE_MARAUDER_MAX_DAMAGE;
		private ForgeConfigSpec.BooleanValue specENABLE_BLOCK_BREAKING;
		private ForgeConfigSpec.DoubleValue specargent_bolt_damage;
		private ForgeConfigSpec.DoubleValue specbfgball_damage;
		private ForgeConfigSpec.DoubleValue specbfgball_damage_dragon;
		private ForgeConfigSpec.DoubleValue specbfgball_damage_aoe;
		private ForgeConfigSpec.DoubleValue specbullet_damage;
		private ForgeConfigSpec.DoubleValue specchaingun_bullet_damage;
		private ForgeConfigSpec.DoubleValue specenergycell_damage;
		private ForgeConfigSpec.DoubleValue specrocket_damage;
		private ForgeConfigSpec.DoubleValue specshotgun_damage;
		private ForgeConfigSpec.DoubleValue specunmaykr_damage;

		public int CRUCIBLE_MARAUDER_MAX_DAMAGE;
		public boolean ENABLE_BLOCK_BREAKING;
		public Double argent_bolt_damage;
		public Double bfgball_damage;
		public Double bfgball_damage_dragon;
		public Double bfgball_damage_aoe;
		public Double bullet_damage;
		public Double chaingun_bullet_damage;
		public Double energycell_damage;
		public Double rocket_damage;
		public Double shotgun_damage;
		public Double unmaykr_damage;

		/*
		 * Spawn Weights
		 */
		public int COMMON_DEMON_SPAWN_WEIGHT;
		public int HEAVY_DEMON_SPAWN_WEIGHT;
		public boolean USE_INDIVIDUAL_SPAWN_RULES;
		public List<? extends String> BIOME_DICT;

		ServerConfig(ForgeConfigSpec.Builder builder) {
			builder.push("spawn_weight");

			// Default settings per entity here.
			for (EntityConfigType entityType : EntityConfigType.values())
				pushDefaults(builder, entityType);

			builder.pop();
			builder.push("gear");
			specCRUCIBLE_MARAUDER_MAX_DAMAGE = builder.comment("Crucible Sword/Marauder Axe Max Damage")
					.translation(DoomMod.MODID + ".config.crucible_marauder_max_damage")
					.defineInRange("CRUCIBLE_MARAUDER_MAX_DAMAGE", 5, 0, Integer.MAX_VALUE);
			specargent_bolt_damage = builder.comment("Argent Bolt Damage")
					.translation(DoomMod.MODID + ".config.argent_bolt_damage")
					.defineInRange("argent_bolt_damage", 14.5F, 0, Double.MAX_VALUE);
			specbfgball_damage = builder.comment("BFG Ball Damage")
					.translation(DoomMod.MODID + ".config.bfgball_damage")
					.defineInRange("bfgball_damage", 100.0F, 0, Double.MAX_VALUE);
			specbfgball_damage_dragon = builder.comment("BFG Ball Dragon Damage")
					.translation(DoomMod.MODID + ".config.bfgball_damage_dragon")
					.defineInRange("bfgball_damage_dragon", 30F, 0, Double.MAX_VALUE);
			specbfgball_damage_aoe = builder.comment("BFG Ball AoE Damage")
					.translation(DoomMod.MODID + ".config.bfgball_damage_aoe")
					.defineInRange("bfgball_damage_aoe", 10F, 0, Double.MAX_VALUE);
			specbullet_damage = builder.comment("Bullet Damage").translation(DoomMod.MODID + ".config.bullet_damage")
					.defineInRange("bullet_damage", 5.5F, 0, Double.MAX_VALUE);
			specchaingun_bullet_damage = builder.comment("Chaingun Bullet Damage")
					.translation(DoomMod.MODID + ".config.chaingun_bullet_damage")
					.defineInRange("chaingun_bullet_damage", 5.5F, 0, Double.MAX_VALUE);
			specenergycell_damage = builder.comment("Energy Cell Damage")
					.translation(DoomMod.MODID + ".config.energycell_damage")
					.defineInRange("energycell_damage", 1.5F, 0, Double.MAX_VALUE);
			specrocket_damage = builder.comment("Rocket Damage").translation(DoomMod.MODID + ".config.rocket_damage")
					.defineInRange("rocket_damage", 20.0F, 0, Double.MAX_VALUE);
			specshotgun_damage = builder.comment("Shotgun Damage").translation(DoomMod.MODID + ".config.shotgun_damage")
					.defineInRange("shotgun_damage", 10.0F, 0, Double.MAX_VALUE);
			specunmaykr_damage = builder.comment("Unmakyr Damage").translation(DoomMod.MODID + ".config.unmaykr_damage")
					.defineInRange("unmaykr_damage", 2.0F, 0, Double.MAX_VALUE);
			builder.pop();
			builder.push("extra");
			specENABLE_BLOCK_BREAKING = builder.comment("You can enable weapons breaking blocks.")
					.translation(DoomMod.MODID + ".config.enable_block_breaking")
					.define("ENABLE_BLOCK_BREAKING", false);
			builder.pop();
		}

		public HashMap<EntityConfigType, EntityConfig> entityConfig = new HashMap<EntityConfigType, EntityConfig>();

		public void pushDefaults(Builder builder, EntityConfigType type) {
			entityConfig.put(type,
					new EntityConfig(type.toString(),
							type.getDefaultAttributes().isHeavy() ? 5 : type == EntityConfigType.ICON_OF_SIN ? 0 : 15,
							1, type.getDefaultAttributes().getDefaultMaxRoll(), 0, 255, Arrays.asList("nether"))
									.setAttributes(type.getDefaultAttributes()).buildVia(builder));
		}

		public void bakeConfig() {
			CRUCIBLE_MARAUDER_MAX_DAMAGE = specCRUCIBLE_MARAUDER_MAX_DAMAGE.get();
			ENABLE_BLOCK_BREAKING = specENABLE_BLOCK_BREAKING.get();
			argent_bolt_damage = specargent_bolt_damage.get();
			bfgball_damage = specbfgball_damage.get();
			bfgball_damage_dragon = specbfgball_damage_dragon.get();
			bfgball_damage_aoe = specbfgball_damage_aoe.get();
			bullet_damage = specbullet_damage.get();
			chaingun_bullet_damage = specchaingun_bullet_damage.get();
			energycell_damage = specenergycell_damage.get();
			rocket_damage = specrocket_damage.get();
			shotgun_damage = specshotgun_damage.get();
			unmaykr_damage = specunmaykr_damage.get();
			entityConfig.values().forEach(config -> config.bake());
		}

	}

	public static void loadConfig(ForgeConfigSpec config, String path) {
		final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave()
				.writingMode(WritingMode.REPLACE).build();
		file.load();
		config.setConfig(file);
	}
}