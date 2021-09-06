package mod.azure.doom.entity.attack;

import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class AttackSound {

	public SoundEvent sound = SoundEvents.ENTITY_ARROW_SHOOT;
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