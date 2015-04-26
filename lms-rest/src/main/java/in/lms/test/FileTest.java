package in.lms.test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileTest {

	public static void main(String[] args) {
		String folder = "C:/Users/adutta/Deskto/office_work/lms/code/upload-folder";
		long courseId = 60;

		String fullpath = folder + File.separator + courseId;
		System.out.println(fullpath);
		File file = new File(fullpath);
		
		/*Path path = new Pa
		Files.exists(arg0, arg1)*/
		
		if(!file.isDirectory())
		{
			boolean flag = file.mkdir();
			System.out.println(flag);
		}
		
		System.out.println(file.exists());
	}

}
