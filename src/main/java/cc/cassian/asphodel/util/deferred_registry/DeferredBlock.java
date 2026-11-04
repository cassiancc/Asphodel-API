package cc.cassian.asphodel.util.deferred_registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class DeferredBlock<I extends Block> extends DeferredHolder<Block, I> {

	public DeferredBlock(Identifier id, Supplier<I> item) {
		super(ResourceKey.create(Registries.BLOCK, id), item);
	}

}
