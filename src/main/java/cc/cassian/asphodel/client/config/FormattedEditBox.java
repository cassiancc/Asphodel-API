//? if >26 {
package cc.cassian.asphodel.client.config;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class FormattedEditBox extends EditBox {
	public boolean parsed = true;

	public FormattedEditBox(Font font, int buttonWidth, int i, MutableComponent empty) {
		super(font, buttonWidth, i, empty);
		this.addFormatter((text, offset) -> {
			if (!parsed)
				return Component.literal(text).withStyle(ChatFormatting.RED, ChatFormatting.ITALIC).getVisualOrderText();
			return null;
		});
	}
}
//?}