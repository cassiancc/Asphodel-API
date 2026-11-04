package cc.cassian.asphodel.mixin;

import cc.cassian.asphodel.references.Identifiable;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Block.class)
public class BlockMixin implements Identifiable {
	@Shadow
	@Final
	private Holder.Reference<Block> builtInRegistryHolder;

	@Override
	public Identifier asphodel$identifier() {
		//~ if >26 'location'->'identifier'
		return builtInRegistryHolder.key().identifier();
	}

	@Override
	public ResourceKey<Block> asphodel$key() {
		return builtInRegistryHolder.key();
	}
}
