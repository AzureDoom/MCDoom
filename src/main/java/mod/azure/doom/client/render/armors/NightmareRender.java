package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.NightmareModel;
import mod.azure.doom.item.armor.NightmareDoomArmor;
import software.bernie.geckolib3q.renderers.geo.GeoArmorRenderer;

public class NightmareRender extends GeoArmorRenderer<NightmareDoomArmor> {
	public NightmareRender() {
		super(new NightmareModel());

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