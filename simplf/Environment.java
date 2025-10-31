package simplf; 

class Environment {
    final Environment enclosing;
    private final java.util.Map<String, Object> values = new java.util.HashMap<>();

    Environment() {
        this.enclosing = null;
    }

    Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }

    void define(Token varToken, String name, Object value) {
        values.put(name, value);
    }

    void assign(Token name, Object value) {
        String key = name.lexeme;
        if (values.containsKey(key)) {
            values.put(key, value);
            return;
        }
        if (enclosing != null) {
            enclosing.assign(name, value);
            return;
        }
        throw new RuntimeError(name, "Undefined variable '" + key + "'.");
    }

    Object get(Token name) {
        String key = name.lexeme;
        if (values.containsKey(key)) {
            return values.get(key);
        }
        if (enclosing != null) {
            return enclosing.get(name);
        }
        throw new RuntimeError(name, "Undefined variable '" + key + "'.");
    }
}

