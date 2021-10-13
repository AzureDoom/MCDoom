package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.MidnightModel;
import mod.azure.doom.item.armor.MidnightDoomArmor;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class MidnightRender extends GeoArmorRenderer<MidnightDoomArmor> {
	public MidnightRender() {
		super(new MidnightModel());

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