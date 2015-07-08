package com.bskcare.ch.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * 文件操作工具类
 * 
 * @author houzhiqing
 */
public class FileUtils {
	private static final Logger log = Logger.getLogger(FileUtils.class);

	private static String ENCODING = "UTF-8";

	public final static String IMAGE_FILE_EXT = "jpg;jpeg;png;gif;bmp;ico";

	public final static String ATTACHE_FILE_EXT = "doc;zip;rar;pdf";// 附件文件

	public final static String FORBID_FILE_EXT = "jsp;com;bat;cmd";// 禁止的文件

	public final static String EXE_FILE_EXT = "exe;com;bat;cmd";//

	public final static String DEMO_PROPPERTY_PATH_NAME = "demo/demo.properties";// 样例文件

	public static boolean isAttacheFile(String fileName) {
		return checkExtFile(ATTACHE_FILE_EXT, fileName);
	}

	public static boolean isForbidFile(String fileName) {
		return checkExtFile(FORBID_FILE_EXT, fileName);
	}

	public static boolean isImgageFile(String fileName) {
		return checkExtFile(IMAGE_FILE_EXT, fileName);
	}

	public static boolean isExeFile(String fileName) {
		return checkExtFile(EXE_FILE_EXT, fileName);
	}

	public static boolean checkExtFile(String ext, String fileName) {
		if (ext == null)
			return false;
		String[] exts = ext.split(";");
		String file = fileName.toLowerCase();
		for (int i = 0; i < exts.length; i++)
			if (file.endsWith("." + exts[i]))
				return true;
		return false;
	}

	/**
	 * ex:pathName="com/bskcare/config/sql.properties";
	 * 表示配置文件在src下的com.bskcare.config/sql.properties
	 * 
	 * @param pathName
	 * @return
	 */
	public static Properties getPropsByPathAndName(String pathName) {
		Properties props = new Properties();
		String path = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();
		InputStream is = null;
		try {
			path = java.net.URLDecoder.decode(path, ENCODING);
			is = new FileInputStream(path + pathName);
			props.load(is);
		} catch (FileNotFoundException e) {
			log.error(e.toString());
		} catch (IOException e) {
			log.error(e.toString());
		}
		return props;
	}

	public static String getPropertiesContext(String key, String pathName) {
		String result = "";
		Properties props = getPropsByPathAndName(pathName);
		result = props.getProperty(key);
		return result;
	}

	/**
	 * 自定义读取配置文件方法样例
	 * 
	 * @param key
	 * @return
	 */
	public static String getDemoProperties(String key) {
		String result = "";
		Properties props = getPropsByPathAndName(DEMO_PROPPERTY_PATH_NAME);
		result = props.getProperty(key);
		return result;
	}

	/**
	 * 获取一个临时文件
	 * 
	 * @param dir
	 * @param fileExt
	 * @return
	 */
	public static String getTempFile(String dir, String fileExt) {
		String tempFileName = StringUtils.getRandString(8) + fileExt;
		File file = new File(dir + "/" + tempFileName);
		if (file.exists())
			return getTempFile(dir, fileExt);
		else
			return tempFileName;
	}

	/**
	 * 以输入流的形式保存文件
	 * 
	 * @param in
	 * @param fileName
	 * @return
	 */
	public static boolean saveFile(InputStream in, String fileName) {
		File outFile = new File(fileName);
		try {
			FileOutputStream out = new FileOutputStream(outFile);
			byte[] temp = new byte[11024];
			int length = -1;
			while ((length = in.read(temp)) > 0) {
				out.write(temp, 0, length);
			}
			out.flush();
			out.close();
			in.close();
		} catch (Exception e) {
			log.error(e.toString());
			return false;
		}
		return true;
	}

	/**
	 * 获得控制台用户输入的信息
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getInputMessage() throws IOException {
		log.debug("请输入您的命令∶");
		byte buffer[] = new byte[1024];
		int count = System.in.read(buffer);
		char[] ch = new char[count - 2];// 最后两位为结束符，删去不要
		for (int i = 0; i < count - 2; i++)
			ch[i] = (char) buffer[i];
		String str = new String(ch);
		return str;
	}

	/**
	 * 以文件流的方式复制文件
	 * 
	 * @param src
	 *            文件源目录
	 * @param dest
	 *            文件目的目录
	 * @throws IOException
	 */
	public void copyFile(String src, String dest) throws IOException {
		FileInputStream in = new FileInputStream(src);
		File file = new File(dest);
		if (!file.exists())
			file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		int c;
		byte buffer[] = new byte[1024];
		while ((c = in.read(buffer)) != -1) {
			for (int i = 0; i < c; i++)
				out.write(buffer[i]);
		}
		in.close();
		out.close();
	}

