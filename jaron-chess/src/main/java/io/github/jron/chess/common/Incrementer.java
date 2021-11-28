package io.github.jron.chess.common;

/**
 * An Incrementer holds the state of whose turn it is. It also can increment the
 * turn.
 *
 * @param <O> The type of object to increment.
 */
public class Incrementer<O> {

    private final int length;
    private final O[] objects;
    private int i = 0;

    public Incrementer(O... o) {
        this.objects = o;
        this.length = o.length;
    }

    public Incrementer<O> increment() {
        i = ++i % length;
        return this;
    }

    public O get() {
        return objects[i];
    }
}
