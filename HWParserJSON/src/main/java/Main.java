import com.google.gson.*;
import com.opencsv.*;
import com.opencsv.bean.*;
import org.json.simple.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Main {

    private static final String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};

    public static List<Employee> parseCSV(String[] mapping, String fileName) {
        List<Employee> list = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(mapping);
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            list = csv.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

   public static List<Employee> parseXML(String xmlFile) {
       List<Employee> employeesList = new ArrayList<>();
       try {
           DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
           DocumentBuilder builder = factory.newDocumentBuilder();
           Document doc = builder.parse(xmlFile);
           Node root = doc.getDocumentElement();
           NodeList nodeList = root.getChildNodes();
           System.out.println();
           for (int i = 0; i < nodeList.getLength(); i++) {
               Employee employee = new Employee();
               Node node = nodeList.item(i);
               if (Node.ELEMENT_NODE == node.getNodeType()) {
                   NodeList tagList = node.getChildNodes();
                   for (int j = 0; j < tagList.getLength(); j++) {
                       Node deeperNode = tagList.item(j);
                       if (Node.ELEMENT_NODE == deeperNode.getNodeType()) {
                           employee.setParsedData(deeperNode.getNodeName(), deeperNode.getChildNodes().item(0).getTextContent());
                       }
                   }
                   employeesList.add(employee);
               }
           }
       } catch (SAXException | ParserConfigurationException | IOException e) {
           e.getStackTrace();
       }
       return employeesList;
   }

   public static String listToJson(List<Employee> employeeList) {
       Gson gson = new GsonBuilder()
               .setPrettyPrinting()
               .create();
       return gson.toJson(employeeList);
   }

    public static void writeListToJSONFile(String JsonList, String fileName) {
        JSONObject obj = new JSONObject();
        JSONArray JsonArray = (JSONArray) JSONValue.parse(JsonList);
        obj.put("Employees", JsonArray);
        try (FileWriter file = new FileWriter(fileName + ".json"))
        {
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();}
    }

    public static void main(String[] args) {
        String fileName = "data.csv";
        String[] employee1 = {"1", "John", "Smith", "USA", "25"};
        String[] employee2 = {"2", "Ivan", "Petrov", "RU", "23"};

        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            writer.writeNext(employee1);
            writer.writeNext(employee2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Employee> csvEmployeeList = parseCSV(columnMapping, fileName);
        String csvListToJson = listToJson(csvEmployeeList);
        writeListToJSONFile(csvListToJson, "data1");

        List<Employee> xmlEmployeeList = parseXML("data.xml");
        String xmlListToJson = listToJson(xmlEmployeeList);
        writeListToJSONFile(xmlListToJson, "data2");
    }

}


