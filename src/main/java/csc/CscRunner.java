package csc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CscRunner implements ApplicationRunner {

	@Autowired
	private CscService cscService;

	private void help() {
		System.out.println("CharsetChecker validates files for corerct charset encoding.");
		System.out.println("Errors and their location are reported.");
		System.out.println();
		System.out.println("--help\t\t\tprint help");
		System.out.println("--version\t\tprint version");
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

	@Override
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
