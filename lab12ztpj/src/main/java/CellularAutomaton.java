import org.graalvm.polyglot.*;

import java.nio.file.Files;
import java.nio.file.Paths;

public class CellularAutomaton {
    private Context context;
    private Value jsBindings;

    public CellularAutomaton(String scriptName) {
        loadScript(scriptName);
        System.out.println(scriptName);
    }

    public void loadScript(String scriptName) {
        clearContext();
        try {
            context = Context.newBuilder("js").allowAllAccess(true).build();
            context.eval("js", new String(Files.readAllBytes(Paths.get(scriptName))));
            jsBindings = context.getBindings("js");
            System.out.println(scriptName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object callFunction(String function, Object... args) {
        try {
            Value jsFunction = jsBindings.getMember(function);
            return jsFunction.execute(args).as(int[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clearContext() {
        if(context != null) {
            context.close();
            context = null;
            jsBindings = null;
        }
    }
}
