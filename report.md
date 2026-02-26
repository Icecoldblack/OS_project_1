# OS Project 1: CPU Scheduling & Memory Management Report

---

## 1. Introduction

This project implements a CPU scheduling simulator and memory management system in Java. The program reads process data from a text file, simulates multiple scheduling algorithms, and demonstrates memory allocation and page replacement strategies. The project consists of four Java source files: `Main.java`, `Process.java`, `Scheduler.java`, and `MemoryManager.java`.

---

## 2. Scheduling Algorithms Implemented

### 2.1 First-Come, First-Served (FCFS)

FCFS is the simplest scheduling algorithm. Processes are executed in the order they arrive. The implementation sorts processes by arrival time and runs each one to completion before moving to the next.

**How it works:**
- Sort all processes by arrival time
- Execute each process in order, one at a time
- If the CPU is idle (no process has arrived yet), jump to the next arrival time
- Calculate Waiting Time (WT) = Start Time - Arrival Time
- Calculate Turnaround Time (TAT) = WT + Burst Time

### 2.2 Shortest Job First (SJF) — Non-Preemptive

SJF selects the process with the shortest burst time among all processes that have arrived. This minimizes average waiting time compared to FCFS.

**How it works:**
- At each point in time, look at all processes that have arrived
- Pick the one with the smallest burst time
- Run it to completion, then repeat
- If no process has arrived, advance time to the next arrival

---

## 3. Sample Test Cases and Results

### 3.1 Input Data (processes.txt)

| PID | Arrival Time | Burst Time | Priority |
|-----|-------------|------------|----------|
| 1   | 0           | 5          | 2        |
| 2   | 2           | 3          | 1        |
| 3   | 4           | 2          | 3        |

### 3.2 FCFS Results

**Gantt Chart:**

```
| P1 | P2 | P3 |
0    5    8   10
```

| PID | Waiting Time | Turnaround Time |
|-----|-------------|-----------------|
| 1   | 0           | 5               |
| 2   | 3           | 6               |
| 3   | 4           | 6               |

- **Average Waiting Time:** 2.33
- **Average Turnaround Time:** 5.67

### 3.3 SJF Results

**Gantt Chart:**

```
| P1 | P3 | P2 |
0    5    7   10
```

| PID | Waiting Time | Turnaround Time |
|-----|-------------|-----------------|
| 1   | 0           | 5               |
| 2   | 5           | 8               |
| 3   | 1           | 3               |

- **Average Waiting Time:** 2.00
- **Average Turnaround Time:** 5.33

**Observation:** SJF produces a lower average waiting time (2.00 vs 2.33) and lower average turnaround time (5.33 vs 5.67) compared to FCFS for this data set, which is expected since SJF is optimal for minimizing average waiting time.

---

## 4. Memory Management (Optional — Step 4)

### 4.1 Memory Allocation

Three memory allocation strategies were implemented using the following test data:

- **Memory Blocks:** 100 KB, 500 KB, 200 KB, 300 KB, 600 KB
- **Process Sizes:** 212 KB, 417 KB, 112 KB, 426 KB

| Process | Size | First-Fit | Best-Fit | Worst-Fit |
|---------|------|-----------|----------|-----------|
| 1       | 212  | Block 2   | Block 4  | Block 5   |
| 2       | 417  | Block 5   | Block 2  | Block 2   |
| 3       | 112  | Block 2   | Block 3  | Block 5   |
| 4       | 426  | Not Allocated | Block 5 | Not Allocated |

**Observation:** Best-Fit was able to allocate all 4 processes by using memory more efficiently. First-Fit and Worst-Fit both failed to allocate Process 4 because larger blocks were consumed by smaller processes early on.

### 4.2 Page Replacement

FIFO and LRU page replacement algorithms were tested with:

- **Page Reference String:** 7, 0, 1, 2, 0, 3, 0, 4, 2, 3
- **Number of Frames:** 3

| Algorithm | Total Page Faults |
|-----------|------------------|
| FIFO      | 9                |
| LRU       | 8                |

**Observation:** LRU produced fewer page faults (8 vs 9) because it makes smarter eviction decisions by removing the page that hasn't been used for the longest time, rather than simply the oldest page.

---

## 5. Challenges Faced

1. **Preventing data corruption across algorithms:** Running multiple scheduling algorithms on the same process list would modify the original data. This was solved by using a copy constructor in the `Process` class to create deep copies before each algorithm runs.

2. **SJF tie-breaking:** When multiple processes have the same burst time, deciding which to run first required a tie-breaking rule. The implementation breaks ties by arrival time to ensure consistent results.

3. **Gantt chart formatting:** Aligning the timeline numbers underneath the Gantt chart bars required careful spacing calculations based on process label widths.

---

## 6. Conclusion

This project successfully implements two CPU scheduling algorithms (FCFS and SJF) along with three memory allocation strategies (First-Fit, Best-Fit, Worst-Fit) and two page replacement algorithms (FIFO and LRU). The results demonstrate the trade-offs between different algorithms — SJF outperforms FCFS in waiting time, Best-Fit uses memory more efficiently than First-Fit or Worst-Fit, and LRU produces fewer page faults than FIFO.
