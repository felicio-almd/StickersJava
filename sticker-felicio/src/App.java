import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
            
        // fazer uma conexão HTTP e buscar os top 3 filmes mais populares
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        System.out.println(body);
    

        // pegar apenas dados necessarios (titulo, poster e rating)
        var parser = new JsonParser(); 
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados na aplicacao
        for (Map<String, String> filme : listaDeFilmes) {
            System.out.println("\u001b[1m" + "Titulo: " + filme.get("title"));
            System.out.println(filme.get("image"));
            System.out.println("\u001b[0;0;0;0;0m \u001b[48;2;42;193;193m" + "Rank: " + filme.get("rank") + "\u001b[m");
            System.out.print("Classificacao: ");
            String nota = filme.get("imDbRating");
            double notaInteira = Double.parseDouble(nota);
            System.out.println(notaInteira);
            System.out.println(); 
        }
    }
}
