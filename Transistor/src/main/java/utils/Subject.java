package utils;

import java.util.ArrayList;
import java.util.List;

public class Subject<T> implements IObserver<T>, IObservable<T>
{
    private final List<IAction<T>> observers;

    public Subject()
    {
        this.observers = new ArrayList<>();
    }

    @Override
    public void subscribe(IAction<T> action)
    {
        observers.add(action);
    }

    @Override
    public void unsubcribe(IAction<T> action)
    {
        observers.remove(action);
    }

    @Override
    public void execute(T args)
    {
        observers.forEach(observer -> observer.execute(args));
    }
}
