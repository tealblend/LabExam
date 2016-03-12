import java.io.*;
import java.net.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.Json;


@WebServlet(name = "Image", urlPatterns = {"/Image"})
public class Image extends HttpServlet {

	private String FACE_ENDPOINT_URL = "http://gateway-a.watsonplatform.net/calls/url/URLGetRankedImageFaceTags";

 @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		CloudantConnector db = new CloudantConnector();
		AlchemyConnector connector = new AlchemyConnector();
		//AlchemyVision service = new AlchemyVision();
		//service.setApiKey(connector.getAPIKey());

		String input_url = (String) request.getParameter("imageurl");
		StringBuilder sb = new StringBuilder();
		String line;
		
		URL face_url = new URL(FACE_ENDPOINT_URL+"?url="+input_url+"&apikey="+connector.getAPIKey()+"&outputMode=json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(face_url.openStream()));
		while ((line = reader.readLine()) != null){
			sb.append(line);
		}
		JSONObject imagescan= new JSONObject(sb.toString());
		JSONArray imageinfo = (JSONArray) imagescan.getJSONArray("imageFaces");
		String age=null;
		String gender=null;
		String combine=null;
		JSONObject cloudant=new JSONObject();
		for(int i=0;i<imageinfo.length();i++){
			//db.deleteDB("image");
			JSONObject innerObj= (JSONObject) imageinfo.get(i);
			age = innerObj.getJSONObject("age").getString("ageRange");
			gender= innerObj.getJSONObject("gender").getString("gender");
			JsonObject info = Json.createObjectBuilder()
			.add("age", age)
			.add("gender", gender).build();
			//request.setAttribute("age",age);
			try{
			db.addEntry(info.toString());
			}catch(Exception e){}
			
		}
		request.setAttribute("age",age);
		request.setAttribute("gender",gender);
		//ImageFaces image_faces = service.recognizeFaces(input_url,false);
		//request.setAttribute("image_faces",image_faces);
	
		response.setContentType("text/html");
        response.setStatus(200);
        request.getRequestDispatcher("viewinfo.jsp").forward(request, response);

	}
	

}

