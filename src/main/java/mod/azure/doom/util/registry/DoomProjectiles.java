package mod.azure.doom.util.registry;

import java.util.LinkedList;
import java.util.List;

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
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class DoomProjectiles {

	public static List<EntityType<? extends Entity>> ENTITY_TYPES = new LinkedList();
	public static List<EntityType<? extends Entity>> ENTITY_THAT_USE_ITEM_RENDERS = new LinkedList();
	public static EntityType<DoomFireEntity> FIRING;
	public static EntityType<ArgentBoltEntity> ARGENT_BOLT;
	public static EntityType<DroneBoltEntity> DRONEBOLT_MOB;
	public static EntityType<BloodBoltEntity> BLOODBOLT_MOB;
	public static EntityType<UnmaykrBoltEntity> UNMAYKR;
	public static EntityType<ShotgunShellEntity> SHOTGUN_SHELL;
	public static EntityType<EnergyCellEntity> ENERGY_CELL;
	public static EntityType<BFGEntity> BFG_CELL;
	public static EntityType<RocketEntity> ROCKET;
	public static EntityType<BarenBlastEntity> BARENBLAST;
	public static EntityType<BulletEntity> BULLETS;
	public static EntityType<ChaingunBulletEntity> CHAINGUN_BULLET;
	public static EntityType<RocketMobEntity> ROCKET_MOB;
	public static EntityType<GladiatorMaceEntity> GLADIATOR_MACE;
	public static EntityType<EnergyCellMobEntity> ENERGY_CELL_MOB;
	public static EntityType<GrenadeEntity> GRENADE;
	public static EntityType<ChaingunMobEntity> CHAINGUN_MOB;
	public static EntityType<FireProjectile> FIRE_MOB;
	public static EntityType<MeatHookEntity> MEATHOOOK_ENTITY;

	private static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id, float height, float width) {
		final var type = FabricEntityTypeBuilder.<T>create(MobCategory.MISC, factory).dimensions(new EntityDimensions(height, width, true)).disableSummon().spawnableFarFromPlayer().trackRangeBlocks(90).trackedUpdateRate(1).build();
		Registry.register(Registry.ENTITY_TYPE, DoomMod.modResource(id), type);
		ENTITY_TYPES.add(type);
		ENTITY_THAT_USE_ITEM_RENDERS.add(type);

		return type;
	}

	public static void initialize() {
		FIRING = projectile(DoomFireEntity::new, "archvile_firing", 2.0F, 2.0F);
		ARGENT_BOLT = projectile(ArgentBoltEntity::new, "argent_bolt", 2.0F, 2.0F);
		DRONEBOLT_MOB = projectile(DroneBoltEntity::new, "dronebolt_mob", 2.0F, 2.0F);
		BLOODBOLT_MOB = projectile(BloodBoltEntity::new, "bloodbolt_mob", 0.5F, 0.5F);
		UNMAYKR = projectile(UnmaykrBoltEntity::new, "unmaykr_bolt", 2.0F, 2.0F);
		SHOTGUN_SHELL = projectile(ShotgunShellEntity::new, "shotgun_shell", 2.0F, 2.0F);
		ENERGY_CELL = projectile(EnergyCellEntity::new, "energy_cell", 2.0F, 2.0F);
		BFG_CELL = projectile(BFGEntity::new, "bfg_cell", 2.0F, 2.0F);
		ROCKET = projectile(RocketEntity::new, "rocket", 2.0F, 2.0F);
		BARENBLAST = projectile(BarenBlastEntity::new, "barenblast", 2.0F, 2.0F);
		BULLETS = projectile(BulletEntity::new, "bullets", 2.0F, 2.0F);
		CHAINGUN_BULLET = projectile(ChaingunBulletEntity::new, "chaingunbullets", 2.0F, 2.0F);
		ROCKET_MOB = projectile(RocketMobEntity::new, "rocket_mob", 2.0F, 2.0F);
		GLADIATOR_MACE = projectile(GladiatorMaceEntity::new, "gladiator_mace", 2.0F, 2.0F);
		ENERGY_CELL_MOB = projectile(EnergyCellMobEntity::new, "energy_cell_mob", 2.0F, 2.0F);
		GRENADE = projectile(GrenadeEntity::new, "doomed_grenade", 2.0F, 2.0F);
		CHAINGUN_MOB = projectile(ChaingunMobEntity::new, "chaingun_mob", 2.0F, 2.0F);
		FIRE_MOB = projectile(FireProjectile::new, "fire_projectile", 2.0F, 2.0F);
		MEATHOOOK_ENTITY = projectile(MeatHookEntity::new, "meathook", 2.0F, 2.0F);
	}
}