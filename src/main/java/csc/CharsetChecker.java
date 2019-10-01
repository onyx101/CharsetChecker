package csc;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

public class CharsetChecker implements Closeable {
	
	private CharsetDecoder decoder;
	private int lineNr;
	private int columnNr;
	
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
		columnNr = 0;
	}
	
	public Error processByCharacter() throws IOException {
		
		int ch;
		
		Error err = null;
		
		while ((ch = br.read()) != -1) {
			
			columnNr++;
			
			String str = new String(Character.toChars(ch));
			
			if ("\n".equals(str)) {
				lineNr++;
				columnNr = 0;
				continue;
			}
			
			if (decoder.replacement().equals(str)) {
				if (err == null) {
					err = new Error();
				}
				err.getColumnNr().add(columnNr);
				err.setLineNr(lineNr+1);
				return err;
			}
			
		}
		
		return err;
		
	}
	
	public Error processByLine() throws IOException {
		
		String line;
		while ((line = br.readLine()) != null) {
			
			lineNr++;
			
			if (line.contains(decoder.replacement()) ) {
				
				Error err = new Error();
				err.setLine(line);
				err.setLineNr(lineNr);
				
				int idx = -1;
				while ((idx = line.indexOf(decoder.replacement(), idx+1)) != -1) {
					err.getColumnNr().add(idx+1);
				}
				return err;
			}
		}
		
		return null;
		
	}

	@Override
	public void close() throws IOException {

		br.close();
		
	}
	
}
