import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Currency;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            mostrarMenuPrincipal();
            int opcionPrincipal = scanner.nextInt();

            switch (opcionPrincipal) {
                case 1:
                    convertirMoneda();
                    break;
                case 2:
                    System.out.println("¡Hasta luego!");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    public static void mostrarMenuPrincipal() {
        System.out.println("Bienvenido al Conversor de Monedas");
        System.out.println("1. Convertir moneda");
        System.out.println("2. Salir");
        System.out.print("Seleccione una opción: ");
    }

    public static void convertirMoneda() {
        Scanner scanner = new Scanner(System.in);
        Set<Currency> availableCurrencies = Set.of(
                Currency.getInstance("ARS"), Currency.getInstance("AUD"),
                Currency.getInstance("BGN"), Currency.getInstance("BOB"),
                Currency.getInstance("BRL"), Currency.getInstance("CAD"),
                Currency.getInstance("CLP"), Currency.getInstance("HTG"),
                Currency.getInstance("MXN"), Currency.getInstance("NZD"),
                Currency.getInstance("PLN"), Currency.getInstance("PYG"),
                Currency.getInstance("USD"), Currency.getInstance("UYU")
        );

        System.out.println("Seleccione la moneda de origen:");
        int contador = 1;
        for (Currency currency : availableCurrencies) {
            System.out.println(contador + ". " + currency.getDisplayName());
            contador++;
        }
        int opcionMonedaOrigen = scanner.nextInt();
        Currency monedaOrigen = getCurrencyByIndex(opcionMonedaOrigen, availableCurrencies);

        System.out.println("Seleccione la moneda de destino:");
        contador = 1;
        for (Currency currency : availableCurrencies) {
            System.out.println(contador + ". " + currency.getDisplayName());
            contador++;
        }
        int opcionMonedaDestino = scanner.nextInt();
        Currency monedaDestino = getCurrencyByIndex(opcionMonedaDestino, availableCurrencies);

        System.out.print("Ingrese la cantidad en " + monedaOrigen.getDisplayName() + ": ");
        double cantidad = scanner.nextDouble();

        // Llama a la API para obtener la tasa de cambio
        String apiKey = "05c18408c3dd8cfe2653386a"; //Aquí va la key individual, en este caso es la mía
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + monedaOrigen.getCurrencyCode() + "/" + monedaDestino.getCurrencyCode() + "/" + cantidad;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // Aquí procesar la respuesta y obtener la tasa de cambio real
            String json = response.body();
            Gson gson = new Gson();
            MonedaOmdb miCambioOmdb = gson.fromJson(json, MonedaOmdb.class);
            MonedaResultado miResultado = new MonedaResultado(miCambioOmdb);
            System.out.print(cantidad);
            System.out.println(miResultado);
        } catch (Exception e) {
            System.out.println("Error al hacer la solicitud a la API: " + e.getMessage());
        }
    }

    public static Currency getCurrencyByIndex(int index, Set<Currency> currencies) {
        int i = 1;
        for (Currency currency : currencies) {
            if (i == index) {
                return currency;
            }
            i++;
        }
        return null;
    }
}
