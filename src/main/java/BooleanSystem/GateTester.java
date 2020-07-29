package BooleanSystem;

public class GateTester {

    public static void main(java.lang.String[] args) {
        AndGate andGate = new AndGate( "C1", "A");
        andGate.addInput("X");
        andGate.addInput("Y");
        System.out.println(andGate.getHealthyVariableRepresentation());
        System.out.println("( ¬HC1 ∨ A ∨ ¬X ∨ ¬Y ) ∧ ( ¬HC1 ∨ ¬A ∨ X ∨ Y ) ∧ ( ¬HC1 ∨ ¬A ∨ X ∨ ¬Y ) ∧ ( ¬HC1 ∨ ¬A ∨ ¬X ∨ Y )");


        AndGate andGate1 = new AndGate( "C2", "B");
        andGate1.addInput("Y");
        andGate1.addInput("Z");
        System.out.println(andGate1.getHealthyVariableRepresentation());

        InverterGate inverterGate = new InverterGate("GATE26", "z1");
        inverterGate.addInput("i9");
        System.out.println(inverterGate.getHealthyVariableRepresentation());

        OrGate orGate = new OrGate("C3", "W");
        orGate.addInput("A");
        orGate.addInput("B");
        System.out.println(orGate.getHealthyVariableRepresentation());

        NandGate nandGate = new NandGate("gate32", "z2");
        nandGate.addInput("i1");
        nandGate.addInput("i2");
        System.out.println(nandGate.getHealthyVariableRepresentation());

        XorGate xorGate = new XorGate("xor1", "C");
        xorGate.addInput("A");
        xorGate.addInput("B");
        System.out.println(xorGate.getHealthyVariableRepresentation());

    }

}
