package cc.cassian.asphodel.events;

//? fabric {
//? if >26 {
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
//?} else {
/*import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
*///?}
import net.minecraft.core.registries.BuiltInRegistries;
//?}
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
//? forge {
/*import cc.cassian.asphodel.forge.ForgeUtil;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
*///?}
//? neoforge {
/*import cc.cassian.asphodel.neoforge.ForgeUtil;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.bus.api.EventPriority;
*///?}

import java.util.*;
import java.util.function.Consumer;

public class CreativeTabEvent {
	protected static void modifyEntries(String modId, ResourceKey<CreativeModeTab> tab, Consumer<CreativeTabPlacements> tabPlacements) {
		//? fabric && >26{
		CreativeModeTabEvents.MODIFY_OUTPUT_ALL.register((creativeModeTab, fabricCreativeModeTabOutput) -> {
			if (tab.equals(BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(creativeModeTab).orElse(null))) {
				applyFabric(tabPlacements, fabricCreativeModeTabOutput);
			}
		});
		//?} else if neoforge || forge {
		/*ForgeUtil.getEventBus(modId).addListener(EventPriority.NORMAL, false, BuildCreativeModeTabContentsEvent.class, event->{
			if (event.getTabKey().equals(tab)) {
				applyForge(tabPlacements, event);
			}
		});
		*///?} else if fabric {
		/*ItemGroupEvents.MODIFY_ENTRIES_ALL.register((creativeModeTab, event) -> {
			if (tab.equals(BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(creativeModeTab).orElse(null))) {
				applyFabric(tabPlacements, event);
			}
		});
		*///?}

	}

	//? fabric && >26 {
	private static void applyFabric(Consumer<CreativeTabPlacements> tabPlacements, FabricCreativeModeTabOutput fabricCreativeModeTabOutput) {
		CreativeTabPlacements t = new CreativeTabPlacements();
		tabPlacements.accept(t);
		for (ItemStack addition : t.ADDITIONS) {
			fabricCreativeModeTabOutput.accept(addition);
		}
		t.AFTER.forEach(fabricCreativeModeTabOutput::insertAfter);
		t.BEFORE.forEach(fabricCreativeModeTabOutput::insertBefore);
	}
	//?} else if fabric {
	/*private static void applyFabric(Consumer<CreativeTabPlacements> tabPlacements, FabricItemGroupEntries fabricCreativeModeTabOutput) {
		CreativeTabPlacements t = new CreativeTabPlacements();
		tabPlacements.accept(t);
		for (ItemStack addition : t.ADDITIONS) {
			fabricCreativeModeTabOutput.accept(addition);
		}
		t.AFTER.forEach(fabricCreativeModeTabOutput::addAfter);
		t.BEFORE.forEach(fabricCreativeModeTabOutput::addBefore);
	}
	*///?} else if neoforge {
	/*private static void applyForge(Consumer<CreativeTabPlacements> tabPlacements, BuildCreativeModeTabContentsEvent event) {
		CreativeTabPlacements t = new CreativeTabPlacements();
		tabPlacements.accept(t);
		for (ItemStack addition : t.ADDITIONS) {
			event.accept(addition);
		}
		t.AFTER.forEach((existingEntry1, newEntry1) -> {
			insertAfter(event, existingEntry1, newEntry1);
		});
		t.BEFORE.forEach((existingEntry1, newEntry1) -> {
			insertBefore(event, existingEntry1, newEntry1);
		});
	}

	private static void insertBefore(BuildCreativeModeTabContentsEvent event, ItemStack beforeStack, List<ItemStack> items) {
		if (event.getParentEntries().contains(beforeStack) && event.getSearchEntries().contains(beforeStack)) {
			for (ItemStack item : items) {
				event.insertBefore(beforeStack, item, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			}
		} else {
			for (ItemStack item : items) {
				event.accept(item);
			}
		}
	}

	private static void insertAfter(BuildCreativeModeTabContentsEvent event, ItemStack afterStack, List<ItemStack> items) {
		if (event.getParentEntries().contains(afterStack) && event.getSearchEntries().contains(afterStack)) {
			for (ItemStack item : items) {
				event.insertAfter(afterStack, item, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			}
		} else {
			for (ItemStack item : items) {
				event.accept(item);
			}
		}
	}

	*///?} else if forge {
	/*private static void applyForge(Consumer<CreativeTabPlacements> tabPlacements, BuildCreativeModeTabContentsEvent event) {
		CreativeTabPlacements t = new CreativeTabPlacements();
		tabPlacements.accept(t);
		for (ItemStack addition : t.ADDITIONS) {
			event.accept(addition);
		}
		for (List<ItemStack> addition : t.AFTER.values()) {
			event.acceptAll(addition);
		}
		for (List<ItemStack> addition : t.BEFORE.values()) {
			event.acceptAll(addition);
		}
	}

	*///?}

