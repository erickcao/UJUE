package com.ujuit.udata.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * @Author dzy
 * @Desc 文件帮助文档
 * @Version 1.0
 */
public class FileUtil {

	/**
	 * @Author dzy
	 * @Desc 删除文件
	 * @param basePath
	 * @param filePath
	 * @return
	 */
	public static void deleteFile(String basePath, String filePath) {
		if (basePath != null && filePath != null && filePath != "") {
			File f = new File(basePath + filePath);
			f.delete();
		}

	}

	/**
	 * @description 删除单个文件
	 * @author Erick
	 * @date
	 * @param path
	 */
	public static void delFile(String path) {
		if (path != null) {
			File f = new File(path);
			f.delete();
		}
	}

	/**
	 * @Author dzy
	 * @Desc 删除文件
	 * @param basePath
	 * @param filePath
	 * @return
	 */
	public static void deleteFileS(String basePath, String[] filePaths) {
		if (filePaths != null) {
			for (int i = 0; i < filePaths.length; i++) {
				deleteFile(basePath, filePaths[i]);
			}
		}
	}

	/**
	 * @Description 获取某路径下的所有文件
	 * @return
	 * @param path
	 * @return
	 * @date 2017年5月26日
	 * @author Erick
	 */
	public static String[] getAllFiles(String path) {
		File f = new File(path);
		String[] sts = f.list();
		return sts;
	}

	/**
	 * 获取某文件夹当前级的所有文件夹
	 * 
	 * @param path
	 * @return
	 */
	public static ArrayList<File> getAllFilefolder(String path) {
		ArrayList<File> result = new ArrayList<File>();
		// 拿到文件夹句柄
		File f = new File(path);
		// 列出所有文件
		File[] files = f.listFiles();
		if (null != files && files.length > 0) {
			for (File file : files) {
				if (file.isDirectory()) {
					result.add(file);
				}
			}
		}
		return result;
	}

	/**
	 * 读取资源文件,资源文件一定要用此方法进行处理，否则打包之后会读取不到
	 * 
	 * @author Erick
	 * @param filePath
	 *            文件路径
	 * @param encoding
	 *            编码格式如iso8859-1
	 * @return
	 */
	public static String readResourceFile(String filePath, String encoding, Object obj) {
		String result = null;
		InputStream inStream = null;
		if (encoding == null) {
			encoding = "UTF-8";// 默认提供一个UTF-8
		}
		try {
			int byteread = 0;
			inStream = obj.getClass().getClassLoader().getResourceAsStream("check.json");
			byte[] buffer;
			try {
				buffer = new byte[inStream.available()];
				while ((byteread = inStream.read(buffer)) != -1) {

				}
				result = new String(buffer, "UTF-8");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 一次性读取所有字节，不知道会不会内存爆了
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inStream != null)
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return result;
	}

	/**
	 * 文件读取
	 * 
	 * @author Erick
	 * @param filePath
	 *            文件路径
	 * @param encoding
	 *            编码格式如iso8859-1
	 * @return
	 */
	public static String readFile(String filePath, String encoding) {
		String result = null;
		InputStream inStream = null;
		if (encoding == null) {
			encoding = "UTF-8";// 默认提供一个UTF-8
		}
		try {
			int byteread = 0;
			File oldfile = new File(filePath);
			if (oldfile != null && oldfile.exists()) {
				inStream = new FileInputStream(filePath);
				if (inStream != null) {
					byte[] buffer = new byte[inStream.available()];// 一次性读取所有字节，不知道会不会内存爆了
					while ((byteread = inStream.read(buffer)) != -1) {

					}
					result = new String(buffer, encoding);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inStream != null)
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return result;
	}

	/**
	 * @description 文件写入
	 * @author Erick
	 * @param filePath
	 * @param content
	 *            要写入的内容
	 * @param encoding
	 *            编码格式如iso8859-1
	 */
	public static void writeFile(String filePath, String content, String encoding) {
		if (filePath == null) {
			return;
		}
		if (encoding == null) {
			encoding = "UTF-8";// 默认提供一个UTF-8
		}
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream(filePath);
			fs.write(content.getBytes(encoding));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fs != null)
					fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Descripiton 拷贝文件
	 * @author Erick
	 * @param sourcePath
	 * @param destPath
	 */
	public static void copyFile(String sourcePath, String destPath) {
		// File source, File dest
		File source = new File(sourcePath);
		File dest = new File(destPath);

		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @description
	 * @author Erick
	 * @date
	 * @param sourceFile
	 * @param destpath
	 */
	public static void moveFile(String sourceFile, String destpath) {
		try {
			File afile = new File(sourceFile);
			if (afile.renameTo(new File(destpath + afile.getName()))) {
			} else {
				System.out.println("File is failed to move!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description 检查文件是否存在
	 * @return
	 * @param path
	 * @return
	 * @date 2017年5月26日
	 * @author Erick
	 */
	public static boolean exists(String path) {
		File file = new File(path);
		return file.exists();
	}

	/**
	 * @description 根据路径创建文件夹
	 * @author Erick
	 * @date
	 */
	public static void mkDir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

}
