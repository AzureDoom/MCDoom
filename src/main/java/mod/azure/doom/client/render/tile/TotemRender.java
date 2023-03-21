package mod.azure.doom.client.render.tile;

import mod.azure.azurelib.renderer.GeoBlockRenderer;
import mod.azure.doom.client.models.tile.TotemModel;
import mod.azure.doom.entity.tileentity.TotemEntity;

public class TotemRender extends GeoBlockRenderer<TotemEntity> {
	public TotemRender() {
		super(new TotemModel());
	}
}