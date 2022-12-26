package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.ZombieModel;
import mod.azure.doom.item.armor.ZombieDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ZombieRender extends GeoArmorRenderer<ZombieDoomArmor> {
	public ZombieRender() {
		super(new ZombieModel());
	}
}