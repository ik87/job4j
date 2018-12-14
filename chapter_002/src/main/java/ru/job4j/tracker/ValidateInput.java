package ru.job4j.tracker;

public class ValidateInput extends ConsoleInput {
    @Override
    public int ask(String question, int[] range) {
        int value;
        while (true) {
            try {
                value = super.ask(question, range);
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
