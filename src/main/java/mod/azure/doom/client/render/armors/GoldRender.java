package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.GoldModel;
import mod.azure.doom.item.armor.GoldDoomArmor;
import software.bernie.geckolib3.renderer.geo.GeoArmorRenderer;

public class GoldRender extends GeoArmorRenderer<GoldDoomArmor> {
	public GoldRender() {
		super(new GoldModel());

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