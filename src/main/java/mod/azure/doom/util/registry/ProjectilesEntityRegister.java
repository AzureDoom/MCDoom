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
import mod.azure.doom.entity.projectiles.RocketEntity;
import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import mod.azure.doom.entity.projectiles.UnmaykrBoltEntity;
import mod.azure.doom.entity.projectiles.entity.BarenBlastEntity;
import mod.azure.doom.entity.projectiles.entity.BloodBoltEntity;
import mod.azure.doom.entity.projectiles.entity.ChainBladeEntity;
import mod.azure.doom.entity.projectiles.entity.ChaingunMobEntity;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import mod.azure.doom.entity.projectiles.entity.DroneBoltEntity;
import mod.azure.doom.entity.projectiles.entity.EnergyCellMobEntity;
import mod.azure.doom.entity.projectiles.entity.GladiatorMaceEntity;
import mod.azure.doom.entity.projectiles.entity.RocketMobEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ProjectilesEntityRegister {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<EntityType<? extends Entity>> ENTITY_TYPES = new LinkedList();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<EntityType<? extends Entity>> ENTITY_THAT_USE_ITEM_RENDERS = new LinkedList();

	public static EntityType<DoomFireEntity> FIRING = projectile(DoomFireEntity::new, "archvile_firing");
	public static EntityType<ChainBladeEntity> CHAINBLADE = projectile(ChainBladeEntity::new, "chain_blade");
	public static EntityType<ArgentBoltEntity> ARGENT_BOLT = projectile(ArgentBoltEntity::new, "argent_bolt");
	public static EntityType<DroneBoltEntity> DRONEBOLT_MOB = projectile(DroneBoltEntity::new, "dronebolt_mob");
	public static EntityType<BloodBoltEntity> BLOODBOLT_MOB = projectile2(BloodBoltEntity::new, "bloodbolt_mob");
	public static EntityType<UnmaykrBoltEntity> UNMAYKR = projectile(UnmaykrBoltEntity::new, "unmaykr_bolt");
	public static EntityType<ShotgunShellEntity> SHOTGUN_SHELL = projectile(ShotgunShellEntity::new, "shotgun_shell");
	public static EntityType<EnergyCellEntity> ENERGY_CELL = projectile(EnergyCellEntity::new, "energy_cell");
	public static EntityType<BFGEntity> BFG_CELL = projectile1(BFGEntity::new, "bfg_cell");
	public static EntityType<RocketEntity> ROCKET = projectile(RocketEntity::new, "rocket");
	public static EntityType<BarenBlastEntity> BARENBLAST = projectile(BarenBlastEntity::new, "barenblast");
	public static EntityType<BulletEntity> BULLETS = projectile(BulletEntity::new, "bullets");
	public static EntityType<ChaingunBulletEntity> CHAINGUN_BULLET = projectile(ChaingunBulletEntity::new,
			"chaingunbullets");
	public static EntityType<RocketMobEntity> ROCKET_MOB = projectile(RocketMobEntity::new, "rocket_mob");
	public static EntityType<GladiatorMaceEntity> GLADIATOR_MACE = projectile(GladiatorMaceEntity::new, "gladiator_mace");
	public static EntityType<EnergyCellMobEntity> ENERGY_CELL_MOB = projectile(EnergyCellMobEntity::new,
			"energy_cell_mob");
	public static EntityType<GrenadeEntity> GRENADE = projectile(GrenadeEntity::new, "doomed_grenade");

	public static EntityType<ChaingunMobEntity> CHAINGUN_MOB = projectile(ChaingunMobEntity::new, "chaingun_mob");

	private static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id) {
		return projectile(factory, id, true);
	}

	private static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id,
			boolean itemRender) {

		EntityType<T> type = FabricEntityTypeBuilder.<T>create(SpawnGroup.MISC, factory)
				.dimensions(new EntityDimensions(0.5F, 0.5F, true)).disableSummon().spawnableFarFromPlayer()
				.trackRangeBlocks(90).trackedUpdateRate(4).build();

		Registry.register(Registry.ENTITY_TYPE, new Identifier(DoomMod.MODID, id), type);

		ENTITY_TYPES.add(type);

		if (itemRender) {
			ENTITY_THAT_USE_ITEM_RENDERS.add(type);
		}

		return type;
	}

	private static <T extends Entity> EntityType<T> projectile1(EntityType.EntityFactory<T> factory, String id) {
		return projectile1(factory, id, true);
	}

	private static <T extends Entity> EntityType<T> projectile1(EntityType.EntityFactory<T> factory, String id,
			boolean itemRender) {

		EntityType<T> type = FabricEntityTypeBuilder.<T>create(SpawnGroup.MISC, factory)
				.dimensions(new EntityDimensions(2.0F, 2.0F, true)).disableSummon().spawnableFarFromPlayer()
				.trackRangeBlocks(90).trackedUpdateRate(4).build();

		Registry.register(Registry.ENTITY_TYPE, new Identifier(DoomMod.MODID, id), type);

		ENTITY_TYPES.add(type);

		if (itemRender) {
			ENTITY_THAT_USE_ITEM_RENDERS.add(type);
		}

		return type;
	}

	private static <T extends Entity> EntityType<T> projectile2(EntityType.EntityFactory<T> factory, String id) {
		return projectile2(factory, id, true);
	}

	private static <T extends Entity> EntityType<T> projectile2(EntityType.EntityFactory<T> factory, String id,
			boolean itemRender) {

		EntityType<T> type = FabricEntityTypeBuilder.<T>create(SpawnGroup.MISC, factory)
				.dimensions(new EntityDimensions(0.5F, 0.5F, true)).disableSummon().spawnableFarFromPlayer()
				.trackRangeBlocks(90).trackedUpdateRate(4).build();

		Registry.register(Registry.ENTITY_TYPE, new Identifier(DoomMod.MODID, id), type);

		ENTITY_TYPES.add(type);

		if (itemRender) {
			ENTITY_THAT_USE_ITEM_RENDERS.add(type);
		}

		return type;
	}
}