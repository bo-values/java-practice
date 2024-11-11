package backend.app;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;

public class HttpServerApp {

    private static final String HTTP_NEW_LINE_SEPARATOR = "\r\n";
    private static final String HTTP_HEAD_BODY_SEPARATOR = HTTP_NEW_LINE_SEPARATOR + HTTP_NEW_LINE_SEPARATOR;
    private static final int HTTP_HEAD_BODY_SEPARATOR_BYTES = HTTP_NEW_LINE_SEPARATOR.getBytes(StandardCharsets.US_ASCII).length;
    private static final int DEFAULT_PACKET_SIZE = 10_000;
    private static final String CONTENT_LENGTH_HEADER = "content-length";
    private static final String CONNECTION_HEADER = "connection";
    private static final String CONNECTION_KEEP_ALIVE = "keep-alive";
    public static void main(String[] args) throws Exception {
        var serverSocket = new ServerSocket(8080);

        var executor = Executors.newFixedThreadPool(10);

        int connections = 0;

        while (true) {
            var connection = serverSocket.accept();
            connections++;
            System.out.println("We have new connection: All =" + connections);
            connection.setSoTimeout(10_000);
            executor.execute(()->{
                try {
                    handleRequest(connection);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static Optional<HttpReq> readRequest(Socket connection) throws Exception {

        var stream = connection.getInputStream();
        var rawRequestHead = readRawRequestHead(stream);

        if(rawRequestHead.length == 0){
            return Optional.empty();
        }

        var requestHead = new String(rawRequestHead,StandardCharsets.US_ASCII);
        var lines = requestHead.split(HTTP_NEW_LINE_SEPARATOR);

        var line = lines[0];
        if (line == null)
            return null;
        var methodUrl = line.split(" ");
        var method = methodUrl[0];
        var url = methodUrl[1];
        var headers = readHeaders(lines);

        var bodyLength = getExpectedBodyLength(headers);

        byte[] body;
        if(bodyLength > 0) {
            var bodyStartIndex = requestHead.indexOf(HTTP_HEAD_BODY_SEPARATOR);
            if(bodyStartIndex > 0) {
                var readBody = Arrays.copyOfRange(rawRequestHead,bodyStartIndex + HTTP_HEAD_BODY_SEPARATOR_BYTES,rawRequestHead.length);
                body = readBody(stream,readBody,bodyLength);
            } else {
                body = new byte[0];
            }
        } else {
            body = new byte[0];
        }


        return Optional.of(new HttpReq(method, url, headers, body));
    }

    private static int getExpectedBodyLength(Map<String, List<String>> headers) {
        try {
            return Integer.parseInt(headers.getOrDefault(CONTENT_LENGTH_HEADER, List.of("0")).get(0));
        } catch(Exception ignored){
            return 0;
        }
    }

    private static void handleRequest(Socket connection) throws Exception {
        try {
        var requestOpt = readRequest(connection);
            if (requestOpt.isEmpty()){
                closeConnnection(connection);
                return;
            }

            var request = requestOpt.get();
            printRequest(request);

            var os = connection.getOutputStream();
            var body = """
                    {
                        "id": 1
                    }
                    """;

            var response = new StringBuilder().append("HTTP/1.1 200 OK")
            .append(HTTP_NEW_LINE_SEPARATOR)
            .append("Content-Type: application/json")
            .append(HTTP_NEW_LINE_SEPARATOR)
            .append("Content-Length: %d".formatted(body.getBytes(StandardCharsets.UTF_8).length))
            .append(HTTP_HEAD_BODY_SEPARATOR)
            .append(body)
            .toString();

            os.write(response.getBytes(StandardCharsets.UTF_8)); 

            if (shouldRequestConnection(request.headers)) {
                System.out.println("Reuing connection..");
                handleRequest(connection);
            }

        } catch (SocketTimeoutException e) {
            System.out.println("Socket timeout closing");
            closeConnnection(connection);
        } catch (Exception e) {
            System.out.println("Problem while Reading connection");
            e.getStackTrace();
            closeConnnection(connection);
        }
    }

    private static void closeConnnection(Socket connection) {
        try {
            System.out.println("Closing connection..");
            connection.close();
        } catch (Exception ignored) {
            ...
        }
    }

    private static boolean shouldRequestConnection(Map<String,List<String>> headers) {
        return headers.getOrDefault(CONNECTION_HEADER, List.of(CONNECTION_KEEP_ALIVE)).get(0).equals(CONNECTION_KEEP_ALIVE);
    }

    private static void printRequest(HttpReq req) {
        System.out.println("Method: " + req.method);
        System.out.println("Url: " + req.url);
        System.out.println( "Headers:");
        req.headers.forEach((k,v) -> {
            System.out.println("%s - %s".formatted(k, v));
        });
        System.out.println( "Body:");
        if(req.body.length > 0) {
            System.out.println(new String(req.body,StandardCharsets.UTF_8));
        } else {
            System.out.println( "Body is Empty");

        }
    }

    private static byte[] readRawRequestHead(InputStream stream) throws Exception {
        var toRead = stream.available();
        if(toRead == 0) {
            toRead = DEFAULT_PACKET_SIZE;
        } 
        var buffer = new byte[toRead];
        var read = stream.read(buffer);
        if(read <= 0){
            return new byte[0];
        }

        return read == toRead ? buffer : Arrays.copyOf(buffer, read);
    }

    private static Map<String, List<String>> readHeaders(String[] lines) {
        var headers = new HashMap<String,List<String>>();

        for (int i = 1; i < lines.length; i++){
            var line = lines[i];
            if (line.isEmpty()){
                break;
            }
            var keyValue = line.split(":",2);
            var key = keyValue[0].toLowerCase().strip();
            var value = keyValue[1].strip();
    
            headers.computeIfAbsent(key,k -> new ArrayList<>()).add(value);
        }

        return headers;
    }

    private static byte[] readBody(InputStream stream, byte[] readBody, int expectedBodyLength ) throws Exception {
        if (readBody.length == expectedBodyLength){
            return readBody;
        }

        var result = new ByteArrayOutputStream(expectedBodyLength);
        result.write(readBody);


        var readBytes = readBody.length;
        var buffer = new byte[DEFAULT_PACKET_SIZE];

        while (readBytes < expectedBodyLength) {
            var read = stream.read(buffer);
            if(read > 0) {
                result.write(buffer,0,read);
                readBytes += read;
            } else {
                break;
            }
        }
        return null;
    }

    private record HttpReq(String method,
            String url,
            Map<String, List<String>> headers,
            byte[] body) {

    }
}
