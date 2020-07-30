package BooleanSystem;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

public class Calculator {
    final static java.lang.String LOGGING = "INFO";

    public static void main(java.lang.String[] args) {

        System.out.print("Syntax : ( , ) , & , | , ~ , -> , <->\n\n>>  ");
        java.lang.String data = new Scanner(System.in).nextLine();
        ArrayList<java.lang.String> Literals = new ArrayList<java.lang.String>();
        StringTokenizer mytoken = new StringTokenizer(data, " )(<>-~&|");
        while (mytoken.hasMoreTokens()) {
            java.lang.String temp = mytoken.nextToken();
            // System.out.println(temp);
            if (!Literals.contains(temp)) {
                Literals.add(temp);
            }
        }
        int[][] literalsTF = new int[(int) Math.pow(2, Literals.size())][Literals.size()];
        for (int i = 0; i < (int) Math.pow(2, Literals.size()); i++) {
            java.lang.String temp = Integer.toBinaryString(i);
            while (temp.length() < Literals.size())
                temp = "0" + temp;
            for (int j = 0; j < Literals.size(); j++) {
                literalsTF[i][j] = temp.toCharArray()[j] - 48;
            }
        }
        // for (int i = 0; i < (int) Math.pow(2, Literals.size()); i++) {
        //
        // for (int j = 0; j < Literals.size(); j++) {
        // System.out.print(literalsTF[i][j] + " ");
        // }
        // System.out.println();
        // }
        ArrayList<String> Ldata = new ArrayList<String>();
        for (int i = 0; i < Literals.size(); i++) {
            int[] tempTF = new int[(int) Math.pow(2, Literals.size())];
            for (int j = 0; j < (int) Math.pow(2, Literals.size()); j++) {
                tempTF[j] = literalsTF[j][i];
            }
            Ldata.add(new String(Literals.get(i), tempTF));
        }
        for (String literal : Ldata) {
            if (LOGGING.equals("INFO"))
                literal.printMe();
        }
        Stack<String> stack = new Stack<String>();
        char[] Cdata = data.toCharArray();
        // data = "(" + data + ")";
        data = data.replace("(", " ( ");
        data = data.replace(")", " )");
        data = data.replace("~", " ~ ");
        data = data.replace("&", " & ");
        data = data.replace("|", " | ");
        data = data.replace("<->", " <-> ");
        if (data.contains("->")) {
            int index = data.indexOf("->");
            if (data.charAt(index - 1) != '<')
                data = data.replaceFirst("->", " -> ");
        }
        mytoken = new StringTokenizer(data, " ");
        while (mytoken.hasMoreTokens()) {
            java.lang.String temp = mytoken.nextToken();
            if (temp.equals("&")) {
                stack.push(new String("&", null));
            } else if (temp.equals("|")) {
                stack.push(new String("|", null));
            } else if (temp.equals("->")) {
                stack.push(new String("->", null));
            } else if (temp.equals("<->")) {
                stack.push(new String("<->", null));
            } else if (temp.equals("~")) {
                stack.push(new String("~", null));

            } else if (temp.equals(")")) {
                String b = stack.pop();
                String func = stack.pop();
                if (func.name.equals("~")) {
                    String ans = String.opHandler(b, null, func);
                    ans.printMe();
                    stack.push(ans);
                } else {
                    String a = stack.pop();
                    String ans = String.opHandler(a, b, func);
                    ans.printMe();
                    stack.push(ans);
                }
            } else {
                for (String literal : Ldata) {
                    if (temp.equals(literal.name)) {
                        stack.push(literal);
                        break;
                    }
                }
            }
        }
        String FinalAnswer = stack.pop();

        System.out.println("\n>>Truth Table Completed");
        if (LOGGING.equals("INFO")) {
            System.out.println("\nCNF : " + CNF(Ldata, FinalAnswer) + "\n");
            System.out.println("\n>>Done!");
        }

    }

