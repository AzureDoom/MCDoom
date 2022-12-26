package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.Mullet2Model;
import mod.azure.doom.item.armor.Mullet2DoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class Mullet2Render extends GeoArmorRenderer<Mullet2DoomArmor> {
	public Mullet2Render() {
		super(new Mullet2Model());
	}
}