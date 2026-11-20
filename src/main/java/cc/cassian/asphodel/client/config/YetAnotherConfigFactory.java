package cc.cassian.asphodel.client.config;

import cc.cassian.asphodel.AsphodelAPI;
import cc.cassian.asphodel.Platform;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueList;
import folk.sisby.kaleido.lib.quiltconfig.impl.values.ValueListImpl;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static cc.cassian.asphodel.client.config.ConfigUtil.*;

public class YetAnotherConfigFactory {

	private static ConfigCategory.Builder createCategory(@Nullable String section, LinkedHashMap<String, ConfigCategory.Builder> categories, ConfigScreenContext context) {
		String key = Objects.requireNonNullElse(section, context.id());
		if (categories.containsKey(key)) {
			return categories.get(key);
		} else {
			Component sectionKey;
			if (section == null) {
				sectionKey = context.modName();
			} else {
				sectionKey = Component.literal(section);
			}
			ConfigCategory.Builder category = ConfigCategory.createBuilder().name(sectionKey);
			categories.put(key, category);
			return category;
		}
	}

	public static Screen create(ConfigScreenContext context, Screen parent) {
		final YetAnotherConfigLib.Builder builder = YetAnotherConfigLib.createBuilder()
				.title(Component.translatable("modmenu.nameTranslation.item_descriptions"));
		LinkedHashMap<String, ConfigCategory.Builder> categories = new LinkedHashMap<>();
		addEntries(context, categories);
		for (ConfigCategory.Builder s : categories.values()) {
			builder.category(s.build());
		}


		builder.save(context.config()::save);
		return builder.build().generateScreen(parent);
	}

	private static void addEntries(ConfigScreenContext context, LinkedHashMap<String, ConfigCategory.Builder> categories) {

		for (var field : context.config().values()) {
			String categoryName = field.key().toString();
			if (categoryName.contains(".")) {
				categoryName = formatString(field.key().getKeyComponent(0));
			} else {
				categoryName = null;
			}
			var category = createCategory(categoryName, categories, context);
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
			else if (field.value().getClass() == Float.class) {
				category.option(Option.<Float>createBuilder()
						.name(fieldName(field))
						.description(optionDescription)
						.binding(
								(Float) field.getDefaultValue(),
								()-> (Float) field.value(),
								(value)->fieldSetter(value, (TrackedValue<Float>) field)
						)
						.controller(FloatFieldControllerBuilder::create)
						.build());
			}
			else if (field.value().getClass() == Double.class) {
				category.option(Option.<Double>createBuilder()
						.name(fieldName(field))
						.description(optionDescription)
						.binding(
								(Double) field.getDefaultValue(),
								()-> (Double) field.value(),
								(value)->fieldSetter(value, (TrackedValue<Double>) field)
						)
						.controller(DoubleFieldControllerBuilder::create)
						.build());
			}
			else if (field.value() instanceof Enum<?> e) {
				category.option(Option.<Enum>createBuilder()
						.name(fieldName(field))
						.description(optionDescription)
						.binding(
								(Enum) field.getDefaultValue(),
								()-> (Enum) field.value(),
								(value)->fieldSetter(value, (TrackedValue<Enum>) field)
						)
						.controller(option -> EnumControllerBuilder.create(option).enumClass((Class<Enum>) e.getClass()))
						.build());
			}
			else if (field.value() instanceof ValueListImpl<?> list) {
				if (list.isEmpty() || list.get(0).getClass() == String.class) {
					category.group(ListOption.<String>createBuilder().initial("")
							.name(fieldName(field))
							.description(optionDescription)
							.binding(
									(List<String>) field.getDefaultValue(),
									()-> (List<String>) field.value(),
									(value)->fieldSetter(value, (ValueList<String>) field)
							)
							.controller(StringControllerBuilder::create)
							.build());

				}
			} else if (Platform.INSTANCE.isDeveloperEnvironment()) {
				AsphodelAPI.LOGGER.info("Unfamiliar with type {}", field.getDefaultValue().getClass());
			}
		}
	}
}