	public static class CreativeTabPlacements {
		Map<ItemStack, List<ItemStack>> AFTER;
		Map<ItemStack, List<ItemStack>> BEFORE;
		List<ItemStack> ADDITIONS;

		CreativeTabPlacements() {
			AFTER = new LinkedHashMap<>();
			BEFORE = new LinkedHashMap<>();
			ADDITIONS = new ArrayList<>();
		}

		public void addBefore(ItemLike anchor, ItemStack stack) {
			addBefore(anchor.asItem().getDefaultInstance(), stack);
		}

		public void addBefore(ItemStack anchor, ItemStack stack) {
			BEFORE.put(anchor, Collections.singletonList(stack));
		}

		public void addBefore(ItemStack anchor, ItemStack... stack) {
			BEFORE.put(anchor, List.of(stack));
		}

		public void addBefore(ItemStack anchor, Collection<ItemStack> stack) {
			BEFORE.put(anchor, List.copyOf(stack));
		}

		public void addBefore(ItemStack anchor, ItemLike stack) {
			BEFORE.put(anchor, Collections.singletonList(stack.asItem().getDefaultInstance()));
		}

		public void addBefore(ItemStack anchor, ItemLike... stack) {
			BEFORE.put(anchor, Arrays.stream(stack).map((ItemLike t) -> t.asItem().getDefaultInstance()).toList());
		}

		public void addAfter(ItemLike anchor, ItemStack stack) {
			AFTER.put(anchor.asItem().getDefaultInstance(), Collections.singletonList(stack));
		}

		public void addAfter(ItemStack anchor, ItemStack stack) {
			AFTER.put(anchor, Collections.singletonList(stack));
		}

		public void addAfter(ItemStack anchor, ItemStack... stack) {
			AFTER.put(anchor, List.of(stack));
		}

		public void addAfter(ItemStack anchor, Collection<ItemStack> stack) {
			AFTER.put(anchor, List.copyOf(stack));
		}

		public void addAfter(ItemStack anchor, ItemLike stack) {
			AFTER.put(anchor, Collections.singletonList(stack.asItem().getDefaultInstance()));
		}

		public void addAfter(ItemStack anchor, ItemLike... stack) {
			AFTER.put(anchor, Arrays.stream(stack).map((ItemLike t) -> t.asItem().getDefaultInstance()).toList());
		}

		public void addAfter(ItemLike anchor, ItemLike... stack) {
			addAfter(anchor.asItem().getDefaultInstance(), stack);
		}

		public void add(Collection<ItemStack> stack) {
			ADDITIONS.addAll(stack);
		}

		public void acceptAll(Collection<ItemStack> stack) {
			ADDITIONS.addAll(stack);
		}

		public void add(ItemStack stack) {
			ADDITIONS.add(stack);
		}

		public void accept(ItemStack stack) {
			add(stack);
		}

		public void accept(ItemLike stack) {
			add(stack);
		}

		public void add(ItemLike stack) {
			add(stack.asItem().getDefaultInstance());
		}

	}
}
