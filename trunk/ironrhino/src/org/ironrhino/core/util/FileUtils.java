package org.ironrhino.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

public class FileUtils {

	public static File zip(File file) throws Exception {
		return zip(file, null);
	}

	public static File zip(File file, File zipFile) throws Exception {
		if (zipFile == null)
			zipFile = defaultZipFileName(file);
		if (!file.exists() || !file.canRead())
			throw new RuntimeException(file + "doesn't exists or cannot read");
		if (file.isDirectory()) {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					zipFile));
			zipDirctory(out, file, "");
			out.close();
		} else {
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			byte[] buf = new byte[1024];
			int len;
			FileOutputStream fos = new FileOutputStream(zipFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			ZipOutputStream zos = new ZipOutputStream(bos);
			ZipEntry ze = new ZipEntry(file.getName());
			zos.putNextEntry(ze);
			while ((len = bis.read(buf)) != -1) {
				zos.write(buf, 0, len);
				zos.flush();
			}
			bis.close();
			zos.close();
		}
		return zipFile;
	}

	private static File defaultZipFileName(File file) {
		String zipFile;
		if (file.isDirectory()) {
			zipFile = file.getAbsolutePath();
			if (zipFile.endsWith("/") || zipFile.endsWith("\\"))
				zipFile = zipFile.substring(0, zipFile.length() - 1);
			zipFile += ".zip";
		} else {
			zipFile = file + ".zip";
		}
		return new File(zipFile);
	}

	private static void zipDirctory(ZipOutputStream out, File file, String base)
			throws Exception {
		if (file.isDirectory()) {
			File[] fl = file.listFiles();
			if (!base.equals(""))
				out.putNextEntry(new ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				zipDirctory(out, fl[i], base + fl[i].getName());
			}
		} else {
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(file);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
		}
	}

	public static Map<String, String> parseManifestFile(File jarfile) {
		InputStream input = null;
		try {
			JarFile jar = new JarFile(jarfile);
			JarEntry entry = jar.getJarEntry(JarFile.MANIFEST_NAME);
			if (entry == null || entry.isDirectory())
				return null;
			input = jar.getInputStream(entry);
			List<String> list = IOUtils.readLines(input);
			Map<String, String> map = new LinkedHashMap<String, String>(
					list.size());
			for (String line : list) {
				if (line.trim().equals(""))
					continue;
				String[] arr = line.split(":", 2);
				map.put(arr[0].trim(), arr.length > 1 ? arr[1].trim() : null);
			}
			return map;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

}
