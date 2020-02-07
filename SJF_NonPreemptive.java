package OperatingSystem;

import java.util.Arrays;
import java.util.Scanner;

public class SJF_NonPreemptive {
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
            return this.burstTime - a.burstTime;
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

        // completion time = (total time taken before completion of the task)
        // turn around time = (completion time - arrival time)
        // waiting time = (turn around time - burst time)

        int timeCounter=0;
        // loop to find completion, waiting & turnaround time and make the gantt chart
        for (int i = 0; i < n; i++) {
            int j;
            for(j=0;j<n;j++){
                if(p[j].arrivalTime<=timeCounter){
                    break;
                }
            }
            gantt[0][i] = p[j].pid;
            gantt[1][i] = timeCounter;
            timeCounter += p[j].burstTime;
            p[j].completionTime = timeCounter;
            p[j].turnAroundTime = p[j].completionTime - p[j].arrivalTime;
            p[j].waitingTime = p[j].turnAroundTime - p[j].burstTime;
            // setting the arrival time to max so that the same process doesnot get picked up again
            p[j].arrivalTime = Integer.MAX_VALUE;
        }
        for (int i = 0; i < n; i++) {
            p[i].arrivalTime = p[i].completionTime - p[i].turnAroundTime;
        }
        System.out.println("GANTT CHART :-");
        for (int i = 0; i < n; i++) {
            System.out.print("p" + gantt[0][i] + "\t");
        }
        System.out.println("");
        for (int i = 0; i < n; i++) {
            System.out.print(gantt[1][i] + "\t");
        }
        System.out.println(timeCounter);

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
