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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public record NeoDoomEntities() {
    public static final DeferredRegister<BlockEntityType<?>> TILE_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MCDoom.MOD_ID);
    public static final RegistryObject<BlockEntityType<IconBlockEntity>> ICON = TILE_TYPES.register("icon", () -> BlockEntityType.Builder.<IconBlockEntity>of(IconBlockEntity::new, Services.BLOCKS_HELPER.getWall1()).build(null));
    public static final RegistryObject<BlockEntityType<TotemEntity>> TOTEM = TILE_TYPES.register("totem", () -> BlockEntityType.Builder.of(TotemEntity::new, Services.BLOCKS_HELPER.getTotem()).build(null));
    public static final RegistryObject<BlockEntityType<GunBlockEntity>> GUN_TABLE_ENTITY = TILE_TYPES.register("guntable", () -> BlockEntityType.Builder.of(GunBlockEntity::new, Services.BLOCKS_HELPER.getGunTable()).build(null));

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MCDoom.MOD_ID);
    public static final RegistryObject<EntityType<BarrelEntity>> BARREL = ENTITY_TYPES.register("barrel", () -> EntityType.Builder.<BarrelEntity>of(BarrelEntity::new, MobCategory.MISC).sized(0.98F, 0.98F).clientTrackingRange(10).build(MCDoom.modResource("barrel").toString()));
    /**
     * Projectiles
     */
    public static final RegistryObject<EntityType<DoomFireEntity>> FIRING = ENTITY_TYPES.register("archvile_firing", () -> EntityType.Builder.<DoomFireEntity>of(DoomFireEntity::new, MobCategory.MISC).sized(0.5F, 0.8F).clientTrackingRange(10).build(MCDoom.modResource("archvile_firing").toString()));
    public static final RegistryObject<EntityType<FireProjectile>> FIRE_MOB = ENTITY_TYPES.register("fire_projectile", () -> EntityType.Builder.<FireProjectile>of(FireProjectile::new, MobCategory.MISC).sized(0.5F, 0.8F).clientTrackingRange(10).build(MCDoom.modResource("fire_projectile").toString()));
    public static final RegistryObject<EntityType<ShotgunShellEntity>> SHOTGUN_SHELL = ENTITY_TYPES.register("shotgun_shell", () -> EntityType.Builder.<ShotgunShellEntity>of(ShotgunShellEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(9).build(MCDoom.modResource("shotgun_shell").toString()));
    public static final RegistryObject<EntityType<MeatHookEntity>> MEATHOOOK_ENTITY = ENTITY_TYPES.register("meathook", () -> EntityType.Builder.<MeatHookEntity>of(MeatHookEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(9).build(MCDoom.modResource("meathook").toString()));
    public static final RegistryObject<EntityType<DroneBoltEntity>> DRONEBOLT_MOB = ENTITY_TYPES.register("dronebolt_mob", () -> EntityType.Builder.<DroneBoltEntity>of(DroneBoltEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(9).build(MCDoom.modResource("dronebolt_mob").toString()));
    public static final RegistryObject<EntityType<BloodBoltEntity>> BLOODBOLT_MOB = ENTITY_TYPES.register("bloodbolt_mob", () -> EntityType.Builder.<BloodBoltEntity>of(BloodBoltEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(9).build(MCDoom.modResource("bloodbolt_mob").toString()));
    public static final RegistryObject<EntityType<ArgentBoltEntity>> ARGENT_BOLT = ENTITY_TYPES.register("argent_bolt", () -> EntityType.Builder.<ArgentBoltEntity>of(ArgentBoltEntity::new, MobCategory.MISC).clientTrackingRange(9).sized(0.5F, 0.5F).build(MCDoom.modResource("argent_bolt").toString()));
    public static final RegistryObject<EntityType<GrenadeEntity>> GRENADE = ENTITY_TYPES.register("doomed_grenade", () -> EntityType.Builder.<GrenadeEntity>of(GrenadeEntity::new, MobCategory.MISC).clientTrackingRange(9).sized(0.5F, 0.5F).build(MCDoom.modResource("doomed_grenade").toString()));
    public static final RegistryObject<EntityType<UnmaykrBoltEntity>> UNMAYKR = ENTITY_TYPES.register("unmaykr_bolt", () -> EntityType.Builder.<UnmaykrBoltEntity>of(UnmaykrBoltEntity::new, MobCategory.MISC).clientTrackingRange(9).sized(0.5F, 0.5F).build(MCDoom.modResource("unmaykr_bolt").toString()));
    public static final RegistryObject<EntityType<EnergyCellEntity>> ENERGY_CELL = ENTITY_TYPES.register("energy_cell", () -> EntityType.Builder.<EnergyCellEntity>of(EnergyCellEntity::new, MobCategory.MISC).clientTrackingRange(9).sized(0.5F, 0.5F).build(MCDoom.modResource("energy_cell").toString()));
    public static final RegistryObject<EntityType<BFGEntity>> BFG_CELL = ENTITY_TYPES.register("bfg_cell", () -> EntityType.Builder.<BFGEntity>of(BFGEntity::new, MobCategory.MISC).sized(2.0F, 2.0F).clientTrackingRange(9).build(MCDoom.modResource("bfg_cell").toString()));
    public static final RegistryObject<EntityType<RocketEntity>> ROCKET = ENTITY_TYPES.register("rocket", () -> EntityType.Builder.<RocketEntity>of(RocketEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(9).build(MCDoom.modResource("rocket").toString()));
    public static final RegistryObject<EntityType<BarenBlastEntity>> BARENBLAST = ENTITY_TYPES.register("barenblast", () -> EntityType.Builder.<BarenBlastEntity>of(BarenBlastEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(9).build(MCDoom.modResource("barenblast").toString()));
    public static final RegistryObject<EntityType<BulletEntity>> BULLETS = ENTITY_TYPES.register("bullets", () -> EntityType.Builder.<BulletEntity>of(BulletEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(9).build(MCDoom.modResource("bullets").toString()));
    public static final RegistryObject<EntityType<ChaingunBulletEntity>> CHAINGUN_BULLET = ENTITY_TYPES.register("chaingunbullets", () -> EntityType.Builder.<ChaingunBulletEntity>of(ChaingunBulletEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(9).build(MCDoom.modResource("chaingunbullets").toString()));
    public static final RegistryObject<EntityType<GladiatorMaceEntity>> GLADIATOR_MACE = ENTITY_TYPES.register("gladiator_mace", () -> EntityType.Builder.<GladiatorMaceEntity>of(GladiatorMaceEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(9).build(MCDoom.modResource("gladiator_mace").toString()));
    public static final RegistryObject<EntityType<EnergyCellMobEntity>> ENERGY_CELL_MOB = ENTITY_TYPES.register("energy_cell_mob", () -> EntityType.Builder.<EnergyCellMobEntity>of(EnergyCellMobEntity::new, MobCategory.MISC).clientTrackingRange(9).sized(0.5F, 0.5F).build(MCDoom.modResource("energy_cell_mob").toString()));
    public static final RegistryObject<EntityType<RocketMobEntity>> ROCKET_MOB = ENTITY_TYPES.register("rocket_mob", () -> EntityType.Builder.<RocketMobEntity>of(RocketMobEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(9).build(MCDoom.modResource("rocket_mob").toString()));
    public static final RegistryObject<EntityType<ChaingunMobEntity>> CHAINGUN_MOB = ENTITY_TYPES.register("chaingun_mob", () -> EntityType.Builder.<ChaingunMobEntity>of(ChaingunMobEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(9).build(MCDoom.modResource("chaingun_mob").toString()));
    /**
     * Mobs
     */
    public static final RegistryObject<EntityType<LostSoulEntity>> LOST_SOUL = ENTITY_TYPES.register("lost_soul", () -> EntityType.Builder.<LostSoulEntity>of(LostSoulEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "lost_soul").toString()));

    public static final RegistryObject<EntityType<ImpEntity>> IMP = ENTITY_TYPES.register("imp", () -> EntityType.Builder.of(ImpEntity::new, MobCategory.MONSTER).sized(0.6f, 1.95F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "imp").toString()));

    public static final RegistryObject<EntityType<PinkyEntity>> PINKY = ENTITY_TYPES.register("pinky", () -> EntityType.Builder.of(PinkyEntity::new, MobCategory.MONSTER).sized(1.7f, 2.2F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "pinky").toString()));

    public static final RegistryObject<EntityType<SpectreEntity>> SPECTRE = ENTITY_TYPES.register("spectre", () -> EntityType.Builder.of(SpectreEntity::new, MobCategory.MONSTER).sized(1.7f, 2.2F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "spectre").toString()));

    public static final RegistryObject<EntityType<CacodemonEntity>> CACODEMON = ENTITY_TYPES.register("cacodemon", () -> EntityType.Builder.of(CacodemonEntity::new, MobCategory.MONSTER).sized(2.0F, 2.0F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "cacodemon").toString()));

    public static final RegistryObject<EntityType<ArchvileEntity>> ARCHVILE = ENTITY_TYPES.register("archvile", () -> EntityType.Builder.of(ArchvileEntity::new, MobCategory.MONSTER).sized(0.9F, 3.3F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "archvile").toString()));

    public static final RegistryObject<EntityType<BaronEntity>> BARON = ENTITY_TYPES.register("baron", () -> EntityType.Builder.of(BaronEntity::new, MobCategory.MONSTER).clientTrackingRange(9).sized(1.3f, 3.9F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "baron").toString()));

    public static final RegistryObject<EntityType<MancubusEntity>> MANCUBUS = ENTITY_TYPES.register("mancubus", () -> EntityType.Builder.of(MancubusEntity::new, MobCategory.MONSTER).clientTrackingRange(9).sized(2.3F, 3.0F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "mancubus").toString()));

    public static final RegistryObject<EntityType<SpiderMastermindEntity>> SPIDERMASTERMIND = ENTITY_TYPES.register("spidermastermind", () -> EntityType.Builder.of(SpiderMastermindEntity::new, MobCategory.MONSTER).sized(6.0F, 4.0F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "spidermastermind").toString()));

    public static final RegistryObject<EntityType<ArachnotronEntity>> ARACHNOTRON = ENTITY_TYPES.register("arachnotron", () -> EntityType.Builder.of(ArachnotronEntity::new, MobCategory.MONSTER).sized(4.0F, 2.0F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "arachnotron").toString()));

    public static final RegistryObject<EntityType<ZombiemanEntity>> ZOMBIEMAN = ENTITY_TYPES.register("zombieman", () -> EntityType.Builder.of(ZombiemanEntity::new, MobCategory.MONSTER).sized(0.75f, 2.1F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "zombieman").toString()));

    public static final RegistryObject<EntityType<RevenantEntity>> REVENANT = ENTITY_TYPES.register("revenant", () -> EntityType.Builder.of(RevenantEntity::new, MobCategory.MONSTER).sized(1.9f, 3.95F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "revenant").toString()));

    public static final RegistryObject<EntityType<ImpStoneEntity>> IMP_STONE = ENTITY_TYPES.register("stone_imp", () -> EntityType.Builder.of(ImpStoneEntity::new, MobCategory.MONSTER).sized(0.6f, 1.95F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "stone_imp").toString()));

    public static final RegistryObject<EntityType<ChaingunnerEntity>> CHAINGUNNER = ENTITY_TYPES.register("chaingunner", () -> EntityType.Builder.of(ChaingunnerEntity::new, MobCategory.MONSTER).sized(0.75f, 2.1F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "chaingunner").toString()));

    public static final RegistryObject<EntityType<MarauderEntity>> MARAUDER = ENTITY_TYPES.register("marauder", () -> EntityType.Builder.of(MarauderEntity::new, MobCategory.MONSTER).sized(1.5f, 2.6F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "marauder").toString()));

    public static final RegistryObject<EntityType<ShotgunguyEntity>> SHOTGUNGUY = ENTITY_TYPES.register("shotgunguy", () -> EntityType.Builder.of(ShotgunguyEntity::new, MobCategory.MONSTER).sized(0.75f, 2.1F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "shotgunguy").toString()));

    public static final RegistryObject<EntityType<PainEntity>> PAIN = ENTITY_TYPES.register("painelemental", () -> EntityType.Builder.of(PainEntity::new, MobCategory.MONSTER).sized(2.0F, 2.0F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "painelemental").toString()));

    public static final RegistryObject<EntityType<HellknightEntity>> HELLKNIGHT = ENTITY_TYPES.register("hellknight", () -> EntityType.Builder.of(HellknightEntity::new, MobCategory.MONSTER).sized(1.4F, 3.5F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "hellknight").toString()));

    public static final RegistryObject<EntityType<Hellknight2016Entity>> HELLKNIGHT2016 = ENTITY_TYPES.register("hellknight2016", () -> EntityType.Builder.<Hellknight2016Entity>of(Hellknight2016Entity::new, MobCategory.MONSTER).sized(1.8F, 3.0F).clientTrackingRange(9).fireImmune().build(new ResourceLocation(MCDoom.MOD_ID, "hellknight2016").toString()));

    public static final RegistryObject<EntityType<Hellknight2016Entity>> DREADKNIGHT = ENTITY_TYPES.register("dreadknight", () -> EntityType.Builder.<Hellknight2016Entity>of(Hellknight2016Entity::new, MobCategory.MONSTER).sized(1.8F, 3.0F).clientTrackingRange(9).fireImmune().build(new ResourceLocation(MCDoom.MOD_ID, "dreadknight").toString()));

    public static final RegistryObject<EntityType<CyberdemonEntity>> CYBERDEMON = ENTITY_TYPES.register("cyberdemon", () -> EntityType.Builder.of(CyberdemonEntity::new, MobCategory.MONSTER).sized(3.0f, 7.0F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "cyberdemon").toString()));

    public static final RegistryObject<EntityType<UnwillingEntity>> UNWILLING = ENTITY_TYPES.register("unwilling", () -> EntityType.Builder.of(UnwillingEntity::new, MobCategory.MONSTER).sized(0.6f, 1.95F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "unwilling").toString()));

    public static final RegistryObject<EntityType<PossessedSoldierEntity>> POSSESSEDSOLDIER = ENTITY_TYPES.register("possessed_soldier", () -> EntityType.Builder.of(PossessedSoldierEntity::new, MobCategory.MONSTER).sized(0.9f, 2.35F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "possessed_soldier").toString()));

    public static final RegistryObject<EntityType<GoreNestEntity>> GORE_NEST = ENTITY_TYPES.register("gore_nest", () -> EntityType.Builder.of(GoreNestEntity::new, MobCategory.MONSTER).sized(3.0f, 4.0F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "gore_nest").toString()));

    public static final RegistryObject<EntityType<PossessedScientistEntity>> POSSESSEDSCIENTIST = ENTITY_TYPES.register("possessed_scientist", () -> EntityType.Builder.of(PossessedScientistEntity::new, MobCategory.MONSTER).sized(1.5f, 1.95F).clientTrackingRange(9).fireImmune().build(new ResourceLocation(MCDoom.MOD_ID, "possessed_scientist").toString()));

    public static final RegistryObject<EntityType<PossessedScientistEntity>> POSSESSEDWORKER = ENTITY_TYPES.register("possessed_worker", () -> EntityType.Builder.of(PossessedScientistEntity::new, MobCategory.MONSTER).sized(1.5f, 1.95F).clientTrackingRange(9).fireImmune().build(new ResourceLocation(MCDoom.MOD_ID, "possessed_worker").toString()));

    public static final RegistryObject<EntityType<CueBallEntity>> CUEBALL = ENTITY_TYPES.register("cueball", () -> EntityType.Builder.of(CueBallEntity::new, MobCategory.MONSTER).sized(1.1F, 2.1F).clientTrackingRange(9).fireImmune().build(new ResourceLocation(MCDoom.MOD_ID, "cueball").toString()));

    public static final RegistryObject<EntityType<MechaZombieEntity>> MECHAZOMBIE = ENTITY_TYPES.register("mechazombie", () -> EntityType.Builder.of(MechaZombieEntity::new, MobCategory.MONSTER).sized(1.2f, 2.3F).clientTrackingRange(9).fireImmune().build(new ResourceLocation(MCDoom.MOD_ID, "mechazombie").toString()));

    public static final RegistryObject<EntityType<ProwlerEntity>> PROWLER = ENTITY_TYPES.register("prowler", () -> EntityType.Builder.of(ProwlerEntity::new, MobCategory.MONSTER).sized(1.2f, 2.3F).clientTrackingRange(9).fireImmune().build(new ResourceLocation(MCDoom.MOD_ID, "prowler").toString()));

    public static final RegistryObject<EntityType<IconofsinEntity>> ICONOFSIN = ENTITY_TYPES.register("iconofsin", () -> EntityType.Builder.of(IconofsinEntity::new, MobCategory.MONSTER).sized(6.3f, 20.0F).clientTrackingRange(9).fireImmune().build(new ResourceLocation(MCDoom.MOD_ID, "iconofsin").toString()));

    public static final RegistryObject<EntityType<GargoyleEntity>> GARGOYLE = ENTITY_TYPES.register("gargoyle", () -> EntityType.Builder.of(GargoyleEntity::new, MobCategory.MONSTER).sized(1.3f, 2.25F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "gargoyle").toString()));

    public static final RegistryObject<EntityType<ArachnotronEntity>> ARACHNOTRONETERNAL = ENTITY_TYPES.register("arachnotroneternal", () -> EntityType.Builder.of(ArachnotronEntity::new, MobCategory.MONSTER).sized(4.0F, 3.0F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "arachnotroneternal").toString()));

    public static final RegistryObject<EntityType<DoomHunterEntity>> DOOMHUNTER = ENTITY_TYPES.register("doom_hunter", () -> EntityType.Builder.of(DoomHunterEntity::new, MobCategory.MONSTER).sized(3.0f, 7.0F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "doom_hunter").toString()));

    public static final RegistryObject<EntityType<WhiplashEntity>> WHIPLASH = ENTITY_TYPES.register("whiplash", () -> EntityType.Builder.of(WhiplashEntity::new, MobCategory.MONSTER).sized(1.7f, 2.2F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "whiplash").toString()));

    public static final RegistryObject<EntityType<BaronEntity>> BARON2016 = ENTITY_TYPES.register("baron2016", () -> EntityType.Builder.of(BaronEntity::new, MobCategory.MONSTER).sized(1.7f, 4.2F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "baron2016").toString()));

    public static final RegistryObject<EntityType<FireBaronEntity>> FIREBARON = ENTITY_TYPES.register("firebronebaron", () -> EntityType.Builder.of(FireBaronEntity::new, MobCategory.MONSTER).sized(1.7f, 4.2F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "firebronebaron").toString()));

    public static final RegistryObject<EntityType<ArmoredBaronEntity>> ARMORBARON = ENTITY_TYPES.register("armoredbaron", () -> EntityType.Builder.of(ArmoredBaronEntity::new, MobCategory.MONSTER).sized(1.7f, 4.2F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "armoredbaron").toString()));

    public static final RegistryObject<EntityType<MaykrDroneEntity>> MAYKRDRONE = ENTITY_TYPES.register("maykr_drone", () -> EntityType.Builder.of(MaykrDroneEntity::new, MobCategory.MONSTER).sized(1.2f, 2.7F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "maykr_drone").toString()));

    public static final RegistryObject<EntityType<SpiderMastermind2016Entity>> SPIDERMASTERMIND2016 = ENTITY_TYPES.register("spidermastermind2016", () -> EntityType.Builder.of(SpiderMastermind2016Entity::new, MobCategory.MONSTER).sized(6.0F, 4.0F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "spidermastermind2016").toString()));

    public static final RegistryObject<EntityType<BloodMaykrEntity>> BLOODMAYKR = ENTITY_TYPES.register("blood_maykr", () -> EntityType.Builder.of(BloodMaykrEntity::new, MobCategory.MONSTER).sized(2.7f, 5.5F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "blood_maykr").toString()));

    public static final RegistryObject<EntityType<MotherDemonEntity>> MOTHERDEMON = ENTITY_TYPES.register("motherdemon", () -> EntityType.Builder.of(MotherDemonEntity::new, MobCategory.MONSTER).sized(6.3f, 10.0F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "motherdemon").toString()));

    public static final RegistryObject<EntityType<TentacleEntity>> TENTACLE = ENTITY_TYPES.register("tentacle", () -> EntityType.Builder.of(TentacleEntity::new, MobCategory.MONSTER).sized(1.7f, 2.2F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "tentacle").toString()));

    public static final RegistryObject<EntityType<TurretEntity>> TURRET = ENTITY_TYPES.register("turret", () -> EntityType.Builder.of(TurretEntity::new, MobCategory.MONSTER).sized(1.7f, 2.2F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "turret").toString()));

    public static final RegistryObject<EntityType<ArchMakyrEntity>> ARCHMAKER = ENTITY_TYPES.register("arch_maykr", () -> EntityType.Builder.of(ArchMakyrEntity::new, MobCategory.MONSTER).sized(4.7f, 11.2F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "arch_maykr").toString()));

    public static final RegistryObject<EntityType<SummonerEntity>> SUMMONER = ENTITY_TYPES.register("summoner", () -> EntityType.Builder.of(SummonerEntity::new, MobCategory.MONSTER).sized(0.9F, 4.3F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "summoner").toString()));

    public static final RegistryObject<EntityType<Revenant2016Entity>> REVENANT2016 = ENTITY_TYPES.register("revenant2016", () -> EntityType.Builder.of(Revenant2016Entity::new, MobCategory.MONSTER).sized(1.9f, 3.95F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "revenant2016").toString()));

    public static final RegistryObject<EntityType<GladiatorEntity>> GLADIATOR = ENTITY_TYPES.register("gladiator", () -> EntityType.Builder.of(GladiatorEntity::new, MobCategory.MONSTER).sized(1.7f, 4.2F).fireImmune().clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "gladiator").toString()));

    public static final RegistryObject<EntityType<LostSoulEntity>> LOST_SOUL_ETERNAL = ENTITY_TYPES.register("lost_soul_eternal", () -> EntityType.Builder.<LostSoulEntity>of(LostSoulEntity::new, MobCategory.MISC).sized(1.50F, 1.50F).clientTrackingRange(9).build(new ResourceLocation(MCDoom.MOD_ID, "lost_soul_eternal").toString()));

    public static final RegistryObject<EntityType<CarcassEntity>> CARCASS = ENTITY_TYPES.register("carcass", () -> EntityType.Builder.of(CarcassEntity::new, MobCategory.MONSTER).sized(0.6f, 1.95F).clientTrackingRange(9).fireImmune().build(new ResourceLocation(MCDoom.MOD_ID, "carcass").toString()));

}
