package chumakov.alexei.client;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Args {
    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = "-t", description = "Type of the request")
    private String request;

    @Parameter(names = "-k", description = "Key of the cell")
    private String key;

    @Parameter(names = "-v", description = "Value to save in the database")
    private String value;

    @Parameter(names = "-in", description = "Filename with request")
    private String fileName;

    public String getRequest() {
        return request;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return request + " " + key + " " + value;
    }
}
