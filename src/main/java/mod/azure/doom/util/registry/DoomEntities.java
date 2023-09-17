package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierambient.CueBallEntity;
import mod.azure.doom.entity.tierambient.GoreNestEntity;
import mod.azure.doom.entity.tierambient.TentacleEntity;
import mod.azure.doom.entity.tierambient.TurretEntity;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
import mod.azure.doom.entity.tierboss.GladiatorEntity;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import mod.azure.doom.entity.tierboss.SpiderMastermind2016Entity;
import mod.azure.doom.entity.tierboss.SpiderMastermindEntity;
import mod.azure.doom.entity.tierfodder.ChaingunnerEntity;
import mod.azure.doom.entity.tierfodder.GargoyleEntity;
import mod.azure.doom.entity.tierfodder.ImpEntity;
import mod.azure.doom.entity.tierfodder.ImpStoneEntity;
import mod.azure.doom.entity.tierfodder.LostSoulEntity;
import mod.azure.doom.entity.tierfodder.MaykrDroneEntity;
import mod.azure.doom.entity.tierfodder.MechaZombieEntity;
import mod.azure.doom.entity.tierfodder.PossessedScientistEntity;
import mod.azure.doom.entity.tierfodder.PossessedSoldierEntity;
import mod.azure.doom.entity.tierfodder.ShotgunguyEntity;
import mod.azure.doom.entity.tierfodder.UnwillingEntity;
import mod.azure.doom.entity.tierfodder.ZombiemanEntity;
import mod.azure.doom.entity.tierheavy.ArachnotronEntity;
import mod.azure.doom.entity.tierheavy.BloodMaykrEntity;
import mod.azure.doom.entity.tierheavy.CacodemonEntity;
import mod.azure.doom.entity.tierheavy.CarcassEntity;
import mod.azure.doom.entity.tierheavy.Hellknight2016Entity;
import mod.azure.doom.entity.tierheavy.HellknightEntity;
import mod.azure.doom.entity.tierheavy.MancubusEntity;
import mod.azure.doom.entity.tierheavy.PainEntity;
import mod.azure.doom.entity.tierheavy.PinkyEntity;
import mod.azure.doom.entity.tierheavy.ProwlerEntity;
import mod.azure.doom.entity.tierheavy.Revenant2016Entity;
import mod.azure.doom.entity.tierheavy.RevenantEntity;
import mod.azure.doom.entity.tierheavy.SpectreEntity;
import mod.azure.doom.entity.tierheavy.WhiplashEntity;
import mod.azure.doom.entity.tiersuperheavy.ArchvileEntity;
import mod.azure.doom.entity.tiersuperheavy.ArmoredBaronEntity;
import mod.azure.doom.entity.tiersuperheavy.BaronEntity;
import mod.azure.doom.entity.tiersuperheavy.CyberdemonEntity;
import mod.azure.doom.entity.tiersuperheavy.DoomHunterEntity;
import mod.azure.doom.entity.tiersuperheavy.FireBaronEntity;
import mod.azure.doom.entity.tiersuperheavy.MarauderEntity;
import mod.azure.doom.entity.tiersuperheavy.SummonerEntity;
import mod.azure.doom.entity.tileentity.BarrelEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class DoomEntities {

	public static EntityType<BarrelEntity> BARREL;

	public static EntityType<ImpEntity> IMP;
	public static EntityType<PinkyEntity> PINKY;
	public static EntityType<SpectreEntity> SPECTRE;
	public static EntityType<LostSoulEntity> LOST_SOUL;
	public static EntityType<LostSoulEntity> LOST_SOUL_ETERNAL;
	public static EntityType<CacodemonEntity> CACODEMON;
	public static EntityType<ArchvileEntity> ARCHVILE;
	public static EntityType<BaronEntity> BARON;
	public static EntityType<MancubusEntity> MANCUBUS;
	public static EntityType<SpiderMastermindEntity> SPIDERMASTERMIND;
	public static EntityType<ArachnotronEntity> ARACHNOTRON;
	public static EntityType<ZombiemanEntity> ZOMBIEMAN;
	public static EntityType<RevenantEntity> REVENANT;
	public static EntityType<ImpStoneEntity> IMP_STONE;
	public static EntityType<ChaingunnerEntity> CHAINGUNNER;
	public static EntityType<MarauderEntity> MARAUDER;
	public static EntityType<ShotgunguyEntity> SHOTGUNGUY;
	public static EntityType<PainEntity> PAIN;
	public static EntityType<HellknightEntity> HELLKNIGHT;
	public static EntityType<Hellknight2016Entity> HELLKNIGHT2016;
	public static EntityType<Hellknight2016Entity> DREADKNIGHT;
	public static EntityType<CyberdemonEntity> CYBERDEMON;
	public static EntityType<UnwillingEntity> UNWILLING;
	public static EntityType<PossessedSoldierEntity> POSSESSEDSOLDIER;
	public static EntityType<PossessedScientistEntity> POSSESSEDSCIENTIST;
	public static EntityType<PossessedScientistEntity> POSSESSEDWORKER;
	public static EntityType<MechaZombieEntity> MECHAZOMBIE;
	public static EntityType<CueBallEntity> CUEBALL;
	public static EntityType<GoreNestEntity> GORE_NEST;
	public static EntityType<GargoyleEntity> GARGOYLE;
	public static EntityType<ProwlerEntity> PROWLER;
	public static EntityType<IconofsinEntity> ICONOFSIN;
	public static EntityType<ArachnotronEntity> ARACHNOTRONETERNAL;
	public static EntityType<DoomHunterEntity> DOOMHUNTER;
	public static EntityType<WhiplashEntity> WHIPLASH;
	public static EntityType<BaronEntity> BARON2016;
	public static EntityType<FireBaronEntity> FIREBARON;
	public static EntityType<ArmoredBaronEntity> ARMORBARON;
	public static EntityType<SpiderMastermind2016Entity> SPIDERMASTERMIND2016;
	public static EntityType<MaykrDroneEntity> MAYKRDRONE;
	public static EntityType<TentacleEntity> TENTACLE;
	public static EntityType<TurretEntity> TURRET;
	public static EntityType<MotherDemonEntity> MOTHERDEMON;
	public static EntityType<BloodMaykrEntity> BLOODMAYKR;
	public static EntityType<ArchMakyrEntity> ARCHMAKER;
	public static EntityType<SummonerEntity> SUMMONER;
	public static EntityType<Revenant2016Entity> REVENANT2016;
	public static EntityType<GladiatorEntity> GLADIATOR;
	public static EntityType<CarcassEntity> CARCASS;

	private static <T extends Entity> EntityType<T> mob(String id, EntityType.EntityFactory<T> factory, float height, float width) {
		final var type = FabricEntityTypeBuilder.<T>create(MobCategory.MONSTER, factory).dimensions(EntityDimensions.scalable(height, width)).fireImmune().trackedUpdateRate(1).trackRangeBlocks(90).build();
		Registry.register(BuiltInRegistries.ENTITY_TYPE, DoomMod.modResource(id), type);

		return type;
	}

	private static <T extends Entity> EntityType<T> blockentity(String id, EntityType.EntityFactory<T> factory, float height, float width) {
		final var type = FabricEntityTypeBuilder.<T>create(MobCategory.MISC, factory).dimensions(EntityDimensions.scalable(height, width)).fireImmune().trackedUpdateRate(1).trackRangeBlocks(90).build();
		Registry.register(BuiltInRegistries.ENTITY_TYPE, DoomMod.modResource(id), type);

		return type;
	}

	public static void initialize() {
		CARCASS = mob("carcass", CarcassEntity::new, 0.6f, 1.95F);
		BARREL = blockentity("barrel", BarrelEntity::new, 0.98F, 0.98F);
		IMP = mob("imp", ImpEntity::new, 0.6f, 1.95F);
		PINKY = mob("pinky", PinkyEntity::new, 1.7f, 2.2F);
		SPECTRE = mob("spectre", SpectreEntity::new, 1.7f, 2.2F);
		LOST_SOUL = mob("lost_soul", LostSoulEntity::new, 1.0F, 1.0F);
		LOST_SOUL_ETERNAL = mob("lost_soul_eternal", LostSoulEntity::new, 1.5F, 1.5F);
		CACODEMON = mob("cacodemon", CacodemonEntity::new, 2.0F, 2.0F);
		ARCHVILE = mob("archvile", ArchvileEntity::new, 0.9F, 3.3F);
		BARON = mob("baron", BaronEntity::new, 1.3f, 3.9F);
		MANCUBUS = mob("mancubus", MancubusEntity::new, 2.3F, 3.0F);
		SPIDERMASTERMIND = mob("spidermastermind", SpiderMastermindEntity::new, 6.0F, 4.0F);
		ARACHNOTRON = mob("arachnotron", ArachnotronEntity::new, 4.0F, 2.0F);
		ZOMBIEMAN = mob("zombieman", ZombiemanEntity::new, 0.75f, 2.1F);
		REVENANT = mob("revenant", RevenantEntity::new, 1.9f, 3.95F);
		IMP_STONE = mob("stone_imp", ImpStoneEntity::new, 0.6f, 1.95F);
		CHAINGUNNER = mob("chaingunner", ChaingunnerEntity::new, 0.75f, 2.1F);
		MARAUDER = mob("marauder", MarauderEntity::new, 1.5f, 2.6F);
		SHOTGUNGUY = mob("shotgunguy", ShotgunguyEntity::new, 0.75f, 2.1F);
		PAIN = mob("painelemental", PainEntity::new, 2.0F, 2.0F);
		HELLKNIGHT = mob("hellknight", HellknightEntity::new, 1.4F, 3.5F);
		HELLKNIGHT2016 = mob("hellknight2016", Hellknight2016Entity::new, 1.8F, 3.0F);
		DREADKNIGHT = mob("dreadknight", Hellknight2016Entity::new, 1.8F, 3.0F);
		CYBERDEMON = mob("cyberdemon", CyberdemonEntity::new, 3.0f, 7.0F);
		UNWILLING = mob("unwilling", UnwillingEntity::new, 0.6f, 1.95F);
		POSSESSEDSOLDIER = mob("possessed_soldier", PossessedSoldierEntity::new, 0.9f, 2.35F);
		POSSESSEDSCIENTIST = mob("possessed_scientist", PossessedScientistEntity::new, 1.5f, 1.95F);
		POSSESSEDWORKER = mob("possessed_worker", PossessedScientistEntity::new, 1.5f, 1.95F);
		MECHAZOMBIE = mob("mechazombie", MechaZombieEntity::new, 1.2f, 2.3F);
		CUEBALL = mob("cueball", CueBallEntity::new, 1.1F, 2.1F);
		GORE_NEST = mob("gore_nest", GoreNestEntity::new, 3.0f, 4.0F);
		GARGOYLE = mob("gargoyle", GargoyleEntity::new, 3.0f, 4.0F);
		PROWLER = mob("prowler", ProwlerEntity::new, 3.0f, 4.0F);
		ICONOFSIN = mob("iconofsin", IconofsinEntity::new, 6.3f, 20.0F);
		ARACHNOTRONETERNAL = mob("arachnotroneternal", ArachnotronEntity::new, 4.0F, 3.0F);
		DOOMHUNTER = mob("doom_hunter", DoomHunterEntity::new, 3.0f, 7.0F);
		WHIPLASH = mob("whiplash", WhiplashEntity::new, 1.7f, 2.2F);
		BARON2016 = mob("baron2016", BaronEntity::new, 1.7f, 4.2F);
		FIREBARON = mob("firebronebaron", FireBaronEntity::new, 1.7f, 4.2F);
		ARMORBARON = mob("armoredbaron", ArmoredBaronEntity::new, 1.7f, 4.2F);
		SPIDERMASTERMIND2016 = mob("spidermastermind2016", SpiderMastermind2016Entity::new, 6.0F, 4.0F);
		MAYKRDRONE = mob("maykr_drone", MaykrDroneEntity::new, 1.2f, 2.7F);
		TENTACLE = mob("tentacle", TentacleEntity::new, 1.7f, 2.2F);
		TURRET = mob("turret", TurretEntity::new, 1.7f, 2.2F);
		MOTHERDEMON = mob("motherdemon", MotherDemonEntity::new, 6.3f, 10.0F);
		BLOODMAYKR = mob("blood_maykr", BloodMaykrEntity::new, 2.7f, 5.5F);
		ARCHMAKER = mob("arch_maykr", ArchMakyrEntity::new, 4.7f, 11.2F);
		SUMMONER = mob("summoner", SummonerEntity::new, 0.9F, 4.3F);
		REVENANT2016 = mob("revenant2016", Revenant2016Entity::new, 1.9f, 3.95F);
		GLADIATOR = mob("gladiator", GladiatorEntity::new, 1.7f, 4.2F);
	}
}