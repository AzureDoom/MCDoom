package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.AstroModel;
import mod.azure.doom.item.armor.AstroDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class AstroRender extends GeoArmorRenderer<AstroDoomArmor> {
	public AstroRender() {
		super(new AstroModel());
	}
}