package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.MaykrModel;
import mod.azure.doom.item.armor.MaykrDoomArmor;
import software.bernie.geckolib3.renderer.geo.GeoArmorRenderer;

public class MaykrRender extends GeoArmorRenderer<MaykrDoomArmor> {
	public MaykrRender() {
		super(new MaykrModel());

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