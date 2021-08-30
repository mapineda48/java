package util;

@FunctionalInterface
public interface Task<T> {
    void run(Res<T> res, Rej rej);
}
