package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.TwentyFiveModel;
import mod.azure.doom.item.armor.TwentyFiveDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class TwentyFiveRender extends GeoArmorRenderer<TwentyFiveDoomArmor> {
	public TwentyFiveRender() {
		super(new TwentyFiveModel());
	}
}