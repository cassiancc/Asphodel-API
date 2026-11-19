//? if >26 {
package cc.cassian.asphodel.client.config;

import cc.cassian.asphodel.AsphodelAPI;
import cc.cassian.asphodel.Platform;
import cc.cassian.asphodel.client.util.HudUtils;
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

import java.util.*;

import static cc.cassian.asphodel.client.config.ConfigUtil.*;

public class KaleidoConfigScreen extends AbstractConfigScreen {
	private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 32, 32);
	private final ConfigScreenContext context;
	public int buttonWidth;
	@SuppressWarnings("all")
	protected ScrollableLayout scrollableLayout;

	public KaleidoConfigScreen(ConfigScreenContext context, Screen parent) {
		super(context.modName(), parent);
		this.context = context;
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor graphics
								  //? if <26.4
			, int mouseX, int mouseY, float a
	) {
		super.extractBackground(graphics
				//? if <26.4
				, mouseX, mouseY, a
		);
		extractListBackground(graphics);
		extractListSeparators(graphics);
	}

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

		for (TrackedValue<?> value : context.config().values()) {
			var first = value.key().getKeyComponent(0);
			var last = value.key().getLastComponent();
			GridLayout gridLayout = createGridLayout();
			GridData data = new GridData(gridLayout, gridLayout.createRowHelper(2));
			if (!Objects.equals(first, last)) {
				layoutHelper.putIfAbsent(first, data);
			} else {
				layoutHelper.putIfAbsent(context.id(), data);
			}
		}

		for (TrackedValue<?> value : context.config().values()) {
			addValue(value, layoutHelper);
		}

		layoutHelper.forEach((key, element)->{
			var title = key.equals(context.id()) ? context.modName() : formatText(key);
			linearLayout.addChild(new StringWidget(title, this.font));
			linearLayout.addChild(element.gridLayout());
		});


		// done

		this.addRenderableWidget(this.layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, button -> this.onClose()).size(100, 20).build()));

		// finalize

		finalizeLayout(linearLayout, layout, this);
	}

	private <T> void addValue(TrackedValue<T> value, Map<String, GridData> generalHelper) {
		var commentList = fieldTooltip(value);
		if (value.value() instanceof Boolean b) {
			GridLayout.RowHelper helper = getHelper(value, generalHelper);
			addBooleanOption(helper, fieldName(value), commentList,  b, (cycleButton, newValue )-> value.setValue((T) newValue));
		}
		else if (value.value() instanceof Enum<?> e) {
			GridLayout.RowHelper helper = getHelper(value, generalHelper);
			addEnumOption(helper, fieldName(value), commentList, e, e.getClass().getEnumConstants(),  (cycleButton, newValue )-> value.setValue((T) newValue));
		}
		else if (value.value() instanceof String string) {
			GridLayout.RowHelper helper = getHelper(value, generalHelper);
			addStringOption(helper, (TrackedValue<String>) value, commentList, string);
		}
		else if (value.value() instanceof Integer i) {
			GridLayout.RowHelper helper = getHelper(value, generalHelper);
			addIntegerOption(helper, (TrackedValue<Integer>) value, commentList, i);
		}
		else if (Platform.INSTANCE.isDeveloperEnvironment()) {
			AsphodelAPI.LOGGER.info("Unfamiliar with type {}", value.getDefaultValue().getClass());
		}
	}

	private <T> GridLayout.RowHelper getHelper(TrackedValue<T> value, Map<String, GridData> generalHelper) {
		return generalHelper.getOrDefault(value.key().getKeyComponent(0), generalHelper.get(context.id())).rowHelper();
	}

	static void finalizeLayout(LinearLayout linearLayout, HeaderAndFooterLayout layout, KaleidoConfigScreen screen) {
		screen.addRenderableWidget(layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, button -> screen.onClose()).size(100, 20).build()));

		screen.scrollableLayout = layout.addToContents(new ScrollableLayout(Minecraft.getInstance(), linearLayout, screen.layout.getContentHeight()) {});
		screen.scrollableLayout.arrangeElements();

		screen.scrollableLayout.visitWidgets(screen::addRenderableWidget);
		layout.arrangeElements();
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
		FormattedEditBox widget = new FormattedEditBox(font, buttonWidth, 20, Component.empty());
		widget.setHint(fieldName(value));
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

	private void addIntegerOption(GridLayout.RowHelper linearLayout, TrackedValue<Integer> value, List<Component> tooltip, Integer currentValue) {
		FormattedEditBox widget = new FormattedEditBox(font, buttonWidth, 20, Component.empty());
		widget.setHint(fieldName(value));
		widget.setValue(String.valueOf(currentValue));
		widget.setResponder((newValue )-> {
			widget.parsed = true;
			try {
				value.setValue(Integer.valueOf(newValue));
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

	private static Tooltip getTooltip(List<Component> comments) {
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
		context.config().save();
		super.onClose();
	}

	private record GridData(GridLayout gridLayout, GridLayout.RowHelper rowHelper) {
	}
}
//?}