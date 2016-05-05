package crawling.file;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

/**
 * Created by midori on 2016/05/05.
 */
public abstract class Input {
    public abstract void inputFile(String filepath, Collection<String> col);
}
