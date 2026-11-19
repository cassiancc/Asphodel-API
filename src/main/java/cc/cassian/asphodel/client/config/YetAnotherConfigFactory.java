package cc.cassian.asphodel.client.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import folk.sisby.kaleido.lib.quiltconfig.api.Config;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Locale;

import static cc.cassian.asphodel.client.config.ConfigUtil.*;

public class YetAnotherConfigFactory {

	private static ConfigCategory.Builder createCategory(String section, LinkedHashMap<String, ConfigCategory.Builder> categories, Component modName) {
		if (categories.containsKey(section)) {
			return categories.get(section);
		} else {
			Component sectionKey;
			if (section == null) {
				sectionKey = modName;
			} else {
				sectionKey = Component.literal(section);
			}
			ConfigCategory.Builder category = ConfigCategory.createBuilder().name(sectionKey);
			categories.put(section, category);
			return category;
		}
	}

	public static Screen create(String id, Component modName, Config config, Screen parent) {
		final YetAnotherConfigLib.Builder builder = YetAnotherConfigLib.createBuilder()
				.title(Component.translatable("modmenu.nameTranslation.item_descriptions"));
		LinkedHashMap<String, ConfigCategory.Builder> categories = new LinkedHashMap<>();
		addEntries(config.values(), categories, modName);
		for (ConfigCategory.Builder s : categories.values()) {
			builder.category(s.build());
		}


		builder.save(config::save);
		return builder.build().generateScreen(parent);
	}

	private static void addEntries(Iterable<TrackedValue<?>> fields, LinkedHashMap<String, ConfigCategory.Builder> categories, Component modName) {

		for (var field : fields) {
			String categoryName = field.key().toString();
			if (categoryName.contains(".")) {
				categoryName = formatString(field.key().getKeyComponent(0));
			} else {
				categoryName = null;
			}
			var category = createCategory(categoryName, categories, modName);
			OptionDescription optionDescription = OptionDescription.of(fieldTooltip(field).toArray(new Component[0]));
			if (field.value().getClass() == Boolean.class) {
				category.option(Option.<Boolean>createBuilder()
						.name(fieldName(field))
						.description(optionDescription)
						.binding(
								(boolean) field.getDefaultValue(),
								()-> (Boolean) field.value(),
								(value)->fieldSetter(value, (TrackedValue<Boolean>) field)
						)
						.controller(TickBoxControllerBuilder::create)
						.build());

			}
			else if (field.value().getClass() == String.class) {
				category.option(Option.<String>createBuilder()
						.name(fieldName(field))
						.description(optionDescription)
						.binding(
								(String) field.getDefaultValue(),
								()-> (String) field.value(),
								(value)->fieldSetter(value, (TrackedValue<String>) field)
						)
						.controller(StringControllerBuilder::create)
						.build());
			}
			else if (field.value().getClass() == Integer.class) {
				if (field.key().toString().toLowerCase(Locale.ROOT).contains("colour")) {
					category.option(Option.<Color>createBuilder()
							.name(fieldName(field))
							.description(optionDescription)
							.binding(
									new Color((Integer) field.getDefaultValue()),
									()-> new Color((Integer) field.value()),
									(value)->fieldSetter(value, (TrackedValue<Integer>) field)
							)
							.controller(ColorControllerBuilder::create)
							.build());

				} else {
					category.option(Option.<Integer>createBuilder()
							.name(fieldName(field))
							.description(optionDescription)
							.binding(
									(Integer) field.getDefaultValue(),
									()-> (Integer) field.value(),
									(value)->fieldSetter(value, (TrackedValue<Integer>) field)
							)
							.controller(IntegerFieldControllerBuilder::create)
							.build());
				}

			}
		}
	}
}