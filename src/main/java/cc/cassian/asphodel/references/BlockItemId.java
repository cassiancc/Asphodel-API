//? if <26.2 {
/*package cc.cassian.asphodel.references;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public record BlockItemId(ResourceKey<Block> block, ResourceKey<Item> item) implements Identifiable {
	public static BlockItemId create(final Identifier blockId, final Identifier itemId) {
		return new BlockItemId(ResourceKey.create(Registries.BLOCK, blockId), ResourceKey.create(Registries.ITEM, itemId));
	}

	@Override
	public Identifier asphodel$identifier() {
		return block().asphodel$identifier();
	}

	@Override
	public ResourceKey<?> asphodel$key() {
		return block();
	}
}
*///?}