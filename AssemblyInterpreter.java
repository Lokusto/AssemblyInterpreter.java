import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class AssemblyOperation {
    abstract void execute(Map<String, Integer> registers);
}

class MovOperation extends AssemblyOperation {
    private String dest;
    private String source;

    public MovOperation(String dest, String source) {
        this.dest = dest;
        this.source = source;
    }

    @Override
    void execute(Map<String, Integer> registers) {
        if (registers.containsKey(source)) {
            registers.put(dest, registers.get(source));
        } else {
            registers.put(dest, Integer.parseInt(source));
        }
    }
}

class AddOperation extends AssemblyOperation {
    private String dest;
    private String source;

    public AddOperation(String dest, String source) {
        this.dest = dest;
        this.source = source;
    }

    @Override
    void execute(Map<String, Integer> registers) {
        int value1 = registers.get(dest);
        int value2 = registers.containsKey(source) ? registers.get(source) : Integer.parseInt(source);
        registers.put(dest, value1 + value2);
    }
}

class SubOperation extends AssemblyOperation {
    private String dest;
    private String source;

    public SubOperation(String dest, String source) {
        this.dest = dest;
        this.source = source;
    }

    @Override
    void execute(Map<String, Integer> registers) {
        int value1 = registers.get(dest);
        int value2 = registers.containsKey(source) ? registers.get(source) : Integer.parseInt(source);
        registers.put(dest, value1 - value2);
    }
}

public class AssemblyInterpreter {
    public static void main(String[] args) {
        List<String> assemblyCode = new ArrayList<>();
        assemblyCode.add("MOV AX 10");
        assemblyCode.add("MOV BX, 5");
        assemblyCode.add("ADD AX, BX");
        assemblyCode.add("SUB AX, BX");
        assemblyCode.add("MOV AX, 20");
        assemblyCode.add("MOV BX, 15");
        assemblyCode.add("SUB AX, BX");
        assemblyCode.add("ADD AX, BX");
        assemblyCode.add("ADD AX, 35");
        assemblyCode.add("SUB BX, 5");
        assemblyCode.add("ADD AX, BX");

        Map<String, Integer> registers = new HashMap<>();
        for (String line : assemblyCode) {
            String[] parts = line.split("\\s+");
            if (parts.length < 3) {
                System.err.println("Instrução inválida: " + line);
                continue;
            }

            String operation = parts[0];
            String dest = parts[1];
            String source = parts[2];

            AssemblyOperation assemblyOperation = null;

            switch (operation) {
                case "MOV":
                    assemblyOperation = new MovOperation(dest, source);
                    break;
                case "ADD":
                    assemblyOperation = new AddOperation(dest, source);
                    break;
                case "SUB":
                    assemblyOperation = new SubOperation(dest, source);
                    break;
                default:
                    System.err.println("Operação desconhecida: " + operation);
            }

            if (assemblyOperation != null) {
                assemblyOperation.execute(registers);
            }

            System.out.println("AX = " + registers.get("AX"));
            System.out.println("BX = " + registers.get("BX"));
        }
    }
}
