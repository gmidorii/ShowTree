package crawling.selector;

import crawling.exception.FromBeginningException;

import java.io.IOException;

/**
 * Created by midori on 2016/05/05.
 */
public abstract class Selector {
    public abstract void select() throws IOException, FromBeginningException;
}
