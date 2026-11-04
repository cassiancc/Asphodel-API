package cc.cassian.asphodel.mixin;

import cc.cassian.asphodel.references.Identifiable;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(Holder.Reference.class)
public abstract class HolderMixin implements Identifiable {

	@Shadow
	public abstract Optional<ResourceKey<?>> unwrapKey();

	@Override
	public Identifier asphodel$identifier() {
		//~ if >26 'location'->'identifier'
		return unwrapKey().orElseThrow().identifier();
	}

	@Override
	public ResourceKey<?> asphodel$key() {
		return unwrapKey().orElseThrow();
	}
}
