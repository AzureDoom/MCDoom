package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.Mullet1Model;
import mod.azure.doom.item.armor.MulletDoomArmor;
import software.bernie.geckolib3.renderer.geo.GeoArmorRenderer;

public class Mullet1Render extends GeoArmorRenderer<MulletDoomArmor> {
	public Mullet1Render() {
		super(new Mullet1Model());

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