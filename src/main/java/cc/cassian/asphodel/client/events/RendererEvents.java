package cc.cassian.asphodel.client.events;

//? fabric && >=26.4 {
/*import net.minecraft.client.core.registries.BuiltInClientRegistries;
*///?} else if fabric {
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
 //?} else if neoforge {
/*import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import cc.cassian.asphodel.neoforge.ForgeUtil;
*///?}
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
//? forge {
/*import cc.cassian.asphodel.forge.ForgeUtil;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
*///?}

public final class RendererEvents {
	static <T extends Entity> void registerEntity(String modId, EntityType<? extends T> type, EntityRendererProvider<T> renderer) {
		//? fabric && >26{
		EntityRenderers.register(
				//? if >=26.4
				//BuiltInClientRegistries.ENTITY_RENDERER,
				type, renderer);
		//?} else if fabric{
		/*EntityRendererRegistry.register(type, renderer);
		 *///?} else if neoforge || forge {
		/*ForgeUtil.getEventBus(modId).addListener(EventPriority.NORMAL, true, EntityRenderersEvent.RegisterRenderers.class, event-> event.registerEntityRenderer(type, renderer));
		 *///?}
	}

}
