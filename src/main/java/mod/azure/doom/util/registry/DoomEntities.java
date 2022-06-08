package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.BarrelEntity;
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
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DoomEntities {

	public static final EntityType<BarrelEntity> BARREL = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "barrel"),
			FabricEntityTypeBuilder.<BarrelEntity>create(SpawnGroup.MISC, BarrelEntity::new)
					.dimensions(EntityDimensions.fixed(0.98F, 0.98F)).trackRangeBlocks(90).trackedUpdateRate(4)
					.build());

	public static final EntityType<ImpEntity> IMP = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "imp"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ImpEntity::new)
					.dimensions(EntityDimensions.fixed(0.6f, 1.95F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<PinkyEntity> PINKY = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "pinky"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, PinkyEntity::new)
					.dimensions(EntityDimensions.fixed(1.7f, 2.2F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<SpectreEntity> SPECTRE = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "spectre"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SpectreEntity::new)
					.dimensions(EntityDimensions.fixed(1.7f, 2.2F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<LostSoulEntity> LOST_SOUL = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "lost_soul"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, LostSoulEntity::new)
					.dimensions(EntityDimensions.fixed(1.0F, 1.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<LostSoulEntity> LOST_SOUL_ETERNAL = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "lost_soul_eternal"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, LostSoulEntity::new)
					.dimensions(EntityDimensions.fixed(1.5F, 1.5F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<CacodemonEntity> CACODEMON = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "cacodemon"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CacodemonEntity::new)
					.dimensions(EntityDimensions.fixed(2.0F, 2.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<ArchvileEntity> ARCHVILE = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "archvile"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ArchvileEntity::new)
					.dimensions(EntityDimensions.fixed(0.9F, 3.3F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<BaronEntity> BARON = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "baron"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BaronEntity::new)
					.dimensions(EntityDimensions.fixed(1.3f, 3.9F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<MancubusEntity> MANCUBUS = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "mancubus"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, MancubusEntity::new)
					.dimensions(EntityDimensions.fixed(2.3F, 3.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<SpiderMastermindEntity> SPIDERMASTERMIND = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "spidermastermind"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SpiderMastermindEntity::new)
					.dimensions(EntityDimensions.fixed(6.0F, 4.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<ArachnotronEntity> ARACHNOTRON = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "arachnotron"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ArachnotronEntity::new)
					.dimensions(EntityDimensions.fixed(4.0F, 2.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<ZombiemanEntity> ZOMBIEMAN = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "zombieman"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ZombiemanEntity::new)
					.dimensions(EntityDimensions.fixed(0.75f, 2.1F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<RevenantEntity> REVENANT = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "revenant"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, RevenantEntity::new)
					.dimensions(EntityDimensions.fixed(1.9f, 3.95F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<ImpStoneEntity> IMP_STONE = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "stone_imp"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ImpStoneEntity::new)
					.dimensions(EntityDimensions.fixed(0.6f, 1.95F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());
	public static final EntityType<ChaingunnerEntity> CHAINGUNNER = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "chaingunner"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChaingunnerEntity::new)
					.dimensions(EntityDimensions.fixed(0.75f, 2.1F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<MarauderEntity> MARAUDER = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "marauder"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, MarauderEntity::new)
					.dimensions(EntityDimensions.fixed(1.5f, 2.6F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<ShotgunguyEntity> SHOTGUNGUY = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "shotgunguy"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ShotgunguyEntity::new)
					.dimensions(EntityDimensions.fixed(0.75f, 2.1F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<PainEntity> PAIN = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "painelemental"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, PainEntity::new)
					.dimensions(EntityDimensions.fixed(2.0F, 2.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<HellknightEntity> HELLKNIGHT = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "hellknight"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, HellknightEntity::new)
					.dimensions(EntityDimensions.fixed(1.4F, 3.5F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<Hellknight2016Entity> HELLKNIGHT2016 = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "hellknight2016"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, Hellknight2016Entity::new)
					.dimensions(EntityDimensions.fixed(1.8F, 3.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<Hellknight2016Entity> DREADKNIGHT = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "dreadknight"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, Hellknight2016Entity::new)
					.dimensions(EntityDimensions.fixed(1.8F, 3.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<CyberdemonEntity> CYBERDEMON = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "cyberdemon"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CyberdemonEntity::new)
					.dimensions(EntityDimensions.fixed(3.0f, 7.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<UnwillingEntity> UNWILLING = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "unwilling"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, UnwillingEntity::new)
					.dimensions(EntityDimensions.fixed(0.6f, 1.95F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<PossessedSoldierEntity> POSSESSEDSOLDIER = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "possessed_soldier"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, PossessedSoldierEntity::new)
					.dimensions(EntityDimensions.fixed(0.9f, 2.35F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<PossessedScientistEntity> POSSESSEDSCIENTIST = Registry.register(
			Registry.ENTITY_TYPE, new Identifier(DoomMod.MODID, "possessed_scientist"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, PossessedScientistEntity::new)
					.dimensions(EntityDimensions.fixed(1.5f, 1.95F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<PossessedScientistEntity> POSSESSEDWORKER = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "possessed_worker"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, PossessedScientistEntity::new)
					.dimensions(EntityDimensions.fixed(1.5f, 1.95F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<MechaZombieEntity> MECHAZOMBIE = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "mechazombie"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, MechaZombieEntity::new)
					.dimensions(EntityDimensions.fixed(1.2f, 2.3F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<CueBallEntity> CUEBALL = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "cueball"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CueBallEntity::new)
					.dimensions(EntityDimensions.fixed(1.1F, 2.1F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<GoreNestEntity> GORE_NEST = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "gore_nest"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, GoreNestEntity::new)
					.dimensions(EntityDimensions.fixed(3.0f, 4.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<GargoyleEntity> GARGOYLE = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "gargoyle"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, GargoyleEntity::new)
					.dimensions(EntityDimensions.fixed(3.0f, 4.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<ProwlerEntity> PROWLER = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "prowler"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ProwlerEntity::new)
					.dimensions(EntityDimensions.fixed(3.0f, 4.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<IconofsinEntity> ICONOFSIN = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "iconofsin"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, IconofsinEntity::new)
					.dimensions(EntityDimensions.fixed(6.3f, 20.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<ArachnotronEntity> ARACHNOTRONETERNAL = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "arachnotroneternal"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ArachnotronEntity::new)
					.dimensions(EntityDimensions.fixed(4.0F, 3.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<DoomHunterEntity> DOOMHUNTER = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "doom_hunter"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, DoomHunterEntity::new)
					.dimensions(EntityDimensions.fixed(3.0f, 7.0F)).fireImmune().trackedUpdateRate(9)
					.trackRangeBlocks(90).build());

	public static final EntityType<WhiplashEntity> WHIPLASH = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "whiplash"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WhiplashEntity::new)
					.dimensions(EntityDimensions.fixed(1.7f, 2.2F)).fireImmune().trackedUpdateRate(9)
					.trackRangeBlocks(90).build());

	public static final EntityType<BaronEntity> BARON2016 = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "baron2016"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BaronEntity::new)
					.dimensions(EntityDimensions.fixed(1.7f, 4.2F)).fireImmune().trackedUpdateRate(9)
					.trackRangeBlocks(90).build());

	public static final EntityType<FireBaronEntity> FIREBARON = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "firebronebaron"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, FireBaronEntity::new)
					.dimensions(EntityDimensions.fixed(1.7f, 4.2F)).fireImmune().trackedUpdateRate(9)
					.trackRangeBlocks(90).build());

	public static final EntityType<ArmoredBaronEntity> ARMORBARON = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "armoredbaron"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ArmoredBaronEntity::new)
					.dimensions(EntityDimensions.fixed(1.7f, 4.2F)).fireImmune().trackedUpdateRate(9)
					.trackRangeBlocks(90).build());

	public static final EntityType<SpiderMastermind2016Entity> SPIDERMASTERMIND2016 = Registry.register(
			Registry.ENTITY_TYPE, new Identifier(DoomMod.MODID, "spidermastermind2016"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SpiderMastermind2016Entity::new)
					.dimensions(EntityDimensions.fixed(6.0F, 4.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<MaykrDroneEntity> MAYKRDRONE = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "maykr_drone"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, MaykrDroneEntity::new)
					.dimensions(EntityDimensions.fixed(1.2f, 2.7F)).fireImmune().trackedUpdateRate(9)
					.trackRangeBlocks(90).build());

	public static final EntityType<TentacleEntity> TENTACLE = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "tentacle"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TentacleEntity::new)
					.dimensions(EntityDimensions.fixed(1.7f, 2.2F)).fireImmune().trackedUpdateRate(9)
					.trackRangeBlocks(90).build());

	public static final EntityType<TurretEntity> TURRET = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "turret"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TurretEntity::new)
					.dimensions(EntityDimensions.fixed(1.7f, 2.2F)).fireImmune().trackedUpdateRate(9)
					.trackRangeBlocks(90).build());

	public static final EntityType<MotherDemonEntity> MOTHERDEMON = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "motherdemon"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, MotherDemonEntity::new)
					.dimensions(EntityDimensions.fixed(6.3f, 10.0F)).fireImmune().trackedUpdateRate(9)
					.trackRangeBlocks(90).build());

	public static final EntityType<BloodMaykrEntity> BLOODMAYKR = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "blood_maykr"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BloodMaykrEntity::new)
					.dimensions(EntityDimensions.fixed(2.7f, 5.5F)).fireImmune().trackedUpdateRate(9)
					.trackRangeBlocks(90).build());

	public static final EntityType<ArchMakyrEntity> ARCHMAKER = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "arch_maykr"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ArchMakyrEntity::new)
					.dimensions(EntityDimensions.fixed(4.7f, 11.2F)).fireImmune().trackedUpdateRate(9)
					.trackRangeBlocks(90).build());

	public static final EntityType<SummonerEntity> SUMMONER = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "summoner"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SummonerEntity::new)
					.dimensions(EntityDimensions.fixed(0.9F, 4.3F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<Revenant2016Entity> REVENANT2016 = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "revenant2016"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, Revenant2016Entity::new)
					.dimensions(EntityDimensions.fixed(1.9f, 3.95F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(4).build());

	public static final EntityType<GladiatorEntity> GLADIATOR = Registry.register(Registry.ENTITY_TYPE,
			new Identifier(DoomMod.MODID, "gladiator"),
			FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, GladiatorEntity::new)
					.dimensions(EntityDimensions.fixed(1.7f, 4.2F)).fireImmune().trackedUpdateRate(9)
					.trackRangeBlocks(90).build());
}