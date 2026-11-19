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

	private static ConfigCategory createCategory(String section, ConfigBuilder builder, ConfigRenderContext context) {
		if (section == null) {
			return builder.getOrCreateCategory(context.modName());
		}
		return builder.getOrCreateCategory(Component.literal(section));
	}

	public static Screen create(ConfigRenderContext context, Screen parent) {
		final var builder = ConfigBuilder.create()
				.setParentScreen(parent)
				.setTitle(context.modName());

		addEntries(context, builder);

		builder.setSavingRunnable(context.config()::save);
		return builder.build();
	}

	private static void addEntries(ConfigRenderContext context, ConfigBuilder builder) {
		var entryBuilder = builder.entryBuilder();
		for (TrackedValue<?> field : context.config().values()) {
			String categoryName = field.key().toString();
			if (categoryName.contains(".")) {
				categoryName = formatString(field.key().getKeyComponent(0));
			} else {
				categoryName = null;
			}
			var category = createCategory(categoryName, builder, context);
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

}