package cc.cassian.asphodel.mixin;

import cc.cassian.asphodel.references.Identifiable;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TagKey.class)
public abstract class TagKeyMixin<T> implements Identifiable {

	@Shadow
	@Final
	private Identifier location;

	@Shadow
	public abstract ResourceKey<? extends Registry<T>> registry();

	@Override
	public Identifier asphodel$identifier() {
		return location;
	}

	@Override
	public ResourceKey<T> asphodel$key() {
		return ResourceKey.create(registry(), asphodel$identifier());
	}
}
