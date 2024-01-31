package com.kousenit.wikipedia;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("unused")
public class WikiPage {
    @JsonIgnore
    private int pageid;
    @JsonIgnore
    private int ns;
    private String title;
    private String extract;
    @JsonIgnore
    private boolean missing;

    public int getPageid() {
        return pageid;
    }

    public void setPageid(int pageid) {
        this.pageid = pageid;
    }

    public int getNs() {
        return ns;
    }

    public void setNs(int ns) {
        this.ns = ns;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExtract() {
        return extract;
    }

    public void setExtract(String extract) {
        this.extract = extract;
    }

    public boolean getMissing() {
        return missing;
    }

    public void setMissing(boolean missing) {
        this.missing = missing;
    }

    @Override
    public String toString() {
        return "WikiPage{" +
                "pageid=" + pageid +
                ", ns=" + ns +
                ", title='" + title + '\'' +
                ", extract='" + extract + '\'' +
                ", missing=" + missing +
                '}';
    }
}
