package chumakov.alexei.server;

public interface Database<T> {
    boolean set(T index, T value);

    String get(T index);

    boolean delete(T index);
}
