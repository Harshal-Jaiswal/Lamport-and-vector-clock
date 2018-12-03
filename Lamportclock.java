/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamportclock;

/**
 *
 * @author CES
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author SRCOEM10
 */
class Points {

    int id;
    int size;
    int toa[];
    int tob[];
    int ts[];   //time stamp
    ArrayList<ArrayList<Integer>> tsv;

    public Points(int id, int size) {
        this.id = id;
        this.size = size;
        toa = new int[size + 1];
        tob = new int[size + 1];
        ts = new int[size + 1];
        for (int i = 0; i < size + 1; i++) {
            ts[i] = i;
        }
        toa[0] = -1;
        tob[0] = -1;
        tsv = new ArrayList();

    }

    public void setab(int pt, int val1, int val2) {
        toa[pt] = val1;
        tob[pt] = val2;
    }

    public void addtsv(ArrayList al) {
        tsv.add(al);
    }

    public int geta(int pt) {
        return toa[pt];
    }

    public int getb(int pt) {
        return tob[pt];
    }
}

class Vector {

    ArrayList<Points> pt;
    Scanner sc;

    public Vector() {
        pt = new ArrayList();
        sc = new Scanner(System.in);
    }

    public void add(Points p) {
        pt.add(p);
    }

    public void addto() {
        for (int i = 0; i < pt.size(); i++) {
            for (int j = 0; j < pt.get(i).size; j++) {
                System.out.println(" (" + pt.get(i).id + "," + (j + 1) + ") :");
                int a, b;
                a = sc.nextInt();
                if (a == 0) {
                    b = 0;
                } else {
                    b = sc.nextInt();
                }
                pt.get(i).setab(j + 1, a, b);

            }
        }

        calc();
    }

    public void calc() {
        ArrayList<Integer> al = new ArrayList();

        for (int i = 0; i < pt.size(); i++) {

            for (int j = 1; j < pt.get(i).size + 1; j++) {
                al.clear();
                al.add(0);
                for (int x = 1; x < pt.size() + 1; x++) {

                    if (pt.get(i).id == x) {
                        al.add(j);
                    } else {
                        al.add(0);
                    }
                }
                pt.get(i).addtsv(new ArrayList(al));
                al.clear();
            }
        }

        System.out.println("Vector clock");
        vectcalc();
    }

    private void vectcalc() {
        int ts1, flag = 0;
        Points p, q;

        for (int x = 0; x < pt.size(); x++) {
            for (int i = 0; i < pt.size(); i++) {
                p = pt.get(i);
                for (int j = 1; j < p.size + 1; j++) {
                    if (p.toa[j] != 0) {
                        q = pt.get(p.toa[j] - 1);
                        for (int m = p.id; m < pt.size() + 1; m++) {

                            ts1 = p.tsv.get(j - 1).get(m);

                            if (q.tsv.get(p.tob[j] - 1).get(m) < ts1) {
                                for (int k = p.tob[j] - 1; k < q.size; k++) {

                                    q.tsv.get(k).remove(m);
                                    q.tsv.get(k).add(m, ts1);
                                    //ts1++;
                                }
                            }
                        }

                    }
                }

            }
        }

        for (int i = 0; i < pt.size(); i++) {
            System.out.println(" process P(" + pt.get(i).id + ") :");
            p = pt.get(i);
            for (int k = 0; k < p.size; k++) {
                System.out.print(" (");
                for (int x = 1; x < p.tsv.get(k).size(); x++) {
                    System.out.print(" " + p.tsv.get(k).get(x) + ",");
                }
                System.out.println(")");
            }
            System.out.println(" ");
        }

    }

}

class Lamp {

    ArrayList<Points> pt;
    Scanner sc;

    public Lamp() {
        pt = new ArrayList();
        sc = new Scanner(System.in);
    }

    public void addLamp(Points p) {
        pt.add(p);
    }

    public void addto() {
        for (int i = 0; i < pt.size(); i++) {
            for (int j = 0; j < pt.get(i).size; j++) {
                System.out.println(" (" + pt.get(i).id + "," + (j + 1) + ") :");
                int a, b;
                a = sc.nextInt();
                if (a == 0) {
                    b = 0;
                } else {
                    b = sc.nextInt();
                }
                pt.get(i).setab(j + 1, a, b);

            }
        }

        System.out.println("\n\n LAMPORT CLOCK");

        calcLamport();
    }

    private void calcLamport() {
        int ts1, flag = 0;
        Points p, q;
        for (int x = 0; x < pt.size(); x++) {
            for (int i = 0; i < pt.size(); i++) {
                p = pt.get(i);
                for (int j = 1; j < p.size + 1; j++) {
                    if (p.toa[j] != 0) {

                        q = pt.get(p.toa[j] - 1);
                        ts1 = p.ts[j] + 1;

                        if (q.ts[p.tob[j]] < ts1) {

                            for (int k = p.tob[j]; k < q.size + 1; k++) {

//                                if (x > 0) {
//                                    flag = 1;
//                                    break;
//                                }
                                q.ts[k] = ts1;
                                ts1++;
                            }
                        }
                    }
                }

            }
        }

        //if (flag == 1) {
        System.out.println(" Caution Voilation");
        //} else {
        for (int i = 0; i < pt.size(); i++) {
            System.out.println(" process P(" + pt.get(i).id + ") :");
            for (int k = 1; k < pt.get(i).ts.length; k++) {
                System.out.print(pt.get(i).ts[k] + ",");
            }
            System.out.println(" ");
        }
        //}

    }

}

public class Lamportclock {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);

        Lamp l = new Lamp();
        Vector v = new Vector();
        int a = 1, b;
        while (true) {
            while (true) {

                System.out.println("Enter process No. and its size or enter 0 to calculate lamport");

                b = sc.nextInt();

                if (b == 0) {
                    break;
                }

                //b = sc.nextInt();
                Points p = new Points(a++, b);
                l.addLamp(p);
                v.add(p);

            }

            //l.addto();
            v.addto();
            System.out.println("press 1 to add more process or 0 to exit");
            a = sc.nextInt();
            if (a == 0) {
                break;
            }
        }
    }

}
