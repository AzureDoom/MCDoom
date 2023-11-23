package mod.azure.doom.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.azure.doom.MCDoom;
import mod.azure.doom.platform.Services;
import mod.azure.doom.recipes.GunTableRecipe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class GunTableScreen extends AbstractContainerScreen<GunTableScreenHandler> {
    private static final ResourceLocation TEXTURE = MCDoom.modResource("textures/gui/gun_table_gui.png");

    private int selectedIndex;
    private final WidgetButtonPage[] offers = new WidgetButtonPage[7];
    private int indexStartOffset;
    private boolean scrolling;

    public GunTableScreen(GunTableScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        this.imageWidth = 300;
        this.inventoryLabelX = 107;
    }

    private void syncRecipeIndex() {
        this.menu.setRecipeIndex(this.selectedIndex);
        this.menu.switchTo(this.selectedIndex);
        Services.NETWORK.sendCraftingPacket(this.selectedIndex);
    }

    @Override
    protected void init() {
        super.init();
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        int k = j + 18;

        for (int l = 0; l < 7; ++l) {
            this.offers[l] = this.addRenderableWidget(new WidgetButtonPage(i, k, l, button -> {
                if (button instanceof WidgetButtonPage widgetButtonPage) {
                    this.selectedIndex = widgetButtonPage.getIndex() + this.indexStartOffset;
                    this.syncRecipeIndex();
                }
            }));
            k += 20;
        }
    }

    @Override
    protected void renderLabels(GuiGraphics matrices, int mouseX, int mouseY) {
        matrices.drawString(this.font, this.title, (75 + this.imageWidth / 2 - this.font.width(this.title) / 2), 6, 4210752, false);
    }

    protected void renderBg(GuiGraphics matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = ((this.width - this.imageWidth) / 2) - 5;
        int j = (this.height - this.imageHeight) / 2;
        matrices.blit(TEXTURE, i, j, 0, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 512, 256);

    }

    private void renderScrollbar(GuiGraphics matrices, int x, int y, List<GunTableRecipe> tradeOffers) {
        int i = tradeOffers.size() + 1 - 7;
        if (i > 1) {
            int j = 139 - (27 + (i - 1) * 139 / i);
            int k = 1 + j / i + 139 / i;
            int m = Math.min(113, this.indexStartOffset * k);
            if (this.indexStartOffset == i - 1) {
                m = 113;
            }

            matrices.blit(TEXTURE, x + 113, y + 18 + m, 0, 0.0F, 199.0F, 6, 27, 512, 256);
        } else {
            matrices.blit(TEXTURE, x + 113, y + 18, 0, 6.0F, 199.0F, 6, 27, 512, 256);
        }

    }

    @Override
    public void render(GuiGraphics matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        List<GunTableRecipe> tradeOfferList = this.menu.getRecipes();
        if (!tradeOfferList.isEmpty()) {
            int i = (this.width - this.imageWidth) / 2;
            int j = (this.height - this.imageHeight) / 2;
            int yPos = j + 17;
            int xPos = i + 3;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, TEXTURE);
            this.renderScrollbar(matrices, i, j, tradeOfferList);
            int m = 0;

            while (true) {
                for (GunTableRecipe gunTableRecipe : tradeOfferList)
                    if (this.canScroll(tradeOfferList.size()) && (m < this.indexStartOffset || m >= 7 + this.indexStartOffset)) {
                        ++m;
                    } else {
                        ItemStack output = gunTableRecipe.output;
                        int n = yPos + 2;
                        this.renderIngredients(matrices, gunTableRecipe, xPos, n);

                        this.renderArrow(matrices, gunTableRecipe, i + 22, n);
                        matrices.renderFakeItem(output, i + 24 + 68, n);
                        matrices.renderItemDecorations(this.font, output, i + 24 + 68, n);
                        yPos += 20;
                        ++m;
                    }

                for (WidgetButtonPage widgetButtonPage : this.offers) {
                    if (widgetButtonPage.isHovered()) {
                        widgetButtonPage.renderToolTip(matrices, mouseX, mouseY);
                    }

                    widgetButtonPage.visible = widgetButtonPage.index < this.menu.getRecipes().size();
                }

                RenderSystem.enableDepthTest();
                break;
            }
        }

        this.renderTooltip(matrices, mouseX, mouseY);
    }

    private void renderArrow(GuiGraphics matrices, GunTableRecipe tradeOffer, int x, int y) {
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE);

    }

    private void renderIngredients(GuiGraphics matrices, GunTableRecipe gunTableRecipe, int x, int y) {
        for (int i = 0; i < 5; i++) {
            ItemStack[] displayStacks = gunTableRecipe.getIngredientForSlot(i).getItems();
            if (displayStacks.length > 0) {
                // probably slow, but subclassing ingredient is hard in fabric
                ItemStack stack = new ItemStack(displayStacks[0].getItem(), gunTableRecipe.countRequired(i));
                if (!stack.isEmpty()) {
                    matrices.renderFakeItem(stack, x, y);
                    matrices.renderItemDecorations(this.font, stack, x, y);
                    x += 16;
                }
            }
        }
    }

    private boolean canScroll(int listSize) {
        return listSize > 7;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        int i = this.menu.getRecipes().size();
        if (this.canScroll(i)) {
            int j = i - 7;
            this.indexStartOffset = (int) (this.indexStartOffset - amount);
            this.indexStartOffset = Mth.clamp(this.indexStartOffset, 0, j);
        }
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        int i = this.menu.getRecipes().size();
        if (this.scrolling) {
            int j = this.topPos + 18;
            int k = j + 139;
            int l = i - 7;
            float f = ((float) mouseY - j - 13.5F) / ((k - j) - 27.0F);
            f = f * l + 0.5F;
            this.indexStartOffset = Mth.clamp((int) f, 0, l);
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.scrolling = false;
        var i = (this.width - this.imageWidth) / 2;
        var j = (this.height - this.imageHeight) / 2;
        if (this.canScroll(this.menu.getRecipes().size()) && mouseX > (i + 94) && mouseX < (i + 94 + 6) && mouseY > (j + 18) && mouseY <= (j + 18 + 139 + 1))
            this.scrolling = true;

        return super.mouseClicked(mouseX, mouseY, button);
    }

    class WidgetButtonPage extends Button {

        final int index;

        public WidgetButtonPage(int x, int y, int index, OnPress onPress) {
            super(x, y, 112, 20, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
            this.index = index;
            this.visible = false;
        }

        public int getIndex() {
            return this.index;
        }

        public void renderToolTip(GuiGraphics matrices, int mouseX, int mouseY) {
            if (this.isHovered && menu.getRecipes().size() > this.index + indexStartOffset) {
                ItemStack stack;
                if (mouseX < this.getX() + 20) {
                    stack = menu.getRecipes().get(this.index + indexStartOffset).output;
                    renderTooltip(matrices, mouseX, mouseY);
                } else if (mouseX < this.getX() + 50 && mouseX > this.getX() + 30) {
                    stack = menu.getRecipes().get(this.index + indexStartOffset).output;
                    if (!stack.isEmpty()) {
                        renderTooltip(matrices, mouseX, mouseY);
                    }
                } else if (mouseX > this.getX() + 65) {
                    stack = menu.getRecipes().get(this.index + indexStartOffset).output;
                    renderTooltip(matrices, mouseX, mouseY);
                }
            }
        }
    }
}