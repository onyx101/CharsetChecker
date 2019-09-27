package csc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class CscService {
	
	public void perform(Options options) throws IOException {
		
		PrintStream os = System.out;
		if (options.getOutputFile() != null) {
			os = new PrintStream(options.getOutputFile());
		}
		
		try (FileInputStream fis = new FileInputStream(options.getInputFile());
				PrintStream stream = os;) {
		
			CharsetChecker charsetChecker = new CharsetChecker(StandardCharsets.UTF_8, fis);
			
			Error err = null;
			
			while ((err = charsetChecker.processByLine()) != null) {
				
				stream.println("Error found in line ["+err.getLineNr()+"] and column(s) ["+StringUtils.join(err.getColumnNr(), ',')+"]");
				stream.println(err.getLine());
				stream.println();
				
			}
			
		}
		
	}
	
}
