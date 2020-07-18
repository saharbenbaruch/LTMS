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
//        description.add( "rf");
//        description.add("ok");

        List<String> observation= new ArrayList<String>();
        observation.add( "rf");
        observation.add( "ok");


        ProblemSolver solver= new ProblemSolver(description, observation);
        ArrayList<Clause> conflicts =solver.solve();


    }

}
