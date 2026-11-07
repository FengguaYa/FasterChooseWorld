package dev.fasterchooseworld.mixin;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Safety net: if anything tries to jump from the title screen straight into the
 * create-world screen, show the world list instead.
 */
@Mixin(Gui.class)
public abstract class GuiMixin {

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    private void fasterchooseworld$keepWorldList(Screen screen, CallbackInfo ci) {
        if (!(screen instanceof CreateWorldScreen)) {
            return;
        }
        Gui self = (Gui) (Object) this;
        if (self.screen() instanceof TitleScreen titleScreen) {
            self.setScreen(new SelectWorldScreen(titleScreen));
            ci.cancel();
        }
    }
}
