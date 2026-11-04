package cc.cassian.asphodel.references;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

public interface Identifiable {
	default Identifier asphodel$identifier() {
		throw new AssertionError("not implemented!");
	}

	default ResourceKey<?> asphodel$key() {
		throw new AssertionError("not implemented!");
	}

	default boolean asphodel$equals(Identifiable identifier) {
		return this.asphodel$identifier().equals(identifier.asphodel$identifier());
	}
}
