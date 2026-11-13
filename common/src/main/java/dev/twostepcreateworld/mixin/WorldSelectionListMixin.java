package dev.twostepcreateworld.mixin;

import java.util.List;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Keeps the world list visible when there is no world to select.
 * <p>
 * Vanilla opens the create-world screen from {@code handleNewLevels} when the list is empty,
 * and the loading header is only removed by that screen transition. Instead of letting the
 * automatic transition happen, the empty list is cleared here so the player stays on the
 * world list without an endless "Loading World List" spinner.
 */
@Mixin(WorldSelectionList.class)
public abstract class WorldSelectionListMixin {

    @Shadow
    private Screen screen;

    @Shadow
    private List<LevelSummary> currentlyDisplayedLevels;

    @Shadow
    protected abstract void clearEntries();

    @Inject(method = "handleNewLevels", at = @At("HEAD"), cancellable = true)
    private void twostepcreateworld$showEmptyWorldList(List<LevelSummary> levels, CallbackInfo ci) {
        if (levels == null || !levels.isEmpty()) {
            return;
        }
        if (!(this.screen instanceof SelectWorldScreen)) {
            return;
        }

        this.clearEntries();
        this.currentlyDisplayedLevels = levels;
        ci.cancel();
    }
}