	/**
	 * 文件输出示例 写文件
	 */
	public void PrintStreamDemo() {
		try {
			FileOutputStream out = new FileOutputStream("D:/test.txt");
			PrintStream p = new PrintStream(out);
			for (int i = 0; i < 10; i++)
				p.println("This is " + i + " line");
		} catch (FileNotFoundException e) {
			log.error(e.toString());
		}
	}

	/*
	 * 利用StringBuffer写文件
	 */
	public void StringBufferDemo() throws IOException {
		File file = new File("/root/sms.log");
		if (!file.exists())
			file.createNewFile();
		FileOutputStream out = new FileOutputStream(file, true);
		for (int i = 0; i < 10000; i++) {
			StringBuffer sb = new StringBuffer();
			sb.append("这是第" + i + "行:前面介绍的各种方法都不关用,为什么总是奇怪的问题 ");
			out.write(sb.toString().getBytes("utf-8"));
		}
		out.close();
	}

	/**
	 * 文件重命名
	 * 
	 * @param path
	 *            文件目录
	 * @param oldname
	 *            原来的文件名
	 * @param newname
	 *            新文件名
	 */
	public void renameFile(String path, String oldname, String newname) {
		if (!oldname.equals(newname)) {// 新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile = new File(path + "/" + oldname);
			File newfile = new File(path + "/" + newname);
			if (newfile.exists())// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				log.debug(newname + "已经存在！");
			else {
				oldfile.renameTo(newfile);
			}
		}
	}

	/**
	 * 转移文件目录
	 * 
	 * @param filename
	 *            文件名
	 * @param oldpath
	 *            旧目录
	 * @param newpath
	 *            新目录
	 * @param cover
	 *            若新目录下存在和转移文件具有相同文件名的文件时，是否覆盖新目录下文件，cover=true将会覆盖原文件， 否则不操作
	 */
	public void changeDirectory(String filename, String oldpath,
			String newpath, boolean cover) {
		if (!oldpath.equals(newpath)) {
			File oldfile = new File(oldpath + "/" + filename);
			File newfile = new File(newpath + "/" + filename);
			if (newfile.exists()) {// 若在待转移目录下，已经存在待转移文件
				if (cover)// 覆盖
					oldfile.renameTo(newfile);
				else
					log.debug("在新目录下已经存在：" + filename);
			} else {
				oldfile.renameTo(newfile);
			}
		}
	}

	/**
	 * 读文件
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public String FileInputStreamDemo(String path) throws IOException {
		File file = new File(path);
		if (!file.exists() || file.isDirectory())
			throw new FileNotFoundException();
		FileInputStream fis = new FileInputStream(file);
		byte[] buf = new byte[1024];
		StringBuffer sb = new StringBuffer();
		while ((fis.read(buf)) != -1) {
			sb.append(new String(buf));
			buf = new byte[1024];// 重新生成，避免和上次读取的数据重复
		}
		return sb.toString();
	}

	/**
	 * 读文件
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public String BufferedReaderDemo(String path) throws IOException {
		File file = new File(path);
		if (!file.exists() || file.isDirectory())
			throw new FileNotFoundException();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String temp = null;
		StringBuffer sb = new StringBuffer();
		temp = br.readLine();
		while (temp != null) {
			sb.append(temp + " ");
			temp = br.readLine();
		}
		return sb.toString();
	}

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 *            目录
	 */
	public void createDir(String path) {
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdir();
	}
	
