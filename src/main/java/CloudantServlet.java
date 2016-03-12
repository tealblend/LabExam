import java.io.*;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class CloudantServlet extends HttpServlet 
{
	private CloudantConnector db = new CloudantConnector();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	PrintWriter out = response.getWriter();
    	String jsonString = "";
    	int addStat;
    	try {
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			for (FileItem item : items) {
				if (!item.isFormField()) 
				{
					// item is the file (and not a field), read it in and add to List
					Scanner scanner = new Scanner(new InputStreamReader(item.getInputStream(), "UTF-8"));

					//converting uploaded file to json
					while (scanner.hasNextLine()) 
					{
						String line = scanner.nextLine().trim();
						if (line.length() > 0) 
						{
							jsonString += line;
						}
					}
					scanner.close();

					addStat = db.addEntry(jsonString);
					request.setAttribute("msg", "Entry Added!");
					break;
				}
			}
			
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			e.printStackTrace(System.err);
		}

		response.setContentType("text/html");
        response.setStatus(200);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}