import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class JobsMain {

  public static void main(String[] args) throws IOException {
    // load JSON into Java data structure
    ClassLoader classLoader = JobsMain.class.getClassLoader();
    File positionsJson = new File(classLoader.getResource("positions.json").getFile());
    ObjectMapper mapper = new ObjectMapper();
    List<HashMap<String, String>> positionsList = mapper.readValue(positionsJson, ArrayList.class);
    System.out.println(positionsList.get(0).get("title"));

    // calculate totals for each location
    Map<String, Integer> locations = new HashMap<>();
    for (HashMap<String, String> position : positionsList) {
      String location = position.get("location");
      if (locations.containsKey(location)) {
        Integer previousCount = locations.get(location);
        locations.put(location, previousCount + 1);
      } else {
        locations.put(location, 1);
      }
    }
    System.out.println(locations);
    for (String location : locations.keySet()) {
      System.out.println(location + ": " + locations.get(location));
    }

    // calculate totals for each company
    Map<String, Integer> companies = new HashMap<>();
    for (HashMap<String, String> position : positionsList) {
      String company = position.get("company");
      if (companies.containsKey(company)) {
        companies.put(company, companies.get(company) + 1);
      } else {
        companies.put(company, 1);
      }
    }
    System.out.println(companies);

    // write to JSON file
    Scanner scanner = new Scanner(System.in);
    String userInput = "";
    while (!userInput.equals("y") && !userInput.equals("n")) {
      System.out.print("\nWould you like to post a new job? (y/n)\n> ");
      userInput = scanner.nextLine();
    }

    if (userInput.equals("y")) {
      System.out.print("Type: ");
      String jobType = scanner.nextLine();
      System.out.print("URL: ");
      String url = scanner.nextLine();
      System.out.print("Title: ");
      String title = scanner.nextLine();
      System.out.print("Description: ");
      String description = scanner.nextLine();

      File jobJson = new File("src/main/resources/job.json");
      JsonFactory jf = new JsonFactory();
      JsonGenerator jg = jf.createGenerator(jobJson, JsonEncoding.UTF8);
      jg.useDefaultPrettyPrinter();
      jg.writeStartObject();
      jg.writeStringField("type", jobType);
      jg.writeStringField("url", url);
      jg.writeStringField("title", title);
      jg.writeStringField("description", description);
      jg.writeEndObject();
      jg.close();

      System.out.println("JSON created!");
    }

    System.out.println("Goodbye.");
  }
}
