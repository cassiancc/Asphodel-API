package cc.cassian.asphodel.client.config;

import folk.sisby.kaleido.lib.quiltconfig.api.Config;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.awt.*;

import static cc.cassian.asphodel.client.config.ConfigUtil.*;

public class ClothConfigFactory {

	private static ConfigCategory createCategory(String section, ConfigBuilder builder, String id, Component modName) {
		if (section == null) {
			return builder.getOrCreateCategory(modName);
		}
		return builder.getOrCreateCategory(Component.literal(section));
	}

	public static Screen create(String id, Component modName, Config config, Screen parent) {
		final var builder = ConfigBuilder.create()
				.setParentScreen(parent)
				.setTitle(modName);

		addEntries(config.values(), builder, id, modName);

		builder.setSavingRunnable(config::save);
		return builder.build();
	}

	private static void addEntries(Iterable<TrackedValue<?>> fields, ConfigBuilder builder, String id, Component modName) {
		var entryBuilder = builder.entryBuilder();
		for (TrackedValue<?> field : fields) {
			String categoryName = field.key().toString();
			if (categoryName.contains(".")) {
				categoryName = formatString(categoryName.split("\\.")[0]);
			} else {
				categoryName = null;
			}
			var category = createCategory(categoryName, builder, id, modName);
			Component[] tooltip = fieldTooltip(field).toArray(new Component[0]);
			if (field.value().getClass() == Boolean.class) {
				category.addEntry(entryBuilder.startBooleanToggle(fieldName(field), (boolean) field.value())
						.setSaveConsumer((o)-> fieldSetter(o, (TrackedValue<Boolean>) field))
						.setTooltip(tooltip)
						.setDefaultValue((boolean) field.getDefaultValue()).build());

			}
			else if (field.value().getClass() == String.class) {
				category.addEntry(entryBuilder.startStrField(fieldName(field), (String) field.value())
						.setSaveConsumer((o)-> fieldSetter(o, (TrackedValue<String>) field))
						.setTooltip(tooltip)
						.setDefaultValue((String) field.getDefaultValue()).build());
			}
			else if (field.value().getClass() == Integer.class) {
				category.addEntry(entryBuilder.startIntField(fieldName(field), (int) field.value())
						.setSaveConsumer((o)-> fieldSetter(o, (TrackedValue<Integer>) field))
						.setTooltip(tooltip)
						.setDefaultValue((int) field.getDefaultValue()).build());
			}
//            else if (field.getType() == List.class) {
//                category.addEntry(entryBuilder.startStrList(fieldName(field, categoryName), fieldGet(config, field))
//                        .setSaveConsumer(fieldSetter(config, field))
//                        .setTooltip(tooltip)
//                        .setDefaultValue((List<String>) fieldGet(defaultValues, field)).build());
//            }
		}
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
}