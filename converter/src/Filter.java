import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * The class defining the correspondence of the motherboard to the given conditions
 *
 * @author  Steblyansky Anton
 * @version 1.0
 * @since   2018-02-27
 */

public class Filter {
   private String nameVariableFilter;
   private String valueVariableFilter;
   private String sign;

/**
 * This method is constructor class Filter
 * @see Filter
 * @param nameVariableFilter Name for comparison
 * @param sign Comparison operator. Possible values {"=", "!=", "<", "<=", ">", ">="}
 * @param valueVariableFilter value parameter for comparison
 */

    Filter(String nameVariableFilter, String sign, String valueVariableFilter) {
        this.nameVariableFilter = nameVariableFilter;
        this.sign = sign;
        this.valueVariableFilter = valueVariableFilter;
    }

    /**
     * Check if the current motherboard matches the filter conditions
     * @param motherboard Current motherboard
     * @return Returns true or false, according to the result of the checking
     */

     boolean filtration(Motherboard motherboard){
        try {
            switch (sign) {
                case "=": {
                    return valueVariableFilter.equals(motherboard.getValueVariableByName(nameVariableFilter));

                }
                case "!=": {
                    return !Objects.equals(valueVariableFilter, motherboard.getValueVariableByName(nameVariableFilter));
                }
                case "<": {
                    if (nameVariableFilter.equals(Constans.PRICE) && (checkParameter())){
                        return Float.parseFloat(motherboard.getPrice().replace(",", ".")) < Float.parseFloat(valueVariableFilter);
                    } else if (nameVariableFilter.equals(Constans.DATE)){
                        Date date = parseDate(valueVariableFilter);
                        Date date1 = parseDate(motherboard.getDate());
                        return date != null && date1 != null && date1.before(date);
                    } else {
                        return Integer.parseInt(motherboard.getValueVariableByName(nameVariableFilter)) < Float.parseFloat(valueVariableFilter);
                    }
                }
            case "<=": {
                if (nameVariableFilter.equals(Constans.PRICE) && (checkParameter())){
                    return Float.parseFloat(motherboard.getPrice().replace(",", ".")) <= Float.parseFloat(valueVariableFilter);
                } else if (nameVariableFilter.equals(Constans.DATE)){
                    Date date = parseDate(valueVariableFilter);
                    Date date1 = parseDate(motherboard.getDate());
                    return date != null && date1 != null && (date1.before(date) || date1.equals(date));
                } else {
                    return Integer.parseInt(motherboard.getValueVariableByName(nameVariableFilter)) <= Float.parseFloat(valueVariableFilter);
                }
            }
            case ">": {
                if (nameVariableFilter.equals(Constans.PRICE) && (checkParameter())){
                    return Float.parseFloat(motherboard.getPrice().replace(",", ".")) > Float.parseFloat(valueVariableFilter);
                } else if (nameVariableFilter.equals(Constans.DATE)){
                    Date date = parseDate(valueVariableFilter);
                    Date date1 = parseDate(motherboard.getDate());
                    return date != null && date1 != null && date1.after(date);
                } else {
                    return Integer.parseInt(motherboard.getValueVariableByName(nameVariableFilter)) > Float.parseFloat(valueVariableFilter);
                }
            }
            case ">=": {
                if (nameVariableFilter.equals(Constans.PRICE) && (checkParameter())) {
                    return Float.parseFloat(motherboard.getPrice().replace(",", ".")) >= Float.parseFloat(valueVariableFilter);
                } else if (nameVariableFilter.equals(Constans.DATE)) {
                    Date date = parseDate(valueVariableFilter);
                    Date date1 = parseDate(motherboard.getDate());
                    return date != null && date1 != null && (date1.after(date) || date1.equals(date));
                } else {
                    return Integer.parseInt(motherboard.getValueVariableByName(nameVariableFilter)) >= Float.parseFloat(valueVariableFilter);
                }
            }
            }
            System.out.println("error reading parameters\n Check the correctness of the input parameters");
            return false;
        } catch (Exception exception){
            System.out.println("error reading parameters\n Check the correctness of the input parameters");
            return false;
        }
    }

    /**
     * Check if the parameters are suitable for comparison
     * @return Returns true or false, according to the result of the checking
     */

    private boolean checkParameter(){
        return (!nameVariableFilter.equals(Constans.MANUFACTURER)) && (!nameVariableFilter.equals(Constans.PRODUCT_NAME)) && (!nameVariableFilter.equals(Constans.CHIPSET_TYPE));
    }
    /**
     * Parser string to date
     * @param date String in format DATE_FORMAT {@link Constans}
     *
     */
    private Date parseDate(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constans.DATE_FORMAT);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("error reading date\n Check the correctness of the input parameters or structure of xml file");
            return null;
        }
    }
}
