package mod.azure.doom.util.config;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

/*https://github.com/Alex-the-666/AlexsMobs/tree/40166a9119f9c75fce1dda8ba6138e2addd029f6/src/main/java/com/github/alexthe666/alexsmobs/config*/
public class BiomeConfig {
	public static List<? extends String> ARACHNOTRON = Lists.newArrayList(DefaultBiomes.ARACHNOTRON);
	public static List<? extends String> ARCHVILE = Lists.newArrayList(DefaultBiomes.ARCHVILE);
	public static List<? extends String> BARON = Lists.newArrayList(DefaultBiomes.BARON);
	public static List<? extends String> CACODEMON = Lists.newArrayList(DefaultBiomes.CACODEMON);
	public static List<? extends String> CHAINGUNNER = Lists.newArrayList(DefaultBiomes.CHAINGUNNER);
	public static List<? extends String> CYBER_DEMON_2016 = Lists.newArrayList(DefaultBiomes.CYBER_DEMON_2016);
	public static List<? extends String> CYBER_DEMON = Lists.newArrayList(DefaultBiomes.CYBER_DEMON);
	public static List<? extends String> GARGOYLE = Lists.newArrayList(DefaultBiomes.GARGOYLE);
	public static List<? extends String> GORE_NEST = Lists.newArrayList(DefaultBiomes.GORE_NEST);
	public static List<? extends String> HELL_KNIGHT_2016 = Lists.newArrayList(DefaultBiomes.HELL_KNIGHT_2016);
	public static List<? extends String> HELL_KNIGHT = Lists.newArrayList(DefaultBiomes.HELL_KNIGHT);
	public static List<? extends String> IMP_2016 = Lists.newArrayList(DefaultBiomes.IMP_2016);
	public static List<? extends String> IMP = Lists.newArrayList(DefaultBiomes.IMP);
	public static List<? extends String> LOST_SOUL = Lists.newArrayList(DefaultBiomes.LOST_SOUL);
	public static List<? extends String> MANCUBUS = Lists.newArrayList(DefaultBiomes.MANCUBUS);
	public static List<? extends String> MARAUDER = Lists.newArrayList(DefaultBiomes.MARAUDER);
	public static List<? extends String> MECHA_ZOMBIE = Lists.newArrayList(DefaultBiomes.MECHA_ZOMBIE);
	public static List<? extends String> NIGHTMARE_IMP = Lists.newArrayList(DefaultBiomes.NIGHTMARE_IMP);
	public static List<? extends String> PAIN = Lists.newArrayList(DefaultBiomes.PAIN);
	public static List<? extends String> PINKY = Lists.newArrayList(DefaultBiomes.PINKY);
	public static List<? extends String> SPECTRE = Lists.newArrayList(DefaultBiomes.SPECTRE);
	public static List<? extends String> POSSESSED_SCIENTIST = Lists.newArrayList(DefaultBiomes.POSSESSED_SCIENTIST);
	public static List<? extends String> POSSESSED_SOLDIER = Lists.newArrayList(DefaultBiomes.POSSESSED_SOLDIER);
	public static List<? extends String> REVENANT = Lists.newArrayList(DefaultBiomes.REVENANT);
	public static List<? extends String> SHOTGUN_GUY = Lists.newArrayList(DefaultBiomes.SHOTGUN_GUY);
	public static List<? extends String> SPIDERMASTERMIND = Lists.newArrayList(DefaultBiomes.SPIDER_DEMON);
	public static List<? extends String> UNWILLING = Lists.newArrayList(DefaultBiomes.UNWILLING);
	public static List<? extends String> ZOMBIEMAN = Lists.newArrayList(DefaultBiomes.ZOMBIEMAN);
	public static List<? extends String> CUEBALL = Lists.newArrayList(DefaultBiomes.CUEBALL);
	public static List<? extends String> PROWLER = Lists.newArrayList(DefaultBiomes.PROWLER);
	public static List<? extends String> IMP_STONE = Lists.newArrayList(DefaultBiomes.IMP_STONE);
	public static List<? extends String> TYRANT = Lists.newArrayList(DefaultBiomes.TYRANT);
	public static List<? extends String> POSSESSEDWORKER = Lists.newArrayList(DefaultBiomes.POSSESSEDWORKER);
	public static List<? extends String> WHIPLASH = Lists.newArrayList(DefaultBiomes.WHIPLASH);
	public static List<? extends String> DOOMHUNTER = Lists.newArrayList(DefaultBiomes.DOOMHUNTER);
	public static List<? extends String> MAYKRDRONE = Lists.newArrayList(DefaultBiomes.MAYKRDRONE);
	public static List<? extends String> BLOODMAYKR = Lists.newArrayList(DefaultBiomes.BLOODMAYKR);
	public static List<? extends String> KHANMAKYR = Lists.newArrayList(DefaultBiomes.KHANMAKYR);
	public static List<? extends String> TENTACLE = Lists.newArrayList(DefaultBiomes.TENTACLE);
	public static List<? extends String> SUMMONER = Lists.newArrayList(DefaultBiomes.SUMMONER);

