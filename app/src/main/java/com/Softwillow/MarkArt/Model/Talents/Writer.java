package com.Softwillow.MarkArt.Model.Talents;

public class Writer{
    private String script,isWriter;

    public Writer() {
    }

    public Writer(String script, String isWriter) {
        this.script = script;
        this.isWriter = isWriter;

    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getIsWriter() {
        return isWriter;
    }

    public void setIsWriter(String isWriter) {
        this.isWriter = isWriter;
    }
}
