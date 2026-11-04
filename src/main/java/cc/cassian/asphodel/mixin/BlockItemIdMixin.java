package cc.cassian.asphodel.mixin;

import cc.cassian.asphodel.references.Identifiable;
//~ if >=26.2 'cc.cassian.asphodel'->'net.minecraft'
import cc.cassian.asphodel.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockItemId.class)
public abstract class BlockItemIdMixin implements Identifiable {

	//? if <26.2 {
	@Shadow
	public abstract ResourceKey<Block> block();

	@Override
	public Identifier asphodel$identifier() {
		return block().asphodel$identifier();
	}

	@Override
	public ResourceKey<?> asphodel$key() {
		return block();
	}

	//?}
}
