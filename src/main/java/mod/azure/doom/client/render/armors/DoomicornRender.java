package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.DoomicornModel;
import mod.azure.doom.item.armor.DoomicornDoomArmor;
import software.bernie.geckolib3.renderer.geo.GeoArmorRenderer;

public class DoomicornRender extends GeoArmorRenderer<DoomicornDoomArmor> {
	public DoomicornRender() {
		super(new DoomicornModel());

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