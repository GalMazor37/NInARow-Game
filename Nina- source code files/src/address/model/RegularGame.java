package address.model;
import address.utils.xsdClass.*;

import java.io.Serializable;

public class RegularGame extends Game implements Serializable {
    public RegularGame(GameDescriptor g) {
        super(g);
    }
}
