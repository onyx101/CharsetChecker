package csc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class CharsetChecker {
	
	private CharsetDecoder decoder;
	private int lineNr;
	
	private InputStream inputStream;
	
	private BufferedReader br;
	
	public CharsetChecker(Charset charSet, InputStream is) {
		decoder = charSet.newDecoder();
		decoder.onMalformedInput(CodingErrorAction.REPLACE);
		decoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
		
		inputStream = is;
		
		br = new BufferedReader(new InputStreamReader(inputStream, decoder));
		
		reset();
	}
	
	public void reset() {
		lineNr = 0;
	}
	
	public Error processByLine() throws IOException {
		
		String line;
		while ((line = br.readLine()) != null) {
			
			lineNr++;
			
			if (line.contains(decoder.replacement()) ) {
				
				Error err = new Error();
				err.setLine(line);
				err.setLineNr(lineNr);
				err.setColumnNr(new ArrayList<>());
				
				int idx = -1;
				while ((idx = line.indexOf(decoder.replacement(), idx+1)) != -1) {
					err.getColumnNr().add(idx+1);
				}
				return err;
			}
		}
		
		return null;
		
	}
	
}
