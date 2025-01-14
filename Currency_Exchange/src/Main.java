import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;
import java.net.http.*;
import java.net.http.HttpResponse.*;
import java.net.http.HttpRequest.*;
import com.google.gson.*;




public class Main {

    static String apiKey = "5879392cbdbcaf6d4b7ab05e";
    static String currencyToExchange;
    static String currencyExchanged;
    static double amountToExchange;
    static StringBuilder stringToCompareWithInputUserBuilder;
    static String stringToCompareWithInputUser;



    public static void main(String[] args) throws URISyntaxException, IOException {

        getJSonToCompareWithUserInput();


        Scanner inputUser = new Scanner(System.in);
        currencyToExchange = getCurrencyToExchange(inputUser);


        currencyExchanged = getCurrencyExchanged(inputUser);

        amountToExchange = getAmountToExchange(inputUser);




        HttpURLConnection request = getHttpURLConnection();


        StringBuilder stringToParseInJson = getStringToParseInJson(request);


        extractedJsonObject(stringToParseInJson, amountToExchange, currencyToExchange, currencyExchanged);


    }

    private static void getJSonToCompareWithUserInput() throws IOException {
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

    private static void extractedJsonObject(StringBuilder stringToParseInJson, double amountToExchange, String currencyToExchange, String currencyExchanged) {
        JsonObject expectedJsonObject;
        expectedJsonObject = JsonParser.parseString(String.valueOf(stringToParseInJson)).getAsJsonObject();


        // Accessing object
        String req_result = expectedJsonObject.get("conversion_result").getAsString();
        double amount = Double.parseDouble(req_result);
        System.out.println(STR."\{amountToExchange} \{currencyToExchange.toUpperCase()} converts to \{amount} \{currencyExchanged.toUpperCase()}");
    }

    private static StringBuilder getStringToParseInJson(HttpURLConnection request) {
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

    private static HttpURLConnection getHttpURLConnection() {
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

    private static double getAmountToExchange(Scanner inputUser) {
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

    private static String getCurrencyExchanged(Scanner inputUser) {
        String currencyExchanged;
        System.out.println("Thank you! Now, please select the currency you'd like to be exchanged to: ");
        while (true) {
            currencyExchanged = inputUser.nextLine();
            if (currencyExchanged.length() != 3 || currencyExchanged.matches(".*\\d.*") || !stringToCompareWithInputUser.contains(currencyExchanged.toUpperCase())) {
                System.out.println("You've not entered 3 characters, or your value entered contains a number, or you haven't entered a correct currency, please try again: ");
            } else {
                break;
            }
        }
        return currencyExchanged;
    }

    private static String getCurrencyToExchange(Scanner inputUser) {
        String currencyToExchange;
        System.out.println("Please insert the currency you'd like to exchange(only use letters and no more than 3 characters): \n" +
                "Please follow this link to see available codes: https://en.wikipedia.org/wiki/ISO_4217");


        while (true) {
            currencyToExchange = inputUser.nextLine();
            if (currencyToExchange.length() != 3 || currencyToExchange.matches(".*\\d.*") || !stringToCompareWithInputUser.contains(currencyToExchange.toUpperCase())) {
                System.out.println("You've not entered 3 characters, or your value entered contains a number, or you haven't entered a correct currency, please try again: ");
            } else {
                break;
            }
        }
        return currencyToExchange;
    }

}

