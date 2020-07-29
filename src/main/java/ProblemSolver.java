import java.util.ArrayList;
import java.util.List;

public class ProblemSolver {
    private TrueMaintenanceSystem ltms=new TrueMaintenanceSystem();
    private List<String> description;
    private List<String> observation;
    private List <Clause> clauses=new ArrayList<Clause>();

    public ProblemSolver(List<String> description, List<String> observation) {
        this.description = description;
        this.observation= observation;
    }

    /**
     * solve by returning conflict in system if exists
     */
    public ArrayList<Clause> solve() {
        parseClauses();
        ltms.clauses=clauses;
        return ltms.solve();
    }

    /**
     * parse description and observation from String (our input) -> clauses
     */
    private void parseClauses() {
        for (String desc :description){
            clauses.add(parse(desc));
        }
        int i = 0;
        for (String obs :observation){
            if (i % 500 == 0)
                System.out.println("Parsing obs number " + i + " out of " + observation.size());
            clauses.add(parse(obs));
            i += 1;
        }
    }
    /**
     * parse strings to clauses
     */
    private Clause parse(String desc) {
        //remove spaces.
        List<CLiteral> CLiterals =new ArrayList<CLiteral>();
        desc=desc.replaceAll("\\s+","");

        String [] current=desc.split("[|]");
        for (int i=0; i<current.length;i++){
            CLiteral l;
            if (current[i].contains("~")){
                 l= new CLiteral(current[i].replace("~",""),true);
            }
            else{
                 l= new CLiteral(current[i].replace("~",""),false);
            }
            CLiterals.add(l);
        }

        Clause c= new Clause(CLiterals);
        return c;
    }
}
