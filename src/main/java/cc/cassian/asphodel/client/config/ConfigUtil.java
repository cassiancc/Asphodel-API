package cc.cassian.asphodel.client.config;

import cc.cassian.asphodel.AsphodelAPI;
import cc.cassian.asphodel.compat.ModCompat;
import folk.sisby.kaleido.lib.quiltconfig.api.Config;
import folk.sisby.kaleido.lib.quiltconfig.api.metadata.Comments;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueList;
import folk.sisby.kaleido.lib.quiltconfig.impl.values.ValueListImpl;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.apache.commons.lang3.text.WordUtils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ConfigUtil {

	protected static MutableComponent formatText(String key) {
		return Component.literal(formatString(key));
	}

	protected static String formatString(String key) {
		return WordUtils.capitalize(camelToSnake(key).replace("_", " "));
	}

	// Function to convert camel case string to snake case string.
	public static String camelToSnake(String str) {
		StringBuilder result = new StringBuilder();

		// Lowercase and append first character to result string
		result.append(Character.toLowerCase(str.charAt(0)));

		// Traverse the string from first index to last index
		for (int i = 1; i < str.length(); i++) {

			char ch = str.charAt(i);

			// Check if the character is upper case
			// then append '_' and such character
			// (in lower case) to result string
			if (Character.isUpperCase(ch)) {
				if (Character.isLowerCase(str.charAt(i-1)))
					result.append('_');
				result.append(Character.toLowerCase(ch));
			}

			// If the character is lower case, add the character to the result string
			else {
				result.append(ch);
			}
		}

		// return the result
		return result.toString();
	}

	protected static <T> Component fieldName(TrackedValue<T> value) {
		return formatText(value.key().getLastComponent());
	}

	protected static <T> ArrayList<Component> fieldTooltip(TrackedValue<T> value) {
		var commentList = new ArrayList<Component>();
		value.metadata().forEach((metadataType, o) -> {
			if (o instanceof Comments comments) {
				comments.forEach(c->commentList.add(Component.literal(c)));
			}
		});
		if (commentList.isEmpty()) {
			commentList.add(0, fieldName(value));
		}
		return commentList;
	}

	public static KaleidoConfigFactory<Screen> create(String id, String name, Config config) {
		AsphodelAPI.LOGGER.info("Asphodel API: Added config screen for mod: {}", id);
		ConfigRenderContext configRenderContext = new ConfigRenderContext(id, Component.translatableWithFallback("modmenu.nameTranslation." + name, name), config);
		if (ModCompat.YET_ANOTHER_CONFIG_LIB) {
			return (s)->YetAnotherConfigFactory.create(configRenderContext, s);
		}
		if (ModCompat.CLOTH_CONFIG) {
			return (s)->ClothConfigFactory.create(configRenderContext, s);
		}
		//? if >26 {
		return (s) -> new KaleidoConfigScreen(configRenderContext, s);
		//?} else {
		/*return null;
		*///?}
	}

	public static <T> void fieldSetter(List<T> instance, List<T> field) {
		field.clear();
		field.addAll(instance);
	}

	public static void fieldSetter(boolean instance, TrackedValue<Boolean> field) {
		field.setValue(instance);
	}
	public static void fieldSetter(Integer instance, TrackedValue<Integer> field) {
		field.setValue(instance);
	}
	public static void fieldSetter(String instance, TrackedValue<String> field) {
		field.setValue(instance);
	}
	public static void fieldSetter(Color instance, TrackedValue<Integer> field) {
		field.setValue(instance.getRGB());
	}

	public record ConfigRenderContext(String id, MutableComponent modName, Config config) {
	}

	public interface KaleidoConfigFactory<S extends Screen> {
		S create(Screen var1);
	}
}
