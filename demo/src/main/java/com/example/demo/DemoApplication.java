package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		// Load .env file (if present) into system properties so Spring can resolve ${MONGODB_URI}
		try {
			java.nio.file.Path envPath = java.nio.file.Paths.get(".env");
			if (java.nio.file.Files.exists(envPath)) {
				java.util.List<String> lines = java.nio.file.Files.readAllLines(envPath);
				for (String line : lines) {
					line = line.trim();
					if (line.isEmpty() || line.startsWith("#") || !line.contains("=")) continue;
					int idx = line.indexOf('=');
					String key = line.substring(0, idx).trim();
					String val = line.substring(idx + 1).trim();
					if ((val.startsWith("\"") && val.endsWith("\"")) || (val.startsWith("'") && val.endsWith("'"))) {
						val = val.substring(1, val.length() - 1);
					}
					if (!key.isEmpty() && !val.isEmpty()) {
						System.setProperty(key, val);
					}
				}
			}
		} catch (Exception e) {
			// ignore any errors reading .env; fall back to environment/system properties
		}
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ProductService ps) {
		return args -> {
			String uri = System.getProperty("MONGODB_URI");
			if (uri == null || uri.isBlank()) uri = System.getenv("MONGODB_URI");
			System.out.println("MongoDB URI: " + maskUri(uri));
			try {
				ps.initSampleData();
			} catch (Exception e) {
				System.err.println("Warning: failed to initialize sample data: " + e.getMessage());
				e.printStackTrace();
			}
		};
	}

	private static String maskUri(String uri) {
		if (uri == null) return "(not set)";
		try {
			int at = uri.indexOf('@');
			if (at > 0) {
				int sl = uri.indexOf("//");
				if (sl >= 0 && sl < at) {
					String prefix = uri.substring(0, sl + 2);
					String rest = uri.substring(at + 1);
					return prefix + "*****@" + rest;
				}
				return "*****@" + uri.substring(at + 1);
			}
			return uri;
		} catch (Exception e) {
			return "(masked)";
		}
	}

}
