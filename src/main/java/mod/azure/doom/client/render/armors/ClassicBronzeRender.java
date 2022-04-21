package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.ClassicBronzeModel;
import mod.azure.doom.item.armor.ClassicBronzeDoomArmor;
import software.bernie.geckolib3q.renderers.geo.GeoArmorRenderer;

public class ClassicBronzeRender extends GeoArmorRenderer<ClassicBronzeDoomArmor> {
	public ClassicBronzeRender() {
		super(new ClassicBronzeModel());

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