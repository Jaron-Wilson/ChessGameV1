package io.github.jron.chess.common.swing;

import io.github.jron.chess.common.Position;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface BoardPanel {
    Position convert(MouseEvent event);

    Component getComponent();
}
