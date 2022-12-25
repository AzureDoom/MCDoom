package mod.azure.doom.config;

import java.awt.Color;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.blaze3d.vertex.PoseStack;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

/**
 * MidnightConfig v2.1.0 by TeamMidnightDust & Motschen Single class config
 * library - feel free to copy!
 *
 * Based on https://github.com/Minenash/TinyConfig Credits to Minenash
 */

public abstract class CustomMidnightConfig {
	private static final Pattern INTEGER_ONLY = Pattern.compile("(-?[0-9]*)");
	private static final Pattern DECIMAL_ONLY = Pattern.compile("-?([\\d]+\\.?[\\d]*|[\\d]*\\.?[\\d]+|\\.)");
	private static final Pattern HEXADECIMAL_ONLY = Pattern.compile("(-?[#0-9a-fA-F]*)");

	private static final List<EntryInfo> entries = new ArrayList<>();

	protected static class EntryInfo {
		Field field;
		Object widget;
		int width;
		int max;
		boolean centered;
		Map.Entry<EditBox, Component> error;
		Object defaultValue;
		Object value;
		String tempValue;
		boolean inLimits = true;
		String id;
		Component name;
		int index;
		AbstractWidget colorButton;
	}

	public static final Map<String, Class<?>> configClass = new HashMap<>();
	private static Path path;

