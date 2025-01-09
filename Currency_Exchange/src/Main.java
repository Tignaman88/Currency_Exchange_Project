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

    public static void main(String[] args) throws URISyntaxException {

        String currencyToExchange;
        String currencyExchanged;
        double amountToExchange;
        String apiKey = "5879392cbdbcaf6d4b7ab05e";

        Scanner inputUser = new Scanner(System.in);
        System.out.println("Please insert the currency you'd like to exchange(only use letters and no more than 3 characters): ");


        while (true) {
            currencyToExchange = inputUser.nextLine();
            if (currencyToExchange.length() != 3 || currencyToExchange.matches(".*\\d.*")) {
                System.out.println("You've not entered 3 characters, or your value entered contains a number, please try again: ");
            } else {
                break;
            }
        }


        System.out.println("Thank you! Now, please select the currency you'd like to be exchanged to: ");
        while (true) {
            currencyExchanged = inputUser.nextLine();
            if (currencyExchanged.length() != 3 || currencyExchanged.matches(".*\\d.*")) {
                System.out.println("You've not entered 3 characters, or your value entered contains a number, please try again: ");
            } else {
                break;
            }
        }

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



        StringBuilder stringToParseInJson = new StringBuilder();
        try {
            Scanner apiConnectionScanner = new Scanner(request.getInputStream());
            while (apiConnectionScanner.hasNext()) {
                stringToParseInJson.append(apiConnectionScanner.nextLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        JsonObject expectedJsonObject;
        expectedJsonObject = JsonParser.parseString(String.valueOf(stringToParseInJson)).getAsJsonObject();


        // Accessing object
        String req_result = expectedJsonObject.get("conversion_result").getAsString();
        System.out.println(req_result);
        double amount = Double.parseDouble(req_result);
        System.out.println(amount);


    }

}

