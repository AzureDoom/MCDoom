package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.AstroModel;
import mod.azure.doom.item.armor.AstroDoomArmor;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class AstroRender extends GeoArmorRenderer<AstroDoomArmor> {
	public AstroRender() {
		super(new AstroModel());

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