//? if <26.2 {
package cc.cassian.asphodel.tags;

import cc.cassian.asphodel.references.Identifiable;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public record BlockItemTagId(TagKey<Block> block, TagKey<Item> item) implements Identifiable {
	public static BlockItemTagId create(final Identifier blockId, final Identifier itemId) {
		return new BlockItemTagId(TagKey.create(Registries.BLOCK, blockId), TagKey.create(Registries.ITEM, itemId));
	}

	public static BlockItemTagId create(final Identifier id) {
		return create(id, id);
	}
}
//?}