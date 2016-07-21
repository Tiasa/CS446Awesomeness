package cs446.notebank;

/**
 * Created by Long on 7/12/2016.
 */
    import android.util.Log;

    import org.json.JSONArray;
    import org.json.JSONObject;

    import java.io.BufferedReader;
    import java.io.InputStreamReader;

    import java.net.HttpURLConnection;
    import java.net.URL;
    import java.util.ArrayList;
    import java.util.HashSet;
    import java.util.Set;

public class DataRequest {
    //TODO: POST does not work since we dont have post working
    private static final String TAG_ID = "id";

    //notebank.click/users
    private static final String TAG_USERNAME = "username";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_PASSWORD = "password";
    ArrayList<String> user = new ArrayList<>();
    ArrayList<String> email = new ArrayList<>();
    ArrayList<String> password = new ArrayList<>();

    //notebank.click/courses
    private static final String TAG_PROFESSOR = "professor";
    private static final String TAG_COURSENAME = "name";
    private static final String TAG_TERM = "term";
    ArrayList<String> prof = new ArrayList<>();
    ArrayList<String> course_name = new ArrayList<>();
    ArrayList<String> term = new ArrayList<>();


    JSONArray search_json;
    //constructor
    public DataRequest() {}


    //------------------------------------------//
    //function to do http get request
    // take in the url with the format : http://notebank.click/socket

    //return 1 if error, 0 otherwise
    public int getRequest(String url) {
        String raw_data;
        String type;
        try {
            URL myurl = new URL(url);

            //find out what are we getting: courses, user, blah blah blah
            type = myurl.toString();
            type = type.substring(type.lastIndexOf("/")+ 1);


            HttpURLConnection con = (HttpURLConnection) myurl.openConnection();

            // HTTP GET we do stuff here
            //set property, time out if it takes too long
            con.setRequestMethod("GET");
            con.setRequestProperty("Connection", "keep-alive");
            con.setRequestProperty("ConnectionTimeout", "1200");




            //read in raw data
            BufferedReader in_stream = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in_stream.readLine()) != null) {
                response.append(inputLine);
            }
            in_stream.close();
            raw_data = response.toString();
        }catch (Exception e) {
            //this is debug, if you got any exception thrown, use this to google
            e.printStackTrace();
            return 1;
        }

        //parseJSON and store in this one
        return parseJSON(type,raw_data);
    }


    //-------
    //extract data from JSON format
    //only called by getRequest
    //return 1 if error, 0if everything go smoothly
    private  int parseJSON(String type,String data) {
        //if its null data, return 1 to signal error
        Log.d("here3",type);
        if (data != null) {
            //switch cases
            if (type.equals("courses")) {
                //CLEAR ALL THE OLD DATA before reading anything
                //Using sets to avoid duplicates, but converting back to arrayLists at the end
                try {
                    Set<String> profSet = new HashSet<>();
                    Set<String> courseSet = new HashSet<>();
                    Set<String> termSet = new HashSet<>();
                    //get the JSON array
                    JSONArray jsonArray = new JSONArray(data);

                    //loop through the array
                    for (int i=0;i < jsonArray.length();i++ ) {
                        JSONObject temp= jsonArray.getJSONObject(i);

                        //get the name
                        String t_prof = temp.getString(TAG_PROFESSOR);
                        String t_name = temp.getString(TAG_COURSENAME);
                        String t_term = temp.getString(TAG_TERM);

                        //push to the array
                        profSet.add(t_prof);
                        courseSet.add(t_name);
                        termSet.add(t_term);
                    }//end for loop

                    // Add the sets to the corresponding ArrayLists
                    prof.clear();
                    course_name.clear();
                    term.clear();

                    prof.addAll(profSet);
                    course_name.addAll(courseSet);
                    term.addAll(termSet);
                }catch (Exception e) {
                    e.printStackTrace();
                    return 1;
                }//end try-catch
            }else if (type.equals("users")) {
                user.clear();
                password.clear();
                email.clear();

                try {
                    //get the JSON array
                    JSONArray jsonArray = new JSONArray(data);

                    //loop through the array
                    for (int i=0;i < jsonArray.length();i++ ) {
                        JSONObject temp= jsonArray.getJSONObject(i);

                        //get the name
                        String t_user = temp.getString(TAG_USERNAME);
                        String t_email = temp.getString(TAG_EMAIL);
                        String t_pass = temp.getString(TAG_PASSWORD);

                        //push to the array
                        user.add(t_user);
                        password.add(t_pass);
                        email.add(t_email);

                    }//end for loop

                }catch (Exception e) {
                    e.printStackTrace();
                    return 1;
                }//end try-catch

            } else if (type.equals("documents")) {

                try {
                    search_json = new JSONArray(data);
                    Log.d("here1","yes");
                } catch (Exception e) {
                    e.printStackTrace();
                    return 1;
                }//end
            }
        }else {

            return 1;
        } //end null check

        return 0;
    }
}
