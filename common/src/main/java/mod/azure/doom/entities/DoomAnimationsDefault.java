package mod.azure.doom.entities;

import mod.azure.azurelib.core.animation.Animation.LoopType;
import mod.azure.azurelib.core.animation.RawAnimation;

public record DoomAnimationsDefault() {

    public static final RawAnimation FLYING = RawAnimation.begin().thenLoop("flying");
    public static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    public static final RawAnimation SPAWN = RawAnimation.begin().then("spawn", LoopType.PLAY_ONCE);
    public static final RawAnimation IDLE_NOHELMET = RawAnimation.begin().then("idle_nohelmet", LoopType.PLAY_ONCE);

    public static final RawAnimation RUN = RawAnimation.begin().thenLoop("run");
    public static final RawAnimation MOVING = RawAnimation.begin().thenLoop("moving");
    public static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    public static final RawAnimation WALK_HOSTILE = RawAnimation.begin().thenLoop("walk_hostile");
    public static final RawAnimation WALKING = RawAnimation.begin().thenLoop("walking");
    public static final RawAnimation WALKING_NOHELMET = RawAnimation.begin().then("walking_nohelmet",
            LoopType.PLAY_ONCE);

    public static final RawAnimation RANGED = RawAnimation.begin().then("ranged", LoopType.PLAY_ONCE);
    public static final RawAnimation FIRE = RawAnimation.begin().then("fire", LoopType.PLAY_ONCE);
    public static final RawAnimation ATTACK = RawAnimation.begin().then("attack", LoopType.PLAY_ONCE);
    public static final RawAnimation ATTACKING = RawAnimation.begin().then("attacking", LoopType.PLAY_ONCE);
    public static final RawAnimation ATTACKING_AOE = RawAnimation.begin().then("attacking_aoe", LoopType.PLAY_ONCE);
    public static final RawAnimation SUMMONED = RawAnimation.begin().then("summoned", LoopType.PLAY_ONCE);
    public static final RawAnimation SUMMONED_NOHELMET = RawAnimation.begin().then("summoned_nohelmet",
            LoopType.PLAY_ONCE);
    public static final RawAnimation STOMP = RawAnimation.begin().then("stomp", LoopType.PLAY_ONCE);
    public static final RawAnimation STOMP_NOHELMET = RawAnimation.begin().then("stomp_nohelmet", LoopType.PLAY_ONCE);
    public static final RawAnimation GROUND = RawAnimation.begin().then("ground", LoopType.PLAY_ONCE);
    public static final RawAnimation MELEE = RawAnimation.begin().then("melee", LoopType.PLAY_ONCE);
    public static final RawAnimation CHIANSAW = RawAnimation.begin().then("chainsaw", LoopType.PLAY_ONCE);
    public static final RawAnimation ROCKETS = RawAnimation.begin().then("rockets", LoopType.PLAY_ONCE);
    public static final RawAnimation FLAMETHROWER = RawAnimation.begin().then("flamethrower", LoopType.PLAY_ONCE);
    public static final RawAnimation SHIELD = RawAnimation.begin().then("shield_plant", LoopType.PLAY_ONCE);
    public static final RawAnimation MELEE_PHASEONE = RawAnimation.begin().then("melee_phaseone", LoopType.PLAY_ONCE);
    public static final RawAnimation MELEE_PHASEONETWO = RawAnimation.begin().then("melee_phaseone2",
            LoopType.PLAY_ONCE);
    public static final RawAnimation MELEE_PHASEONETHREE = RawAnimation.begin().then("melee_phaseone3",
            LoopType.PLAY_ONCE);
    public static final RawAnimation MELEE_PHASETWOONE = RawAnimation.begin().then("melee_phasetwo",
            LoopType.PLAY_ONCE);
    public static final RawAnimation MELEE_PHASETWOTWO = RawAnimation.begin().then("melee_phasetwo2",
            LoopType.PLAY_ONCE);
    public static final RawAnimation MELEE_PHASETWOTHREE = RawAnimation.begin().then("melee_phasetwo2",
            LoopType.PLAY_ONCE);

    public static final RawAnimation DEATH = RawAnimation.begin().thenPlayAndHold("death");
    public static final RawAnimation DEATH2 = RawAnimation.begin().thenPlayAndHold("death2");
    public static final RawAnimation DEATH_PHASEONE = RawAnimation.begin().thenPlayAndHold("death_phaseone");
}
