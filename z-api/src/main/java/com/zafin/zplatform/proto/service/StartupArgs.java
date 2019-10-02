package com.zafin.zplatform.proto.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class StartupArgs {
    static boolean DEBUG = true; 
    public StartupArgs(String[] args) {
        super();
        List<String> modifiedArgs = new ArrayList<>(args.length + 1);
        modifiedArgs.addAll(Arrays.asList(args));
        if (DEBUG) {
            modifiedArgs.add("debug=true");
        }
        this.args = modifiedArgs.stream().toArray(size -> new String[size]);
        System.out.println("Established startup args: " + Arrays.asList(modifiedArgs).toString());
    }

    private final String[] args;
    
    public String[] get() {
        return args;
    }
    
    @Override
    public String toString() {
        return Arrays.asList(args).toString();
    }
}
