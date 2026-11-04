//? neoforge {
/*package cc.cassian.asphodel.neoforge;

import cc.cassian.asphodel.events.CommonRegisterEvent;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.neoforge.registries.RegisterEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@EventBusSubscriber(modid = "asphodel_api")
public class NeoForgeEvents {
	public static final Logger LOGGER = LoggerFactory.getLogger("Asphodel API");

	@SubscribeEvent
	public static void register(RegisterEvent event) {
		if (event.getRegistryKey().equals(Registries.BLOCK)) {
			LoadingModList.get().getMods().forEach(modInfo -> {
				Optional<String> optional = modInfo.getConfig().getConfigElement("asphodel_api");
				if (optional.isPresent()) {
					LOGGER.debug("Asphodel API: Registering content for : {}", modInfo.getModId());
					try {
						Class<?> clazz = Class.forName(optional.get());
						CommonRegisterEvent integration = ((CommonRegisterEvent) clazz.getConstructor().newInstance());
						integration.onInitialize();
						LOGGER.debug("Asphodel API: Registered content for mod: {}", modInfo.getModId());
						return;
					} catch (Exception ignored) {
					}

					LOGGER.error("Asphodel API: Failed to load content: {}", optional.get());
				}
			});
		}

	}
}
*///?}