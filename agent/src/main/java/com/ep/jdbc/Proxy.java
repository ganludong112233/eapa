package com.ep.jdbc;

public class Proxy extends Target {
    public Target t;

    public Proxy(Target t) {
        this.t = t;
    }

    public String getContent() {
        return "aaaa:" + t.getContent();
    }
}
