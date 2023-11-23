package mod.azure.doom.entities.ai;

import mod.azure.doom.entities.DemonEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class DemonFloatControl extends MoveControl {
    private final DemonEntity demon;
    private int courseChangeCooldown;

    public DemonFloatControl(DemonEntity demonEntity) {
        super(demonEntity);
        demon = demonEntity;
    }

    @Override
    public void tick() {
        if (operation == Operation.MOVE_TO && courseChangeCooldown-- <= 0) {
            courseChangeCooldown += demon.getRandom().nextInt(5) + 2;
            var vector3d = new Vec3(wantedX - demon.getX(), wantedY - demon.getY(), wantedZ - demon.getZ());
            final var d0 = vector3d.length();
            vector3d = vector3d.normalize();
            if (canReach(vector3d, Mth.ceil(d0)))
                demon.setDeltaMovement(demon.getDeltaMovement().add(vector3d.scale(0.1D)));
            else operation = Operation.WAIT;
        }
    }

    private boolean canReach(Vec3 vec3, int number) {
        var axisalignedbb = demon.getBoundingBox();

        for (var i = 1; i < number; ++i) {
            axisalignedbb = axisalignedbb.move(vec3);
            if (!demon.level().noCollision(demon, axisalignedbb)) return false;
        }

        return true;
    }
}