	public static Map<String, ForgeConfigSpec.ConfigValue<List<? extends String>>> biomeConfigValues = new HashMap<>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BiomeConfig(final ForgeConfigSpec.Builder builder) {
		builder.comment("Mob Biome Config", "To filter biomes by registry name \"mod_id:biome_id\"",
				"To filter biomes by category \"@category\"", "To filter biomes by tags \"#tag\"", "\tExamples:",
				"\t\t\"minecraft:plains\"", "\t\t\"@desert\"", "\t\t\"#overworld\"", "",
				"If you want to exclude biomes put a ! before the biome identifier", "\tExamples:",
				"\t\t\"!minecraft:plains\"", "\t\t\"!@desert\"", "\t\t\"!#nether\"", "",
				"If you want to include biomes that would be satisfied by any in a set use |", "\tExamples:",
				"\t\t\"|minecraft:plains\"", "\t\t\"|@desert\"", "\t\t\"|#nether\"", "",
				"If you want a condition that MUST be satisfied use an & before the biome identifier",
				"Please note using this on a registry name wouldn't be that useful", "\tExamples:",
				"\t\t\"&minecraft:plains\"", "\t\t\"&@forest\"", "\t\t\"&#overworld\"", "",
				"NOTE: Any entry without a !, |, or & symbol has a higher precedence",
				"A list like [\"!minecraft:plains\", \"#overworld\"] would still see the plains as a viable biome", "",
				"Finally, you can create a expression that can be evaluated by itself using a + to combine identifiers",
				"\tExamples:", "\t\t\"!#hot+!#dry+!#mountain\"", "",
				"These expressions can be used to filter biomes in a lot of ways",
				"Lets say we don't want anything to spawn in any place dry and sandy", "\t\"!#dry+!#sandy\"", "",
				"But there is a hot place we want them to spawn that's also wet", "\t\"#hot+#wet\"", "",
				"We just put them as separate values in the list and that'll work out",
				"\t[\"!#dry+!#sandy\",\"#hot+#wet\"]", "",
				"NOTE: Any entry that's an expression will not be affected by anything else in the list")
				.push("mob_biome_configs");
		try {
			for (Field f : BiomeConfig.class.getDeclaredFields()) {
				Object obj = f.get(null);
				if (obj instanceof List) {
					biomeConfigValues.putIfAbsent(f.getName(),
							builder.defineList(f.getName(), (List) obj, o -> o instanceof String));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void bake(ModConfig config) {
		try {
			for (Field f : BiomeConfig.class.getDeclaredFields()) {
				Object obj = f.get(null);
				if (obj instanceof List) {
					ForgeConfigSpec.ConfigValue<List<? extends String>> configValue = BiomeConfig.biomeConfigValues
							.get(f.getName());
					if (config != null) {
						f.set(null, configValue.get());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
