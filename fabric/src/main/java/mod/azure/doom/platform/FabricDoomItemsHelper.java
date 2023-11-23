package mod.azure.doom.platform;

import mod.azure.doom.platform.services.DoomItemsHelper;
import mod.azure.doom.registry.FabricDoomBlocks;
import mod.azure.doom.registry.FabricDoomItems;
import net.minecraft.world.item.Item;

public class FabricDoomItemsHelper implements DoomItemsHelper {
    @Override
    public Item getArgentEngery() {
        return FabricDoomItems.ARGENT_ENERGY;
    }

    @Override
    public Item getArgentBlock() {
        return FabricDoomBlocks.ARGENT_BLOCK.asItem();
    }

    @Override
    public Item getGun() {
        return FabricDoomItems.PISTOL;
    }

    @Override
    public Item getShotGun() {
        return FabricDoomItems.SG;
    }

    @Override
    public Item getChainGun() {
        return FabricDoomItems.CHAINGUN;
    }

    @Override
    public Item getBullets() {
        return FabricDoomItems.BULLETS;
    }

    @Override
    public Item getChaingunBullets() {
        return FabricDoomItems.CHAINGUN_BULLETS;
    }

    @Override
    public Item getBFGCell() {
        return FabricDoomItems.BFG_CELL;
    }

    @Override
    public Item getEngeryCell() {
        return FabricDoomItems.ENERGY_CELLS;
    }

    @Override
    public Item getRocket() {
        return FabricDoomItems.ROCKET;
    }

    @Override
    public Item getShells() {
        return FabricDoomItems.SHOTGUN_SHELLS;
    }

    @Override
    public Item getUnmaykrBolts() {
        return FabricDoomItems.UNMAKRY_BOLT;
    }

    @Override
    public Item getArgentBolts() {
        return FabricDoomItems.ARGENT_BOLT;
    }

    @Override
    public Item getGasItem() {
        return FabricDoomItems.GAS_BARREL;
    }
}
