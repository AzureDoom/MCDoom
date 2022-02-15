package mod.azure.doom.mixin;

import static mod.azure.doom.DoomMod.DataTrackers.MEATHOOK_TRACKER;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.azure.doom.util.PlayerProperties;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerProperties {
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
	public void readNbt(NbtCompound tag, CallbackInfo info) {
		dataTracker.set(MEATHOOK_TRACKER, tag.getBoolean("hasHook"));
	}

	@Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
	public void writeNbt(NbtCompound tag, CallbackInfo info) {
		tag.putBoolean("hasHook", dataTracker.get(MEATHOOK_TRACKER));
	}

	@Inject(method = "initDataTracker", at = @At("HEAD"))
	public void initTracker(CallbackInfo info) {
		dataTracker.startTracking(MEATHOOK_TRACKER, false);
	}

	@Override
	public boolean hasMeatHook() {
		return dataTracker.get(MEATHOOK_TRACKER);
	}

	@Override
	public void setHasMeatHook(boolean hasHook) {
		dataTracker.set(MEATHOOK_TRACKER, hasHook);
	}
}