import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MemoryManager {

    // ==================== MEMORY ALLOCATION ====================

    public static void firstFit(int[] blockSizes, int[] processSizes) {
        System.out.println("\n========== First-Fit Memory Allocation ==========");
        allocate(blockSizes, processSizes, "First-Fit");
    }

    public static void bestFit(int[] blockSizes, int[] processSizes) {
        System.out.println("\n========== Best-Fit Memory Allocation ==========");
        allocate(blockSizes, processSizes, "Best-Fit");
    }

    public static void worstFit(int[] blockSizes, int[] processSizes) {
        System.out.println("\n========== Worst-Fit Memory Allocation ==========");
        allocate(blockSizes, processSizes, "Worst-Fit");
    }

    private static void allocate(int[] blockSizes, int[] processSizes, String method) {
        int[] blocks = blockSizes.clone();
        int[] allocation = new int[processSizes.length];

        for (int i = 0; i < allocation.length; i++) {
            allocation[i] = -1;
        }

        for (int i = 0; i < processSizes.length; i++) {
            int chosenBlock = -1;

            for (int j = 0; j < blocks.length; j++) {
                if (blocks[j] >= processSizes[i]) {
                    if (method.equals("First-Fit")) {
                        chosenBlock = j;
                        break;
                    } else if (method.equals("Best-Fit")) {
                        if (chosenBlock == -1 || blocks[j] < blocks[chosenBlock]) {
                            chosenBlock = j;
                        }
                    } else if (method.equals("Worst-Fit")) {
                        if (chosenBlock == -1 || blocks[j] > blocks[chosenBlock]) {
                            chosenBlock = j;
                        }
                    }
                }
            }

            if (chosenBlock != -1) {
                allocation[i] = chosenBlock;
                blocks[chosenBlock] -= processSizes[i];
            }
        }

        System.out.printf("%-10s %-15s %-15s%n", "Process", "Size", "Block");
        System.out.println("----------------------------------------");
        for (int i = 0; i < processSizes.length; i++) {
            String block = (allocation[i] != -1) ? "Block " + (allocation[i] + 1) : "Not Allocated";
            System.out.printf("%-10d %-15d %-15s%n", (i + 1), processSizes[i], block);
        }
    }

    // ==================== PAGE REPLACEMENT ====================

    public static void fifoPageReplacement(int[] pages, int frameCount) {
        System.out.println("\n========== FIFO Page Replacement ==========");
        System.out.println("Frames: " + frameCount + "\n");

        LinkedList<Integer> frames = new LinkedList<>();
        int pageFaults = 0;

        System.out.printf("%-8s %-15s %-10s%n", "Page", "Frames", "Fault?");
        System.out.println("----------------------------------");

        for (int page : pages) {
            boolean fault = false;

            if (!frames.contains(page)) {
                fault = true;
                pageFaults++;
                if (frames.size() == frameCount) {
                    frames.removeFirst();
                }
                frames.add(page);
            }

            System.out.printf("%-8d %-15s %-10s%n", page, frames.toString(), fault ? "Yes" : "No");
        }

        System.out.println("\nTotal Page Faults (FIFO): " + pageFaults);
    }

    public static void lruPageReplacement(int[] pages, int frameCount) {
        System.out.println("\n========== LRU Page Replacement ==========");
        System.out.println("Frames: " + frameCount + "\n");

        List<Integer> frames = new ArrayList<>();
        int pageFaults = 0;

        System.out.printf("%-8s %-15s %-10s%n", "Page", "Frames", "Fault?");
        System.out.println("----------------------------------");

        for (int page : pages) {
            boolean fault = false;

            if (frames.contains(page)) {
                frames.remove(Integer.valueOf(page));
                frames.add(page);
            } else {
                fault = true;
                pageFaults++;
                if (frames.size() == frameCount) {
                    frames.remove(0);
                }
                frames.add(page);
            }

            System.out.printf("%-8d %-15s %-10s%n", page, frames.toString(), fault ? "Yes" : "No");
        }

        System.out.println("\nTotal Page Faults (LRU): " + pageFaults);
    }
}
