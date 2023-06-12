package mod.azure.doom.util;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

public class RollExtraTablesLootModifier extends LootModifier {

	public static final Supplier<Codec<RollExtraTablesLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(builder -> codecStart(builder).and(ResourceLocation.CODEC.listOf().fieldOf("tables").forGetter(instance -> instance.additionalTables)).apply(builder, RollExtraTablesLootModifier::new)));
	private final List<ResourceLocation> additionalTables;

	public RollExtraTablesLootModifier(LootItemCondition[] conditions, List<ResourceLocation> additionalTables) {
		super(conditions);

		this.additionalTables = additionalTables;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}

	@Nonnull
	@Override
	protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
		for (ResourceLocation tableLocation : additionalTables) {
			LootTable table = context.getResolver().getLootTable(tableLocation);
			boolean compatible = true;

			for (LootContextParam<?> param : table.getParamSet().getRequired()) {
				if (!context.hasParam(param)) {
					compatible = false;
					break;
				}
			}

			if (compatible && table != LootTable.EMPTY)
				table.getRandomItems(context, generatedLoot::add);
		}

		return generatedLoot;
	}
}
