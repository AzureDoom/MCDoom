package mod.azure.doom.platform;

import mod.azure.doom.platform.services.DoomItemsHelper;
import mod.azure.doom.registry.NeoDoomEchantments;
import mod.azure.doom.registry.NeoDoomItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class NeoForgeDoomItemsHelper implements DoomItemsHelper {
    @Override
    public Enchantment getMicroEnchantment() {
        return NeoDoomEchantments.MICRO_MOD.get();
    }

    @Override
    public Enchantment getStickEnchantment() {
        return NeoDoomEchantments.STICKY_MOD.get();
    }

    @Override
    public Item getArgentEngery() {
        return NeoDoomItems.ARGENT_ENERGY.get();
    }

    @Override
    public Item getArgentBlock() {
        return NeoDoomItems.ARGENT_BLOCK.get();
    }

    @Override
    public Item getGun() {
        return NeoDoomItems.PISTOL.get();
    }

    @Override
    public Item getShotGun() {
        return NeoDoomItems.SG.get();
    }

    @Override
    public Item getChainGun() {
        return NeoDoomItems.CHAINGUN.get();
    }

    @Override
    public Item getBullets() {
        return NeoDoomItems.BULLETS.get();
    }

    @Override
    public Item getChaingunBullets() {
        return NeoDoomItems.CHAINGUN_BULLETS.get();
    }

    @Override
    public Item getBFGCell() {
        return NeoDoomItems.BFG_CELL.get();
    }

    @Override
    public Item getEngeryCell() {
        return NeoDoomItems.ENERGY_CELLS.get();
    }

    @Override
    public Item getRocket() {
        return NeoDoomItems.ROCKET.get();
    }

    @Override
    public Item getShells() {
        return NeoDoomItems.SHOTGUN_SHELLS.get();
    }

    @Override
    public Item getUnmaykrBolts() {
        return NeoDoomItems.UNMAKRY_BOLT.get();
    }

    @Override
    public Item getArgentBolts() {
        return NeoDoomItems.ARGENT_BOLT.get();
    }

    @Override
    public Item getGasItem() {
        return NeoDoomItems.GAS_BARREL.get();
    }
}
