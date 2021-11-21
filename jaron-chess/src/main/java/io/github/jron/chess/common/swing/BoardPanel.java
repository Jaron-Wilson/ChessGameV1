package io.github.jron.chess.common.swing;

import io.github.jron.chess.common.Coordinate;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface BoardPanel {
    Coordinate convert(MouseEvent event);

    Component getComponent();
}
