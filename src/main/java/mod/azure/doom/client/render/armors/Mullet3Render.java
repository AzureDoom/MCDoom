package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.Mullet3Model;
import mod.azure.doom.item.armor.Mullet3DoomArmor;
import software.bernie.geckolib3.renderer.geo.GeoArmorRenderer;

public class Mullet3Render extends GeoArmorRenderer<Mullet3DoomArmor> {
	public Mullet3Render() {
		super(new Mullet3Model());

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