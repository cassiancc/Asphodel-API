//? fabric {
package cc.cassian.asphodel.compat;

import cc.cassian.asphodel.Platform;
import cc.cassian.asphodel.client.config.ConfigUtil;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import folk.sisby.kaleido.lib.quiltconfig.impl.util.ConfigsImpl;
import net.fabricmc.loader.api.FabricLoader;

import java.util.HashMap;
import java.util.Map;

public class ModMenuCompat implements ModMenuApi {

	@Override
	public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
		// mcqoy's looks better at the moment
		if (Platform.INSTANCE.isModLoaded("mcqoy")) return Map.of();
		//? if <26
		//if (ModCompat.YET_ANOTHER_CONFIG_LIB || ModCompat.CLOTH_CONFIG) return Map.of();

		var map = new HashMap<String, ConfigScreenFactory<?>>();
		ConfigsImpl.getAll().iterator().forEachRemaining(config -> {
			FabricLoader.getInstance().getModContainer(config.id()).ifPresent(container -> {
				String id = container.getMetadata().getId();
				map.put(id, screen -> ConfigUtil.create(id, container.getMetadata().getName(), config).create(screen));
			});
		});

		return map;
	}
}
//?}