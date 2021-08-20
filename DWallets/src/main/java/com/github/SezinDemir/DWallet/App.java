 package com.github.SezinDemir.DWallet;
 import com.fasterxml.jackson.core.JsonProcessingException;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import com.github.SezinDemir.DWallet.domain.Customer;
 import io.netty.buffer.ByteBuf;
 import io.netty.buffer.ByteBufAllocator;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.context.annotation.AnnotationConfigApplicationContext;
 import reactor.netty.DisposableServer;
 import java.io.ByteArrayOutputStream;
 import java.io.IOException;
 import java.net.URISyntaxException;


 public class App {

 static final ObjectMapper OBJECT_MAPPER=new ObjectMapper();

 public static void main(String[] args) throws URISyntaxException {
      Logger log= LoggerFactory.getLogger(App.class);
      log.info("Digital Wallets Starting...");
      Netty();
      }

 private static void Netty() throws URISyntaxException {
       AnnotationConfigApplicationContext applicationContext=new AnnotationConfigApplicationContext(AppConfig.class);

       applicationContext.getBean(DisposableServer.class)
          .onDispose()
          .block();
          applicationContext.close();
    }
 static ByteBuf toByteBuf(Object o){
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        try {
            OBJECT_MAPPER.writeValue(out, o);
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return ByteBufAllocator.DEFAULT.buffer().writeBytes(out.toByteArray());}


 static Customer parseCustomer (String str){
        Customer customer=null;
        try {
            customer = OBJECT_MAPPER.readValue(str, Customer.class);
        }catch(JsonProcessingException ex) {
            String[] params = str.split("&");
            int id = Integer.parseInt(params[0].split("=")[1]);
            String lastname = params[1].split("=")[1];
            String firstname = params[2].split("=")[1];
            double balance = Double.parseDouble(params[3].split("=")[1]);
            customer = new Customer(id, lastname, firstname, balance);
        }
        return customer;
    }
 }