	/**
	 * 删除文件 
	 * @param pathName (path + fileName)
	 * 如：/home/a/b/c/aaa.jpg
	 */
	public static void delFile(String pathName) {
		if(null == pathName || "".equals(pathName)) {
			return;
		}
		File file = new File(pathName);
		if (file.exists() && file.isFile())
			file.delete();
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 *            目录
	 * @param filename
	 *            文件名
	 */
	public void delFile(String path, String filename) {
		File file = new File(path + "/" + filename);
		if (file.exists() && file.isFile())
			file.delete();
	}

	/** */
	/**
	 * 递归删除文件夹
	 * 
	 * @param path
	 *            要利用File类的delete()方法删除目录时，必须保证该目录下没有文件或者子目录，否则删除失败，
	 *            因此在实际应用中，我们要删除目录，必须利用递归删除该目录下的所有子目录和文件，然后再删除该目录。
	 */
	public void delDir(String path) {
		File dir = new File(path);
		if (dir.exists()) {
			File[] tmp = dir.listFiles();
			for (int i = 0; i < tmp.length; i++) {
				if (tmp[i].isDirectory()) {
					delDir(path + "/" + tmp[i].getName());
				} else {
					tmp[i].delete();
				}
			}
			dir.delete();
		}
	}

	/**
	 * the java.io.File.renameTo(File,String newname) actually move the file to
	 * newname's path and rename it which is inconvenient.<br>
	 * this method just rename the file and keep where it is,ignore the move<br>
	 * and return the new File's reference
	 * <p>
	 * eg. file = renameFile(file,"NewName.xxx");
	 * 
	 * @param file
	 *            File
	 * @param name
	 *            String the newName
	 * @return File the new File's reference
	 */
	public static File renameFile(String file, String name) {
		return renameFile(new File(file), name);
	}

	public static File renameFile(File file, String name) {
		File newname;
		if (file == null || !file.exists()) {
			log.debug("File not found!");
			return null;
		}
		if (file.getParent() == null) {
			newname = new File(name);
			file.renameTo(newname);
		} else {
			newname = new File(file.getParentFile(), name);
			file.renameTo(newname);
		}
		log.debug("rename is done: " + file + " -> " + newname);
		return newname;
	}

	/**
	 * use java.io.File.renameTo(File,String newname) to move file
	 * <p>
	 * parameters must be a file and a directory<br>
	 * return a reference point to the new file
	 * 
	 * @param scr
	 *            String
	 * @param dir
	 *            String
	 * @return File a reference point to the new file
	 */
	public static File moveFile(String scr, String dir) {
		return moveFile(new File(scr), new File(dir));
	}

	public static File moveFile(File scr, File dir) {
		if (scr == null || dir == null) {
			log.debug("a null reference!");
			return null;
		}
		if (!scr.exists() || !dir.exists() || scr.isDirectory() || dir.isFile()) {
			log.debug("not file or directory or not exist!");
			return null;
		}
		File f = new File(dir, scr.getName());
		if (f.exists()) {
			log.debug("target file has existed!");
		}
		scr.renameTo(f);
		log.debug("move file done: " + scr + " -> " + f);
		return f;
	}

	/**
	 * turn file to String
	 * <p>
	 * maybe you can use it to access file randomly through the string<br>
	 * but it maybe fault when trun a big file to string
	 * 
	 * @param file
	 *            String
	 * @return String
	 */
	public static String fileToString(String file) {
		// String lineStr = "";
		String string = "";
		int i;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));
			while ((i = in.read()) != -1) {
				string += (char) i;
			}
			string = string.trim();
			return string;
		} catch (FileNotFoundException ex) {
			log.error("File Not Found!");
		} catch (IOException ex) {
			log.error("IO exception!");
		} finally {
			try {
				in.close();
			} catch (IOException ex1) {
				log.error("IOException when closing!");
			}
		}
		return null;
	}

	/**
	 * 文件与String互转 write the string to file<br>
	 * if fail return false else return true
	 * 
	 * @param src
	 *            String
	 * @param file
	 *            String
	 * @return boolean
	 */
	public static boolean stringToFile(String src, String file) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file));
			out.write(src);
			return true;
		} catch (Exception ex) {
			log.error("IO exception!");
		} finally {
			try {
				out.close();
			} catch (IOException ex) {
				log.error("IOException when closing!");
			}
		}
		return false;
	}

	/**
	 * write the content to a file;
	 * 
	 * @param output
	 * @param content
	 * @throws Exception
	 */
	public static void createFile(String output, String content)
			throws Exception {
		OutputStreamWriter fw = null;
		PrintWriter out = null;
		try {
			fw = new OutputStreamWriter(new FileOutputStream(output), ENCODING);
			out = new PrintWriter(fw);
			out.print(content);
		} catch (Exception ex) {
			throw new Exception(ex);
		} finally {
			if (out != null)
				out.close();
			if (fw != null)
				fw.close();
		}

	}

	/**
	 * read the content from a file;
	 * 
	 * @param output
	 * @param content
	 * @throws Exception
	 */
	public static String readFile(String input) throws Exception {
		char[] buffer = new char[4096];
		int len = 0;
		StringBuffer content = new StringBuffer(4096);

		InputStreamReader fr = null;
		BufferedReader br = null;
		try {
			fr = new InputStreamReader(new FileInputStream(input), ENCODING);
			br = new BufferedReader(fr);
			while ((len = br.read(buffer)) > -1) {
				content.append(buffer, 0, len);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (br != null)
				br.close();
			if (fr != null)
				fr.close();
		}
		return content.toString();
	}

	/**
	 * This class moves an input file to output file
	 * 
	 * @param String
	 *            input file to move from
	 * @param String
	 *            output file
	 * 
	 */
	public static void move(String input, String output) throws Exception {
		File inputFile = new File(input);
		File outputFile = new File(output);
		try {
			inputFile.renameTo(outputFile);
		} catch (Exception ex) {
			throw new Exception("Can not mv" + input + " to " + output
					+ ex.getMessage());
		}
	}

	/**
	 * This class copies an input file to output file
	 * 
	 * @param String
	 *            input file to copy from
	 * @param String
	 *            output file
	 */
	public static boolean copy(String input, String output) throws Exception {
		int BUFSIZE = 65536;
		FileInputStream fis = new FileInputStream(input);
		FileOutputStream fos = new FileOutputStream(output);

		try {
			int s;
			byte[] buf = new byte[BUFSIZE];
			while ((s = fis.read(buf)) > -1) {
				fos.write(buf, 0, s);
			}

		} catch (Exception ex) {
			throw new Exception("makehome" + ex.getMessage());
		} finally {
			fis.close();
			fos.close();
		}
		return true;
	}

	/**
	 * create a directory
	 * 
	 * @param home
	 * @throws Exception
	 */
	public static void makehome(String home) throws Exception {
		File homedir = new File(home);
		if (!homedir.exists()) {
			try {
				homedir.mkdirs();
			} catch (Exception ex) {
				throw new Exception("Can not mkdir :" + home
						+ " Maybe include special charactor!");
			}
		}
	}

	/**
	 * This class copies an input files of a directory to another directory not
	 * include subdir
	 * 
	 * @param String
	 *            sourcedir the directory to copy from such as:/home/bqlr/images
	 * @param String
	 *            destdir the target directory
	 */
	public static void CopyDir(String sourcedir, String destdir)
			throws Exception {
		File dest = new File(destdir);
		File source = new File(sourcedir);

		String[] files = source.list();
		try {
			makehome(destdir);
		} catch (Exception ex) {
			throw new Exception("CopyDir:" + ex.getMessage());
		}

		for (int i = 0; i < files.length; i++) {
			String sourcefile = source + File.separator + files[i];
			String destfile = dest + File.separator + files[i];
			File temp = new File(sourcefile);
			if (temp.isFile()) {
				try {
					copy(sourcefile, destfile);
				} catch (Exception ex) {
					throw new Exception("CopyDir:" + ex.getMessage());
				}
			}
		}
	}

	/**
	 * This class del a directory recursively,that means delete all files and
	 * directorys.
	 * 
	 * @param File
	 *            directory the directory that will be deleted.
	 */
	public static void recursiveRemoveDir(File directory) throws Exception {
		if (!directory.exists())
			throw new IOException(directory.toString() + " do not exist!");

		String[] filelist = directory.list();
		File tmpFile = null;
		for (int i = 0; i < filelist.length; i++) {
			tmpFile = new File(directory.getAbsolutePath(), filelist[i]);
			if (tmpFile.isDirectory()) {
				recursiveRemoveDir(tmpFile);
			} else if (tmpFile.isFile()) {
				try {
					tmpFile.delete();
				} catch (Exception ex) {
					throw new Exception(tmpFile.toString()
							+ " can not be deleted " + ex.getMessage());
				}
			}
		}
		try {
			directory.delete();
		} catch (Exception ex) {
			throw new Exception(directory.toString() + " can not be deleted "
					+ ex.getMessage());
		} finally {
			filelist = null;
		}
	}

	// =======================================================================================

	/**
	 * com.jdon.sample.xxx.xml ==> com/jdon/sample/xxx.xml
	 * 
	 * @param filePathName
	 * @return filename's string
	 */
	public String getConfPathXmlFile(String filePathName) {
		int i = filePathName.lastIndexOf(".xml");
		String name = filePathName.substring(0, i);
		name = name.replace('.', '/');
		name += ".xml";
		return getConfFile(name);
	}

	/**
	 * same as getConfPathXmlFile
	 * 
	 * @param filePathName
	 * @return the InputStream intance
	 */
	public InputStream getConfPathXmlStream(String filePathName) {
		int i = filePathName.lastIndexOf(".xml");
		String name = filePathName.substring(0, i);
		name = name.replace('.', '/');
		name += ".xml";
		return getConfStream(name);
	}

	public String getConfFile(String fileName) {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		if (classLoader == null) {
			classLoader = getClass().getClassLoader();
		}
		URL confURL = classLoader.getResource(fileName);
		if (confURL == null)
			confURL = classLoader.getResource("META-INF/" + fileName);
		if (confURL == null) {
			return null;
		} else {
			File file1 = new File(confURL.getFile());
			if (file1.isFile()) {
				System.out.println(" locate file: " + confURL.getFile());
				return confURL.getFile();
			} else {
				System.err.println(" it is not a file: " + confURL.getFile());
				return null;
			}
		}
	}

	public InputStream getConfStream(String fileName) {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		if (classLoader == null) {
			classLoader = this.getClass().getClassLoader();
		}
		InputStream stream = classLoader.getResourceAsStream(fileName);
		if (stream == null)
			stream = classLoader.getResourceAsStream("META-INF/" + fileName);

		return stream;
	}

	/**
	 * 读取配置文件 数据
	 * 
	 * @param key
	 *            变量名
	 * @param args
	 *            第一个是 配置文件类路径 格式 com.file 省略.properties 第二个参数是 获取不到值时 的默认值
	 * @return
	 */
	public static String getProperties(String key, String... args) {
		String propertyPath = "httpLinkIP";
		String defaultValue = null;
		String result = null;

		for (int i = 0; i < args.length; i++) {
			if (StringUtils.isEmpty(args[i]))
				continue;
			if (0 == i)
				propertyPath = args[i];
			else if (1 == i)
				defaultValue = args[i];
		}

		ResourceBundle bundle = ResourceBundle.getBundle(propertyPath);
		try {
			result = bundle.getString(key);
		} catch (Exception e) {
			if (StringUtils.isEmpty(result) && null != defaultValue) {
				result = defaultValue;
			}
		}
		return result;
	}

	/**
	 * 格式化string
	 * 
	 * @param str
	 * @param param
	 * @return
	 */
	public static String stringFormat(String str, String[] param) {
		MessageFormat format = new MessageFormat(str);
		return format.format(param);
	}
	/**将上传的附件保存到磁盘，为防文件名冲突，使用保护
	 * @param formfile
	 * @param upfileFileName 
	 * @param uploadedFile*/
	 
	public synchronized static String writeFile(File formfile,String disFilePath,String fileType, String upfileFileName) {
		OutputStream output;
		try {
//			String url = disFilePath + Calendar.getInstance().getTimeInMillis() + fileType;
			String url = disFilePath + upfileFileName;
			
			File uploadFile = new File(url);
			
			InputStream input = new FileInputStream(formfile) ;
			
			output = new FileOutputStream(uploadFile);
			int read = 0;
			byte[] buffer = new byte[8192];
			while ((read = input.read(buffer, 0, 8192)) != -1)  
			{
				output.write(buffer, 0, read);
			}
			input.close();
			output.close();
			
			return url.replace("\\", "/");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	  /** *//** 
     * 把字节数组保存为一个文件 
     * @Author Sean.guo 
     * @EditTime 2007-8-13 上午11:45:56 
     */ 
    public synchronized static File getFileFromBytes(byte[] b, String outputFile){ 
        BufferedOutputStream stream = null; 
        File file = null; 
        try{ 
            file = new File(outputFile); 
            FileOutputStream fstream = new FileOutputStream(file); 
            stream = new BufferedOutputStream(fstream); 
            stream.write(b); 
        } catch (Exception e){ 
            e.printStackTrace(); 
        } finally{ 
            if (stream != null){ 
                try{ 
                    stream.close(); 
                } catch (IOException e1){ 
                    e1.printStackTrace(); 
                } 
            } 
        } 
        return file; 
    } 
    
}
