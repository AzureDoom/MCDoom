package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.BronzeModel;
import mod.azure.doom.item.armor.BronzeDoomArmor;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class BronzeRender extends GeoArmorRenderer<BronzeDoomArmor> {
	public BronzeRender() {
		super(new BronzeModel());

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