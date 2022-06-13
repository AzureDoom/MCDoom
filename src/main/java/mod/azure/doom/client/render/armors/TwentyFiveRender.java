package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.TwentyFiveModel;
import mod.azure.doom.item.armor.TwentyFiveDoomArmor;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class TwentyFiveRender extends GeoArmorRenderer<TwentyFiveDoomArmor> {
	public TwentyFiveRender() {
		super(new TwentyFiveModel());

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