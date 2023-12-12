package mod.azure.doom;

import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.config.format.ConfigFormats;
import mod.azure.doom.client.gui.GunTableScreenHandler;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.helper.DoomLoot;
import mod.azure.doom.helper.DoomVillagerTrades;
import mod.azure.doom.helper.MobAttributes;
import mod.azure.doom.helper.MobSpawn;
import mod.azure.doom.network.Networking;
import mod.azure.doom.recipes.GunTableRecipe;
import mod.azure.doom.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public final class FabricMCDoomMod implements ModInitializer {

    public static final RecipeSerializer<GunTableRecipe> GUN_TABLE_RECIPE_SERIALIZER = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER, MCDoom.modResource("gun_table"), new GunTableRecipe.Serializer());
    public static final ResourceKey<CreativeModeTab> DoomEggItemGroup = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
            MCDoom.modResource("eggs"));
    public static final ResourceKey<CreativeModeTab> DoomArmorItemGroup = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB, MCDoom.modResource("armor"));
    public static final ResourceKey<CreativeModeTab> DoomBlockItemGroup = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB, MCDoom.modResource("blocks"));
    public static final ResourceKey<CreativeModeTab> DoomWeaponItemGroup = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB, MCDoom.modResource("weapons"));
    public static final ResourceKey<CreativeModeTab> DoomPowerUPItemGroup = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB, MCDoom.modResource("powerup"));
    public static MenuType<GunTableScreenHandler> SCREEN_HANDLER_TYPE;

    @Override
    public void onInitialize() {
        MCDoom.config = AzureLibMod.registerConfig(DoomConfig.class, ConfigFormats.json()).getConfigInstance();
        SCREEN_HANDLER_TYPE = new MenuType<>(GunTableScreenHandler::new, FeatureFlags.VANILLA_SET);
        Registry.register(BuiltInRegistries.MENU, MCDoom.modResource("guntable_screen_type"), SCREEN_HANDLER_TYPE);
        FabricDoomBlocks.initialize();
        FabricDoomEntities.initialize();
        FabricDoomItems.initItems();
        FabricDoomItems.initArmor();
        FabricDoomItems.initEggs();
        FabricDoomSounds.initialize();
        MobSpawn.addSpawnEntries();
        MobAttributes.initialize();
        Networking.registerMessages();
        FabricDoomStructures.registerStructureFeatures();
        if (MCDoom.config.enable_all_villager_trades)
            ServerLifecycleEvents.SERVER_STARTED.register(minecraftServer -> DoomVillagerTrades.addTrades());
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, supplier, setter) -> {
            if (DoomLoot.BASTION_BRIDGE.equals(id) || DoomLoot.BASTION_HOGLIN_STABLE.equals(
                    id) || DoomLoot.BASTION_OTHER.equals(id) || DoomLoot.BASTION_TREASURE.equals(
                    id) || DoomLoot.NETHER_BRIDGE.equals(id) || DoomLoot.RUINED_PORTAL.equals(
                    id) || DoomLoot.SPAWN_BONUS_CHEST.equals(id)) {
                final LootPool poolBuilder = LootPool.lootPool().setRolls(ConstantValue.exactly(1)).with(
                        LootItem.lootTableItem(FabricDoomItems.INMORTAL).build()).with(
                        LootItem.lootTableItem(FabricDoomItems.INVISIBLE).build()).with(
                        LootItem.lootTableItem(FabricDoomItems.MEGA).build()).with(
                        LootItem.lootTableItem(FabricDoomItems.POWER).build()).with(
                        LootItem.lootTableItem(FabricDoomItems.SOULCUBE).build()).with(
                        LootItem.lootTableItem(FabricDoomItems.DAISY).build()).build();
                supplier.pool(poolBuilder);
            }
        });
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, DoomEggItemGroup,
                FabricItemGroup.builder().icon(() -> new ItemStack(FabricDoomItems.IMP_SPAWN_EGG)) // icon
                        .title(Component.translatable("itemGroup.doom.eggs")) // title
                        .displayItems((context, entries) -> {
                            entries.accept(FabricDoomItems.GORE_NEST_SPAWN_EGG);
                            entries.accept(FabricDoomItems.CUEBALL_SPAWN_EGG);
                            entries.accept(FabricDoomItems.TENTACLE_SPAWN_EGG);
                            entries.accept(FabricDoomItems.TURRET_SPAWN_EGG);
                            entries.accept(FabricDoomItems.CHAINGUNNER_SPAWN_EGG);
                            entries.accept(FabricDoomItems.GARGOYLE_SPAWN_EGG);
                            entries.accept(FabricDoomItems.IMP_SPAWN_EGG);
                            entries.accept(FabricDoomItems.STONEIMP_SPAWN_EGG);
                            entries.accept(FabricDoomItems.LOST_SOUL_SPAWN_EGG);
                            entries.accept(FabricDoomItems.LOST_SOUL_ETERNAL_SPAWN_EGG);
                            entries.accept(FabricDoomItems.MAYKR_DRONE_SPAWN_EGG);
                            entries.accept(FabricDoomItems.MECH_ZOMBIE_SPAWN_EGG);
                            entries.accept(FabricDoomItems.POSSESSED_SCIENTIST_SPAWN_EGG);
                            entries.accept(FabricDoomItems.POSSESSED_WORKER_SPAWN_EGG);
                            entries.accept(FabricDoomItems.POSSESSED_SOLDIER_SPAWN_EGG);
                            entries.accept(FabricDoomItems.SHOTGUNGUY_SPAWN_EGG);
                            entries.accept(FabricDoomItems.UNWILLING_SPAWN_EGG);
                            entries.accept(FabricDoomItems.ZOMBIEMAN_SPAWN_EGG);
                            entries.accept(FabricDoomItems.ARACHNOTRON_SPAWN_EGG);
                            entries.accept(FabricDoomItems.ARACHNOTRONETERNAL_SPAWN_EGG);
                            entries.accept(FabricDoomItems.BLOOD_MAYKR_SPAWN_EGG);
                            entries.accept(FabricDoomItems.CACODEMON_SPAWN_EGG);
                            entries.accept(FabricDoomItems.CARCASS_SPAWN_EGG);
                            entries.accept(FabricDoomItems.HELLKNIGHT_SPAWN_EGG);
                            entries.accept(FabricDoomItems.HELLKNIGHT2016_SPAWN_EGG);
                            entries.accept(FabricDoomItems.MANCUBUS_SPAWN_EGG);
                            entries.accept(FabricDoomItems.PAIN_SPAWN_EGG);
                            entries.accept(FabricDoomItems.PINKY_SPAWN_EGG);
                            entries.accept(FabricDoomItems.SPECTRE_SPAWN_EGG);
                            entries.accept(FabricDoomItems.PROWLER_SPAWN_EGG);
                            entries.accept(FabricDoomItems.REVENANT_SPAWN_EGG);
                            entries.accept(FabricDoomItems.REVENANT2016_SPAWN_EGG);
                            entries.accept(FabricDoomItems.WHIPLASH_SPAWN_EGG);
                            entries.accept(FabricDoomItems.ARCHVILE_SPAWN_EGG);
                            entries.accept(FabricDoomItems.BARON_SPAWN_EGG);
                            entries.accept(FabricDoomItems.BARON2016_SPAWN_EGG);
                            entries.accept(FabricDoomItems.FIREBORNE_BARON_SPAWN_EGG);
                            entries.accept(FabricDoomItems.ARMORED_BARON_SPAWN_EGG);
                            entries.accept(FabricDoomItems.CYBERDEMON_SPAWN_EGG);
                            entries.accept(FabricDoomItems.DOOMHUNTER_SPAWN_EGG);
                            entries.accept(FabricDoomItems.MARAUDER_SPAWN_EGG);
                            entries.accept(FabricDoomItems.SUMMONER_SPAWN_EGG);
                            entries.accept(FabricDoomItems.SPIDERMASTERMIND_SPAWN_EGG);
                            entries.accept(FabricDoomItems.SPIDERMASTERMIND2016_SPAWN_EGG);
                            entries.accept(FabricDoomItems.ICON_SPAWN_EGG);
                            entries.accept(FabricDoomItems.MOTHERDEMON_SPAWN_EGG);
                            entries.accept(FabricDoomItems.GLADIATOR_SPAWN_EGG);
                            entries.accept(FabricDoomItems.ARCH_MAKYR_SPAWN_EGG);
                        }).build()); // build() no longer registers by itself
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, DoomArmorItemGroup,
                FabricItemGroup.builder().icon(() -> new ItemStack(FabricDoomItems.DOOM_HELMET)) // icon
                        .title(Component.translatable("itemGroup.doom.armor")) // title
                        .displayItems((context, entries) -> {
                            entries.accept(FabricDoomItems.DOOM_HELMET);
                            entries.accept(FabricDoomItems.DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.DOOM_BOOTS);
                            entries.accept(FabricDoomItems.PRAETOR_DOOM_HELMET);
                            entries.accept(FabricDoomItems.PRAETOR_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.PRAETOR_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.PRAETOR_DOOM_BOOTS);
                            entries.accept(FabricDoomItems.ASTRO_DOOM_HELMET);
                            entries.accept(FabricDoomItems.ASTRO_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.ASTRO_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.ASTRO_DOOM_BOOTS);
                            entries.accept(FabricDoomItems.CRIMSON_DOOM_HELMET);
                            entries.accept(FabricDoomItems.CRIMSON_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.CRIMSON_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.CRIMSON_DOOM_BOOTS);
                            entries.accept(FabricDoomItems.MIDNIGHT_DOOM_HELMET);
                            entries.accept(FabricDoomItems.MIDNIGHT_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.MIDNIGHT_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.MIDNIGHT_DOOM_BOOTS);
                            entries.accept(FabricDoomItems.DEMONIC_DOOM_HELMET);
                            entries.accept(FabricDoomItems.DEMONIC_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.DEMONIC_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.DEMONIC_DOOM_BOOTS);
                            entries.accept(FabricDoomItems.DEMONCIDE_DOOM_HELMET);
                            entries.accept(FabricDoomItems.DEMONCIDE_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.DEMONCIDE_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.DEMONCIDE_DOOM_BOOTS);
                            entries.accept(FabricDoomItems.SENTINEL_DOOM_HELMET);
                            entries.accept(FabricDoomItems.SENTINEL_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.SENTINEL_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.SENTINEL_DOOM_BOOTS);
                            entries.accept(FabricDoomItems.EMBER_DOOM_HELMET);
                            entries.accept(FabricDoomItems.EMBER_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.EMBER_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.EMBER_DOOM_BOOTS);
                            entries.accept(FabricDoomItems.ZOMBIE_DOOM_HELMET);
                            entries.accept(FabricDoomItems.ZOMBIE_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.ZOMBIE_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.ZOMBIE_DOOM_BOOTS);
                            entries.accept(FabricDoomItems.PHOBOS_DOOM_HELMET);
                            entries.accept(FabricDoomItems.PHOBOS_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.PHOBOS_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.PHOBOS_DOOM_BOOTS);
                            entries.accept(FabricDoomItems.NIGHTMARE_DOOM_HELMET);
                            entries.accept(FabricDoomItems.NIGHTMARE_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.NIGHTMARE_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.NIGHTMARE_DOOM_BOOTS);
                            entries.accept(FabricDoomItems.PURPLEPONY_DOOM_HELMET);
                            entries.accept(FabricDoomItems.PURPLEPONY_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.PURPLEPONY_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.PURPLEPONY_DOOM_BOOTS);
                            entries.accept(FabricDoomItems.DOOMICORN_DOOM_HELMET);
                            entries.accept(FabricDoomItems.DOOMICORN_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.DOOMICORN_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.DOOMICORN_DOOM_BOOTS);
                            entries.accept(FabricDoomItems.GOLD_DOOM_HELMET);
                            entries.accept(FabricDoomItems.GOLD_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.GOLD_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.GOLD_DOOM_BOOTS);
                            entries.accept(FabricDoomItems.TWENTY_FIVE_DOOM_HELMET);
                            entries.accept(FabricDoomItems.TWENTY_FIVE_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.TWENTY_FIVE_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.TWENTY_FIVE_DOOM_BOOTS);
                            entries.accept(FabricDoomItems.BRONZE_DOOM_HELMET);
                            entries.accept(FabricDoomItems.BRONZE_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.BRONZE_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.BRONZE_DOOM_BOOTS);
                            entries.accept(FabricDoomItems.CULTIST_DOOM_HELMET);
                            entries.accept(FabricDoomItems.CULTIST_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.CULTIST_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.CULTIST_DOOM_BOOTS);
                            entries.accept(FabricDoomItems.MAYKR_DOOM_HELMET);
                            entries.accept(FabricDoomItems.MAYKR_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.MAYKR_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.MAYKR_DOOM_BOOTS);
                            entries.accept(FabricDoomItems.PAINTER_DOOM_HELMET);
                            entries.accept(FabricDoomItems.PAINTER_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.CLASSIC_DOOM_HELMET);
                            entries.accept(FabricDoomItems.CLASSIC_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.CLASSIC_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.CLASSIC_RED_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.CLASSIC_RED_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.CLASSIC_INDIGO_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.CLASSIC_INDIGO_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.CLASSIC_BRONZE_DOOM_CHESTPLATE);
                            entries.accept(FabricDoomItems.CLASSIC_BRONZE_DOOM_LEGGINGS);
                            entries.accept(FabricDoomItems.CLASSIC_DOOM_BOOTS);
                            entries.accept(FabricDoomItems.MULLET_DOOM_HELMET1);
                            entries.accept(FabricDoomItems.MULLET_DOOM_CHESTPLATE1);
                            entries.accept(FabricDoomItems.MULLET_DOOM_CHESTPLATE2);
                            entries.accept(FabricDoomItems.MULLET_DOOM_CHESTPLATE3);
                            entries.accept(FabricDoomItems.MULLET_DOOM_LEGGINGS1);
                            entries.accept(FabricDoomItems.MULLET_DOOM_BOOTS1);
                            entries.accept(FabricDoomItems.HOTROD_HELMET);
                            entries.accept(FabricDoomItems.HOTROD_CHESTPLATE);
                            entries.accept(FabricDoomItems.HOTROD_LEGGINGS);
                            entries.accept(FabricDoomItems.HOTROD_BOOTS);
                            entries.accept(FabricDoomItems.SANTA_HELMET);
                            entries.accept(FabricDoomItems.DARKLORD_HELMET);
                            entries.accept(FabricDoomItems.DARKLORD_CHESTPLATE);
                            entries.accept(FabricDoomItems.DARKLORD_LEGGINGS);
                            entries.accept(FabricDoomItems.DARKLORD_BOOTS);
                        }).build()); // build() no longer registers by itself
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, DoomBlockItemGroup,
                FabricItemGroup.builder().icon(() -> new ItemStack(FabricDoomBlocks.BARREL_BLOCK)) // icon
                        .title(Component.translatable("itemGroup.doom.blocks")) // title
                        .displayItems((context, entries) -> {
                            entries.accept(FabricDoomBlocks.BARREL_BLOCK);
                            entries.accept(FabricDoomBlocks.JUMP_PAD);
                            entries.accept(FabricDoomBlocks.DOOM_SAND);
                            entries.accept(FabricDoomBlocks.ARGENT_BLOCK);
                            entries.accept(FabricDoomBlocks.ARGENT_LAMP_BLOCK);
                            entries.accept(FabricDoomBlocks.TOTEM);
                            entries.accept(FabricDoomBlocks.GUN_TABLE);
                            entries.accept(FabricDoomBlocks.E1M1_1);
                            entries.accept(FabricDoomBlocks.E1M1_2);
                            entries.accept(FabricDoomBlocks.E1M1_3);
                            entries.accept(FabricDoomBlocks.E1M1_4);
                            entries.accept(FabricDoomBlocks.E1M1_5);
                            entries.accept(FabricDoomBlocks.E1M1_6);
                            entries.accept(FabricDoomBlocks.E1M1_7);
                            entries.accept(FabricDoomBlocks.E1M1_8);
                            entries.accept(FabricDoomBlocks.E1M1_9);
                            entries.accept(FabricDoomBlocks.E1M1_10);
                            entries.accept(FabricDoomBlocks.E1M1_11);
                            entries.accept(FabricDoomBlocks.E1M1_12);
                            entries.accept(FabricDoomBlocks.E1M1_13);
                            entries.accept(FabricDoomBlocks.E1M1_14);
                            entries.accept(FabricDoomBlocks.E1M1_15);
                            entries.accept(FabricDoomBlocks.E1M1_16);
                            entries.accept(FabricDoomBlocks.E1M1_17);
                            entries.accept(FabricDoomBlocks.E1M1_18);
                            entries.accept(FabricDoomBlocks.E1M1_19);
                            entries.accept(FabricDoomBlocks.E1M1_20);
                            entries.accept(FabricDoomBlocks.E1M1_21);
                            entries.accept(FabricDoomBlocks.E1M1_22);
                            entries.accept(FabricDoomBlocks.E1M1_23);
                            entries.accept(FabricDoomBlocks.E1M1_24);
                            entries.accept(FabricDoomBlocks.E1M1_25);
                            entries.accept(FabricDoomBlocks.E1M1_26);
                            entries.accept(FabricDoomBlocks.E1M1_27);
                            entries.accept(FabricDoomBlocks.E1M1_28);
                            entries.accept(FabricDoomBlocks.E1M1_29);
                            entries.accept(FabricDoomBlocks.ICON_WALL1);
                            entries.accept(FabricDoomBlocks.ICON_WALL2);
                            entries.accept(FabricDoomBlocks.ICON_WALL3);
                            entries.accept(FabricDoomBlocks.ICON_WALL4);
                            entries.accept(FabricDoomBlocks.ICON_WALL5);
                            entries.accept(FabricDoomBlocks.ICON_WALL6);
                            entries.accept(FabricDoomBlocks.ICON_WALL7);
                            entries.accept(FabricDoomBlocks.ICON_WALL8);
                            entries.accept(FabricDoomBlocks.ICON_WALL9);
                            entries.accept(FabricDoomBlocks.ICON_WALL10);
                            entries.accept(FabricDoomBlocks.ICON_WALL11);
                            entries.accept(FabricDoomBlocks.ICON_WALL12);
                            entries.accept(FabricDoomBlocks.ICON_WALL13);
                            entries.accept(FabricDoomBlocks.ICON_WALL14);
                            entries.accept(FabricDoomBlocks.ICON_WALL15);
                            entries.accept(FabricDoomBlocks.ICON_WALL16);
                        }).build()); // build() no longer registers by itself
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, DoomWeaponItemGroup,
                FabricItemGroup.builder().icon(() -> new ItemStack(FabricDoomItems.BFG_ETERNAL)) // icon
                        .title(Component.translatable("itemGroup.doom.weapons")) // title
                        .displayItems((context, entries) -> {
                            entries.accept(FabricDoomItems.ARGENT_AXE);
                            entries.accept(FabricDoomItems.ARGENT_HOE);
                            entries.accept(FabricDoomItems.ARGENT_SHOVEL);
                            entries.accept(FabricDoomItems.ARGENT_PICKAXE);
                            entries.accept(FabricDoomItems.ARGENT_SWORD);
                            entries.accept(FabricDoomItems.ARGENT_PAXEL);
                            entries.accept(FabricDoomItems.CHAINSAW);
                            entries.accept(FabricDoomItems.CHAINSAW64);
                            entries.accept(FabricDoomItems.CHAINSAW_ETERNAL);
                            entries.accept(FabricDoomItems.PISTOL);
                            entries.accept(FabricDoomItems.HEAVYCANNON);
                            entries.accept(FabricDoomItems.CHAINGUN);
                            entries.accept(FabricDoomItems.SG);
                            entries.accept(FabricDoomItems.DSG);
                            entries.accept(FabricDoomItems.SSG);
                            entries.accept(FabricDoomItems.DPLASMARIFLE);
                            entries.accept(FabricDoomItems.PLASMAGUN);
                            entries.accept(FabricDoomItems.ROCKETLAUNCHER);
                            entries.accept(FabricDoomItems.DGAUSS);
                            entries.accept(FabricDoomItems.BALLISTA);
                            entries.accept(FabricDoomItems.UNMAKER);
                            entries.accept(FabricDoomItems.UNMAYKR);
                            entries.accept(FabricDoomItems.BFG);
                            entries.accept(FabricDoomItems.BFG_ETERNAL);
                            entries.accept(FabricDoomItems.SWORD_CLOSED);
                            entries.accept(FabricDoomItems.CRUCIBLESWORD);
                            entries.accept(FabricDoomItems.AXE_CLOSED);
                            entries.accept(FabricDoomItems.AXE_OPEN);
                            entries.accept(FabricDoomItems.DARKLORDCRUCIBLE);
                            entries.accept(FabricDoomItems.SENTINELHAMMER);
                            entries.accept(FabricDoomItems.GRENADE);
                            entries.accept(FabricDoomItems.GAS_BARREL);
                            entries.accept(FabricDoomItems.BULLETS);
                            entries.accept(FabricDoomItems.SHOTGUN_SHELLS);
                            entries.accept(FabricDoomItems.CHAINGUN_BULLETS);
                            entries.accept(FabricDoomItems.ROCKET);
                            entries.accept(FabricDoomItems.ARGENT_BOLT);
                            entries.accept(FabricDoomItems.UNMAKRY_BOLT);
                            entries.accept(FabricDoomItems.ENERGY_CELLS);
                            entries.accept(FabricDoomItems.BFG_CELL);
                        }).build()); // build() no longer registers by itself
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, DoomPowerUPItemGroup,
                FabricItemGroup.builder().icon(() -> new ItemStack(FabricDoomItems.SOULCUBE)) // icon
                        .title(Component.translatable("itemGroup.doom.powerup")) // title
                        .displayItems((context, entries) -> {
                            entries.accept(FabricDoomItems.ARGENT_ENERGY);
                            entries.accept(FabricDoomItems.ARGENT_PLATE);
                            entries.accept(FabricDoomItems.SOULCUBE);
                            entries.accept(FabricDoomItems.INMORTAL);
                            entries.accept(FabricDoomItems.INVISIBLE);
                            entries.accept(FabricDoomItems.MEGA);
                            entries.accept(FabricDoomItems.POWER);
                            entries.accept(FabricDoomItems.DAISY);
                            entries.accept(FabricDoomItems.E1M1_MUSIC_DISC);
                            entries.accept(FabricDoomItems.GEOF_MUSIC_DISC);
                        }).build()); // build() no longer registers by itself
        DataTrackers.MEATHOOK_TRACKER.getId();
        FuelRegistry.INSTANCE.add(FabricDoomItems.ARGENT_ENERGY, 32767);
        CustomPortalBuilder.beginPortal().frameBlock(FabricDoomBlocks.E1M1_1).lightWithItem(
                FabricDoomItems.ARGENT_ENERGY).destDimID(MCDoom.modResource("doom_hell")).tintColor(139, 0,
                0).registerPortal();
    }

    public static class DataTrackers {
        public static final EntityDataAccessor<Boolean> MEATHOOK_TRACKER = SynchedEntityData.defineId(Player.class,
                EntityDataSerializers.BOOLEAN);

        private DataTrackers() {
        }
    }
}
