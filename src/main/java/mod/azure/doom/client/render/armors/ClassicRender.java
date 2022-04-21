package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.ClassicModel;
import mod.azure.doom.item.armor.ClassicDoomArmor;
import software.bernie.geckolib3q.renderers.geo.GeoArmorRenderer;

public class ClassicRender extends GeoArmorRenderer<ClassicDoomArmor> {
	public ClassicRender() {
		super(new ClassicModel());

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