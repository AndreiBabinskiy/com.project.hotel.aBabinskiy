package com.project.aBabinskiy.server.command;

import org.json.JSONObject;
import org.json.JSONWriter;

public interface ServerCommand {

    void process(JSONObject requestData, JSONWriter jsonWriter);
}
