package mod.azure.doom.entity.ai.goal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;

public class DoomPathFinder extends PathFinder {
	public DoomPathFinder(NodeEvaluator processor, int maxVisitedNodes) {
		super(processor, maxVisitedNodes);
	}

	@Nullable
	@Override
	public Path findPath(PathNavigationRegion regionIn, Mob mob, Set<BlockPos> targetPositions, float maxRange,
			int accuracy, float searchDepthMultiplier) {
		Path path = super.findPath(regionIn, mob, targetPositions, maxRange, accuracy, searchDepthMultiplier);
		return path == null ? null : new PatchedPath(path);
	}

	@Override
	protected float distance(Node first, Node second) {
		return first.distanceToXZ(second);
	}

	static class PatchedPath extends Path {
		public PatchedPath(Path original) {
			super(copyPathPoints(original), original.getTarget(), original.canReach());
		}

		@Override
		public Vec3 getEntityPosAtNode(Entity entity, int index) {
			Node point = this.getNode(index);
			double d0 = point.x + Mth.floor(entity.getBbWidth() + 1.0F) * 0.5D;
			double d1 = point.y;
			double d2 = point.z + Mth.floor(entity.getBbWidth() + 1.0F) * 0.5D;
			return new Vec3(d0, d1, d2);
		}

		private static List<Node> copyPathPoints(Path original) {
			List<Node> points = new ArrayList();
			for (int i = 0; i < original.getNodeCount(); i++) {
				points.add(original.getNode(i));
			}
			return points;
		}
	}
}