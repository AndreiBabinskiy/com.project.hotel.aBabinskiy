package com.project.aBabinskiy.server;

import com.project.aBabinskiy.data.Account;
import com.project.aBabinskiy.server.command.CommandFactory;
import com.project.aBabinskiy.server.command.ServerCommand;
import com.project.aBabinskiy.utils.SocketJsonStreams;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(8848);

        System.out.println("Server Started...");

        while (true) {
            Socket client = server.accept();
            SocketJsonStreams streams = new SocketJsonStreams(client);
            JSONTokener jsonTokener = streams.getJsonTokener();
            JSONObject request = (JSONObject)jsonTokener.nextValue();

            JSONObject requestHeader = request.getJSONObject("request-header");
            JSONObject requestData = extractRequestData(request);

            String commandName = requestHeader.getString("command-name");
            ServerCommand command = CommandFactory.getCommand(commandName);

            JSONWriter jsonWriter = streams.getJsonWriter();

            jsonWriter.object();

            try {
                command.process(requestData, jsonWriter);

                jsonWriter.key("response-header").object();
                    jsonWriter.key("response-code").value(200);
                    jsonWriter.key("response-message").value("OK");
                jsonWriter.endObject();
            } catch (Exception e) {
                jsonWriter.key("response-header").object();
                    jsonWriter.key("response-code").value(500);
                    jsonWriter.key("response-message").value(e.getMessage());
                jsonWriter.endObject();
            }

            jsonWriter.endObject();

            streams.flushWriter();
        }
    }
    private static JSONObject extractRequestData(JSONObject request) {
        try {
            return request.getJSONObject("request-data");
        } catch (Exception e) {
            return new JSONObject();
        }
    }
}
