package mod.azure.doom.client.render.weapons;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.doom.client.models.weapons.RocketLauncherModel;
import mod.azure.doom.items.weapons.RocketLauncher;

public class RocketLauncherRender extends GeoItemRenderer<RocketLauncher> {
	public RocketLauncherRender() {
		super(new RocketLauncherModel());
	}
}