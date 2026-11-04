package cc.cassian.asphodel.mixin;

import cc.cassian.asphodel.references.Identifiable;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ResourceKey.class)
public abstract class ResourceKeyMixin<T> implements Identifiable {

	@Shadow
	@Final
	//~ if >26 'location'->'identifier'
	private Identifier identifier;

	@Override
	public Identifier asphodel$identifier() {
		//~ if >26 'location'->'identifier'
		return identifier;
	}

	@Override
	public ResourceKey<T> asphodel$key() {
		return (ResourceKey<T>) (Object) this;
	}
}
