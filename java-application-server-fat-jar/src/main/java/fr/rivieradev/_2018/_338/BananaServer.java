package fr.rivieradev._2018._338;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Arrays;
import java.util.Vector;
import java.util.zip.Deflater;
import java.lang.Integer;
import java.lang.Thread;

public class BananaServer {
    public static void main(String[] args) throws IOException  {
        System.out.println("Main Class Start");
        int port = 8080;

        HttpServer server
                = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new KRootHandler());
        server.setExecutor(null);
        server.start();
    }
    static final Map<Integer,byte[]> m = new HashMap<>();
    static int nbUser = 0;
    static class KRootHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            String fullPath = t.getRequestURI().getPath().trim();
            System.out.println("[accessing] " + fullPath);
            if (fullPath.startsWith("/ram")) {
                getRam(t, fullPath);
                return;
            }
            else if (fullPath.startsWith("/cpu")) {
                getCpu(t, fullPath);
                return;
            }
            else if (fullPath.startsWith("/deflater")) {
                getDeflater(t, fullPath);
                return;
            }
            else if (fullPath.equals("/")) {
                getDefaultPage(t);
                return;
            }
            getDefaultPage(t);
            return;
        }
        private void getDefaultPage(HttpExchange t) throws IOException {
            OutputStream os = t.getResponseBody();
            StringBuilder sb = new StringBuilder();
            Runtime runtime = Runtime.getRuntime();
            NumberFormat format = NumberFormat.getInstance();
            long maxMemory = runtime.maxMemory();
            long allocatedMemory = runtime.totalMemory();
            long freeMemory = runtime.freeMemory();
            
            String message = "--- Server status page ---";
            message = message + "\n Available processors : " + Runtime.getRuntime().availableProcessors();
            message = message + "\nMemory --- max=" + format.format(maxMemory / 1024 / 1024) + "MB : ";
            message = message + "allocated=" + format.format(allocatedMemory / 1024 / 1024) + "MB : ";
            message = message + "free= " + format.format(freeMemory / 1024 / 1024) + "MB";
            message = message + "\n Active application threads : " + Thread.activeCount() + "\n";
            sb.append(message);

            t.sendResponseHeaders(200, sb.length());
            os.write(sb.toString().getBytes());
            os.close();
        }

        /**
         *
         * @param t
         * @throws IOException
         * @see
         * http://alvinalexander.com/blog/post/java/java-program-consume-all-memory-ram-on-computer
         */
        private void getCpu(HttpExchange t, String fullPath) throws IOException {
            OutputStream os = t.getResponseBody();
            
            String[] parts = fullPath.split("\\-", 2);
            String path = parts[0];
            String pathParams = null;
            
            int firstParam = 0;
            if (parts.length == 2) {
                firstParam = Integer.parseInt(parts[1]);
            }

            int count;
            if (firstParam != 0) {
              count = firstParam;
            }
            else {
              count = Runtime.getRuntime().availableProcessors();
            }
            
            String message = new String();

            message = count + " threads started";

            t.sendResponseHeaders(200, message.length());
            os.write(message.getBytes());
            os.close();


            for (int i = 0; i < count; i++) {
              new Thread(this::startBackgroundThread,"CPU-based-thread-" + i).start();
            }

        }

        private void getDeflater(HttpExchange t, String fullPath) throws IOException {
            OutputStream os = t.getResponseBody();
            
            String[] parts = fullPath.split("\\-", 2);
            String path = parts[0];
            String pathParams = null;
            
            int firstParam = 0;
            if (parts.length == 2) {
                firstParam = Integer.parseInt(parts[1]);
            }

            int count;
            if (firstParam != 0) {
              count = firstParam;
            }
            else {
              count = Runtime.getRuntime().availableProcessors();
            }
            
            String message = new String();

            message = "Running Deflater " + count + " times.";

            t.sendResponseHeaders(200, message.length());
            os.write(message.getBytes());
            os.close();

            for (int i=0; i < count; i++) {
              //System.out.println(i);
              new Deflater();
            }

        }
        

        private void startBackgroundThread() {
               System.out.println("Creating thread: " );
               try {
                Thread.sleep(1000000);
                } catch (InterruptedException ex) {
                  Thread.currentThread().interrupt();
                }
        }

        /**
         *
         * @param t
         * @throws IOException
         * @see
         * http://alvinalexander.com/blog/post/java/java-program-consume-all-memory-ram-on-computer
         */
        private void getRam(HttpExchange t, String fullPath) throws IOException {

            OutputStream os = t.getResponseBody();
            String message = new String();

            message = message + "\n RAM draining started";

            t.sendResponseHeaders(200, message.length());
            os.write(message.getBytes());
            os.close();
            try {
              Thread.sleep(3000);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            Vector v = new Vector();
            while (true) {
                try {
                    Thread.sleep(500);                 //1000 milliseconds is one second.
                    byte b[] = new byte[20048576];
                    v.add(b);
                    Runtime runtime = Runtime.getRuntime();
                    NumberFormat format = NumberFormat.getInstance();
                    StringBuilder sb = new StringBuilder();
                    long maxMemory = runtime.maxMemory();
                    long allocatedMemory = runtime.totalMemory();
                    long freeMemory = runtime.freeMemory();
                    message = "Memory --- max=" + format.format(maxMemory / 1024 / 1024) + "MB : ";
                    message = message + "allocated=" + format.format(allocatedMemory / 1024 / 1024) + "MB : ";
                    message = message +"free= " + format.format(freeMemory / 1024 / 1024) + "MB";
                    System.out.println(message);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

            }

        }
    }
}
