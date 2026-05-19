package factory;

public interface IFactory {
    IState testState();
    IState stateForBlackLineTest();
    IState stateForWhiteLineTest();
    IState emptyState();
    IState doubleLineStateTest();
}