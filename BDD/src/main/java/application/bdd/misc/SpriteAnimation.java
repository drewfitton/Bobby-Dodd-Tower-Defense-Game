package application.bdd.misc;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {

    private final ImageView imageView;
    private final int count;
    private final int columns;
    private final int width;
    private final int height;

    private int lastIndex;

    public SpriteAnimation(
        ImageView imageView,
        Duration duration,
        int count,   int columns,
        int width,   int height) {
        this.imageView = imageView;
        this.count     = count;
        this.columns   = columns;
        this.width     = width;
        this.height    = height;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            final int x = (index - 1) * width;
            imageView.setViewport(new Rectangle2D(x, 0, width, height));
            lastIndex = index;
        }
    }
}
