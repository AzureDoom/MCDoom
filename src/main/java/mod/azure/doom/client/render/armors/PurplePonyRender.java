package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.PurplePonyModel;
import mod.azure.doom.item.armor.PurplePonyDoomArmor;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class PurplePonyRender extends GeoArmorRenderer<PurplePonyDoomArmor> {
	public PurplePonyRender() {
		super(new PurplePonyModel());

		this.headBone = "armorHead";
		this.bodyBone = "armorBody";
		this.rightArmBone = "armorRightArm";
		this.leftArmBone = "armorLeftArm";
		this.rightLegBone = "armorRightLeg";
		this.leftLegBone = "armorLeftLeg";
		this.rightBootBone = "armorRightBoot";
		this.leftBootBone = "armorLeftBoot";
	}
}