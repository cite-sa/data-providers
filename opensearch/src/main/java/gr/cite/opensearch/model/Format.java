package gr.cite.opensearch.model.elements;

public enum Format {
    ATOM("atom"),
    RSS("rss");

    private final String format;

    Format(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }


}
