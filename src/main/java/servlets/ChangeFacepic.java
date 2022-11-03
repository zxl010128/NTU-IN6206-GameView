package servlets;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import entity.User;
import DAO.UserDBAO;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class ChangeFacepic
 */
@WebServlet("/ChangeFacepic")
@MultipartConfig
public class ChangeFacepic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static String http = "https://";
	// Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。oss-cn-hangzhou.aliyuncs.com
	public static String endpoint = "oss-ap-southeast-1.aliyuncs.com";
	// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
	public static String accessKeyId = "LTAI5t5nwpTLqE9Q7YCsCnhK";
	public static String accessKeySecret = "YmTMbWgQEvywYNwYYRknXknrW4NAPQ";
	// 填写Bucket名称，例如examplebucket。
	public static String bucketName = "gameview6206";
	// 填写Bucket 下面文件夹名称，例如wsw
	public static String bucketFolderName = "user/";



	// 创建OSSClient实例。
	public static OSS ossClient = new OSSClientBuilder().build(http+endpoint, accessKeyId, accessKeySecret);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeFacepic() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub


		String token = request.getParameter("token");
		Part file =null;

		for (Part part : request.getParts()) {
			String fileName = part.getSubmittedFileName();
			if (fileName==null) continue;
			file=part;
		}
		String facepicture = http+bucketName+"."+endpoint+"/"+bucketFolderName+file.getSubmittedFileName();
		try {
			PrintWriter out = response.getWriter();
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			UserDBAO userdbao = new UserDBAO();
			Long id = userdbao.identifyId(token);
			if(id == 0) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "fail");
				jsonObject.put("status_code", 401);
				out.write(jsonObject.toString());
				out.flush();
				out.close();
				return;
			}

			boolean x=userdbao.changeFacepic(id,facepicture);
			if (x == false) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", "");
				jsonObject.put("message", "fail");
				jsonObject.put("status_code", 500);
				out.write(jsonObject.toString());
				out.flush();
				out.close();
				return;

			}
			JSONObject fileKv = new JSONObject();
			fileKv.put("file_name",file.getSubmittedFileName());
			fileKv.put("file_url",http+bucketName+"."+endpoint+"/"+bucketFolderName+file.getSubmittedFileName());

			// 创建PutObject请求。
			ossClient.putObject(bucketName, bucketFolderName+file.getSubmittedFileName(), file.getInputStream());

			JSONObject jsonObject=new JSONObject();
			jsonObject.put("data", fileKv);
			jsonObject.put("status_code", 200);
			jsonObject.put("message", "success");
			out.write(jsonObject.toString());
			out.flush();
			out.close();
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}

		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
