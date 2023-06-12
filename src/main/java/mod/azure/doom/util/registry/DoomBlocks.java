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
import mod.azure.doom.block.TotemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DoomBlocks {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DoomMod.MODID);

	public static final RegistryObject<Block> TOTEM = BLOCKS.register("totem", () -> new TotemBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.BONE_BLOCK).destroyTime(3).requiresCorrectToolForDrops().strength(3.0F, 3.0F).noOcclusion()));

	public static final RegistryObject<Block> GUN_TABLE = BLOCKS.register("gun_table", () -> new GunTableBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.METAL).noOcclusion()));

	public static final RegistryObject<Block> BARREL_BLOCK = BLOCKS.register("barrel", () -> new BarrelBlock(BlockBehaviour.Properties.of().instabreak().sound(SoundType.METAL).noOcclusion()));

	public static final RegistryObject<Block> ARGENT_BLOCK = BLOCKS.register("argent_block", () -> new ArgentBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.METAL)));

	public static final RegistryObject<Block> ARGENT_LAMP_BLOCK = BLOCKS.register("argent_lamp_block", () -> new ArgentLampBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.BONE_BLOCK)));

	public static final RegistryObject<Block> DOOM_SAND = BLOCKS.register("doom_sand", () -> new DoomSandBlock(Block.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.STONE)));

	public static final RegistryObject<Block> JUMP_PAD = BLOCKS.register("jump_pad", JumppadBlock::new);

	public static final RegistryObject<Block> ICON_WALL1 = BLOCKS.register("icon_wall1", () -> new DoomWallBlock(Block.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.STONE)));
	public static final RegistryObject<Block> ICON_WALL2 = BLOCKS.register("icon_wall2", () -> new DoomWallBlock(Block.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.STONE)));
	public static final RegistryObject<Block> ICON_WALL3 = BLOCKS.register("icon_wall3", () -> new DoomWallBlock(Block.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.STONE)));
	public static final RegistryObject<Block> ICON_WALL4 = BLOCKS.register("icon_wall4", () -> new DoomWallBlock(Block.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.STONE)));
	public static final RegistryObject<Block> ICON_WALL5 = BLOCKS.register("icon_wall5", () -> new DoomWallBlock(Block.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.STONE)));
	public static final RegistryObject<Block> ICON_WALL6 = BLOCKS.register("icon_wall6", () -> new DoomWallBlock(Block.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.STONE)));
	public static final RegistryObject<Block> ICON_WALL7 = BLOCKS.register("icon_wall7", () -> new DoomWallBlock(Block.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.STONE)));
	public static final RegistryObject<Block> ICON_WALL8 = BLOCKS.register("icon_wall8", () -> new DoomWallBlock(Block.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.STONE)));
	public static final RegistryObject<Block> ICON_WALL9 = BLOCKS.register("icon_wall9", () -> new DoomWallBlock(Block.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.STONE)));
	public static final RegistryObject<Block> ICON_WALL10 = BLOCKS.register("icon_wall10", () -> new DoomWallBlock(Block.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.STONE)));
	public static final RegistryObject<Block> ICON_WALL11 = BLOCKS.register("icon_wall11", () -> new DoomWallBlock(Block.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.STONE)));
	public static final RegistryObject<Block> ICON_WALL12 = BLOCKS.register("icon_wall12", () -> new DoomWallBlock(Block.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.STONE)));
	public static final RegistryObject<Block> ICON_WALL13 = BLOCKS.register("icon_wall13", () -> new DoomWallBlock(Block.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.STONE)));
	public static final RegistryObject<Block> ICON_WALL14 = BLOCKS.register("icon_wall14", () -> new DoomWallBlock(Block.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.STONE)));
	public static final RegistryObject<Block> ICON_WALL15 = BLOCKS.register("icon_wall15", () -> new DoomWallBlock(Block.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.STONE)));
	public static final RegistryObject<Block> ICON_WALL16 = BLOCKS.register("icon_wall16", () -> new DoomWallBlock(Block.Properties.of().explosionResistance(30).strength(4.0F).sound(SoundType.STONE)));

	public static final RegistryObject<Block> E1M1_1 = BLOCKS.register("e1m1_block1", () -> new ArgentLampBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_2 = BLOCKS.register("e1m1_block2", () -> new ArgentLampBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_3 = BLOCKS.register("e1m1_block3", () -> new ArgentLampBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_4 = BLOCKS.register("e1m1_block4", () -> new ArgentLampBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_5 = BLOCKS.register("e1m1_block5", () -> new ArgentLampBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_6 = BLOCKS.register("e1m1_block6", () -> new ArgentLampBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_7 = BLOCKS.register("e1m1_block7", () -> new E1M1StairsBlock(E1M1_5.get().defaultBlockState(), BlockBehaviour.Properties.copy(E1M1_5.get())));
	public static final RegistryObject<Block> E1M1_8 = BLOCKS.register("e1m1_block8", () -> new E1M1StairsBlock(E1M1_4.get().defaultBlockState(), BlockBehaviour.Properties.copy(E1M1_4.get())));
	public static final RegistryObject<Block> E1M1_9 = BLOCKS.register("e1m1_block9", () -> new E1M1TurnableBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_10 = BLOCKS.register("e1m1_block10", () -> new E1M1TurnableBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_11 = BLOCKS.register("e1m1_block11", () -> new E1M1TurnableHurtBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_12 = BLOCKS.register("e1m1_block12", () -> new E1M1TurnableBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_13 = BLOCKS.register("e1m1_block13", () -> new E1M1TurnableBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_14 = BLOCKS.register("e1m1_block14", () -> new E1M1TurnableBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_15 = BLOCKS.register("e1m1_block15", () -> new E1M1TurnableBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_16 = BLOCKS.register("e1m1_block16", () -> new E1M1TurnableBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_17 = BLOCKS.register("e1m1_block17", () -> new E1M1TurnableBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_18 = BLOCKS.register("e1m1_block18", () -> new E1M1TurnableBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_19 = BLOCKS.register("e1m1_block19", () -> new E1M1TurnableBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_20 = BLOCKS.register("e1m1_block20", () -> new E1M1TurnableBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_21 = BLOCKS.register("e1m1_block21", () -> new E1M1TurnableBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_22 = BLOCKS.register("e1m1_block22", () -> new E1M1TurnableBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_23 = BLOCKS.register("e1m1_block23", () -> new E1M1TurnableBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_24 = BLOCKS.register("e1m1_block24", () -> new E1M1TurnableBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_25 = BLOCKS.register("e1m1_block25", () -> new E1M1TurnableBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
	public static final RegistryObject<Block> E1M1_26 = BLOCKS.register("e1m1_block26", () -> new E1M1TurnableBlock(BlockBehaviour.Properties.of().explosionResistance(30).strength(4.0F)));
}