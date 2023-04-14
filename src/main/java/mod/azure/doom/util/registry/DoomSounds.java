package mod.azure.doom.util.registry;

import mod.azure.doom.DoomMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DoomSounds {

	public static final DeferredRegister<SoundEvent> MOD_SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DoomMod.MODID);

	public static final RegistryObject<SoundEvent> EMPTY = MOD_SOUNDS.register("doom.emptyclip", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.emptyclip")));
	public static final RegistryObject<SoundEvent> BEEP = MOD_SOUNDS.register("doom.grenadeabouttoexplode", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.grenadeabouttoexplode")));

	public static final RegistryObject<SoundEvent> BFG_FIRING = MOD_SOUNDS.register("doom.bfg_firing", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.bfg_firing")));
	public static final RegistryObject<SoundEvent> BFG_HIT = MOD_SOUNDS.register("doom.bfg_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.bfg_hit")));

	public static final RegistryObject<SoundEvent> PLASMA_FIRING = MOD_SOUNDS.register("doom.plasmafire", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.plasmafire")));
	public static final RegistryObject<SoundEvent> PLASMA_HIT = MOD_SOUNDS.register("doom.plasmahit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.plasmahit")));

	public static final RegistryObject<SoundEvent> HEAVY_CANNON = MOD_SOUNDS.register("doom.heavy_cannon", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.heavy_cannon")));

	public static final RegistryObject<SoundEvent> CHAINSAW_ATTACKING = MOD_SOUNDS.register("doom.chainsaw_attacking", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.chainsaw_attacking")));
	public static final RegistryObject<SoundEvent> CHAINSAW_IDLE = MOD_SOUNDS.register("doom.chainsaw_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.chainsaw_idle")));

	public static final RegistryObject<SoundEvent> ROCKET_FIRING = MOD_SOUNDS.register("doom.rocketfire", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.rocketfire")));
	public static final RegistryObject<SoundEvent> ROCKET_HIT = MOD_SOUNDS.register("doom.rockethit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.rockethit")));

	public static final RegistryObject<SoundEvent> SHOOT1 = MOD_SOUNDS.register("doom.shoot1", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.shoot1")));

	public static final RegistryObject<SoundEvent> CHAINGUN_SHOOT = MOD_SOUNDS.register("doom.chaingun_fire", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.chaingun_fire")));

	public static final RegistryObject<SoundEvent> PISTOL_HIT = MOD_SOUNDS.register("doom.pistol_fire", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.pistol_fire")));

	public static final RegistryObject<SoundEvent> CLIPRELOAD = MOD_SOUNDS.register("doom.clipreload", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.clipreload")));

	public static final RegistryObject<SoundEvent> SHOTGUN_SHOOT = MOD_SOUNDS.register("doom.shotgun_fire", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.shotgun_fire")));

	public static final RegistryObject<SoundEvent> SHOTGUNRELOAD = MOD_SOUNDS.register("doom.shotgunreload", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.shotgunreload")));

	public static final RegistryObject<SoundEvent> SUPER_SHOTGUN_SHOOT = MOD_SOUNDS.register("doom.super_shotgun_fire", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.super_shotgun_fire")));

	public static final RegistryObject<SoundEvent> UNMAKYR_FIRE = MOD_SOUNDS.register("doom.unmakyr_fire", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.unmakyr_fire")));

	public static final RegistryObject<SoundEvent> LOADING_END = MOD_SOUNDS.register("doom.loading_end", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.loading_end")));

	public static final RegistryObject<SoundEvent> QUICK1_1 = MOD_SOUNDS.register("doom.quick1_1", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.quick1_1")));

	public static final RegistryObject<SoundEvent> CRUCIBLE_LEFT = MOD_SOUNDS.register("crucible_left", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "crucible_left")));
	public static final RegistryObject<SoundEvent> CRUCIBLE_RIGHT = MOD_SOUNDS.register("crucible_right", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "crucible_right")));
	public static final RegistryObject<SoundEvent> CRUCIBLE_STAB = MOD_SOUNDS.register("crucible_stab", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "crucible_stab")));

	public static final RegistryObject<SoundEvent> CRUCIBLE_HAMMER = MOD_SOUNDS.register("crucible_hammer", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "crucible_hammer")));
	public static final RegistryObject<SoundEvent> CRUCIBLE_AXE_RIGHT = MOD_SOUNDS.register("crucible_axe_right", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "crucible_axe_right")));
	public static final RegistryObject<SoundEvent> CRUCIBLE_AXE_LEFT = MOD_SOUNDS.register("crucible_axe_left", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "crucible_axe_left")));

	public static final RegistryObject<SoundEvent> E1M1 = MOD_SOUNDS.register("doom.e1m1", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.e1m1")));
	public static final RegistryObject<SoundEvent> NETHERAMBIENT_GEOFFPLAYSGUITAR = MOD_SOUNDS.register("doom.netherambient_geoffplaysguitar", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.netherambient_geoffplaysguitar")));

	public static final RegistryObject<SoundEvent> IMP_AMBIENT = MOD_SOUNDS.register("doom.imp_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.imp_ambient")));
	public static final RegistryObject<SoundEvent> IMP_DEATH = MOD_SOUNDS.register("doom.imp_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.imp_death")));
	public static final RegistryObject<SoundEvent> IMP_HURT = MOD_SOUNDS.register("doom.imp_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.imp_hurt")));
	public static final RegistryObject<SoundEvent> IMP_STEP = MOD_SOUNDS.register("doom.imp_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.imp_step")));

	public static final RegistryObject<SoundEvent> ARCHVILE_DEATH = MOD_SOUNDS.register("doom.arch_vile_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.arch_vile_death")));
	public static final RegistryObject<SoundEvent> ARCHVILE_HURT = MOD_SOUNDS.register("doom.arch_vile_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.arch_vile_hit")));
	public static final RegistryObject<SoundEvent> ARCHVILE_AMBIENT = MOD_SOUNDS.register("doom.arch_vile_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.arch_vile_idle")));
	public static final RegistryObject<SoundEvent> ARCHVILE_PORTAL = MOD_SOUNDS.register("doom.arch_vile_portal", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.arch_vile_portal")));
	public static final RegistryObject<SoundEvent> ARCHVILE_SCREAM = MOD_SOUNDS.register("doom.arch_vile_scream", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.arch_vile_scream")));
	public static final RegistryObject<SoundEvent> ARCHVILE_STARE = MOD_SOUNDS.register("doom.arch_vile_stare", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.arch_vile_stare")));

	public static final RegistryObject<SoundEvent> BARON_AMBIENT = MOD_SOUNDS.register("doom.baron_angry", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.baron_angry")));
	public static final RegistryObject<SoundEvent> BARON_DEATH = MOD_SOUNDS.register("doom.baron_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.baron_death")));
	public static final RegistryObject<SoundEvent> BARON_HURT = MOD_SOUNDS.register("doom.baron_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.baron_hurt")));
	public static final RegistryObject<SoundEvent> BARON_STEP = MOD_SOUNDS.register("doom.baron_say", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.baron_say")));

	public static final RegistryObject<SoundEvent> PINKY_AMBIENT = MOD_SOUNDS.register("doom.pinky_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.pinky_idle")));
	public static final RegistryObject<SoundEvent> PINKY_DEATH = MOD_SOUNDS.register("doom.pinky_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.pinky_death")));
	public static final RegistryObject<SoundEvent> PINKY_HURT = MOD_SOUNDS.register("doom.pinky_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.pinky_hurt")));
	public static final RegistryObject<SoundEvent> PINKY_STEP = MOD_SOUNDS.register("doom.pinky_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.pinky_step")));
	public static final RegistryObject<SoundEvent> PINKY_YELL = MOD_SOUNDS.register("doom.pinky_yell", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.pinky_yell")));

	public static final RegistryObject<SoundEvent> LOST_SOUL_DEATH = MOD_SOUNDS.register("doom.lost_soul_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.lost_soul_death")));
	public static final RegistryObject<SoundEvent> LOST_SOUL_AMBIENT = MOD_SOUNDS.register("doom.lost_soul_say", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.lost_soul_say")));

	public static final RegistryObject<SoundEvent> CACODEMON_AMBIENT = MOD_SOUNDS.register("doom.cacodemon_moan", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.cacodemon_moan")));
	public static final RegistryObject<SoundEvent> CACODEMON_DEATH = MOD_SOUNDS.register("doom.cacodemon_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.cacodemon_death")));
	public static final RegistryObject<SoundEvent> CACODEMON_HURT = MOD_SOUNDS.register("doom.cacodemon_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.cacodemon_hit")));
	public static final RegistryObject<SoundEvent> CACODEMON_AFFECTIONATE_SCREAM = MOD_SOUNDS.register("doom.cacodemon_affectionate_scream", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.cacodemon_affectionate_scream")));

	public static final RegistryObject<SoundEvent> SPIDERDEMON_AMBIENT = MOD_SOUNDS.register("doom.spiderdemon_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.spiderdemon_step")));
	public static final RegistryObject<SoundEvent> SPIDERDEMON_DEATH = MOD_SOUNDS.register("doom.spiderdemon_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.cacodemon_death")));
	public static final RegistryObject<SoundEvent> SPIDERDEMON_HURT = MOD_SOUNDS.register("doom.spiderdemon_say", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.spiderdemon_say")));

	public static final RegistryObject<SoundEvent> ZOMBIEMAN_AMBIENT = MOD_SOUNDS.register("doom.zombieman_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.zombieman_idle")));
	public static final RegistryObject<SoundEvent> ZOMBIEMAN_DEATH = MOD_SOUNDS.register("doom.zombieman_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.zombieman_death")));
	public static final RegistryObject<SoundEvent> ZOMBIEMAN_HURT = MOD_SOUNDS.register("doom.zombieman_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.zombieman_hurt")));

	public static final RegistryObject<SoundEvent> CYBERDEMON_AMBIENT = MOD_SOUNDS.register("doom.cyberdemon_throw", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.cyberdemon_throw")));
	public static final RegistryObject<SoundEvent> CYBERDEMON_DEATH = MOD_SOUNDS.register("doom.cyberdemon_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.cyberdemon_death")));
	public static final RegistryObject<SoundEvent> CYBERDEMON_HURT = MOD_SOUNDS.register("doom.cyberdemon_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.cyberdemon_hit")));
	public static final RegistryObject<SoundEvent> CYBERDEMON_STEP = MOD_SOUNDS.register("doom.cyberdemon_walk", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.cyberdemon_walk")));

	public static final RegistryObject<SoundEvent> MANCUBUS_AMBIENT = MOD_SOUNDS.register("doom.mancubus_say", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.mancubus_say")));
	public static final RegistryObject<SoundEvent> MANCUBUS_DEATH = MOD_SOUNDS.register("doom.mancubus_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.mancubus_death")));
	public static final RegistryObject<SoundEvent> MANCUBUS_HURT = MOD_SOUNDS.register("doom.mancubus_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.mancubus_hit")));
	public static final RegistryObject<SoundEvent> MANCUBUS_STEP = MOD_SOUNDS.register("doom.mancubus_walk", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.mancubus_walk")));

	public static final RegistryObject<SoundEvent> REVENANT_AMBIENT = MOD_SOUNDS.register("doom.revenant_say", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.revenant_say")));
	public static final RegistryObject<SoundEvent> REVENANT_DEATH = MOD_SOUNDS.register("doom.revenant_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.revenant_death")));
	public static final RegistryObject<SoundEvent> REVENANT_HURT = MOD_SOUNDS.register("doom.revenant_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.revenant_hurt")));
	public static final RegistryObject<SoundEvent> REVENANT_ATTACK = MOD_SOUNDS.register("doom.revenant_attack", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.revenant_attack")));
	public static final RegistryObject<SoundEvent> REVENANT_DOOT = MOD_SOUNDS.register("doom.revenant_doot", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.revenant_doot")));

	public static final RegistryObject<SoundEvent> HELLKNIGHT_AMBIENT = MOD_SOUNDS.register("doom.hellknight_say", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.hellknight_say")));
	public static final RegistryObject<SoundEvent> HELLKNIGHT_DEATH = MOD_SOUNDS.register("doom.hellknight_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.hellknight_death")));
	public static final RegistryObject<SoundEvent> HELLKNIGHT_HURT = MOD_SOUNDS.register("doom.hellknight_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.hellknight_hurt")));

	public static final RegistryObject<SoundEvent> PAIN_AMBIENT = MOD_SOUNDS.register("doom.pain_say", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.pain_say")));
	public static final RegistryObject<SoundEvent> PAIN_DEATH = MOD_SOUNDS.register("doom.pain_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.pain_death")));
	public static final RegistryObject<SoundEvent> PAIN_HURT = MOD_SOUNDS.register("doom.pain_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.pain_hurt")));

	public static final RegistryObject<SoundEvent> ICON_AMBIENT = MOD_SOUNDS.register("doom.icon_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.icon_ambient")));
	public static final RegistryObject<SoundEvent> ICON_DEATH = MOD_SOUNDS.register("doom.icon_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.icon_death")));
	public static final RegistryObject<SoundEvent> ICON_HURT = MOD_SOUNDS.register("doom.icon_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.icon_hurt")));

	public static final RegistryObject<SoundEvent> ARACHNOTRON_AMBIENT = MOD_SOUNDS.register("doom.arachnotron_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.arachnotron_idle")));
	public static final RegistryObject<SoundEvent> ARACHNOTRON_DEATH = MOD_SOUNDS.register("doom.arachnotron_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.arachnotron_death")));
	public static final RegistryObject<SoundEvent> ARACHNOTRON_HURT = MOD_SOUNDS.register("doom.arachnotron_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.arachnotron_hurt")));

	public static final RegistryObject<SoundEvent> BALLISTA_FIRING = MOD_SOUNDS.register("doom.ballista_firing", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.ballista_firing")));

	public static final RegistryObject<SoundEvent> PSOLDIER_AMBIENT = MOD_SOUNDS.register("doom.psoldier_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.psoldier_idle")));
	public static final RegistryObject<SoundEvent> PSOLDIER_DEATH = MOD_SOUNDS.register("doom.psoldier_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.psoldier_death")));
	public static final RegistryObject<SoundEvent> PSOLDIER_HURT = MOD_SOUNDS.register("doom.psoldier_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.psoldier_hit")));

	public static final RegistryObject<SoundEvent> GARGOLYE_AMBIENT = MOD_SOUNDS.register("doom.gargolye_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.gargolye_idle")));
	public static final RegistryObject<SoundEvent> GARGOLYE_DEATH = MOD_SOUNDS.register("doom.gargolye_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.gargolye_death")));
	public static final RegistryObject<SoundEvent> GARGOLYE_HURT = MOD_SOUNDS.register("doom.gargolye_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.gargolye_hit")));

	public static final RegistryObject<SoundEvent> MECHA_AMBIENT = MOD_SOUNDS.register("doom.mecha_idle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.mecha_idle")));
	public static final RegistryObject<SoundEvent> MECHA_DEATH = MOD_SOUNDS.register("doom.mecha_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.mecha_death")));
	public static final RegistryObject<SoundEvent> MECHA_HURT = MOD_SOUNDS.register("doom.mecha_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.mecha_hit")));

	public static final RegistryObject<SoundEvent> WHIPLASH_AMBIENT = MOD_SOUNDS.register("doom.whiplash_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.whiplash_ambient")));
	public static final RegistryObject<SoundEvent> WHIPLASH_DEATH = MOD_SOUNDS.register("doom.whiplash_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.whiplash_death")));
	public static final RegistryObject<SoundEvent> WHIPLASH_HURT = MOD_SOUNDS.register("doom.whiplash_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.whiplash_hurt")));

	public static final RegistryObject<SoundEvent> DOOMHUNTER_AMBIENT = MOD_SOUNDS.register("doom.doomhunter_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.doomhunter_ambient")));
	public static final RegistryObject<SoundEvent> DOOMHUNTER_DEATH = MOD_SOUNDS.register("doom.doomhunter_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.doomhunter_death")));
	public static final RegistryObject<SoundEvent> DOOMHUNTER_HURT = MOD_SOUNDS.register("doom.doomhunter_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.doomhunter_hurt")));
	public static final RegistryObject<SoundEvent> DOOMHUNTER_PHASECHANGE = MOD_SOUNDS.register("doom.doomhunter_phasechange", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.doomhunter_phasechange")));

	public static final RegistryObject<SoundEvent> MAKYR_AMBIENT = MOD_SOUNDS.register("doom.maykr_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.maykr_ambient")));
	public static final RegistryObject<SoundEvent> MAKYR_DEATH = MOD_SOUNDS.register("doom.maykr_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.maykr_death")));
	public static final RegistryObject<SoundEvent> MAKYR_HURT = MOD_SOUNDS.register("doom.maykr_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.maykr_hurt")));

	public static final RegistryObject<SoundEvent> MOTHER_AMBIENT = MOD_SOUNDS.register("doom.mother_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.mother_ambient")));
	public static final RegistryObject<SoundEvent> MOTHER_DEATH = MOD_SOUNDS.register("doom.mother_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.mother_death")));
	public static final RegistryObject<SoundEvent> MOTHER_ATTACK = MOD_SOUNDS.register("doom.mother_attack", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.mother_attack")));
	public static final RegistryObject<SoundEvent> MOTHER_HURT = MOD_SOUNDS.register("doom.mother_pain", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DoomMod.MODID, "doom.mother_pain")));
}