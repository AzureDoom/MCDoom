package mod.azure.doom.client.gui;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;

import mod.azure.doom.DoomMod;
import mod.azure.doom.network.C2SMessageSelectCraft;
import mod.azure.doom.util.recipes.GunTableRecipe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class GunTableScreen extends HandledScreen<GunTableScreenHandler> {
	private static final Identifier TEXTURE = new Identifier(DoomMod.MODID, "textures/gui/gun_table_gui.png");

	private int selectedIndex;
	private final GunTableScreen.WidgetButtonPage[] offers = new GunTableScreen.WidgetButtonPage[7];
	private int indexStartOffset;
	private boolean scrolling;

	public GunTableScreen(GunTableScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		this.backgroundWidth = 300;
		this.playerInventoryTitleX = 107;
	}

	private void syncRecipeIndex() {
		this.handler.setRecipeIndex(this.selectedIndex);
		this.handler.switchTo(this.selectedIndex);
		C2SMessageSelectCraft.send(selectedIndex);
	}

	protected void init() {
		super.init();
		int i = (this.width - this.backgroundWidth) / 2;
		int j = (this.height - this.backgroundHeight) / 2;
		int k = j + 18;

		for (int l = 0; l < 7; ++l) {
			this.offers[l] = this.addDrawableChild(new WidgetButtonPage(i, k, l, (button) -> {
				if (button instanceof WidgetButtonPage) {
					this.selectedIndex = ((WidgetButtonPage) button).getIndex() + this.indexStartOffset;
					this.syncRecipeIndex();
				}
			}));
			k += 20;
		}
	}

	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
		this.textRenderer.draw(matrices, this.title,
				(float) (75 + this.backgroundWidth / 2 - this.textRenderer.getWidth(this.title) / 2), 6.0F, 4210752);
	}

	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int i = ((this.width - this.backgroundWidth) / 2) - 5;
		int j = (this.height - this.backgroundHeight) / 2;
		drawTexture(matrices, i, j, this.getZOffset(), 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, 256,
				512);

	}

	private void renderScrollbar(MatrixStack matrices, int x, int y, List<GunTableRecipe> tradeOffers) {
		int i = tradeOffers.size() + 1 - 7;
		if (i > 1) {
			int j = 139 - (27 + (i - 1) * 139 / i);
			int k = 1 + j / i + 139 / i;
			int m = Math.min(113, this.indexStartOffset * k);
			if (this.indexStartOffset == i - 1) {
				m = 113;
			}

			drawTexture(matrices, x + 113, y + 18 + m, this.getZOffset(), 0.0F, 199.0F, 6, 27, 256, 512);
		} else {
			drawTexture(matrices, x + 113, y + 18, this.getZOffset(), 6.0F, 199.0F, 6, 27, 256, 512);
		}

	}

	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		List<GunTableRecipe> tradeOfferList = this.handler.getRecipes();
		if (!tradeOfferList.isEmpty()) {
			int i = (this.width - this.backgroundWidth) / 2;
			int j = (this.height - this.backgroundHeight) / 2;
			int yPos = j + 17;
			int xPos = i + 3;
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, TEXTURE);
			this.renderScrollbar(matrices, i, j, tradeOfferList);
			int m = 0;

			while (true) {
				for (GunTableRecipe gunTableRecipe : tradeOfferList)
					if (this.canScroll(tradeOfferList.size())
							&& (m < this.indexStartOffset || m >= 7 + this.indexStartOffset)) {
						++m;
					} else {
						ItemStack output = gunTableRecipe.getOutput();
						this.itemRenderer.zOffset = 100.0F;
						int n = yPos + 2;
						this.renderIngredients(matrices, gunTableRecipe, xPos, n);

						this.renderArrow(matrices, gunTableRecipe, i + 22, n);
						this.itemRenderer.renderInGui(output, i + 24 + 68, n);
						this.itemRenderer.renderGuiItemOverlay(this.textRenderer, output, i + 24 + 68, n);
						this.itemRenderer.zOffset = 0.0F;
						yPos += 20;
						++m;
					}

				for (WidgetButtonPage widgetButtonPage : this.offers) {
					if (widgetButtonPage.isHovered()) {
						widgetButtonPage.renderToolTip(matrices, mouseX, mouseY);
					}

					widgetButtonPage.visible = widgetButtonPage.index < this.handler.getRecipes().size();
				}

				RenderSystem.enableDepthTest();
				break;
			}
		}

		this.drawMouseoverTooltip(matrices, mouseX, mouseY);
	}

	private void renderArrow(MatrixStack matrices, GunTableRecipe tradeOffer, int x, int y) {
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, TEXTURE);
		drawTexture(matrices, x + 5 + 35 + 20, y + 3, this.getZOffset(), 15.0F, 171.0F, 10, 9, 256, 512);

	}

	private void renderIngredients(MatrixStack matrices, GunTableRecipe gunTableRecipe, int x, int y) {
		for (int i = 0; i < 5; i++) {
			ItemStack[] displayStacks = gunTableRecipe.getIngredientForSlot(i).getMatchingStacks();
			if (displayStacks.length > 0) {
				// probably slow, but subclassing ingredient is hard in fabric
				ItemStack stack = new ItemStack(displayStacks[0].getItem(), gunTableRecipe.countRequired(i));
				if (!stack.isEmpty()) {
					this.itemRenderer.renderInGui(stack, x, y);
					this.itemRenderer.renderGuiItemOverlay(this.textRenderer, stack, x, y);
					x += 16;
				}
			}
		}
	}

	private boolean canScroll(int listSize) {
		return listSize > 7;
	}

	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		int i = this.handler.getRecipes().size();
		if (this.canScroll(i)) {
			int j = i - 7;
			this.indexStartOffset = (int) ((double) this.indexStartOffset - amount);
			this.indexStartOffset = MathHelper.clamp(this.indexStartOffset, 0, j);
		}
		return true;
	}

	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		int i = this.handler.getRecipes().size();
		if (this.scrolling) {
			int j = this.y + 18;
			int k = j + 139;
			int l = i - 7;
			float f = ((float) mouseY - (float) j - 13.5F) / ((float) (k - j) - 27.0F);
			f = f * (float) l + 0.5F;
			this.indexStartOffset = MathHelper.clamp((int) f, 0, l);
			return true;
		} else {
			return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
		}
	}

	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		this.scrolling = false;
		int i = (this.width - this.backgroundWidth) / 2;
		int j = (this.height - this.backgroundHeight) / 2;
		if (this.canScroll(this.handler.getRecipes().size()) && mouseX > (double) (i + 94)
				&& mouseX < (double) (i + 94 + 6) && mouseY > (double) (j + 18)
				&& mouseY <= (double) (j + 18 + 139 + 1)) {
			this.scrolling = true;
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Environment(EnvType.CLIENT)
	class WidgetButtonPage extends ButtonWidget {
		final int index;

		public WidgetButtonPage(int x, int y, int index, ButtonWidget.PressAction onPress) {
			super(x, y, 112, 20, LiteralText.EMPTY, onPress);
			this.index = index;
			this.visible = false;
		}

		public int getIndex() {
			return this.index;
		}

		public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {
			if (this.hovered && handler.getRecipes().size() > this.index + indexStartOffset) {
				ItemStack stack;
				if (mouseX < this.x + 20) {
					stack = handler.getRecipes().get(this.index + indexStartOffset).getOutput();
					renderTooltip(matrices, mouseX, mouseY);
				} else if (mouseX < this.x + 50 && mouseX > this.x + 30) {
					stack = handler.getRecipes().get(this.index + indexStartOffset).getOutput();
					if (!stack.isEmpty()) {
						renderTooltip(matrices, mouseX, mouseY);
					}
				} else if (mouseX > this.x + 65) {
					stack = handler.getRecipes().get(this.index + indexStartOffset).getOutput();
					renderTooltip(matrices, mouseX, mouseY);
				}
			}
		}
	}
}