package dev.fasterchooseworld.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Keeps the world list visible when there is no world to select.
 * <p>
 * Vanilla opens the create-world screen automatically from
 * {@code WorldSelectionList#handleNewLevels} when the list is empty. The redirect below
 * swallows that call so the player can still see the world list and choose
 * "Create New World" from there.
 */
@Mixin(WorldSelectionList.class)
public abstract class WorldSelectionListMixin {

    @Redirect(
            method = "handleNewLevels",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen;openFresh(Lnet/minecraft/client/Minecraft;Ljava/lang/Runnable;)V"
            )
    )
    private void fasterchooseworld$keepEmptyWorldList(Minecraft minecraft, Runnable callback) {
        // Intentionally empty: do not open the create-world screen automatically.
    }
}
