package me.netshow.crawlersympla.controller;

import me.netshow.crawlersympla.service.EmpresaService;

public class EmpresaController {
	
	EmpresaService empresaService = new EmpresaService();
	
	public void start(){
//		String link = "http://www.sympla.com.br/eventos/sao-paulo-sp?ordem=relevancia&pagina=1";
		
		this.empresaService.gerenciador();
	}
	
}
