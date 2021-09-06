package mod.azure.doom.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Heightmap;

@Mixin(SpawnRestriction.class)
public class SpawnRestrictionMixin {

	@Shadow
	private static <T extends MobEntity> void register(EntityType<T> type, SpawnRestriction.Location location,
			Heightmap.Type heightmapType, SpawnRestriction.SpawnPredicate<T> predicate) {
	}

	static {
		register(ModEntityTypes.ARCHVILE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
		register(ModEntityTypes.ZOMBIEMAN, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.SPIDERMASTERMIND, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.ARACHNOTRON, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.MANCUBUS, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
		register(ModEntityTypes.BARON, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
		register(ModEntityTypes.REVENANT, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
		register(ModEntityTypes.IMP, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
		register(ModEntityTypes.PINKY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
		register(ModEntityTypes.SPECTRE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
		register(ModEntityTypes.CACODEMON, SpawnRestriction.Location.IN_LAVA, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
		register(ModEntityTypes.LOST_SOUL, SpawnRestriction.Location.IN_LAVA, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
		register(ModEntityTypes.IMP2016, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
		register(ModEntityTypes.NIGHTMARE_IMP, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.CHAINGUNNER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.MARAUDER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
		register(ModEntityTypes.SHOTGUNGUY, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.PAIN, SpawnRestriction.Location.IN_LAVA, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
		register(ModEntityTypes.HELLKNIGHT, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.HELLKNIGHT2016, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.CYBERDEMON, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.CYBERDEMON2016, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.UNWILLING, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.POSSESSEDSCIENTIST, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.POSSESSEDSOLDIER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.ICONOFSIN, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.MECHAZOMBIE, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.GORE_NEST, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.GARGOYLE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
		register(ModEntityTypes.CUEBALL, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
		register(ModEntityTypes.PROWLER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
		register(ModEntityTypes.DREADKNIGHT, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.IMP_STONE, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.TYRANT, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
		register(ModEntityTypes.POSSESSEDWORKER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.DOOMHUNTER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.WHIPLASH, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
		register(ModEntityTypes.PINKY2016, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.FIREBARON, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.BARON2016, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.ARMORBARON, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.ARACHNOTRONETERNAL, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.MAYKRDRONE, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.SPIDERMASTERMIND2016, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.BLOODMAYKR, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.ARCHMAKER, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.ARCHVILEETERNAL, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.TENTACLE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
		register(ModEntityTypes.MOTHERDEMON, SpawnRestriction.Location.ON_GROUND,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DemonEntity::canSpawnInDark);
		register(ModEntityTypes.TURRET, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
		register(ModEntityTypes.SUMMONER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
		register(ModEntityTypes.REVENANT2016, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				DemonEntity::canSpawnInDark);
	}
}