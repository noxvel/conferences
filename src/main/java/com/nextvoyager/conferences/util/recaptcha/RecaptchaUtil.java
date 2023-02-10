package com.nextvoyager.conferences.util.recaptcha;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

/**
 * Utility class for Google reCAPTCHA utility
 *
 * @author Stanislav Bozhevskyi
 */
public class RecaptchaUtil {
    private static final String PROPERTIES_FILE = "recaptcha.properties";
    private static final Properties PROPERTIES = new Properties();
    private static final Logger LOG = LogManager.getLogger(RecaptchaUtil.class);

    private static final String URL_VERIFY = "https://www.google.com/recaptcha/api/siteverify";
    private static final String USER_AGENT = "Mozilla/5.0";
    public static final String SITE_KEY;
    private static final String SECRET_KEY;

    private RecaptchaUtil(){}

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

        if (propertiesFile == null) {
            throw new RecaptchaConfigException("Properties file '" + PROPERTIES_FILE + "' is missing!");
        }

        try {
            PROPERTIES.load(propertiesFile);
        } catch (IOException ex) {
            throw new RecaptchaConfigException("Cannot load properties file '" + PROPERTIES_FILE + "' .", ex);
        }

        SITE_KEY = getProperty("key.site");
        SECRET_KEY = getProperty("key.secret");
    }

    private static String getProperty(String key) throws RecaptchaConfigException {
        String property = PROPERTIES.getProperty(key);

        if (property == null || property.trim().length() == 0) {
            throw new RecaptchaConfigException("Require property '" + key + "' is missing in  properties file '" +
                    PROPERTIES_FILE + "'!");
        }
        return property;
    }

    public static boolean verify(String gRecaptchaParam) {
        if (gRecaptchaParam == null || gRecaptchaParam.isEmpty()) {
            return false;
        }

        try{
            // create a client
            var client = HttpClient.newHttpClient();

            // create a request
            StringBuilder postParams = new StringBuilder();
            postParams.append("secret=")
                    .append(SECRET_KEY)
                    .append("&response=")
                    .append(gRecaptchaParam);

            var request = HttpRequest.newBuilder(
                            URI.create(URL_VERIFY))
                    .header("User-Agent", USER_AGENT)
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(postParams.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int responseCode = response.statusCode();
            LOG.debug("Sending 'POST' request to URL : {}, Post parameters : {}, Response Code : {}",
                    URL_VERIFY, postParams.toString(), responseCode);

            if (responseCode == 200) {
                // print result
                LOG.debug("response for verify reCAPTCHA: {}", response.body());

                //parse JSON response and return 'success' value
                JsonReader jsonReader = Json.createReader(new StringReader(response.body()));
                JsonObject jsonObject = jsonReader.readObject();
                jsonReader.close();

                return jsonObject.getBoolean("success");
            } else {
                LOG.error("Bad response from reCAPTCHA request with code {} and text {}", responseCode, response);
                return false;
            }
        }catch(Exception e){
            LOG.error("Error while send request to verify Google reCAPTCHA - {}", e.getMessage());
            return false;
        }

    }
}
