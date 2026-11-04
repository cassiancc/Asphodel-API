package cc.cassian.asphodel.mixin;

import cc.cassian.asphodel.references.Identifiable;
//~ if >=26.2 'cc.cassian.asphodel'->'net.minecraft'
import cc.cassian.asphodel.tags.BlockItemTagId;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockItemTagId.class)
public abstract class BlockItemTagIdMixin implements Identifiable {

	//? if <26.2 {
	@Shadow
	public abstract TagKey<Block> block();

	@Override
	public Identifier asphodel$identifier() {
		return block().asphodel$identifier();
	}

	@Override
	public ResourceKey<?> asphodel$key() {
		return block().asphodel$key();
	}
	//?}
}
