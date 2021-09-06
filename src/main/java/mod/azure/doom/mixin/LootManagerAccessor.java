package mod.azure.doom.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;

@Mixin(LootManager.class)
public interface LootManagerAccessor {
    @Accessor("tables")
    Map<Identifier, LootTable> rs_getTables();
}