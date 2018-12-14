package ru.job4j.tracker;

public class ValidateInput implements Input {

    private final Input input;

    public ValidateInput(final Input input) {
        this.input = input;
    }

    @Override
    public String ask(String question) {
        return this.input.ask(question);
    }

    @Override
    public int ask(String question, int[] range) {
        int value;
        while (true) {
            try {
                value = this.input.ask(question, range);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter correct value");
            } catch (MenuOutException e) {
                System.out.println(String.format("Please select in the range %d - %d",
                        range[0], range[range.length - 1]));
            }
        }
        return value;
    }
}
