package cc.cassian.asphodel.compat;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public class AccessoriesCompat {
    public static void checkForImportantAccessories(Player player, Consumer<ItemStack> isImportantItemOrContainer) {
        var capability = AccessoriesCapability.get(player);
        if (capability != null) {
            List<SlotEntryReference> allEquipped = capability.getAllEquipped();
            for (SlotEntryReference slotEntryReference : allEquipped) {
                isImportantItemOrContainer.accept(slotEntryReference.stack());
            }
        }
    }
}
