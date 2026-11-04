package cc.cassian.asphodel.mixin;

import cc.cassian.asphodel.references.Identifiable;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SoundEvent.class)
public class SoundEventMixin implements Identifiable {

	@Shadow
	@Final
	private Identifier location;

	@Override
	public Identifier asphodel$identifier() {
		return location;
	}

	@Override
	public ResourceKey<?> asphodel$key() {
		return ResourceKey.create(Registries.SOUND_EVENT, asphodel$identifier());
	}
}
