package cc.cassian.asphodel.client.events;

import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;

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
}
