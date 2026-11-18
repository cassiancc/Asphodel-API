package cc.cassian.asphodel.client.events;

import com.mojang.serialization.MapCodec;

//? if >26
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
//? if >=26.4 {
/*import net.minecraft.client.core.registries.BuiltInClientRegistries;
*///?}
//? fabric && >26 {
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
//?}
//? neoforge && >26 {
/*import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
*///?}

public class ItemPropertyEvents {
	//? if >26 {
	protected static void registerRangeSelectItemProperty(String modId, Identifier id, MapCodec<? extends RangeSelectItemModelProperty> properties) {
		//? if >=26.4 {
		/*Registry.register(BuiltInClientRegistries.RANGE_SELECT_ITEM_MODEL_PROPERTY_TYPE, id, properties);
		*///?} fabric{
		RangeSelectItemModelProperties.ID_MAPPER.put(id, properties);
		//?} else if neoforge {
		/*ModList.get().getModContainerById(modId).orElseThrow().getEventBus().addListener(RegisterRangeSelectItemModelPropertyEvent.class, event->{
			event.register(id, properties);
		});
		*///?}
	}
	//?}
}
