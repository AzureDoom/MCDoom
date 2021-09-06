package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.ClassicRedModel;
import mod.azure.doom.item.armor.ClassicRedDoomArmor;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class ClassicRedRender extends GeoArmorRenderer<ClassicRedDoomArmor> {
	public ClassicRedRender() {
		super(new ClassicRedModel());

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