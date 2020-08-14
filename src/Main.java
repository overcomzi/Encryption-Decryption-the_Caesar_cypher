import java.io.FileNotFoundException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        try {
            HashMap<String, String> arguments = parseArg(args);
            ClientController controller = new ClientController();
            controller.execute(arguments);
        } catch (MissingParameterValueException | InvalidParameterType | FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static HashMap<String, String> parseArg(String[] args) throws MissingParameterValueException {
        HashMap<String, String> arguments = new HashMap<>(args.length);
        String arg = "";
        for (int i = 0; i < args.length; i += 2) {
            arg = args[i];
            if (args[i + 1].startsWith("-") && !isNumeric(args[i + 1])) {
                throw new MissingParameterValueException("Отсутствует значения параметра: " + arg);
            }
            arguments.put(args[i].substring(1), args[i + 1]);
        }

        return arguments;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}