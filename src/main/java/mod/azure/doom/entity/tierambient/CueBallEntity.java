package mod.azure.doom.entity.tierambient;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.config.DoomConfig;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CueBallEntity extends DemonEntity implements IAnimatable, IAnimationTickable {

	private AnimationFactory factory = new AnimationFactory(this);

	public int flameTimer;

	public CueBallEntity(EntityType<CueBallEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MAX_HEALTH, DoomConfig.SERVER.cueball_health.get()).add(Attributes.ATTACK_DAMAGE, 0.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.0D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
	}

	@Override
	public void knockback(double strength, double x, double z) {
		super.knockback(3, x, z);
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 30) {
			this.remove(RemovalReason.KILLED);
			this.dropExperience();
			if (!this.level.isClientSide) {
				this.explode();
			}
		}
	}

	protected void explode() {
		this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F, Explosion.BlockInteraction.NONE);
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<CueBallEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
	}

	protected boolean shouldDrown() {
		return false;
	}

	protected boolean shouldBurnInDay() {
		return false;
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 1;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		flameTimer = (flameTimer + 1) % 2;
	}

	public int getFlameTimer() {
		return flameTimer;
	}

	@Override
	public int tickTimer() {
		return tickCount;
	}

}
