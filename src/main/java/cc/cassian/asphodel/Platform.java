package cc.cassian.asphodel;

//? fabric {
import cc.cassian.asphodel.fabric.FabricPlatformImpl;
//?}
import java.nio.file.Path;
import java.util.ArrayList;
//? neoforge {
/*import cc.cassian.asphodel.neoforge.NeoforgePlatformImpl;
*///?}

public interface Platform {

    //? if fabric {
    Platform INSTANCE = new FabricPlatformImpl();
    //?} else if neoforge {
    /*Platform INSTANCE = new NeoforgePlatformImpl();
    *///?}

	default boolean isModLoaded(String modid) {
		return isLoaded(modid);
	}

	boolean isLoaded(String modid);

    boolean isLoadingLoaded(String mod);

    String loader();

    Path configPath();

	default Path getConfigDir() {
		return configPath();
	}

	ArrayList<String> getMods();

	boolean isDeveloperEnvironment();

	default boolean isDevEnvironment() {
		return isDeveloperEnvironment();
	}
}
