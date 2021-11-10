package mod.azure.doom.util;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.tierambient.CueBallEntity;
import mod.azure.doom.entity.tierambient.GoreNestEntity;
import mod.azure.doom.entity.tierambient.TentacleEntity;
import mod.azure.doom.entity.tierambient.TurretEntity;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import mod.azure.doom.entity.tierboss.SpiderMastermind2016Entity;
import mod.azure.doom.entity.tierboss.SpiderMastermindEntity;
import mod.azure.doom.entity.tierfodder.ChaingunnerEntity;
import mod.azure.doom.entity.tierfodder.GargoyleEntity;
import mod.azure.doom.entity.tierfodder.Imp2016Entity;
import mod.azure.doom.entity.tierfodder.ImpEntity;
import mod.azure.doom.entity.tierfodder.ImpStoneEntity;
import mod.azure.doom.entity.tierfodder.LostSoulEntity;
import mod.azure.doom.entity.tierfodder.MaykrDroneEntity;
import mod.azure.doom.entity.tierfodder.MechaZombieEntity;
import mod.azure.doom.entity.tierfodder.NightmareImpEntity;
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
import mod.azure.doom.entity.tierheavy.Pinky2016;
import mod.azure.doom.entity.tierheavy.PinkyEntity;
import mod.azure.doom.entity.tierheavy.ProwlerEntity;
import mod.azure.doom.entity.tierheavy.Revenant2016Entity;
import mod.azure.doom.entity.tierheavy.RevenantEntity;
import mod.azure.doom.entity.tierheavy.SpectreEntity;
import mod.azure.doom.entity.tierheavy.WhiplashEntity;
import mod.azure.doom.entity.tiersuperheavy.ArchvileEntity;
import mod.azure.doom.entity.tiersuperheavy.ArmoredBaronEntity;
import mod.azure.doom.entity.tiersuperheavy.BaronEntity;
import mod.azure.doom.entity.tiersuperheavy.Cyberdemon2016Entity;
import mod.azure.doom.entity.tiersuperheavy.CyberdemonEntity;
import mod.azure.doom.entity.tiersuperheavy.DoomHunterEntity;
import mod.azure.doom.entity.tiersuperheavy.FireBaronEntity;
import mod.azure.doom.entity.tiersuperheavy.MarauderEntity;
import mod.azure.doom.entity.tiersuperheavy.SummonerEntity;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = DoomMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {

	@SubscribeEvent
	public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
//		EntitySpawnPlacementRegistry.register(ModEntityTypes.GLADIATOR.get(),
//				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
//				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.ARCHVILE.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.LOST_SOUL.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.ZOMBIEMAN.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.SPIDERMASTERMIND.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.ARACHNOTRON.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.MANCUBUS.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.BARON.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.REVENANT.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.IMP.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.NIGHTMARE_IMP.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.PINKY.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.SPECTRE.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.CACODEMON.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.IMP2016.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.CHAINGUNNER.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.MARAUDER.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.SHOTGUNGUY.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.PAIN.get(),
				EntitySpawnPlacementRegistry.PlacementType.IN_LAVA, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.HELLKNIGHT.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.HELLKNIGHT2016.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.CYBERDEMON.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.CYBERDEMON2016.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.UNWILLING.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.POSSESSEDSCIENTIST.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.POSSESSEDSOLDIER.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.ICONOFSIN.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.GORE_NEST.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.MECHAZOMBIE.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.GARGOYLE.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.CUEBALL.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.PROWLER.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.DREADKNIGHT.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.IMP_STONE.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.TYRANT.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.POSSESSEDWORKER.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.DOOMHUNTER.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.WHIPLASH.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.PINKY2016.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.FIREBARON.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.BARON2016.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.ARMORBARON.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.ARACHNOTRONETERNAL.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.MAYKRDRONE.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.SPIDERMASTERMIND2016.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.BLOODMAYKR.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.ARCHMAKER.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.ARCHVILEETERNAL.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.TENTACLE.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.MOTHERDEMON.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.TURRET.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.SUMMONER.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
		EntitySpawnPlacementRegistry.register(ModEntityTypes.REVENANT2016.get(),
				EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::passPeacefulAndYCheck);
	}

	@SubscribeEvent
	public static void entityAttributes(EntityAttributeCreationEvent event) {
		// event.put(ModEntityTypes.GLADIATOR.get(),
		// GladiatorEntity.createAttributes().build());
		event.put(ModEntityTypes.CYBERDEMON.get(), CyberdemonEntity.createAttributes().build());
		event.put(ModEntityTypes.ARCHVILE.get(), ArchvileEntity.createAttributes().build());
		event.put(ModEntityTypes.BARON.get(), BaronEntity.createAttributes().build());
		event.put(ModEntityTypes.CHAINGUNNER.get(), ChaingunnerEntity.createAttributes().build());
		event.put(ModEntityTypes.CYBERDEMON2016.get(), Cyberdemon2016Entity.createAttributes().build());
		event.put(ModEntityTypes.HELLKNIGHT.get(), HellknightEntity.createAttributes().build());
		event.put(ModEntityTypes.HELLKNIGHT2016.get(), Hellknight2016Entity.createAttributes().build());
		event.put(ModEntityTypes.ICONOFSIN.get(), IconofsinEntity.createAttributes().build());
		event.put(ModEntityTypes.IMP2016.get(), Imp2016Entity.createAttributes().build());
		event.put(ModEntityTypes.NIGHTMARE_IMP.get(), NightmareImpEntity.createAttributes().build());
		event.put(ModEntityTypes.IMP.get(), ImpEntity.createAttributes().build());
		event.put(ModEntityTypes.MANCUBUS.get(), MancubusEntity.createAttributes().build());
		event.put(ModEntityTypes.MARAUDER.get(), MarauderEntity.createAttributes().build());
		event.put(ModEntityTypes.PINKY.get(), PinkyEntity.createAttributes().build());
		event.put(ModEntityTypes.SPECTRE.get(), SpectreEntity.createAttributes().build());
		event.put(ModEntityTypes.LOST_SOUL.get(), LostSoulEntity.createAttributes().build());
		event.put(ModEntityTypes.POSSESSEDSCIENTIST.get(), PossessedScientistEntity.createAttributes().build());
		event.put(ModEntityTypes.POSSESSEDSOLDIER.get(), PossessedSoldierEntity.createAttributes().build());
		event.put(ModEntityTypes.REVENANT.get(), RevenantEntity.createAttributes().build());
		event.put(ModEntityTypes.SHOTGUNGUY.get(), ShotgunguyEntity.createAttributes().build());
		event.put(ModEntityTypes.ARACHNOTRON.get(), ArachnotronEntity.createAttributes().build());
		event.put(ModEntityTypes.SPIDERMASTERMIND.get(), SpiderMastermindEntity.createAttributes().build());
		event.put(ModEntityTypes.UNWILLING.get(), UnwillingEntity.createAttributes().build());
		event.put(ModEntityTypes.ZOMBIEMAN.get(), ZombiemanEntity.createAttributes().build());
		event.put(ModEntityTypes.CACODEMON.get(), CacodemonEntity.createAttributes().build());
		event.put(ModEntityTypes.PAIN.get(), PainEntity.createAttributes().build());
		event.put(ModEntityTypes.GORE_NEST.get(), GoreNestEntity.createAttributes().build());
		event.put(ModEntityTypes.MECHAZOMBIE.get(), MechaZombieEntity.createAttributes().build());
		event.put(ModEntityTypes.GARGOYLE.get(), GargoyleEntity.createAttributes().build());
		event.put(ModEntityTypes.CUEBALL.get(), CueBallEntity.createAttributes().build());
		event.put(ModEntityTypes.PROWLER.get(), ProwlerEntity.createAttributes().build());
		event.put(ModEntityTypes.DREADKNIGHT.get(), Hellknight2016Entity.createAttributes().build());
		event.put(ModEntityTypes.IMP_STONE.get(), ImpStoneEntity.createAttributes().build());
		event.put(ModEntityTypes.TYRANT.get(), Cyberdemon2016Entity.createAttributes().build());
		event.put(ModEntityTypes.POSSESSEDWORKER.get(), PossessedScientistEntity.createAttributes().build());
		event.put(ModEntityTypes.DOOMHUNTER.get(), DoomHunterEntity.createAttributes().build());
		event.put(ModEntityTypes.WHIPLASH.get(), WhiplashEntity.createAttributes().build());
		event.put(ModEntityTypes.PINKY2016.get(), Pinky2016.createAttributes().build());
		event.put(ModEntityTypes.BARON2016.get(), BaronEntity.createAttributes().build());
		event.put(ModEntityTypes.ARMORBARON.get(), ArmoredBaronEntity.createAttributes().build());
		event.put(ModEntityTypes.ARACHNOTRONETERNAL.get(), ArachnotronEntity.createAttributes().build());
		event.put(ModEntityTypes.MAYKRDRONE.get(), MaykrDroneEntity.createAttributes().build());
		event.put(ModEntityTypes.SPIDERMASTERMIND2016.get(), SpiderMastermind2016Entity.createAttributes().build());
		event.put(ModEntityTypes.BLOODMAYKR.get(), BloodMaykrEntity.createAttributes().build());
		event.put(ModEntityTypes.ARCHMAKER.get(), ArchMakyrEntity.createAttributes().build());
		event.put(ModEntityTypes.FIREBARON.get(), FireBaronEntity.createAttributes().build());
		event.put(ModEntityTypes.ARCHVILEETERNAL.get(), ArchvileEntity.createAttributes().build());
		event.put(ModEntityTypes.TENTACLE.get(), TentacleEntity.createAttributes().build());
		event.put(ModEntityTypes.MOTHERDEMON.get(), MotherDemonEntity.createAttributes().build());
		event.put(ModEntityTypes.TURRET.get(), TurretEntity.createAttributes().build());
		event.put(ModEntityTypes.SUMMONER.get(), SummonerEntity.createAttributes().build());
		event.put(ModEntityTypes.REVENANT2016.get(), Revenant2016Entity.createAttributes().build());
	}
}