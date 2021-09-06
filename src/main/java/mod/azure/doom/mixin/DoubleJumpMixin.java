package mod.azure.doom.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import io.netty.buffer.Unpooled;
import mod.azure.doom.DoomMod;
import mod.azure.doom.util.registry.DoomEnchantments;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

@SuppressWarnings("deprecation")
@Mixin(ClientPlayerEntity.class)
public abstract class DoubleJumpMixin extends AbstractClientPlayerEntity {

	@Shadow
	public abstract boolean isRiding();

	@Shadow
	public Input input;

	private int jumpCount = 0;
	private boolean jumpKey = true;

	public DoubleJumpMixin(ClientWorld world, GameProfile profile) {
		super(world, profile);
	}

	@Inject(method = "tickMovement", at = @At("TAIL"))
	private void doubleJumpTickMovement(CallbackInfo ci) {
		this.doDoubleJump();
	}

	private void doDoubleJump() {

		MinecraftClient mc = MinecraftClient.getInstance();

		Vec3d pos = this.getPos();
		Vec3d motion = this.getVelocity();
		ClientPlayerEntity player = mc.player;

		Box box = new Box(pos.getX(), pos.getY() + this.getEyeHeight(this.getPose()) * 1.6D, pos.getZ(), pos.getX(),
				pos.getY() + this.getHeight(), pos.getZ());

		ItemStack stack = this.getEquippedStack(EquipmentSlot.FEET);
		if (!stack.isEmpty()) {
			Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(stack);
			if (enchantments.containsKey(DoomEnchantments.LEAPING_DOOM))

				if (this.onGround || this.world.containsFluid(box) || this.isRiding() || this.abilities.allowFlying) {

					this.jumpCount = this.getMultiJumps();

				} else if (this.input.jumping) {

					if (!this.jumpKey && this.jumpCount > 0 && motion.getY() < 0.333
							&& this.getHungerManager().getFoodLevel() > 0) {

						this.jump();
						this.jumpCount--;

						this.fallDistance = 0.0F;
						double d0 = world.random.nextGaussian() * 0.02D;
						double d1 = world.random.nextGaussian() * 0.02D;
						double d2 = world.random.nextGaussian() * 0.02D;
						PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
						passedData.writeFloat(this.fallDistance);
						ClientSidePacketRegistry.INSTANCE.sendToServer(DoomMod.FALL_DISTANCE_PACKET_ID, passedData);
						world.addParticle(ParticleTypes.CRIT,
								player.getX() + (double) (player.world.random.nextFloat() * player.getWidth() * 2.0F)
										- (double) player.getWidth() - d0 * 10.0D,
								player.getY() + (double) (player.world.random.nextFloat() * player.getHeight())
										- d1 * 10.0D,
								player.getZ() + (double) (player.world.random.nextFloat() * player.getWidth() * 2.0F)
										- (double) player.getWidth() - d2 * 10.0D,
								d0, d1, d2);
						Vec3d playerLook = player.getRotationVec(1);
						Vec3d dashVec = new Vec3d(playerLook.x, player.getVelocity().y, playerLook.z);
						player.setVelocity(dashVec);
						player.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1.0F, 2.0F);
					}

					this.jumpKey = true;

				} else {

					this.jumpKey = false;

				}
		}
	}

	private int getMultiJumps() {

		int jumpCount = 0;
		jumpCount += 1;

		ItemStack stack = this.getEquippedStack(EquipmentSlot.FEET);
		if (!stack.isEmpty()) {
			Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(stack);
			if (enchantments.containsKey(DoomEnchantments.LEAPING_DOOM))
				jumpCount += enchantments.get(DoomEnchantments.LEAPING_DOOM);
		}

		return jumpCount;
	}
}