package mod.azure.doom.entity.projectiles.entity;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import mod.azure.doom.entity.tierheavy.MancubusEntity;
import mod.azure.doom.entity.tiersuperheavy.ArchvileEntity;
import mod.azure.doom.entity.tiersuperheavy.DoomHunterEntity;
import mod.azure.doom.entity.tiersuperheavy.SummonerEntity;
import mod.azure.doom.util.registry.ModEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class DoomFireEntity extends Entity implements IAnimatable {

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<DoomFireEntity>(this, "controller", 0, this::predicate));
	}

	private int warmupDelayTicks;
	private boolean sentSpikeEvent;
	private int lifeTicks = 22;
	private boolean clientSideAttackStarted;
	private LivingEntity caster;
	private UUID casterUuid;
	private float damage = 2.0F;

	public DoomFireEntity(EntityType<DoomFireEntity> p_i50170_1_, World p_i50170_2_) {
		super(p_i50170_1_, p_i50170_2_);
	}

	public DoomFireEntity(World worldIn, double x, double y, double z, float p_i47276_8_, int p_i47276_9_,
			LivingEntity casterIn, float damage) {
		this(ModEntityTypes.FIRING.get(), worldIn);
		this.warmupDelayTicks = p_i47276_9_;
		this.setCaster(casterIn);
		this.yRot = p_i47276_8_ * (180F / (float) Math.PI);
		this.setPos(x, y, z);
		this.damage = damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	protected void defineSynchedData() {
	}

	public void setCaster(@Nullable LivingEntity p_190549_1_) {
		this.caster = p_190549_1_;
		this.casterUuid = p_190549_1_ == null ? null : p_190549_1_.getUUID();
	}

	@Nullable
	public LivingEntity getCaster() {
		if (this.caster == null && this.casterUuid != null && this.level instanceof ServerWorld) {
			Entity entity = ((ServerWorld) this.level).getEntity(this.casterUuid);
			if (entity instanceof LivingEntity) {
				this.caster = (LivingEntity) entity;
			}
		}

		return this.caster;
	}

	protected void readAdditionalSaveData(CompoundNBT compound) {
		this.warmupDelayTicks = compound.getInt("Warmup");
		if (compound.hasUUID("Owner")) {
			this.casterUuid = compound.getUUID("Owner");
		}

	}

	protected void addAdditionalSaveData(CompoundNBT compound) {
		compound.putInt("Warmup", this.warmupDelayTicks);
		if (this.casterUuid != null) {
			compound.putUUID("Owner", this.casterUuid);
		}

	}

	public void tick() {
		super.tick();
		if (--this.warmupDelayTicks < 0) {
			if (!this.sentSpikeEvent) {
				this.level.broadcastEntityEvent(this, (byte) 4);
				this.sentSpikeEvent = true;
			}

			if (--this.lifeTicks < 0) {
				this.remove();
			}
		}
		List<Entity> list = this.level.getEntities(this,
				new AxisAlignedBB(this.blockPosition().above()).inflate(1D, 1D, 1D));
		for (int k2 = 0; k2 < list.size(); ++k2) {
			Entity entity = list.get(k2);
			if (!(entity instanceof MancubusEntity) && !(entity instanceof ArchvileEntity)
					&& !(entity instanceof IconofsinEntity) && !(entity instanceof DoomHunterEntity)
					&& !(entity instanceof SummonerEntity) && !(entity instanceof MotherDemonEntity)) {
				double d12 = (double) (MathHelper.sqrt(entity.distanceTo(this)));
				if (d12 <= 1.0D) {
					if (entity.isAlive()) {
						entity.hurt(DamageSource.indirectMobAttack(entity, this.getCaster()), damage);
						entity.setRemainingFireTicks(60);
					}
				}
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte id) {
		super.handleEntityEvent(id);
		if (id == 4) {
			this.clientSideAttackStarted = true;
		}

	}

	@OnlyIn(Dist.CLIENT)
	public float getAnimationProgress(float partialTicks) {
		if (!this.clientSideAttackStarted) {
			return 0.0F;
		} else {
			int i = this.lifeTicks - 2;
			return i <= 0 ? 1.0F : 1.0F - ((float) i - partialTicks) / 20.0F;
		}
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}