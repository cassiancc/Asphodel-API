package cc.cassian.asphodel.client.config;

import cc.cassian.asphodel.client.util.HudUtils;
import cc.cassian.asphodel.util.CommonUtils;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class AbstractConfigScreen extends Screen {
	protected static final Identifier MENU_LIST_BACKGROUND = CommonUtils.parseId("textures/gui/menu_list_background.png");
	protected static final Identifier INWORLD_MENU_LIST_BACKGROUND = CommonUtils.parseId("textures/gui/inworld_menu_list_background.png");
	public static final int PADDING = 4;
	private final Screen parent;

	protected AbstractConfigScreen(Component component, Screen parent) {
		super(component);
		this.parent = parent;
	}

	GridLayout createGridLayout() {
		var gridLayout = new GridLayout();
		gridLayout.defaultCellSetting().paddingHorizontal(4).paddingBottom(4).alignHorizontallyCenter();
		return gridLayout;
	}

	@Override
	public void onClose() {
		HudUtils.setScreen(this.getParentScreen());
	}

	private Screen getParentScreen() {
		return parent;
	}
}
