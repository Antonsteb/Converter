public class Filter {
    public static boolean filtration(Motherboard motherboard, String variableNameFiltr, String valueVariableFiltr){
        if (valueVariableFiltr.equals(motherboard.getValueVariableByName(variableNameFiltr)))
            return true;
        return false;
    }
}
