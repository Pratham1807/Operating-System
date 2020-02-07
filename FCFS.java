package OperatingSystem;

import java.util.Arrays;
import java.util.Scanner;

/*5
1 8 0
2 4 1
3 3 2
4 7 3
5 2 3*/

public class FCFS {
    static class Process implements Comparable<Process>{
        public int pid;
        public int burstTime;
        public int arrivalTime;
        public int completionTime;
        public int waitingTime;
        public int turnAroundTime;
        Process(){
            super();
            pid = 0;
            burstTime =0;
            arrivalTime=0;
            completionTime = 0;
            waitingTime = 0;
            turnAroundTime = 0;
        }
        @Override
        public int compareTo(Process a){
            return this.arrivalTime - a.arrivalTime;
        }
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Process[] p = new Process[n];
        for (int i = 0; i < n; i++) {
            p[i] = new Process();
            p[i].pid = sc.nextInt();
            p[i].burstTime = sc.nextInt();
            p[i].arrivalTime = sc.nextInt();
        }

        // Sort the array on the basis of its arrival time
        Arrays.sort(p);

        int[][] gantt = new int[2][n];
        int counter = 0;
        System.out.println("GANTT CHART :-");

        // completion time = (total time taken before completion of the task)
        // turn around time = (completion time - arrival time)
        // waiting time = (turn around time - burst time)

        // loop to find completion, waiting & turnaround time and make the gantt chart
        for (int i = 0; i < n; i++) {
            p[i].completionTime = p[i].burstTime + ((p[i].arrivalTime > counter) ? p[i].arrivalTime : counter);
            counter += p[i].burstTime;
            p[i].turnAroundTime = p[i].completionTime - p[i].arrivalTime;
            p[i].waitingTime = p[i].turnAroundTime - p[i].burstTime;
            gantt[0][i] = p[i].pid;
            gantt[1][i] = p[i].waitingTime + p[i].arrivalTime;
        }
        for (int i = 0; i < n; i++) {
            System.out.print("p" + gantt[0][i] + "\t");
        }
        System.out.println("");
        for (int i = 0; i < n; i++) {
            System.out.print(gantt[1][i] + "\t");
        }
        System.out.print(p[n - 1].completionTime);
        float wtAvg = 0, tatAvg = 0, ctAvg = 0;
        System.out.println("\n\nProcessId | Burst Time | Arrival Time | Completion Time | Turn Around Time | Waiting Time");
        // printing values and computing averages
        for (int i = 0; i < n; i++) {
            wtAvg += p[i].waitingTime;
            ctAvg += p[i].completionTime;
            tatAvg += p[i].turnAroundTime;
            System.out.println("\t" + p[i].pid + "\t\t\t" + p[i].burstTime + "\t\t\t\t" + p[i].arrivalTime + "\t\t\t\t" + p[i].completionTime + "\t\t\t\t" + p[i].turnAroundTime + "\t\t\t\t\t" + p[i].waitingTime);
        }
        System.out.println("__________________________________________________________________________________________");
        System.out.println("\t\t\t\t\t\t\t\t\tAVERAGE :- " + ctAvg / n + "\t\t\t\t" + tatAvg / n + "\t\t\t\t" + wtAvg / n);
    }
}
