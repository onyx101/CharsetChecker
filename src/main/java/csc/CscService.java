package csc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class CscService {
	
	public void perform(Options options) throws IOException {
		
		PrintStream os = System.out;
		if (options.getOutputFile() != null) {
			os = new PrintStream(options.getOutputFile(), options.getEncoding());
		}
		
		try (FileInputStream fis = new FileInputStream(options.getInputFile());
				CharsetChecker charsetChecker = new CharsetChecker(Charset.forName(options.getEncoding()), fis)) {
		
			Error err = null;
			
			if (options.isLineBased()) {
				while ((err = charsetChecker.processByLine()) != null) {
					
					os.println("Error found in line ["+err.getLineNr()+"] and column(s) ["+StringUtils.join(err.getColumnNr(), ',')+"]");
					if (err.getLine() != null) {
						os.println(err.getLine());
					}
					os.println();
				}
			} else {
				while ((err = charsetChecker.processByCharacter()) != null) {
					os.println("Error found in line ["+err.getLineNr()+"] and column ["+StringUtils.join(err.getColumnNr(), ',')+"]");
				}
			}
			
		} finally {
			
			if (options.getOutputFile() != null) {
				os.close();
			}
			
		}
		
	}
	
}
