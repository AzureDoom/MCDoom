package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.DemoncideModel;
import mod.azure.doom.item.armor.DemoncideDoomArmor;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class DemoncideRender extends GeoArmorRenderer<DemoncideDoomArmor> {
	public DemoncideRender() {
		super(new DemoncideModel());

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