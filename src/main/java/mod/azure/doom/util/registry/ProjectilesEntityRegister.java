package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.ArgentBoltEntity;
import mod.azure.doom.entity.projectiles.BFGEntity;
import mod.azure.doom.entity.projectiles.BulletEntity;
import mod.azure.doom.entity.projectiles.ChaingunBulletEntity;
import mod.azure.doom.entity.projectiles.EnergyCellEntity;
import mod.azure.doom.entity.projectiles.GrenadeEntity;
import mod.azure.doom.entity.projectiles.MeatHookEntity;
import mod.azure.doom.entity.projectiles.RocketEntity;
import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import mod.azure.doom.entity.projectiles.UnmaykrBoltEntity;
import mod.azure.doom.entity.projectiles.entity.BarenBlastEntity;
import mod.azure.doom.entity.projectiles.entity.BloodBoltEntity;
import mod.azure.doom.entity.projectiles.entity.ChaingunMobEntity;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.entity.projectiles.entity.DroneBoltEntity;
import mod.azure.doom.entity.projectiles.entity.EnergyCellMobEntity;
import mod.azure.doom.entity.projectiles.entity.FireProjectile;
import mod.azure.doom.entity.projectiles.entity.GladiatorMaceEntity;
import mod.azure.doom.entity.projectiles.entity.RocketMobEntity;
import mod.azure.doom.entity.tileentity.BarrelEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ProjectilesEntityRegister {

	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
			.create(ForgeRegistries.ENTITY_TYPES, DoomMod.MODID);

	public static final RegistryObject<EntityType<DoomFireEntity>> FIRING = ENTITY_TYPES.register("archvile_firing",
			() -> EntityType.Builder.<DoomFireEntity>of(DoomFireEntity::new, MobCategory.MISC).sized(0.5F, 0.8F)
					.clientTrackingRange(10).build(new ResourceLocation(DoomMod.MODID, "archvile_firing").toString()));

	public static final RegistryObject<EntityType<FireProjectile>> FIRE_MOB = ENTITY_TYPES.register("fire_projectile",
			() -> EntityType.Builder.<FireProjectile>of(FireProjectile::new, MobCategory.MISC).sized(0.5F, 0.8F)
					.clientTrackingRange(10).build(new ResourceLocation(DoomMod.MODID, "fire_projectile").toString()));

	public static final RegistryObject<EntityType<BarrelEntity>> BARREL = ENTITY_TYPES.register("barrel",
			() -> EntityType.Builder.<BarrelEntity>of(BarrelEntity::new, MobCategory.MISC).sized(0.98F, 0.98F)
					.clientTrackingRange(10).build(new ResourceLocation(DoomMod.MODID, "barrel").toString()));

	public static final RegistryObject<EntityType<ShotgunShellEntity>> SHOTGUN_SHELL = ENTITY_TYPES
			.register("shotgun_shell",
					() -> EntityType.Builder.<ShotgunShellEntity>of(ShotgunShellEntity::new, MobCategory.MISC)
							.sized(0.5F, 0.5F).clientTrackingRange(9)
							.build(new ResourceLocation(DoomMod.MODID, "shotgun_shell").toString()));

	public static final RegistryObject<EntityType<MeatHookEntity>> MEATHOOOK_ENTITY = ENTITY_TYPES.register("meathook",
			() -> EntityType.Builder.<MeatHookEntity>of(MeatHookEntity::new, MobCategory.MISC).sized(0.5F, 0.5F)
					.clientTrackingRange(9).build(new ResourceLocation(DoomMod.MODID, "meathook").toString()));

	public static final RegistryObject<EntityType<DroneBoltEntity>> DRONEBOLT_MOB = ENTITY_TYPES
			.register("dronebolt_mob",
					() -> EntityType.Builder.<DroneBoltEntity>of(DroneBoltEntity::new, MobCategory.MISC)
							.sized(0.5F, 0.5F).clientTrackingRange(9)
							.build(new ResourceLocation(DoomMod.MODID, "dronebolt_mob").toString()));

	public static final RegistryObject<EntityType<BloodBoltEntity>> BLOODBOLT_MOB = ENTITY_TYPES
			.register("bloodbolt_mob",
					() -> EntityType.Builder.<BloodBoltEntity>of(BloodBoltEntity::new, MobCategory.MISC)
							.sized(0.5F, 0.5F).clientTrackingRange(9)
							.build(new ResourceLocation(DoomMod.MODID, "bloodbolt_mob").toString()));

	public static final RegistryObject<EntityType<ArgentBoltEntity>> ARGENT_BOLT = ENTITY_TYPES.register("argent_bolt",
			() -> EntityType.Builder.<ArgentBoltEntity>of(ArgentBoltEntity::new, MobCategory.MISC)
					.clientTrackingRange(9).sized(0.5F, 0.5F)
					.build(new ResourceLocation(DoomMod.MODID, "argent_bolt").toString()));

	public static final RegistryObject<EntityType<GrenadeEntity>> GRENADE = ENTITY_TYPES.register("doomed_grenade",
			() -> EntityType.Builder.<GrenadeEntity>of(GrenadeEntity::new, MobCategory.MISC).clientTrackingRange(9)
					.sized(0.5F, 0.5F).build(new ResourceLocation(DoomMod.MODID, "doomed_grenade").toString()));

	public static final RegistryObject<EntityType<UnmaykrBoltEntity>> UNMAYKR = ENTITY_TYPES.register("unmaykr_bolt",
			() -> EntityType.Builder.<UnmaykrBoltEntity>of(UnmaykrBoltEntity::new, MobCategory.MISC)
					.clientTrackingRange(9).sized(0.5F, 0.5F)
					.build(new ResourceLocation(DoomMod.MODID, "unmaykr_bolt").toString()));

	public static final RegistryObject<EntityType<EnergyCellEntity>> ENERGY_CELL = ENTITY_TYPES.register("energy_cell",
			() -> EntityType.Builder.<EnergyCellEntity>of(EnergyCellEntity::new, MobCategory.MISC)
					.clientTrackingRange(9).sized(0.5F, 0.5F)
					.build(new ResourceLocation(DoomMod.MODID, "energy_cell").toString()));

	public static final RegistryObject<EntityType<BFGEntity>> BFG_CELL = ENTITY_TYPES.register("bfg_cell",
			() -> EntityType.Builder.<BFGEntity>of(BFGEntity::new, MobCategory.MISC).sized(2.0F, 2.0F)
					.clientTrackingRange(9).build(new ResourceLocation(DoomMod.MODID, "bfg_cell").toString()));

	public static final RegistryObject<EntityType<RocketEntity>> ROCKET = ENTITY_TYPES.register("rocket",
			() -> EntityType.Builder.<RocketEntity>of(RocketEntity::new, MobCategory.MISC).sized(0.5F, 0.5F)
					.clientTrackingRange(9).build(new ResourceLocation(DoomMod.MODID, "rocket").toString()));

	public static final RegistryObject<EntityType<BarenBlastEntity>> BARENBLAST = ENTITY_TYPES.register("barenblast",
			() -> EntityType.Builder.<BarenBlastEntity>of(BarenBlastEntity::new, MobCategory.MISC).sized(1.0F, 1.0F)
					.clientTrackingRange(9).build(new ResourceLocation(DoomMod.MODID, "barenblast").toString()));

	public static final RegistryObject<EntityType<BulletEntity>> BULLETS = ENTITY_TYPES.register("bullets",
			() -> EntityType.Builder.<BulletEntity>of(BulletEntity::new, MobCategory.MISC).sized(0.5F, 0.5F)
					.clientTrackingRange(9).build(new ResourceLocation(DoomMod.MODID, "bullets").toString()));

	public static final RegistryObject<EntityType<ChaingunBulletEntity>> CHAINGUN_BULLET = ENTITY_TYPES.register(
			"chaingunbullets",
			() -> EntityType.Builder.<ChaingunBulletEntity>of(ChaingunBulletEntity::new, MobCategory.MISC)
					.sized(0.5F, 0.5F).clientTrackingRange(9)
					.build(new ResourceLocation(DoomMod.MODID, "chaingunbullets").toString()));

	public static final RegistryObject<EntityType<GladiatorMaceEntity>> GLADIATOR_MACE = ENTITY_TYPES.register(
			"gladiator_mace",
			() -> EntityType.Builder.<GladiatorMaceEntity>of(GladiatorMaceEntity::new, MobCategory.MISC)
					.sized(0.5F, 0.5F).clientTrackingRange(9)
					.build(new ResourceLocation(DoomMod.MODID, "gladiator_mace").toString()));

	public static final RegistryObject<EntityType<EnergyCellMobEntity>> ENERGY_CELL_MOB = ENTITY_TYPES.register(
			"energy_cell_mob",
			() -> EntityType.Builder.<EnergyCellMobEntity>of(EnergyCellMobEntity::new, MobCategory.MISC)
					.clientTrackingRange(9).sized(0.5F, 0.5F)
					.build(new ResourceLocation(DoomMod.MODID, "energy_cell_mob").toString()));

	public static final RegistryObject<EntityType<RocketMobEntity>> ROCKET_MOB = ENTITY_TYPES.register("rocket_mob",
			() -> EntityType.Builder.<RocketMobEntity>of(RocketMobEntity::new, MobCategory.MISC).sized(0.5F, 0.5F)
					.clientTrackingRange(9).build(new ResourceLocation(DoomMod.MODID, "rocket_mob").toString()));

	public static final RegistryObject<EntityType<ChaingunMobEntity>> CHAINGUN_MOB = ENTITY_TYPES
			.register("chaingun_mob",
					() -> EntityType.Builder.<ChaingunMobEntity>of(ChaingunMobEntity::new, MobCategory.MISC)
							.sized(0.5F, 0.5F).clientTrackingRange(9)
							.build(new ResourceLocation(DoomMod.MODID, "chaingun_mob").toString()));

}
