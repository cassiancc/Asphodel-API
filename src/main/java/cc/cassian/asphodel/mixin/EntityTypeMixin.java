package cc.cassian.asphodel.mixin;

import cc.cassian.asphodel.references.Identifiable;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityType.class)
public class EntityTypeMixin implements Identifiable {
	@Shadow
	@Final
	private Holder.Reference<EntityType<?>> builtInRegistryHolder;

	@Override
	public Identifier asphodel$identifier() {
		//~ if >26 'location'->'identifier'
		return builtInRegistryHolder.key().identifier();
	}

	@Override
	public ResourceKey<EntityType<?>> asphodel$key() {
		return builtInRegistryHolder.key();
	}
}
