import BooleanSystem.BooleanSystemParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static BufferedReader systemDescriptionBR = null;
    public static BufferedReader systemObservationBR = null;

    public static void main(String[] args) {
        System.out.println("--- Welcome to the LTMS system by Sahar & Nadav ---");
        System.out.println("Please choose from the following options:");
        System.out.println("1. Lecture running basic example");
        System.out.println("2. Boolean system running example (for system #74181) with default parameters (1 conflict, no time limit)");
        System.out.println("3. Boolean system custom running");
        String option = new Scanner(System.in).nextLine();
        while (Integer.parseInt(option) != 1 && Integer.parseInt(option) != 2 && Integer.parseInt(option) != 3) {
            System.out.println("Illegal input, please try again");
            option = new Scanner(System.in).nextLine();
        }

        if (option.equals("1")) { // Lecture example
            List<String> description = new ArrayList<String>();
            description.add(" ~ nci | ~ a | nco");
            description.add("~ia | nco");
            description.add("~ok | a");
            description.add("~rf | ia");
            description.add("~uf | ia");
            description.add("~ok | ~rf");
            description.add("~ok | ~uf");
            description.add("~rf | ~uf");
            description.add("~a | ~ia");

            List<String> observation = new ArrayList<String>();
            observation.add("rf");
            observation.add("ok");

            ProblemSolver solver = new ProblemSolver(description, observation, -1, 1);
            ArrayList<Clause> conflicts = solver.solve();
            System.out.println("**************CONFLICTS:************************");
            System.out.println(conflicts);
        }
        else if (option.equals("2")){
            setBufferedReader("description", "src/main/resources/examples/Data_Systems/74181.sys");
            setBufferedReader("observation", "src/main/resources/examples/Data_Systems_Obs/74181_iscas85.obs");
            BooleanSystemParser bsp = new BooleanSystemParser(systemDescriptionBR, systemObservationBR);
            ArrayList<String> sysDesc = bsp.getSystemDescription();
            ArrayList<String> sysObs = bsp.getSystemObservation();
            ProblemSolver solver = new ProblemSolver(sysDesc, sysObs, -1, 1);
            ArrayList<Clause> conflicts = solver.solve();
            System.out.println("**************CONFLICTS:************************");
            for (int i = 0; i < conflicts.size(); i++) {
                System.out.println(conflicts.get(i).toString());
            }



        }
        else if (option.equals("3")){ // Boolean system running
            System.out.println("--- Boolean system ---");

            // Time limit
            System.out.println("Please select a time limit in seconds, if you do not want to limit the time, please enter '0'");
            // TBD input validation

            String timeLimitStr = new Scanner(System.in).nextLine();
            while (!timeLimitStr.matches("\\d+")) {
                System.out.println("Please type valid number.");
                timeLimitStr = new Scanner(System.in).nextLine();
            }
            int timeLimitInt = Integer.parseInt(timeLimitStr);
            if (timeLimitInt == 0)
                System.out.println("The duration of the run will be limited to " + timeLimitInt + " seconds");
            else
                System.out.println("The duration of the run will be unlimited");
            System.out.println("------------------------------------------------");


            // Conflicts limit
            System.out.println("Please select max number of conflicts (LTMS system default is 1)");
            // TBD input validation
            String conflictsSizeStr = new Scanner(System.in).nextLine();
            while (!conflictsSizeStr.matches("\\d+")) {
                System.out.println("Please type valid number.");
                conflictsSizeStr = new Scanner(System.in).nextLine();
            }
            int conflictsSizeInt = Integer.parseInt(conflictsSizeStr);
            System.out.println("The run will end when " + conflictsSizeInt + " conflicts or less will be found");
            System.out.println("------------------------------------------------");


            // System description file path
            System.out.println("Please enter full path to the system DESCRIPTION file");
            String systemDescriptionFilePath = new Scanner(System.in).nextLine();
            while (systemDescriptionBR == null) {
                setBufferedReader("description", systemDescriptionFilePath);
                if (systemDescriptionBR == null) {
                    System.out.println("Illegal file path, please try again");
                    systemDescriptionFilePath = new Scanner(System.in).nextLine();
                }
            }
            System.out.println("Loading system description file been successfully");
            System.out.println("------------------------------------------------");

            // System observation file path
            System.out.println("Please enter full path to the system OBSERVATIONS file");
            String systemObservationFilePath = new Scanner(System.in).nextLine();
            while (systemObservationBR == null) {
                setBufferedReader("observation", systemObservationFilePath);
                if (systemObservationBR == null) {
                    System.out.println("Illegal file path, please try again");
                    systemObservationFilePath = new Scanner(System.in).nextLine();
                }
            }
            System.out.println("Loading system observation file been successfully");
            System.out.println("------------------------------------------------");

            System.out.println("Press enter to start the LTMS flow");
            String input = new Scanner(System.in).nextLine();
            if (input != null) {
                BooleanSystemParser bsp = new BooleanSystemParser(systemDescriptionBR, systemObservationBR);
                ArrayList<String> sysDesc = bsp.getSystemDescription();
                ArrayList<String> sysObs = bsp.getSystemObservation();
                ProblemSolver solver = new ProblemSolver(sysDesc, sysObs, timeLimitInt, conflictsSizeInt);
                ArrayList<Clause> conflicts = solver.solve();
                System.out.println("**************CONFLICTS:************************");
                for (int i = 0; i < conflicts.size(); i++) {
                    System.out.println(conflicts.get(i).toString());
                }
            }
        }
    }


    private static void setBufferedReader(String type, String filePath) {
        try {
            if (type.equals("description")) {
                systemDescriptionBR = new BufferedReader(new FileReader(filePath));
            } else if (type.equals("observation")) {
                systemObservationBR = new BufferedReader(new FileReader(filePath),16*1024);
            }

        } catch (FileNotFoundException ex) {

        }
    }

}
