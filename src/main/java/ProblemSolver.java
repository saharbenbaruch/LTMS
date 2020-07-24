import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

enum Format { STRINGS, ELECTRICALCIRCUITS }
public class ProblemSolver {
    private TrueMaintenanceSystem ltms=new TrueMaintenanceSystem();
    private List<String> description;
    private List<String> observation;
    private List <Clause> clauses=new ArrayList<Clause>();
    private Format format;

    public Format getFormat() {
        return format;
    }

    public ProblemSolver(Format problemFormat, List<String> description, List<String> observation) {
        this.description = description;
        this.observation= observation;
        this.format = problemFormat;
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

        if(getFormat()== Format.ELECTRICALCIRCUITS){
            /**
             *      get paths of description and observations
              */

            Scanner scanner = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Enter path for circuits description: ");

            String circuitDescriptionPath = scanner.nextLine();  // Read user input
            System.out.println("Path for circuit description is: " + circuitDescriptionPath);  // Output user input

            System.out.println("Enter path for observations: ");
            String observationsPath= scanner.nextLine();
            System.out.println("Path for observations is: " + observationsPath);  // Output user input

            String descContent= readFile(circuitDescriptionPath);
            String obsContent= readFile(observationsPath);

            CircuitParserFormat parser =new CircuitParserFormat();
            description =parser.parseElectricalCircuitDescription(descContent);
            observation= parser.parseElectricalCircuitObservation(obsContent);

        }

        for (String desc : description) {
            clauses.add(parseStringsFormat(desc));
        }
        for (String obs : observation) {
            clauses.add(parseStringsFormat(obs));
        }
    }

    /**
     *
     * @param path path of file
     * @return content of file as string
     */
    private String readFile(String path){
        String content="";
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                content+=data;
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return content;
    }
    /**
     * parse strings to clause
     */
    private Clause parseStringsFormat(String desc) {
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
