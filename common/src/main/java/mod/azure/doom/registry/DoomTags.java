package mod.azure.doom.registry;

import mod.azure.doom.MCDoom;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public record DoomTags() {
    public static final TagKey<Block> PAXEL_BLOCKS = TagKey.create(Registries.BLOCK,
            MCDoom.modResource("paxel_blocks"));

    public static final TagKey<Biome> BLOODMAYKR_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("bloodmaykr_biomes"));
    public static final TagKey<Biome> MAYKREDRONE_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("maykrdrone_biomes"));
    public static final TagKey<Biome> ARCHMAYKR_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("archmaykr_biomes"));

    public static final TagKey<Biome> ARCHNOTRON_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("arachnotron_biomes"));
    public static final TagKey<Biome> ARCHVILE_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("archvile_biomes"));
    public static final TagKey<Biome> ARMOREDBARON_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("armoredbaron_biomes"));
    public static final TagKey<Biome> BARON_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("baron_biomes"));
    public static final TagKey<Biome> CACODEMON_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("cacodemon_biomes"));
    public static final TagKey<Biome> CARCASS_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("carcass_biomes"));
    public static final TagKey<Biome> CHAINGUNNER_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("chaingunner_biomes"));
    public static final TagKey<Biome> CUEBALL_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("cueball_biomes"));
    public static final TagKey<Biome> CYBERDEMON_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("cyberdemon_biomes"));
    public static final TagKey<Biome> DOOMHUNTER_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("doomhunter_biomes"));
    public static final TagKey<Biome> GARGOYLE_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("gargoyle_biomes"));
    public static final TagKey<Biome> GLADIATOR_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("gladiator_biomes"));
    public static final TagKey<Biome> GORENEST_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("gorenest_biomes"));
    public static final TagKey<Biome> HELLKNIGHT2016_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("hellknight2016_biomes"));
    public static final TagKey<Biome> HELLKNIGHT_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("hellknight_biomes"));
    public static final TagKey<Biome> IMPSTONE_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("impstone_biomes"));
    public static final TagKey<Biome> IMP_BIOMES = TagKey.create(Registries.BIOME, MCDoom.modResource("imp_biomes"));
    public static final TagKey<Biome> LOST_SOUL_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("lost_soul_biomes"));
    public static final TagKey<Biome> MANCUBUS_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("mancubus_biomes"));
    public static final TagKey<Biome> MARAUDER_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("marauder_biomes"));
    public static final TagKey<Biome> MECHAZOMBIE_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("mechazombie_biomes"));
    public static final TagKey<Biome> MOTHERDEMON_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("motherdemon_biomes"));
    public static final TagKey<Biome> PAIN_BIOMES = TagKey.create(Registries.BIOME, MCDoom.modResource("pain_biomes"));
    public static final TagKey<Biome> PINKY_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("pinky_biomes"));
    public static final TagKey<Biome> POSSESSED_SCIENTIST_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("possessed_scientist_biomes"));
    public static final TagKey<Biome> POSSESSED_SOLDIER_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("possessed_soldier_biomes"));
    public static final TagKey<Biome> POSSESSED_WORKER_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("possessed_worker_biomes"));
    public static final TagKey<Biome> PROWLER_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("prowler_biomes"));
    public static final TagKey<Biome> REVENANT_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("revenant_biomes"));
    public static final TagKey<Biome> SHOTGUNGUY_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("shotgunguy_biomes"));
    public static final TagKey<Biome> SPECTRE_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("spectre_biomes"));
    public static final TagKey<Biome> SPIDER_MASTERMIND_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("spider_mastermind_biomes"));
    public static final TagKey<Biome> SUMMONER_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("summoner_biomes"));
    public static final TagKey<Biome> TENTACLE_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("tentacle_biomes"));
    public static final TagKey<Biome> TURRET_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("turret_biomes"));
    public static final TagKey<Biome> UNWILLING_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("unwilling_biomes"));
    public static final TagKey<Biome> WHIPLASH_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("whiplash_biomes"));
    public static final TagKey<Biome> ZOMBIEMAN_BIOMES = TagKey.create(Registries.BIOME,
            MCDoom.modResource("zombieman_biomes"));

}
