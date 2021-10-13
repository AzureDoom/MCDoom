package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.HotrodModel;
import mod.azure.doom.item.armor.HotrodDoomArmor;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class HotrodRender extends GeoArmorRenderer<HotrodDoomArmor> {
	public HotrodRender() {
		super(new HotrodModel());

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