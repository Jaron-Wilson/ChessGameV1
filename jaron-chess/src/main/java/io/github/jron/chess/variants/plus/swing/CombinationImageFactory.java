package io.github.jron.chess.variants.plus.swing;

import io.github.jron.chess.common.piece.Piece;
import io.github.jron.chess.common.swing.StandardImageFactory;
import io.github.jron.chess.variants.plus.Combination;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

public class CombinationImageFactory extends StandardImageFactory {

    public CombinationImageFactory() throws IOException {
        super();
    }

    public BufferedImage getImage(Piece piece, ImageObserver observer) {
        if (piece instanceof Combination combination) {

            BufferedImage image1 = getImage(combination.getPiece1(), observer);
            BufferedImage image2 = getImage(combination.getPiece2(), observer);

            //Draw the entire first image.
            BufferedImage newImage = new BufferedImage(image1.getWidth(), image1.getHeight(), BufferedImage.TYPE_INT_ARGB);

            BufferedImage cutImage1 = image1.getSubimage(0, 0, image1.getWidth() / 2, image1.getWidth());
            newImage.getGraphics().drawImage(cutImage1, 0, 0, observer);

            //Draw the second half of the second piece.
            int center = image2.getWidth() / 2;
            BufferedImage cutImage2 = image2.getSubimage(center, 0, center, image2.getHeight());
            newImage.getGraphics().drawImage(cutImage2, center, 0, observer);

            return newImage;
        } else {
            return super.getImage(piece, observer);
        }
    }
}
