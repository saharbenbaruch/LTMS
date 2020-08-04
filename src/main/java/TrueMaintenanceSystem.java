import java.util.*;

public class TrueMaintenanceSystem {
    public int maxConflicts=1;
    List<Clause> clauses;
    Stack<Clause> fringe;
    List<Clause> conflicts;
    Dictionary<String, Integer> labels;

    public TrueMaintenanceSystem() {
        clauses = new ArrayList<Clause>();
        fringe = new Stack<Clause>();
        conflicts = new ArrayList<Clause>();
        labels = new Hashtable<String, Integer>();
    }

    public ArrayList<Clause> solve() {

        // init 'labels' for example -> a = 1 (true), b=0 (false) , c=-1 (unknown)
        for (Clause c : clauses) {
            for (CLiteral l : c.getCLiterals()) {
                if (labels.get(l.getName()) == null)
                    labels.put(l.getName(), -1);
            }
        }
        init();

    return (ArrayList<Clause>) conflicts;
    }

    /**
     * init by creating empty list for conflicts.
     * all clause contain only ONE unknown literal and the rest is FAlSE -> fringe
     */
    public void init() {
        for (Clause c : clauses) {
            // if there is only one literal UNKNOWN and the other are NEGATIVE , add to fringe
            if (c.getNumUnknownLiterals() == 1 && c.getNumOfLiterals() - c.getNumUnknownLiterals() == c.getNumOfNegative()) {
                fringe.push(c);
            }
        }
        computeLabel();
    }


    public void computeLabel() {
        while (fringe.size() > 0 && conflicts.size() < maxConflicts) {
            Clause c = fringe.pop();
            List<String> changedLiteral = propagate(c);
            //updateFringeAndConflicts();
            updateFringeAndConflicts(changedLiteral);
        }
    }

    private List<String> propagate(Clause c) {
        List<String> changedLiteral = new ArrayList<String>();
        // -1 = UNKNOWN , 0= NEGATIVE , 1= POSITIVE
        for (CLiteral l : c.getCLiterals()) {
            if (labels.get(l.getName()) == -1) {
                if (l.negative) {
                    labels.remove(l.getName());
                    labels.put(l.getName(), 0);
                    changedLiteral.add((l.getName()));
                } else {
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
    private void updateFringeAndConflicts(List<String> changed) {
        //update fringe
        for (Clause ci : fringe) {
            for (CLiteral l : ci.getCLiterals()) {
                if (changed.contains(l.getName())) {
                    ci.updateClause(labels);
                    if ( ci.numOfNegative>0)
                        // else if (c.numUnknownLiterals==0 && c.numOfNegative==c.numUnknownLiterals)
                        if (!conflicts.contains(ci)) {
                            if (conflicts.size()+1<=maxConflicts)
                                conflicts.add(ci);

                            break;
                        }
                }
            }
        }

        // check rest of clauses which not in fringe or conflicts.
        for (Clause c : clauses) {
            if (!fringe.contains(c) && !conflicts.contains(c)) {
                for (CLiteral l : c.getCLiterals()) {
                    if (changed.contains(l.getName())) {
                        c.updateClause(labels);

                        if (c.getNumUnknownLiterals() == 1 && c.getNumOfNegative() == c.getNumOfLiterals() - c.getNumUnknownLiterals()) {
                            fringe.push(c);
                            break;
                        } else if (c.numOfNegative >0) {
                            // else if (c.numUnknownLiterals==0 && c.numOfNegative==c.numUnknownLiterals)
                            if (!conflicts.contains(c)) {
                                if (conflicts.size()+1<=maxConflicts)
                                    conflicts.add(c);

                                    break;
                            }
                        }
                    }
                }
            }
        }
    }


}