//? fabric {
package cc.cassian.asphodel.compat;

import cc.cassian.asphodel.AsphodelAPI;
import cc.cassian.asphodel.Platform;
import cc.cassian.asphodel.client.config.ConfigScreenContext;
import cc.cassian.asphodel.client.config.ConfigUtil;
import cc.cassian.asphodel.client.config.SelectConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import folk.sisby.kaleido.lib.quiltconfig.api.Config;
import folk.sisby.kaleido.lib.quiltconfig.impl.util.ConfigsImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModMenuCompat implements ModMenuApi {

	@Override
	public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
		// mcqoy's looks better at the moment
		if (Platform.INSTANCE.isModLoaded("mcqoy")) return Map.of();
		//? if <26
		//if (!ModCompat.YET_ANOTHER_CONFIG_LIB && !ModCompat.CLOTH_CONFIG) return Map.of();

		var map = new HashMap<String, ConfigScreenFactory<?>>();
		ConfigsImpl.getAll().iterator().forEachRemaining(config -> {
			if (config.family().isBlank()) {
				FabricLoader.getInstance().getModContainer(config.id()).ifPresent(container -> map.put(config.id(), s-> addContainer(config, container).getConfigScreen().create(s)));
			} else{
				FabricLoader.getInstance().getModContainer(config.family()).ifPresent(container -> {
					map.putIfAbsent(config.family(), parent->{
						ArrayList<ConfigScreenContext> list = new ArrayList<>();
						ConfigsImpl.getConfigs(config.family()).forEach(configItem -> {
							list.add(addContainer(configItem, container));
						});
						return new SelectConfigScreen(container.getMetadata().getName(), list, parent);
					});
				});
			}
		});

		return map;
	}

	private static ConfigScreenContext addContainer(Config config, ModContainer container) {
		String id = container.getMetadata().getId();
		return ConfigUtil.createContext(id, container.getMetadata().getName(), config);
	}
}
//?}