import java.io.BufferedWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ProblemSolver {
    private static final String CONSOLE_LOGGING_LEVEL = "DEBUG";
    private final int MAX_RUNNING_DURATION_IN_MINUTES = 120;
    private final double CLAUSES_PARSING_INDICATION_INTERVALS = 0.05; // 0.05 -> 5%
    private static BufferedWriter loggingBW;

    private TrueMaintenanceSystem ltms = new TrueMaintenanceSystem();
    private List<String> description;
    private List<String> observation;
    private List<Clause> clauses = new ArrayList<Clause>();

    protected int maxConflictSize;
    protected long endTime;

    public ProblemSolver(List<String> description, List<String> observation, int timeLimitInt, int conflictsSizeInt) {
        this.description = description;
        this.observation = observation;
        if (timeLimitInt != -1) // set endTime based on user input
            this.endTime = System.currentTimeMillis() + timeLimitInt * 1000;
        else // set endTime based on 'MAX_RUNNING_DURATION_IN_MINUTES'
            this.endTime = System.currentTimeMillis() + MAX_RUNNING_DURATION_IN_MINUTES * 60000;
        maxConflictSize = conflictsSizeInt;
    }

    public static String getConsoleLoggingLevel() {
        return CONSOLE_LOGGING_LEVEL;
    }


    /**
     * solve by returning conflict in system if exists
     */
    public ArrayList<Clause> solve() {
        parseClauses();
        ltms.clauses = clauses;
        ltms.maxConflicts=this.maxConflictSize;
        return ltms.solve();
    }

    /**
     * parse description and observation from String (our input) -> clauses
     */
    private void parseClauses() {
        String progressIndication = "Progress: ";
        int progressPercents = 0;
        double nextPercentIndication = CLAUSES_PARSING_INDICATION_INTERVALS;

        System.out.println("Starting to parse system description clauses");
        System.out.println(progressIndication);
        System.out.print("0% ");
        int i;
        for (i = 0; i < description.size(); i++) {
            if (System.currentTimeMillis() > endTime) {
                System.out.println("Parsing progress been interrupted by TIMEOUT");
                break;
            }
            clauses.add(parse(description.get(i)));
            Double progress = (double) i / (double) description.size();
            if (round(progress, 2) == nextPercentIndication) {
                nextPercentIndication += CLAUSES_PARSING_INDICATION_INTERVALS;
                progressPercents += CLAUSES_PARSING_INDICATION_INTERVALS * 100;
                System.out.print("***** " + progressPercents + "% ");
            }
        }
        if (i == description.size()) {
            while (progressPercents <= 95) {
                progressPercents += 5;
                System.out.print("***** " + progressPercents + "% ");
            }
            System.out.println();
            System.out.println("Finished to parse system description clauses");
            System.out.println();
        }


        progressPercents = 0;
        nextPercentIndication = CLAUSES_PARSING_INDICATION_INTERVALS;
        if (i == description.size()) { // Description parsing completed
            System.out.println("Starting to parse system observations clauses");
            System.out.println(progressIndication);
            System.out.print("0% ");
        }
        for (i = 0; i < observation.size(); i++) {
            if (System.currentTimeMillis() > endTime) {
                System.out.println("Parsing progress been interrupted by TIMEOUT");
                break;
            }
            clauses.add(parse(observation.get(i)));
            if (round(i / observation.size(), 2) == nextPercentIndication) {
                nextPercentIndication += CLAUSES_PARSING_INDICATION_INTERVALS;
                progressPercents += CLAUSES_PARSING_INDICATION_INTERVALS * 100;
                System.out.print("***** " + progressPercents + "% ");
            }
        }
        if (i == observation.size()) {
            while (progressPercents <= 95) {
                progressPercents += 5;
                System.out.print("***** " + progressPercents + "% ");
            }
            System.out.println();
            System.out.println("Finished to parse system observations clauses");
            System.out.println();
        }
    }

    /**
     * parse strings to clauses
     */
    private Clause parse(String desc) {
        //remove spaces.
        List<CLiteral> CLiterals = new ArrayList<CLiteral>();
        desc = desc.replaceAll("\\s+", "");

        String[] current = desc.split("[|]");
        for (int i = 0; i < current.length; i++) {
            CLiteral l;
            if (current[i].contains("~")) {
                l = new CLiteral(current[i].replace("~", ""), true);
            } else {
                l = new CLiteral(current[i].replace("~", ""), false);
            }
            CLiterals.add(l);
        }

        Clause c = new Clause(CLiterals);
        return c;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
