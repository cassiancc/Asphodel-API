package cc.cassian.asphodel.util.deferred_registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class DeferredItem<I extends Item> extends DeferredHolder<Item, I> implements ItemLike {

	public DeferredItem(Identifier name, Supplier<I> item) {
		super(ResourceKey.create(Registries.ITEM, name), item);
	}

	@Override
	public Item asItem() {
		return get();
	}
}
