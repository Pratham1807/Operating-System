package OperatingSystem;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;

public class SJF_Preemptive {
    static class Process{
        public int pid;
        public int burstTime;
        public int arrivalTime;
        public int completionTime;
        public int waitingTime;
        public int turnAroundTime;
        Process(int pid, int burstTime, int arrivalTime){
            super();
            this.pid = pid;
            this.burstTime = burstTime;
            this.arrivalTime= arrivalTime;
            this.completionTime = 0;
            this.waitingTime = 0;
            this.turnAroundTime = 0;
        }
    }

    static class ProcessCmpAT implements Comparator<Process> {
        public int compare(Process a, Process b){
            if(a.arrivalTime > b.arrivalTime){
                return 1;
            }
            else{
                return -1;
            }
        }
    }

    static class ProcessCmpBT implements Comparator<Process> {
        public int compare(Process a, Process b){
            if(a.burstTime == b.burstTime){
                if(a.arrivalTime > b.arrivalTime){
                    return 1;
                }
                else{
                    return -1;
                }
            }
            else if(a.burstTime > b.burstTime){
                return 1;
            }
            else{
                return -1;
            }
        }
    }

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        TreeSet<Process> p = new TreeSet<>(new ProcessCmpAT());
        for (int i = 0; i < n; i++){
            p.add(new Process(sc.nextInt(), sc.nextInt(), sc.nextInt()));
        }

        int timeCounter = 0;
        //int[][] gantt = new int[2][];

        TreeSet<Process> tree = new TreeSet<>(new ProcessCmpBT());
        while(!p.isEmpty()){
            int AT = p.first().arrivalTime;
            int timeLapse = AT - timeCounter;
            if(!tree.isEmpty()){
                while(timeLapse>0){
                    System.out.println(tree.first().pid+" "+(AT-timeLapse));
                    int BT = tree.first().burstTime;
                    if(BT <= timeLapse){
                        tree.pollFirst();
                        timeLapse -= BT;
                    }
                    else{
                        tree.first().burstTime = BT - timeLapse;
                        timeLapse = 0;
                    }
                }
            }
            while(p.first().arrivalTime == AT){
                Process temp = p.pollFirst();
                tree.add(temp);
                if(p.isEmpty()){
                    break;
                }
            }
            timeCounter = AT;
        }
        while(!tree.isEmpty()){
            Process temp = tree.pollFirst();
            System.out.println(temp.pid+" "+timeCounter);
            timeCounter+=temp.burstTime;
        }
        System.out.println("  "+timeCounter);
/*
        // completion time = (total time taken before completion of the task)
        // turn around time = (completion time - arrival time)
        // waiting time = (turn around time - burst time)

        int timeCounter=0;

        // loop to find completion, waiting & turnaround time and make the gantt chart
        for (int i = 0; i < n; i++){

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
*/
    }
}
