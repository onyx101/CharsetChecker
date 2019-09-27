package csc;

import java.util.List;

public class Error {
	
	private String line;
	private int lineNr;
	private List<Integer> columnNr;
	
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public int getLineNr() {
		return lineNr;
	}
	public void setLineNr(int lineNr) {
		this.lineNr = lineNr;
	}
	public List<Integer> getColumnNr() {
		return columnNr;
	}
	public void setColumnNr(List<Integer> columnNr) {
		this.columnNr = columnNr;
	}

}
