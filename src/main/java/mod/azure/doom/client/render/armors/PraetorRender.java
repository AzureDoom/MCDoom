package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.PraetorModel;
import mod.azure.doom.item.armor.PraetorDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class PraetorRender extends GeoArmorRenderer<PraetorDoomArmor> {
	public PraetorRender() {
		super(new PraetorModel());
	}
}