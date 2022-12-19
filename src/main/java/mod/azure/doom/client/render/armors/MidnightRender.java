package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.MidnightModel;
import mod.azure.doom.item.armor.MidnightDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class MidnightRender extends GeoArmorRenderer<MidnightDoomArmor> {
	public MidnightRender() {
		super(new MidnightModel());
	}
}