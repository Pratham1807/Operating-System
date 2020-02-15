package OperatingSystem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;

public class PriorityScheduling {
    static class Process{
        public int pid;
        public int burstTime;
        public int arrivalTime;
        public int completionTime;
        public int waitingTime;
        public int turnAroundTime;
        public int priority;
        Process(int pid, int burstTime, int arrivalTime, int priority){
            super();
            this.pid = pid;
            this.burstTime = burstTime;
            this.arrivalTime= arrivalTime;
            this.completionTime = 0;
            this.waitingTime = 0;
            this.turnAroundTime = 0;
            this.priority = priority;
        }
    }
    static class ProcessCmpPriority implements Comparator<Process> {
        public int compare(Process a, Process b){
            if(a.priority == b.priority){
                if(a.arrivalTime > b.arrivalTime){
                    return 1;
                }
                else{
                    return -1;
                }
            }
            else if(a.priority > b.priority){
                return 1;
            }
            else{
                return -1;
            }
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
    public static class GANTT{
        public int pid;
        public int time;
        GANTT(int pid, int time){
            this.pid = pid;
            this.time = time;
        }
    }
    public static void main(String[] args){
        TreeSet<Process> p= new TreeSet<>(new ProcessCmpAT());
        TreeSet<Process> processQueue= new TreeSet<>(new ProcessCmpPriority());
        ArrayList<GANTT> g = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 0; i < n; i++){
            p.add(new Process(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt()));
        }

        int timeCounter = 0;
        float wtAvg = 0, tatAvg = 0, ctAvg = 0;

        System.out.println("\n\nProcessId | Burst Time | Arrival Time  | Priority | Completion Time | Turn Around Time | Waiting Time");
        while(!p.isEmpty()){
            timeCounter = (timeCounter<p.first().arrivalTime) ? p.first().arrivalTime : timeCounter;
            while(p.first().arrivalTime <= timeCounter){
                processQueue.add(p.pollFirst());
                if(p.isEmpty()){
                    break;
                }
            }
            if(!processQueue.isEmpty()){
                Process temp = processQueue.pollFirst();
                temp.completionTime = timeCounter+temp.burstTime;
                temp.turnAroundTime = temp.completionTime - temp.arrivalTime;
                temp.waitingTime = temp.turnAroundTime - temp.burstTime;

                wtAvg = temp.waitingTime;
                tatAvg = temp.turnAroundTime;
                ctAvg = temp.completionTime;

                System.out.println("\t" + temp.pid + "\t\t\t" + temp.burstTime + "\t\t\t\t" + temp.arrivalTime + "\t\t\t\t"+ temp.priority + "\t\t\t\t" + temp.completionTime + "\t\t\t\t" + temp.turnAroundTime + "\t\t\t\t\t" + temp.waitingTime);

                g.add(new GANTT(temp.pid, temp.completionTime));
                timeCounter  = temp.completionTime;
            }
        }
        while(!processQueue.isEmpty()){
            Process temp = processQueue.pollFirst();
            temp.completionTime = timeCounter+temp.burstTime;
            temp.turnAroundTime = temp.completionTime - temp.arrivalTime;
            temp.waitingTime = temp.turnAroundTime - temp.burstTime;

            wtAvg = temp.waitingTime;
            tatAvg = temp.turnAroundTime;
            ctAvg = temp.completionTime;

            System.out.println("\t" + temp.pid + "\t\t\t" + temp.burstTime + "\t\t\t\t" + temp.arrivalTime + "\t\t\t\t"+ temp.priority + "\t\t\t\t" + temp.completionTime + "\t\t\t\t" + temp.turnAroundTime + "\t\t\t\t\t" + temp.waitingTime);

            g.add(new GANTT(temp.pid, temp.completionTime));
            timeCounter  = temp.completionTime;
        }
        System.out.println("_________________________________________________________________________________________________________");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\tAVERAGE :- " + ctAvg / n + "\t\t\t\t" + tatAvg / n + "\t\t\t\t\t" + wtAvg / n);

        System.out.print("\nGANTT CHART:\n");
        for(GANTT slot: g){
            System.out.print("  p"+slot.pid+" ");
        }
        System.out.print("\n0 ");
        for(GANTT slot: g){
            System.out.print(" "+slot.time+"  ");
        }
    }
}
