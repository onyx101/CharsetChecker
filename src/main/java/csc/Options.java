package csc;

public class Options {
	
	private String inputFile;
	private String outputFile;
	
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

}
