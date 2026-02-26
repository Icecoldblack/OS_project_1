import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Scheduler {

    // ==================== FCFS ====================
    public static void fcfs(List<Process> original) {
        List<Process> processes = copyList(original);
        System.out.println("\n========== FCFS (First-Come, First-Served) ==========");

        processes.sort(Comparator.comparingInt(Process::getArrivalTime)
                .thenComparingInt(Process::getPid));

        List<String> ganttNames = new ArrayList<>();
        List<Integer> ganttTimes = new ArrayList<>();
        int currentTime = 0;

        for (Process p : processes) {
            if (currentTime < p.getArrivalTime()) {
                currentTime = p.getArrivalTime();
            }
            ganttNames.add("P" + p.getPid());
            ganttTimes.add(currentTime);

            p.setWaitingTime(currentTime - p.getArrivalTime());
            currentTime += p.getBurstTime();
            p.setTurnaroundTime(p.getWaitingTime() + p.getBurstTime());
        }
        ganttTimes.add(currentTime);

        printGanttChart(ganttNames, ganttTimes);
        printResults(processes);
    }

    // ==================== SJF ====================
    public static void sjf(List<Process> original) {
        List<Process> processes = copyList(original);
        System.out.println("\n========== SJF (Shortest Job First) ==========");

        List<Process> completed = new ArrayList<>();
        List<Process> remaining = new ArrayList<>(processes);
        List<String> ganttNames = new ArrayList<>();
        List<Integer> ganttTimes = new ArrayList<>();
        int currentTime = 0;

        while (!remaining.isEmpty()) {
            List<Process> available = new ArrayList<>();
            for (Process p : remaining) {
                if (p.getArrivalTime() <= currentTime) {
                    available.add(p);
                }
            }

            if (available.isEmpty()) {
                int earliest = Integer.MAX_VALUE;
                for (Process p : remaining) {
                    earliest = Math.min(earliest, p.getArrivalTime());
                }
                currentTime = earliest;
                continue;
            }

            available.sort(Comparator.comparingInt(Process::getBurstTime)
                    .thenComparingInt(Process::getArrivalTime));
            Process shortest = available.get(0);

            ganttNames.add("P" + shortest.getPid());
            ganttTimes.add(currentTime);

            shortest.setWaitingTime(currentTime - shortest.getArrivalTime());
            currentTime += shortest.getBurstTime();
            shortest.setTurnaroundTime(shortest.getWaitingTime() + shortest.getBurstTime());

            completed.add(shortest);
            remaining.remove(shortest);
        }
        ganttTimes.add(currentTime);

        printGanttChart(ganttNames, ganttTimes);
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

    private static void printGanttChart(List<String> names, List<Integer> times) {
        System.out.println("\nGantt Chart:");

        StringBuilder topBar = new StringBuilder("|");
        StringBuilder timeline = new StringBuilder();

        for (int i = 0; i < names.size(); i++) {
            String label = " " + names.get(i) + " ";
            topBar.append(label).append("|");
        }

        for (int i = 0; i < times.size(); i++) {
            if (i == 0) {
                timeline.append(times.get(i));
            } else {
                String prevLabel = " " + names.get(i - 1) + " ";
                int spacing = prevLabel.length() + 1 - String.valueOf(times.get(i)).length();
                for (int s = 0; s < spacing; s++) {
                    timeline.append(" ");
                }
                timeline.append(times.get(i));
            }
        }

        System.out.println(topBar);
        System.out.println(timeline);
    }

    private static void printResults(List<Process> processes) {
        System.out.printf("\n%-5s %-15s %-12s %-10s %-15s %-15s%n",
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
