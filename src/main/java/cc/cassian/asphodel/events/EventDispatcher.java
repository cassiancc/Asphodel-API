package cc.cassian.asphodel.events;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Consumer;

public class EventDispatcher {
	private final String namespace;

	public EventDispatcher(String namespace) {
		this.namespace = namespace;
	}

	public void modifyCreativeTabs(ResourceKey<CreativeModeTab> tab, Consumer<CreativeTabEvent.CreativeTabPlacements> tabPlacements) {
		CreativeTabEvent.modifyEntries(this.namespace, tab, tabPlacements);
	}

	public void syncRecipes(Iterable<RecipeSerializer<?>> serializers, Iterable<RecipeType<?>> types) {
		RecipeSyncEvent.syncRecipes(this.namespace, serializers, types);
	}
}
