package cc.cassian.asphodel.client.fabric;

import cc.cassian.asphodel.client.util.Overlay;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
//? if >1.21.2 {
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
 //?} else {
/*import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
*///?}


public class ClientVersionedUtil {
	public static void registerKeyMapping(KeyMapping keyMapping) {
		//? if <26 {
		/*KeyBindingHelper.registerKeyBinding(keyMapping);
		*///?} else {
        KeyMappingHelper.registerKeyMapping(keyMapping);
        //?}
	}

	public static void registerOverlay(Identifier id, Overlay overlay) {
		//? if >26.3 {
		/*HudElementRegistry.addFirst(id, (guiGraphicsExtractor)->overlay.render(guiGraphicsExtractor, guiGraphicsExtractor.deltaTracker()));
		*///?} else if >1.21.5 {
		HudElementRegistry.addFirst(id, overlay::render);
		 //?} else {
		/*HudRenderCallback.EVENT.register(overlay::render);
		*///?}
	}
}