package mod.azure.doom.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.util.Identifier;

@Mixin(LootContext.class)
public interface LootContextAccessor {
	@Accessor("parameters")
	Map<LootContextParameter<?>, Object> rs_getParameters();

	@Accessor("drops")
	Map<Identifier, LootContext.Dropper> rs_getDrops();
}