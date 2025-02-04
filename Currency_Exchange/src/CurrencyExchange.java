import java.io.IOException;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.google.gson.*;



public class CurrencyExchange {

    StringBuilder stringToCompareWithInputUserBuilder;
    String stringToCompareWithInputUser;
    String apiKey = "5879392cbdbcaf6d4b7ab05e";
    String currencyToExchange;
    String currencyExchanged;
    double amountToExchange;

    public void setCurrencyToExchange(String currencyToExchange) {
        this.currencyToExchange = currencyToExchange;
    }

    public String getCurrencyExchanged() {
        return currencyExchanged;
    }

    public void setCurrencyExchanged(String currencyExchanged) {
        this.currencyExchanged = currencyExchanged;
    }

    public double getAmountToExchange() {
        return amountToExchange;
    }

    public void setAmountToExchange(double amountToExchange) {
        this.amountToExchange = amountToExchange;
    }

    public HttpURLConnection getHttpURLConnection() {
        // Method to set a URL connection
        // Setting URL
        String url_str = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + currencyToExchange + "/" + currencyExchanged + "/" + amountToExchange;
        // Making Request
        URL url = null;
        HttpURLConnection request = null;
        try {
            url = new URL(url_str);
            request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("GET");
            request.connect();
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return request;
    }

    public StringBuilder getStringToParseInJson(HttpURLConnection request) {
        //create a StringBuilder that will be parsed into a Json Object
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


    public String extractedJsonObject(StringBuilder stringToParseInJson) {

        //parse Json Object
        JsonObject expectedJsonObject;
        expectedJsonObject = JsonParser.parseString(String.valueOf(stringToParseInJson)).getAsJsonObject();


        // Accessing object
        String req_result = expectedJsonObject.get("conversion_result").getAsString();
        return req_result;
    }

    /**
     * method that checks that the user enters a valid currency (3 chars, no numbers, and it needs to be contained in JSon Object)
     * @param inputUser
     * @return the currency the user want to exchange
     */
    public String getCurrencyToExchange(Scanner inputUser) {
        String currencyToExchange;
        System.out.println("Please insert the currency you'd like to exchange(only use letters and no more than 3 characters): \n" +
                "Please follow this link to see available codes: https://en.wikipedia.org/wiki/ISO_4217");


        while (true) {
            currencyToExchange = inputUser.nextLine();
            if (currencyToExchange.length() != 3 || currencyToExchange.matches(".*\\d.*") || !stringToCompareWithInputUser.contains(currencyToExchange.toUpperCase())) {
                System.out.println("You've not entered 3 characters, or your value entered contains a number, please try again: ");
            } else {
                break;
            }
        }
        return currencyToExchange;
    }

    /**
     * method that checks that the user enters a valid currency (3 chars, no numbers, and it needs to be contained in JSon Object)
     * @param inputUser
     * @return the currency the user want its originally currency exchanged to
     */
    public String getCurrencyExchanged(Scanner inputUser) {
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

    /**
     * method that checks that the user enters a number
     * @param inputUser
     * @return the amount the user want to exchange
     */
    public double getAmountToExchange(Scanner inputUser) {
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

    public void getJSonObjectForComparisonWithUserInput() throws IOException {
        // Create StringBuilder which will contain the JSon Object to see if input user is contained in JSon file
        stringToCompareWithInputUserBuilder = new StringBuilder();
        // original URL called by API
        String url_str_to_compare = "https://v6.exchangerate-api.com/v6/5879392cbdbcaf6d4b7ab05e/latest/USD";
        URL url_to_compare = null;
        url_to_compare = new URL(url_str_to_compare);
        // open a connection and GET the data that will be compared with user input
        HttpURLConnection requestToCompare = null;
        requestToCompare = (HttpURLConnection) url_to_compare.openConnection();
        requestToCompare.setRequestMethod("GET");
        requestToCompare.connect();
        // get input from the URL, append to StringBuilder variable until the URL has data
        try {
            Scanner apiConnectionScanner = new Scanner(requestToCompare.getInputStream());
            while (apiConnectionScanner.hasNext()) {
                stringToCompareWithInputUserBuilder.append(apiConnectionScanner.nextLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // convert the value of StringBuilder into a string and assign it to a new variable
        stringToCompareWithInputUser = stringToCompareWithInputUserBuilder.toString();
    }


}


