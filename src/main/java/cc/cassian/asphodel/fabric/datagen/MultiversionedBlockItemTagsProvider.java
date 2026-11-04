//? fabric {
//~ if <26 'FabricTagsProvider'->'FabricTagProvider' {
//~ if <26 '.BlockTagsProvider'->'.BlockTagProvider' {
//~ if <26 'FabricPackOutput'->'FabricDataOutput' {
package cc.cassian.asphodel.fabric.datagen;


import cc.cassian.asphodel.util.CommonUtils;
import cc.cassian.asphodel.references.Identifiable;
//~ if >=26.2 'cc.cassian.asphodel'->'net.minecraft' {
import cc.cassian.asphodel.tags.BlockItemTagId;
//~}
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public abstract class MultiversionedBlockItemTagsProvider {

	private final MultiversionedBlockTagsProvider blocks;
	private final MultiversionedItemTagsProvider items;

	public MultiversionedBlockItemTagsProvider(MultiversionedBlockTagsProvider blockTagsProvider, MultiversionedItemTagsProvider itemTagsProvider) {
		this.blocks = blockTagsProvider;
		this.items = itemTagsProvider;
	}

	public class MultiversionedBlockItemTagBuilder {
		private MultiversionedBlockTagsProvider.MultiversionedBlockTagBuilder blocks;
		private MultiversionedItemTagsProvider.MultiversionedItemTagBuilder items;

		public MultiversionedBlockItemTagBuilder(TagKey<Block> block, TagKey<Item> item) {
			this.blocks = MultiversionedBlockItemTagsProvider.this.blocks.tagBuilder(block);
			this.items = MultiversionedBlockItemTagsProvider.this.items.tagBuilder(item);
		}

		public MultiversionedBlockItemTagBuilder add(Identifiable... block) {
			return add(Arrays.asList(block));
		}

		public MultiversionedBlockItemTagBuilder add(Stream<? extends Identifiable> block) {
			return add(block.toList());
		}

		public MultiversionedBlockItemTagBuilder add(List<? extends Identifiable> block) {
			for (Identifiable identifiable : block) {
				if (identifiable instanceof TagKey<?>) {
					addTag(identifiable);
				} else if (identifiable instanceof BlockItemTagId blockItemTagId) {
					addTag(blockItemTagId);
				}  else {
					blocks = blocks.add(identifiable.asphodel$identifier());
					items = items.add(identifiable.asphodel$identifier());
				}
			}
			return this;
		}

		protected MultiversionedBlockItemTagBuilder addTag(Identifiable identifiable) {
			BlockItemTagId blockItemTagId = BlockItemTagId.create(identifiable.asphodel$identifier(), identifiable.asphodel$identifier());
			return addTag(blockItemTagId);
		}

		protected MultiversionedBlockItemTagBuilder addTag(BlockItemTagId blockItemTagId) {
			blocks = blocks.addOptionalTag(blockItemTagId);
			items = items.addOptionalTag(blockItemTagId);
			return this;
		}
	}

	protected MultiversionedBlockItemTagBuilder tagBuilder(TagKey<Block> block, TagKey<Item> item) {
		return new MultiversionedBlockItemTagBuilder(block, item);
	}

	protected MultiversionedBlockItemTagBuilder tagBuilder(BlockItemTagId tag) {
		return new MultiversionedBlockItemTagBuilder(tag.block(), tag.item());
	}

	public static TagKey<Block> conventionTag(String id) {
		return CommonUtils.blockTag("c", id);
	}
}
//~}
//~}
//~}
//?}