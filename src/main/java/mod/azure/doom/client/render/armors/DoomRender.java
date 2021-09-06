package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.DoomModel;
import mod.azure.doom.item.armor.DoomArmor;
import software.bernie.geckolib3.renderer.geo.GeoArmorRenderer;

public class DoomRender extends GeoArmorRenderer<DoomArmor> {
	public DoomRender() {
		super(new DoomModel());

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