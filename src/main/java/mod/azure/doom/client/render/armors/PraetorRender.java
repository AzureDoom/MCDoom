package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.PraetorModel;
import mod.azure.doom.item.armor.PraetorDoomArmor;
import software.bernie.geckolib3.renderer.geo.GeoArmorRenderer;

public class PraetorRender extends GeoArmorRenderer<PraetorDoomArmor> {
	public PraetorRender() {
		super(new PraetorModel());

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