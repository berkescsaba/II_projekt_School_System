
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

//        JsonWork.jsonPrint();

//        JsonWork.searchTeacher("János");

//        MenuSelect.mainMenu();

        JSONObject main = new JSONObject();

        JSONArray teacher = new JSONArray();
        Map m = new LinkedHashMap(3);
        m.put("firstName", "Nagy");
        m.put("lastName", "Pista");
        m.put("subject", "MATEK");

        teacher.add(m);

        main.put("teacher", teacher);


        PrintWriter pw = new PrintWriter("JSONExample.json");
        pw.write(teacher.toJSONString());

        pw.flush();
        pw.close();

    }
}


