package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.CultistModel;
import mod.azure.doom.item.armor.CultistDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class CultistRender extends GeoArmorRenderer<CultistDoomArmor> {
	public CultistRender() {
		super(new CultistModel());
	}
}