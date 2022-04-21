package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.CrimsonModel;
import mod.azure.doom.item.armor.CrimsonDoomArmor;
import software.bernie.geckolib3q.renderers.geo.GeoArmorRenderer;

public class CrimsonRender extends GeoArmorRenderer<CrimsonDoomArmor> {
	public CrimsonRender() {
		super(new CrimsonModel());

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