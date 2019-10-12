package rs.raf.storage.app.model;

import java.util.List;
import java.util.Scanner;

public class Lifecycle {

    private static final String OPTION_SEPARATOR = " - ";
    private static final String SELECTION = "Selection: ";
    private static final String BACK_OPTION = "Back";
    private static final String EXIT_OPTION = "Exit";
    private static final String WRONG_OPTION = "Wrong option";

    private Structure structure;
    private Scanner input;

    public Lifecycle(Structure structure) {
        this.structure = structure;

        input = new Scanner(System.in);
    }

    public void run() {
        ask(structure.getQuestion());
    }

    private void ask(Question question) {
        System.out.println(question.getTitle());

        int index = 0;

        List<Option> options = question.getOptions();
        options.add(new ExecuteOption(BACK_OPTION) {
            @Override
            public void execute() {
                if(question.hasParent()) {
                    ask(question.getParent());
                }
            }
        });
        options.add(new ExecuteOption(EXIT_OPTION) {
            @Override
            public void execute() {
                System.exit(0);
            }
        });

        for(Option option : options) {
            System.out.println(++index + OPTION_SEPARATOR + option.getTitle());
        }

        System.out.print(SELECTION);

        Option selection;

        try {
            selection = options.get(input.nextInt() - 1);
        }catch(IndexOutOfBoundsException e) {
            System.out.println(WRONG_OPTION);
            ask(question);
            return;
        }

        selection.onSelected();

        if(selection.hasQuestion()) {
            ask(selection.getQuestion());
        }else {
            ask(structure.getQuestion());
        }
    }
}
