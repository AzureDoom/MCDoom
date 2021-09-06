package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.ClassicIndigoModel;
import mod.azure.doom.item.armor.ClassicIndigoDoomArmor;
import software.bernie.geckolib3.renderer.geo.GeoArmorRenderer;

public class ClassicIndigoRender extends GeoArmorRenderer<ClassicIndigoDoomArmor> {
	public ClassicIndigoRender() {
		super(new ClassicIndigoModel());

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