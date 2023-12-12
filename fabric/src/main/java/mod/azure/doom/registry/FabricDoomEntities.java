package mod.azure.doom.registry;

import mod.azure.doom.MCDoom;
import mod.azure.doom.blocks.blockentities.BarrelEntity;
import mod.azure.doom.blocks.blockentities.GunBlockEntity;
import mod.azure.doom.blocks.blockentities.IconBlockEntity;
import mod.azure.doom.blocks.blockentities.TotemEntity;
import mod.azure.doom.entities.projectiles.*;
import mod.azure.doom.entities.projectiles.entity.*;
import mod.azure.doom.entities.tierambient.CueBallEntity;
import mod.azure.doom.entities.tierambient.GoreNestEntity;
import mod.azure.doom.entities.tierambient.TentacleEntity;
import mod.azure.doom.entities.tierambient.TurretEntity;
import mod.azure.doom.entities.tierboss.*;
import mod.azure.doom.entities.tierfodder.*;
import mod.azure.doom.entities.tierheavy.*;
import mod.azure.doom.entities.tiersuperheavy.*;
import mod.azure.doom.platform.Services;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.LinkedList;
import java.util.List;

public record FabricDoomEntities() {

    public static List<EntityType<? extends Entity>> ENTITY_TYPES = new LinkedList<>();
    public static List<EntityType<? extends Entity>> ENTITY_THAT_USE_ITEM_RENDERS = new LinkedList<>();
    public static BlockEntityType<TotemEntity> TOTEM;
    public static BlockEntityType<IconBlockEntity> ICON;
    public static BlockEntityType<GunBlockEntity> GUN_TABLE_ENTITY;
    public static EntityType<BarrelEntity> BARREL;

    /**
     * Projectiles
     */
    public static EntityType<DoomFireEntity> FIRING;
    public static EntityType<DroneBoltEntity> DRONEBOLT_MOB;
    public static EntityType<BloodBoltEntity> BLOODBOLT_MOB;
    public static EntityType<BFGEntity> BFG_CELL;
    public static EntityType<RocketEntity> ROCKET;
    public static EntityType<BarenBlastEntity> BARENBLAST;
    public static EntityType<BulletEntity> BULLETS;
    public static EntityType<RocketMobEntity> ROCKET_MOB;
    public static EntityType<GladiatorMaceEntity> GLADIATOR_MACE;
    public static EntityType<EnergyCellMobEntity> ENERGY_CELL_MOB;
    public static EntityType<GrenadeEntity> GRENADE;
    public static EntityType<ChaingunMobEntity> CHAINGUN_MOB;
    public static EntityType<FireProjectile> FIRE_MOB;
    public static EntityType<MeatHookEntity> MEATHOOOK_ENTITY;

    /**
     * Mobs
     */
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

    private static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id, float height, float width) {
        final var type = FabricEntityTypeBuilder.create(MobCategory.MISC, factory).dimensions(
                new EntityDimensions(height, width, true)).disableSummon().spawnableFarFromPlayer().trackRangeBlocks(
                90).trackedUpdateRate(1).build();
        Registry.register(BuiltInRegistries.ENTITY_TYPE, MCDoom.modResource(id), type);
        ENTITY_TYPES.add(type);
        ENTITY_THAT_USE_ITEM_RENDERS.add(type);

        return type;
    }

    private static <T extends Entity> EntityType<T> blockentity(String id, EntityType.EntityFactory<T> factory, float height, float width) {
        final var type = FabricEntityTypeBuilder.create(MobCategory.MISC, factory).dimensions(
                EntityDimensions.scalable(height, width)).fireImmune().trackedUpdateRate(1).trackRangeBlocks(
                90).build();
        Registry.register(BuiltInRegistries.ENTITY_TYPE, MCDoom.modResource(id), type);

        return type;
    }

    private static <T extends Entity> EntityType<T> mob(String id, EntityType.EntityFactory<T> factory, float height, float width) {
        final var type = FabricEntityTypeBuilder.create(MobCategory.MONSTER, factory).dimensions(
                EntityDimensions.scalable(height, width)).fireImmune().trackedUpdateRate(1).trackRangeBlocks(
                90).build();
        Registry.register(BuiltInRegistries.ENTITY_TYPE, MCDoom.modResource(id), type);

        return type;
    }

    public static void initialize() {
        ICON = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, MCDoom.modResource("icon"),
                FabricBlockEntityTypeBuilder.create(IconBlockEntity::new, Services.BLOCKS_HELPER.getWall1()).build(
                        null));
        TOTEM = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, MCDoom.modResource("totem"),
                FabricBlockEntityTypeBuilder.create(TotemEntity::new, Services.BLOCKS_HELPER.getTotem()).build(null));
        GUN_TABLE_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, MCDoom.modResource("guntable"),
                FabricBlockEntityTypeBuilder.create(GunBlockEntity::new, Services.BLOCKS_HELPER.getGunTable()).build(
                        null));
        BARREL = blockentity("barrel", BarrelEntity::new, 0.98F, 0.98F);
        FIRING = projectile(DoomFireEntity::new, "archvile_firing", 0.5F, 0.5F);
        DRONEBOLT_MOB = projectile(DroneBoltEntity::new, "dronebolt_mob", 0.5F, 0.5F);
        BLOODBOLT_MOB = projectile(BloodBoltEntity::new, "bloodbolt_mob", 0.5F, 0.5F);
        BFG_CELL = projectile(BFGEntity::new, "bfg_cell", 0.5F, 0.5F);
        ROCKET = projectile(RocketEntity::new, "rocket", 0.5F, 0.5F);
        BARENBLAST = projectile(BarenBlastEntity::new, "barenblast", 0.5F, 0.5F);
        BULLETS = projectile(BulletEntity::new, "bullets", 0.5F, 0.5F);
        ROCKET_MOB = projectile(RocketMobEntity::new, "rocket_mob", 0.5F, 0.5F);
        GLADIATOR_MACE = projectile(GladiatorMaceEntity::new, "gladiator_mace", 0.5F, 0.5F);
        ENERGY_CELL_MOB = projectile(EnergyCellMobEntity::new, "energy_cell_mob", 0.5F, 0.5F);
        GRENADE = projectile(GrenadeEntity::new, "doomed_grenade", 0.5F, 0.5F);
        CHAINGUN_MOB = projectile(ChaingunMobEntity::new, "chaingun_mob", 0.5F, 0.5F);
        FIRE_MOB = projectile(FireProjectile::new, "fire_projectile", 0.5F, 0.5F);
        MEATHOOOK_ENTITY = projectile(MeatHookEntity::new, "meathook", 0.5F, 0.5F);
        CARCASS = mob("carcass", CarcassEntity::new, 0.6f, 1.95F);
        IMP = mob("imp", ImpEntity::new, 0.6f, 1.95F);
        PINKY = mob("pinky", PinkyEntity::new, 1.7f, 2.2F);
        SPECTRE = mob("spectre", SpectreEntity::new, 1.7f, 2.2F);
        LOST_SOUL = mob("lost_soul", LostSoulEntity::new, 1.0F, 1.0F);
        LOST_SOUL_ETERNAL = mob("lost_soul_eternal", LostSoulEntity::new, 1.5F, 1.5F);
        CACODEMON = mob("cacodemon", CacodemonEntity::new, 0.5F, 0.5F);
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
        PAIN = mob("painelemental", PainEntity::new, 0.5F, 0.5F);
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
