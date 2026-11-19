package cc.cassian.asphodel.client.config;

import cc.cassian.asphodel.client.util.HudUtils;
import cc.cassian.asphodel.util.CommonUtils;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.*;

public class SelectConfigScreen extends AbstractConfigScreen {
	public static final int PADDING = 4;

	private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 32, 32);
	private final ArrayList<ConfigScreenContext> context;
	public int buttonWidth;
	@SuppressWarnings("all")
	protected LinearLayout linearLayout;

	public SelectConfigScreen(String name, ArrayList<ConfigScreenContext> context, Screen parent) {
		super(Component.literal(name), parent);
		this.context = context;
	}

	@Override
	//~ if <26 'extract'->'render'
	public void extractBackground(GuiGraphicsExtractor graphics
								  //? if <26.4 && >1.21
			, int mouseX, int mouseY, float a
	) {
		//~ if <26 'extract'->'render'
		super.extractBackground(graphics
				//? if <26.4 && >1.21
				, mouseX, mouseY, a
		);
		extractListBackground(graphics);
		extractListSeparators(graphics);
	}

	//? if <1.21 {
	/*@Override
	public void render(GuiGraphicsExtractor guiGraphics, int i, int j, float f) {
		renderBackground(guiGraphics);
		super.render(guiGraphics, i, j, f);
	}
	*///?}

	protected void extractListBackground(final GuiGraphicsExtractor graphics) {
		//? if >26 {
		Identifier menuListBackground = this.minecraft.level == null ? MENU_LIST_BACKGROUND : INWORLD_MENU_LIST_BACKGROUND;
		HudUtils.blit(
				graphics,
				menuListBackground,
				0,
				layout.getHeaderHeight()-PADDING,
				(float) linearLayout.getX()+ linearLayout.getWidth(),
				layout.getY(),
				this.width,
				getContentHeight() + PADDING,
				32,
				32
		);
		//?}
	}

	private int getContentHeight() {
		//? if >1.21 {
		return layout.getContentHeight();
		//?} else {
		/*return layout.getHeight()-layout.getHeaderHeight()-layout.getFooterHeight();
		*///?}
	}


	protected void extractListSeparators(final GuiGraphicsExtractor graphics) {
		//? if >1.21 {
		Identifier headerSeparator = this.minecraft.level == null ? Screen.HEADER_SEPARATOR : Screen.INWORLD_HEADER_SEPARATOR;
		Identifier footerSeparator = this.minecraft.level == null ? Screen.FOOTER_SEPARATOR : Screen.INWORLD_FOOTER_SEPARATOR;
		HudUtils.blit(graphics, headerSeparator, 0, layout.getHeaderHeight() - PADDING - 2, 0.0F, 0.0F, this.width, 2, 32, 2);
		HudUtils.blit(graphics, footerSeparator, 0, layout.getHeaderHeight()+ layout.getContentHeight(), 0.0F, 0.0F, this.width, 2, 32, 2);
		//?}
	}

	@Override
	protected void init() {

		// setup


		StringWidget stringWidget = this.layout.addToHeader(new StringWidget(title, this.font));
		this.addRenderableWidget(stringWidget);
		//? if >1.21 {
		LinearLayout linearLayout = LinearLayout.vertical().spacing(2);
		//?} else {
		/*LinearLayout linearLayout = new LinearLayout(0, 0, LinearLayout.Orientation.VERTICAL);
		*///?}
		this.buttonWidth = (int) (this.width / 2.5);

		for (ConfigScreenContext value : context) {
			GridLayout gridLayout = createGridLayout();
			GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(2);
			rowHelper.addChild(Button.builder(ConfigUtil.formatText(value.config().id()), e->{HudUtils.setScreen(value.getConfigScreen().create(this));}).build());
			linearLayout.addChild(gridLayout);
		}


		// done

		this.addRenderableWidget(this.layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, button -> this.onClose()).size(100, 20).build()));

		// finalize

		finalizeLayout(linearLayout, layout, this);
	}

	static void finalizeLayout(LinearLayout linearLayout, HeaderAndFooterLayout layout, SelectConfigScreen screen) {
		screen.addRenderableWidget(layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, button -> screen.onClose()).size(100, 20).build()));

		screen.linearLayout = layout.addToContents(linearLayout);
		screen.linearLayout.arrangeElements();

		screen.linearLayout.visitWidgets(screen::addRenderableWidget);
		layout.arrangeElements();
	}
}
