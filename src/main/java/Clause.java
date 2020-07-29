import java.util.Dictionary;
import java.util.HashSet;
import java.util.List;

public class Clause {
    private List <CLiteral> CLiterals;
     int numUnknownLiterals;
     int numOfLiterals;
     int numOfNegative;

    @Override
    public String toString() {
        return "Clause{" +
                "CLiterals=" + CLiterals +
                ", numOfNegative=" + numOfNegative +
                '}';
    }

    public Clause(List<CLiteral> CLiterals) {
        this.CLiterals = CLiterals;
        findHowManyLiterals();
}

    public List<CLiteral> getCLiterals() {
        return CLiterals;
    }


    public int getNumUnknownLiterals() {
        return numUnknownLiterals;
    }

    private void findHowManyLiterals() {
        HashSet set= new HashSet();
        for (CLiteral l: CLiterals){
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
    public void remove(CLiteral l) {
        for (int i = 0; i< CLiterals.size(); i++){
            if (l.getName().equals(CLiterals.get(i).getName()))
                CLiterals.remove(i);
        }
    }

    /**
     * add literal to clause
     * @param temp
     */
    public void add(CLiteral temp) {
        CLiterals.add(temp);
    }

    /**
     * update number of UNKNOWN CLiterals and FALSE CLiterals in one clause.
     * @param labels
     */
    public void updateClause(Dictionary<String , Integer> labels) {
        numOfNegative=0;
        numUnknownLiterals=0;
        for (int i = 0; i< CLiterals.size(); i++){
            if (labels.get(CLiterals.get(i).getName())==-1){
                System.out.println("Current literal: "+ CLiterals.get(i).getName());
                numUnknownLiterals++;
            }
            else{
                // calculate the label of literal with
                int label = labels.get(CLiterals.get(i).getName());
                boolean label_bool= label > 0 ? true : false ;
                boolean value= label_bool ^ CLiterals.get(i).negative;
                if (!value)
                    numOfNegative++;
            }
        }

    }
}
