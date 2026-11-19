//? forge {
/*package cc.cassian.asphodel.forge;

import cc.cassian.asphodel.AsphodelAPI;
import cc.cassian.asphodel.Platform;
import cc.cassian.asphodel.client.config.ConfigScreenContext;
import cc.cassian.asphodel.client.config.ConfigUtil;
import cc.cassian.asphodel.client.config.SelectConfigScreen;
import cc.cassian.asphodel.compat.ModCompat;
import folk.sisby.kaleido.lib.quiltconfig.api.Config;
import folk.sisby.kaleido.lib.quiltconfig.impl.util.ConfigsImpl;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = AsphodelAPI.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeClientEvents {
	@SubscribeEvent(priority = EventPriority.LOW)
	public static void register(FMLClientSetupEvent event) {

		// mcqoy's look better at the moment
		if (Platform.INSTANCE.isModLoaded("mcqoy")) return;
		if (!ModCompat.YET_ANOTHER_CONFIG_LIB && !ModCompat.CLOTH_CONFIG) return;

		ConfigsImpl.getAll().iterator().forEachRemaining(config -> {
			if (config.family().isBlank()) {
				ModList.get().getModContainerById(config.id()).ifPresent(container -> container.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, ()-> new ConfigScreenHandler.ConfigScreenFactory(parent-> addContainer(config, container).getConfigScreen().create(parent))));
			} else{
				ModList.get().getModContainerById(config.family()).ifPresent(container -> {
					if (container.getCustomExtension(ConfigScreenHandler.ConfigScreenFactory.class).isEmpty()) {
						container.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, ()->{
							ArrayList<ConfigScreenContext> list = new ArrayList<>();
							ConfigsImpl.getConfigs(config.family()).forEach(configItem -> {
								list.add(addContainer(configItem, container));
							});
							return new ConfigScreenHandler.ConfigScreenFactory(parent-> new SelectConfigScreen(container.getModId(), list, parent));
						});
					}
				});
			}
		});
	}

	private static ConfigScreenContext addContainer(Config config, ModContainer container) {
		String id = container.getModId();
		return ConfigUtil.createContext(id, container.getModInfo().getDisplayName(), config);
	}
}
*///?}