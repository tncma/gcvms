package com.cma.gcvms.repository.support;

/**
 * Static values to use in conjunction with {@link SearchParameters} object.
 * It maps the kind of search you can do in SQL.
 */
public enum SearchMode {
    /**
     * Match exactly the properties
     */
    EQUALS("eq"),
    /**
     * Activates LIKE search and add a '%' prefix and suffix before searching.
     */
    ANYWHERE("any"),
    /**
     * Activate LIKE search and add a '%' prefix before searching.
     */
    STARTING_LIKE("sl"),
    /**
     * Activate LIKE search. User provides the wildcard.
     */
    LIKE("li"),
    /**
     * Activate LIKE search and add a '%' suffix before searching.
     */
    ENDING_LIKE("el");

    private final String code;

    SearchMode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static final SearchMode convert(String code) {
        for (SearchMode searchMode : SearchMode.values()) {
            if (searchMode.getCode().equals(code)) {
                return searchMode;
            }
        }

        return EQUALS; // default
    }
}
