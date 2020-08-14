import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class ClientController {
    // TODO: Валидация параметров - точная типизация значений параметров
    // TODO: Разрешить только конкретные операции - аргументы
    protected void execute(HashMap<String, String> arguments) throws InvalidParameterType, FileNotFoundException {
        validation(arguments);

        String mode = arguments.getOrDefault("mode", "enc");
        String data = arguments.getOrDefault("data", "");
        String alg = arguments.getOrDefault("alg", "shift");
        int key = Integer.parseInt(arguments.getOrDefault("key", "0"));
        String inPath = arguments.getOrDefault("in", "");
        String outPath = arguments.getOrDefault("out", "");

        String message = getMessage(data, inPath);
        AlgorithmContext algorithmContext = new AlgorithmContext();
        switch (alg) {
            case "shift":
                algorithmContext.setAlgorithm(new ShiftAlgorithm(key));
                break;
            case "unicode":
                algorithmContext.setAlgorithm(new UnicodeAlgorithm(key));
                break;
        }

        String output = message;
        switch (mode) {
            case "enc":
                output = algorithmContext.encode(message);
                if (outPath.equals("")) {
                    System.out.println(output);
                } else {
                    printTo(outPath, output);
                }
                break;
            case "dec":
                output = algorithmContext.decrypt(message);
                if (outPath.equals("")) {
                    System.out.println(output);
                } else {
                    printTo(outPath, output);
                }
                break;
        }
    }

    private void validation(HashMap<String, String> arguments) throws InvalidParameterType {
        try {
            Integer.parseInt(arguments.getOrDefault("key", "0"));
        } catch (NumberFormatException e) {
            InvalidParameterType thr = new InvalidParameterType("Параметр -key должен быть числом");
            thr.initCause(e);
            throw thr;
        }
        String alg = arguments.getOrDefault("alg", "shift");
        if (!alg.equalsIgnoreCase("Shift") &&
                !alg.equalsIgnoreCase("Unicode")) {
            throw new InvalidParameterType("Не несуществует алгоритма " + alg);
        }
        String mode = arguments.getOrDefault("mode", "enc");
        if (!mode.equalsIgnoreCase("enc") &&
                !mode.equalsIgnoreCase("dec")) {
            throw new InvalidParameterType("Не существует операции " + mode);
        }

    }

    private String getMessage(String data, String inPath) throws FileNotFoundException {
        String message = "";
        if (!data.equals("")) {
            return data;
        }
        if (!(inPath.equals(""))) {
            message = readFrom(inPath);
        }

        return message;

    }

    private void printTo(String pathName, String message) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File(pathName))) {
            writer.write(message);
        } catch (IOException e) {
            FileNotFoundException thr = new FileNotFoundException("Ошибка открытия файла для записи: " + pathName);
            thr.initCause(e);
            throw thr;
        }
    }

    private String readFrom(String pathName) throws FileNotFoundException {
        StringBuilder message = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(pathName))) {
            while (scanner.hasNextLine()) {
                message.append(scanner.nextLine());
                message.append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new FileNotFoundException("Ошибка открытия файла для чтения: " + pathName);
        }
        return message.toString();
    }
}
