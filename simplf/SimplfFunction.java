package simplf;
 
import java.util.List;

class SimplfFunction implements SimplfCallable {

    private final Stmt.Function declaration;
    private Environment closure;

    SimplfFunction(Stmt.Function declaration, Environment closure) {
        this.declaration = declaration;
        this.closure = closure;
    }

    public void setClosure(Environment environment) {
        this.closure = environment;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> args) {
        // creating a new environment in the function's closure
        Environment env = new Environment(closure);
        // binding the parameters to the arguments
        for (int i = 0; i < declaration.params.size(); i++) {
            Token param = declaration.params.get(i);
            Object value = null;
            if (i < args.size())
                value = args.get(i);
            env.define(param, param.lexeme, value);
        }

        // executing the function body in new environment and returning the last value
        Object last = interpreter.executeBlock(declaration.body, env);
        return last;
    }

    @Override
    public String toString() {
        return "<fn >";
    }

}