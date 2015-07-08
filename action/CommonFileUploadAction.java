package com.bskcare.ch.base.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;

import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.util.StringUtils;
import com.bskcare.ch.util.SystemConfig;
import com.bskcare.ch.vo.ClientInfo;

@Scope("prototype")
public class CommonFileUploadAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	/**
	 * 上传文件
	 * @param uploadFile  上传文件
	 * @param uploadFileName  上传文件名称
	 * @param clientInfo  客户信息
	 * @param msgUrl   上传文件地址
	 * @return 如果大于5M不上传文件
	 * @throws IOException
	 */
	public static String uploadFile(File uploadFile, String uploadFileName,
			ClientInfo clientInfo, String msgUrl) throws IOException {
		if (null != uploadFile) {
			String filePath = "";

			if(uploadFile.length() > 10485760/2) {
				return null;
			}
			
			String yyyy = DateUtils.formatDate(DateUtils.YEAR_PATTERN_PLAIN,
					new Date());

			String yyyyMMddhhmmss = DateUtils.formatDate(
					DateUtils.LONG_DATE_PATTERN_PLAIN, new Date());
			String fileExt = uploadFileName.substring(
					uploadFileName.lastIndexOf(".") + 1).toLowerCase();

			String base = SystemConfig.getString("client_upload_base");
			String msg = SystemConfig.getString(msgUrl);
			String fileName = "";

			if (clientInfo.getName() != null) {
				String name = clientInfo.getName();
				fileName = name + yyyyMMddhhmmss + "." + fileExt;
			} else {
				fileName = yyyyMMddhhmmss + "." + fileExt;
			}

			msg = MessageFormat.format(msg, new Object[] {
					""+clientInfo.getId(), yyyy, fileName });
			File file = new File(base + msg);

			FileUtils.copyFile(uploadFile, file);

			//filePath = file.toString();
			return msg;

		}
		return null;
	}
	
	/**
	 * 安卓上传头像
	 * @throws IOException 
	 */
	
	public static String uploadHeadportrait(File uploadFile, String filePath, ClientInfo clientInfo ) throws IOException{
		if (null != uploadFile&&clientInfo!=null&&clientInfo.getId()!=null) {
			if(uploadFile.length() > 1048576) {
				return null;
			}
			File file = new File(filePath);
			FileUtils.copyFile(uploadFile, file,true);
			//filePath = file.toString();
			return "success";
		}
		return null;
	}

	/**
	 * pc上传头像
	 * @param uploadFile  上传图片
	 * @param uploadFileName  上传文件名称
	 * @param clientInfo  客户信息
	 * @param msgUrl   上传文件地址
	 * @throws IOException
	 */
	public static String uploadImage(File uploadImage,ClientInfo clientInfo) throws IOException {
		if (null != uploadImage) {
			String filePath = "";
			// 判断文件大小,大于1048576kb(即1M),限制上传
			if(uploadImage.length() > 1048576) {
				return "big";
			}
			// 获得盘符路径
			String base = SystemConfig.getString("client_upload_base");
			// 转换格式
			String fileExt = clientInfo.getHeadPortrait().substring(
					clientInfo.getHeadPortrait().lastIndexOf(".") + 1).toLowerCase().replace("tmp", "jpg");
			// 获得文件部分路径
			String msg = SystemConfig.getString("client_head_portrait_url");
			String fileName = "";
			// 文件命名
			fileName = clientInfo.getId() + "_bak." + fileExt;
			msg = MessageFormat.format(msg, new Object[] {
					""+clientInfo.getId(), fileName });
			// 将文件写入服务器
			File file = new File(base + msg);
			FileUtils.copyFile(uploadImage, file);
			// 读取图片获得宽高
			BufferedImage image = ImageIO.read(new File(base + msg));
			int imageWidth = image.getWidth();
			int imageHeight = image.getHeight();
			if (imageWidth < 180 && imageHeight < 180) {
				return "small";
			}
			// 计算宽高(高宽)比,如果大于3,限制上传
			double ratio_a,ratio_b;
			ratio_a = imageWidth / imageHeight;
			ratio_b = imageHeight / imageWidth;
			if (ratio_a > 3.0 && ratio_a != 0.0) {
				return "exception";
			}
			if (ratio_b > 3.0 && ratio_b != 0.0) {
				return "exception";
			}
			return msg;
		}
		return null;
	}
	
	/**
	 * 下载
	 * @param filePath  下载文件的地址
	 * @param uploadDate	上传时间
	 * @param client  用户信息
	 */
	public static String downLoad(String filePath, Date uploadDate,
			ClientInfo client) throws Exception {
		if (!StringUtils.isEmpty(filePath)) {
			HttpServletResponse response = ServletActionContext.getResponse();

			String fileName = "";
			String yyyyMMddhhmmss = DateUtils.formatDate(
					DateUtils.LONG_DATE_PATTERN_PLAIN, uploadDate);
			String fileExt = filePath.substring(filePath.lastIndexOf(".") + 1)
					.toLowerCase();
			
			String base = SystemConfig.getString("client_upload_base");
			filePath = base + filePath;
			
			if (client != null) {
				if (!StringUtils.isEmpty(client.getName())) {
					String name = new String(client.getName().getBytes(),
							"ISO8859-1");
					fileName = name + yyyyMMddhhmmss + "." + fileExt;
				} else {
					fileName = yyyyMMddhhmmss + "." + fileExt;
				}
			}

			// 下载本地文件
			// 读到流中
			File file = new File(filePath);
			InputStream inStream = new FileInputStream(file);// 文件的存放路径
			// 设置输出的格式
			response.reset();
			response.setContentType("bin");
			response.addHeader("Content-Disposition", "attachment; filename=\""
					+ fileName + "\"");

			// 循环取出流中的数据
			byte[] b = new byte[100];
			int len;
			try {
				while ((len = inStream.read(b)) > 0)
					response.getOutputStream().write(b, 0, len);
				response.getOutputStream().close();
				inStream.close();
				return null;

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 手机apk文件下载
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String phoneDownload(String filePath) throws Exception {
		if (!StringUtils.isEmpty(filePath)) {
			HttpServletResponse response = ServletActionContext.getResponse();

			// 下载本地文件
			// 读到流中
			File file = new File(filePath);
			InputStream inStream = new FileInputStream(file);// 文件的存放路径
			// 设置输出的格式
			response.reset();
			response.setContentType("application/vnd.android.package-archive");
			response.addHeader("Content-Disposition", "attachment; filename=\""
					+ file.getName() + "\"");

			// 循环取出流中的数据
			byte[] b = new byte[100];
			int len;
			try {
				OutputStream ops = response.getOutputStream();
				while ((len = inStream.read(b)) > 0) {
					ops.write(b, 0, len);
				}
				
				if(null != inStream) {
					inStream.close();
				}
				if(null != ops) {
					ops.flush();
					ops.close();
				}
				
				return null;
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
		return null;
	}

}
