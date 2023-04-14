package mod.azure.doom.client.gui;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import mod.azure.doom.DoomMod;
import mod.azure.doom.recipes.GunTableRecipe;
import mod.azure.doom.util.packets.DoomCraftingPacket;
import mod.azure.doom.util.packets.DoomPacketHandler;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class GunTableScreen extends AbstractContainerScreen<GunTableScreenHandler> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(DoomMod.MODID, "textures/gui/gun_table_gui.png");

	private int selectedIndex;
	private final GunTableScreen.RecipeButton[] offers = new GunTableScreen.RecipeButton[7];
	private int scrollOff;
	private boolean scrolling;

	public GunTableScreen(GunTableScreenHandler handler, Inventory inventory, Component title) {
		super(handler, inventory, title);
		imageWidth = 300;
		inventoryLabelX = 107;
	}

	private void postButtonClick() {
		menu.setRecipeIndex(selectedIndex);
		menu.switchTo(selectedIndex);
		DoomPacketHandler.CRAFTING.sendToServer(new DoomCraftingPacket(selectedIndex));
	}

	@Override
	protected void init() {
		super.init();
		final int i = (width - imageWidth) / 2;
		final int j = (height - imageHeight) / 2;
		int k = j + 18;

		for (int l = 0; l < 7; ++l) {
			offers[l] = this.addRenderableWidget(new RecipeButton(i, k, l, button -> {
				if (button instanceof RecipeButton) {
					selectedIndex = ((RecipeButton) button).getIndex() + scrollOff;
					postButtonClick();
				}
			}));
			k += 20;
		}
	}

	@Override
	protected void renderLabels(PoseStack matrices, int mouseX, int mouseY) {
		font.draw(matrices, title, 65 + imageWidth / 2 - font.width(title) / 2, 6.0F, 4210752);
	}

	@Override
	protected void renderBg(PoseStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		final int i = (width - imageWidth) / 2 - 5;
		final int j = (height - imageHeight) / 2;
		blit(matrices, i, j, 0, 0.0F, 0.0F, imageWidth, imageHeight, 512, 256);

	}

	private void renderScroller(PoseStack matrices, int x, int y, List<GunTableRecipe> tradeOffers) {
		final int i = tradeOffers.size() + 1 - 7;
		if (i > 1) {
			final int j = 139 - (27 + (i - 1) * 139 / i);
			final int k = 1 + j / i + 139 / i;
			int m = Math.min(113, scrollOff * k);
			if (scrollOff == i - 1) {
				m = 113;
			}

			blit(matrices, x + 113, y + 18 + m, 0, 0.0F, 199.0F, 6, 27, 512, 256);
		} else {
			blit(matrices, x + 113, y + 18, 0, 6.0F, 199.0F, 6, 27, 512, 256);
		}

	}

	@Override
	public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		final List<GunTableRecipe> tradeOfferList = menu.getRecipes();
		if (!tradeOfferList.isEmpty()) {
			final int i = (width - imageWidth) / 2;
			final int j = (height - imageHeight) / 2;
			int yPos = j + 17;
			final int xPos = i + 3;
			matrices.pushPose();
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, TEXTURE);
			renderScroller(matrices, i, j, tradeOfferList);
			int m = 0;

			for (final GunTableRecipe gunTableRecipe : tradeOfferList) {
				if (canScroll(tradeOfferList.size()) && (m < scrollOff || m >= 7 + scrollOff)) {
					++m;
				} else {
					final ItemStack output = gunTableRecipe.output;
					final int n = yPos + 2;
					renderIngredients(matrices, gunTableRecipe, xPos, n);

					renderButtonArrows(matrices, gunTableRecipe, i + 20, n);
					itemRenderer.renderAndDecorateFakeItem(matrices, output, i + 24 + 68, n);
					itemRenderer.renderGuiItemDecorations(matrices, font, output, i + 24 + 68, n);
					yPos += 20;
					++m;
				}
			}

			for (final RecipeButton widgetButtonPage : offers) {
				if (widgetButtonPage.isHoveredOrFocused()) {
					widgetButtonPage.renderToolTip(matrices, mouseX, mouseY);
				}

				widgetButtonPage.visible = widgetButtonPage.index < menu.getRecipes().size();
			}

			matrices.popPose();
			RenderSystem.enableDepthTest();
		}

		this.renderTooltip(matrices, mouseX, mouseY);
	}

	public void renderButtonArrows(PoseStack matrices, GunTableRecipe tradeOffer, int x, int y) {
		RenderSystem.enableBlend();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, TEXTURE);
		blit(matrices, x + 5 + 35 + 20, y + 3, 0, 15.0F, 171.0F, 10, 9, 512, 256);

	}

	private void renderIngredients(PoseStack matrices, GunTableRecipe gunTableRecipe, int x, int y) {
		for (int i = 0; i < 5; i++) {
			final ItemStack[] displayStacks = gunTableRecipe.getIngredientForSlot(i).getItems();
			if (displayStacks.length > 0) {
				final ItemStack stack = new ItemStack(displayStacks[0].getItem(), gunTableRecipe.countRequired(i));
				if (!stack.isEmpty()) {
					itemRenderer.renderGuiItem(matrices, stack, x, y);
					itemRenderer.renderGuiItemDecorations(matrices, font, stack, x, y);
					x += 20;
				}
			}
		}
	}

	private boolean canScroll(int listSize) {
		return listSize > 7;
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
		final int i = menu.getRecipes().size();
		if (canScroll(i)) {
			final int j = i - 7;
			scrollOff = (int) (scrollOff - amount);
			scrollOff = Mth.clamp(scrollOff, 0, j);
		}
		return true;
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		final int i = menu.getRecipes().size();
		if (scrolling) {
			final int j = topPos + 18;
			final int k = j + 139;
			final int l = i - 7;
			float f = ((float) mouseY - j - 13.5F) / (k - j - 27.0F);
			f = f * l + 0.5F;
			scrollOff = Mth.clamp((int) f, 0, l);
			return true;
		} else {
			return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		scrolling = false;
		final int i = (width - imageWidth) / 2;
		final int j = (height - imageHeight) / 2;
		if (canScroll(menu.getRecipes().size()) && mouseX > i + 94 && mouseX < i + 94 + 6 && mouseY > j + 18 && mouseY <= j + 18 + 139 + 1) {
			scrolling = true;
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}

	class RecipeButton extends Button {
		final int index;

		public RecipeButton(int x, int y, int index, Button.OnPress onPress) {
			super(x, y, 112, 20, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
			this.index = index;
			visible = false;
		}

		public int getIndex() {
			return index;
		}

		public void renderToolTip(PoseStack matrices, int mouseX, int mouseY) {
			if (isHovered && menu.getRecipes().size() > index + scrollOff) {
				ItemStack stack;
				if (mouseX < getX() + 20) {
					stack = menu.getRecipes().get(index + scrollOff).output;
					renderTooltip(matrices, stack, mouseX, mouseY);
				} else if (mouseX < getX() + 50 && mouseX > getX() + 30) {
					stack = menu.getRecipes().get(index + scrollOff).output;
					if (!stack.isEmpty()) {
						renderTooltip(matrices, stack, mouseX, mouseY);
					}
				} else if (mouseX > getX() + 65) {
					stack = menu.getRecipes().get(index + scrollOff).output;
					renderTooltip(matrices, stack, mouseX, mouseY);
				}
			}
		}
	}
}