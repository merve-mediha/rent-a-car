package com.kodlamaio.rentACar.core.utilities.aspects;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

public class Logging {
	@Before("execution(* com.kodlamaio.rentACar.business.concretes.*.*(..))") // önce kullanacağım // için before ..
	// bütün metodlarda çalışması için
	public void beforeLog(JoinPoint joinPoint) {
		
		MethodSignature signature = (MethodSignature) joinPoint.getSignature(); // BUXING ÖRNEĞİ

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[ \n");

		stringBuilder.append(" {" + "\n");
		stringBuilder.append("\"date\"" + " : \" " + (LocalDate.now() + "\"" + ", \n"));
		stringBuilder.append("\"className\":" + joinPoint.getTarget().getClass().getSimpleName());
		stringBuilder.append("\"methodName\"" + " : \""+ 
				signature.getMethod().getName() + "\" ," + " \n");

		if (signature.getMethod().getName() != "getAll") {
			stringBuilder.append("parameters : " + joinPoint.getArgs()[0]);
		}

		else {
			stringBuilder.append("parameters :" + "none");
		}

		Class<?> str = stringBuilder.getClass();

		Method[] methods = str.getMethods();

		for (Method method : methods) {

			Parameter[] parameters = method.getParameters();
			
			List<String> parameterNames = new ArrayList<>();
			

			for (Parameter parameter : parameters) {

				String parameterName = parameter.getName();
				
				parameterNames.add(parameterName);
			}
			
			for (String pName : parameterNames) {

				System.out.println("pName");
			}

			

		}
		stringBuilder.append("\n ]");

		File file = new File("C:\\logs\\operations.json");
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {

			bufferedWriter.write(stringBuilder.toString());

		} catch (IOException e) {
			System.out.println("Unable to read file " + file.toString());
		}

	}
}
	 

