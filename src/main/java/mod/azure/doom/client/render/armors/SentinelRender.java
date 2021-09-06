package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.SentinelModel;
import mod.azure.doom.item.armor.SentinelDoomArmor;
import software.bernie.geckolib3.renderer.geo.GeoArmorRenderer;

public class SentinelRender extends GeoArmorRenderer<SentinelDoomArmor> {
	public SentinelRender() {
		super(new SentinelModel());

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