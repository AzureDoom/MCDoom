package mod.azure.doom.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.util.Identifier;

@Mixin(LootContext.Builder.class)
public interface BuilderAccessor {
	@Mutable
	@Accessor("drops")
	void rs_setDrops(Map<Identifier, LootContext.Dropper> drops);

	@Mutable
	@Accessor("parameters")
	void rs_setParameters(Map<LootContextParameter<?>, Object> parameters);
}