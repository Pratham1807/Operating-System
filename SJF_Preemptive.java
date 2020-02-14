package OperatingSystem;

import java.util.*;

/*5
1 8 0
2 4 1
3 3 2
4 7 3
5 2 3*/

public class SJF_Preemptive {
    static class Process{
        public int pid;
        public int burstTime;
        public int arrivalTime;
        public int completionTime;
        public int waitingTime;
        public int turnAroundTime;
        public int burstTimeCtr;
        Process(int pid, int burstTime, int arrivalTime){
            super();
            this.pid = pid;
            this.burstTime = burstTime;
            this.arrivalTime= arrivalTime;
            this.completionTime = 0;
            this.waitingTime = 0;
            this.turnAroundTime = 0;
            this.burstTimeCtr = burstTime;
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
            if(a.burstTimeCtr == b.burstTimeCtr){
                if(a.arrivalTime > b.arrivalTime){
                    return 1;
                }
                else{
                    return -1;
                }
            }
            else if(a.burstTimeCtr > b.burstTimeCtr){
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

    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        TreeSet<Process> p = new TreeSet<>(new ProcessCmpAT());
        for (int i = 0; i < n; i++){
            p.add(new Process(sc.nextInt(), sc.nextInt(), sc.nextInt()));
        }

        int timeCounter = 0;

        TreeSet<Process> tree = new TreeSet<>(new ProcessCmpBT());
        ArrayList<GANTT> Gantt = new ArrayList<>();
        // completion time = (total time taken before completion of the task)
        // turn around time = (completion time - arrival time)
        // waiting time = (turn around time - burst time)
        float wtAvg = 0, tatAvg = 0, ctAvg = 0;
        System.out.println("5\n" +
                "1 8 0\n" +
                "2 4 1\n" +
                "3 3 2\n" +
                "4 7 3\n" +
                "5 2 3");
        System.out.println("\n\nProcessId | Burst Time | Arrival Time | Completion Time | Turn Around Time | Waiting Time");
        while(!p.isEmpty()){
            int AT = p.first().arrivalTime;
            int timeLapse = AT - timeCounter;
            if(!tree.isEmpty()){
                while(timeLapse>0){
                    Gantt.add(new GANTT(tree.first().pid, (AT-timeLapse)));
                    int BT = tree.first().burstTimeCtr;
                    if(BT <= timeLapse){
                        Process temp = tree.pollFirst();
                        temp.completionTime = (AT-timeLapse-BT);
                        temp.turnAroundTime = temp.completionTime - temp.arrivalTime;
                        temp.waitingTime = temp.turnAroundTime - temp.burstTime;

                        wtAvg = temp.waitingTime;
                        tatAvg = temp.turnAroundTime;
                        ctAvg = temp.completionTime;

                        System.out.println("\t" + temp.pid + "\t\t\t" + temp.burstTime + "\t\t\t\t" + temp.arrivalTime + "\t\t\t\t" + temp.completionTime + "\t\t\t\t" + temp.turnAroundTime + "\t\t\t\t\t" + temp.waitingTime);
                        timeLapse -= BT;

                    }
                    else{
                        tree.first().burstTimeCtr = BT - timeLapse;
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

            Gantt.add(new GANTT(temp.pid, timeCounter));

            timeCounter+=temp.burstTimeCtr;
            temp.completionTime = (timeCounter);
            temp.turnAroundTime = temp.completionTime - temp.arrivalTime;
            temp.waitingTime= temp.turnAroundTime - temp.burstTime;

            wtAvg += temp.waitingTime;
            tatAvg += temp.turnAroundTime;
            ctAvg += temp.completionTime;

            System.out.println("\t" + temp.pid + "\t\t\t" + temp.burstTime + "\t\t\t\t" + temp.arrivalTime + "\t\t\t\t" + temp.completionTime + "\t\t\t\t" + temp.turnAroundTime + "\t\t\t\t\t" + temp.waitingTime);
        }

        System.out.println("__________________________________________________________________________________________");
        System.out.println("\t\t\t\t\t\t\t\t\tAVERAGE :- " + ctAvg / n + "\t\t\t\t" + tatAvg / n + "\t\t\t\t" + wtAvg / n);

        System.out.print("\nGANTT CHART:\n");
        for(GANTT slot: Gantt){
            System.out.print(" p"+slot.pid+" ");
        }
        System.out.println("");
        for(GANTT slot: Gantt){
            System.out.print(" "+slot.time+"  ");
        }
        System.out.println(timeCounter);
    }
}
