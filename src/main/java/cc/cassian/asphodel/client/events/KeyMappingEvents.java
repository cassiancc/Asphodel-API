package cc.cassian.asphodel.client.events;


import net.minecraft.client.KeyMapping;
//? fabric && >26 {
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
//?} else if fabric {
/*import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
*///?} else if neoforge {
/*import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import cc.cassian.asphodel.neoforge.ForgeUtil;
*///?}
import net.minecraft.resources.Identifier;
//? forge {
/*import cc.cassian.asphodel.forge.ForgeUtil;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
*///?}

public class KeyMappingEvents {
	protected static void registerKeyMapping(String modId, KeyMapping mapping) {
		//? fabric && >26{
		KeyMappingHelper.registerKeyMapping(mapping);
		//?} else if fabric{
		/*KeyBindingHelper.registerKeyBinding(mapping);
		*///?} else if neoforge || forge {
		/*ForgeUtil.getEventBus(modId).addListener(EventPriority.NORMAL, true, RegisterKeyMappingsEvent.class, event-> event.register(mapping));
		*///?}
	}

	//~ if <1.21.8 'static KeyMapping.Category'->'static String' {
	public static KeyMapping.Category registerKeyMappingCategory(String modId, Identifier location) {
	//~}
		//? if neoforge && >1.21.8 {
		/*KeyMapping.Category category = new KeyMapping.Category(location);
		ForgeUtil.getEventBus(modId).addListener(EventPriority.NORMAL, true, RegisterKeyMappingsEvent.class, event-> event.registerCategory(category));
		return category;
		*///?} else if >1.21.8 {
		return KeyMapping.Category.register(location); // The category translation key used to categorize in the Controls screen
		//?} else {
		/*return location.toLanguageKey("key.category");
		 *///?}
	}


}
