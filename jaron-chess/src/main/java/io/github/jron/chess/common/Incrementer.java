package io.github.jron.chess.common;

/**
 * An Incrementer holds the state of who's turn it is. It also can increment the
 * turn.
 *
 * @param <O> The type of object to increment.
 */
public class Incrementer<O> {

    private int i=0;
    private final int length;
    private final O[] objects;

    public Incrementer(O... o){
        this.objects = o;
        this.length = o.length;
    }
    public Incrementer(int startIndex, O... o){
        this(o);
        this.i = startIndex;
    }

    public Incrementer<O> increment(){
        i = ++i%length;
        return this;
    }

    public O get(){
        return objects[i];
    }
}
