package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.DemonicModel;
import mod.azure.doom.item.armor.DemonicDoomArmor;
import software.bernie.geckolib3q.renderers.geo.GeoArmorRenderer;

public class DemonicRender extends GeoArmorRenderer<DemonicDoomArmor> {
	public DemonicRender() {
		super(new DemonicModel());

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