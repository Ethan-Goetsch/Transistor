package utils;

public interface IObservable<T>
{
    public void subscribe(IAction<T> action);
    public void unsubcribe(IAction<T> action);
}