import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Iterator;
import java.util.Scanner;
import java.net.http.*;
import java.net.http.HttpResponse.*;
import java.net.http.HttpRequest.*;
import com.google.gson.*;




public class Main {

    public static void main(String[] args) throws URISyntaxException {

        String currencyToExchange = "";
        String currencyExchanged;
        double amountToExchange;
        String apiKey = "5879392cbdbcaf6d4b7ab05e";

        Scanner inputUser = new Scanner(System.in);
        System.out.println("Hello! Please enter the currency you'd like to exchange(needs to be 3 characters, do not enter a number): ");

        while (currencyToExchange.length() != 3) {
           if(!inputUser.hasNextInt()) {
//                currencyToExchange = inputUser.nextLine();
                if (currencyToExchange.length() != 3) {
                    System.out.println("Value entered is not 3 characters long, please try again: ");
                    inputUser.next();
                    currencyToExchange = inputUser.nextLine();
                }
           } else {
                System.out.println("Wrong value entered, please try again: ");
                inputUser.next();
                currencyToExchange = inputUser.nextLine();
            }
        }



        System.out.println("Thank you! Now, please select the currency you'd like to be exchanged to: ");
        currencyExchanged = inputUser.nextLine();
        System.out.println("And now please enter the amount you want to exchange: ");
        amountToExchange = inputUser.nextDouble();

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




//        exchangeRate(currencyToExchange, currencyExchanged, amountToExchange);
    }

}

