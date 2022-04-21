package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.CultistModel;
import mod.azure.doom.item.armor.CultistDoomArmor;
import software.bernie.geckolib3q.renderers.geo.GeoArmorRenderer;

public class CultistRender extends GeoArmorRenderer<CultistDoomArmor> {
	public CultistRender() {
		super(new CultistModel());

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