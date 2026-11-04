//? fabric {
package cc.cassian.asphodel.client.fabric;

import cc.cassian.asphodel.client.events.ClientRegisterEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class FabricClientEvents implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		FabricLoader.getInstance().invokeEntrypoints("asphodel_client", ClientRegisterEvent.class, ClientRegisterEvent::onInitializeClient);
	}
}
//?}