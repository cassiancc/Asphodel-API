//? fabric {
//~ if <26 'FabricTagsProvider'->'FabricTagProvider' {
//~ if <26 '.ItemTagsProvider'->'.ItemTagProvider' {
//~ if <26 'BlockTagsProvider'->'BlockTagProvider' {
//~ if <26 'FabricPackOutput'->'FabricDataOutput' {
package cc.cassian.asphodel.fabric.datagen;

import cc.cassian.asphodel.references.Identifiable;
//~ if >=26.2 'cc.cassian.asphodel'->'net.minecraft' {
import net.minecraft.tags.BlockItemTagId;
import net.minecraft.references.BlockItemId;
//~}
import cc.cassian.asphodel.util.CommonUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
//? if >26.1 {
import net.minecraft.data.tags.BlockItemTagAppender;
//?}
//? if >26 {
import net.minecraft.data.tags.TagAppender;
//?}
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class MultiversionedItemTagsProvider extends FabricTagsProvider.ItemTagsProvider {

	public MultiversionedItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture, BlockTagsProvider blockTagsProvider) {
		super(output, registryLookupFuture, blockTagsProvider);
	}

	public MultiversionedItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output, registryLookupFuture);
	}

	public class MultiversionedItemTagBuilder {
		//? if >=26.2 {
		private final BlockItemTagAppender<Item> valueLookupBuilder;
		//?} else if >1.21.2 {
		/*private TagAppender<Item, Item> valueLookupBuilder;
		*///?} else {
		/*private FabricTagsProvider<Item>.FabricTagBuilder valueLookupBuilder;
		 *///?}

		private TagBuilder rawBuilder;

		public MultiversionedItemTagBuilder(TagKey<Item> tag) {
			//? if >=26.2 {
			this.valueLookupBuilder = builder(tag);
			//?} else if >1.21.2 {
			/*this.valueLookupBuilder = valueLookupBuilder(tag);
			*///?} else {
			/*this.valueLookupBuilder = getOrCreateTagBuilder(tag);
			 *///?}

			this.rawBuilder = getOrCreateRawBuilder(tag);
		}

		public MultiversionedItemTagBuilder add(Item item) {
			//? if >=26.2 {
			rawBuilder = rawBuilder.addElement(item.asphodel$identifier());
			//?} else {
			/*valueLookupBuilder = valueLookupBuilder.add(item);
			*///?}
			return this;
		}

		public MultiversionedItemTagBuilder add(Supplier<Item> item) {
			return add(item.get());
		}

		public MultiversionedItemTagBuilder add(Holder<Item> item) {
			return add(item.value());
		}

		public MultiversionedItemTagBuilder add(Identifiable item) {
			if (item instanceof TagKey<?>)
				rawBuilder = rawBuilder.addOptionalTag(item.asphodel$identifier());
			else rawBuilder = rawBuilder.addElement(item.asphodel$identifier());
			return this;
		}

		public MultiversionedItemTagBuilder add(Identifiable... item) {
			for (Identifiable identifiable : item) {
				if (identifiable instanceof TagKey<?>)
					rawBuilder = rawBuilder.addOptionalTag(identifiable.asphodel$identifier());
				else rawBuilder = rawBuilder.addElement(identifiable.asphodel$identifier());
			}
			return this;
		}

		public MultiversionedItemTagBuilder add(Stream<? extends Identifiable> item) {
			item.forEach(identifiable -> {
				if (identifiable instanceof TagKey<?>)
					rawBuilder = rawBuilder.addOptionalTag(identifiable.asphodel$identifier());
				else rawBuilder = rawBuilder.addElement(identifiable.asphodel$identifier());
			});
			return this;
		}

		public MultiversionedItemTagBuilder add(List<? extends Identifiable> item) {
			item.forEach(identifiable -> {
				if (identifiable instanceof TagKey<?>)
					rawBuilder = rawBuilder.addOptionalTag(identifiable.asphodel$identifier());
				else rawBuilder = rawBuilder.addElement(identifiable.asphodel$identifier());
			});
			return this;
		}

		public MultiversionedItemTagBuilder addAll(Stream<? extends Identifiable> item) {
			return add(item);
		}

		public MultiversionedItemTagBuilder addAll(List<? extends Identifiable> item) {
			return add(item);
		}

		public MultiversionedItemTagBuilder add(BlockItemId item) {
			return add(item.item().asphodel$identifier());
		}

		public MultiversionedItemTagBuilder addOptionalTag(BlockItemTagId itemTagKey) {
			return addOptionalTag(itemTagKey.item());
		}

		public MultiversionedItemTagBuilder add(ResourceKey<Item> itemTagKey) {
			return add(itemTagKey.asphodel$identifier());
		}

		public MultiversionedItemTagBuilder addOptionalTag(TagKey<Item> itemTagKey) {
			rawBuilder = rawBuilder.addOptionalTag(itemTagKey.asphodel$identifier());
			return this;
		}

		public MultiversionedItemTagBuilder add(Item... items) {
			//? if >=26.2 {
			for (Item item : items) {
				rawBuilder = rawBuilder.addElement(item.asphodel$identifier());
			}
			//?} else {
			/*valueLookupBuilder = valueLookupBuilder.add(items);
			 *///?}
			return this;
		}

		public MultiversionedItemTagBuilder addOptional(Identifier item) {
			rawBuilder = rawBuilder.addOptionalElement(item);
			return this;
		}

		public MultiversionedItemTagBuilder add(Identifier item) {
			rawBuilder = rawBuilder.addElement(item);
			return this;
		}
	}

	public void copy(BlockItemTagId... blockItemTagIds) {
		for (BlockItemTagId blockItemTagId : blockItemTagIds) {
			super.copy(blockItemTagId.block(), blockItemTagId.item());
		}
	}

	protected MultiversionedItemTagBuilder tagBuilder(TagKey<Item> tag) {
		return new MultiversionedItemTagBuilder(tag);
	}

	protected MultiversionedItemTagBuilder tagBuilder(BlockItemTagId tag) {
		return new MultiversionedItemTagBuilder(tag.item());
	}

	public static TagKey<Item> conventionTag(String id) {
		return CommonUtils.itemTag("c", id);
	}
}
//~}
//~}
//~}
//~}
//?}