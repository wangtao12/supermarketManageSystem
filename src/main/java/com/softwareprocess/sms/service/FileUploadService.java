package com.softwareprocess.sms.service;



import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.softwareprocess.sms.tools.DateUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * 文件上传服务 Created by Allen on 2017/4/26.
 */
@Service
public class FileUploadService {

	/**
	 * 存文件
	 *
	 * @param files
	 *            文件数组
	 * @param folder
	 *            子文件夹
	 * @param fileDir
	 *            上传路径
	 * @param maxNumber
	 *            最大文件数
	 * @param maxSize
	 *            单文件最大大小（KB）
	 * @param fileTypes
	 *            文件类型限制，为空不限制
	 * @return 文件夹路径（配置文件中的上传路径+子文件夹）
	 * @throws Exception
	 *             typeError 类型错误 numberError 数量错误 sizeError 大小错误
	 *
	 */
	public String fileUpload(MultipartFile[] files, String folder, String fileDir, int maxNumber, long maxSize,
			String[] fileTypes) throws Exception {
		String dirPath = "";
		DateUtil dateUtil = new DateUtil();
		if (files != null && files.length > 0) {
			if (files.length <= maxNumber) {
				dirPath = fileDir + folder + "/"; // 定义文件夹路径
				File dir = new File(dirPath);
				if (!dir.exists()) { // 如果目录不存在就创建目录
					dir.mkdirs();
				}
				// 循环获取file数组中得文件
				for (int i = 0; i < files.length; i++) {
					MultipartFile file = files[i];

					String originalFileName = file.getOriginalFilename();// 文件名
					long fileSize = file.getSize();// 文件大小
					String fileType = originalFileName.split("\\.")[originalFileName.split("\\.").length - 1];// 文件扩展名

					// 上传文件类型判断
					if (fileTypes != null && fileTypes.length > 0) {
						List<String> fileTypesList = Arrays.asList(fileTypes);
						if (!fileTypesList.contains(fileType)) {
							throw new Exception("typeError");
						}
					}

					// 文件大小判断
					if ((fileSize / 1024) > maxSize) {
						throw new Exception("sizeError");
					}

					// 保存文件
					// 判断文件是否为空
					if (!file.isEmpty()) {
						try {
							// 文件保存路径
							String filePath = dirPath + folder + dateUtil.getCurrentTime("yyMMddhhmmss")
									+ originalFileName;
							// 转存文件
							file.transferTo(new File(filePath));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				throw new Exception("numberError");
			}
		}
		return dirPath;
	}

	/**
	 * 存文件
	 *
	 * @param file
	 *            文件
	 * @param folder
	 *            子文件夹
	 * @param fileDir
	 *            上传路径
	 * @param maxSize
	 *            单文件最大大小（KB）
	 * @param fileTypes
	 *            文件类型限制，为空不限制
	 * @return 文件路径（配置文件中的上传路径+子文件夹+文件名）
	 * @throws Exception
	 *             typeError 类型错误 numberError 数量错误 sizeError 大小错误
	 *
	 */
	public String fileSingleUpload(MultipartFile file, String folder, String fileDir, long maxSize, String[] fileTypes)
			throws Exception {
		String dirPath = "";
		String uploadFileName = "";
		DateUtil dateUtil = new DateUtil();
		if (file != null) {
			dirPath = fileDir + folder + "/"; // 定义文件夹路径
			File dir = new File(dirPath);
			if (!dir.exists()) { // 如果目录不存在就创建目录
				dir.mkdirs();
			}
			String originalFileName = file.getOriginalFilename();// 文件名
			long fileSize = file.getSize();// 文件大小
			String fileType = originalFileName.split("\\.")[originalFileName.split("\\.").length - 1];// 文件扩展名
			// 上传文件类型判断
			if (fileTypes != null && fileTypes.length > 0) {
				List<String> fileTypesList = Arrays.asList(fileTypes);
				if (!fileTypesList.contains(fileType)) {
					throw new Exception("typeError");
				}
			}
			// 文件大小判断
			if ((fileSize / 1024) > maxSize) {
				throw new Exception("sizeError");
			}
			// 保存文件
			// 判断文件是否为空
			if (!file.isEmpty()) {
				try {
					// 文件保存路径
					uploadFileName = folder + dateUtil.getCurrentTime("yyMMddhhmmss") + originalFileName;
					String localPath = dirPath + uploadFileName;
					// 转存文件
					file.transferTo(new File(localPath));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return folder+"/"+uploadFileName;
	}

	/**
	 * 检验文件格式
	 * 
	 * @param files
	 *            文件数组
	 * @param folder
	 *            子文件夹
	 * @param fileDir
	 *            上传路径
	 * @param maxNumber
	 *            最大文件数
	 * @param maxSize
	 *            单文件最大大小（KB）
	 * @param fileTypes
	 *            文件类型限制，为空不限制
	 * @return
	 * @throws Exception
	 */
	public String checkFileUpload(MultipartFile[] files, String folder, String fileDir, int maxNumber, long maxSize,
			String[] fileTypes) throws Exception {
		if (files != null && files.length > 0) {
			if (files.length <= maxNumber) {
				// 循环获取file数组中得文件
				for (int i = 0; i < files.length; i++) {
					MultipartFile file = files[i];

					String originalFileName = file.getOriginalFilename();// 文件名
					long fileSize = file.getSize();// 文件大小
					String fileType = originalFileName.split("\\.")[originalFileName.split("\\.").length - 1];// 文件扩展名

					// 上传文件类型判断
					if (fileTypes != null && fileTypes.length > 0) {
						List<String> fileTypesList = Arrays.asList(fileTypes);
						if (!fileTypesList.contains(fileType)) {
							return "typeError";
						}
					}

					// 文件大小判断
					if ((fileSize / 1024) > maxSize) {
						return "sizeError";
					}
				}
				return "success";
			} else {
				return "numberError";
			}
		}
		return "success";
	}
}
