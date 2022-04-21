package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.ZombieModel;
import mod.azure.doom.item.armor.ZombieDoomArmor;
import software.bernie.geckolib3q.renderers.geo.GeoArmorRenderer;

public class ZombieRender extends GeoArmorRenderer<ZombieDoomArmor> {
	public ZombieRender() {
		super(new ZombieModel());

		this.headBone = "armorHead";
		this.bodyBone = "armorBody";
		this.rightArmBone = "armorRightArm";
		this.leftArmBone = "armorLeftArm";
		this.rightLegBone = "armorLeftLeg";
		this.leftLegBone = "armorRightLeg";
		this.rightBootBone = "armorLeftBoot";
		this.leftBootBone = "armorRightBoot";
	}
}