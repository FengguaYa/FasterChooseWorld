package dev.twostepcreateworld.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Makes the title screen's "Singleplayer" button always open the world list.
 * <p>
 * Vanilla builds the button through {@link Button#builder(Component, Button.OnPress)}. The
 * redirect below only swaps the press handler for the button whose label is
 * {@code menu.singleplayer}; every other button keeps its original behaviour.
 */
@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {

    @Redirect(
            method = "createNormalMenuOptions",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/components/Button;builder(Lnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/components/Button$OnPress;)Lnet/minecraft/client/gui/components/Button$Builder;"
            )
    )
    private Button.Builder fasterchooseworld$openWorldList(Component message, Button.OnPress original) {
        if (Component.translatable("menu.singleplayer").equals(message)) {
            TitleScreen self = (TitleScreen) (Object) this;
            return Button.builder(message,
                    button -> Minecraft.getInstance().setScreen(new SelectWorldScreen(self)));
        }
        return Button.builder(message, original);
    }
}
