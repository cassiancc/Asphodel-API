package cc.cassian.asphodel.client.events;

import cc.cassian.asphodel.client.util.InputUtil;
import net.minecraft.network.chat.Component;
//? fabric {
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
//?}
//? neoforge {
/*import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import cc.cassian.asphodel.neoforge.ForgeUtil;
*///?}
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
//? forge {
/*import cc.cassian.asphodel.forge.ForgeUtil;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
*///?}

import java.util.List;
import java.util.function.Consumer;

public final class ItemTooltipEvents {
	static void modifyItemTooltip(String modId, Consumer<TooltipAccess> tooltip) {
		//? fabric{
		ItemTooltipCallback.EVENT.register((itemStack,
											//? if >1.21
											tooltipContext,
											tooltipFlag, list) -> {
			//? if >1.21 {
			TooltipInfo info = new TooltipInfo(tooltipFlag, tooltipContext);
			//?} else {
			/*TooltipInfo info = new TooltipInfo(tooltipFlag);
			*///?}
			TooltipAccess access = new TooltipAccess(itemStack, info, list);
			tooltip.accept(access);
		});
		 //?} else if neoforge || forge {
		/*ForgeUtil.getEventBus(modId).addListener(EventPriority.NORMAL, true, ItemTooltipEvent.class, event->{
			var access = new TooltipAccess(event.getItemStack(), new TooltipInfo(event.getFlags()
					//? if >1.21
					, event.getContext()
			), event.getToolTip());
			tooltip.accept(access);
		});
		*///?}
	}

	public record TooltipAccess(ItemStack stack, TooltipInfo info, List<Component> lines) {
	}

	public static class TooltipInfo implements TooltipFlag {
		//? if >1.21
		private final Item.TooltipContext context;
		public TooltipFlag tooltipFlag;

		//? if >1.21 {
		private TooltipInfo(TooltipFlag tooltipFlag, Item.TooltipContext context) {
			this.tooltipFlag = tooltipFlag;
			this.context = context;
		}
		//?}


		private TooltipInfo(TooltipFlag tooltipFlag) {
			this.tooltipFlag = tooltipFlag;
			//? if >1.21
			this.context = null;
		}

		//? if >1.21 {
		public Item.TooltipContext getContext() {
			return context;
		}
		//?}

		@Override
		public boolean isAdvanced() {
			return tooltipFlag.isAdvanced();
		}

		@Override
		public boolean isCreative() {
			return tooltipFlag.isCreative();
		}

		//? if >1.21 {
		@Override
		public boolean shouldDisplayAllInformation() {
			return tooltipFlag.shouldDisplayAllInformation();
		}
		//?} else {
		/*public boolean shouldDisplayAllInformation() {
			return false;
		}
		*///?}

		public boolean hasShiftDown() {
			//? if neoforge {
			/*return tooltipFlag.hasShiftDown();
			*///?} else {
			return InputUtil.hasShiftDown();
			//?}

		}
	}
}
