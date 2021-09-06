package mod.azure.doom.entity.attack;

import net.minecraft.entity.Entity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class AttackSound {

	public SoundEvent sound = SoundEvents.ARROW_SHOOT;
	public float volume = 1.0F;
	public float pitch = 1.0F;

	public AttackSound(SoundEvent sound, float volume, float pitch) {
		this.sound = sound;
		this.volume = volume;
		this.pitch = pitch;
	}

	public void play(Entity e) {
		e.playSound(sound, volume, pitch);
	}

}