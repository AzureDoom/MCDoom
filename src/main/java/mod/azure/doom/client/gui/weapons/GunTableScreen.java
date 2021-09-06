package mod.azure.doom.client.gui.weapons;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import mod.azure.doom.DoomMod;
import mod.azure.doom.recipes.GunTableRecipe;
import mod.azure.doom.util.packets.DoomCraftingPacket;
import mod.azure.doom.util.packets.DoomPacketHandler;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

@SuppressWarnings("deprecation")
public class GunTableScreen extends ContainerScreen<GunTableScreenHandler> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(DoomMod.MODID,
			"textures/gui/gun_table_gui.png");

	private int selectedIndex;
	private final GunTableScreen.RecipeButton[] offers = new GunTableScreen.RecipeButton[7];
	private int scrollOff;
	private boolean scrolling;

	public GunTableScreen(GunTableScreenHandler handler, PlayerInventory inventory, ITextComponent title) {
		super(handler, inventory, title);
		this.imageWidth = 300;
		this.inventoryLabelX = 107;
	}

	private void postButtonClick() {
		this.menu.setRecipeIndex(this.selectedIndex);
		this.menu.switchTo(this.selectedIndex);
		DoomPacketHandler.CRAFTING.sendToServer(new DoomCraftingPacket(this.selectedIndex));
	}

	protected void init() {
		super.init();
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		int k = j + 18;

		for (int l = 0; l < 7; ++l) {
			this.offers[l] = this.addButton(new RecipeButton(i, k, l, (button) -> {
				if (button instanceof RecipeButton) {
					this.selectedIndex = ((RecipeButton) button).getIndex() + this.scrollOff;
					this.postButtonClick();
				}
			}));
			k += 20;
		}
	}

	protected void renderLabels(MatrixStack matrices, int mouseX, int mouseY) {
		this.font.draw(matrices, this.title, (float) (65 + this.imageWidth / 2 - this.font.width(this.title) / 2), 6.0F,
				4210752);
	}

	protected void renderBg(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bind(TEXTURE);
		int i = ((this.width - this.imageWidth) / 2) - 5;
		int j = (this.height - this.imageHeight) / 2;
		blit(matrices, i, j, this.getBlitOffset(), 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 512);

	}

	private void renderScroller(MatrixStack matrices, int x, int y, List<GunTableRecipe> tradeOffers) {
		int i = tradeOffers.size() + 1 - 7;
		if (i > 1) {
			int j = 139 - (27 + (i - 1) * 139 / i);
			int k = 1 + j / i + 139 / i;
			int m = Math.min(113, this.scrollOff * k);
			if (this.scrollOff == i - 1) {
				m = 113;
			}

			blit(matrices, x + 113, y + 18 + m, this.getBlitOffset(), 0.0F, 199.0F, 6, 27, 256, 512);
		} else {
			blit(matrices, x + 113, y + 18, this.getBlitOffset(), 6.0F, 199.0F, 6, 27, 256, 512);
		}

	}

	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		List<GunTableRecipe> tradeOfferList = this.menu.getRecipes();
		if (!tradeOfferList.isEmpty()) {
			int i = (this.width - this.imageWidth) / 2;
			int j = (this.height - this.imageHeight) / 2;
			int yPos = j + 17;
			int xPos = i + 3;
			matrices.pushPose();
			RenderSystem.enableRescaleNormal();
			this.minecraft.getTextureManager().bind(TEXTURE);
			this.renderScroller(matrices, i, j, tradeOfferList);
			int m = 0;

			for (GunTableRecipe gunTableRecipe : tradeOfferList) {
				if (this.canScroll(tradeOfferList.size()) && (m < this.scrollOff || m >= 7 + this.scrollOff)) {
					++m;
				} else {
					ItemStack output = gunTableRecipe.getResultItem();
					this.itemRenderer.blitOffset = 100.0F;
					int n = yPos + 2;
					this.renderIngredients(matrices, gunTableRecipe, xPos, n);

					this.renderButtonArrows(matrices, gunTableRecipe, i + 20, n);
					this.itemRenderer.renderAndDecorateFakeItem(output, i + 24 + 68, n);
					this.itemRenderer.renderGuiItemDecorations(this.font, output, i + 24 + 68, n);
					this.itemRenderer.blitOffset = 0.0F;
					yPos += 20;
					++m;
				}
			}

			for (RecipeButton widgetButtonPage : this.offers) {
				if (widgetButtonPage.isHovered()) {
					widgetButtonPage.renderToolTip(matrices, mouseX, mouseY);
				}

				widgetButtonPage.visible = widgetButtonPage.index < this.menu.getRecipes().size();
			}

			matrices.popPose();
			RenderSystem.enableDepthTest();
		}

		this.renderTooltip(matrices, mouseX, mouseY);
	}

	public void renderButtonArrows(MatrixStack matrices, GunTableRecipe tradeOffer, int x, int y) {
		RenderSystem.enableBlend();
		this.minecraft.getTextureManager().bind(TEXTURE);
		blit(matrices, x + 5 + 35 + 20, y + 3, this.getBlitOffset(), 15.0F, 171.0F, 10, 9, 256, 512);

	}

	private void renderIngredients(MatrixStack matrices, GunTableRecipe gunTableRecipe, int x, int y) {
		for (int i = 0; i < 5; i++) {
			ItemStack[] displayStacks = gunTableRecipe.getIngredientForSlot(i).getItems();
			if (displayStacks.length > 0) {
				ItemStack stack = new ItemStack(displayStacks[0].getItem(), gunTableRecipe.countRequired(i));
				if (!stack.isEmpty()) {
					this.itemRenderer.renderGuiItem(stack, x, y);
					this.itemRenderer.renderGuiItemDecorations(this.font, stack, x, y);
					x += 20;
				}
			}
		}
	}

	private boolean canScroll(int listSize) {
		return listSize > 7;
	}

	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		int i = this.menu.getRecipes().size();
		if (this.canScroll(i)) {
			int j = i - 7;
			this.scrollOff = (int) ((double) this.scrollOff - amount);
			this.scrollOff = MathHelper.clamp(this.scrollOff, 0, j);
		}
		return true;
	}

	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		int i = this.menu.getRecipes().size();
		if (this.scrolling) {
			int j = this.topPos + 18;
			int k = j + 139;
			int l = i - 7;
			float f = ((float) mouseY - (float) j - 13.5F) / ((float) (k - j) - 27.0F);
			f = f * (float) l + 0.5F;
			this.scrollOff = MathHelper.clamp((int) f, 0, l);
			return true;
		} else {
			return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
		}
	}

	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		this.scrolling = false;
		int i = (this.width - this.imageWidth) / 2;
		int j = (this.height - this.imageHeight) / 2;
		if (this.canScroll(this.menu.getRecipes().size()) && mouseX > (double) (i + 94)
				&& mouseX < (double) (i + 94 + 6) && mouseY > (double) (j + 18)
				&& mouseY <= (double) (j + 18 + 139 + 1)) {
			this.scrolling = true;
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}

	class RecipeButton extends Button {
		final int index;

		public RecipeButton(int x, int y, int index, Button.IPressable onPress) {
			super(x, y, 112, 20, StringTextComponent.EMPTY, onPress);
			this.index = index;
			this.visible = false;
		}

		public int getIndex() {
			return this.index;
		}

		public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {
			if (this.isHovered && menu.getRecipes().size() > this.index + scrollOff) {
				ItemStack stack;
				if (mouseX < this.x + 20) {
					stack = menu.getRecipes().get(this.index + scrollOff).getResultItem();
					renderTooltip(matrices, stack, mouseX, mouseY);
				} else if (mouseX < this.x + 50 && mouseX > this.x + 30) {
					stack = menu.getRecipes().get(this.index + scrollOff).getResultItem();
					if (!stack.isEmpty()) {
						renderTooltip(matrices, stack, mouseX, mouseY);
					}
				} else if (mouseX > this.x + 65) {
					stack = menu.getRecipes().get(this.index + scrollOff).getResultItem();
					renderTooltip(matrices, stack, mouseX, mouseY);
				}
			}
		}
	}
}