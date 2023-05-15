import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {

    public static void main(String[] args) throws Exception {

        API api = API.NASA;

        String url = api.getUrl();
        ExtratorConteudo extrator = api.getExtrator();

        var http = new ClienteHttp();
        String json = http.buscaDados(url);

        // CRIADOR DE NOVA PASTA
        var pasta = new File("sticker/");
           pasta.mkdir(); 

        // exibir e manipular os dados na aplicacao
        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        var criador = new CriadorStickers();

        for (int i = 0; i < 3; i++) {

            Conteudo conteudo = conteudos.get(i);

            InputStream inputStream = new URL(conteudo.urlImagem()).openStream();
            String nomeArquivo = "sticker/" + conteudo.titulo() + ".png";

            criador.cria(inputStream, nomeArquivo);

            System.out.println(conteudo.titulo());
            System.out.println();
            
            // System.out.println("\u001b[0;0;0;0;0m \u001b[48;2;42;193;193m" + "Rank: " + conteudo.get("rank") + "\u001b[m");
            // System.out.print("Classificacao: ");
            
            //  String nota = conteudo.get("imDbRating");
            //  double notaInteira = Double.parseDouble(nota);
            //  System.out.println(notaInteira);
            // double classificacao = Double.parseDouble(conteudo.get("imDbRating"));
            // int numeroEstrelinhas = (int) classificacao ;
            // for (int n= 1; n<= numeroEstrelinhas; n++) {
            //     System.out.print("⭐");
            //  }
             System.out.println();
        
        }
    }
}



