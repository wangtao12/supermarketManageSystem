package com.softwareprocess.sms.tools;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 文件相关操作
 * @author yangyue
 *
 */
public class FileOptUtil {
	
	/**
	 * 从页面上传文件
	 * @param request
	 * @param realPath 存储路径
	 * @file fileName null为保持默认，多文件时禁用
	 * @param size 文件大小 1024*1024=1M
	 * @return
	 */
	public boolean uploadFile(HttpServletRequest request,String realPath,String fileName,long size){
		createFolder(realPath);
		boolean result=true;
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(size);//设置大小
			List<FileItem> items = null;
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
				result=false;
			}
			for (FileItem item : items) {
				if (!item.isFormField()) {
					File fullFile = new File(item.getName());
					if(fileName==null){
						if (item.getName().lastIndexOf("\\") != -1) {
							fileName = item.getName().substring(item.getName().lastIndexOf("\\") + 1, item.getName().length());
						} else {
							fileName = fullFile.getName();
						}
					}
					File uploadFile = new File(realPath, fileName);
					try {
						item.write(uploadFile);
					} catch (Exception e) {
						e.printStackTrace();
						result=false;
					}
				}
			}
		} catch (Exception e) {
			result=false;
		}
		return result;
	}
	
	/**
	 * 图片上传用
	 * @param request
	 * @param realPath
	 * @param fileName
	 * @param size
	 * @return
	 */
	public boolean uploadPicFile(HttpServletRequest request,String realPath,String fileName,long size){
		createFolder(realPath);
		boolean result=true;
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(size);//设置大小
			List<FileItem> items = null;
			items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (!item.isFormField()) {
					try {
						File uploadFile = new File(realPath, fileName);
						FileOutputStream fstream = new FileOutputStream(uploadFile);
						BufferedOutputStream stream = new BufferedOutputStream(fstream);
			            stream.write(item.get());
			            convertPicFormat(uploadFile,"png");
						uploadFile.createNewFile();
						if (stream != null) {
			                try {
			                    stream.close();
			                } catch (IOException e1) {
			                    e1.printStackTrace();
			                }
						}
					} catch (Exception e) {
						e.printStackTrace();
						result=false;
					}
				}
			}
		} catch (Exception e) {
			result=false;
		}
		return result;
	}
	
	/**
	 * 图像格式转换
	 * @param source 输入文件
	 * @param formatName 要转换格式
	 */
	public void convertPicFormat(File source, String formatName) {  
        try {  
        	source.canRead();  
            BufferedImage src = ImageIO.read(source);  
            ImageIO.write(src, formatName, source);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
	
	/**
	 * 判断路径是否存在
	 * @param path
	 * @return
	 */
	public boolean checkUrl(String path) {
		File file = new File(path);
		if (file.exists()) {
			//file.mkdir();
			return true;
		}
		return false;
	}
	
	/**
	 * 文件是否存在
	 * @param path 路径
	 * @return 是否存在
	 * @throws Exception
	 */
	public boolean isExist(String path) throws Exception {
		File f = new File(path);
		return f.exists();
	}
	
	/**
	 * 按路径得到文件信息
	 * @param path 路径  name：名称  absolutePath：绝对路径   time：时长
	 * @return 文件信息
	 * @throws Exception
	 */
	public List<Map<String, String>> getFileInfoList(String path) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		File f = new File(path);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String[] temp = f.list();
		if (temp == null) {
			return null;
		}
		for (int i = 0; i < temp.length; i++) {
			File file = new File(f.getAbsolutePath() + "/" + temp[i]);
			if (file.isFile()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", file.getName());
				map.put("absolutePath", file.getAbsolutePath());
				map.put("time", sf.format(new Date(file.lastModified())));
				list.add(map);
			}
		}
		return list;
	}
	
	/**
	 * 按路径得到文件信息(包含目录)
	 * @param path 路径  name：名称  absolutePath：绝对路径   time：时长
	 * @return 文件信息
	 * @throws Exception
	 */
	public List<Map<String, String>> getFilesInfoList(String path) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		File f = new File(path);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String[] temp = f.list();
		if (temp == null) {
			return null;
		}
		for (int i = 0; i < temp.length; i++) {
			File file = new File(f.getAbsolutePath() + "/" + temp[i]);
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", file.getName());
			map.put("absolutePath", file.getAbsolutePath());
			map.put("time", sf.format(new Date(file.lastModified())));
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 拷贝文件s到t.
	 * @param s　源文件.
	 * @param t　新文件.
	 */
	public void fileChannelCopy(String s, String t) {
	    FileInputStream fi = null;
	    FileOutputStream fo = null;
	    FileChannel in = null;
	    FileChannel out = null;
	    File f1 = new File(s);
		File f2 = new File(t);
		try {
			if (!f2.getParentFile().exists()) {
				f2.getParentFile().mkdirs();
	        }
			f2.createNewFile();
	        fi = new FileInputStream(f1);
	        fo = new FileOutputStream(f2);
	        in = fi.getChannel();//得到对应的文件通道
	        out = fo.getChannel();//得到对应的文件通道
	        in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            fi.close();
	            in.close();
	            fo.close();
	            out.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	/**
	 * 复制文件
	 * @param oldPath 源文件
	 * @param newPath 目的文件
	 * @throws Exception
	 */
	public void copyFile(String oldPath, String newPath) throws Exception {
		File f1 = new File(oldPath);
		File f2 = new File(newPath);

		int length = 2097152;
		FileInputStream in = new FileInputStream(f1);
		FileOutputStream out = new FileOutputStream(f2);
		byte[] buffer = new byte[length];

		while (true) {
			int ins = in.read(buffer);
			if (ins == -1) {
				in.close();
				out.flush();
				out.close();
				break;
			} else
				out.write(buffer, 0, ins);
		}

	}
	
	/**
	 * 删除文件.
	 * @param path
	 */
	public boolean deleteFile(String path) {
		boolean flag = false;
	    File file = new File(path);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}
	
	/**
	 * 读取文件
	 * @param path 文件路径
	 * @return
	 * @throws Exception
	 */
	public String readFile(String path,String charset) throws Exception {
		if(!new File(path).exists()) {
			return "";
		}
		InputStreamReader isr = new InputStreamReader(new FileInputStream(path), charset);
		BufferedReader br = new BufferedReader(isr);
		String temp = "";
		String content = "";
		while ((temp = br.readLine()) != null) {
			content = content + temp + "\n";
		}
		return content;
	}
	
	/**
	 * 把list保存为csv文件
	 * @param list 要保存的list
	 * @param fields list里面的map字段名
	 * @param file 保存的文件名
	 * @throws Exception
	 */
	public void saveCSVList(List<Map<String, Object>> list, String[] fields, String[] fieldType, String file) throws Exception {
		delFile(file);
		File f = new File(new File(file).getParent());
		if (!f.exists()) {
			f.mkdirs();
		}
		f = new File(file);
		if (!f.exists()) {
			f.createNewFile();
		}
		List<String> csvList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> tempMap = list.get(i);
			String tempStr = "";
			for (int j = 0; j < fields.length; j++) {
				if (j == 0) {
					if (fieldType[j].equals("String")) {
						tempStr = tempStr + "\"" + tempMap.get(fields[j]) + "\"";
					} else {
						tempStr = tempStr + tempMap.get(fields[j]);
					}
				} else {
					if (fieldType[j].equals("String")) {
						tempStr = tempStr + ",\"" + tempMap.get(fields[j]) + "\"";
					} else {
						tempStr = tempStr + "," + tempMap.get(fields[j]);
					}
				}
			}
			csvList.add(tempStr);
			if (csvList.size() == 10000) {
				this.saveFileListAppendNoCheck(csvList, file);
				csvList.clear();
			}
		}
		this.saveFileListAppendNoCheck(csvList, file);
	}
	
	public static void delFile(String path) {
		System.out.println("path="+path);
		if(path !=null) {
			File file = new File(path);
			file.delete();
		}
	}
	
	public void saveFileListAppendNoCheck(List<String> list, String filepath) throws Exception {
		StringBuffer content = new StringBuffer();
		int index = 0;
		for (int i = 0; i < list.size(); i++) {
			content.append(list.get(i).toString()).append("\n");
			index ++;
			if(index==10000)
			{
				this.appendMethodNoCheck(filepath,content.toString());
				index = 0;
				content.setLength(0);
			}
		}
		if(content.length()!=0) {
			this.appendMethodNoCheck(filepath,content.toString());
		}
	}
	
	public void appendMethodNoCheck(String fileName, String content) {
		try {
			RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
			long fileLength = randomFile.length();
			randomFile.seek(fileLength);
			randomFile.write(content.getBytes("GBK"));
			randomFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存文件
	 * @param value String[]数据格式
	 * @param filepath 保存文件路径
	 * @throws Exception
	 */
	public void saveFileFromArray(String[] value,String filepath) throws Exception {
		String content = "";
		for (int i = 0; i < value.length; i++) {
			content = content + value[i] + "\n";
		}
		saveFile(content,filepath,"GBK");
	}
	public void saveFile(String content, String filepath, String encoding) throws IOException {
		File f = new File(filepath);
		String path = f.getParent();
		f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		f = new File(filepath);
		FileOutputStream fileoutstream;
		OutputStreamWriter outputstreamwriter;
		BufferedWriter bufferedwriter;
		File file = new File(filepath);
		fileoutstream = new FileOutputStream(file);
		outputstreamwriter = new OutputStreamWriter(fileoutstream, encoding);
		bufferedwriter = new BufferedWriter(outputstreamwriter);
		bufferedwriter.write(content);
		bufferedwriter.close();
		outputstreamwriter.close();
		fileoutstream.close();
	}
	
	/**
	 * 输入文件夹路径，如果不存在就创建他
	 * @param path
	 */
	public void createFolder(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
	/**
	 * 获取指定路径下的所有文件列表
	 * @param dir 要查找的目录
	 * @return
	 */
	public List<String> getFileList(String dir) {
	    List<String> listFile = new ArrayList<String>();
	    File dirFile = new File(dir);
	    //如果不是目录文件，则直接返回
	    if (dirFile.isDirectory()) {
	        //获得文件夹下的文件列表，然后根据文件类型分别处理
	        File[] files = dirFile.listFiles();
	        if (null != files && files.length > 0) {
	            //根据时间排序
	            Arrays.sort(files, new Comparator<File>() {
	                public int compare(File f1, File f2) {
	                    return (int) (f1.lastModified() - f2.lastModified());
	                }
	                public boolean equals(Object obj) {
	                    return true;
	                }
	            });
	            for (File file : files) {
	                //如果不是目录，直接添加
	                if (!file.isDirectory()) {
	                    listFile.add(file.getAbsolutePath());
	                } else {
	                    //对于目录文件，递归调用
	                    listFile.addAll(getFileList(file.getAbsolutePath()));
	                }
	            }
	        }
	    }
	    return listFile;
	}
	
	/** 
	 * 删除目录 
	 * @param path 路径 
	 * @return  true 成功执行删除 
	 */  
	public boolean deleteDirectory(String path) {  
	    if (!path.endsWith(File.separator)) {  
	    	path = path + File.separator;  
	    }  
	    File dirFile = new File(path);  
	    if (!dirFile.exists() || !dirFile.isDirectory()) {  
	        return false;  
	    }  
	    boolean flag = true;  
	    //删除文件夹下的所有文件(包括子目录)  
	    File[] files = dirFile.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        //删除子文件  
	        if (files[i].isFile()) {  
	            flag = deleteFile(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        } //删除子目录  
	        else {  
	            flag = deleteDirectory(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }  
	    }  
	    if (!flag) return false;  
	    //删除当前目录  
	    if (dirFile.delete()) {  
	        return true;  
	    } else {  
	        return false;  
	    }  
	}
	
	/**
	 * 得到一个BufferedReader
	 * @param filepath 文件名
	 * @param charSet 字符集
	 * @return
	 * @throws Exception
	 */
	public BufferedReader getBufferedReader(String filepath, String charSet) throws Exception {
		return new BufferedReader(new InputStreamReader(new FileInputStream(filepath), charSet));
	}
	
	/**
	 * 文件下载
	 * @param response
	 * @param request
	 * @param path 路径
	 * @param displayname 名称
	 * @throws Exception
	 */
	public void download(HttpServletResponse response,HttpServletRequest request, String path,
			String displayname) throws Exception {
		response.reset();
		response.setContentType("application/x-download");
		String filenamedownload =  path;
		String filenamedisplay = displayname;
		filenamedisplay = URLEncoder.encode(filenamedisplay, "UTF-8");
		filenamedisplay = filenamedisplay.replace("+", "%20");
		response.addHeader("Content-Disposition", "attachment;filename=" + filenamedisplay);
		OutputStream output = null;
		FileInputStream fis = null;
		try {
			output = response.getOutputStream();
			fis = new FileInputStream(filenamedownload);
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = fis.read(b)) > 0) {
				output.write(b, 0, i);
			}
			output.flush();
		} catch (Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
				fis = null;
			}
			if (output != null) {
				output.close();
				output = null;
			}
		}
	}
	
		
}
