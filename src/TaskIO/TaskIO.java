package TaskIO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class TaskIO {
    public static void main(String[] args) throws IOException {
        Path pathPrices = Path.of("resourses", "items-price.txt");
        Path pathNames = Path.of("resourses", "items-name.txt");
        Path pathErrors = Path.of("resourses", "items-errors.txt");
        Path pathResult = Path.of("resourses", "items-result.txt");
        HashSet ids = new HashSet<String>();
        Scanner scanner = null;
        Stream<String> stream = null;
        Stream<String> stream1 = null;
        HashMap prices = new HashMap<String, String>();
        HashMap names = new HashMap<String, String>();
        List<String> result = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        try {
            scanner = new Scanner(pathPrices);
            while (scanner.hasNext()) {
                String str = scanner.next();
                String [] parts = str.split(",");
                if(!parts[0].equals("ID")) {
                    prices.put(parts[0], parts[1]);
                    ids.add(parts[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            stream = Files.lines(pathNames);
            stream.forEach(str ->
                    {
                        String[] parts = str.toString().split(",");
                        if(!parts[0].equals("ID")) {
                            names.put(parts[0], parts[1]);
                            ids.add(parts[0]);
                        }
                    }
            );

        } catch (IOException e) {
            e.printStackTrace();
        }


        stream1 = ids.stream();
        stream1.forEach(str-> {
            String name = (String) names.get(str);
            String price = (String) prices.get( str);
            if(name!=null&&price!=null) {
                result.add(str + "," + name + "," + price);
            } else {
                errors.add(str);
            }
        });
        Files.write(pathErrors, errors);
        Files.write(pathResult, result);


    }
}
