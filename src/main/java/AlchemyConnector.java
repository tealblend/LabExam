
import java.util.Map;
import java.text.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.cloudfoundry.runtime.env.*;

public class AlchemyConnector {

    private String apikey;

    public AlchemyConnector() {
        setAPIKey();
    }

    private void setAPIKey() {
		CloudEnvironment environment = new CloudEnvironment();
        if ( environment.getServiceDataByLabels("alchemy_api").size() == 0 ) {
            //throw new Exception( "No Alchemy service is bound to this app!!" );
        } 

        Map credential = (Map)((Map)environment.getServiceDataByLabels("alchemy_api").get(0)).get( "credentials" );
		String apikey = (String) credential.get("apikey");
        this.apikey = apikey;
    }

    public String getAPIKey() {
        return apikey;
    }

}
