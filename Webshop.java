package webshop;

import java.util.HashMap;

public class Webshop {

	public static void main(String[] args) {

		// Initialize HashMaps with Vat and Currency rates
		HashMap<String, Double> Currency_Rates = initCurrencyRates();
		HashMap<String, Double> vatOnlineRates = initVatOnlineRates();
		HashMap<String, Double> vatBookRates = initVatBookRates();

		// Default values for currency and vat
		String outputCurrency = "dkk";
		String inputCurrency = "dkk";
		double vatRate = 0.0;

		// Variable for the final price
		double finalPrice = 0.0;

		// Gets the 3 first arguments
		// Product amount
		int prodAmount = Integer.parseInt(args[0]);
		// Product price
		double prodPrice = Double.parseDouble(args[1]);
		// Product type
		String prodType = args[2].toLowerCase();

		// Loop through the arguments If custom arguments are present
		if (args.length > 3) {
			for (String arg : args) {
				arg = arg.toLowerCase();

				// Vat argument
				if (arg.startsWith("--vat=")) {
					arg = arg.substring(6);

					if (prodType.equals("online")) {
						if (vatOnlineRates.get(arg) != null) {
							vatRate = vatOnlineRates.get(arg);
						}

					} else if (prodType.equals("book")) {
						if (vatBookRates.get(arg) != null) {
							vatRate = vatBookRates.get(arg);

						}
					}
					// Input currency
				} else if (arg.startsWith("--input-currency=")) {
					arg = arg.substring(17);
					inputCurrency = arg;
					// Output currency
				} else if (arg.startsWith("--output-currency=")) {
					arg = arg.substring(18);
					outputCurrency = arg;
				}
			}
		}

		// Calculate price
		finalPrice = prodAmount * prodPrice;

		// Adds freight cost if type is set to book
		if (prodType.equals("book")) {
			if (prodAmount <= 10) {
				finalPrice = finalPrice + 50;
			} else {
				finalPrice = finalPrice + 50;
				prodAmount = prodAmount - 10;

				while (prodAmount >= 1) {
					finalPrice = finalPrice + 25;
					prodAmount = prodAmount - 10;
				}
			}
		}

		// Adds vat if the vat rate differs from the default value of 0.0
		if (vatRate != 0.0) {
			finalPrice = finalPrice + (finalPrice * vatRate);
		}

		// Check if currency conversion is needed.
		// The output currency is set to the input currency if the output currency isn't available.
		if (inputCurrency != outputCurrency) {
			if (Currency_Rates.get(outputCurrency) != null) {
				finalPrice = (finalPrice / Currency_Rates.get(outputCurrency)) * 100;
			} else {
				outputCurrency = inputCurrency;
			}
		}

		// Prints the result
		System.out.print(String.format("%.2f", finalPrice) + " " + outputCurrency.toUpperCase());

	}

	// HashMap with Currency codes and Rates. Currency code is used as the key.
	public static HashMap<String, Double> initCurrencyRates() {
		HashMap<String, Double> currencyRates = new HashMap<String, Double>();
		currencyRates.put("dkk", 100.0);
		currencyRates.put("nok", 73.50);
		currencyRates.put("sek", 70.23);
		currencyRates.put("gbp", 891.07);
		currencyRates.put("eur", 743.93);

		return currencyRates;

	}

	// HashMap with online Vat rates. Country code is used as the key.
	public static HashMap<String, Double> initVatOnlineRates() {
		HashMap<String, Double> vatOnlineRates = new HashMap<String, Double>();
		vatOnlineRates.put("dk", 0.25);
		vatOnlineRates.put("no", 0.25);
		vatOnlineRates.put("se", 0.25);
		vatOnlineRates.put("gb", 0.20);
		vatOnlineRates.put("de", 0.19);

		return vatOnlineRates;

	}

	// HashMap with book Vat rates. Country code is used as the key.
	public static HashMap<String, Double> initVatBookRates() {
		HashMap<String, Double> vatBookRates = new HashMap<String, Double>();
		vatBookRates.put("dk", 0.25);
		vatBookRates.put("no", 0.25);
		vatBookRates.put("se", 0.25);
		vatBookRates.put("gb", 0.20);
		vatBookRates.put("de", 0.12);

		return vatBookRates;
	}

}