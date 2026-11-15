//? if >1.21 {
package cc.cassian.asphodel.util.deferred_registry;

import cc.cassian.asphodel.util.RegistryUtils;
import net.minecraft.core.Registry;
//? if >1.21
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class DeferredRegister<T> {
	private final Registry<T> registry;
	private final String modid;
	private final List<Runnable> callbacks = new ArrayList<>();

	public DeferredRegister(Registry<T> registry, String modId) {
		this.registry = registry;
		this.modid = modId;
	}

	public static <A> DeferredRegister<A> create(ResourceKey<Registry<A>> registryKey, String modId) {
		return new DeferredRegister<>((Registry<A>) RegistryUtils.getValue(BuiltInRegistries.REGISTRY, registryKey.asphodel$identifier()), modId);
	}

	private static <A> DeferredRegister<A> create(Registry<A> registry, String modId) {
		return new DeferredRegister<>(registry, modId);
	}

	public static DataComponents createDataComponents(ResourceKey<Registry<DataComponentType<?>>> registryResourceKey, String modId) {
		return new DataComponents(registryResourceKey, modId);
	}

	public String namespace() {
		return modid;
	}

	public Registry<T> registry() {
		return registry;
	}

	public static Blocks createBlocks(String modId) {
		return new Blocks(modId);
	}

	public <A extends T> DeferredHolder<T, A> register(String name, Supplier<T> o) {
		DeferredHolder<T, A> holder = new DeferredHolder<>(ResourceKey.create(registry.key(), Identifier.fromNamespaceAndPath(namespace(), name)), (Supplier<A>) o);
		entries.add((DeferredHolder<T, T>) holder);
		return holder;
	}

	public List<DeferredHolder<T, T>> entries = new ArrayList<>();

	public List<DeferredHolder<T, T>> getEntries() {
		return entries;
	}

	public void register() {
		for (DeferredHolder<T, T> entry : entries) {
			T register = Registry.register(registry, entry.getId(), entry.get());
			entry.setValue(register);
		}
		for (Runnable callback : getCallbacks()) {
			callback.run();
		}
	}

	public List<Runnable> getCallbacks() {
		return callbacks;
	}

	/// Add a callback that will be run after all values are registered.
	public boolean addCallback(Runnable callback) {
		return callbacks.add(callback);
	}

	public static class Items {

		private final DeferredRegister<Item> register;

		public Items(String modId) {
			this.register = DeferredRegister.create(BuiltInRegistries.ITEM, modId);
		}

		public static Items createItems(String modId) {
			return new Items(modId);
		}

		public <I extends Item> DeferredItem<I> register(String name, Supplier<I> o) {
			Identifier id = Identifier.fromNamespaceAndPath(register.namespace(), name);
			return new DeferredItem<>(id, register.register(name, (Supplier<Item>) o));
		}

		public <I extends Item> DeferredItem<I> register(String name, Function<Item.Properties, I> o, Item.Properties properties) {
			Identifier id = Identifier.fromNamespaceAndPath(register.namespace(), name);
			return register(name, () -> o.apply(properties
					//? if >1.21.2
					.setId(ResourceKey.create(Registries.ITEM, id))
					));
		}

		public void register() {
			register.register();
		}

		public List<DeferredHolder<Item, Item>> getEntries() {
			return register.entries;
		}
	}

	public static class Blocks {

		private final DeferredRegister<Block> register;

		public Blocks(String modId) {
			this.register = DeferredRegister.create(BuiltInRegistries.BLOCK, modId);
		}

		public <I extends Block> DeferredBlock<I> register(String name, Supplier<I> o) {
			Identifier id = Identifier.fromNamespaceAndPath(register.namespace(), name);
			return new DeferredBlock<>(id, register.register(name, (Supplier<Block>) o));
		}

		public <I extends Block> DeferredBlock<I> register(String name, Function<BlockBehaviour.Properties, I> o, BlockBehaviour.Properties properties) {
			Identifier id = Identifier.fromNamespaceAndPath(register.namespace(), name);
			return register(name, () -> o.apply(properties
					//? if >1.21.2
					.setId(ResourceKey.create(Registries.BLOCK, id))
					));
		}

		public void register() {
			register.register();
		}
	}

	public static class DataComponents {
		private final DeferredRegister<DataComponentType<?>> register;

		public DataComponents(ResourceKey<Registry<DataComponentType<?>>> dataComponentType, String modId) {
			this.register = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, modId);
		}

		public <A> DeferredHolder<DataComponentType<?>, DataComponentType<A>> registerComponentType(String name, Consumer<DataComponentType.Builder<A>> stuff) {
			DataComponentType.Builder<A> builder = DataComponentType.builder();
			stuff.accept(builder);
			return register.register(name, builder::build);
		}

		public void register() {
			register.register();
		}
	}
}
//?}