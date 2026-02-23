import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    // Store all processes read from the file
    static List<Process> processList = new ArrayList<>();

    public static void main(String[] args) {
        String filename = "processes.txt";

        readProcesses(filename);

        // Display loaded processes
        System.out.println(" Loaded Processes ");
        System.out.printf("%-5s %-15s %-12s %-10s%n", "PID", "Arrival_Time", "Burst_Time", "Priority");
        System.out.println("-------------------------------------------");
        for (Process p : processList) {
            System.out.printf("%-5d %-15d %-12d %-10d%n",
                    p.getPid(), p.getArrivalTime(), p.getBurstTime(), p.getPriority());
        }
        System.out.println("\nTotal processes loaded: " + processList.size());

        // Run FCFS and SJF scheduling algorithms
        Scheduler.fcfs(processList);
        Scheduler.sjf(processList);
    }

    /**
     * Reads process data from a tab/whitespace-delimited text file.
     * Skips the header line and parses each subsequent line into a Process object.
     */
    public static void readProcesses(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;

                // Skip the header row
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] parts = line.split("\\s+");
                if (parts.length >= 4) {
                    int pid = Integer.parseInt(parts[0]);
                    int arrivalTime = Integer.parseInt(parts[1]);
                    int burstTime = Integer.parseInt(parts[2]);
                    int priority = Integer.parseInt(parts[3]);

                    processList.add(new Process(pid, arrivalTime, burstTime, priority));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number in file: " + e.getMessage());
        }
    }
}
