package cc.cassian.asphodel.events;

//? fabric && >26 {
import net.fabricmc.fabric.api.recipe.v1.sync.RecipeSynchronization;
//?}
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
//? neoforge && >26 {
/*import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
*///?}

public final class RecipeSyncEvent {
	static void syncRecipes(String modId, Iterable<RecipeSerializer<?>> serializers, Iterable<RecipeType<?>> types) {
		//? fabric && >26{
		serializers.forEach(RecipeSynchronization::synchronizeRecipeSerializer);
		//?} else if neoforge && >26 {
		/*ModList.get().getModContainerById(modId).orElseThrow().getEventBus().addListener(OnDatapackSyncEvent.class, event->{
			event.sendRecipes(types);
		});
		*///?}
	}
}
