package csc;

public class Options {
	
	private String inputFile;
	private String outputFile;
	private String encoding = "UTF-8";
	
	public boolean isValid() {
		if (inputFile == null) {
			System.out.println("You must give an input file with --i");
			return false;
		}
		
		return true;
	}
	
	public String getInputFile() {
		return inputFile;
	}
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	public String getOutputFile() {
		return outputFile;
	}
	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
