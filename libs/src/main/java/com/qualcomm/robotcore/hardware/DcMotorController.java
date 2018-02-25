package com.qualcomm.robotcore.hardware;

// import cli.FTCCompCTL.*;
import cli.System.Object;

public class DcMotorController {
    int nxtPort;
    LegacyModule legacy;
    public DcMotorController(LegacyModule legacy, int port) {
        nxtPort = port;
        this.legacy = legacy;
    }

    public int getPort() {
        return nxtPort;
    }
}