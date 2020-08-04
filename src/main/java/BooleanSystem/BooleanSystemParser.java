package BooleanSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BooleanSystemParser {
    BufferedReader systemDescriptionReader;
    BufferedReader systemObservationReader;
    Calculator calc;


    public BooleanSystemParser(BufferedReader systemDescription, BufferedReader systemObservation) {
        this.systemDescriptionReader = systemDescription;
        this.systemObservationReader = systemObservation;
        calc = new Calculator();
    }

    public ArrayList<java.lang.String> getSystemDescription() {
        ArrayList<java.lang.String> descriptionsList = new ArrayList<java.lang.String>();
        try {
            java.lang.String line;
            int j = 0;

            while ((line = systemDescriptionReader.readLine()) != null) {
                if (line.toLowerCase().contains("gate")) {
                    line = line.replaceAll("\\[", "").replaceAll("\\]", "");
                    java.lang.String[] gateDetails = line.split(",");
                    java.lang.String type = gateDetails[0].replaceAll("^[\\s\\.\\d]+", "");
                    java.lang.String id = gateDetails[1];
                    java.lang.String output = gateDetails[2];

                    Gate gate = null;

                    if (type.contains("inverter")) {
                        gate = new InverterGate(id, output);
                    } else if (type.contains("and")) {
                        gate = new AndGate(id, output);
                    } else if (type.contains("buffer")) {
                        gate = new BufferGate(id, output);
                    } else if (type.contains("nor")) {
                        gate = new NorGate(id, output);
                    } else if (type.contains("nand")) {
                        gate = new NandGate(id, output);
                    } else if (type.contains("xor")) {
                        gate = new XorGate(id, output);
                    } else if (type.contains("or")) {
                        gate = new OrGate(id, output);
                    }

                    assert gate != null;
                    for (int i = 3; i < gateDetails.length; i++)
                        gate.addInput(gateDetails[i]);


                    java.lang.String healthyVariable = gate.getHealthyVariableRepresentation();
                    if (Calculator.LOGGING.equals("INFO"))
                        System.out.println("Starting to get CNF for record" + j + " : " + healthyVariable);
                    java.lang.String CNF = calc.getCNF(healthyVariable);
                    java.lang.String[] splited = CNF.split("&");

                    for (int i = 0; i < splited.length; i++) {
                        java.lang.String cleanClause = splited[i].replaceAll("\\(", "").replaceAll("\\)", "");
                        descriptionsList.add(cleanClause);
                    }
                    j += 1;
                }
            }
            systemDescriptionReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return descriptionsList;
    }

    public ArrayList<java.lang.String> getSystemObservation() {
        ArrayList<java.lang.String> observationList = new ArrayList<java.lang.String>();
        java.lang.String line;
        int j = 0;
        Scanner read =new Scanner(systemObservationReader);
        read.useDelimiter("\\.");
        while (read.hasNext()) {
            line=read.next();
            line = line.replaceAll("-", "~");
            line = line.replaceAll(",", "&");
            if (line.contains("]") || line.contains(")") || line.contains(".")) {
                java.lang.String[] splitedLine = line.split("\\[");
                line = splitedLine[1].replaceAll("\\]\\)\\.", "");
                java.lang.String[] splited = line.split("&");
                for (int i = 0; i < splited.length; i++) {
                   // java.lang.String cleanClause = splited[i].replaceAll("\\(", "").replaceAll("\\)", "");
                    java.lang.String cleanClause = splited[i].replaceAll("[()]",  "").replaceAll("[]]","");
                    observationList.add(cleanClause);
                }
                j += 1;
            }
            else
                break;

        }
        read.close();
        return observationList;
    }
}
