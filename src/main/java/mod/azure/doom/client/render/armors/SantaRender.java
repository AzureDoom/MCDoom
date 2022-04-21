package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.SantaModel;
import mod.azure.doom.item.armor.SantaDoomArmor;
import software.bernie.geckolib3q.renderers.geo.GeoArmorRenderer;

public class SantaRender extends GeoArmorRenderer<SantaDoomArmor> {
	public SantaRender() {
		super(new SantaModel());

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