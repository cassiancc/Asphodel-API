package cc.cassian.asphodel.util.block;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public interface ToolModifiableBlock {
	default @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, TagKey<Item> tag, boolean simulate) {
		return null;
	}
}

