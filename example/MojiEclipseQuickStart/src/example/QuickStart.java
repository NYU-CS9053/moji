package example;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import it.zielke.moji.SocketClient;

import java.io.File;
import java.net.URL;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

enum Language {
	JAVA (new String[] {"java"}, "java"),
	PYTHON (new String[] {"py"}, "python"),
	C (new String[] {"c"}, "c"),
	CPP (new String[] {"cpp", "h"}, "cc"),
	CSHARP (new String[] {"cs"}, "csharp"),
	JAVASCRIPT (new String[] {"js"}, "javascript");

	private final String[] extention;
	private final String name;
	Language(String[] extention, String name){
		this.extention = extention;
		this.name = name;
	}

	public String[] getExtention() {
		return extention;
	}

	public String getName() {
		return name;
	}
}

public class QuickStart {
	//all you need to do is change these constant
	private final static Language LANGUAGE = Language.CPP;
	private final static String SOLUTION_DIRECTORY = "/Users/pyq/Documents/Fall-2014/Assignment_week1";
	private final static String BASE_DIRECTORY = "/Users/pyq/Documents/sh/moss-base-dir/week1";

	//argument is the moss user ID

	public static void main(String[] args) throws Exception {
		// a list of students' source code files located in the prepared
		// directory.

		Collection<File> files = FileUtils.listFiles(new File(
				SOLUTION_DIRECTORY), LANGUAGE.getExtention(), true);

		// a list of base files that was given to the students for this
		// assignment.
		Collection<File> baseFiles = FileUtils.listFiles(new File(
				BASE_DIRECTORY), LANGUAGE.getExtention(), true);

		System.out.println("solution file is "+files.size());
		System.out.println("base file is "+baseFiles.size());

		// get a new socket client to communicate with the MOSS server
		// and set its parameters.
		SocketClient socketClient = new SocketClient();

		// set your MOSS user ID
		socketClient.setUserID(args[0]);
		// socketClient.setOpt...

		// set the programming language of all student source codes
		socketClient.setLanguage(LANGUAGE.getName());

		// initialize connection and send parameters
		socketClient.run();

		// upload all base files
		for (File f : baseFiles) {
			socketClient.uploadBaseFile(f);
		}

		// upload all source files of students
		for (File f : files) {
			socketClient.uploadFile(f);
		}

		// finished uploading, tell server to check files
		socketClient.sendQuery();

		// get URL with MOSS results and do something with it
		URL results = socketClient.getResultURL();
		System.out.println("Results available at " + results.toString());
	}
}
