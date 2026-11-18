package cc.cassian.asphodel.client.events;

//? if >26
import com.mojang.serialization.MapCodec;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

import java.util.function.Consumer;

public class ClientEventDispatcher {
	private final String namespace;

	public ClientEventDispatcher(String namespace) {
		this.namespace = namespace;
	}

	//? if >26 {
	public void registerRangeSelectItemProperty(Identifier id, MapCodec<net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty> properties) {
		ItemPropertyEvents.registerRangeSelectItemProperty(namespace, id, properties);
	}
	//?}

	public void modifyItemTooltip(Consumer<ItemTooltipEvents.TooltipAccess> tooltip) {
		ItemTooltipEvents.modifyItemTooltip(namespace, tooltip);
	}

	public void registerKeyMapping(KeyMapping mapping) {
		KeyMappingEvents.registerKeyMapping(namespace, mapping);
	}

	//~ if <1.21.8 'KeyMapping.Category'->'String'
	public KeyMapping.Category registerKeyMappingCategory(Identifier category) {
		return KeyMappingEvents.registerKeyMappingCategory(namespace, category);
	}

}
