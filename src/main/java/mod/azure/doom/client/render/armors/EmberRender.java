package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.EmberModel;
import mod.azure.doom.item.armor.EmberDoomArmor;
import software.bernie.geckolib3.renderer.geo.GeoArmorRenderer;

public class EmberRender extends GeoArmorRenderer<EmberDoomArmor> {
	public EmberRender() {
		super(new EmberModel());

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