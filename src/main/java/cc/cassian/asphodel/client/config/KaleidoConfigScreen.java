package cc.cassian.asphodel.client.config;

import cc.cassian.asphodel.client.util.HudUtils;
import folk.sisby.kaleido.lib.quiltconfig.api.Config;
import folk.sisby.kaleido.lib.quiltconfig.api.metadata.Comments;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import org.apache.commons.lang3.text.WordUtils;
import org.jspecify.annotations.NonNull;

import java.util.*;

public class KaleidoConfigScreen extends Screen {
	public static final int PADDING = 4;

	private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 32, 32);
	private final Config configs;
	private final String id;
	private final Screen parent;
	public int buttonWidth;
	protected ScrollableLayout scrollableLayout;

	public KaleidoConfigScreen(String id, Component modName, Config config, Screen parent) {
		super(modName);
		this.id = id;
		this.parent = parent;
		this.configs = config;
	}


	@Override
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		super.extractBackground(graphics, mouseX, mouseY, a);
		extractListBackground(graphics);
		extractListSeparators(graphics);
	}

	private static final Identifier MENU_LIST_BACKGROUND = Identifier.withDefaultNamespace("textures/gui/menu_list_background.png");
	private static final Identifier INWORLD_MENU_LIST_BACKGROUND = Identifier.withDefaultNamespace("textures/gui/inworld_menu_list_background.png");

	protected void extractListBackground(final GuiGraphicsExtractor graphics) {
		Identifier menuListBackground = this.minecraft.level == null ? MENU_LIST_BACKGROUND : INWORLD_MENU_LIST_BACKGROUND;
		HudUtils.blit(
				graphics,
				menuListBackground,
				0,
				scrollableLayout.getY()-PADDING,
				(float)scrollableLayout.getX()+scrollableLayout.getWidth(),
				(float)(scrollableLayout.getY()+scrollableLayout.getHeight() + (int)scrollableLayout.container.scrollAmount()),
				this.width,
				scrollableLayout.getHeight()+ PADDING,
				32,
				32
		);
	}


	protected void extractListSeparators(final GuiGraphicsExtractor graphics) {
		Identifier headerSeparator = this.minecraft.level == null ? Screen.HEADER_SEPARATOR : Screen.INWORLD_HEADER_SEPARATOR;
		Identifier footerSeparator = this.minecraft.level == null ? Screen.FOOTER_SEPARATOR : Screen.INWORLD_FOOTER_SEPARATOR;
		HudUtils.blit(graphics, headerSeparator, 0, scrollableLayout.getY() - PADDING - 2, 0.0F, 0.0F, this.width, 2, 32, 2);
		HudUtils.blit(graphics, footerSeparator, 0, scrollableLayout.getY()+ scrollableLayout.getHeight(), 0.0F, 0.0F, this.width, 2, 32, 2);
	}



	@Override
	protected void init() {

		// setup


		StringWidget stringWidget = this.layout.addToHeader(new StringWidget(title, this.font));
		this.addRenderableWidget(stringWidget);
		LinearLayout linearLayout = LinearLayout.vertical().spacing(2);
		this.buttonWidth = (int) (this.width / 2.5);

		Map<String, GridData> layoutHelper = new LinkedHashMap<>();

		for (TrackedValue<?> value : configs.values()) {
			var first = value.key().getKeyComponent(0);
			var last = value.key().getLastComponent();
			GridLayout gridLayout = createGridLayout();
			GridData data = new GridData(gridLayout, gridLayout.createRowHelper(2));
			if (!Objects.equals(first, last)) {
				layoutHelper.putIfAbsent(first, data);
			} else {
				layoutHelper.putIfAbsent(id, data);
			}
		}

		for (TrackedValue<?> value : configs.values()) {
			addValue(value, layoutHelper);
		}

		layoutHelper.forEach((key, element)->{
			linearLayout.addChild(new StringWidget(formatText(key), this.font));
			linearLayout.addChild(element.gridLayout());
		});


		// done

		this.addRenderableWidget(this.layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, button -> this.onClose()).size(100, 20).build()));

		// finalize

		finalizeLayout(linearLayout, layout, this);
	}

	private static MutableComponent formatText(String key) {
		return Component.literal(formatString(key));
	}

	private static String formatString(String key) {
		return WordUtils.capitalize(camelToSnake(key).replace("_", " "));
	}

	// Function to convert camel case string to snake case string.
	public static String camelToSnake(String str) {
		StringBuilder result = new StringBuilder();

		// Lowercase and append first character to result string
		result.append(Character.toLowerCase(str.charAt(0)));

		// Traverse the string from first index to last index
		for (int i = 1; i < str.length(); i++) {

			char ch = str.charAt(i);

			// Check if the character is upper case
			// then append '_' and such character
			// (in lower case) to result string
			if (Character.isUpperCase(ch)) {
				if (Character.isLowerCase(str.charAt(i-1)))
					result.append('_');
				result.append(Character.toLowerCase(ch));
			}

			// If the character is lower case, add the character to the result string
			else {
				result.append(ch);
			}
		}

		// return the result
		return result.toString();
	}

	private <T> void addValue(TrackedValue<T> value, Map<String, GridData> generalHelper) {
		var commentList = new ArrayList<Component>();
		value.metadata().forEach((metadataType, o) -> {
			if (o instanceof Comments comments) {
				comments.forEach(c->commentList.add(Component.literal(c)));
			}
		});
		if (commentList.isEmpty()) {
			commentList.add(0, getName(value));
		}
		if (value.value() instanceof Boolean b) {
			GridLayout.RowHelper helper = getHelper(value, generalHelper);
			addBooleanOption(helper, getName(value), commentList,  b, (cycleButton, newValue )-> value.setValue((T) newValue));
		}
		else if (value.value() instanceof Enum<?> e) {
			GridLayout.RowHelper helper = getHelper(value, generalHelper);
			addEnumOption(helper, getName(value), commentList, e, e.getClass().getEnumConstants(),  (cycleButton, newValue )-> value.setValue((T) newValue));
		}
		else if (value.value() instanceof String string) {
			GridLayout.RowHelper helper = getHelper(value, generalHelper);
			addStringOption(helper, (TrackedValue<String>) value, commentList, string);
		}
		else {
//			System.out.println(value.value().getClass());
		}
	}

	private <T> Component getName(TrackedValue<T> value) {
		return formatText(value.key().getLastComponent());
	}

	private <T> GridLayout.RowHelper getHelper(TrackedValue<T> value, Map<String, GridData> generalHelper) {
		return generalHelper.getOrDefault(value.key().getKeyComponent(0), generalHelper.get(id)).rowHelper();
	}

	static void finalizeLayout(LinearLayout linearLayout, HeaderAndFooterLayout layout, KaleidoConfigScreen screen) {
		screen.addRenderableWidget(layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, button -> screen.onClose()).size(100, 20).build()));

		screen.scrollableLayout = layout.addToContents(new ScrollableLayout(Minecraft.getInstance(), linearLayout, screen.layout.getContentHeight()) {});
		screen.scrollableLayout.arrangeElements();

		screen.scrollableLayout.visitWidgets(screen::addRenderableWidget);
		layout.arrangeElements();
	}

	GridLayout createGridLayout() {
		var gridLayout = new GridLayout();
		gridLayout.defaultCellSetting().paddingHorizontal(4).paddingBottom(4).alignHorizontallyCenter();
		return gridLayout;
	}

	private <T extends Enum<?>> void addEnumOption(GridLayout.RowHelper linearLayout, Component key, List<Component> comments, T initialValue, T[] values, CycleButton.OnValueChange<T> newValueSetter) {
		CycleButton<T> widget = CycleButton.builder((value) -> formatText(String.valueOf(value)).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD)), initialValue)
				.withValues(values)
				.create(0, 0, buttonWidth, 20, key, newValueSetter);
		addTooltip(comments, widget);
		linearLayout.addChild(
				widget
		);
	}

	private void addBooleanOption(GridLayout.RowHelper linearLayout, Component key, List<Component> tooltip, boolean currentValue, CycleButton.OnValueChange<Boolean> newValueSetter) {
		CycleButton<Boolean> widget = CycleButton.booleanBuilder(Component.translatable("asphodel_api.config.enabled").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GREEN)), Component.translatable("asphodel_api.config.disabled").setStyle(Style.EMPTY.applyFormat(ChatFormatting.RED)), currentValue).create(0, 0, buttonWidth, 20, key, newValueSetter);
		addTooltip(tooltip, widget);
		linearLayout.addChild(widget);
	}

	private void addStringOption(GridLayout.RowHelper linearLayout, TrackedValue<String> value, List<Component> tooltip, String currentValue) {
		StringEditBox widget = new StringEditBox(font, buttonWidth, 20, Component.empty());
		widget.setHint(getName(value));
		widget.setValue(currentValue);
		widget.setResponder((newValue )-> {
			widget.parsed = true;
			try {
				value.setValue(newValue);
			} catch (Exception ignored) {
				widget.parsed = false;
			}
		});
		addTooltip(tooltip, widget);
		linearLayout.addChild(widget);
	}

