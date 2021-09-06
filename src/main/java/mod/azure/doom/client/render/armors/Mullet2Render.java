package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.Mullet2Model;
import mod.azure.doom.item.armor.Mullet2DoomArmor;
import software.bernie.geckolib3.renderer.geo.GeoArmorRenderer;

public class Mullet2Render extends GeoArmorRenderer<Mullet2DoomArmor> {
	public Mullet2Render() {
		super(new Mullet2Model());

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