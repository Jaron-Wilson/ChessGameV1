package io.github.jron.chess.common.swing;

import io.github.jron.chess.common.piece.Piece;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public interface ImageFactory {
    BufferedImage getImage(Piece piece, ImageObserver observer);
}
