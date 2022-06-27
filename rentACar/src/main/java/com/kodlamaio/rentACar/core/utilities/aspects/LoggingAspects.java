//package com.kodlamaio.rentACar.core.utilities.aspects;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.lang.reflect.Method;
//import java.time.LocalDate;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class LoggingAspects {
//////////////////////////////////////////////////////////////////
//	@Before("execution(* com.kodlamaio.rentACar.business.concretes..(..))") // önce kullanacağım // için before ..
//	// bütün metodlarda çalışması için
//public void beforeLog(JoinPoint joinPoint) {
//
//MethodSignature signature = (MethodSignature) joinPoint.getSignature(); // BUXING ÖRNEĞİ
//
//StringBuilder stringBuilder = new StringBuilder();
//stringBuilder.append("[ \n");
//
//stringBuilder.append(" {" + "\n");
//stringBuilder.append("\"date\"" + " : \" " + LocalDate.now() + "\"" + ", \n");
//stringBuilder
//.append("\"className\"" + " : \"" + joinPoint.getTarget().getClass().getSimpleName() + "\"" + ", \n");
//stringBuilder.append("\"methodName\"" + " : \"" + signature.getMethod().getName() + "\" ," + " \n");
//
//if (signature.getMethod().getName() != "getAll") {
//stringBuilder.append("parameters : " + joinPoint.getArgs()[0]);
//}
//
//else {
//stringBuilder.append("parameters :" + "none");
//}
//
//Class<?> str =joinPoint.getTarget().getClass();
//
//
//
//Method[] methods = str.getMethods();
//
//
//Paranamer paranamer = new CachingParanamer();
//
//for (Method method : methods) {
//System.out.println(method);
//String[] parameterNames = paranamer.lookupParameterNames(method,false);
//System.out.println(parameterNames.length);
//
//for (String string : parameterNames) {
//
//
//stringBuilder.append("{ " + "\n");
//
//String[] abc = string.split(",");
//
//for (int i = 0; i < abc.length; i++) {
//
//stringBuilder.append(abc[i] + "," + "\n");
//
//}
//
//stringBuilder.append("," + "\n " + "}");
//
//}
//
//
//}
//stringBuilder.append("\n ]");
//
//File file = new File("C:\\logs\\operations.json");
//try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
//
//bufferedWriter.write(stringBuilder.toString());
//
//} catch (IOException e) {
//System.out.println("Unable to read file " + file.toString());
//}
//
//}
//}