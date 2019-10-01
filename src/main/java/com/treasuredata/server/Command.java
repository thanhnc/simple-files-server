package com.treasuredata.server;

import java.io.IOException;

public interface Command {
    boolean execute() throws IOException;
}
