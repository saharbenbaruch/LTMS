import BooleanSystem.BooleanSystemParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String [] args){
        List<String> description= new ArrayList<String>();
        description.add(" ~ nci | ~ a | nco");
        description.add( "~ia | nco");
        description.add( "~ok | a");
        description.add( "~rf | ia");
        description.add( "~uf | ia");
        description.add( "~ok | ~rf");
        description.add("~ok | ~uf");
        description.add("~rf | ~uf");
        description.add( "~a | ~ia");

        List<String> observation= new ArrayList<String>();
        observation.add( "rf");
        observation.add( "ok");


        ProblemSolver solver= new ProblemSolver(description, observation);
        ArrayList<Clause> conflicts =solver.solve();
        System.out.println(conflicts);


        try {
            BufferedReader brSystem = new BufferedReader(new FileReader("C:\\Users\\Nadav Bar David\\IdeaProjects\\LTMS\\src\\main\\resources\\examples\\Data_Systems\\c17.sys"));
            BufferedReader brObs = new BufferedReader(new FileReader("C:\\Users\\Nadav Bar David\\IdeaProjects\\LTMS\\src\\main\\resources\\examples\\Data_Systems_Obs\\c17_iscas85.obs"));
            BooleanSystemParser bsp = new BooleanSystemParser(brSystem, brObs);
            ArrayList<String> sysDesc = bsp.getSystemDescription();
            ArrayList<String> sysObs = bsp.getSystemObservation();
            solver = new ProblemSolver(sysDesc, sysObs);
            conflicts = solver.solve();

            for (int i = 0; i < conflicts.size() ; i++) {
                System.out.println(conflicts.get(i).toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }




    }

}
