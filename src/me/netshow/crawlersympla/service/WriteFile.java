package me.netshow.crawlersympla.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import me.netshow.crawlersympla.entity.Empresa;

public class WriteFile {
	
	public void write(List<Empresa> empresas){
		
		File arquivo = new File("dados.csv");
		try(
			FileWriter fw = new FileWriter(arquivo, true) ){
			
			for(Empresa empresa: empresas){
				fw.write("\n"+empresa.getCity());
				fw.write(","+empresa.getLink());
				fw.write(","+empresa.getName());
				fw.write(","+empresa.getLocation());
				fw.write(","+empresa.getEventName());
			}
		    
		    fw.flush();
		}catch(IOException ex){
		  ex.printStackTrace();
		}
	}
}
