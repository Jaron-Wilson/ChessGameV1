package io.github.jron.chess.common.swing;

import io.github.jron.chess.common.piece.Piece;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

public class StandardImageFactory implements ImageFactory {

    private BufferedImage[][] images;

    public StandardImageFactory() throws IOException {

        this.images = new BufferedImage[2][6];

        BufferedImage pieceBuffer = ImageIO.read(ClassLoader.getSystemResource("chess-pieces.png"));

        int width = pieceBuffer.getWidth() / 6;
        for (int i = 0; i < 6; i++) {
            images[1][i] = pieceBuffer.getSubimage(i * width, 0, width, width);
            images[0][i] = pieceBuffer.getSubimage(i * width, width, width, width);
        }
    }

    public BufferedImage getImage(Piece piece, ImageObserver observer) {
        return images[piece.getColor().ordinal()][piece.getPieceId()];
    }
}
