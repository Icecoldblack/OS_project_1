# OS Project 1 — CPU Scheduling & Memory Management Simulator

A Java-based simulator that demonstrates CPU scheduling algorithms and memory management techniques.

## Features

- **CPU Scheduling:** FCFS (First-Come, First-Served) and SJF (Shortest Job First)
- **Memory Allocation:** First-Fit, Best-Fit, and Worst-Fit
- **Page Replacement:** FIFO and LRU
- **Gantt Chart:** Visual text-based execution timeline
- **Statistics:** Waiting Time, Turnaround Time, and averages per algorithm

## Project Structure

```
OS_project_1/
├── src/
│   ├── Main.java            # Entry point, reads processes.txt
│   ├── Process.java         # Process data model (PID, arrival, burst, priority)
│   ├── Scheduler.java       # FCFS and SJF scheduling algorithms
│   └── MemoryManager.java   # Memory allocation and page replacement
├── processes.txt            # Input file with process data
```

## How to Run

```bash
javac -d bin src/*.java
java -cp bin Main
```

## Sample Input (`processes.txt`)

```
PID  Arrival_Time  Burst_Time  Priority
1    0             5           2
2    2             3           1
3    4             2           3
```

## Sample Output

```
========== FCFS (First-Come, First-Served) ==========

Gantt Chart:
| P1 | P2 | P3 |
0    5    8   10

Average Waiting Time:    2.33
Average Turnaround Time: 5.67
```
