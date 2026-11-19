//? neoforge {
/*package cc.cassian.asphodel.client.neoforge;

import cc.cassian.asphodel.AsphodelAPI;
import cc.cassian.asphodel.Platform;
import cc.cassian.asphodel.client.config.ConfigUtil;
import cc.cassian.asphodel.client.events.ClientRegisterEvent;
import cc.cassian.asphodel.compat.ModCompat;
import folk.sisby.kaleido.lib.quiltconfig.impl.util.ConfigsImpl;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.Optional;

@EventBusSubscriber(modid = AsphodelAPI.MOD_ID, value = Dist.CLIENT)
public class NeoForgeClientEvents {

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void register(FMLClientSetupEvent event) {
		LoadingModList.get().getMods().forEach(modInfo -> {
			Optional<String> optional = modInfo.getConfig().getConfigElement("asphodel_client");
			if (optional.isPresent()) {
				AsphodelAPI.LOGGER.debug("Asphodel API: Registering client content for : {}", modInfo.getModId());
				try {
					Class<?> clazz = Class.forName(optional.get());
					ClientRegisterEvent integration = ((ClientRegisterEvent) clazz.getConstructor().newInstance());
					integration.onInitializeClient();
					AsphodelAPI.LOGGER.debug("Asphodel API: Registered client content for mod: {}", modInfo.getModId());
				} catch (Exception ignored) {
					AsphodelAPI.LOGGER.error("Asphodel API: Failed to load client content: {}", optional.get());
				}
			}
		});
		// mcqoy's look better at the moment
		if (Platform.INSTANCE.isModLoaded("mcqoy")) return;
		//? if <26
		//if (ModCompat.YET_ANOTHER_CONFIG_LIB || ModCompat.CLOTH_CONFIG) return;

		ConfigsImpl.getAll().iterator().forEachRemaining(config -> {
			ModList.get().getModContainerById(config.id()).ifPresent(container -> {
				String id = container.getModId();
				if (container.getCustomExtension(IConfigScreenFactory.class).isEmpty())
					container.registerExtensionPoint(IConfigScreenFactory.class, (c, screen) -> ConfigUtil.create(id, container.getModInfo().getDisplayName(), config).create(screen));
			});
		});
	}
}
*///?}