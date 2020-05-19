import java.util.Dictionary;
import java.util.HashSet;
import java.util.List;

public class Clause {
    private List <Literal> literals;
     int numUnknownLiterals;
     int numOfLiterals;
     int numOfNegative;

    public Clause(List<Literal> literals) {
        this.literals = literals;
        findHowManyLiterals();
}

    public List<Literal> getLiterals() {
        return literals;
    }


    public int getNumUnknownLiterals() {
        return numUnknownLiterals;
    }

    private void findHowManyLiterals() {
        HashSet set= new HashSet();
        for (Literal l:literals ){
            if(!set.contains(l.name))
                set.add(l.name);
        }
        numOfLiterals=set.size();
        numUnknownLiterals=set.size();
    }


    public int getNumOfNegative() {
        return numOfNegative;
    }

    public int getNumOfLiterals() {
        return numOfLiterals;
    }

    /**
     * remove literal from clause
     * @param l
     */
    public void remove(Literal l) {
        for (int i=0; i<literals.size();i++){
            if (l.getName().equals(literals.get(i).getName()))
                literals.remove(i);
        }
    }

    /**
     * add literal to clause
     * @param temp
     */
    public void add(Literal temp) {
        literals.add(temp);
    }

    /**
     * update number of UNKNOWN literals and FALSE literals in one clause.
     * @param labels
     */
    public void updateClause(Dictionary<String , Integer> labels) {
        numOfNegative=0;
        numUnknownLiterals=0;
        for (int i=0 ; i<literals.size();i++){
            if (labels.get(literals.get(i).getName())==-1){
                System.out.println("Current literal: "+literals.get(i).getName());
                numUnknownLiterals++;
            }
            else{
                // calculate the label of literal with
                int label = labels.get(literals.get(i).getName());
                boolean label_bool= label > 0 ? true : false ;
                boolean value= label_bool ^ literals.get(i).negative;
                if (!value)
                    numOfNegative++;
            }
        }

    }
}
