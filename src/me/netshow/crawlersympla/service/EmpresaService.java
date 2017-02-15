package me.netshow.crawlersympla.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import me.netshow.crawlersympla.entity.Empresa;

public class EmpresaService {
	
	public void gerenciador(){
//		"http://www.sympla.com.br/eventos/sao-paulo-sp?ordem=relevancia&pagina=6"
		
		String [] cidades = {"sao-paulo-sp","belo-horizonte-mg","rio-de-janeiro-rj","salvador-ba"};
		
		for(int i = 0; i < cidades.length;i ++){
			String link = "http://www.sympla.com.br/eventos/";
			
			link += cidades[i]+ "?ordem=relevancia&";
			
			int contador = 1;
			
			String site = "";
			
			while(!site.contains("Você pode fazer uma nova busca")){
				String linkCompleto = link + "pagina="+contador;
				site = this.connect(linkCompleto);
				
				String [] linkList = this.getLinks(site);
				
				List<Empresa> empresas = this.getEmpresas(linkList, cidades[i]);
				
				WriteFile writeFile = new WriteFile();
				writeFile.write(empresas);
				
				System.out.println("Cidade: "+cidades[i]+" Pagina "+contador);
				contador++;
			}
		}
	}
	
	private List<Empresa> getEmpresas(String [] linkList, String cidade){
		List<Empresa> empresas = new ArrayList<Empresa>();
		
		for(int i = 1; i < linkList.length; i++){
			String site = this.connect(linkList[i]);
			System.out.println(linkList[i]);
			
			Empresa empresa = new Empresa();
			
			empresa.setCity(cidade);
			empresa.setLink(linkList[i]);
			empresa.setLocation("sympla");
			empresa.setName(this.tituloH4(site));
			empresa.setEventName(this.tituloH1(site));
			
			empresas.add(empresa);
		}
		
		return empresas;
	}
	
	private String tituloH1(String site){
		
		String [] h1 = site.split("<h1 class=\"uppercase\"");
		
		int begin = h1[1].indexOf(">");
		int end = h1[1].indexOf("</h1>");
		
		String titulo = h1[1].substring(begin+1, end);
		
		return titulo;
	}
	
	private String tituloH4(String site){
		
		String [] produtor = site.split("id=\"produtor");
		String divProdutor = produtor[produtor.length-1];
		
		int beginProdutor = divProdutor.indexOf("<h4");
		
		String nome = divProdutor.substring(beginProdutor+3, produtor[1].length());
		
		int begin = nome.indexOf(">");
		int end =  nome.indexOf("</h4>");
		
		String nomeEmpresa = nome.substring(begin+1, end);
		
		return nomeEmpresa;
	}
	
	private String [] getLinks(String site){
		
		String [] linkList = site.split("event-box-link");
		
		linkList[0] = null;
		
		for(int i = 1; i < linkList.length; i ++){
			int begin = linkList[i].indexOf("href=");
			int end = linkList[i].indexOf(">");
			
			if(end > 0){
				linkList[i] = linkList[i].substring(begin+6,end-1);
			}
		}
		
		return linkList;
	}
	
	
	private String connect(String link){
		String jsonReturn = "";
		try {	
			URL url = new URL(link);

			URLConnection urlConnection = url.openConnection();

			urlConnection.addRequestProperty("User-Agent", 
					"Mozilla/48.0 (compatible; MSIE 6.0; Windows NT 5.0)");

			InputStreamReader inputStreamReader= new InputStreamReader(urlConnection.getInputStream(), "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String inputLine;

			while((inputLine = bufferedReader.readLine()) != null){
				jsonReturn += (inputLine + "\n");
			}

			bufferedReader.close();
			inputStreamReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}


		return jsonReturn;
	}
}
