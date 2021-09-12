package mod.azure.doom.util.config;

import java.util.List;

import mod.azure.doom.DoomMod;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class EntityConfig {

	public String name;
	private String lName;
	private String uName;

	private ForgeConfigSpec.IntValue specSPAWN_WEIGHT;
	private ForgeConfigSpec.IntValue specMIN_GROUP;
	private ForgeConfigSpec.IntValue specMAX_GROUP;
	private ForgeConfigSpec.IntValue specMIN_Y;
	private ForgeConfigSpec.IntValue specMAX_Y;

	public int SPAWN_WEIGHT;
	public int MIN_GROUP;
	public int MAX_GROUP;
	public int MIN_Y;
	public int MAX_Y;
	public List<? extends String> BIOME_DICT;

	public EntityConfig(String name, int spawnWeight, int minGroup, int maxGroup, int minY, int maxY,
			List<String> biomeDict) {
		this.name = name;
		lName = name.toLowerCase();
		uName = name.toUpperCase();
		SPAWN_WEIGHT = spawnWeight;
		MIN_GROUP = minGroup;
		MAX_GROUP = maxGroup;
		MIN_Y = minY;
		MAX_Y = maxY;
	}

	private ConfigValue<Double> specMAX_HEALTH;
	private ConfigValue<Double> specMELEE_ATTACK_DAMAGE;
	private ConfigValue<Double> specRANGED_ATTACK_DAMAGE;
	private ConfigValue<Double> specGENERAL_SPEED;
	private ConfigValue<Double> specFLY_SPEED;

	public double MAX_HEALTH;
	public double MELEE_ATTACK_DAMAGE;
	public float RANGED_ATTACK_DAMAGE;
	public double GENERAL_SPEED;
	public double FLY_SPEED;

	protected void bake() {
		// load into memory to avoid real-time persistent storage calls
		SPAWN_WEIGHT = specSPAWN_WEIGHT.get();
		MIN_GROUP = specMIN_GROUP.get();
		MAX_GROUP = specMAX_GROUP.get();
		MIN_Y = specMIN_Y.get();
		MAX_Y = specMAX_Y.get();
		MAX_HEALTH = specMAX_HEALTH.get();
		MELEE_ATTACK_DAMAGE = specMELEE_ATTACK_DAMAGE == null ? 0 : specMELEE_ATTACK_DAMAGE.get();
		RANGED_ATTACK_DAMAGE = specRANGED_ATTACK_DAMAGE == null ? 0F : specRANGED_ATTACK_DAMAGE.get().floatValue();
		GENERAL_SPEED = specGENERAL_SPEED == null ? 0 : specGENERAL_SPEED.get();
		FLY_SPEED = specFLY_SPEED == null ? 0 : specFLY_SPEED.get();
	}

	public EntityConfig setAttributes(EntityDefaults defaults) {
		MAX_HEALTH = defaults.getDefaultMaxHealth();
		MELEE_ATTACK_DAMAGE = defaults.getDefaultMeleeDamage();
		RANGED_ATTACK_DAMAGE = defaults.getDefaultRangedDamage();
		GENERAL_SPEED = defaults.getDefaultGeneralSpeed();
		FLY_SPEED = defaults.getDefaultFlySpeed();
		return this;
	}

	public Builder pushAttributes(Builder attributes) {
		if (MELEE_ATTACK_DAMAGE > 0) {
			attributes.add(Attributes.ATTACK_DAMAGE, MELEE_ATTACK_DAMAGE);
		}
		if (GENERAL_SPEED > 0) {
			attributes.add(Attributes.MOVEMENT_SPEED, GENERAL_SPEED);
		}
		if (FLY_SPEED > 0) {
			attributes.add(Attributes.FLYING_SPEED, FLY_SPEED);
		}
		return attributes.add(Attributes.MAX_HEALTH, MAX_HEALTH);
	}

	public EntityConfig buildVia(ForgeConfigSpec.Builder builder) {

		builder.push("individual_spawning");
		builder.push(name);

		/*
		 * Customisable per-entity spawn weights
		 */

		specSPAWN_WEIGHT = builder
				.comment(
						new StringBuilder("Spawn weight of ").append(name).append("s. Set to 0 to disable.").toString())
				.translation(new StringBuilder(DoomMod.MODID).append(".config.").append(lName).append("_spawn_weight")
						.toString())
				.defineInRange(new StringBuilder(uName).append("_SPAWN_WEIGHT").toString(), SPAWN_WEIGHT, 0, 9999);

		specMIN_GROUP = builder.comment("Smallest group to spawn.")
				.translation(new StringBuilder(DoomMod.MODID).append(".config.").append(lName).append("_min_group")
						.toString())
				.defineInRange(new StringBuilder(uName).append("_MIN_GROUP").toString(), MIN_GROUP, 1, 9);

		specMAX_GROUP = builder.comment("Largest group to spawn.")
				.translation(new StringBuilder(DoomMod.MODID).append(".config.").append(lName).append("_max_group")
						.toString())
				.defineInRange(new StringBuilder(uName).append("_MAX_GROUP").toString(), MAX_GROUP, 1, 9);

		specMIN_Y = builder.comment("Minimum Y location to spawn at.")
				.translation(
						new StringBuilder(DoomMod.MODID).append(".config.").append(lName).append("_min_y").toString())
				.defineInRange(new StringBuilder(uName).append("_MIN_Y").toString(), MIN_Y, 0, 255);

		specMAX_Y = builder.comment("Maximum Y location to spawn at.")
				.translation(
						new StringBuilder(DoomMod.MODID).append(".config.").append(lName).append("_max_y").toString())
				.defineInRange(new StringBuilder(uName).append("_MAX_Y").toString(), MAX_Y, 0, 255);

		builder.pop(3);

		/*
		 * Customisable mob attributes
		 */
		builder.push("attributes");
		builder.push(name);

		specMAX_HEALTH = builder.comment(new StringBuilder("Health of ").append(name).append("s").toString())
				.translation(new StringBuilder(DoomMod.MODID).append(".config.").append(lName).append("_max_health")
						.toString())
				.defineInRange(new StringBuilder(uName).append("_MAX_HEALTH").toString(), MAX_HEALTH, 0.5,
						Double.MAX_VALUE);

		if (MELEE_ATTACK_DAMAGE > 0) {
			specMELEE_ATTACK_DAMAGE = builder
					.comment(new StringBuilder("Average melee damaged caused by ").append(name)
							.append("s when attacking.").toString())
					.translation(new StringBuilder(DoomMod.MODID).append(".config.").append(lName)
							.append("_melee_attack_damage").toString())
					.defineInRange(new StringBuilder(uName).append("_MELEE_ATTACK_DAMAGE").toString(),
							MELEE_ATTACK_DAMAGE, 0, Double.MAX_VALUE);
		}

		if (RANGED_ATTACK_DAMAGE > 0) {
			specRANGED_ATTACK_DAMAGE = builder
					.comment(new StringBuilder("Average RANGED damaged caused by ").append(name)
							.append("s when attacking.").toString())
					.translation(new StringBuilder(DoomMod.MODID).append(".config.").append(lName)
							.append("_ranged_attack_damage").toString())
					.defineInRange(new StringBuilder(uName).append("_RANGED_ATTACK_DAMAGE").toString(),
							RANGED_ATTACK_DAMAGE, 0, Double.MAX_VALUE);
		}

		if (GENERAL_SPEED > 0) {
			specGENERAL_SPEED = builder
					.comment(new StringBuilder("General (most likely walking) movement speed of ").append(name)
							.append("s").toString())
					.translation(new StringBuilder(DoomMod.MODID).append(".config.").append(lName)
							.append("_ground_speed").toString())
					.defineInRange(new StringBuilder(uName).append("_GROUND_SPEED").toString(), GENERAL_SPEED, 0, 10);
		}

		if (FLY_SPEED > 0) {
			specFLY_SPEED = builder.comment(new StringBuilder("Flying speed of ").append(name).append("s").toString())
					.translation(new StringBuilder(DoomMod.MODID).append(".config.").append(lName).append("_fly_speed")
							.toString())
					.defineInRange(new StringBuilder(uName).append("_FLY_SPEED").toString(), FLY_SPEED, 0, 10);
		}

		builder.pop(2);
		builder.push("spawn_weight");
		return this;
	}

}
