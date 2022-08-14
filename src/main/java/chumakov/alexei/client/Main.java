package chumakov.alexei.client;

import com.beust.jcommander.JCommander;

public class Main {
    public static void main(String[] args) {
        Args argv = new Args();
        JCommander.newBuilder()
                .addObject(argv)
                .build()
                .parse(args);

        Controller.connect(argv);
    }

}
