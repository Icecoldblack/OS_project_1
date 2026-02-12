public class Process {
    private int pid;
    private int arrivalTime;
    private int burstTime;
    private int priority;

    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    // Getters
    public int getPid() {
        return pid;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }

    // Setters
    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Process{PID=" + pid +
                ", ArrivalTime=" + arrivalTime +
                ", BurstTime=" + burstTime +
                ", Priority=" + priority + "}";
    }
}
