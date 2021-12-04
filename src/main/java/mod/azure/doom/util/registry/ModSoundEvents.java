package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSoundEvents {

	public static final DeferredRegister<SoundEvent> MOD_SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,
			DoomMod.MODID);

	public static final RegistryObject<SoundEvent> BFG_FIRING = MOD_SOUNDS.register("doom.bfg_firing",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.bfg_firing")));
	public static final RegistryObject<SoundEvent> BFG_HIT = MOD_SOUNDS.register("doom.bfg_hit",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.bfg_hit")));

	public static final RegistryObject<SoundEvent> PLASMA_FIRING = MOD_SOUNDS.register("doom.plasmafire",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.plasmafire")));
	public static final RegistryObject<SoundEvent> PLASMA_HIT = MOD_SOUNDS.register("doom.plasmahit",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.plasmahit")));

	public static final RegistryObject<SoundEvent> HEAVY_CANNON = MOD_SOUNDS.register("doom.heavy_cannon",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.heavy_cannon")));

	public static final RegistryObject<SoundEvent> CHAINSAW_ATTACKING = MOD_SOUNDS.register("doom.chainsaw_attacking",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.chainsaw_attacking")));
	public static final RegistryObject<SoundEvent> CHAINSAW_IDLE = MOD_SOUNDS.register("doom.chainsaw_idle",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.chainsaw_idle")));

	public static final RegistryObject<SoundEvent> ROCKET_FIRING = MOD_SOUNDS.register("doom.rocketfire",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.rocketfire")));
	public static final RegistryObject<SoundEvent> ROCKET_HIT = MOD_SOUNDS.register("doom.rockethit",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.rockethit")));

	public static final RegistryObject<SoundEvent> SHOOT1 = MOD_SOUNDS.register("doom.shoot1",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.shoot1")));

	public static final RegistryObject<SoundEvent> CHAINGUN_SHOOT = MOD_SOUNDS.register("doom.chaingun_fire",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.chaingun_fire")));

	public static final RegistryObject<SoundEvent> PISTOL_HIT = MOD_SOUNDS.register("doom.pistol_fire",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.pistol_fire")));

	public static final RegistryObject<SoundEvent> CLIPRELOAD = MOD_SOUNDS.register("doom.clipreload",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.clipreload")));

	public static final RegistryObject<SoundEvent> SHOTGUN_SHOOT = MOD_SOUNDS.register("doom.shotgun_fire",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.shotgun_fire")));

	public static final RegistryObject<SoundEvent> SHOTGUNRELOAD = MOD_SOUNDS.register("doom.shotgunreload",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.shotgunreload")));

	public static final RegistryObject<SoundEvent> SUPER_SHOTGUN_SHOOT = MOD_SOUNDS.register("doom.super_shotgun_fire",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.super_shotgun_fire")));

	public static final RegistryObject<SoundEvent> UNMAKYR_FIRE = MOD_SOUNDS.register("doom.unmakyr_fire",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.unmakyr_fire")));

	public static final RegistryObject<SoundEvent> LOADING_END = MOD_SOUNDS.register("doom.loading_end",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.loading_end")));

	public static final RegistryObject<SoundEvent> QUICK1_1 = MOD_SOUNDS.register("doom.quick1_1",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.quick1_1")));

	public static final RegistryObject<SoundEvent> E1M1 = MOD_SOUNDS.register("doom.e1m1",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.e1m1")));

	public static final RegistryObject<SoundEvent> IMP_AMBIENT = MOD_SOUNDS.register("doom.imp_ambient",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.imp_ambient")));
	public static final RegistryObject<SoundEvent> IMP_DEATH = MOD_SOUNDS.register("doom.imp_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.imp_death")));
	public static final RegistryObject<SoundEvent> IMP_HURT = MOD_SOUNDS.register("doom.imp_hurt",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.imp_hurt")));
	public static final RegistryObject<SoundEvent> IMP_STEP = MOD_SOUNDS.register("doom.imp_step",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.imp_step")));

	public static final RegistryObject<SoundEvent> ARCHVILE_DEATH = MOD_SOUNDS.register("doom.arch_vile_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.arch_vile_death")));
	public static final RegistryObject<SoundEvent> ARCHVILE_HURT = MOD_SOUNDS.register("doom.arch_vile_hit",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.arch_vile_hit")));
	public static final RegistryObject<SoundEvent> ARCHVILE_AMBIENT = MOD_SOUNDS.register("doom.arch_vile_idle",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.arch_vile_idle")));
	public static final RegistryObject<SoundEvent> ARCHVILE_PORTAL = MOD_SOUNDS.register("doom.arch_vile_portal",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.arch_vile_portal")));
	public static final RegistryObject<SoundEvent> ARCHVILE_SCREAM = MOD_SOUNDS.register("doom.arch_vile_scream",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.arch_vile_scream")));
	public static final RegistryObject<SoundEvent> ARCHVILE_STARE = MOD_SOUNDS.register("doom.arch_vile_stare",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.arch_vile_stare")));

	public static final RegistryObject<SoundEvent> BARON_AMBIENT = MOD_SOUNDS.register("doom.baron_angry",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.baron_angry")));
	public static final RegistryObject<SoundEvent> BARON_DEATH = MOD_SOUNDS.register("doom.baron_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.baron_death")));
	public static final RegistryObject<SoundEvent> BARON_HURT = MOD_SOUNDS.register("doom.baron_hurt",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.baron_hurt")));
	public static final RegistryObject<SoundEvent> BARON_STEP = MOD_SOUNDS.register("doom.baron_say",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.baron_say")));

	public static final RegistryObject<SoundEvent> PINKY_AMBIENT = MOD_SOUNDS.register("doom.pinky_idle",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.pinky_idle")));
	public static final RegistryObject<SoundEvent> PINKY_DEATH = MOD_SOUNDS.register("doom.pinky_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.pinky_death")));
	public static final RegistryObject<SoundEvent> PINKY_HURT = MOD_SOUNDS.register("doom.pinky_hurt",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.pinky_hurt")));
	public static final RegistryObject<SoundEvent> PINKY_STEP = MOD_SOUNDS.register("doom.pinky_step",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.pinky_step")));

	public static final RegistryObject<SoundEvent> LOST_SOUL_DEATH = MOD_SOUNDS.register("doom.lost_soul_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.lost_soul_death")));
	public static final RegistryObject<SoundEvent> LOST_SOUL_AMBIENT = MOD_SOUNDS.register("doom.lost_soul_say",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.lost_soul_say")));

	public static final RegistryObject<SoundEvent> CACODEMON_AMBIENT = MOD_SOUNDS.register("doom.cacodemon_moan",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.cacodemon_moan")));
	public static final RegistryObject<SoundEvent> CACODEMON_DEATH = MOD_SOUNDS.register("doom.cacodemon_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.cacodemon_death")));
	public static final RegistryObject<SoundEvent> CACODEMON_HURT = MOD_SOUNDS.register("doom.cacodemon_hit",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.cacodemon_hit")));
	public static final RegistryObject<SoundEvent> CACODEMON_AFFECTIONATE_SCREAM = MOD_SOUNDS.register(
			"doom.cacodemon_affectionate_scream",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.cacodemon_affectionate_scream")));

	public static final RegistryObject<SoundEvent> SPIDERDEMON_AMBIENT = MOD_SOUNDS.register("doom.spiderdemon_step",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.spiderdemon_step")));
	public static final RegistryObject<SoundEvent> SPIDERDEMON_DEATH = MOD_SOUNDS.register("doom.spiderdemon_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.cacodemon_death")));
	public static final RegistryObject<SoundEvent> SPIDERDEMON_HURT = MOD_SOUNDS.register("doom.spiderdemon_say",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.spiderdemon_say")));

	public static final RegistryObject<SoundEvent> ZOMBIEMAN_AMBIENT = MOD_SOUNDS.register("doom.zombieman_idle",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.zombieman_idle")));
	public static final RegistryObject<SoundEvent> ZOMBIEMAN_DEATH = MOD_SOUNDS.register("doom.zombieman_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.zombieman_death")));
	public static final RegistryObject<SoundEvent> ZOMBIEMAN_HURT = MOD_SOUNDS.register("doom.zombieman_hurt",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.zombieman_hurt")));

	public static final RegistryObject<SoundEvent> CYBERDEMON_AMBIENT = MOD_SOUNDS.register("doom.cyberdemon_throw",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.cyberdemon_throw")));
	public static final RegistryObject<SoundEvent> CYBERDEMON_DEATH = MOD_SOUNDS.register("doom.cyberdemon_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.cyberdemon_death")));
	public static final RegistryObject<SoundEvent> CYBERDEMON_HURT = MOD_SOUNDS.register("doom.cyberdemon_hit",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.cyberdemon_hit")));
	public static final RegistryObject<SoundEvent> CYBERDEMON_STEP = MOD_SOUNDS.register("doom.cyberdemon_walk",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.cyberdemon_walk")));

	public static final RegistryObject<SoundEvent> MANCUBUS_AMBIENT = MOD_SOUNDS.register("doom.mancubus_say",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.mancubus_say")));
	public static final RegistryObject<SoundEvent> MANCUBUS_DEATH = MOD_SOUNDS.register("doom.mancubus_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.mancubus_death")));
	public static final RegistryObject<SoundEvent> MANCUBUS_HURT = MOD_SOUNDS.register("doom.mancubus_hit",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.mancubus_hit")));
	public static final RegistryObject<SoundEvent> MANCUBUS_STEP = MOD_SOUNDS.register("doom.mancubus_walk",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.mancubus_walk")));

	public static final RegistryObject<SoundEvent> REVENANT_AMBIENT = MOD_SOUNDS.register("doom.revenant_say",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.revenant_say")));
	public static final RegistryObject<SoundEvent> REVENANT_DEATH = MOD_SOUNDS.register("doom.revenant_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.revenant_death")));
	public static final RegistryObject<SoundEvent> REVENANT_HURT = MOD_SOUNDS.register("doom.revenant_hurt",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.revenant_hurt")));
	public static final RegistryObject<SoundEvent> REVENANT_ATTACK = MOD_SOUNDS.register("doom.revenant_attack",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.revenant_attack")));
	public static final RegistryObject<SoundEvent> REVENANT_DOOT = MOD_SOUNDS.register("doom.revenant_doot",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.revenant_doot")));

	public static final RegistryObject<SoundEvent> HELLKNIGHT_AMBIENT = MOD_SOUNDS.register("doom.hellknight_say",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.hellknight_say")));
	public static final RegistryObject<SoundEvent> HELLKNIGHT_DEATH = MOD_SOUNDS.register("doom.hellknight_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.hellknight_death")));
	public static final RegistryObject<SoundEvent> HELLKNIGHT_HURT = MOD_SOUNDS.register("doom.hellknight_hurt",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.hellknight_hurt")));

	public static final RegistryObject<SoundEvent> PAIN_AMBIENT = MOD_SOUNDS.register("doom.pain_say",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.pain_say")));
	public static final RegistryObject<SoundEvent> PAIN_DEATH = MOD_SOUNDS.register("doom.pain_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.pain_death")));
	public static final RegistryObject<SoundEvent> PAIN_HURT = MOD_SOUNDS.register("doom.pain_hurt",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.pain_hurt")));

	public static final RegistryObject<SoundEvent> ICON_AMBIENT = MOD_SOUNDS.register("doom.icon_ambient",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.icon_ambient")));
	public static final RegistryObject<SoundEvent> ICON_DEATH = MOD_SOUNDS.register("doom.icon_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.icon_death")));
	public static final RegistryObject<SoundEvent> ICON_HURT = MOD_SOUNDS.register("doom.icon_hurt",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.icon_hurt")));

	public static final RegistryObject<SoundEvent> ARACHNOTRON_AMBIENT = MOD_SOUNDS.register("doom.arachnotron_idle",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.arachnotron_idle")));
	public static final RegistryObject<SoundEvent> ARACHNOTRON_DEATH = MOD_SOUNDS.register("doom.arachnotron_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.arachnotron_death")));
	public static final RegistryObject<SoundEvent> ARACHNOTRON_HURT = MOD_SOUNDS.register("doom.arachnotron_hurt",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.arachnotron_hurt")));

	public static final RegistryObject<SoundEvent> BALLISTA_FIRING = MOD_SOUNDS.register("doom.ballista_firing",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.ballista_firing")));

	public static final RegistryObject<SoundEvent> PSOLDIER_AMBIENT = MOD_SOUNDS.register("doom.psoldier_idle",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.psoldier_idle")));
	public static final RegistryObject<SoundEvent> PSOLDIER_DEATH = MOD_SOUNDS.register("doom.psoldier_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.psoldier_death")));
	public static final RegistryObject<SoundEvent> PSOLDIER_HURT = MOD_SOUNDS.register("doom.psoldier_hit",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.psoldier_hit")));

	public static final RegistryObject<SoundEvent> GARGOLYE_AMBIENT = MOD_SOUNDS.register("doom.gargolye_idle",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.gargolye_idle")));
	public static final RegistryObject<SoundEvent> GARGOLYE_DEATH = MOD_SOUNDS.register("doom.gargolye_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.gargolye_death")));
	public static final RegistryObject<SoundEvent> GARGOLYE_HURT = MOD_SOUNDS.register("doom.gargolye_hit",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.gargolye_hit")));

	public static final RegistryObject<SoundEvent> MECHA_AMBIENT = MOD_SOUNDS.register("doom.mecha_idle",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.mecha_idle")));
	public static final RegistryObject<SoundEvent> MECHA_DEATH = MOD_SOUNDS.register("doom.mecha_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.mecha_death")));
	public static final RegistryObject<SoundEvent> MECHA_HURT = MOD_SOUNDS.register("doom.mecha_hit",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.mecha_hit")));

	public static final RegistryObject<SoundEvent> WHIPLASH_AMBIENT = MOD_SOUNDS.register("doom.whiplash_ambient",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.whiplash_ambient")));
	public static final RegistryObject<SoundEvent> WHIPLASH_DEATH = MOD_SOUNDS.register("doom.whiplash_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.whiplash_death")));
	public static final RegistryObject<SoundEvent> WHIPLASH_HURT = MOD_SOUNDS.register("doom.whiplash_hurt",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.whiplash_hurt")));

	public static final RegistryObject<SoundEvent> DOOMHUNTER_AMBIENT = MOD_SOUNDS.register("doom.doomhunter_ambient",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.doomhunter_ambient")));
	public static final RegistryObject<SoundEvent> DOOMHUNTER_DEATH = MOD_SOUNDS.register("doom.doomhunter_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.doomhunter_death")));
	public static final RegistryObject<SoundEvent> DOOMHUNTER_HURT = MOD_SOUNDS.register("doom.doomhunter_hurt",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.doomhunter_hurt")));

	public static final RegistryObject<SoundEvent> MAKYR_AMBIENT = MOD_SOUNDS.register("doom.maykr_ambient",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.maykr_ambient")));
	public static final RegistryObject<SoundEvent> MAKYR_DEATH = MOD_SOUNDS.register("doom.maykr_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.maykr_death")));
	public static final RegistryObject<SoundEvent> MAKYR_HURT = MOD_SOUNDS.register("doom.maykr_hurt",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.maykr_hurt")));

	public static final RegistryObject<SoundEvent> MOTHER_AMBIENT = MOD_SOUNDS.register("doom.mother_ambient",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.mother_ambient")));
	public static final RegistryObject<SoundEvent> MOTHER_DEATH = MOD_SOUNDS.register("doom.mother_death",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.mother_death")));
	public static final RegistryObject<SoundEvent> MOTHER_ATTACK = MOD_SOUNDS.register("doom.mother_attack",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.mother_attack")));
	public static final RegistryObject<SoundEvent> MOTHER_HURT = MOD_SOUNDS.register("doom.mother_pain",
			() -> new SoundEvent(new ResourceLocation(DoomMod.MODID, "doom.mother_pain")));
}