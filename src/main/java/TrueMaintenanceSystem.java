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
             if (c.getNumUnknownLiterals()==1 && c.getNumOfLiterals()-c.getNumUnknownLiterals()==0){
                 fringe.push(c);
             }
         }
        computeLabel();
    }


    public void computeLabel(){
        while(fringe.size()>0 && conflicts.size()==0 ){
            Clause c= fringe.pop();
            propagate(c);
            updateFringeAndConflicts();
        }
    }

    private void propagate(Clause c) {
        for (Literal l : c.getLiterals()) {
            if (labels.get(l.getName()) == -1) {
                if (l.negative) {
                    labels.remove(l.getName());
                    labels.put(l.getName(), 0);
                } else {
                    labels.remove(l.getName());
                    labels.put(l.getName(), 1);
                }
            }
        }
    }

    /**
     * update fringe and conflicts after label literal.
     */
    private void updateFringeAndConflicts() {
        fringe.removeAllElements();
        for (Clause c: clauses){
            c.updateClause(labels);
            if ( c.getNumUnknownLiterals()==1 && c.getNumOfNegative()==c.getNumOfLiterals()-c.getNumUnknownLiterals()){
                fringe.push(c);
            }

            else if (c.numUnknownLiterals==0 && c.numOfNegative==c.numUnknownLiterals)
                conflicts.add(c);
        }
    }


}