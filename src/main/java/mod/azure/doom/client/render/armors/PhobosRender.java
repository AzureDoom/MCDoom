package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.PhobosModel;
import mod.azure.doom.item.armor.PhobosDoomArmor;
import software.bernie.geckolib3.renderer.geo.GeoArmorRenderer;

public class PhobosRender extends GeoArmorRenderer<PhobosDoomArmor> {
	public PhobosRender() {
		super(new PhobosModel());

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