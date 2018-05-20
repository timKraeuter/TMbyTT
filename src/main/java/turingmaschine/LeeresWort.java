package turingmaschine;

import java.util.Objects;

public class LeeresWort implements Zeichen {

    private static LeeresWort instance;

    private LeeresWort() {
    }

    public static LeeresWort getInstance() {
        if(Objects.isNull(instance)) {
            instance = new LeeresWort();
        }
        return instance;
    }

}