    public java.lang.String getCNF(java.lang.String formula) {
        java.lang.String data = formula;
        ArrayList<java.lang.String> Literals = new ArrayList<java.lang.String>();
        StringTokenizer mytoken = new StringTokenizer(data, " )(<>-~&|");
        while (mytoken.hasMoreTokens()) {
            java.lang.String temp = mytoken.nextToken();
            // System.out.println(temp);
            if (!Literals.contains(temp)) {
                Literals.add(temp);
            }
        }
        int[][] literalsTF = new int[(int) Math.pow(2, Literals.size())][Literals.size()];
        for (int i = 0; i < (int) Math.pow(2, Literals.size()); i++) {
            java.lang.String temp = Integer.toBinaryString(i);
            while (temp.length() < Literals.size())
                temp = "0" + temp;
            for (int j = 0; j < Literals.size(); j++) {
                literalsTF[i][j] = temp.toCharArray()[j] - 48;
            }
        }

        ArrayList<String> Ldata = new ArrayList<String>();
        for (int i = 0; i < Literals.size(); i++) {
            int[] tempTF = new int[(int) Math.pow(2, Literals.size())];
            for (int j = 0; j < (int) Math.pow(2, Literals.size()); j++) {
                tempTF[j] = literalsTF[j][i];
            }
            Ldata.add(new String(Literals.get(i), tempTF));
        }
        for (String literal : Ldata) {
            if (LOGGING.equals("INFO"))
                literal.printMe();
        }
        Stack<String> stack = new Stack<String>();
        char[] Cdata = data.toCharArray();
        // data = "(" + data + ")";
        data = data.replace("(", " ( ");
        data = data.replace(")", " )");
        data = data.replace("~", " ~ ");
        data = data.replace("&", " & ");
        data = data.replace("|", " | ");
        data = data.replace("<->", " <-> ");
        if (data.contains("->")) {
            int index = data.indexOf("->");
            if (data.charAt(index - 1) != '<')
                data = data.replaceFirst("->", " -> ");
        }
        mytoken = new StringTokenizer(data, " ");
        while (mytoken.hasMoreTokens()) {
            java.lang.String temp = mytoken.nextToken();
            if (temp.equals("&")) {
                stack.push(new String("&", null));
            } else if (temp.equals("|")) {
                stack.push(new String("|", null));
            } else if (temp.equals("->")) {
                stack.push(new String("->", null));
            } else if (temp.equals("<->")) {
                stack.push(new String("<->", null));
            } else if (temp.equals("~")) {
                stack.push(new String("~", null));

            } else if (temp.equals(")")) {
                String b = stack.pop();
                String func = stack.pop();
                if (func.name.equals("~")) {
                    String ans = String.opHandler(b, null, func);
                    ans.printMe();
                    stack.push(ans);
                } else {
                    String a = stack.pop();
                    String ans = String.opHandler(a, b, func);
                    ans.printMe();
                    stack.push(ans);
                }
            } else {
                for (String literal : Ldata) {
                    if (temp.equals(literal.name)) {
                        stack.push(literal);
                        break;
                    }
                }
            }
        }
        String FinalAnswer = stack.pop();
        System.out.println("\n>>Truth Table Completed");
        if (LOGGING.equals("INFO")) {
            System.out.println("\nCNF : " + CNF(Ldata, FinalAnswer) + "\n");
            System.out.println("\n>>Done!");
        }
        return CNF(Ldata, FinalAnswer);
    }


    public static java.lang.String CNF(ArrayList<String> literalData, String FinalAnswer) {
        java.lang.String result = " ";
        for (int i = 0; i < FinalAnswer.myTF.length; i++) {
            boolean flag = false;
            if (FinalAnswer.myTF[i] == 0) {
                java.lang.String temp = "(";
                for (String literal : literalData) {
                    if (flag) {
                        temp += " |";
                    } else {
                        flag = true;
                    }
                    if (literal.myTF[i] == 1)
                        temp = temp + " ~" + literal.name;
                    else
                        temp = temp + " " + literal.name;
                }
                temp = temp + " )";
                result = result + " & " + temp;
            }
        }
        result = result.replace("  & (", " (");
        if (result.equals(" "))
            return "( " + literalData.get(0).name + " | ~" + literalData.get(0).name + " )" + "  *all true case";
        return result;
    }
}
