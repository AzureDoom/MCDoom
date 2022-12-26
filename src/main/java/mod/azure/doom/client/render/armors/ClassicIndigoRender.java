package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.ClassicIndigoModel;
import mod.azure.doom.item.armor.ClassicIndigoDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ClassicIndigoRender extends GeoArmorRenderer<ClassicIndigoDoomArmor> {
	public ClassicIndigoRender() {
		super(new ClassicIndigoModel());
	}
}