package Pages;


import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import io.github.bonigarcia.wdm.WebDriverManager;

public class Basepage {
        public static ChromeDriver driver;

     
        public void setUp() {
        String chromeMajor = null;

        // Try to detect installed Chrome major version on Windows
        try {
            String[] possible = new String[] {
                    "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
                    "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe"
            };
            String chromePath = null;
            for (String p : possible) {
                java.nio.file.Path pp = java.nio.file.Paths.get(p);
                if (java.nio.file.Files.exists(pp)) {
                    chromePath = p;
                    break;
                }
            }

            if (chromePath != null) {
                ProcessBuilder pb = new ProcessBuilder("powershell.exe", "-NoProfile", "-Command",
                        "(Get-Item \"" + chromePath + "\").VersionInfo.ProductVersion");
                pb.redirectErrorStream(true);
                Process proc = pb.start();
                try (java.io.BufferedReader r = new java.io.BufferedReader(new java.io.InputStreamReader(proc.getInputStream()))) {
                    String line;
                    while ((line = r.readLine()) != null) {
                        if (!line.isBlank()) {
                            String[] parts = line.trim().split("\\.");
                            if (parts.length > 0) {
                                chromeMajor = parts[0];
                            }
                            break;
                        }
                    }
                }
                proc.waitFor();
            }
        } catch (Exception ignored) {
            // ignore and let WebDriverManager handle detection
        }

        // ChromeOptions
        // headless can be toggled via system property -Dheadless=false or environment variable HEADLESS=false
        String headlessProp = System.getProperty("headless", System.getenv().getOrDefault("HEADLESS", "true"));
        boolean headless = Boolean.parseBoolean(headlessProp);

        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        // Use WebDriverManager to resolve and create the WebDriver instance.
        try {
            io.github.bonigarcia.wdm.WebDriverManager wdm = WebDriverManager.chromedriver();
            if (chromeMajor != null) {
                wdm = wdm.browserVersion(chromeMajor);
            }
            // Clear cache to avoid using an outdated driver, then create
            wdm.clearDriverCache();
            driver = (ChromeDriver) wdm.capabilities(options).create();
            // Register a shutdown hook once to ensure driver quits even if TestNG fails to call cleanup
            registerShutdownHook();
        } catch (Exception e) {
            // Fallback: normal setup and direct ChromeDriver
            try {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(options);
                registerShutdownHook();
            } catch (Exception ex) {
                throw new RuntimeException("Failed to initialize ChromeDriver", ex);
            }
        }
    }

    private static volatile boolean shutdownHookRegistered = false;

    private static synchronized void registerShutdownHook() {
        if (shutdownHookRegistered) {
            return;
        }
        shutdownHookRegistered = true;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                quitDriver();
            } catch (Throwable ignored) {
                // ignore
            }
        }));
    }

    /**
     * Quit and null the shared driver safely.
     */
    public static synchronized void quitDriver() {
        try {
            if (driver != null) {
                try {
                    driver.quit();
                } catch (Throwable t) {
                    // swallow, but attempt to destroy driver process if possible
                    try {
                        driver.close();
                    } catch (Throwable ignored) {
                    }
                }
                driver = null;
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * Return a configured URL if provided via system property (-DbaseUrl=...) or
     * environment variable BASE_URL, otherwise return the provided fallback.
     */
    public static String getUrl(String fallback) {
        // Priority: -DbaseUrl > BASE_URL env var > -Denv (look up in environments.json) > fallback
        String prop = System.getProperty("baseUrl");
        if (prop != null && !prop.isBlank()) {
            return prop;
        }

        prop = System.getenv("BASE_URL");
        if (prop != null && !prop.isBlank()) {
            return prop;
        }

        // If an environment name is provided, read the JSON config
        String env = System.getProperty("env");
        if (env == null || env.isBlank()) {
            env = System.getenv("ENV");
        }
        if (env != null && !env.isBlank()) {
            try (java.io.InputStream is = Basepage.class.getClassLoader().getResourceAsStream("config/environments.json")) {
                if (is != null) {
                    java.io.Reader r = new java.io.InputStreamReader(is);
                    com.google.gson.JsonObject root = com.google.gson.JsonParser.parseReader(r).getAsJsonObject();
                    if (root.has(env)) {
                        com.google.gson.JsonObject obj = root.getAsJsonObject(env);
                        if (obj.has("baseUrl")) {
                            String url = obj.get("baseUrl").getAsString();
                            if (url != null && !url.isBlank()) {
                                return url;
                            }
                        }
                    }
                }
            } catch (Exception ignored) {
                // ignore and fall back
            }
        }

        return fallback;
    }

    /**
     * Return the configured base URL (via -DbaseUrl, BASE_URL or -Denv lookup). Falls back to provided default.
     */
    public static String getBaseUrl(String defaultBase) {
        return getUrl(defaultBase);
    }

    /**
     * Build an absolute URL by combining base URL and a relative path.
     */
    public static String navigateTo(String relativeOrAbsolute, String defaultBase) {
        if (relativeOrAbsolute == null) return getBaseUrl(defaultBase);
        String trimmed = relativeOrAbsolute.trim();
        if (trimmed.startsWith("http://") || trimmed.startsWith("https://")) {
            return trimmed;
        }
        String base = getBaseUrl(defaultBase);
        if (base.endsWith("/") && trimmed.startsWith("/")) {
            return base + trimmed.substring(1);
        } else if (!base.endsWith("/") && !trimmed.startsWith("/")) {
            return base + "/" + trimmed;
        } else {
            return base + trimmed;
        }
    }
}
