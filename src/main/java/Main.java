import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String [] args){

        // TEST 1 - STRINGS format
        List<String> description= new ArrayList<String>();
        List<String> observation= new ArrayList<String>();
//        description.add(" ~ nci | ~ a | nco");
//        description.add( "~ia | nco");
//        description.add( "~ok | a");
//        description.add( "~rf | ia");
//        description.add( "~uf | ia");
//        description.add( "~ok | ~rf");
//        description.add("~ok | ~uf");
//        description.add("~rf | ~uf");
//        description.add( "~a | ~ia");
//

//        observation.add( "rf");
//        observation.add( "ok");
     //   ProblemSolver solver= new ProblemSolver(Format.STRINGS,description, observation);
       // ArrayList<Clause> conflicts =solver.solve();
        ///**********************************************

        //TEST 2 - elctrical ciruits




        ProblemSolver solver= new ProblemSolver(Format.ELECTRICALCIRCUITS,description, observation);
        ArrayList<Clause> conflicts =solver.solve();


    }

}
