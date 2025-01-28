import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CurrencyExchange {

    static StringBuilder stringToCompareWithInputUserBuilder;
    static String stringToCompareWithInputUser;
    static String apiKey = "5879392cbdbcaf6d4b7ab05e";
    static String currencyToExchange;
    static String currencyExchanged;
    static double amountToExchange;

    public static void CurrencyExchange() {

        getHttpURLConnection();
        getStringToParseInJson(getHttpURLConnection());

    }

    public static StringBuilder getStringToCompareWithInputUserBuilder() {
        return stringToCompareWithInputUserBuilder;
    }

    public static void setStringToCompareWithInputUserBuilder(StringBuilder stringToCompareWithInputUserBuilder) {
        CurrencyExchange.stringToCompareWithInputUserBuilder = stringToCompareWithInputUserBuilder;
    }

    public static String getStringToCompareWithInputUser() {
        return stringToCompareWithInputUser;
    }

    public static void setStringToCompareWithInputUser(String stringToCompareWithInputUser) {
        CurrencyExchange.stringToCompareWithInputUser = stringToCompareWithInputUser;
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static void setApiKey(String apiKey) {
        CurrencyExchange.apiKey = apiKey;
    }

    public static String getCurrencyToExchange() {
        return currencyToExchange;
    }

    public static void setCurrencyToExchange(String currencyToExchange) {
        CurrencyExchange.currencyToExchange = currencyToExchange;
    }

    public static String getCurrencyExchanged() {
        return currencyExchanged;
    }

    public static void setCurrencyExchanged(String currencyExchanged) {
        CurrencyExchange.currencyExchanged = currencyExchanged;
    }

    public static double getAmountToExchange() {
        return amountToExchange;
    }

    public static void setAmountToExchange(double amountToExchange) {
        CurrencyExchange.amountToExchange = amountToExchange;
    }

    public static void getJSonToCompareWithUserInput() throws IOException {
        // Create object to see if input user is contained in JSon file
        stringToCompareWithInputUserBuilder = new StringBuilder();
        String url_str_to_compare = "https://v6.exchangerate-api.com/v6/5879392cbdbcaf6d4b7ab05e/latest/USD";
        URL url_to_compare = null;
        url_to_compare = new URL(url_str_to_compare);
        HttpURLConnection requestToCompare = null;
        requestToCompare = (HttpURLConnection) url_to_compare.openConnection();
        requestToCompare.setRequestMethod("GET");
        requestToCompare.connect();
        try {
            Scanner apiConnectionScanner = new Scanner(requestToCompare.getInputStream());
            while (apiConnectionScanner.hasNext()) {
                stringToCompareWithInputUserBuilder.append(apiConnectionScanner.nextLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stringToCompareWithInputUser = stringToCompareWithInputUserBuilder.toString();
    }

    public static String getCurrencyToExchange(Scanner inputUser) {
        String currencyToExchange;
        System.out.println("Please insert the currency you'd like to exchange(only use letters and no more than 3 characters): \n" +
                "Please follow this link to see available codes: https://en.wikipedia.org/wiki/ISO_4217");


        while (true) {
            currencyToExchange = inputUser.nextLine();
            if (currencyToExchange.length() != 3 || currencyToExchange.matches(".*\\d.*") || stringToCompareWithInputUser.contains(currencyToExchange.toUpperCase())) {
                System.out.println("You've not entered 3 characters, or your value entered contains a number, please try again: ");
            } else {
                break;
            }
        }
        return currencyToExchange;
    }

    public static String getCurrencyExchanged(Scanner inputUser) {
        String currencyExchanged;
        System.out.println("Thank you! Now, please select the currency you'd like to be exchanged to: ");
        while (true) {
            currencyExchanged = inputUser.nextLine();
            if (currencyExchanged.length() != 3 || currencyExchanged.matches(".*\\d.*") || !stringToCompareWithInputUser.contains(currencyExchanged.toUpperCase())) {
                System.out.println("You've not entered 3 characters, or your value entered contains a number, please try again: ");
            } else {
                break;
            }
        }
        return currencyExchanged;
    }

    public static double getAmountToExchange(Scanner inputUser) {
        double amountToExchange;
        System.out.println("And now please enter the amount you want to exchange: ");
        while (true) {
            try {
                amountToExchange = inputUser.nextDouble();
                Double.valueOf(amountToExchange);
                break;
            }
            catch(InputMismatchException nfe){
                System.out.println("Invalid number, please try again: ");
                inputUser.nextLine();
            }

        }
        return amountToExchange;
    }

    public static HttpURLConnection getHttpURLConnection() {
        // Setting URL
        String url_str = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + currencyToExchange + "/" + currencyExchanged + "/" + amountToExchange;
        // Making Request
        URL url = null;
        try {
            url = new URL(url_str);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        HttpURLConnection request = null;
        try {
            request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("GET");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            request.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return request;
    }

    public static StringBuilder getStringToParseInJson(HttpURLConnection request) {
        StringBuilder stringToParseInJson = new StringBuilder();
        try {
            Scanner apiConnectionScanner = new Scanner(request.getInputStream());
            while (apiConnectionScanner.hasNext()) {
                stringToParseInJson.append(apiConnectionScanner.nextLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringToParseInJson;

    }

    public static void extractedJsonObject(StringBuilder stringToParseInJson, double amountToExchange, String currencyToExchange, String currencyExchanged) {
        JsonObject expectedJsonObject;
        expectedJsonObject = JsonParser.parseString(String.valueOf(stringToParseInJson)).getAsJsonObject();


        // Accessing object
        String req_result = expectedJsonObject.get("conversion_result").getAsString();
        double amount = Double.parseDouble(req_result);
        System.out.println(STR."\{amountToExchange} \{currencyToExchange.toUpperCase()} converts to \{amount} \{currencyExchanged.toUpperCase()}");
    }
}
