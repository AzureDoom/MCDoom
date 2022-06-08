package mod.azure.doom.util.registry;

public class ModRegistry {

	public static DoomBlocks BLOCKS;
	public static DoomSounds SOUNDS;
	public static DoomItems ITEMS;
	public static DoomEntities MOBS;
	public static ProjectilesEntityRegister PROJECTILES;

	public static void init() {

		BLOCKS = new DoomBlocks();
		ITEMS = new DoomItems();
		SOUNDS = new DoomSounds();
		PROJECTILES = new ProjectilesEntityRegister();
		MOBS = new DoomEntities();

	}
}