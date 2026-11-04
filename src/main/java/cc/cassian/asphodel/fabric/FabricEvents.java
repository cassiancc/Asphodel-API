//? fabric {
package cc.cassian.asphodel.fabric;

import cc.cassian.asphodel.events.CommonRegisterEvent;
import cc.cassian.asphodel.util.block.ToolModifiableBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class FabricEvents implements ModInitializer {

	@Override
	public void onInitialize() {
		FabricLoader.getInstance().invokeEntrypoints("asphodel_common", CommonRegisterEvent.class, CommonRegisterEvent::onInitialize);
		UseBlockCallback.EVENT.register((player, world, hand, blockHitResult) -> {
			var state = world.getBlockState(blockHitResult.getBlockPos());
			var item = player.getItemInHand(hand);
			if (state.getBlock() instanceof ToolModifiableBlock toolModifiableBlock) {
				//~ if >26 'getTags'->'tags'
				for (TagKey<Item> tagKey : item.tags().toList()) {
					var newState = toolModifiableBlock.getToolModifiedState(state, new UseOnContext(player, hand, blockHitResult), tagKey, false);
					if (newState != null) {
						world.setBlockAndUpdate(blockHitResult.getBlockPos(), newState);
						return InteractionResult.SUCCESS;
					}
				}
			}
			return InteractionResult.PASS;
		});
	}
}
//?}