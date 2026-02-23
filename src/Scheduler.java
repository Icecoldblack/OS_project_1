import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Scheduler {

    // ==================== FCFS ====================
    public static void fcfs(List<Process> original) {
        List<Process> processes = copyList(original);
        System.out.println("\n========== FCFS (First-Come, First-Served) ==========");

        // Sort by arrival time (ties broken by PID)
        processes.sort(Comparator.comparingInt(Process::getArrivalTime)
                .thenComparingInt(Process::getPid));

        int currentTime = 0;
        for (Process p : processes) {
            // If CPU is idle, jump to the next process's arrival
            if (currentTime < p.getArrivalTime()) {
                currentTime = p.getArrivalTime();
            }
            p.setWaitingTime(currentTime - p.getArrivalTime());
            currentTime += p.getBurstTime();
            p.setTurnaroundTime(p.getWaitingTime() + p.getBurstTime());
        }

        printResults(processes);
    }

    // ==================== SJF ====================
    public static void sjf(List<Process> original) {
        List<Process> processes = copyList(original);
        System.out.println("\n========== SJF (Shortest Job First) ==========");

        List<Process> completed = new ArrayList<>();
        List<Process> remaining = new ArrayList<>(processes);
        int currentTime = 0;

        while (!remaining.isEmpty()) {
            // Find processes that have arrived by currentTime
            List<Process> available = new ArrayList<>();
            for (Process p : remaining) {
                if (p.getArrivalTime() <= currentTime) {
                    available.add(p);
                }
            }

            if (available.isEmpty()) {
                // No process available — jump ahead to nearest arrival
                int earliest = Integer.MAX_VALUE;
                for (Process p : remaining) {
                    earliest = Math.min(earliest, p.getArrivalTime());
                }
                currentTime = earliest;
                continue;
            }

            // Pick the one with the shortest burst time
            available.sort(Comparator.comparingInt(Process::getBurstTime)
                    .thenComparingInt(Process::getArrivalTime));
            Process shortest = available.get(0);

            shortest.setWaitingTime(currentTime - shortest.getArrivalTime());
            currentTime += shortest.getBurstTime();
            shortest.setTurnaroundTime(shortest.getWaitingTime() + shortest.getBurstTime());

            completed.add(shortest);
            remaining.remove(shortest);
        }

        // Sort by PID for display
        completed.sort(Comparator.comparingInt(Process::getPid));
        printResults(completed);
    }

    // ==================== Helper Methods ====================

    private static List<Process> copyList(List<Process> original) {
        List<Process> copy = new ArrayList<>();
        for (Process p : original) {
            copy.add(new Process(p));
        }
        return copy;
    }

    private static void printResults(List<Process> processes) {
        System.out.printf("%-5s %-15s %-12s %-10s %-15s %-15s%n",
                "PID", "Arrival_Time", "Burst_Time", "Priority", "Waiting_Time", "Turnaround_Time");
        System.out.println("-----------------------------------------------------------------------");

        double totalWT = 0, totalTAT = 0;
        for (Process p : processes) {
            System.out.printf("%-5d %-15d %-12d %-10d %-15d %-15d%n",
                    p.getPid(), p.getArrivalTime(), p.getBurstTime(),
                    p.getPriority(), p.getWaitingTime(), p.getTurnaroundTime());
            totalWT += p.getWaitingTime();
            totalTAT += p.getTurnaroundTime();
        }

        System.out.printf("\nAverage Waiting Time:    %.2f%n", totalWT / processes.size());
        System.out.printf("Average Turnaround Time: %.2f%n", totalTAT / processes.size());
    }
}
