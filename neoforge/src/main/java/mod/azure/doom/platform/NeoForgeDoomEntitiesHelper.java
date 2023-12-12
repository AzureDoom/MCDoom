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
import mod.azure.doom.registry.NeoDoomEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class NeoForgeDoomEntitiesHelper implements DoomEntitiesHelper {
    @Override
    public BlockEntityType<TotemEntity> getTotemEntity() {
        return NeoDoomEntities.TOTEM.get();
    }

    @Override
    public BlockEntityType<IconBlockEntity> getIconBlockEntity() {
        return NeoDoomEntities.ICON.get();
    }

    @Override
    public BlockEntityType<GunBlockEntity> getGunTableEntity() {
        return NeoDoomEntities.GUN_TABLE_ENTITY.get();
    }

    @Override
    public EntityType<BarrelEntity> getBarrelEntity() {
        return NeoDoomEntities.BARREL.get();
    }

    @Override
    public EntityType<DoomFireEntity> getDoomFireEntity() {
        return NeoDoomEntities.FIRING.get();
    }

    @Override
    public EntityType<DroneBoltEntity> getDroneBoltEntity() {
        return NeoDoomEntities.DRONEBOLT_MOB.get();
    }

    @Override
    public EntityType<BloodBoltEntity> getBloodBoltEntity() {
        return NeoDoomEntities.BLOODBOLT_MOB.get();
    }

    @Override
    public EntityType<BFGEntity> getBFGEtntity() {
        return NeoDoomEntities.BFG_CELL.get();
    }

    @Override
    public EntityType<RocketEntity> getRocketEntity() {
        return NeoDoomEntities.ROCKET.get();
    }

    @Override
    public EntityType<BarenBlastEntity> getBarenBlastEntity() {
        return NeoDoomEntities.BARENBLAST.get();
    }

    @Override
    public EntityType<BulletEntity> getBulletEntity() {
        return NeoDoomEntities.BULLETS.get();
    }

    @Override
    public EntityType<RocketMobEntity> getRocketMobEntity() {
        return NeoDoomEntities.ROCKET_MOB.get();
    }

    @Override
    public EntityType<GladiatorMaceEntity> getGlaiatorMaceEntity() {
        return NeoDoomEntities.GLADIATOR_MACE.get();
    }

    @Override
    public EntityType<EnergyCellMobEntity> getEnergyCellMobEntity() {
        return NeoDoomEntities.ENERGY_CELL_MOB.get();
    }

    @Override
    public EntityType<GrenadeEntity> getGranadeEntity() {
        return NeoDoomEntities.GRENADE.get();
    }

    @Override
    public EntityType<ChaingunMobEntity> getChaingunBulletMobEntity() {
        return NeoDoomEntities.CHAINGUN_MOB.get();
    }

    @Override
    public EntityType<FireProjectile> getFireEntity() {
        return NeoDoomEntities.FIRE_MOB.get();
    }

    @Override
    public EntityType<MeatHookEntity> getMeatHookEntity() {
        return NeoDoomEntities.MEATHOOOK_ENTITY.get();
    }

    @Override
    public EntityType<IconofsinEntity> getIconofSinEntity() {
        return NeoDoomEntities.ICONOFSIN.get();
    }

    @Override
    public EntityType<LostSoulEntity> getLostSoulEntity() {
        return NeoDoomEntities.LOST_SOUL.get();
    }

    @Override
    public EntityType<TentacleEntity> getTentacleEntity() {
        return NeoDoomEntities.TENTACLE.get();
    }
}
