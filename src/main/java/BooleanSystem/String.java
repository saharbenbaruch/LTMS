package BooleanSystem;

public class String {
    public String(java.lang.String name, int[] myTF) {
        this.name = name;
        this.myTF = myTF;
    }

    java.lang.String name;
    int[] myTF;

    void printMe() {
        System.out.println();
        System.out.print(name + " : ");
        for (int i : myTF) {
            if (i == 0)
                System.out.print("F ");
            else
                System.out.print("T ");
        }
        System.out.println();
    }

    public static String opHandler(String a, String b, String func) {
        if (func.name.equals("~"))
            return _not(a);
        if (func.name.equals("&"))
            return _and(a, b);
        if (func.name.equals("|"))
            return _or(a, b);
        if (func.name.equals("->"))
            return _eq(a, b);
        if (func.name.equals("<->"))
            return _deq(a, b);
        return null;
    }

    static String _and(String a, String b) {
        int[] temp = new int[a.myTF.length];
        for (int i = 0; i < a.myTF.length; i++) {
            temp[i] = a.myTF[i] * b.myTF[i];
        }
        return new String(a.name + "&" + b.name, temp);
    }

    static String _or(String a, String b) {
        int[] temp = new int[a.myTF.length];
        for (int i = 0; i < a.myTF.length; i++) {
            int t = a.myTF[i] + b.myTF[i];
            if (t == 2)
                t = 1;
            temp[i] = t;
        }
        return new String(a.name + "|" + b.name, temp);
    }

    static String _eq(String a, String b) {
        int[] temp = new int[a.myTF.length];
        for (int i = 0; i < a.myTF.length; i++) {
            int t1 = a.myTF[i];
            int t2 = b.myTF[i];
            int t = -1;
            if (t1 == 1 && t2 == 0)
                t = 0;
            else if (t1 == 1 && t2 == 1)
                t = 1;
            else if (t1 == 0 && t2 == 1)
                t = 1;
            else if (t1 == 0 && t2 == 0)
                t = 1;
            temp[i] = t;
        }
        return new String(a.name + "->" + b.name, temp);
    }

    static String _deq(String a, String b) {
        int[] temp = new int[a.myTF.length];
        for (int i = 0; i < a.myTF.length; i++) {
            int t1 = a.myTF[i];
            int t2 = b.myTF[i];
            int t = -1;
            if (t1 == 1 && t2 == 0)
                t = 0;
            else if (t1 == 1 && t2 == 1)
                t = 1;
            else if (t1 == 0 && t2 == 1)
                t = 0;
            else if (t1 == 0 && t2 == 0)
                t = 1;
            temp[i] = t;
        }
        return new String(a.name + "<->" + b.name, temp);
    }

    static String _not(String a) {
        int[] temp = new int[a.myTF.length];
        for (int i = 0; i < a.myTF.length; i++) {
            if (a.myTF[i] == 1)
                temp[i] = 0;
            else
                temp[i] = 1;
        }
        return new String("~" + a.name, temp);
    }
}
