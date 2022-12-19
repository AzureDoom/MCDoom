package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.ClassicBronzeModel;
import mod.azure.doom.item.armor.ClassicBronzeDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ClassicBronzeRender extends GeoArmorRenderer<ClassicBronzeDoomArmor> {
	public ClassicBronzeRender() {
		super(new ClassicBronzeModel());
	}
}