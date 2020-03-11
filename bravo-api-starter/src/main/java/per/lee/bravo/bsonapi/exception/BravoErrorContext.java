package per.lee.bravo.bsonapi.exception;


import per.lee.bravo.bsonapi.body.errors.ErrorItem;

import java.util.HashSet;
import java.util.Set;

public class BravoErrorContext extends Exception {

    private Set<BravoApiAbstractException> exceptionSet = new HashSet<>();

    public BravoErrorContext add(BravoApiAbstractException e) {
        this.exceptionSet.add(e);
        return this;
    }

    public Set<BravoApiAbstractException> flush() {
        return this.exceptionSet;
    }

    public Set<ErrorItem> toErrors() {
        Set<ErrorItem> errors = new HashSet<>();
        return errors;
    }

}
