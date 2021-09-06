package mod.azure.doom.util.registry;

import mod.azure.doom.util.ModSoundEvents;

public class ModRegistry {

	public static DoomBlocks BLOCKS;
	public static ModSoundEvents SOUNDS;
	public static DoomItems ITEMS;
	public static ModEntityTypes MOBS;
	public static ProjectilesEntityRegister PROJECTILES;

	public static void init() {

		BLOCKS = new DoomBlocks();
		ITEMS = new DoomItems();
		SOUNDS = new ModSoundEvents();
		PROJECTILES = new ProjectilesEntityRegister();
		MOBS = new ModEntityTypes();

	}
}