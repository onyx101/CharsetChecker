package csc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CscRunner implements CommandLineRunner {

	@Autowired
	private CscService cscService;

	private void help() {
		System.out.println("CharsetChecker validates files for corerct charset encoding.");
		System.out.println("Errors and their location are reported.");
		System.out.println();
		System.out.println("--help\t\t\tprint help");
		System.out.println("--version\t\tprint version");
		System.out.println("--char\t\t\tcharacter based parsing");
		System.out.println("--enc=\t\t\tencoding, default UTF-8");
		System.out.println("--i=\t\t\tinput file");
		System.out.println("[--o=]\t\t\toutput file");
	}
	
	private void version() throws IOException {
		
		try (InputStream is = getClass().getResourceAsStream("/META-INF/maven/csc/csc/pom.properties")) {
			Properties props = new Properties();
			props.load(is);
			String version = props.getProperty("version");
			if (version == null) {
				version = "n/a";
			}
			System.out.println(version);
		}
		
	}
	
	private void help2(org.apache.commons.cli.Options opts) {
		HelpFormatter hf = new HelpFormatter();
		hf.printHelp("csc", opts);
	}
	
	public void run(String... args) throws Exception {
		
		Options options = new Options();
		
		org.apache.commons.cli.Options opts = new org.apache.commons.cli.Options();
		
		Option help = new Option("h", "help", false, "print help");
		Option version = new Option("v", "version", false, "print version");
		Option ch = new Option("c", "char", false, "character based parsing");
		Option enc = new Option("e", "enc", true, "encoding, default UTF8");
		Option input = new Option("i", "input", true, "input file");
		Option output = new Option("o", "output", true, "output file");
		
		opts.addOption(help);
		opts.addOption(version);
		opts.addOption(ch);
		opts.addOption(enc);
		opts.addOption(input);
		opts.addOption(output);
		
		CommandLineParser clp = new DefaultParser();
		
		try {
		
			CommandLine cl = clp.parse(opts, args);
			
			if (cl.hasOption("help")) {
				help2(opts);
				return;
			}
			
			if (cl.hasOption("version")) {
				version();
				return;
			}
			
			if (cl.hasOption("char")) {
				options.setLineBased(false);
			}
			
			if (cl.hasOption("enc")) {
				options.setEncoding(cl.getOptionValue("enc"));
			}
			
			if (cl.hasOption("i")) {
				options.setInputFile(cl.getOptionValue("i"));
			}
			
			if (cl.hasOption("o")) {
				options.setOutputFile(cl.getOptionValue("o"));
			}
			
			if (options.isValid()) {
				cscService.perform(options);
			} else {
				help2(opts);
			}
			
		} catch (ParseException pe) {
			System.out.print("Error: "+pe.getMessage());
		}
		
		
	}

	public void run(ApplicationArguments args) throws Exception {

		Options options = new Options();

		if (args.containsOption("help") || args.getOptionNames().size() == 0) {
			help();
			return;
		}
		
		if (args.containsOption("version")) {
			version();
			return;
		}
		
		if (args.containsOption("char")) {
			options.setLineBased(false);
		}
		
		if (args.containsOption("i")) {
			if (args.getOptionValues("i").size() != 1) {
				System.out.println("Error with argument 'i'");
				return;
			} else {
				options.setInputFile(args.getOptionValues("i").get(0));
			}
		}

		if (args.containsOption("o")) {
			if (args.getOptionValues("o").size() != 1) {
				System.out.println("Error with argument 'o'");
				return;
			} else {
				options.setOutputFile(args.getOptionValues("o").get(0));
			}
		}
		
		if (args.containsOption("enc")) {
			if (args.getOptionValues("enc").size() != 1) {
				System.out.println("Error with argument 'enc'");
				return;
			} else {
				options.setEncoding(args.getOptionValues("enc").get(0));
			}
		}
		
		if (options.isValid()) {
			cscService.perform(options);
		}

		else {

			help();

		}

	}

	public static void main(String[] args) throws Exception {

		SpringApplication.run(CscRunner.class, args);

	}

}
