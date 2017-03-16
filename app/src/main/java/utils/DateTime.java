package utils;

import android.util.Log;

import com.app.flirtjar.App;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rutvik on 3/6/2017 at 9:14 PM.
 */

public class DateTime
{
    public static String convertDate(String inputFormat, String outputFormat, String dateToBeConverted)
    {
        String convertedDate = "";
        SimpleDateFormat inDateFormat = new SimpleDateFormat(inputFormat, Locale.getDefault());
        try
        {
            Date date = inDateFormat.parse(dateToBeConverted);
            SimpleDateFormat outDateFormat = new SimpleDateFormat(outputFormat, Locale.getDefault());
            convertedDate = outDateFormat.format(date);
            Log.i(App.APP_TAG, "CONVERTED DATE: " + convertedDate);
            return convertedDate;
        } catch (ParseException e)
        {
            e.printStackTrace();
            return dateToBeConverted;
        }
    }
}
