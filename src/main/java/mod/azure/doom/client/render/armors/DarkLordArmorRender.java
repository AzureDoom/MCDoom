package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.DarkLordModel;
import mod.azure.doom.item.armor.DarkLordArmor;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class DarkLordArmorRender extends GeoArmorRenderer<DarkLordArmor> {
	public DarkLordArmorRender() {
		super(new DarkLordModel());

		this.headBone = "armorHead";
		this.bodyBone = "armorBody";
		this.rightArmBone = "armorRightArm";
		this.leftArmBone = "armorLeftArm";
		this.leftLegBone = "armorLeftLeg";
		this.rightLegBone = "armorRightLeg";
		this.leftBootBone = "armorLeftBoot";
		this.rightBootBone = "armorRightBoot";
	}
}