package cc.cassian.asphodel.util.deferred_registry;

import cc.cassian.asphodel.references.Identifiable;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DeferredHolder<I, S extends I>
	//? if >1.21
		extends Holder.Reference<I>
		implements Supplier<S>, Identifiable {
	private final Identifier id;
	private Supplier<S> value;
	private final ResourceKey<I> key;


	public DeferredHolder(ResourceKey<I> id, Supplier<S> value) {
		//? if >1.21
		super(Type.STAND_ALONE, null, id, null);
		this.id = id.asphodel$identifier();
		this.key = id;
		this.value = value;
	}

	//? if >1.21
	@Override
	public I value() {
		return get();
	}

	public Identifier getId() {
		return id;
	}

	@Override
	public S get() {
		return value.get();
	}

	public ResourceKey<I> getKey() {
		return key;
	}

	public void setValue(S register) {
		value = ()-> register;
	}

	@Override
	public Identifier asphodel$identifier() {
		return id;
	}
}
