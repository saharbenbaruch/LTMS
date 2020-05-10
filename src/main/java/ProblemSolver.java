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
        for (String obs :observation){
            clauses.add(parse(obs));
        }
    }
    /**
     * parse strings to clauses
     */
    private Clause parse(String desc) {
        //remove spaces.
        List<Literal> literals=new ArrayList<Literal>();
        desc=desc.replaceAll("\\s+","");

        String [] current=desc.split("[|]");
        for (int i=0; i<current.length;i++){
            Literal l;
            if (current[i].contains("~")){
                 l= new Literal(current[i].replace("~",""),true);
            }
            else{
                 l= new Literal(current[i].replace("~",""),false);
            }
            literals.add(l);
        }

        Clause c= new Clause(literals);
        return c;
    }
}
