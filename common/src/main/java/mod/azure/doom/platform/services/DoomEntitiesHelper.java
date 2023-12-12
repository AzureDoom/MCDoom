package mod.azure.doom.platform.services;

import mod.azure.doom.blocks.blockentities.BarrelEntity;
import mod.azure.doom.blocks.blockentities.GunBlockEntity;
import mod.azure.doom.blocks.blockentities.IconBlockEntity;
import mod.azure.doom.blocks.blockentities.TotemEntity;
import mod.azure.doom.entities.projectiles.*;
import mod.azure.doom.entities.projectiles.entity.*;
import mod.azure.doom.entities.tierambient.TentacleEntity;
import mod.azure.doom.entities.tierboss.IconofsinEntity;
import mod.azure.doom.entities.tierfodder.LostSoulEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface DoomEntitiesHelper {

    BlockEntityType<TotemEntity> getTotemEntity();

    BlockEntityType<IconBlockEntity> getIconBlockEntity();

    BlockEntityType<GunBlockEntity> getGunTableEntity();

    EntityType<BarrelEntity> getBarrelEntity();


    EntityType<DoomFireEntity> getDoomFireEntity();

    EntityType<DroneBoltEntity> getDroneBoltEntity();

    EntityType<BloodBoltEntity> getBloodBoltEntity();

    EntityType<BFGEntity> getBFGEtntity();

    EntityType<RocketEntity> getRocketEntity();

    EntityType<BarenBlastEntity> getBarenBlastEntity();

    EntityType<BulletEntity> getBulletEntity();

    EntityType<RocketMobEntity> getRocketMobEntity();

    EntityType<GladiatorMaceEntity> getGlaiatorMaceEntity();

    EntityType<EnergyCellMobEntity> getEnergyCellMobEntity();

    EntityType<GrenadeEntity> getGranadeEntity();

    EntityType<ChaingunMobEntity> getChaingunBulletMobEntity();

    EntityType<FireProjectile> getFireEntity();

    EntityType<MeatHookEntity> getMeatHookEntity();

    EntityType<IconofsinEntity> getIconofSinEntity();

    EntityType<LostSoulEntity> getLostSoulEntity();

    EntityType<TentacleEntity> getTentacleEntity();
}
