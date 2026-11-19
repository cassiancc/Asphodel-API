package cc.cassian.asphodel.client.config;

import cc.cassian.asphodel.compat.ModCompat;
import folk.sisby.kaleido.lib.quiltconfig.api.Config;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.MutableComponent;

public record ConfigScreenContext(String id, MutableComponent modName, Config config) {
	public Factory<Screen> getConfigScreen() {
		if (ModCompat.YET_ANOTHER_CONFIG_LIB) {
			return (s) -> YetAnotherConfigFactory.create(this, s);
		}
		if (ModCompat.CLOTH_CONFIG) {
			return (s) -> ClothConfigFactory.create(this, s);
		}
		//? if >26 {
		return (s) -> new KaleidoConfigScreen(this, s);
		//?} else {
		/*return null;
		 *///?}
	}

	public interface Factory<S extends Screen> {
		S create(Screen var1);
	}
}


