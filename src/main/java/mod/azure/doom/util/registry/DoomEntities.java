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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class DoomEntities {

	public static final EntityType<BarrelEntity> BARREL = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "barrel"),
			FabricEntityTypeBuilder.<BarrelEntity>create(MobCategory.MISC, BarrelEntity::new)
					.dimensions(EntityDimensions.fixed(0.98F, 0.98F)).trackRangeBlocks(90).trackedUpdateRate(1).forceTrackedVelocityUpdates(true)
					.build());

	public static final EntityType<ImpEntity> IMP = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "imp"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, ImpEntity::new)
					.dimensions(EntityDimensions.fixed(0.6f, 1.95F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<PinkyEntity> PINKY = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "pinky"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, PinkyEntity::new)
					.dimensions(EntityDimensions.fixed(1.7f, 2.2F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<SpectreEntity> SPECTRE = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "spectre"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, SpectreEntity::new)
					.dimensions(EntityDimensions.fixed(1.7f, 2.2F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<LostSoulEntity> LOST_SOUL = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "lost_soul"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, LostSoulEntity::new)
					.dimensions(EntityDimensions.fixed(1.0F, 1.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<LostSoulEntity> LOST_SOUL_ETERNAL = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "lost_soul_eternal"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, LostSoulEntity::new)
					.dimensions(EntityDimensions.fixed(1.5F, 1.5F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<CacodemonEntity> CACODEMON = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "cacodemon"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, CacodemonEntity::new)
					.dimensions(EntityDimensions.fixed(2.0F, 2.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<ArchvileEntity> ARCHVILE = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "archvile"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, ArchvileEntity::new)
					.dimensions(EntityDimensions.fixed(0.9F, 3.3F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<BaronEntity> BARON = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "baron"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, BaronEntity::new)
					.dimensions(EntityDimensions.fixed(1.3f, 3.9F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<MancubusEntity> MANCUBUS = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "mancubus"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, MancubusEntity::new)
					.dimensions(EntityDimensions.fixed(2.3F, 3.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<SpiderMastermindEntity> SPIDERMASTERMIND = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "spidermastermind"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, SpiderMastermindEntity::new)
					.dimensions(EntityDimensions.fixed(6.0F, 4.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<ArachnotronEntity> ARACHNOTRON = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "arachnotron"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, ArachnotronEntity::new)
					.dimensions(EntityDimensions.fixed(4.0F, 2.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<ZombiemanEntity> ZOMBIEMAN = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "zombieman"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, ZombiemanEntity::new)
					.dimensions(EntityDimensions.fixed(0.75f, 2.1F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<RevenantEntity> REVENANT = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "revenant"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, RevenantEntity::new)
					.dimensions(EntityDimensions.fixed(1.9f, 3.95F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<ImpStoneEntity> IMP_STONE = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "stone_imp"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, ImpStoneEntity::new)
					.dimensions(EntityDimensions.fixed(0.6f, 1.95F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());
	public static final EntityType<ChaingunnerEntity> CHAINGUNNER = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "chaingunner"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, ChaingunnerEntity::new)
					.dimensions(EntityDimensions.fixed(0.75f, 2.1F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<MarauderEntity> MARAUDER = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "marauder"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, MarauderEntity::new)
					.dimensions(EntityDimensions.fixed(1.5f, 2.6F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<ShotgunguyEntity> SHOTGUNGUY = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "shotgunguy"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, ShotgunguyEntity::new)
					.dimensions(EntityDimensions.fixed(0.75f, 2.1F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<PainEntity> PAIN = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "painelemental"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, PainEntity::new)
					.dimensions(EntityDimensions.fixed(2.0F, 2.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<HellknightEntity> HELLKNIGHT = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "hellknight"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, HellknightEntity::new)
					.dimensions(EntityDimensions.fixed(1.4F, 3.5F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<Hellknight2016Entity> HELLKNIGHT2016 = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "hellknight2016"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, Hellknight2016Entity::new)
					.dimensions(EntityDimensions.fixed(1.8F, 3.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<Hellknight2016Entity> DREADKNIGHT = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "dreadknight"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, Hellknight2016Entity::new)
					.dimensions(EntityDimensions.fixed(1.8F, 3.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<CyberdemonEntity> CYBERDEMON = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "cyberdemon"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, CyberdemonEntity::new)
					.dimensions(EntityDimensions.fixed(3.0f, 7.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<UnwillingEntity> UNWILLING = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "unwilling"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, UnwillingEntity::new)
					.dimensions(EntityDimensions.fixed(0.6f, 1.95F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<PossessedSoldierEntity> POSSESSEDSOLDIER = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "possessed_soldier"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, PossessedSoldierEntity::new)
					.dimensions(EntityDimensions.fixed(0.9f, 2.35F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<PossessedScientistEntity> POSSESSEDSCIENTIST = Registry.register(
			BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(DoomMod.MODID, "possessed_scientist"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, PossessedScientistEntity::new)
					.dimensions(EntityDimensions.fixed(1.5f, 1.95F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<PossessedScientistEntity> POSSESSEDWORKER = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "possessed_worker"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, PossessedScientistEntity::new)
					.dimensions(EntityDimensions.fixed(1.5f, 1.95F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<MechaZombieEntity> MECHAZOMBIE = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "mechazombie"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, MechaZombieEntity::new)
					.dimensions(EntityDimensions.fixed(1.2f, 2.3F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<CueBallEntity> CUEBALL = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "cueball"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, CueBallEntity::new)
					.dimensions(EntityDimensions.fixed(1.1F, 2.1F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<GoreNestEntity> GORE_NEST = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "gore_nest"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, GoreNestEntity::new)
					.dimensions(EntityDimensions.fixed(3.0f, 4.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<GargoyleEntity> GARGOYLE = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "gargoyle"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, GargoyleEntity::new)
					.dimensions(EntityDimensions.fixed(3.0f, 4.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<ProwlerEntity> PROWLER = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "prowler"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, ProwlerEntity::new)
					.dimensions(EntityDimensions.fixed(3.0f, 4.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<IconofsinEntity> ICONOFSIN = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "iconofsin"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, IconofsinEntity::new)
					.dimensions(EntityDimensions.fixed(6.3f, 20.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<ArachnotronEntity> ARACHNOTRONETERNAL = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "arachnotroneternal"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, ArachnotronEntity::new)
					.dimensions(EntityDimensions.fixed(4.0F, 3.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<DoomHunterEntity> DOOMHUNTER = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "doom_hunter"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, DoomHunterEntity::new)
					.dimensions(EntityDimensions.fixed(3.0f, 7.0F)).fireImmune().trackedUpdateRate(1)
					.trackRangeBlocks(90).build());

	public static final EntityType<WhiplashEntity> WHIPLASH = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "whiplash"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, WhiplashEntity::new)
					.dimensions(EntityDimensions.fixed(1.7f, 2.2F)).fireImmune().trackedUpdateRate(1)
					.trackRangeBlocks(90).build());

	public static final EntityType<BaronEntity> BARON2016 = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "baron2016"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, BaronEntity::new)
					.dimensions(EntityDimensions.fixed(1.7f, 4.2F)).fireImmune().trackedUpdateRate(1)
					.trackRangeBlocks(90).build());

	public static final EntityType<FireBaronEntity> FIREBARON = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "firebronebaron"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, FireBaronEntity::new)
					.dimensions(EntityDimensions.fixed(1.7f, 4.2F)).fireImmune().trackedUpdateRate(1)
					.trackRangeBlocks(90).build());

	public static final EntityType<ArmoredBaronEntity> ARMORBARON = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "armoredbaron"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, ArmoredBaronEntity::new)
					.dimensions(EntityDimensions.fixed(1.7f, 4.2F)).fireImmune().trackedUpdateRate(1)
					.trackRangeBlocks(90).build());

	public static final EntityType<SpiderMastermind2016Entity> SPIDERMASTERMIND2016 = Registry.register(
			BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(DoomMod.MODID, "spidermastermind2016"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, SpiderMastermind2016Entity::new)
					.dimensions(EntityDimensions.fixed(6.0F, 4.0F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<MaykrDroneEntity> MAYKRDRONE = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "maykr_drone"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, MaykrDroneEntity::new)
					.dimensions(EntityDimensions.fixed(1.2f, 2.7F)).fireImmune().trackedUpdateRate(1)
					.trackRangeBlocks(90).build());

	public static final EntityType<TentacleEntity> TENTACLE = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "tentacle"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, TentacleEntity::new)
					.dimensions(EntityDimensions.fixed(1.7f, 2.2F)).fireImmune().trackedUpdateRate(1)
					.trackRangeBlocks(90).build());

	public static final EntityType<TurretEntity> TURRET = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "turret"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, TurretEntity::new)
					.dimensions(EntityDimensions.fixed(1.7f, 2.2F)).fireImmune().trackedUpdateRate(1)
					.trackRangeBlocks(90).build());

	public static final EntityType<MotherDemonEntity> MOTHERDEMON = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "motherdemon"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, MotherDemonEntity::new)
					.dimensions(EntityDimensions.fixed(6.3f, 10.0F)).fireImmune().trackedUpdateRate(1)
					.trackRangeBlocks(90).build());

	public static final EntityType<BloodMaykrEntity> BLOODMAYKR = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "blood_maykr"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, BloodMaykrEntity::new)
					.dimensions(EntityDimensions.fixed(2.7f, 5.5F)).fireImmune().trackedUpdateRate(1)
					.trackRangeBlocks(90).build());

	public static final EntityType<ArchMakyrEntity> ARCHMAKER = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "arch_maykr"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, ArchMakyrEntity::new)
					.dimensions(EntityDimensions.fixed(4.7f, 11.2F)).fireImmune().trackedUpdateRate(1)
					.trackRangeBlocks(90).build());

	public static final EntityType<SummonerEntity> SUMMONER = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "summoner"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, SummonerEntity::new)
					.dimensions(EntityDimensions.fixed(0.9F, 4.3F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<Revenant2016Entity> REVENANT2016 = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "revenant2016"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, Revenant2016Entity::new)
					.dimensions(EntityDimensions.fixed(1.9f, 3.95F)).fireImmune().trackRangeBlocks(90)
					.trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build());

	public static final EntityType<GladiatorEntity> GLADIATOR = Registry.register(BuiltInRegistries.ENTITY_TYPE,
			new ResourceLocation(DoomMod.MODID, "gladiator"),
			FabricEntityTypeBuilder.create(MobCategory.MONSTER, GladiatorEntity::new)
					.dimensions(EntityDimensions.fixed(1.7f, 4.2F)).fireImmune().trackedUpdateRate(1)
					.trackRangeBlocks(90).build());
}