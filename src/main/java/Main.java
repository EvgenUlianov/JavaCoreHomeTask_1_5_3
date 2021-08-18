import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    //private static final String FILE_XML = "data.xml";
    private static final String FILE_JSON = "data.gson";

    public static void main(String[] args) {
        System.out.println("Задача 3: JSON парсер (со звездочкой *)");

        makeJson();

        List<Employee> list = jsonToList();
        list.forEach(System.out::println);
    }

    public static  List<Employee> jsonToList(){
        List<Employee> list = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(FILE_JSON));
            JSONArray employeesJSON = (JSONArray) obj;

            for (Object  element : employeesJSON) {
                Employee employee = new Employee();
                JSONObject employeeJSON = (JSONObject)  element;
                employee.setId((Long)employeeJSON.get("id"));
                employee.setFirstName((String)employeeJSON.get("firstName"));
                employee.setLastName((String)employeeJSON.get("lastName"));
                employee.setCountry((String)employeeJSON.get("country"));
                employee.setAge(((Long)employeeJSON.get("age")).intValue());
                list.add(employee);
            }


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static void makeJson() {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee(1,"John", "Smith", "USA", 25));
        list.add(new Employee(2,"Inav", "Petrov", "RU", 23));
        list.add(new Employee(3,"Irina", "Smelova", "UA", 21));

        // function 'setPrettyPrinting' makes returned String pretty)))
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();

        saveTextFile(gson.toJson(list));
    }

    private static void saveTextFile(String extForSaving) {
        if (!extForSaving.isEmpty()) {
            try (FileWriter writer = new FileWriter(Main.FILE_JSON, false)) {
                // запись всей строки
                writer.write(extForSaving);
                // дозаписываем и очищаем буфер
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
