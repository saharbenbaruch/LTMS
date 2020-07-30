import java.util.*;

public class TrueMaintenanceSystem {
    List<Clause> clauses;
    Stack<Clause> fringe;
    List <Clause> conflicts;
    Dictionary<String , Integer> labels;

    public TrueMaintenanceSystem() {
        clauses=new ArrayList<Clause>();
        fringe= new Stack<Clause>();
        conflicts= new ArrayList<Clause>();
        labels= new Hashtable<String, Integer>();
    }

    public ArrayList<Clause> solve() {

        // init 'labels' for example -> a = 1 (true), b=0 (false) , c=-1 (unknown)
        for (Clause c: clauses){
            for (Literal l : c.getLiterals() ){
                if (labels.get(l.getName())==null)
                    labels.put(l.getName(),-1);
            }
        }
        init();
        return (ArrayList<Clause>) conflicts;
    }

    /**
     * init by creating empty list for conflicts.
     * all clause contain only ONE unknown literal and the rest is FAlSE -> fringe
     */
    public void init(){
         for (Clause c: clauses){
             // if there is only one literal UNKNOWN and the other are NEGATIVE , add to fringe
             if (c.getNumUnknownLiterals()==1 && c.getNumOfLiterals()-c.getNumUnknownLiterals()==0){
                 fringe.push(c);
             }
         }
        computeLabel();
    }


    public void computeLabel(){
        while(fringe.size()>0 && conflicts.size()==0 ){
            Clause c= fringe.pop();
            List<String> changedLiteral=propagate(c);
            //updateFringeAndConflicts();
            updateFringeAndConflicts(changedLiteral);
        }
    }

    private List<String> propagate(Clause c) {
        List<String> changedLiteral= new ArrayList<String>();
        // -1 = UNKNOWN , 0= NEGATIVE , 1= POSITIVE
        for (Literal l : c.getLiterals()) {
            if (labels.get(l.getName()) == -1) {
                if (l.negative) {
                    labels.remove(l.getName());
                    labels.put(l.getName(), 0);
                    changedLiteral.add((l.getName()));
                }
                else {
                    labels.remove(l.getName());
                    labels.put(l.getName(), 1);
                    changedLiteral.add((l.getName()));
                }
            }
        }
        return changedLiteral;
    }

    /**
     * update fringe and conflicts after label literal.
     */
    private void updateFringeAndConflicts(List <String> changed) {
        //update fringe
        for (Clause ci: fringe) {
            for (Literal l : ci.getLiterals()) {
                if (changed.contains(l.getName())) {
                    ci.updateClause(labels);
                    if (ci.numUnknownLiterals == 0 && ci.numOfNegative == ci.getNumOfLiterals())
                        // else if (c.numUnknownLiterals==0 && c.numOfNegative==c.numUnknownLiterals)
                        conflicts.add(ci);
                }
            }
        }

        // check rest of clauses which not in fringe or conflicts.
        for (Clause c: clauses) {
            if (!fringe.contains(c) && !conflicts.contains(c)) {
                for (Literal l : c.getLiterals()) {
                    if (changed.contains(l.getName())) {
                        c.updateClause(labels);

                        if (c.getNumUnknownLiterals() == 1 && c.getNumOfNegative() == c.getNumOfLiterals() - c.getNumUnknownLiterals()) {
                            fringe.push(c);
                            break;
                        } else if (c.numUnknownLiterals == 0 && c.numOfNegative == c.getNumOfLiterals()) {
                            // else if (c.numUnknownLiterals==0 && c.numOfNegative==c.numUnknownLiterals)
                            conflicts.add(c);
                            break;
                        }
                    }
                }
            }
        }

    }


}