package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.PainterModel;
import mod.azure.doom.item.armor.PainterDoomArmor;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class PainterRender extends GeoArmorRenderer<PainterDoomArmor> {
	public PainterRender() {
		super(new PainterModel());

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