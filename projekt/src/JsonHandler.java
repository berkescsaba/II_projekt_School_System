import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonHandler {
    static void fileWrite(JSONObject jsonObject) throws IOException {
        FileWriter fileWriter = new FileWriter(FilePath.DIARY);
        fileWriter.write(jsonObject.toJSONString());
        fileWriter.close();
    }
    static JSONObject jsonReader() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader fileReader = new FileReader(FilePath.DIARY);
        Object obj = parser.parse(fileReader);
        JSONObject jsonObject = (JSONObject) obj;
        return jsonObject;
    }


}
