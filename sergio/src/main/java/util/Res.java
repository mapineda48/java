package util;

@FunctionalInterface
public interface Res<T> {
    void run(T arg);
}