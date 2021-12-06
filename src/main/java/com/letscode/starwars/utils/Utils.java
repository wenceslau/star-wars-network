package com.letscode.starwars.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.*;
import java.lang.reflect.Type;

/**
 * Metodos uteis
 * @author Wenceslau
 *
 */
public class Utils {

	private static final Logger logger = LoggerFactory.getLogger(Utils.class);
	private static final Gson gson = new Gson();

	private static String osName = System.getProperty("os.name").toLowerCase();
	public static String packaging;
	public static String versionParent;
	public static String warname;
	
	public static boolean debug = true;
	public static boolean info = true;
	public static boolean warn = true;
	public static boolean error = true;

	/*
	 * Contexto da aplicacao no Spring
	 * A variavel eh atribuida na classe CoreAppListener
	 * quando o evento applicationReadyEvent foi disparado
	 * A classe CoreAppListener injeta o contexo usando o Autowired
	 * e assim quardamos ele aqui para poder recuperar objetos do contexto
	 * onde ele nao esta disponivel, como nas classes Listener do Hibernate
	 */
	public static ApplicationContext context;


	/**
	 * Retorna um diretorio temporario onde sera gravados arquivos temporarios
	 * @return texto
	 */
	public static String getPathTemp() {
		String tempDir = "";

		try {

			File f;

			if (isWindows())
				f = new File("/temp/");
			else
				f = new File("/tmp/");

			try {

				if (!f.exists())
					f.mkdir();

				tempDir = f.getAbsolutePath() + "/";

			} catch (Exception e) {

				throw new RuntimeException("Erro ao criar dir temp", e);

			}

		} catch (Exception e) {

			throw new RuntimeException("Erro ao identificar diretorio temp.", e);

		}

		return tempDir;

	}

	public static String getOsName() {
		return osName;
	}

	public static boolean isWindows() {
		return (osName.contains("win"));
	}

	public static boolean isMac() {
		return (osName.contains("mac"));
	}

	public static boolean isUnix() {
		return (osName.contains("nix") || osName.contains("nux") || osName.contains("aix"));
	}
	// ******************************************************//

	/* JSON */

	/**
	 * Format object to json
	 * @param object objeto
	 * @return texto
	 */
	public static String objectToJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		String jsonInString = "Error convert object to json. ";

		try {

			jsonInString = mapper.writeValueAsString(object);

		} catch (Exception e) {

			jsonInString += e.getMessage();

		}

		return jsonInString;

	}

	/**
	 * Converte Objeto para JSON e nao mostra as propriedade com valor null
	 * @param object objeto
	 * @return texto
	 */
	public static String objectToJsonNotIncludNull(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.setSerializationInclusion(Include.NON_NULL);

		String jsonInString = "Error convert object to json ";

		try {

			jsonInString = mapper.writeValueAsString(object);

		} catch (Exception e) {

			jsonInString += e.getMessage();

		}

		return jsonInString;

	}

	/**
	 * Converte Sring json a um objeto com o tipo informado
	 * @param json texto
	 * @param type tipo objeto
	 * @return objeto
	 */
	public static Object jsonToObject(String json, Type type) {
		try {
			Object o = gson.fromJson(json, type);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Monta uma mensage par ao log
	 * @param clsMthLn texto
	 * @param ste pilha
	 */
	public static void prepareMsgLogger(StringBuilder clsMthLn, StackTraceElement ste) {

		clsMthLn.append("[").append(ste.getClassName()).append(".").append(ste.getMethodName()).append(":").append(ste.getLineNumber());

		if (clsMthLn.length() > 61)
			clsMthLn.delete(1, clsMthLn.length() - 61);
		else
			for (int i = clsMthLn.length(); i <= 61; i++)
				clsMthLn.append(".");

		clsMthLn.append("] ");
	}

	/**
	 * Recuera do contexo o objeto baseado o nome. O objeto context eh injetado
	 * no CoreAppListener e depois reatribuido a variavel estatica do UtilsCore
	 * @param name
	 * @return
	 */
	public static Object getBean(String name) {
		if (context != null) {
			return context.getBean(name);
		} else {
			throw new RuntimeException("msg_o_contexto_da_a_e_n");
		}
	}

}
