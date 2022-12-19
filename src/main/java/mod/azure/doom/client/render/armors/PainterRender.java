package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.PainterModel;
import mod.azure.doom.item.armor.PainterDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class PainterRender extends GeoArmorRenderer<PainterDoomArmor> {
	public PainterRender() {
		super(new PainterModel());
	}
}