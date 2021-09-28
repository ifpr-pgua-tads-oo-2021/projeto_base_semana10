package ifpr.pgua.eic.biblioteca;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class TestApp {
    
    @Test
    public void criaAutor(){
        Autor a = new Autor("Zé","ze@teste.com","001");

        assertEquals("Zé", a.getNome());
        assertEquals("ze@teste.com", a.getEmail());
        assertEquals("001", a.getCpf()); 
    }

    @Test
    public void atributosAutorPrivados() throws NoSuchFieldException{
        Class<Autor> clazz = Autor.class;
        
        Field nome = clazz.getDeclaredField("nome");
        Field email = clazz.getDeclaredField("email");
        Field cpf = clazz.getDeclaredField("cpf");
        
        Assertions.assertTrue(Modifier.isPrivate(nome.getModifiers()));
        Assertions.assertTrue(Modifier.isPrivate(email.getModifiers()));
        Assertions.assertTrue(Modifier.isPrivate(cpf.getModifiers()));
        
        
    }

    @Test
    public void itemAcervoAbstract(){

        Class<ItemAcervo> clazz = ItemAcervo.class;
        
        Assertions.assertTrue(Modifier.isAbstract(clazz.getModifiers()));
    }

    @Test
    public void atributosItemAcervoPrivados() throws NoSuchFieldException{

        Class<ItemAcervo> clazz = ItemAcervo.class;

        Field titulo = clazz.getDeclaredField("titulo");
        Field anoPublicacao = clazz.getDeclaredField("anoPublicacao");
        Field editora = clazz.getDeclaredField("editora");
        Field numeroPaginas = clazz.getDeclaredField("numeroPaginas");
        
        Assertions.assertTrue(Modifier.isPrivate(titulo.getModifiers()));
        Assertions.assertTrue(Modifier.isPrivate(anoPublicacao.getModifiers()));
        Assertions.assertTrue(Modifier.isPrivate(editora.getModifiers()));
        Assertions.assertTrue(Modifier.isPrivate(numeroPaginas.getModifiers()));
        
    }

    @Test
    public void livroEstendeItemAcervo(){
        Class clazz = Livro.class.getSuperclass();

        Assertions.assertTrue(clazz.getName().contains("ItemAcervo"));
    }

    @Test
    public void atributosLivroPrivados() throws NoSuchFieldException{

        Class<Livro> clazz = Livro.class;

        
        Field numeroCapitulos = clazz.getDeclaredField("numeroCapitulos");
        Field autor = clazz.getDeclaredField("autor");

        Assertions.assertTrue(Modifier.isPrivate(numeroCapitulos.getModifiers()));
        Assertions.assertTrue(Modifier.isPrivate(autor.getModifiers()));
    
    }

    @Test
    public void criaLivro(){
        Autor autor = new Autor("Zé","ze@teste.com","001");

        Livro livro = new Livro("Titulo",autor, 2001,1000,"Editora",10);

        assertEquals("Titulo", livro.getTitulo());
        assertEquals(autor.getNome(), livro.getAutor().getNome());
        assertEquals(2001,livro.getAnoPublicacao());
        assertEquals(1000, livro.getNumeroPaginas());
        assertEquals("Editora", livro.getEditora());
        assertEquals(10, livro.getNumeroCapitulos());
        
    }

    @Test
    public void revistaEstendeItemAcervo(){
        Class clazz = Revista.class.getSuperclass();

        Assertions.assertTrue(clazz.getName().contains("ItemAcervo"));
    }

    @Test
    public void atributosRevistaPrivados() throws NoSuchFieldException{

        Class<Revista> clazz = Revista.class;

        Field numero = clazz.getDeclaredField("numero");
        
        Assertions.assertTrue(Modifier.isPrivate(numero.getModifiers()));
        
    }

    @Test
    public void criaRevista(){
        
        Revista revista = new Revista("Titulo",10, 2001,1000,"Editora");

        assertEquals("Titulo", revista.getTitulo());
        assertEquals(10,revista.getNumero());
        assertEquals(2001,revista.getAnoPublicacao());
        assertEquals(1000, revista.getNumeroPaginas());
        assertEquals("Editora", revista.getEditora());

    }

    @Test
    public void atributosBiblioteca() throws NoSuchFieldException{
        Class<Biblioteca> clazz = Biblioteca.class;

        Field autores = clazz.getDeclaredField("autores");
        Field acervo = clazz.getDeclaredField("acervo");

        Assertions.assertTrue(Modifier.isPrivate(autores.getModifiers()));
        Assertions.assertTrue(Modifier.isPrivate(acervo.getModifiers()));

        Assertions.assertEquals(ArrayList.class, autores.getType());
        Assertions.assertEquals(ArrayList.class, acervo.getType());
    }

    @Test
    public void cadastraAutor(){
        String nome = "Autor";
        String email = "autor@teste.com";
        String cpf = "001";

        Biblioteca biblioteca = new Biblioteca();

        Assertions.assertTrue(biblioteca.cadastraAutor(nome, email, cpf));

        Assertions.assertEquals("Autor", biblioteca.getAutores().get(0).getNome());
    }


    @Test
    public void bibliotecaRetornaListaAutores(){
        Biblioteca biblioteca = new Biblioteca();

        biblioteca.cadastraAutor("Autor 1", "autor1@teste.com", "001");
        biblioteca.cadastraAutor("Autor 2", "autor2@teste.com", "002");
        biblioteca.cadastraAutor("Autor 3", "autor3@teste.com", "003");
        
        Assertions.assertEquals(3, biblioteca.getAutores().size());
    }

    @Test
    public void naoPodeCadastrarAutorMesmoCpf(){
        String nome = "Autor";
        String email = "autor@teste.com";
        String cpf = "001";

        Biblioteca biblioteca = new Biblioteca();

        biblioteca.cadastraAutor(nome, email, cpf);
        
        Assertions.assertFalse(biblioteca.cadastraAutor(nome, email, cpf));
    }

    @Test
    public void buscaAutorCpf(){
        String nome = "Autor";
        String email = "autor@teste.com";
        String cpf = "001";

        Biblioteca biblioteca = new Biblioteca();

        biblioteca.cadastraAutor(nome, email, cpf);
        Autor r = biblioteca.buscaAutorCpf(cpf);
        Assertions.assertNotNull(r);
        Assertions.assertEquals(cpf, r.getCpf());
    }

    @Test
    public void buscaAutorNome(){
        String nome = "Autor";
        String email = "autor@teste.com";
        String cpf = "001";

        Biblioteca biblioteca = new Biblioteca();

        biblioteca.cadastraAutor(nome, email, cpf);
        
        Autor r = biblioteca.buscaAutorNome(nome);

        Assertions.assertNotNull(r);
        Assertions.assertEquals(nome, r.getNome());
    }

    @Test
    public void cadastraLivro(){
        Autor autor = new Autor("Zé","ze@teste.com","001");

        Livro livro = new Livro("Titulo",autor, 2001,1000,"Editora",10);

        Biblioteca biblioteca = new Biblioteca();

        Assertions.assertTrue(biblioteca.cadastraLivro(livro.getTitulo(), autor, livro.getAnoPublicacao(), 
                                  livro.getNumeroPaginas(), livro.getEditora(), 
                                  livro.getNumeroCapitulos()));

        Assertions.assertEquals(livro.getTitulo(), biblioteca.getLivros().get(0).getTitulo());
    }

    @Test
    public void naoPodeCadastrarLivroMesmoTitulo(){
        Autor autor = new Autor("Zé","ze@teste.com","001");

        Livro livro = new Livro("Titulo",autor, 2001,1000,"Editora",10);

        Biblioteca biblioteca = new Biblioteca();

        biblioteca.cadastraLivro(livro.getTitulo(), autor, livro.getAnoPublicacao(), 
                                  livro.getNumeroPaginas(), livro.getEditora(), 
                                  livro.getNumeroCapitulos());

        Assertions.assertFalse(biblioteca.cadastraLivro(livro.getTitulo(), autor, livro.getAnoPublicacao(), 
                                  livro.getNumeroPaginas(), livro.getEditora(), 
                                  livro.getNumeroCapitulos()));
   }


    @Test
    public void cadastraRevista(){
        
        Revista revista = new Revista("Titulo",10, 2001,1000,"Editora");

        Biblioteca biblioteca = new Biblioteca();

        Assertions.assertTrue(biblioteca.cadastraRevista(revista.getTitulo(),revista.getNumero(),revista.getAnoPublicacao(),revista.getNumeroPaginas(),revista.getEditora()));
    }


    @Test
    public void naoPodeCadastrarRevistaMesmoTituloNumero(){
        
        Revista revista = new Revista("Titulo",10, 2001,1000,"Editora");

        Biblioteca biblioteca = new Biblioteca();

        Assertions.assertTrue(biblioteca.cadastraRevista(revista.getTitulo(),revista.getNumero(),revista.getAnoPublicacao(),revista.getNumeroPaginas(),revista.getEditora()));
        Assertions.assertFalse(biblioteca.cadastraRevista(revista.getTitulo(),revista.getNumero(),revista.getAnoPublicacao(),revista.getNumeroPaginas(),revista.getEditora()));
    
    }

    @Test
    public void buscaLivroTitulo(){
        Autor autor = new Autor("Zé","ze@teste.com","001");

        Livro livro = new Livro("Titulo",autor, 2001,1000,"Editora",10);

        Biblioteca biblioteca = new Biblioteca();

        biblioteca.cadastraLivro(livro.getTitulo(), autor, livro.getAnoPublicacao(), 
                                  livro.getNumeroPaginas(), livro.getEditora(), 
                                  livro.getNumeroCapitulos());
        
        ItemAcervo item = biblioteca.buscaItemAcervo(livro.getTitulo());

        Assertions.assertNotNull(item);
        Assertions.assertTrue(item.getClass().getName().contains("Livro"));
        Assertions.assertEquals(livro.getTitulo(), item.getTitulo());
    }

    @Test
    public void buscaRevistaTitulo(){
        Revista revista = new Revista("Titulo",10, 2001,1000,"Editora");

        Biblioteca biblioteca = new Biblioteca();

        biblioteca.cadastraRevista(revista.getTitulo(),revista.getNumero(),revista.getAnoPublicacao(),revista.getNumeroPaginas(),revista.getEditora());

        ItemAcervo item = biblioteca.buscaItemAcervo(revista.getTitulo());

        Assertions.assertNotNull(item);
        Assertions.assertTrue(item.getClass().getName().contains("Revista"));
        Assertions.assertEquals(revista.getTitulo(), item.getTitulo());
    }

    @Test
    public void buscaRevistaTituloNumero(){
        Revista revista = new Revista("Titulo",10, 2001,1000,"Editora");

        Biblioteca biblioteca = new Biblioteca();

        biblioteca.cadastraRevista(revista.getTitulo(),revista.getNumero(),revista.getAnoPublicacao(),revista.getNumeroPaginas(),revista.getEditora());

        ItemAcervo item = biblioteca.buscaItemAcervo(revista.getTitulo(),revista.getNumero());

        Assertions.assertNotNull(item);
        Assertions.assertTrue(item.getClass().getName().contains("Revista"));
        Assertions.assertEquals(revista.getTitulo(), item.getTitulo());

        Revista res = (Revista) item;

        Assertions.assertEquals(revista.getNumero(), res.getNumero());
        
    } 
}

