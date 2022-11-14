import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
//        BufferedReader reader = null;
        try (
//              Automate finally check
                BufferedReader reader = new BufferedReader(new FileReader(args[0]))
        ) {
            processFile(reader);
        }
//        Unchecked exception
        catch (ArithmeticException ex) {
            System.out.println("Invalid math operation: " + ex.getMessage());
        }
//        Checked exception
        catch (FileNotFoundException ex) {
            System.out.println("File not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IOException Error: " + ex.getMessage());
        } catch (InvalidStatementException ex) {
            System.out.println("InvalidStatementException Error: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
//        manual finally check
//         {
//            System.out.println("Closing file: " + args[0]);
//            assert reader != null;
//            reader.close();
//        }

    }

    private static void processFile(BufferedReader reader) throws IOException, InvalidStatementException {
        String inputLine = null;
        while ((inputLine = reader.readLine()) != null)
            performOperation(inputLine);
    }

    private static void performOperation(String inputLine) throws InvalidStatementException {
        String[] parts = inputLine.split(" ");

        if (parts.length != 3) throw new InvalidStatementException("Statement must have 3 parts");

        MathOperation operation = MathOperation.valueOf(parts[0].toUpperCase());
        int leftVal = valueFromWord(parts[1]);
        int rightVal = valueFromWord(parts[2]);

        int result = execute(operation, leftVal, rightVal);

        System.out.println(inputLine + " = " + result);
    }

    static int execute(MathOperation operation, int leftVal, int rightVal) {
        int result = 0;
        switch (operation) {
            case ADD -> result = leftVal + rightVal;
            case SUBTRACT -> result = leftVal - rightVal;
            case MULTIPLY -> result = leftVal * rightVal;
            case DIVIDE -> {
                if (rightVal == 0) {
                    throw new IllegalArgumentException("Cannot divide with value zero.");
                }
                result = leftVal / rightVal;
            }
        }
        ;
        return result;
    }

    static int valueFromWord(String word) {
        String[] numberWords = {
                "zero", "one", "two", "three", "four",
                "five", "six", "seven", "eight", "nine"
        };
        int value = -1;
        for (int index = 0; index < numberWords.length; index++) {
            if (word.equals(numberWords[index])) {
                value = index;
                break;
            }
        }
        if (value == -1)
            value = Integer.parseInt(word);

        return value;
    }

}
