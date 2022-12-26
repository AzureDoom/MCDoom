package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.ClassicModel;
import mod.azure.doom.item.armor.ClassicDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ClassicRender extends GeoArmorRenderer<ClassicDoomArmor> {
	public ClassicRender() {
		super(new ClassicModel());
	}
}