//	private void addIntegerOption(GridLayout.RowHelper linearLayout, String key, int currentValue, CycleButton.OnValueChange<Integer> newValueSetter) {
//		CycleButton<Integer> widget = CycleButton.builder(c-> c == 0 ? Component.translatable("options.guiScale.auto") : Component.translatable("rrv.client_settings.unit.items", String.valueOf(c)), currentValue).withValues(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15).create(0, 0, buttonWidth, 20, clientSetting(key), newValueSetter);
//		addTooltip(key, widget);
//		linearLayout.addChild(widget);
//	}

	private static void addTooltip(List<Component> comments, AbstractWidget widget) {
		if (!comments.isEmpty()) {
			widget.setTooltip(getTooltip(comments));
		}
	}

	private static @NonNull Tooltip getTooltip(List<Component> comments) {
		MutableComponent builder = Component.empty();
		for (int i = 0; i < comments.size(); i++) {
			if (i > 0) {
				builder.append("\n");
			}
			Component str = comments.get(i);
			builder.append(str);
		}
		return Tooltip.create(builder);
	}

	@Override
	public void onClose() {
		configs.save();
		HudUtils.setScreen(this.getParentScreen());
	}

	private Screen getParentScreen() {
		return parent;
	}

	private record GridData(GridLayout gridLayout, GridLayout.RowHelper rowHelper) {
	}
}