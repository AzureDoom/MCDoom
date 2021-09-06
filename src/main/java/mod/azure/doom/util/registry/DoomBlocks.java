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
import mod.azure.doom.block.JumppadBlock;
import mod.azure.doom.block.TotemBlock;
import mod.azure.doom.item.DoomBlockItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

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

	public static void init() {
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "totem"), DoomBlocks.TOTEM);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "totem"),
				new DoomBlockItem(DoomBlocks.TOTEM, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));

		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "argent_block"), DoomBlocks.ARGENT_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "argent_block"),
				new BlockItem(DoomBlocks.ARGENT_BLOCK, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));

		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "jump_pad"), DoomBlocks.JUMP_PAD);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "jump_pad"),
				new BlockItem(DoomBlocks.JUMP_PAD, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));

		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "doom_sand"), DoomBlocks.DOOM_SAND);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "doom_sand"),
				new BlockItem(DoomBlocks.DOOM_SAND, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));

		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "argent_lamp_block"),
				DoomBlocks.ARGENT_LAMP_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "argent_lamp_block"),
				new BlockItem(DoomBlocks.ARGENT_LAMP_BLOCK, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));

		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "barrel"), DoomBlocks.BARREL_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "barrel"),
				new BlockItem(DoomBlocks.BARREL_BLOCK, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));

		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "icon_wall1"), DoomBlocks.ICON_WALL1);
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "icon_wall2"), DoomBlocks.ICON_WALL2);
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "icon_wall3"), DoomBlocks.ICON_WALL3);
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "icon_wall4"), DoomBlocks.ICON_WALL4);
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "icon_wall5"), DoomBlocks.ICON_WALL5);
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "icon_wall6"), DoomBlocks.ICON_WALL6);
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "icon_wall7"), DoomBlocks.ICON_WALL7);
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "icon_wall8"), DoomBlocks.ICON_WALL8);
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "icon_wall9"), DoomBlocks.ICON_WALL9);
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "icon_wall10"), DoomBlocks.ICON_WALL10);
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "icon_wall11"), DoomBlocks.ICON_WALL11);
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "icon_wall12"), DoomBlocks.ICON_WALL12);
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "icon_wall13"), DoomBlocks.ICON_WALL13);
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "icon_wall14"), DoomBlocks.ICON_WALL14);
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "icon_wall15"), DoomBlocks.ICON_WALL15);
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "icon_wall16"), DoomBlocks.ICON_WALL16);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "icon_wall1"),
				new BlockItem(DoomBlocks.ICON_WALL1, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "icon_wall2"),
				new BlockItem(DoomBlocks.ICON_WALL2, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "icon_wall3"),
				new BlockItem(DoomBlocks.ICON_WALL3, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "icon_wall4"),
				new BlockItem(DoomBlocks.ICON_WALL4, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "icon_wall5"),
				new BlockItem(DoomBlocks.ICON_WALL5, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "icon_wall6"),
				new BlockItem(DoomBlocks.ICON_WALL6, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "icon_wall7"),
				new BlockItem(DoomBlocks.ICON_WALL7, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "icon_wall8"),
				new BlockItem(DoomBlocks.ICON_WALL8, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "icon_wall9"),
				new BlockItem(DoomBlocks.ICON_WALL9, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "icon_wall10"),
				new BlockItem(DoomBlocks.ICON_WALL10, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "icon_wall11"),
				new BlockItem(DoomBlocks.ICON_WALL11, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "icon_wall12"),
				new BlockItem(DoomBlocks.ICON_WALL12, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "icon_wall13"),
				new BlockItem(DoomBlocks.ICON_WALL13, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "icon_wall14"),
				new BlockItem(DoomBlocks.ICON_WALL14, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "icon_wall15"),
				new BlockItem(DoomBlocks.ICON_WALL15, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "icon_wall16"),
				new BlockItem(DoomBlocks.ICON_WALL16, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));

		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block1"), DoomBlocks.E1M1_1);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block1"),
				new BlockItem(DoomBlocks.E1M1_1, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block2"), DoomBlocks.E1M1_2);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block2"),
				new BlockItem(DoomBlocks.E1M1_2, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block3"), DoomBlocks.E1M1_3);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block3"),
				new BlockItem(DoomBlocks.E1M1_3, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block4"), DoomBlocks.E1M1_4);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block4"),
				new BlockItem(DoomBlocks.E1M1_4, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block5"), DoomBlocks.E1M1_5);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block5"),
				new BlockItem(DoomBlocks.E1M1_5, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block6"), DoomBlocks.E1M1_6);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block6"),
				new BlockItem(DoomBlocks.E1M1_6, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));

		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block7"), DoomBlocks.E1M1_7);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block7"),
				new BlockItem(DoomBlocks.E1M1_7, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block8"), DoomBlocks.E1M1_8);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block8"),
				new BlockItem(DoomBlocks.E1M1_8, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block9"), DoomBlocks.E1M1_9);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block9"),
				new BlockItem(DoomBlocks.E1M1_9, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block10"), DoomBlocks.E1M1_10);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block10"),
				new BlockItem(DoomBlocks.E1M1_10, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block11"), DoomBlocks.E1M1_11);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block11"),
				new BlockItem(DoomBlocks.E1M1_11, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block12"), DoomBlocks.E1M1_12);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block12"),
				new BlockItem(DoomBlocks.E1M1_12, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block13"), DoomBlocks.E1M1_13);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block13"),
				new BlockItem(DoomBlocks.E1M1_13, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block14"), DoomBlocks.E1M1_14);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block14"),
				new BlockItem(DoomBlocks.E1M1_14, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block15"), DoomBlocks.E1M1_15);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block15"),
				new BlockItem(DoomBlocks.E1M1_15, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block16"), DoomBlocks.E1M1_16);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block16"),
				new BlockItem(DoomBlocks.E1M1_16, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block17"), DoomBlocks.E1M1_17);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block17"),
				new BlockItem(DoomBlocks.E1M1_17, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block18"), DoomBlocks.E1M1_18);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block18"),
				new BlockItem(DoomBlocks.E1M1_18, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block19"), DoomBlocks.E1M1_19);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block19"),
				new BlockItem(DoomBlocks.E1M1_19, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block20"), DoomBlocks.E1M1_20);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block20"),
				new BlockItem(DoomBlocks.E1M1_20, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block21"), DoomBlocks.E1M1_21);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block21"),
				new BlockItem(DoomBlocks.E1M1_21, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block22"), DoomBlocks.E1M1_22);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block22"),
				new BlockItem(DoomBlocks.E1M1_22, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block23"), DoomBlocks.E1M1_23);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block23"),
				new BlockItem(DoomBlocks.E1M1_23, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block24"), DoomBlocks.E1M1_24);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block24"),
				new BlockItem(DoomBlocks.E1M1_24, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block25"), DoomBlocks.E1M1_25);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block25"),
				new BlockItem(DoomBlocks.E1M1_25, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
		Registry.register(Registry.BLOCK, new Identifier(DoomMod.MODID, "e1m1_block26"), DoomBlocks.E1M1_26);
		Registry.register(Registry.ITEM, new Identifier(DoomMod.MODID, "e1m1_block26"),
				new BlockItem(DoomBlocks.E1M1_26, new Item.Settings().group(DoomMod.DoomBlockItemGroup)));
	}
}