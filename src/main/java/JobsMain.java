import com.fasterxml.jackson.databind.ObjectMapper;
import netscape.javascript.JSException;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JobsMain {

    public static void main(String[] args) {
        File positionsJson = new File("/Users/tran/Downloads/java-github-analysis-clinic/src/main/resources/positions.json");
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> positionsList = new ArrayList<HashMap<String, String>>();

        try {
            positionsList = mapper.readValue(positionsJson, ArrayList.class);
            List<Job> jobList = new ArrayList<Job>();

            for(Map<String, String> position : positionsList) {
                Job newJob = new Job(position.get("id"), position.get("type"), position.get("url"),
                        position.get("createdAt"), position.get("company"), position.get("companyUrl"), position.get("location"),
                        position.get("title"), position.get("description"), position.get("howToApply"), position.get("companyLogo"));
                jobList.add(newJob);
            }

            calculateJobPostingsPerCompany(jobList);
            writeGHJob();
            calculateNumJobsPerLocation(jobsList);

        } catch(IOException exception){
            System.out.println(exception.getMessage());
        }
        finally{
//            System.out.println("test");
        }

    }

    private static void calculateNumJobsPerLocation(List<Job> jobsList) {

        Map<String, Integer> locationsTotals = new HashMap<String, Integer>();
        for(Job jobs : jobsList) {
            if(locationsTotals.containsKey(jobs.getLocation())) {
                String loc = jobs.getLocation();
                locationsTotals.put(loc, locationsTotals.get(loc) + 1);
            } else
                locationsTotals.put(jobs.getLocation(), 1);
        }

        ArrayList<Map.Entry<String, Integer>> unsortedMap = new ArrayList<Map.Entry<String, Integer>>();
        for(Map.Entry<String, Integer> loc : locationsTotals.entrySet()){
            unsortedMap.add(loc);
        }


        Comparator<Map.Entry<String, Integer>> locCompare = new Comparator<Map.Entry<String, Integer>>() {

            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                Integer v1 = e1.getValue();
                Integer v2 = e2.getValue();
                return v2.compareTo(v1);
            }
        };

        Collections.sort(unsortedMap, locCompare);

        for(Map.Entry<String, Integer> e: unsortedMap) {
            System.out.println(e.getKey() + " " +locationsTotals.get(e.getKey()));
        }

    }

    private static void calculateJobPostingsPerCompany(List<Job> jobsList) {
        Map<String, Integer> jobPostingsMap = new HashMap<String, Integer>();

        for(Job job : jobsList){
            String companyName = job.getCompany();
            if(jobPostingsMap.containsKey(companyName)){
                jobPostingsMap.put(companyName, jobPostingsMap.get(companyName) + 1);
            } else {
                jobPostingsMap.put(companyName, 1);
            }
        }

        for(Map.Entry<String, Integer> displayJob : jobPostingsMap.entrySet()){
            System.out.println("Company: " + displayJob.getKey() + " Num postings: " + displayJob.getValue());
        }
    }

    private static void writeGHJob() throws IOException {
        Scanner scan = new Scanner(System.in);
        File newJob = new File("job.json");
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("Would you like to submit a job? (Y/N)");
        String response = scan.nextLine();
        if (response.equalsIgnoreCase("y")) {
            System.out.println("Enter a job type.");
            String type = scan.nextLine();
            System.out.println("Enter a URL.");
            String url = scan.nextLine();
            System.out.println("Enter a title.");
            String title = scan.nextLine();
            System.out.println("Enter a description.");
            String description = scan.nextLine();

            Job jobToAdd = new Job(type, url, title, description);
            mapper.writeValue(newJob, jobToAdd);
        } else {
            System.out.println("Goodbye.");
        }


    }
}
