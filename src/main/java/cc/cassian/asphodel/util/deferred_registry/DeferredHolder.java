package cc.cassian.asphodel.util.deferred_registry;

import cc.cassian.asphodel.references.Identifiable;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;

import java.util.function.Supplier;

public class DeferredHolder<I, S extends I> extends Holder.Reference<I> implements Supplier<S>, Identifiable {
	private final Identifier id;
	private Supplier<S> item;
	private final ResourceKey<I> key;

	public DeferredHolder(ResourceKey<I> id, Supplier<S> item) {
		super(Type.STAND_ALONE, null, id, null);
		this.id = id.asphodel$identifier();
		this.key = id;
		this.item = item;
	}

	@Override
	public I value() {
		return get();
	}

	public Identifier getId() {
		return id;
	}

	@Override
	public S get() {
		return item.get();
	}

	public ResourceKey<I> getKey() {
		return key;
	}

	public void setValue(S register) {
		item = ()-> register;
	}

	@Override
	public Identifier asphodel$identifier() {
		return id;
	}
}
