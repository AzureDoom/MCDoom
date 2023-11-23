package mod.azure.doom.helper;

import mod.azure.doom.MCDoom;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MCDoom.MOD_ID)
public class LootHandler {
    @SubscribeEvent
    public static void lootLoad(LootTableLoadEvent evt) {
        final var prefix = "minecraft:chests/";
        final var name = evt.getName().toString();

        if (name.startsWith(prefix)) {
            final var file = name.substring(name.indexOf(prefix) + prefix.length());
            switch (file) {
                case "ruined_portal":
                case "bastion_bridge":
                case "bastion_hoglin_stable":
                case "bastion_other":
                case "bastion_treasure":
                case "nether_bridge":
                case "spawn_bonus_chest":
                    evt.getTable().addPool(getInjectPool(file));
                    break;
                default:
                    break;
            }
        }
    }

    private static LootPool getInjectPool(String entryName) {
        return LootPool.lootPool().add(getInjectEntry(entryName, 1)).name("doom_inject").build();
    }

    private static LootItem.Builder getInjectEntry(String name, int weight) {
        final var table = new ResourceLocation(MCDoom.MOD_ID, "chests/" + name);
        return LootTableReference.lootTableReference(table).setWeight(weight);
    }
}