package io.github.jron.chess.common.swing;

import io.github.jron.chess.common.Coordanate;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface BoardPanel {
    Coordanate convert(MouseEvent event);
    Component getComponent();
}
