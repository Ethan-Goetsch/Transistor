package utils;

@FunctionalInterface
public interface IAction<T>
{
    public void execute(T args);
}
