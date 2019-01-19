package br.uff.mesc;

import java.net.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.io.*;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class WebCrawlerSimples { 
	
    public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException {
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    	SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
    	SimpleDateFormat horaDeHoje = new SimpleDateFormat("HH-mm");
		Date dataDeHoje = new Date();

		ArrayList<Date> feriados = new ArrayList<Date>();

		//Adicionando feriados a lista
		feriados.add(new GregorianCalendar(2016, Calendar.OCTOBER, 12).getTime());
		feriados.add(new GregorianCalendar(2016, Calendar.NOVEMBER, 2).getTime());
		feriados.add(new GregorianCalendar(2016, Calendar.NOVEMBER, 15).getTime());
		feriados.add(new GregorianCalendar(2016, Calendar.DECEMBER, 25).getTime());
		feriados.add(new GregorianCalendar(2017, Calendar.JANUARY, 1).getTime());
		feriados.add(new GregorianCalendar(2017, Calendar.APRIL, 14).getTime());
		feriados.add(new GregorianCalendar(2017, Calendar.APRIL, 21).getTime());
		feriados.add(new GregorianCalendar(2017, Calendar.MAY, 1).getTime());
		feriados.add(new GregorianCalendar(2017, Calendar.SEPTEMBER, 7).getTime());
		feriados.add(new GregorianCalendar(2017, Calendar.OCTOBER, 12).getTime());
		feriados.add(new GregorianCalendar(2017, Calendar.NOVEMBER, 2).getTime());
		feriados.add(new GregorianCalendar(2017, Calendar.NOVEMBER, 15).getTime());
		feriados.add(new GregorianCalendar(2017, Calendar.DECEMBER, 25).getTime()); 
		
    	while (true){

    		//Inicializando documentos
    		File arquivoXMLC = new File("rotas_CURTA_" + sdf2.format(dataDeHoje) + ".xml");
    		File arquivoXMLM = new File("rotas_MEDIA_" + sdf2.format(dataDeHoje) + ".xml");
    		File arquivoXMLL = new File("rotas_LONGA_" + sdf2.format(dataDeHoje) + ".xml");
    		Source xmlInputC = new StreamSource(arquivoXMLC);
    		Source xmlInputM = new StreamSource(arquivoXMLM);
    		Source xmlInputL = new StreamSource(arquivoXMLL);
            Source xsl = new StreamSource("D:/Backup/Documentos/UFF/Workspace/ClienteTomTom/conversor.xslt");
            Result xmlOutputC = new StreamResult(new File("rotas_CURTA_" + sdf2.format(dataDeHoje) + ".csv"));
            Result xmlOutputM = new StreamResult(new File("rotas_MEDIA_" + sdf2.format(dataDeHoje) + ".csv"));
            Result xmlOutputL = new StreamResult(new File("rotas_LONGA_" + sdf2.format(dataDeHoje) + ".csv"));
    			
    		// timeout connection after 10000 milliseconds
    		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
    		System.setProperty("sun.net.client.defaultReadTimeout",    "10000");

    		int cont = 0;
            String rotasC = "<routes>";
            String rotasM = "<routes>";
            String rotasL = "<routes>";
            
            while (cont < 1)
            {
            	URL urlC = new URL("https://api.tomtom.com/routing/1/calculateRoute/-22.8957919,-43.2267713:-22.905433,-43.1912082/?routeType=shortest&key=c877wnw7zrgj8raaudtsz3yj");
            	URL urlM = new URL("https://api.tomtom.com/routing/1/calculateRoute/-22.9275731,-43.1822217:-22.905433,-43.1912082/?routeType=shortest&key=c877wnw7zrgj8raaudtsz3yj");
            	URL urlL = new URL("https://api.tomtom.com/routing/1/calculateRoute/-22.997786,-43.3543319:-22.905433,-43.1912082/?routeType=shortest&key=c877wnw7zrgj8raaudtsz3yj");
            
            	System.out.println("Foi URL, chamada: " + cont);
            
            	//Conectando ? API para buscar as rotas
            	URLConnection conexaoC = urlC.openConnection();
            	InputStream xmlC = conexaoC.getInputStream();
            	BufferedReader inC = new BufferedReader(new InputStreamReader(xmlC));
            	
            	URLConnection conexaoM = urlM.openConnection();
            	InputStream xmlM = conexaoM.getInputStream();
            	BufferedReader inM = new BufferedReader(new InputStreamReader(xmlM));
            	
            	URLConnection conexaoL = urlL.openConnection();
            	InputStream xmlL = conexaoL.getInputStream();
            	BufferedReader inL = new BufferedReader(new InputStreamReader(xmlL));
    		
            	//Concatenando o resultado as strings auxiliares
            	String inputC = "";
        		String inputLineC;
        		while ((inputLineC = inC.readLine()) != null) 
        			inputC = inputC + inputLineC;
        		inC.close();
        		
        		String inputM = "";
        		String inputLineM;
        		while ((inputLineM = inM.readLine()) != null) 
        			inputM = inputM + inputLineM;
        		inM.close();
        		
        		String inputL = "";
        		String inputLineL;
        		while ((inputLineL = inL.readLine()) != null) 
        			inputL = inputL + inputLineL;
        		inL.close();
        		
            	//System.out.println(inputC);
            	
            	Calendar calendario = new GregorianCalendar();
            	
            	String isTQUAQUI, isFDS, isSeg, isSex, isSab, isDom, isFeriado, isVespera, isPos;
            	
            	//Verifica se e Domingo
            	if ( calendario.get(Calendar.DAY_OF_WEEK) == 1){
            		isDom = "1";
            	}
            	else{
            		isDom = "0";
            	}
            	
            	//Verifica se e Segunda
            	if ( calendario.get(Calendar.DAY_OF_WEEK) == 2){
            		isSeg = "1";
            	}
            	else{
            		isSeg = "0";
            	}
            	
            	//Verifica se e Sexta
            	if ( calendario.get(Calendar.DAY_OF_WEEK) == 6){
            		isSex = "1";
            	}
            	else{
            		isSex = "0";
            	}
            	
            	//Verifica se e Sabado
            	if ( calendario.get(Calendar.DAY_OF_WEEK) == 7){
            		isSab = "1";
            	}
            	else{
            		isSab = "0";
            	}
            	
            	//Verifica se e Terca, Quarta ou Quinta
            	if ( (calendario.get(Calendar.DAY_OF_WEEK) == 3) || 
            		 (calendario.get(Calendar.DAY_OF_WEEK) == 4) || 
            		 (calendario.get(Calendar.DAY_OF_WEEK) == 5)){
            		isTQUAQUI = "1";
            	}
            	else{
            		isTQUAQUI = "0";
            	}
            	
            	//Verifica se e Final de Semana
            	if ( (calendario.get(Calendar.DAY_OF_WEEK) == 7) || (calendario.get(Calendar.DAY_OF_WEEK) == 1)){
            		isFDS = "1";
            	}
            	else{
            		isFDS = "0";
            	}
            	
            	//Verifica se e feriado
            	dataDeHoje = new GregorianCalendar().getTime();
            	if ( feriados.contains(dataDeHoje) ){
            		isFeriado = "1";
            	}
            	else{
            		isFeriado = "0";
            	}
            	
            	//Verifica se e vespera de feriado
            	if ( feriados.contains(Calendar.DATE + 1) ){
            		isVespera = "1";
            	}
            	else{
            		isVespera = "0";
            	}        	
            	
            	//Verifica se e pos-feriado
            	if ( feriados.contains(Calendar.DATE - 1) ){
            		isPos = "1";
            	}
            	else{
            		isPos = "0";
            	}

            	//Realiza calculo da hora
				//Hora + minuto divido por 60
            	String horaFormatada = horaDeHoje.format(dataDeHoje);
            	int hora, minuto;
            	minuto = Integer.parseInt(horaFormatada.substring(3,5));

            	double horaFloat = Float.parseFloat(horaFormatada.substring(0,2)) + ((double)minuto/60);

				DecimalFormat df = new DecimalFormat("##.##");

				//System.out.println(df.format(horaFloat));
				//System.out.println(isDom);
            	//System.out.println(isFDS);
            	//System.out.println(isFeriado);
            	//System.out.println(isPos);
            	//System.out.println(isSab);
            	//System.out.println(isSeg);
            	//System.out.println(isSex);
            	//System.out.println(isTQUAQUI);
            	//System.out.println(isVespera);
            	
            	//Armazenando dados no xml para converter depois
            	String rotaC = inputC.substring(inputC.indexOf("<route>"), inputC.indexOf("</arrivalTime>") + 14);
            	rotaC = rotaC + "<dataFormatada>" + sdf.format(dataDeHoje) + "</dataFormatada>";
            	rotaC = rotaC + "<horaFloat>" + df.format(horaFloat) + "</horaFloat>";
            	rotaC = rotaC + "<isTQQ>" + isTQUAQUI + "</isTQQ>";
            	rotaC = rotaC + "<isFDS>" + isFDS + "</isFDS>";
            	rotaC = rotaC + "<isSeg>" + isSeg + "</isSeg>";
            	rotaC = rotaC + "<isSex>" + isSex + "</isSex>";
            	rotaC = rotaC + "<isSab>" + isSab + "</isSab>";
            	rotaC = rotaC + "<isDom>" + isDom + "</isDom>";
            	rotaC = rotaC + "<isFeriado>" + isFeriado + "</isFeriado>";
            	rotaC = rotaC + "<isVespera>" + isVespera + "</isVespera>";
            	rotaC = rotaC + "<isPosFeriado>" + isPos + "</isPosFeriado>";
            	rotaC = rotaC + inputC.substring(inputC.indexOf("</summary>"), inputC.indexOf("</route>") + 8);
            	
            	String rotaM = inputM.substring(inputM.indexOf("<route>"), inputM.indexOf("</arrivalTime>") + 14);
            	rotaM = rotaM + "<dataFormatada>" + sdf.format(dataDeHoje) + "</dataFormatada>";
				rotaC = rotaC + "<horaFloat>" + df.format(horaFloat) + "</horaFloat>";
            	rotaM = rotaM + "<isTQQ>" + isTQUAQUI + "</isTQQ>";
            	rotaM = rotaM + "<isFDS>" + isFDS + "</isFDS>";
            	rotaM = rotaM + "<isSeg>" + isSeg + "</isSeg>";
            	rotaM = rotaM + "<isSex>" + isSex + "</isSex>";
            	rotaM = rotaM + "<isSab>" + isSab + "</isSab>";
            	rotaM = rotaM + "<isDom>" + isDom + "</isDom>";
            	rotaM = rotaM + "<isFeriado>" + isFeriado + "</isFeriado>";
            	rotaM = rotaM + "<isVespera>" + isVespera + "</isVespera>";
            	rotaM = rotaM + "<isPosFeriado>" + isPos + "</isPosFeriado>";
            	rotaM = rotaM + inputM.substring(inputM.indexOf("</summary>"), inputM.indexOf("</route>") + 8);
            	
            	String rotaL = inputL.substring(inputL.indexOf("<route>"), inputL.indexOf("</arrivalTime>") + 14);
            	rotaL = rotaL + "<dataFormatada>" + sdf.format(dataDeHoje) + "</dataFormatada>";
				rotaC = rotaC + "<horaFloat>" + df.format(horaFloat) + "</horaFloat>";
            	rotaL = rotaL + "<isTQQ>" + isTQUAQUI + "</isTQQ>";
            	rotaL = rotaL + "<isFDS>" + isFDS + "</isFDS>";
            	rotaL = rotaL + "<isSeg>" + isSeg + "</isSeg>";
            	rotaL = rotaL + "<isSex>" + isSex + "</isSex>";
            	rotaL = rotaL + "<isSab>" + isSab + "</isSab>";
            	rotaL = rotaL + "<isDom>" + isDom + "</isDom>";
            	rotaL = rotaL + "<isFeriado>" + isFeriado + "</isFeriado>";
            	rotaL = rotaL + "<isVespera>" + isVespera + "</isVespera>";
            	rotaL = rotaL + "<isPosFeriado>" + isPos + "</isPosFeriado>";
            	rotaL = rotaL + inputL.substring(inputL.indexOf("</summary>"), inputL.indexOf("</route>") + 8); 
            	
            	rotasC = rotasC + rotaC;
            	rotasM = rotasM + rotaM;
            	rotasL = rotasL + rotaL;
            	
            	cont++;
            	Thread.sleep(15000*60);
            	//Thread.sleep(1000*60*30);
            }
            
            rotasC = rotasC + "</routes>";
            rotasM = rotasM + "</routes>";
            rotasL = rotasL + "</routes>";
            
        	//System.out.println(rotasC);

			//Colocando o resultado nos arquivos
            BufferedWriter writerC = new BufferedWriter(new FileWriter(arquivoXMLC));
            writerC.write(rotasC);
            writerC.close();
            
            BufferedWriter writerM = new BufferedWriter(new FileWriter(arquivoXMLM));
            writerM.write(rotasM);
            writerM.close();
            
            BufferedWriter writerL = new BufferedWriter(new FileWriter(arquivoXMLL));
            writerL.write(rotasL);
            writerL.close();

            //Convertendo os arquivos XML para CSV
            try {
                Transformer transformer = TransformerFactory.newInstance().newTransformer(xsl);
                transformer.transform(xmlInputC, xmlOutputC);
            } catch (TransformerException e) {
            }
            try {
                Transformer transformer = TransformerFactory.newInstance().newTransformer(xsl);
                transformer.transform(xmlInputM, xmlOutputM);
            } catch (TransformerException e) {
            }
            try {
                Transformer transformer = TransformerFactory.newInstance().newTransformer(xsl);
                transformer.transform(xmlInputL, xmlOutputL);
            } catch (TransformerException e) {
            }
    	}
	}
}
