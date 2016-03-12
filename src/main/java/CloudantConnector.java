import java.util.*;
import java.io.*;
import org.cloudfoundry.runtime.env.*;
import com.cloudant.client.api.*;
import com.cloudant.client.api.model.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CloudantConnector
{

    public CloudantConnector()
    {

    }

    public int addEntry(String jsonString) throws Exception
    {
        try{
            CloudantClient client = getClientConn();

            Database db = client.database("image", true);
            JSONParser parser = new JSONParser();
            
            try{
              JSONObject json = (JSONObject)parser.parse(jsonString);
              
              Response rs = db.save(json);

              return rs.getStatusCode();

            }catch (Exception e) {
              e.printStackTrace();
            }

        }catch (Exception e) {
            System.out.println("Failed: " + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }
	
	
    protected CloudantClient getClientConn() throws Exception 
    {
        CloudEnvironment environment = new CloudEnvironment();
        if ( environment.getServiceDataByLabels("cloudantNoSQLDB").size() == 0 ) {
           // throw new Exception( "No Cloudant service is bound to this app!!" );
        } 

        Map credential = (Map)((Map)environment.getServiceDataByLabels("cloudantNoSQLDB").get(0)).get( "credentials" );

        CloudantClient client = (CloudantClient) ClientBuilder.account((String)credential.get("username"))
                                         .username((String)credential.get("username"))
                                         .password((String)credential.get("password"))
                                         .build();
     
        return client;
  }
}

