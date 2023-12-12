package mod.azure.doom.platform;

import mod.azure.doom.blocks.blockentities.BarrelEntity;
import mod.azure.doom.blocks.blockentities.GunBlockEntity;
import mod.azure.doom.blocks.blockentities.IconBlockEntity;
import mod.azure.doom.blocks.blockentities.TotemEntity;
import mod.azure.doom.entities.projectiles.*;
import mod.azure.doom.entities.projectiles.entity.*;
import mod.azure.doom.entities.tierambient.TentacleEntity;
import mod.azure.doom.entities.tierboss.IconofsinEntity;
import mod.azure.doom.entities.tierfodder.LostSoulEntity;
import mod.azure.doom.platform.services.DoomEntitiesHelper;
import mod.azure.doom.registry.FabricDoomEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class FabricDoomEntitiesHelper implements DoomEntitiesHelper {

    @Override
    public BlockEntityType<TotemEntity> getTotemEntity() {
        return FabricDoomEntities.TOTEM;
    }

    @Override
    public BlockEntityType<IconBlockEntity> getIconBlockEntity() {
        return FabricDoomEntities.ICON;
    }

    @Override
    public BlockEntityType<GunBlockEntity> getGunTableEntity() {
        return FabricDoomEntities.GUN_TABLE_ENTITY;
    }

    @Override
    public EntityType<BarrelEntity> getBarrelEntity() {
        return FabricDoomEntities.BARREL;
    }

    @Override
    public EntityType<DoomFireEntity> getDoomFireEntity() {
        return FabricDoomEntities.FIRING;
    }

    @Override
    public EntityType<DroneBoltEntity> getDroneBoltEntity() {
        return FabricDoomEntities.DRONEBOLT_MOB;
    }

    @Override
    public EntityType<BloodBoltEntity> getBloodBoltEntity() {
        return FabricDoomEntities.BLOODBOLT_MOB;
    }

    @Override
    public EntityType<BFGEntity> getBFGEtntity() {
        return FabricDoomEntities.BFG_CELL;
    }

    @Override
    public EntityType<RocketEntity> getRocketEntity() {
        return FabricDoomEntities.ROCKET;
    }

    @Override
    public EntityType<BarenBlastEntity> getBarenBlastEntity() {
        return FabricDoomEntities.BARENBLAST;
    }

    @Override
    public EntityType<BulletEntity> getBulletEntity() {
        return FabricDoomEntities.BULLETS;
    }

    @Override
    public EntityType<RocketMobEntity> getRocketMobEntity() {
        return FabricDoomEntities.ROCKET_MOB;
    }

    @Override
    public EntityType<GladiatorMaceEntity> getGlaiatorMaceEntity() {
        return FabricDoomEntities.GLADIATOR_MACE;
    }

    @Override
    public EntityType<EnergyCellMobEntity> getEnergyCellMobEntity() {
        return FabricDoomEntities.ENERGY_CELL_MOB;
    }

    @Override
    public EntityType<GrenadeEntity> getGranadeEntity() {
        return FabricDoomEntities.GRENADE;
    }

    @Override
    public EntityType<ChaingunMobEntity> getChaingunBulletMobEntity() {
        return FabricDoomEntities.CHAINGUN_MOB;
    }

    @Override
    public EntityType<FireProjectile> getFireEntity() {
        return FabricDoomEntities.FIRE_MOB;
    }

    @Override
    public EntityType<MeatHookEntity> getMeatHookEntity() {
        return FabricDoomEntities.MEATHOOOK_ENTITY;
    }

    @Override
    public EntityType<IconofsinEntity> getIconofSinEntity() {
        return FabricDoomEntities.ICONOFSIN;
    }

    @Override
    public EntityType<LostSoulEntity> getLostSoulEntity() {
        return FabricDoomEntities.LOST_SOUL;
    }

    @Override
    public EntityType<TentacleEntity> getTentacleEntity() {
        return FabricDoomEntities.TENTACLE;
    }
}
