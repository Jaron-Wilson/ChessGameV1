package io.github.jron.chess.common;

public class Incrementor<O> {

    private int i=0;
    private final int length;
    private final O[] objects;

    public Incrementor(int startIndex, O... o){
        this.objects = o;
        this.length = o.length;
    }

    public Incrementor<O> increment(){
        i = ++i%length;
        return this;
    }

    public O get(){
        return objects[i];
    }
}