	private static final Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT)
			.excludeFieldsWithModifiers(Modifier.PRIVATE)
			.addSerializationExclusionStrategy(new HiddenAnnotationExclusionStrategy()).setPrettyPrinting().create();

	public static void init(String modid, Class<?> config) {
		path = FabricLoader.getInstance().getConfigDir().resolve(modid + ".json");
		configClass.put(modid, config);

		for (Field field : config.getFields()) {
			EntryInfo info = new EntryInfo();
			if ((field.isAnnotationPresent(Entry.class) || field.isAnnotationPresent(Comment.class))
					&& !field.isAnnotationPresent(Server.class) && !field.isAnnotationPresent(Hidden.class))
				if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
					initClient(modid, field, info);
			if (field.isAnnotationPresent(Comment.class))
				info.centered = field.getAnnotation(Comment.class).centered();
			if (field.isAnnotationPresent(Entry.class))
				try {
					info.defaultValue = field.get(null);
				} catch (IllegalAccessException ignored) {
				}
		}
		try {
			gson.fromJson(Files.newBufferedReader(path), config);
		} catch (Exception e) {
			write(modid);
		}

		for (EntryInfo info : entries) {
			if (info.field.isAnnotationPresent(Entry.class))
				try {
					info.value = info.field.get(null);
					info.tempValue = info.value.toString();
				} catch (IllegalAccessException ignored) {
				}
		}
	}

	@Environment(EnvType.CLIENT)
	private static void initClient(String modid, Field field, EntryInfo info) {
		Class<?> type = field.getType();
		Entry e = field.getAnnotation(Entry.class);
		info.width = e != null ? e.width() : 0;
		info.field = field;
		info.id = modid;

		if (e != null) {
			if (!e.name().equals(""))
				info.name = Component.translatable(e.name());
			if (type == int.class)
				textField(info, Integer::parseInt, INTEGER_ONLY, (int) e.min(), (int) e.max(), true);
			else if (type == float.class)
				textField(info, Float::parseFloat, DECIMAL_ONLY, (float) e.min(), (float) e.max(), false);
			else if (type == double.class)
				textField(info, Double::parseDouble, DECIMAL_ONLY, e.min(), e.max(), false);
			else if (type == String.class || type == List.class) {
				info.max = e.max() == Double.MAX_VALUE ? Integer.MAX_VALUE : (int) e.max();
				textField(info, String::length, null, Math.min(e.min(), 0), Math.max(e.max(), 1), true);
			} else if (type == boolean.class) {
				Function<Object, Component> func = value -> Component
						.translatable((Boolean) value ? "gui.yes" : "gui.no")
						.withStyle((Boolean) value ? ChatFormatting.GREEN : ChatFormatting.RED);
				info.widget = new AbstractMap.SimpleEntry<Button.OnPress, Function<Object, Component>>(button -> {
					info.value = !(Boolean) info.value;
					button.setMessage(func.apply(info.value));
				}, func);
			} else if (type.isEnum()) {
				List<?> values = Arrays.asList(field.getType().getEnumConstants());
				Function<Object, Component> func = value -> Component.translatable(
						modid + ".midnightconfig." + "enum." + type.getSimpleName() + "." + info.value.toString());
				info.widget = new AbstractMap.SimpleEntry<Button.OnPress, Function<Object, Component>>(button -> {
					int index = values.indexOf(info.value) + 1;
					info.value = values.get(index >= values.size() ? 0 : index);
					button.setMessage(func.apply(info.value));
				}, func);
			}
		}
		entries.add(info);
	}

	private static void textField(EntryInfo info, Function<String, Number> f, Pattern pattern, double min, double max,
			boolean cast) {
		boolean isNumber = pattern != null;
		info.widget = (BiFunction<EditBox, Button, Predicate<String>>) (t, b) -> s -> {
			s = s.trim();
			if (!(s.isEmpty() || !isNumber || pattern.matcher(s).matches()))
				return false;

			Number value = 0;
			boolean inLimits = false;
			info.error = null;
			if (!(isNumber && s.isEmpty()) && !s.equals("-") && !s.equals(".")) {
				value = f.apply(s);
				inLimits = value.doubleValue() >= min && value.doubleValue() <= max;
				info.error = inLimits ? null
						: new AbstractMap.SimpleEntry<>(t,
								Component.literal(value.doubleValue() < min
										? "§cMinimum " + (isNumber ? "value" : "length")
												+ (cast ? " is " + (int) min : " is " + min)
										: "§cMaximum " + (isNumber ? "value" : "length")
												+ (cast ? " is " + (int) max : " is " + max)));
			}

			info.tempValue = s;
			t.setTextColor(inLimits ? 0xFFFFFFFF : 0xFFFF7777);
			info.inLimits = inLimits;
			b.active = entries.stream().allMatch(e -> e.inLimits);

			if (inLimits && info.field.getType() != List.class)
				info.value = isNumber ? value : s;
			else if (inLimits) {
				if (((List<String>) info.value).size() == info.index)
					((List<String>) info.value).add("");
				((List<String>) info.value).set(info.index,
						Arrays.stream(info.tempValue.replace("[", "").replace("]", "").split(", ")).toList().get(0));
			}

			if (info.field.getAnnotation(Entry.class).isColor()) {
				if (!s.contains("#"))
					s = '#' + s;
				if (!HEXADECIMAL_ONLY.matcher(s).matches())
					return false;
				try {
					info.colorButton.setMessage(Component.literal("⬛")
							.setStyle(Style.EMPTY.withColor(Color.decode(info.tempValue).getRGB())));
				} catch (Exception ignored) {
				}
			}
			return true;
		};
	}

	public static void write(String modid) {
		path = FabricLoader.getInstance().getConfigDir().resolve(modid + ".json");
		try {
			if (!Files.exists(path))
				Files.createFile(path);
			Files.write(path, gson.toJson(configClass.get(modid).getDeclaredConstructor().newInstance()).getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Environment(EnvType.CLIENT)
	public static Screen getScreen(Screen parent, String modid) {
		return new MidnightConfigScreen(parent, modid);
	}

	@Environment(EnvType.CLIENT)
	public static class MidnightConfigScreen extends Screen {
		protected MidnightConfigScreen(Screen parent, String modid) {
			super(Component.translatable("doom.midnightconfig." + "title"));
			this.parent = parent;
			this.modid = modid;
			this.translationPrefix = "doom.midnightconfig.";
		}

		public final String translationPrefix;
		public final Screen parent;
		public final String modid;
		public MidnightConfigListWidget list;
		public boolean reload = false;

		// Real Time config update //
		@Override
		public void tick() {
			super.tick();
			for (EntryInfo info : entries) {
				try {
					info.field.set(null, info.value);
				} catch (IllegalAccessException ignored) {
				}
			}
			updateResetButtons();
		}

		public void updateResetButtons() {
			if (this.list != null) {
				for (ButtonEntry entry : this.list.children()) {
					if (entry.buttons != null && entry.buttons.size() > 1
							&& entry.buttons.get(1)instanceof Button button) {
						button.active = !Objects.equals(entry.info.value.toString(),
								entry.info.defaultValue.toString());
					}
				}
			}
		}

		public void loadValues() {
			try {
				gson.fromJson(Files.newBufferedReader(path), configClass.get(modid));
			} catch (Exception e) {
				write(modid);
			}

			for (EntryInfo info : entries) {
				if (info.field.isAnnotationPresent(Entry.class))
					try {
						info.value = info.field.get(null);
						info.tempValue = info.value.toString();
					} catch (IllegalAccessException ignored) {
					}
			}
		}

		@Override
		public void init() {
			super.init();
			if (!reload)
				loadValues();

			this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, button -> {
				loadValues();
				Objects.requireNonNull(minecraft).setScreen(parent);
			}).bounds(this.width / 2 - 154, this.height - 28, 150, 20).build());

			Button done = this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (button) -> {
				for (EntryInfo info : entries)
					if (info.id.equals(modid)) {
						try {
							info.field.set(null, info.value);
						} catch (IllegalAccessException ignored) {
						}
					}
				write(modid);
				Objects.requireNonNull(minecraft).setScreen(parent);
			}).bounds(this.width / 2 + 4, this.height - 28, 150, 20).build());

			this.list = new MidnightConfigListWidget(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
			if (this.minecraft != null && this.minecraft.level != null)
				this.list.setRenderBackground(false);
			this.addRenderableWidget(this.list);
			for (EntryInfo info : entries) {
				if (info.id.equals(modid)) {
					Component name = Objects.requireNonNullElseGet(info.name,
							() -> Component.translatable(translationPrefix + info.field.getName()));
					Button resetButton = Button
							.builder(Component.translatable("Reset").withStyle(ChatFormatting.RED), (button -> {
								info.value = info.defaultValue;
								info.tempValue = info.defaultValue.toString();
								info.index = 0;
								double scrollAmount = list.getScrollAmount();
								this.reload = true;
								Objects.requireNonNull(minecraft).setScreen(this);
								list.setScrollAmount(scrollAmount);
							})).bounds(width - 205, 0, 40, 20).build();

					if (info.widget instanceof Map.Entry) {
						Map.Entry<Button.OnPress, Function<Object, Component>> widget = (Map.Entry<Button.OnPress, Function<Object, Component>>) info.widget;
						if (info.field.getType().isEnum())
							widget.setValue(value -> Component.translatable(translationPrefix + "enum."
									+ info.field.getType().getSimpleName() + "." + info.value.toString()));
						this.list.addButton(List.of(Button.builder(widget.getValue().apply(info.value), widget.getKey())
								.bounds(width - 160, 0, 150, 20).build(), resetButton), name, info);
					} else if (info.field.getType() == List.class) {
						if (!reload)
							info.index = 0;
						EditBox widget = new EditBox(font, width - 160, 0, 150, 20, null);
						widget.setMaxLength(info.width);
						if (info.index < ((List<String>) info.value).size())
							widget.setValue((String.valueOf(((List<String>) info.value).get(info.index))));
						else
							widget.setValue("");
						Predicate<String> processor = ((BiFunction<EditBox, Button, Predicate<String>>) info.widget)
								.apply(widget, done);
						widget.setFilter(processor);
						resetButton.setWidth(20);
						resetButton.setMessage(Component.literal("R").withStyle(ChatFormatting.RED));
						Button cycleButton = Button
								.builder(Component.literal(String.valueOf(info.index)).withStyle(ChatFormatting.GOLD),
										(button -> {
											((List<String>) info.value).remove("");
											double scrollAmount = list.getScrollAmount();
											this.reload = true;
											info.index = info.index + 1;
											if (info.index > ((List<String>) info.value).size())
												info.index = 0;
											Objects.requireNonNull(minecraft).setScreen(this);
											list.setScrollAmount(scrollAmount);
										}))
								.bounds(width - 185, 0, 20, 20).build();
						this.list.addButton(List.of(widget, resetButton, cycleButton), name, info);
					} else if (info.widget != null) {
						EditBox widget = new EditBox(font, width - 160, 0, 150, 20, null);
						widget.setMaxLength(info.width);
						widget.setValue(info.tempValue);
						Predicate<String> processor = ((BiFunction<EditBox, Button, Predicate<String>>) info.widget)
								.apply(widget, done);
						widget.setFilter(processor);
						if (info.field.getAnnotation(Entry.class).isColor()) {
							resetButton.setWidth(20);
							resetButton.setMessage(Component.literal("R").withStyle(ChatFormatting.RED));
							Button colorButton = Button.builder(Component.literal("⬛"), (button -> {
							})).bounds(width - 185, 0, 20, 20).build();
							try {
								colorButton.setMessage(Component.literal("⬛")
										.setStyle(Style.EMPTY.withColor(Color.decode(info.tempValue).getRGB())));
							} catch (Exception ignored) {
							}
							info.colorButton = colorButton;
							colorButton.active = false;
							this.list.addButton(List.of(widget, resetButton, colorButton), name, info);
						} else
							this.list.addButton(List.of(widget, resetButton), name, info);
					} else {
						this.list.addButton(List.of(), name, info);
					}
				}
				updateResetButtons();
			}

		}

		@Override
		public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
			this.renderBackground(matrices);
			this.list.render(matrices, mouseX, mouseY, delta);
			drawCenteredString(matrices, font, title, width / 2, 15, 0xFFFFFF);

			for (EntryInfo info : entries) {
				if (info.id.equals(modid)) {
					if (list.getHoveredButton(mouseX, mouseY).isPresent()) {
						AbstractWidget buttonWidget = list.getHoveredButton(mouseX, mouseY).get();
						Component text = ButtonEntry.buttonsWithText.get(buttonWidget);
						Component name = Component.translatable(this.translationPrefix + info.field.getName());
						String key = translationPrefix + info.field.getName() + ".tooltip";

						if (info.error != null && text.equals(name))
							renderTooltip(matrices, info.error.getValue(), mouseX, mouseY);
						else if (I18n.exists(key) && text.equals(name)) {
							List<Component> list = new ArrayList<>();
							for (String str : I18n.get(key).split("\n"))
								list.add(Component.literal(str));
							renderComponentTooltip(matrices, list, mouseX, mouseY);
						}
					}
				}
			}
			super.render(matrices, mouseX, mouseY, delta);
		}
	}

	@Environment(EnvType.CLIENT)
	public static class MidnightConfigListWidget extends ContainerObjectSelectionList<ButtonEntry> {
		Font font;

		public MidnightConfigListWidget(Minecraft minecraftClient, int i, int j, int k, int l, int m) {
			super(minecraftClient, i, j, k, l, m);
			this.centerListVertically = false;
			font = minecraftClient.font;
		}

		@Override
		public int getScrollbarPosition() {
			return this.width - 7;
		}

		public void addButton(List<AbstractWidget> buttons, Component text, EntryInfo info) {
			this.addEntry(ButtonEntry.create(buttons, text, info));
		}

		@Override
		public int getRowWidth() {
			return 10000;
		}

		public Optional<AbstractWidget> getHoveredButton(double mouseX, double mouseY) {
			for (ButtonEntry buttonEntry : this.children()) {
				if (!buttonEntry.buttons.isEmpty() && buttonEntry.buttons.get(0).isMouseOver(mouseX, mouseY)) {
					return Optional.of(buttonEntry.buttons.get(0));
				}
			}
			return Optional.empty();
		}
	}

	public static class ButtonEntry extends ContainerObjectSelectionList.Entry<ButtonEntry> {
		private static final Font font = Minecraft.getInstance().font;
		public final List<AbstractWidget> buttons;
		private final Component text;
		public final EntryInfo info;
		private final List<AbstractWidget> children = new ArrayList<>();
		public static final Map<AbstractWidget, Component> buttonsWithText = new HashMap<>();

		private ButtonEntry(List<AbstractWidget> buttons, Component text, EntryInfo info) {
			if (!buttons.isEmpty())
				buttonsWithText.put(buttons.get(0), text);
			this.buttons = buttons;
			this.text = text;
			this.info = info;
			children.addAll(buttons);
		}

		public static ButtonEntry create(List<AbstractWidget> buttons, Component text, EntryInfo info) {
			return new ButtonEntry(buttons, text, info);
		}

		public void render(PoseStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX,
				int mouseY, boolean hovered, float tickDelta) {
			buttons.forEach(b -> {
				b.setY(y);
				b.render(matrices, mouseX, mouseY, tickDelta);
			});
			if (text != null && (!text.getString().contains("spacer") || !buttons.isEmpty())) {
				if (info.centered)
					font.drawShadow(matrices, text,
							Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2f - (font.width(text) / 2f),
							y + 5, 0xFFFFFF);
				else
					GuiComponent.drawString(matrices, font, text, 12, y + 5, 0xFFFFFF);
			}
		}

		public List<? extends GuiEventListener> children() {
			return children;
		}

		@Override
		public List<? extends NarratableEntry> narratables() {
			return children;
		}
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface Entry {
		int width() default 100;

		double min() default Double.MIN_NORMAL;

		double max() default Double.MAX_VALUE;

		String name() default "";

		boolean isColor() default false;
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface Client {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface Server {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface Hidden {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface Comment {
		boolean centered() default false;
	}

	public static class HiddenAnnotationExclusionStrategy implements ExclusionStrategy {
		public boolean shouldSkipClass(Class<?> clazz) {
			return false;
		}

		public boolean shouldSkipField(FieldAttributes fieldAttributes) {
			return fieldAttributes.getAnnotation(Entry.class) == null;
		}
	}
}
