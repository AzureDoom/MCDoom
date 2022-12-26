package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.Mullet1Model;
import mod.azure.doom.item.armor.MulletDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class Mullet1Render extends GeoArmorRenderer<MulletDoomArmor> {
	public Mullet1Render() {
		super(new Mullet1Model());
	}
}