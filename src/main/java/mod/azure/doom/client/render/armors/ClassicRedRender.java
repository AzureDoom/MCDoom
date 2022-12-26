package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.ClassicRedModel;
import mod.azure.doom.item.armor.ClassicRedDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ClassicRedRender extends GeoArmorRenderer<ClassicRedDoomArmor> {
	public ClassicRedRender() {
		super(new ClassicRedModel());
	}
}