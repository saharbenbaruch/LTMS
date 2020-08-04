import BooleanSystem.BooleanSystemParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static BufferedReader systemDescriptionBR = null;
    public static BufferedReader systemObservationBR = null;

    public static void main(String[] args) {
        System.out.println("--- Welcome to the LTMS system by Sahar & Nadav ---");
        System.out.println("Please choose from the following options:");
        System.out.println("1. Lecture running basic example");
        System.out.println("2. Boolean system running example (for system #74181) with default parameters (1 conflict, no time limit)");
        System.out.println("3. Boolean system custom running");
        System.out.println("4. Boolean system Testing");
        String option = new Scanner(System.in).nextLine();
        while (Integer.parseInt(option) != 1 && Integer.parseInt(option) != 2 && Integer.parseInt(option) != 3 && Integer.parseInt(option) != 4) {
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
        } else if (option.equals("2")) {
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


        } else if (option.equals("3")) { // Boolean system running
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
            if (timeLimitInt != 0)
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
        } else if (option.equals("4")) { // Boolean system running
            File systemsDir = new File("src/main/resources/examples/Testing/Data_Systems");
            File obsDir = new File("src/main/resources/examples/Testing/Data_Systems_Obs");
            File[] systemsListing = systemsDir.listFiles();
            if (systemsListing != null) {
                for (File system : systemsListing) {
                    String nameToSearch = system.getName().substring(0, system.getName().indexOf("System"));
                    File[] obsListing = obsDir.listFiles();
                    if (obsListing != null) {
                        for (File obs : obsListing) {
                            if (obs.getName().contains(nameToSearch)) {
                                String obsFileName = obs.getName();
                                Matcher matcher = Pattern.compile("\\d+").matcher(obsFileName.substring(obsFileName.indexOf("Obs")));
                                matcher.find();
                                int realNumOfConflicts = Integer.valueOf(matcher.group());

                                matcher = Pattern.compile("\\d+").matcher(obsFileName.substring(obsFileName.indexOf("Max")));
                                matcher.find();
                                int maxNumParameter = Integer.valueOf(matcher.group());

                                System.out.println("Running LTMS for system file name: " + system.getName());
                                System.out.println("With obs file name: " + obsFileName);
                                System.out.println("Real num of conflicts: " + realNumOfConflicts);
                                System.out.println("Parameter of max conflicts: " + maxNumParameter);
                                if (realNumOfConflicts < maxNumParameter ){
                                    System.out.println("max num parameter is larger than the num of conflicts");
                                    System.out.println("Expecting for " + realNumOfConflicts + " conflicts to be returned");
                                }
                                else{
                                    System.out.println("max num parameter is smaller or equal than the num of conflicts");
                                    System.out.println("Expecting for " + maxNumParameter + " conflicts to be returned");
                                }
                                System.out.println("--------------------------------------------------------");
                                System.out.println("System description:");
                                BufferedReader br = null;
                                try {
                                    br = new BufferedReader(new FileReader(system.getPath()));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                String line = null;
                                while (true) {
                                    try {
                                        assert br != null;
                                        if ((line = br.readLine()) == null) break;
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println(line);
                                }
                                System.out.println("--------------------------------------------------------");
                                System.out.println("System observation:");
                                br = null;
                                try {
                                    br = new BufferedReader(new FileReader(obs.getPath()));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                line = null;
                                while (true) {
                                    try {
                                        assert br != null;
                                        if ((line = br.readLine()) == null) break;
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println(line);
                                }
                                System.out.println("--------------------------------------------------------");
                                System.out.println();

//                                System.out.println(system.);
                                setBufferedReader("description", system.getPath());
                                setBufferedReader("observation", obs.getPath());
                                BooleanSystemParser bsp = new BooleanSystemParser(systemDescriptionBR, systemObservationBR);
                                ArrayList<String> sysDesc = bsp.getSystemDescription();
                                ArrayList<String> sysObs = bsp.getSystemObservation();
                                ProblemSolver solver = new ProblemSolver(sysDesc, sysObs, 0, maxNumParameter);
                                ArrayList<Clause> conflicts = solver.solve();
                                System.out.println("**************CONFLICTS:************************");
                                for (int i = 0; i < conflicts.size(); i++) {
                                    System.out.println(conflicts.get(i).toString());
                                }
                            }
                            System.out.println("--------------------------------------------------------");
                            System.out.println("--------------------------------------------------------");
                            System.out.println();
                        }
                    }
                }
            } else {
                // Handle the case where dir is not really a directory.
                // Checking dir.isDirectory() above would not be sufficient
                // to avoid race conditions with another process that deletes
                // directories.
            }


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
    }


    private static void setBufferedReader(String type, String filePath) {
        try {
            if (type.equals("description")) {
                systemDescriptionBR = new BufferedReader(new FileReader(filePath));
            } else if (type.equals("observation")) {
                systemObservationBR = new BufferedReader(new FileReader(filePath), 16 * 1024);
            }

        } catch (FileNotFoundException ex) {

        }
    }

}
