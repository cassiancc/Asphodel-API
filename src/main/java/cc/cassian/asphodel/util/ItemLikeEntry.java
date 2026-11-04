package cc.cassian.asphodel.util;

import cc.cassian.asphodel.references.Identifiable;
import net.minecraft.core.registries.Registries;
//~ if >=26.2 'cc.cassian.asphodel.references'->'net.minecraft.references'
import cc.cassian.asphodel.references.BlockItemId;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A pair of a block or item and its ID. Primary benefit over a direct value is for 26.2+ data generation, which requires the ID to be stored separately from the block.
 * @deprecated: Blocks and items now have a helper method for their resource key.
 */
@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated
public record ItemLikeEntry<T extends ItemLike>(Identifier id, T value) implements ItemLike, Identifiable {

	@Override
	public Item asItem() {
		return value.asItem();
	}

	public T get() {
		return value();
	}

	public Identifier identifier() {
		return id;
	}

	public Identifier asphodel$identifier() {
		return id;
	}

	public Identifier id() {
		return asphodel$identifier();
	}

	public String getNamespace() {
		return asphodel$identifier().getNamespace();
	}

	public String getPath() {
		return asphodel$identifier().getPath();
	}

	public boolean isVanilla() {
		return id().getNamespace().equals("minecraft");
	}

	public boolean is(ItemStack heldItem) {
		return heldItem.is(this.value.asItem());
	}

	public ResourceKey<Block> blockKey() {
		return ResourceKey.create(Registries.BLOCK, this.id());
	}

	public ResourceKey<Item> itemKey() {
		return ResourceKey.create(Registries.ITEM, this.id());
	}

	public BlockItemId blockItemId() {
		return new BlockItemId(blockKey(), itemKey());
	}

	public BlockState defaultBlockState() {
		if (value() instanceof Block block) {
			return block.defaultBlockState();
		} else {
			throw new IllegalStateException("Cannot call defaultBlockState on %s, as it is not a Block.".formatted(id));
		}
	}

	public ItemStack getDefaultInstance() {
		return value().asItem().getDefaultInstance();
	}

	@Override
	public String toString() {
		return asphodel$identifier().toString();
	}
}