package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import mod.azure.doom.block.ArgentBlock;
import mod.azure.doom.block.ArgentLampBlock;
import mod.azure.doom.block.BarrelBlock;
import mod.azure.doom.block.DoomSandBlock;
import mod.azure.doom.block.DoomWallBlock;
import mod.azure.doom.block.E1M1StairsBlock;
import mod.azure.doom.block.E1M1TurnableBlock;
import mod.azure.doom.block.E1M1TurnableHurtBlock;
import mod.azure.doom.block.GunTableBlock;
import mod.azure.doom.block.JumppadBlock;
import mod.azure.doom.block.TickingLightBlock;
import mod.azure.doom.block.TotemBlock;
import mod.azure.doom.item.GuntableBlockItem;
import mod.azure.doom.item.TotemBlockItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class DoomBlocks {
	public static final Block JUMP_PAD = new JumppadBlock();

	public static final Block E1M1_1 = new ArgentBlock();
	public static final Block E1M1_2 = new ArgentBlock();
	public static final Block E1M1_3 = new ArgentBlock();
	public static final Block E1M1_4 = new ArgentBlock();
	public static final Block E1M1_5 = new ArgentBlock();
	public static final Block E1M1_6 = new ArgentBlock();
	public static final Block E1M1_7 = new E1M1StairsBlock(E1M1_5.getDefaultState(),
			AbstractBlock.Settings.copy(E1M1_5));
	public static final Block E1M1_8 = new E1M1StairsBlock(E1M1_4.getDefaultState(),
			AbstractBlock.Settings.copy(E1M1_4));
	public static final Block E1M1_9 = new E1M1TurnableBlock();
	public static final Block E1M1_10 = new E1M1TurnableBlock();
	public static final Block E1M1_11 = new E1M1TurnableHurtBlock();
	public static final Block E1M1_12 = new E1M1TurnableBlock();
	public static final Block E1M1_13 = new E1M1TurnableBlock();
	public static final Block E1M1_14 = new E1M1TurnableBlock();
	public static final Block E1M1_15 = new E1M1TurnableBlock();
	public static final Block E1M1_16 = new E1M1TurnableBlock();
	public static final Block E1M1_17 = new E1M1TurnableBlock();
	public static final Block E1M1_18 = new E1M1TurnableBlock();
	public static final Block E1M1_19 = new E1M1TurnableBlock();
	public static final Block E1M1_20 = new E1M1TurnableBlock();
	public static final Block E1M1_21 = new E1M1TurnableBlock();
	public static final Block E1M1_22 = new E1M1TurnableBlock();
	public static final Block E1M1_23 = new E1M1TurnableBlock();
	public static final Block E1M1_24 = new E1M1TurnableBlock();
	public static final Block E1M1_25 = new E1M1TurnableBlock();
	public static final Block E1M1_26 = new E1M1TurnableBlock();

	public static final Block DOOM_SAND = new DoomSandBlock();
	public static final Block ARGENT_BLOCK = new ArgentBlock();
	public static final Block BARREL_BLOCK = new BarrelBlock();
	public static final Block ICON_WALL1 = new DoomWallBlock();
	public static final Block ICON_WALL2 = new DoomWallBlock();
	public static final Block ICON_WALL3 = new DoomWallBlock();
	public static final Block ICON_WALL4 = new DoomWallBlock();
	public static final Block ICON_WALL5 = new DoomWallBlock();
	public static final Block ICON_WALL6 = new DoomWallBlock();
	public static final Block ICON_WALL7 = new DoomWallBlock();
	public static final Block ICON_WALL8 = new DoomWallBlock();
	public static final Block ICON_WALL9 = new DoomWallBlock();
	public static final Block ICON_WALL10 = new DoomWallBlock();
	public static final Block ICON_WALL11 = new DoomWallBlock();
	public static final Block ICON_WALL12 = new DoomWallBlock();
	public static final Block ICON_WALL13 = new DoomWallBlock();
	public static final Block ICON_WALL14 = new DoomWallBlock();
	public static final Block ICON_WALL15 = new DoomWallBlock();
	public static final Block ICON_WALL16 = new DoomWallBlock();
	public static final Block ARGENT_LAMP_BLOCK = new ArgentLampBlock();

	public static final Block TOTEM = new TotemBlock();
	public static final TickingLightBlock TICKING_LIGHT_BLOCK = new TickingLightBlock();
	public static final GunTableBlock GUN_TABLE = new GunTableBlock();

	public static void init() {
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "gun_table"), GUN_TABLE);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "gun_table"),
				new GuntableBlockItem(GUN_TABLE, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "lightblock"), TICKING_LIGHT_BLOCK);

		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "totem"), TOTEM);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "totem"),
				new TotemBlockItem(TOTEM, new Item.Settings()));

		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "argent_block"), ARGENT_BLOCK);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "argent_block"),
				new BlockItem(ARGENT_BLOCK, new Item.Settings()));

		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "jump_pad"), JUMP_PAD);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "jump_pad"),
				new BlockItem(JUMP_PAD, new Item.Settings()));

		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "doom_sand"), DOOM_SAND);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "doom_sand"),
				new BlockItem(DOOM_SAND, new Item.Settings()));

		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "argent_lamp_block"), ARGENT_LAMP_BLOCK);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "argent_lamp_block"),
				new BlockItem(ARGENT_LAMP_BLOCK, new Item.Settings()));

		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "barrel"), BARREL_BLOCK);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "barrel"),
				new BlockItem(BARREL_BLOCK, new Item.Settings()));

		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "icon_wall1"), ICON_WALL1);
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "icon_wall2"), ICON_WALL2);
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "icon_wall3"), ICON_WALL3);
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "icon_wall4"), ICON_WALL4);
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "icon_wall5"), ICON_WALL5);
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "icon_wall6"), ICON_WALL6);
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "icon_wall7"), ICON_WALL7);
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "icon_wall8"), ICON_WALL8);
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "icon_wall9"), ICON_WALL9);
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "icon_wall10"), ICON_WALL10);
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "icon_wall11"), ICON_WALL11);
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "icon_wall12"), ICON_WALL12);
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "icon_wall13"), ICON_WALL13);
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "icon_wall14"), ICON_WALL14);
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "icon_wall15"), ICON_WALL15);
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "icon_wall16"), ICON_WALL16);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "icon_wall1"),
				new BlockItem(ICON_WALL1, new Item.Settings()));
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "icon_wall2"),
				new BlockItem(ICON_WALL2, new Item.Settings()));
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "icon_wall3"),
				new BlockItem(ICON_WALL3, new Item.Settings()));
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "icon_wall4"),
				new BlockItem(ICON_WALL4, new Item.Settings()));
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "icon_wall5"),
				new BlockItem(ICON_WALL5, new Item.Settings()));
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "icon_wall6"),
				new BlockItem(ICON_WALL6, new Item.Settings()));
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "icon_wall7"),
				new BlockItem(ICON_WALL7, new Item.Settings()));
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "icon_wall8"),
				new BlockItem(ICON_WALL8, new Item.Settings()));
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "icon_wall9"),
				new BlockItem(ICON_WALL9, new Item.Settings()));
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "icon_wall10"),
				new BlockItem(ICON_WALL10, new Item.Settings()));
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "icon_wall11"),
				new BlockItem(ICON_WALL11, new Item.Settings()));
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "icon_wall12"),
				new BlockItem(ICON_WALL12, new Item.Settings()));
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "icon_wall13"),
				new BlockItem(ICON_WALL13, new Item.Settings()));
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "icon_wall14"),
				new BlockItem(ICON_WALL14, new Item.Settings()));
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "icon_wall15"),
				new BlockItem(ICON_WALL15, new Item.Settings()));
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "icon_wall16"),
				new BlockItem(ICON_WALL16, new Item.Settings()));

		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block1"), E1M1_1);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block1"),
				new BlockItem(E1M1_1, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block2"), E1M1_2);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block2"),
				new BlockItem(E1M1_2, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block3"), E1M1_3);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block3"),
				new BlockItem(E1M1_3, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block4"), E1M1_4);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block4"),
				new BlockItem(E1M1_4, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block5"), E1M1_5);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block5"),
				new BlockItem(E1M1_5, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block6"), E1M1_6);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block6"),
				new BlockItem(E1M1_6, new Item.Settings()));

		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block7"), E1M1_7);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block7"),
				new BlockItem(E1M1_7, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block8"), E1M1_8);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block8"),
				new BlockItem(E1M1_8, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block9"), E1M1_9);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block9"),
				new BlockItem(E1M1_9, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block10"), E1M1_10);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block10"),
				new BlockItem(E1M1_10, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block11"), E1M1_11);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block11"),
				new BlockItem(E1M1_11, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block12"), E1M1_12);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block12"),
				new BlockItem(E1M1_12, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block13"), E1M1_13);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block13"),
				new BlockItem(E1M1_13, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block14"), E1M1_14);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block14"),
				new BlockItem(E1M1_14, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block15"), E1M1_15);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block15"),
				new BlockItem(E1M1_15, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block16"), E1M1_16);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block16"),
				new BlockItem(E1M1_16, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block17"), E1M1_17);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block17"),
				new BlockItem(E1M1_17, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block18"), E1M1_18);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block18"),
				new BlockItem(E1M1_18, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block19"), E1M1_19);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block19"),
				new BlockItem(E1M1_19, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block20"), E1M1_20);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block20"),
				new BlockItem(E1M1_20, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block21"), E1M1_21);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block21"),
				new BlockItem(E1M1_21, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block22"), E1M1_22);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block22"),
				new BlockItem(E1M1_22, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block23"), E1M1_23);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block23"),
				new BlockItem(E1M1_23, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block24"), E1M1_24);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block24"),
				new BlockItem(E1M1_24, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block25"), E1M1_25);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block25"),
				new BlockItem(E1M1_25, new Item.Settings()));
		Registry.register(Registries.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block26"), E1M1_26);
		Registry.register(Registries.ITEM, new Identifier(DoomMod.MODID, "e1m1_block26"),
				new BlockItem(E1M1_26, new Item.Settings()));
	}
}