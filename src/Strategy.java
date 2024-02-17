public interface Strategy
{
    enum computerMode
    {
        safeBet,
        predictable,
        notAgain,
        random,
        cheater
    }
    public computerMode[] currentMode = new computerMode[1];
}
